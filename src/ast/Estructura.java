/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	estructura:tipo -> nombre:String

public class Estructura extends AbstractTipo {
	
	private String nombre;
	private DefEstructura definicion;

	public Estructura(String nombre) {
		this.nombre = nombre;
	}

	public Estructura(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;

		searchForPositions(nombre);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	public DefEstructura getDefinicion() {
		return definicion;
	}

	public void setDefinicion(DefEstructura defEstructura) {
		this.definicion = defEstructura;
	}
	
	public DefCampo getCampo(String nombreCampo) {
		if (definicion!=null)
			return definicion.getCampo(nombreCampo);
		return null;
	}

	@Override
	public int getSize() {
		return definicion.getCampos().stream()
				.mapToInt(x -> x.getTipo().getSize()).sum();
	}

	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNombreMAPL() {
		return getNombre();
	}

	public int getDesplazamientoCampo(String nombre) {
		if (definicion!=null) {
			return definicion.getDesplazamientoCampo(nombre);
		}
		return -1;
	}
}

