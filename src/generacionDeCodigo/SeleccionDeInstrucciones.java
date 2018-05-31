package generacionDeCodigo;

import java.io.*;
import java.util.*;

import ast.*;
import ast.DefVariable.Ambito;
import visitor.*;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	enum Funcion {
		DIRECCION, VALOR
	}

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;

		instruccion.put("+", "add");
		instruccion.put("-", "sub");
		instruccion.put("*", "mul");
		instruccion.put("/", "div");
		instruccion.put(">", "gt");
		instruccion.put("<", "lt");
		instruccion.put(">=", "ge");
		instruccion.put("<=", "le");
		instruccion.put("==", "eq");
		instruccion.put("!=", "ne");
		instruccion.put("&&", "and");
		instruccion.put("||", "or");
		instruccion.put("!", "not");
	}

	// class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {
		genera("#source \"" + sourceFile + "\"");
		genera("call main");
		genera("halt");
		super.visit(node, param);
		return null;
	}
	
//	class DefEstructura { String nombre;  List<Campo> campo; }
	public Object visit(DefEstructura node, Object param) {
		visitChildren(node.getCampos(), param);
		
		StringBuilder cadena = new StringBuilder();
		cadena.append("#type "+node.getNombre()+": {\n");
		node.getCampos().forEach(x -> cadena.append(x.getNombre()+":"+x.getTipo().getNombreMAPL()+"\n"));
		cadena.append("}");
		
		genera(cadena.toString());
		return null;
	}

//	class DefFuncion { String nombre;  List<DefParametro> defparametro;  Tipo tipo;  List<DefVariableGlobal> defvariableglobal;  List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {
		lastFunction = node;
		genera("#FUNC " + node.getNombre());
		visitChildren(node.getDefparametro(), param);
		
		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
			genera("#RET "+node.getTipo().getNombreMAPL());
		}
		
		visitChildren(node.getDefvariable(), param);
				
		genera(node.getNombre()+":");
		if (node.getDefvariable().size()>0)
			genera("enter "+node.getLocalVariablesSize());
		
		visitChildren(node.getSentencia(), param);
		if (node.getTipo()==null)
			genera("ret "+node.getSize()+", "+node.getLocalVariablesSize()+", "+node.getParamsSize());
		return null;
	}
	
	// class DefVariableGlobal { String nombre; Tipo tipo; }
	public Object visit(DefVariable node, Object param) {
		super.visit(node, param);
		genera("#"+node.getAmbito().toString()+" "+ node.getNombre() + ":" + node.getTipo().getNombreMAPL());
		return null;
	}

	// class Asignacion { Expresion left; Expresion right; }
	public Object visit(Asignacion node, Object param) {
		genera("#line " + node.getEnd().getLine());
		if (node.getLeft() != null)
			node.getLeft().accept(this, Funcion.DIRECCION);
		if (node.getRight() != null)
			node.getRight().accept(this, Funcion.VALOR);
		genera("store", node.getLeft().getTipo());
		return null;
	}

	// class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		genera("#line " + node.getEnd().getLine());
		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, Funcion.VALOR);
			genera("out", node.getExpresion().getTipo());
		}
		return null;
	}

	// class PrintLn { Expresion expresion; }
	public Object visit(PrintLn node, Object param) {
		genera("#line " + node.getEnd().getLine());
		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, param);
			genera("out", node.getExpresion().getTipo());
		}
		genera("pushb 10");
		genera("outb");
		return null;
	}

	// class PrintSp { Expresion expresion; }
	public Object visit(PrintSp node, Object param) {
		genera("#line " + node.getEnd().getLine());
		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, param);
			genera("out", node.getExpresion().getTipo());
		}
		genera("pushb 32");
		genera("outb");
		return null;
	}
	
	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		genera("#line " + node.getEnd().getLine());
		super.visit(node, Funcion.VALOR);
		genera("ret "+lastFunction.getSize()+", "+lastFunction.getLocalVariablesSize()+", "+lastFunction.getParamsSize());
		return null;
	}

	// class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		if (((Funcion) param) == Funcion.VALOR) {
			visit(node, Funcion.DIRECCION);
			genera("load", node.getTipo());
		} else { // Funcion.DIRECCION
			assert (param == Funcion.DIRECCION);
			if (node.getDefinicion().getAmbito()==Ambito.GLOBAL)
				genera("pusha " + node.getDefinicion().getDireccion());
			else {
				genera("pusha BP");
				genera("push " + node.getDefinicion().getDireccion());
				genera("add");
			}
			
		}
		return null;
	}
	
//	class Array { Expresion nombre;  Expresion indice; }
	public Object visit(Array node, Object param) {
		if (node.getNombre() != null)
			node.getNombre().accept(this, Funcion.DIRECCION);
		if (node.getIndice() != null)
			node.getIndice().accept(this, Funcion.VALOR);
		genera("push " + node.getTipo().getSize()); //Tamaño de cada elemento
		genera("mul"); // | -> Genera direccion del elemento
		genera("add"); // |
		if (param == Funcion.VALOR) {
			genera("load"+node.getTipo().getSufijo());
		}
		return null;
	}
	
	// class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		genera("push " + node.getValor());
		return null;
	}

	// class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		genera("pushf " + node.getValor());
		return null;
	}

	// class LiteralChar { String valor; }
	public Object visit(LiteralChar node, Object param) {
		genera("pushb " + node.getAsciValue());
		return null;
	}
	
//	class Navegacion { Expresion argumento;  String right; }
	public Object visit(Navegacion node, Object param) {
		if (node.getArgumento() != null) {			// | -> Comienzo de la estructura
			node.getArgumento().accept(this, Funcion.DIRECCION);// |
			genera("push " + node.getDesplazamientoCampo());
			genera("add");
			if (((Funcion)param)==Funcion.VALOR)
				genera("load"+node.getTipo().getSufijo());
		}
		return null;
	}
	
//	class ExprBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprBinaria node, Object param) {
		super.visit(node, Funcion.VALOR);
		genera(instruccion.get(node.getOperador()));
		return null;
	}
	
//	class ExprComparativa { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprComparativa node, Object param) {
		super.visit(node, param);
		genera(instruccion.get(node.getOperador())+node.getSufijo());
		return null;
	}
	
//	class ExprAritmetica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprAritmetica node, Object param) {
		super.visit(node, param);
		genera(instruccion.get(node.getOperador())+node.getTipo().getSufijo());
		return null;
	}
	
//	class IfElse { Expresion condicion;  List<Sentencia> cierto;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		genera("#line " + node.getStart().getLine());
		ifCount++;
		int copy = ifCount;
		if (node.getCondicion() != null) {
			node.getCondicion().accept(this, Funcion.VALOR);
			genera("jz else"+copy);
		}
		visitChildren(node.getCierto(), param);
		genera("jmp finIf"+copy+":");
		genera("else"+copy+":");
		visitChildren(node.getFalso(), param);
		genera("finIf"+copy+":");
		return null;
	}
	
//	class While { Expresion condicion;  List<Sentencia> cierto; }
	public Object visit(While node, Object param) {
		genera("#line " + node.getStart().getLine());
		whileCount++;
		int copy = whileCount;
		genera("while"+copy+":");
		if (node.getCondicion() != null) {
			node.getCondicion().accept(this, Funcion.VALOR);
			genera("jz finWhile"+copy);
		}
		visitChildren(node.getCierto(), param);
		genera("jmp while"+copy);
		genera("finWhile"+copy+":");
		return null;
	}
	
//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		genera("#line " + node.getStart().getLine());
		super.visit(node, param);
		genera("in"+node.getExpresion().getTipo().getSufijo());
		genera("store"+node.getExpresion().getTipo().getSufijo());
		return null;
	}
	
//	class Negacion { Expresion argumentos; }
	public Object visit(Negacion node, Object param) {
		super.visit(node, param);
		genera("not");
		return null;
	}
	
//	class Invocacion { String nombre;  List<Expresion> argumentos; }
	public Object visit(Invocacion node, Object param) {
		genera("#line " + node.getStart().getLine());
		super.visit(node, Funcion.VALOR);
		genera("call "+node.getNombre());
		if (node.isSentencia() && node.getDefFuncion().getTipo()!=null)
			genera("pop"+node.getDefFuncion().getTipo().getSufijo());
		return null;
	}
	
//	class Cast { Tipo tipo;  List<Expresion> argumentos; }
	public Object visit(Cast node, Object param) {
		genera("#line " + node.getStart().getLine());
		super.visit(node, Funcion.VALOR);
		genera(node.getInstruction());
		return null;
	}
	
	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	private void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}

	private PrintWriter writer;
	private String sourceFile;
	private Map<String, String> instruccion = new HashMap<String, String>();
	private int ifCount = 0;
	private int whileCount = 0;
	private DefFuncion lastFunction = null;
}
