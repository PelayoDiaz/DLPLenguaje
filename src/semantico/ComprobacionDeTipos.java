package semantico;

import java.util.List;

import ast.*;
import ast.DefVariable.Ambito;
import main.*;
import visitor.*;

public class ComprobacionDeTipos extends DefaultVisitor {
	
	public final static String ESTRUCTURA = "ast.Estructura";
	public final static String ARRAY = "ast.ArrayType";
	public final static String INTEGER = "ast.IntType";

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	// class DefFuncion { String nombre; List<DefParametro> defparametro; Tipo tipo;
	// List<DefVariableGlobal> defvariableglobal; List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {
		super.visit(node, node);
		// Comprobamos que el tipo sea simple
		if (node.getTipo() != null)
			predicado(!isTypeOf(node.getTipo(), ESTRUCTURA), "Retorno de tipo no simple", node.getStart());
		return null;
	}

	// class DefParametro { String nombre; Tipo tipo; }
	public Object visit(DefVariable node, Object param) {
		if (node.getAmbito()==Ambito.PARAM && node.getTipo() != null)
			predicado(!isTypeOf(node.getTipo(), ARRAY), "Los parámetros deben ser de tipos primitivos",
					node.getStart());
		return null;
	}

	// class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		super.visit(node, param);
		DefFuncion padre = (DefFuncion) param;
		if (node.getExpresion() != null) {
			predicado(mismoTipo(node.getExpresion(), padre), "El tipo de retorno no coincide", node.getStart());
		} else {
			predicado(assertIsNull(padre.getTipo()), "Debe haber un valor de retorno", node.getStart());
		}
		return null;
	}

	// class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		node.setTipo(new IntType());
		node.setModificable(false);
		return null;
	}
	
	// class LiteralReal { double valor; }
	public Object visit(LiteralReal node, Object param) {
		node.setTipo(new RealType());
		node.setModificable(false);
		return null;
	}
	
	// class LiteralChar { String valor; }
	public Object visit(LiteralChar node, Object param) {
		node.setTipo(new CharType());
		node.setModificable(false);
		return null;
	}
	
//	class ExprBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprBinaria node, Object param) {
		super.visit(node, param);
		predicado(isTypeOf(node.getLeft().getTipo(), INTEGER), "La expresión de la izquierda debe ser de tipo entero", node.getStart());
		predicado(isTypeOf(node.getRight().getTipo(), INTEGER), "La expresión de la derecha debe ser de tipo entero", node.getStart());
		predicado(mismoTipo(node.getLeft(), node.getRight()), "Los tipos no coinciden", node.getStart());
		return null;
	}
	
//	class ExprComparativa { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprComparativa node, Object param) {
		super.visit(node, param);
		predicado(isSimpleType(node.getLeft().getTipo()), "La expresión de la izquierda debe ser de tipo simple", node.getStart());
		predicado(isSimpleType(node.getRight().getTipo()), "La expresión de la derecha debe ser de tipo simple", node.getStart());
		predicado(mismoTipo(node.getLeft(), node.getRight()), "Los tipos no coinciden", node.getStart());
		return null;
	}
	
//	class ExprAritmetica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprAritmetica node, Object param) {
		super.visit(node, param);
		predicado(mismoTipo(node.getLeft(), node.getRight()), "Los tipos no coinciden", node.getStart());
		node.setTipo(node.getLeft().getTipo());
		return null;
	}
	
//	class InvocaSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(Invocacion node, Object param) {
		super.visit(node, param);
		boolean condicion = node.hasCorrectNumberOfParameters();
		predicado(condicion, "Número de argumentos incorrecto", node.getStart());
		if (condicion) {
			predicado(compruebaTiposParametros(node.getDefFuncion().getDefparametro(), node.getArgumentos()),
				"El tipo de los argumentos no coincide", node.getStart());
		}
		return null;
	}
	
//	class If { Expresion condicion;  List<Sentencia> cierto; }
	public Object visit(If node, Object param) {
		if (node.getCondicion() != null) {
			node.getCondicion().accept(this, param);
			predicado(isTypeOf(node.getCondicion().getTipo(), INTEGER), 
					"La condición debe ser de tipo entero", node.getStart());
		}
		visitChildren(node.getCierto(), param);
		return null;
	}
		
//	class While { Expresion condicion;  List<Sentencia> cierto; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null) {
			node.getCondicion().accept(this, param);
			predicado(isTypeOf(node.getCondicion().getTipo(), INTEGER), 
					"La condición debe ser de tipo entero", node.getStart());
		}
		visitChildren(node.getCierto(), param);
		return null;
	}
	
//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		super.visit(node, param);
		predicado(isSimpleType(node.getLeft().getTipo()),
				"El valor de la izquierda debe ser de tipo simple", node.getStart());
		predicado(node.getLeft().isModificable(), "Valor de la izquierda no modificable", node.getStart());
		predicado(mismoTipo(node.getLeft(), node.getRight()), "Los tipos no coinciden", node.getStart());
		return null;
	}
	
//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		super.visit(node, param);
		predicado(isSimpleType(node.getExpresion().getTipo()), "Debe ser un tipo simple", node.getStart());
		predicado(node.getExpresion().isModificable(), "Debe ser modificable", node.getStart());
		return null;
	}
	
//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		super.visit(node, param);
		boolean condicion = assertIsNotNull(node.getExpresion().getTipo());
		predicado(condicion,
				"No tiene tipo de retorno", node.getStart());
		if (condicion)
			predicado(isSimpleType(node.getExpresion().getTipo()),
				"Debe ser un tipo simple", node.getStart());
		return null;
	}


	//	class PrintLn { Expresion expresion; }
	public Object visit(PrintLn node, Object param) {
		super.visit(node, param);
		if (node.getExpresion()!=null) {
			boolean condicion = assertIsNotNull(node.getExpresion().getTipo());
			predicado(condicion,
					"No tiene tipo de retorno", node.getStart());
			if (condicion)
				predicado(isSimpleType(node.getExpresion().getTipo()),
					"Debe ser un tipo simple", node.getStart());
		}
		return null;
	}

	//	class PrintSp { Expresion expresion; }
	public Object visit(PrintSp node, Object param) {
		super.visit(node, param);
		boolean condicion = assertIsNotNull(node.getExpresion().getTipo());
		predicado(condicion,
				"No tiene tipo de retorno", node.getStart());
		if (condicion)
			predicado(isSimpleType(node.getExpresion().getTipo()),
				"Debe ser un tipo simple", node.getStart());
		return null;
	}
	
//	class Cast { Tipo tipo;  List<Expresion> argumentos; }
	public Object visit(Cast node, Object param) {
		super.visit(node, param);
		predicado(isSimpleType(node.getTipo()), "Se debe hacer cast hacia tipos simples", node.getStart());
		predicado(isSimpleType(node.getArgumento().getTipo()), "Las expresiones deben ser de tipo simple", node.getStart());
		predicado(node.areDiferentTypes(), "Deben ser distintos tipos", node.getStart());
		return null;
	}
	
//	class Array { Expresion nombre;  Expresion indice; }
	public Object visit(Array node, Object param) {
		super.visit(node, param);
		if (!(node.getNombre() instanceof Navegacion))
			predicado(isTypeOf(node.getNombreType(), ARRAY), "Debe ser tipo array", node.getStart());
		predicado(isTypeOf(node.getIndice().getTipo(), INTEGER), "Debe ser índice entero", node.getStart());
		return null;
	}
	
//	class Navegacion { Expresion argumento;  String right; }
	public Object visit(Navegacion node, Object param) {
		super.visit(node, param);
		predicado(node.isStructType(), "Se requiere tipo Estructura", node.getStart()); //Debe ser una estructura
		predicado(node.getTipo()!=null, "Campo no definido", node.getStart()); //Debe existir el campo
		return null;
	}

	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la
	 * Gramática Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el método "esPrimitivo"):
	 * predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo
	 * primitivo", expr.getStart()); predicado(esPrimitivo(expr.tipo), "La expresión
	 * debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de
	 * donde se leyó el nodo. Si se usa VGen dicho método será generado en todos los
	 * nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion
	 *            Debe cumplirse para que no se produzca un error
	 * @param mensajeError
	 *            Se imprime si no se cumple la condición
	 * @param posicionError
	 *            Fila y columna del fichero donde se ha producido el error. Es
	 *            opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobación de tipos", mensajeError, posicionError);
	}
	
	/**
	 * Método para saber si un tipo es de tipo simple o no.
	 * @param tipo
	 * @return
	 */
	private boolean isSimpleType(Tipo tipo) {
		return !isTypeOf(tipo, ESTRUCTURA) && !isTypeOf(tipo, ARRAY);
	}
	
	/**
	 * Método para comprobar si un tipo es de una determinada clase
	 * @param tipo El tipo a comprobar
	 * @param clase la clase con la que debe coincidir.
	 * @return
	 */
	private boolean isTypeOf(Tipo tipo, String clase) { 
		Class<?> classType;
		try {
			classType = Class.forName(clase);
			if (tipo!=null)
				return tipo.getClass()==classType;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return false;
	} 
	
	private boolean assertIsNull(Tipo tipo) {
		return tipo == null;
	}
	
	private boolean assertIsNotNull(Tipo tipo) {
		return tipo!=null;
	}
	
	private boolean compruebaTiposParametros(List<DefVariable> defParametro, List<Expresion> argumentos) {
		for (int i=0; i<defParametro.size(); i++) {
			if (!mismoTipo(argumentos.get(i), defParametro.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean mismoTipo(Expresion a, DefVariable defParametro) {
		return defParametro.getTipo().getClass()==a.getTipo().getClass();
	}

	private boolean mismoTipo(Expresion a, DefFuncion b) {
		return (b.getTipo()==null) ? 
				false : 
				(a.getTipo().getClass() == b.getTipo().getClass());
	}

	private boolean mismoTipo(Expresion a, Expresion b) {
		return (a.getTipo()==null || b.getTipo()==null) ?
				false :
					(a.getTipo().getClass() == b.getTipo().getClass());
	}

	private GestorErrores gestorErrores;
}
