/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	variable:expresion -> nombre:String

public class Variable extends AbstractExpresion {

	public Variable(String nombre) {
		this.nombre = nombre;
		this.setModificable(true);
	}

	public Variable(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.setModificable(true);
		searchForPositions(nombre);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private DefVariable definicion;

	public void setDefinicion(DefVariable variable) {
		this.definicion=variable;
		if (definicion!=null)
			this.setTipo(definicion.getTipo());
	}

	public DefVariable getDefinicion() {
		return definicion;
	}
}

