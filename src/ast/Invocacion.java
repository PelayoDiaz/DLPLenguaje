/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	invocacion:expresion -> nombre:String  argumentos:expresion*

public class Invocacion extends AbstractExpresion {
	
	private String nombre;
	private List<Expresion> argumentos;
	private DefFuncion defFuncion;
	private boolean esSentencia;

	public Invocacion(String nombre, List<Expresion> argumentos, boolean esSentencia) {
		this.nombre = nombre;
		this.argumentos = argumentos;
		this.esSentencia = esSentencia;

		searchForPositions(argumentos);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Invocacion(Object nombre, Object argumentos, boolean esSentencia) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.argumentos = (List<Expresion>) argumentos;
		this.esSentencia = esSentencia;

		searchForPositions(nombre, argumentos);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Expresion> getArgumentos() {
		return argumentos;
	}
	public void setArgumentos(List<Expresion> argumentos) {
		this.argumentos = argumentos;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public DefFuncion getDefFuncion() {
		return defFuncion;
	}

	public void setDefFuncion(DefFuncion defFuncion) {
		this.defFuncion = defFuncion;
		this.setTipo(defFuncion.getTipo());
	}
	
	public boolean hasCorrectNumberOfParameters() {
		return getDefFuncion().getDefparametro().size()==getArgumentos().size();
	}

	public boolean isSentencia() {
		return this.esSentencia;
	}
}

