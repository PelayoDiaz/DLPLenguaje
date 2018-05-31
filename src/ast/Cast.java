/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	cast:expresion -> tipo:tipo  argumentos:expresion*

public class Cast extends AbstractExpresion {

	public Cast(Tipo tipo, Expresion argumento) {
		this.tipo = tipo;
		this.argumento = argumento;

		searchForPositions(tipo, argumento);	// Obtener linea/columna a partir de los hijos
	}

	public Cast(Object tipo, Object argumento) {
		this.tipo = (Tipo) tipo;
		this.argumento = (Expresion) argumento;

		searchForPositions(tipo, argumento);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Expresion getArgumento() {
		return argumento;
	}
	public void setArgumentos(Expresion argumentos) {
		this.argumento = argumentos;
	}
	
	public String getInstruction() {
		return argumento.getTipo().getSufijo() + "2" + tipo.getSufijo();
	}
	
	public boolean areDiferentTypes() {
		return argumento.getTipo().getClass()!=tipo.getClass();
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
	private Expresion argumento;
}

