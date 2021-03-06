/**
 * @generated VGen 1.3.3
 */

package visitor;

import java.io.*;

import ast.*;
import ast.DefVariable.Ambito;

import java.util.*;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * 	-	Muestra la estructura del �rbol en HTML.
 * 	-	Destaca los hijos/propiedades a null.
 * 	-	Muestra a qu� texto apuntan las posiciones de cada nodo (linea/columna)
 * 		ayudando a decidir cual de ellas usar en los errores y generaci�n de c�digo.
 * 
 * Esta clase se genera con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto). 
 * 
 */
public class ASTPrinter extends DefaultVisitor {

	/**
	 * toHtml. Muestra la estructura del AST indicando qu� hay en las posiciones (linea y columna) de cada nodo.
	 * 
	 * @param sourceFile	El fichero del cual se ha obtenido el AST
	 * @param raiz				El AST creado a partir de sourceFile
	 * @param filename		Nombre del fichero HMTL a crear con la traza del AST
	 */

	public static void toHtml(String sourceFile, AST raiz, String filename) {
		toHtml(sourceFile, raiz, filename, 4);
	}
	
	public static void toHtml(AST raiz, String filename) {
		toHtml(null, raiz, filename);
	}

	// tabWidth deber�an ser los espacios correspondientes a un tabulador en eclipse.
	// Normalmente no ser� necesario especificarlo. Usar mejor los dos m�todos anteriores.
	public static void toHtml(String sourceFile, AST raiz, String filename, int tabWidth) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(filename.endsWith(".html") ? filename : filename + ".html"));
			generateHeader(writer);
			writer.println("[ASTPrinter] -------------------------------- line:col  line:col");
			if (raiz != null) {
				ASTPrinter tracer = new ASTPrinter(writer, loadLines(sourceFile, tabWidth));
				raiz.accept(tracer, new Integer(0));
			} else
				writer.println("raiz == null");
			writer.println(ls + ls + "[ASTPrinter] --------------------------------");
			generateFooter(writer);
			writer.close();
			System.out.println(ls + "ASTPrinter: Fichero '" + filename + ".html' generado con �xito. Abra el fichero para validar el �rbol AST generado.");
		} catch (IOException e) {
			System.out.println(ls + "ASTPrinter: No se ha podido crear el fichero " + filename);
			e.printStackTrace();
		}
	}

	private static void generateHeader(PrintWriter writer) {
		writer.println("<html>\r\n" +
				"<head>\r\n" +
				"<style type=\"text/css\">\r\n" +
				".value { font-weight: bold; }\r\n" +
				".dots { color: #888888; }\r\n" +
				".type { color: #BBBBBB; }\r\n" +
				".pos { color: #CCCCCC; }\r\n" +
				".sourceText { color: #BBBBBB; }\r\n" +
				".posText {\r\n" +
				"	color: #BBBBBB;\r\n" +
				"	text-decoration: underline; font-weight: bold;\r\n" +
				"}\r\n" +
				".null {\r\n" +
				"	color: #FF0000;\r\n" +
				"	font-weight: bold;\r\n" +
				"	font-style: italic;\r\n" +
				"}\r\n" +
			//	 "pre { font-family: Arial, Helvetica, sans-serif; font-size: 11px; }\r\n" +
			//	"pre { font-size: 11px; }\r\n" +
				"</style>\r\n" +
				"</head>\r\n" +
				"\r\n" +
				"<body><pre>");
	}

	private static void generateFooter(PrintWriter writer) {
		writer.println("</pre>\r\n" +
				"</body>\r\n" +
				"</html>");
	}

	private ASTPrinter(PrintWriter writer, List<String> sourceLines) {
		this.writer = writer;
		this.sourceLines = sourceLines;
	}

	// ----------------------------------------------

	//	class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Programa", node, false);

		visit(indent + 1, "definicion", "List<Definicion>",node.getDefinicion());
		return null;
	}

	//	class DefVariableGlobal { String nombre;  Tipo tipo; }
	public Object visit(DefVariable node, Object param) {
		int indent = ((Integer)param).intValue();

		if (node.getAmbito() == Ambito.GLOBAL)
			printName(indent, "DefVariableGlobal", node, false);
		else if (node.getAmbito() == Ambito.LOCAL)
			printName(indent, "DefVariableLocal", node, false);
		else
			printName(indent, "DefParametro", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		print(indent + 1, "direccion", "int", node.getDireccion());
		return null;
	}

	//	class DefEstructura { String nombre;  List<Campo> campo; }
	public Object visit(DefEstructura node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefEstructura", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "campo", "List<Campo>",node.getCampos());
		return null;
	}

//	class DefFuncion { String nombre;  List<DefVariable> defParametros;  Tipo tipo;  List<DefVariable> defVariables;  List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefFuncion", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "defParametros", "List<DefVariable>",node.getDefparametro());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		visit(indent + 1, "defVariables", "List<DefVariable>",node.getDefvariable());
		visit(indent + 1, "sentencia", "List<Sentencia>",node.getSentencia());
		return null;
	}

	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefCampo", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		print(indent + 1, "direccion", "int", node.getDireccion());
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "IntType", node, true);

		return null;
	}

	//	class RealType {  }
	public Object visit(RealType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "RealType", node, true);

		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CharType", node, true);

		return null;
	}

	//	class Estructura { String nombre; }
	public Object visit(Estructura node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Estructura", node, "nombre", node.getNombre());
		return null;
	}

	//	class ArrayType { int size;  Tipo tipo; }
	public Object visit(ArrayType node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ArrayType", node, false);

		print(indent + 1, "size", "int", node.getSize());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Asignacion", node, false);

		visit(indent + 1, "left", "Expresion",node.getLeft());
		visit(indent + 1, "right", "Expresion",node.getRight());
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Return", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Print", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class PrintLn { Expresion expresion; }
	public Object visit(PrintLn node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "PrintLn", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class PrintSp { Expresion expresion; }
	public Object visit(PrintSp node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "PrintSp", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Read", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> cierto; }
	public Object visit(While node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "While", node, false);

		visit(indent + 1, "condicion", "Expresion",node.getCondicion());
		visit(indent + 1, "cierto", "List<Sentencia>",node.getCierto());
		return null;
	}

	//	class IfElse { Expresion condicion;  List<Sentencia> cierto;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "IfElse", node, false);

		visit(indent + 1, "condicion", "Expresion",node.getCondicion());
		visit(indent + 1, "cierto", "List<Sentencia>",node.getCierto());
		visit(indent + 1, "falso", "List<Sentencia>",node.getFalso());
		return null;
	}

//	class InvocaSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(Invocacion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Invocacion", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		print(indent + 1, "isSentencia", "Boolean", node.isSentencia());
		visit(indent + 1, "argumentos", "List<Expresion>",node.getArgumentos());
		return null;
	}

	//	class Navegacion { Expresion argumento;  String right; }
	public Object visit(Navegacion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Navegacion", node, false);

		visit(indent + 1, "argumento", "Expresion",node.getArgumento());
		print(indent + 1, "right", "String", node.getRight());
		return null;
	}

	//	class ExprBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprBinaria node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ExprBinaria", node, false);

		visit(indent + 1, "left", "Expresion",node.getLeft());
		print(indent + 1, "operador", "String", node.getOperador());
		visit(indent + 1, "right", "Expresion",node.getRight());
		return null;
	}
	
//	class ExprAritmetica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprAritmetica node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ExprAritmetica", node, false);

		visit(indent + 1, "left", "Expresion",node.getLeft());
		print(indent + 1, "operador", "String", node.getOperador());
		visit(indent + 1, "right", "Expresion",node.getRight());
		return null;
	}
	
//	class ExprComparativa { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExprComparativa node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "ExprComparativa", node, false);

		visit(indent + 1, "left", "Expresion",node.getLeft());
		print(indent + 1, "operador", "String", node.getOperador());
		visit(indent + 1, "right", "Expresion",node.getRight());
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Variable", node, "nombre", node.getNombre());
		return null;
	}

	//	class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LiteralInt", node, false);

		print(indent + 1, "valor", "int", node.getValor());
		return null;
	}

	//	class LiteralChar { String valor; }
	public Object visit(LiteralChar node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "LiteralChar", node, "valor", node.getValor());
		return null;
	}

	//	class LiteralReal { double valor; }
	public Object visit(LiteralReal node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "LiteralReal", node, false);

		print(indent + 1, "valor", "double", node.getValor());
		return null;
	}

	//	class Cast { Tipo tipo;  List<Expresion> argumentos; }
	public Object visit(Cast node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Cast", node, false);

		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		visit(indent + 1, "argumentos", "Expresion",node.getArgumento());
		return null;
	}

	//	class Negacion { Expresion argumentos; }
	public Object visit(Negacion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Negacion", node, false);

		visit(indent + 1, "argumentos", "Expresion",node.getArgumentos());
		return null;
	}

	//	class Array { Expresion nombre;  Expresion indice; }
	public Object visit(Array node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Array", node, false);

		visit(indent + 1, "nombre", "Expresion",node.getNombre());
		visit(indent + 1, "indice", "Expresion",node.getIndice());
		return null;
	}

	// -----------------------------------------------------------------
	// M�todos invocados desde los m�todos visit -----------------------

	private void printName(int indent, String name, AST node, boolean empty) {
		String text = ls + tabula(indent) + name + " &rarr;  ";
		text = String.format("%1$-" + 93 + "s", text);
		if (empty)
			text = text.replace(name, valueTag(name));
		writer.print(text + getPosition(node));
	}

	private void print(int indent, String name, String type, Object value) {
		write(indent, formatValue(value) + "  " + typeTag(type));
	}

	private void print(int indent, String attName, String type, List<? extends Object> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (Object child : children)
				write(indent + 1, formatValue(child));
		else
			writer.print(" " + valueTag(null));
	}

	// Versi�n compacta de una linea para nodos que solo tienen un atributo String
	private void printCompact(int indent, String nodeName, AST node, String attName, Object value) {
		String fullName = nodeName + '.' + attName;
		String text = ls + tabula(indent) + '\"' + value + "\"  " + fullName;
		text = String.format("%1$-" + 88 + "s", text);
		// text = text.replace(value.toString(), valueTag(value));
		text = text.replace(fullName, typeTag(fullName));
		writer.print(text + getPosition(node));
	}

	private void visit(int indent, String attName, String type, List<? extends AST> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (AST child : children)
				child.accept(this, indent + 1);
		else
			writer.print(" " + valueTag(null));
	}

	private void visit(int indent, String attName, String type, AST child) {
		if (child != null)
			child.accept(this, new Integer(indent));
		else
			write(indent, valueTag(null) + "  " + attName + ':' + typeTag(type));
	}

	// -----------------------------------------------------------------
	// M�todos auxiliares privados -------------------------------------

	private void write(int indent, String text) {
		writer.print(ls + tabula(indent) + text);
	}

	private static String tabula(int count) {
		StringBuffer cadena = new StringBuffer("<span class=\"dots\">");
		for (int i = 0; i < count; i++)
			cadena.append(i % 2 == 0 && i > 0 ? "|  " : "�  ");
		return cadena.toString() + "</span>";
	}

	private String typeTag(String type) {
		if (type.equals("String"))
			return "";
		return "<span class=\"type\">" + type.replace("<", "&lt;").replace(">", "&gt;") + "</span>";
	}

	private String valueTag(Object value) {
		if (value == null)
			return "<span class=\"null\">null</span>";
		return "<span class=\"value\">" + value + "</span>";
	}

	private String formatValue(Object value) {
		String text = valueTag(value);
		if (value instanceof String)
			text = "\"" + text + '"';
		return text;
	}


	// -----------------------------------------------------------------
	// M�todos para mostrar las Posiciones -----------------------------

	private String getPosition(Traceable node) {
		String text = node.getStart() + "  " + node.getEnd();
		text = "<span class=\"pos\">" + String.format("%1$-" + 13 + "s", text) + "</span>";
		text = text.replace("null", "<span class=\"null\">null</span>");
		String sourceText = findSourceText(node);
		if (sourceText != null)
			text += sourceText;
		return text;
	}

	private String findSourceText(Traceable node) {
		if (sourceLines == null)
			return null;

		Position start = node.getStart();
		Position end = node.getEnd();
		if (start == null || end == null)
			return null;

		String afterText, text, beforeText;
		if (start.getLine() == end.getLine()) {
			String line = sourceLines.get(start.getLine() - 1);
			afterText = line.substring(0, start.getColumn() - 1);
			text = line.substring(start.getColumn() - 1, end.getColumn());
			beforeText = line.substring(end.getColumn());
		} else {
			String firstLine = sourceLines.get(start.getLine() - 1);
			String lastLine = sourceLines.get(end.getLine() - 1);

			afterText = firstLine.substring(0, start.getColumn() - 1);

			text = firstLine.substring(start.getColumn() - 1);
			text += "</span><span class=\"sourceText\">" + " ... " + "</span><span class=\"posText\">";
			text += lastLine.substring(0, end.getColumn()).replaceAll("^\\s+", "");

			beforeText = lastLine.substring(end.getColumn());
		}
		return "<span class=\"sourceText\">" + afterText.replaceAll("^\\s+", "")
				+ "</span><span class=\"posText\">" + text
				+ "</span><span class=\"sourceText\">" + beforeText + "</span>";
	}

	private static List<String> loadLines(String sourceFile, int tabWidth) {
		if (sourceFile == null)
			return null;
		try {
			String spaces = new String(new char[tabWidth]).replace("\0", " ");
			
			List<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null)
				lines.add(line.replace("\t", spaces));
			br.close();
			return lines;
		} catch (FileNotFoundException e) {
			System.out.println("Warning. No se pudo encontrar el fichero fuente '" + sourceFile + "'. No se mostrar� informaic�n de posici�n.");
			return null;
		} catch (IOException e) {
			System.out.println("Warning. Error al leer del fichero fuente '" + sourceFile + "'. No se mostrar� informaic�n de posici�n.");
			return null;
		}
	}


	private List<String> sourceLines;
	private static String ls = System.getProperty("line.separator");
	private PrintWriter writer;
}

