/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	literalReal:expresion -> valor:double

public class LiteralReal extends AbstractExpresion {

	public LiteralReal(double valor, boolean isPositivo) {
		if (!isPositivo)
			this.valor = valor*-1;
		else 
			this.valor = valor;
		this.setTipo(new RealType());
	}

	public LiteralReal(Object valor, boolean isPositivo) {
		this.valor = (valor instanceof Token) ? Double.parseDouble(((Token)valor).getLexeme()) : (Double) valor;
		if (!isPositivo)
			this.valor = this.valor*-1;
		this.setTipo(new RealType());
		
		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private double valor;
}

