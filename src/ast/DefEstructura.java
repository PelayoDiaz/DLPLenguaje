/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;

import visitor.*;

//	defEstructura:definicion -> nombre:String  defvariablelocal:defVariableLocal*

public class DefEstructura extends AbstractDefinicion {

	public DefEstructura(String nombre, List<DefCampo> defCampos) {
		this.nombre = nombre;
		this.defCampos = defCampos;

		searchForPositions(defCampos);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefEstructura(Object nombre, Object defCampos) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.defCampos = (List<DefCampo>) defCampos;

		searchForPositions(nombre, defCampos);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefCampo> getCampos() {
		return defCampos;
	}
	public void setDefvariablelocal(List<DefCampo> defCampos) {
		this.defCampos = defCampos;
	}
	
	/**
	 * Devulve el desplazamiento de un campo dentro de una estructura
	 * Desplazamiento = tamaño tipo del anterior * posicion que ocupa.
	 * @param nombre
	 * @return int el desplazamiento de un campo dentro de la estructura
	 * -1 en caso de error.
	 */
	public int getDesplazamientoCampo(String nombre) {
		for (int i=0; i<defCampos.size(); i++) {
			if (defCampos.get(i).getNombre().equals(nombre)) {
				if (i==0) {
					return 0;
				} else {
					return defCampos.get(i-1).getTipo().getSize()*i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Método para buscar un determinado campo
	 * @param nombreCampo El nombre del campo a buscar
	 * @return El campo si lo encuentra, null en caso contrario.
	 */
	public DefCampo getCampo(String nombreCampo) {
		return defCampos.stream()
				.filter(x -> x.getNombre().equals(nombreCampo))
				.findFirst().orElse(null);
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefCampo> defCampos;
}

