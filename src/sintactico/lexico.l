// @author Ra�l Izquierdo

/* -- No es necesario modificar esta parte ----------------------------------------------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;

%%
%byaccj
%unicode
%line
%column
%public

%{
	public Yylex(Reader in, GestorErrores gestor) {
		this(in);
		this.gestor = gestor;
	}

	public int line() { return yyline + 1; }
	public int column() { return yycolumn + 1; }
	public String lexeme() { return yytext(); }

	// Traza para probar el l�xico de manera independiente al sint�ctico
	public static void main(String[] args) throws Exception {
		Yylex lex = new Yylex(new FileReader(Main.programa), new GestorErrores());
		int token;
		while ((token = lex.yylex()) != 0)
			System.out.println("\t[" + lex.line() + ":" + lex.column() + "] Token: " + token + ". Lexema: " + lex.lexeme());
	}

	private GestorErrores gestor;
%}

%%

/* -- Modificar aqu�. A�adir reglas en esta secci�n ----------------------------------------- */

[+\-*/.:,=;\[\](){}<>!]				{ return yytext().charAt(0); }

var									{ return Parser.VAR; }
struct								{ return Parser.STRUCT; }

print								{ return Parser.PRINT; }
println								{ return Parser.PRINTLN; }
printsp								{ return Parser.PRINTSP; }
return								{ return Parser.RETURN; }
read								{ return Parser.READ; }
cast								{ return Parser.CAST; }
null								{ return Parser.NULL; }

while								{ return Parser.WHILE; }
if									{ return Parser.IF; }
else								{ return Parser.ELSE; }

int									{ return Parser.INT; }
char								{ return Parser.CHAR; }
float								{ return Parser.REAL; }
real								{ return Parser.REAL; }

[a-zA-Z][a-zA-Z0-9_]*				{ return Parser.IDENT; }
[0-9]+ 								{ return Parser.LITERALINT; }
[0-9]+"."[0-9]+						{ return Parser.LITERALREAL; }
'(\\n | .)'							{ return Parser.LITERALCHAR; }

&&									{ return Parser.AND; }
"||"								{ return Parser.OR; }
>=									{ return Parser.MAYORIGUAL; }
"<="								{ return Parser.MENORIGUAL; }
"!="								{ return Parser.DISTINTO; }
"=="								{ return Parser.IGUALA; }

/* -- Se aconseja dejar estas reglas al final ----------------------------------------------- */

"/*"([^*]|\*+[^*/])*\*+"/"		{ }		/* Comentario de varias lineas como este */
"//".*							{ }		// Comentario de una linea como este

[ \n\r]		{ }
"\t"		{ yycolumn += 3; } // Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t == 4 caracteres. En Jflex: \t == 1 car�cter.

.			{ gestor.error("L�xico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column())); }
