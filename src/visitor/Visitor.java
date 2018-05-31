/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(DefVariable node, Object param);
	public Object visit(DefEstructura node, Object param);
	public Object visit(DefFuncion node, Object param);
	public Object visit(DefCampo node, Object param);
	public Object visit(IntType node, Object param);
	public Object visit(RealType node, Object param);
	public Object visit(CharType node, Object param);
	public Object visit(Estructura node, Object param);
	public Object visit(ArrayType node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(PrintLn node, Object param);
	public Object visit(PrintSp node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(While node, Object param);
	public Object visit(If node, Object param);
	public Object visit(Invocacion node, Object param);
	public Object visit(Navegacion node, Object param);
	public Object visit(ExprBinaria node, Object param);
	public Object visit(ExprAritmetica node, Object param);
	public Object visit(ExprComparativa node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(LiteralInt node, Object param);
	public Object visit(LiteralChar node, Object param);
	public Object visit(LiteralReal node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(Negacion node, Object param);
	public Object visit(Array node, Object param);
}
