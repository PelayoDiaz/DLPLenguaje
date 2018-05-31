package generacionDeCodigo;

import java.util.concurrent.atomic.AtomicInteger;

import ast.*;
import ast.DefVariable.Ambito;
import visitor.*;

/** 
 * Clase encargada de asignar direcciones a las variables 
 */
public class GestionDeMemoria extends DefaultVisitor {
	
	//Hacer el visitor en las definiciones de variables, ...
	
	private int sumGlobalVariableSize = 0;
	private int sumLocalVariableSize = 0;
	
//	class DefVariable { String nombre;  Tipo tipo; }
	public Object visit(DefVariable node, Object param) {
		super.visit(node, param);
		
		if (node.getAmbito()==Ambito.GLOBAL) {
			node.setDireccion(sumGlobalVariableSize);
			sumGlobalVariableSize += node.getTipo().getSize();
		} else if (node.getAmbito()==Ambito.LOCAL){
			sumLocalVariableSize -= node.getTipo().getSize();
			node.setDireccion(sumLocalVariableSize);
		}
		return null;
	}
	
//	class DefFuncion { String nombre;  List<DefParametro> defparametro;  Tipo tipo;  List<DefVariableGlobal> defvariableglobal;  List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {
		sumLocalVariableSize=0;
		super.visit(node, param);
		int sumParametersSize = 4; //BP+4
		for (int i=node.getDefparametro().size()-1; i>=0; i--) {
			node.getDefparametro().get(i).setDireccion(sumParametersSize);
			sumParametersSize += node.getDefparametro().get(i).getTipo().getSize();
		}
		return null;
	}
	
//	class DefEstructura { String nombre;  List<Campo> campo; }
	public Object visit(DefEstructura node, Object param) {
		super.visit(node, param);
		AtomicInteger sumCamposSize = new AtomicInteger(0); //int sumCamposSize = 0;
		node.getCampos().forEach(x -> {
			x.setDireccion(sumCamposSize.getAndAdd(x.getTipo().getSize()));
		});
		return null;
	}
}
