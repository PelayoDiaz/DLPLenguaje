/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefinicion(), param);
		return null;
	}

	//	class DefVariableGlobal { String nombre;  Tipo tipo; }
	public Object visit(DefVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefEstructura { String nombre;  List<Campo> campo; }
	public Object visit(DefEstructura node, Object param) {
		visitChildren(node.getCampos(), param);
		return null;
	}

	//	class DefFuncion { String nombre;  List<DefParametro> defparametro;  Tipo tipo;  List<DefVariableGlobal> defvariableglobal;  List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {
		visitChildren(node.getDefparametro(), param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		visitChildren(node.getDefvariable(), param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		return null;
	}

	//	class RealType {  }
	public Object visit(RealType node, Object param) {
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		return null;
	}

	//	class Estructura { String nombre; }
	public Object visit(Estructura node, Object param) {
		return null;
	}

	//	class ArrayType { int size;  Tipo tipo; }
	public Object visit(ArrayType node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class PrintLn { Expresion expresion; }
	public Object visit(PrintLn node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class PrintSp { Expresion expresion; }
	public Object visit(PrintSp node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> cierto; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCierto(), param);
		return null;
	}

	//	class IfElse { Expresion condicion;  List<Sentencia> cierto;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCierto(), param);
		visitChildren(node.getFalso(), param);
		return null;
	}
	
//	class InvocaSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(Invocacion node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}

	//	class Navegacion { Expresion argumento;  String right; }
	public Object visit(Navegacion node, Object param) {
		if (node.getArgumento() != null)
			node.getArgumento().accept(this, param);
		return null;
	}

	//	class ExprBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprBinaria node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}
	
//	class ExprAritmetica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprAritmetica node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}
	
//	class ExprComparativa { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprComparativa node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {
		return null;
	}

	//	class LiteralChar { String valor; }
	public Object visit(LiteralChar node, Object param) {
		return null;
	}

	//	class LiteralReal { double valor; }
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	//	class Cast { Tipo tipo;  List<Expresion> argumentos; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getArgumento() != null)
			node.getArgumento().accept(this, param);
		return null;
	}

	//	class Negacion { Expresion argumentos; }
	public Object visit(Negacion node, Object param) {
		if (node.getArgumentos() != null)
			node.getArgumentos().accept(this, param);
		return null;
	}

	//	class Array { Expresion nombre;  Expresion indice; }
	public Object visit(Array node, Object param) {
		if (node.getNombre() != null)
			node.getNombre().accept(this, param);
		if (node.getIndice() != null)
			node.getIndice().accept(this, param);
		return null;
	}
	
	// Método auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}
}
