/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	defFuncion:definicion -> nombre:String  defparametro:defParametro*  tipo:tipo  defvariable:defVariable*  sentencia:sentencia*

public class DefFuncion extends AbstractDefinicion {

	public DefFuncion(String nombre, List<DefVariable> defParametros, Tipo tipo, List<DefVariable> defVariables, List<Sentencia> sentencia) {
		this.nombre = nombre;
		this.defParametros = defParametros;
		this.tipo = tipo;
		this.defVariables = defVariables;
		this.sentencia = sentencia;

		searchForPositions(defParametros, tipo, defVariables, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefFuncion(Object nombre, Object defParametros, Object tipo, Object defVariables, Object sentencia) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.defParametros = (List<DefVariable>) defParametros;
		this.tipo = (Tipo) tipo;
		this.defVariables = (List<DefVariable>) defVariables;
		this.sentencia = (List<Sentencia>) sentencia;

		searchForPositions(nombre, defParametros, tipo, defVariables, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefVariable> getDefparametro() {
		return defParametros;
	}
	public void setDefparametro(List<DefVariable> defparametro) {
		this.defParametros = defparametro;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Método que devuelve la suma de los tamaños de las variables locales
	 * @return Int. Suma de los tamaños de las variables locales
	 */
	public int getLocalVariablesSize() {
		return (defVariables.size()>0) ? 
				defVariables.stream().mapToInt(x -> x.getTipo().getSize()).sum() :
				0;
	}
	
	/**
	 * Método que devuelve la suma de los tamaños de los parametros de la funcion
	 * @return
	 */
	public int getParamsSize() {
		return (defParametros.size()>0) ? 
				defParametros.stream().mapToInt(x -> x.getTipo().getSize()).sum() : 
				0;
	}
	
	public int getSize() {
		return (tipo!=null) ? tipo.getSize() : 0;
	}

	public List<DefVariable> getDefvariable() {
		return defVariables;
	}
	public void setDefvariable(List<DefVariable> defvariable) {
		this.defVariables = defvariable;
	}

	public List<Sentencia> getSentencia() {
		return sentencia;
	}
	public void setSentencia(List<Sentencia> sentencia) {
		this.sentencia = sentencia;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefVariable> defParametros;
	private Tipo tipo;
	private List<DefVariable> defVariables;
	private List<Sentencia> sentencia;
	
}

