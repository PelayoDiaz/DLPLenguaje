/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	array:expresion -> nombre:expresion  indice:expresion

public class Array extends AbstractExpresion {

	public Array(Expresion nombre, Expresion indice) {
		this.nombre = nombre;
		this.setModificable(true);
		this.indice = indice;

		searchForPositions(nombre, indice);	// Obtener linea/columna a partir de los hijos
	}

	public Array(Object nombre, Object indice) {
		this.nombre = (Expresion) nombre;
		this.indice = (Expresion) indice;
		this.setModificable(true);

		searchForPositions(nombre, indice);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getNombre() {
		return nombre;
	}
	public void setNombre(Expresion nombre) {
		this.nombre = nombre;
	}
	
	public void setDefinicion(DefVariable variable) {
		this.definicion=variable;
		if (definicion!=null) {
			if (definicion.getTipo() instanceof ArrayType)
				this.setTipo(getTipo((ArrayType) definicion.getTipo()));
			else
				this.setTipo(definicion.getTipo());
		}
	}
	
	/**
	 * Método recursivo para buscar el tipo de un array.
	 * Si el tipo es arrayType, se mira su tipo, si este vuelve a serlo
	 * se entra en la recursividad hasta encontrar el tipo.
	 * @param tipo
	 * @return
	 */
	private Tipo getTipo(ArrayType tipo) {
		if (tipo.getTipo() instanceof ArrayType) {
			return getTipo((ArrayType)tipo.getTipo());
		} else {
			return tipo.getTipo();
		}
	}
	
	public Tipo getNombreType(){
		if (nombre instanceof Array) {
			return getNombreType((Array) nombre);
		} else {
			return nombre.getTipo();
		}
	}
	
	/**
	 * Método recursivo para buscar el tipo del nombre.
	 * @param node
	 * @return
	 */
	private Tipo getNombreType(Array node) {
		if (node.getNombre() instanceof Array) {
			return getNombreType((Array) node.getNombre());
		} else {
			return node.getNombre().getTipo();
		}
	}
	
	@Override
	public Tipo getTipo() {
		if (nombre instanceof Navegacion) {
			return ((Navegacion)nombre).getTipo();
		} else {
			return super.getTipo();
		}
	}

	public Expresion getIndice() {
		return indice;
	}
	public void setIndice(Expresion indice) {
		this.indice = indice;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion nombre;
	private Expresion indice;
	private DefVariable definicion;
	
}

