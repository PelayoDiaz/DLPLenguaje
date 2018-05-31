/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	navegacion:expresion -> argumento:expresion  right:String

public class Navegacion extends AbstractExpresion {

	public Navegacion(Expresion argumento, String right) {
		this.argumento = argumento;
		this.right = right;
		this.setModificable(true);

		searchForPositions(argumento);	// Obtener linea/columna a partir de los hijos
	}

	public Navegacion(Object argumento, Object right) {
		this.argumento = (Expresion) argumento;
		this.right = (right instanceof Token) ? ((Token)right).getLexeme() : (String) right;
		this.setModificable(true);

		searchForPositions(argumento, right);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getArgumento() {
		return argumento;
	}
	public void setArgumento(Expresion argumento) {
		this.argumento = argumento;
	}

	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion argumento;
	private String right;
	
	public boolean isStructType() {
		return (argumento.getTipo()!=null) ? 
				argumento.getTipo().getClass()==Estructura.class :
				false;
	}
	
	@Override
	public Tipo getTipo() {
		if (super.getTipo()==null)
			setTipo(searchCampoType());
		return super.getTipo();
	}
	
	/**
	 * Busca el tipo de la navegación tras llegar a la defincion del campo correspondiente
	 * @param argumento el argumento a buscar
	 * @return el tipo o null si el campo no existe
	 */
	private Tipo searchCampoType() {
		if (argumento.getTipo()!=null && argumento.getTipo() instanceof Estructura) {
			DefCampo campo = ((Estructura)argumento.getTipo()).getCampo(getRight());
			if (campo!= null && campo.getTipo() instanceof ArrayType)
				return ((ArrayType)campo.getTipo()).getTipo();
			return (campo!=null) ? campo.getTipo() : null;
		}
		return null;
	}
	
	/**
	 * Devuelve el desplazamiento de un campo dentro de una estructura.
	 * @return el Desplazamiento = tamaño tipo del anterior * posicion que ocupa., 
	 * -1 en caso contrario
	 */
	public int getDesplazamientoCampo() {
		if (argumento.getTipo()!=null && argumento.getTipo() instanceof Estructura) {
			return ((Estructura)argumento.getTipo()).getDesplazamientoCampo(right);
		}
		return -1;
	}
}

