/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	literalInt:expresion -> valor:int

public class LiteralInt extends AbstractExpresion {

	public LiteralInt(int valor, boolean isPositivo) {
		if (!isPositivo)
			this.valor = valor*-1;
		else 
			this.valor = valor;
		this.setTipo(new IntType());
	}

	public LiteralInt(Object valor, boolean isPositivo) {
		this.valor = (valor instanceof Token) ? Integer.parseInt(((Token)valor).getLexeme()) : (Integer) valor;
		if (!isPositivo)
			this.valor = this.valor*-1;
		this.setTipo(new IntType());
		
		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int valor;
}

