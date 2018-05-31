package ast;

public abstract class AbstractBooleanExpresion extends AbstractOperatorExpresion {
	
	@Override
	public Tipo getTipo() {
		IntType tipo = new IntType();
		super.setTipo(tipo);
		return tipo;
	}

}
