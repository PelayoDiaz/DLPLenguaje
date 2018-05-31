/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	negacion:expresion -> argumentos:expresion

public class Negacion extends AbstractExpresion {

	public Negacion(Expresion argumentos) {
		this.argumentos = argumentos;

		searchForPositions(argumentos);	// Obtener linea/columna a partir de los hijos
	}

	public Negacion(Object argumentos) {
		this.argumentos = (Expresion) argumentos;

		searchForPositions(argumentos);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getArgumentos() {
		return argumentos;
	}
	public void setArgumentos(Expresion argumentos) {
		this.argumentos = argumentos;
	}
	
	@Override
	public Tipo getTipo() {
		return new IntType();
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion argumentos;
}

