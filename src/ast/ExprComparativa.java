/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	exprComparativa:expresion -> left:expresion  operador:String  right:expresion

public class ExprComparativa extends AbstractBooleanExpresion {

	public ExprComparativa(Expresion left, String operador, Expresion right) {
		super.setLeft(left);
		super.setOperador(operador);
		super.setRight(right);

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public ExprComparativa(Object left, Object operador, Object right) {
		super.setLeft((Expresion) left);
		super.setOperador((operador instanceof Token) ? ((Token)operador).getLexeme() : (String) operador);
		super.setRight((Expresion) right);

		searchForPositions(left, operador, right);	// Obtener linea/columna a partir de los hijos
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public char getSufijo() {
		return getLeft().getTipo().getSufijo();
	}

}

