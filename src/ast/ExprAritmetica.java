/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	exprAritmetica:expresion -> left:expresion  operador:String  right:expresion

public class ExprAritmetica extends AbstractOperatorExpresion {

	public ExprAritmetica(Expresion left, String operador, Expresion right) {
		super.setLeft(left);
		super.setOperador(operador);
		super.setRight(right);

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public ExprAritmetica(Object left, Object operador, Object right) {
		super.setLeft((Expresion) left);
		super.setOperador((operador instanceof Token) ? ((Token)operador).getLexeme() : (String) operador);
		super.setRight((Expresion) right);

		searchForPositions(left, operador, right);	// Obtener linea/columna a partir de los hijos
	}
	
	@Override
	public Tipo getTipo() {
		if (super.getTipo()==null)
			super.setTipo(super.getLeft().getTipo());
		return super.getTipo();
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
}

