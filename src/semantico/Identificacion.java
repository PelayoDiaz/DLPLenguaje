package semantico;

import java.util.*;

import ast.*;
import ast.DefVariable.Ambito;
import main.*;
import visitor.*;


public class Identificacion extends DefaultVisitor {

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}
	
	/**
	 * Método que comprueba si una determinada función ya ha sido definida en el programa
	 * Si la función fue definida previamente entonces su nombre estará almacenado, por lo 
	 * que se comprueba su existencia. En caso contrario se almacena el valor para posibles
	 * futuras comprobaciones
	 */
	public Object visit(DefFuncion node, Object param) {
		DefFuncion defFuncion = funciones.get(node.getNombre());
		predicado(defFuncion==null, "Funcion ya definida: " + node.getNombre(), node.getStart());
		funciones.put(node.getNombre(), node);
		variables.set();
		return super.visit(node, param);
	}
	
	
//	class Invocacion { String nombre;  List<Expresion> argumentos; }
	public Object visit(Invocacion node, Object param) {
		DefFuncion defFuncion = funciones.get(node.getNombre());
		predicado(defFuncion!=null, "Funcion no definida: " + node.getNombre(), node.getStart());
		node.setDefFuncion(defFuncion);
		super.visit(node, param);
		return null;
	}
	
	public Object visit(DefEstructura node, Object param) {
		DefEstructura defEstructura = estructuras.get(node.getNombre());
		predicado(defEstructura==null, "Estructura ya definida", node.getStart());
		estructuras.put(node.getNombre(), node);
		return super.visit(node, new HashMap<String, DefCampo>());
	}
	
	public Object visit(Estructura node, Object param) {
		DefEstructura defEstructura = estructuras.get(node.getNombre());
		predicado(defEstructura!=null, "Estructura no definida", node.getStart());
		node.setDefinicion(defEstructura);
		return super.visit(node, param);
	}
	
	/**
	 * Cada campo recibe de su padre una estructura de datos donde comprueba
	 * si tiene hermanos llamados del mismo modo.
	 */
	public Object visit(DefCampo node, Object param) {
		@SuppressWarnings("unchecked")
		Map<String, DefCampo> campos = (HashMap<String, DefCampo>) param;
		DefCampo defCampo = campos.get(node.getNombre());
		predicado(defCampo==null, "Campo ya definido", node.getStart());
		campos.put(node.getNombre(), node);
		return super.visit(node, param);
	}
	
	public Object visit(DefVariable node, Object param) {
		DefVariable variable = variables.getFromTop(node.getNombre());
		predicado(variable == null, "Variable ya definida de forma: "+ node.getAmbito().toString(), node.getStart());
		variables.put(node.getNombre(), node);
		return super.visit(node, param);
	}

	public Object visit(Variable node, Object param) {
		DefVariable variableLocal = variables.getFromTop(node.getNombre());
		DefVariable variableGlobal = variables.getFromAny(node.getNombre());
		boolean condicion = variableLocal!=null //Debe existir de forma local
				|| (variableGlobal!=null && variableGlobal.getAmbito()==Ambito.GLOBAL); //...o ser una variable global
		predicado(condicion, "Variable no definida", node.getStart());
		node.setDefinicion((variableLocal!=null) ? variableLocal : variableGlobal);
		return super.visit(node, param);
	}
	
//	class Array { Expresion nombre;  Expresion indice; }
	public Object visit(Array node, Object param) {
		String nombre = searchArrayName(node);
		DefVariable variableLocal = variables.getFromTop(nombre);
		DefVariable variableGlobal = variables.getFromAny(nombre);
		boolean condicion = variableLocal!=null //Debe existir de forma local
				|| (variableGlobal!=null && variableGlobal.getAmbito()==Ambito.GLOBAL); //...o ser una variable global
		predicado(condicion, "Variable no definida", node.getStart());
		node.setDefinicion((variableLocal!=null) ? variableLocal : variableGlobal);
		return super.visit(node, param);
	}
	
	//------------------------------Métodos Auxiliares-----------------------------
	
	/**
	 * Método recursivo utilizado para buscar el nombre de un array. Si la expresion correspondiente
	 * con el atributo nombre del Array es una variable, entonces se devuelve el nombre de la misma,
	 * Si es un array se entra en la recursividad para encontrar el nombre. 
	 * Cuando lo que llega es una navegacion entonces se dan dos situaciones: que la parte izquierda
	 * sea una variable o de nuevo un array. En la primera entonces se devuelve el nombre directamente,
	 * en la segunda se aplica recursividad.
	 * 
	 * El caso base se da cuando la expresion es una variable.
	 * @param node. El Array al que se le debe buscar el nombre
	 * @return String. El nombre del array.
	 */
	private String searchArrayName(Array node) {
		String nombre = null;
		if (node.getNombre() instanceof Variable) {
			return ((Variable)node.getNombre()).getNombre();
		} else if (node.getNombre() instanceof Array) {
			nombre = searchArrayName((Array)node.getNombre());
		} else if (node.getNombre() instanceof Navegacion) {
			if (((Navegacion)node.getNombre()).getArgumento() instanceof Array)
				return searchArrayName((Array) ((Navegacion)node.getNombre()).getArgumento());
			else if (((Navegacion)node.getNombre()).getArgumento() instanceof Variable)
				return ((Variable) ((Navegacion)node.getNombre()).getArgumento()).getNombre();
		}
		return nombre;
	}
	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso:
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", expr.getStart());
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Identificación", mensajeError, posicionError);
	}

	private GestorErrores gestorErrores;
	private Map<String, DefFuncion> funciones = new HashMap<String, DefFuncion>();
	private Map<String, DefEstructura> estructuras = new HashMap<String, DefEstructura>();
	private ContextMap<String, DefVariable> variables = new ContextMap<String, DefVariable>();

}
