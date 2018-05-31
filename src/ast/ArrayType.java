/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	arrayType:tipo -> size:int  tipo:tipo

public class ArrayType extends AbstractTipo {

	public ArrayType(int size, Tipo tipo) {
		this.length = size;
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public ArrayType(Object size, Object tipo) {
		this.length = (size instanceof Token) ? Integer.parseInt(((Token)size).getLexeme()) : (Integer) size;
		this.tipo = (Tipo) tipo;

		searchForPositions(size, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public int getLength() {
		return length;
	}
	public void setLength(int size) {
		this.length = size;
	}
	
	@Override
	public int getSize() {
		return length*tipo.getSize();
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int length;
	private Tipo tipo;
	
	@Override
	public char getSufijo() {
		return tipo.getSufijo();
	}

	@Override
	public String getNombreMAPL() {
		return length+"*"+tipo.getNombreMAPL();
	}
}

