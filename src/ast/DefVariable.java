/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	defVariable:definicion -> nombre:String  tipo:tipo  global:boolean

public class DefVariable extends AbstractDefinicion {
	
	public enum Ambito {LOCAL, GLOBAL, PARAM};

	public DefVariable(String nombre, Tipo tipo, String global) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.ambito = Ambito.valueOf(global);

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public DefVariable(Object nombre, Object tipo, Object global) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.tipo = (Tipo) tipo;
		this.ambito = Ambito.valueOf((String) global);

		searchForPositions(nombre, tipo, global);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Ambito getAmbito() {
		return ambito;
	}
	public void setAmbito(Ambito global) {
		this.ambito = global;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	public int getDireccion() {
		return direccion;
	}
	
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	private String nombre;
	private Tipo tipo;
	private Ambito ambito;
	private int direccion;
}

