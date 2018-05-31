// @author Raúl Izquierdo

/* No es necesario modificar esta sección ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aquí --------------------------------------- */
%left 'OR'
%left 'AND'
%left 'DISTINTO' 'IGUALA'
%left 'MENORIGUAL' 'MAYORIGUAL' '<' '>'
%left '+' '-'
%left '*' '/'
%left '!'
%left '(' ')'
%left '[' ']' '.'

%%

/* Añadir las reglas en esta sección ----------------------- */

programa: definiciones					{ raiz = new Programa($1); }
	;

//Patron 0+SS: Listas de 0 o más elementos
definiciones: 							{ $$ = new ArrayList(); }
	| definiciones definicion			{ $$ = $1; ((List)$1).add($2); }
	;

definicion: definicionVariableGlobal	{ $$ = $1; }
	| definicionEstructura				{ $$ = $1; }
	| definicionFuncion					{ $$ = $1; }
	;

definicionVariableGlobal: 'VAR' 'IDENT' ':' tipo ';'		{ $$ = new DefVariable($2, $4, "GLOBAL"); }
	;

//Composicion
tipo: 'INT'							{ $$ = new IntType(); }
	| 'REAL'						{ $$ = new RealType(); }
	| 'CHAR'						{ $$ = new CharType(); }
	| 'IDENT'						{ $$ = new Estructura($1); }
	| '[' 'LITERALINT' ']' tipo		{ $$ = new ArrayType($2, $4); }
	;

definicionEstructura: 'STRUCT' 'IDENT' '{' cuerpoEstructura '}' ';' 	{ $$ = new DefEstructura($2, $4); }
	;

//Patron 0+SS: Listas de 0 o más elementos
cuerpoEstructura: 								{ $$ = new ArrayList(); }
	| cuerpoEstructura variableEstructura		{ $$ = $1; ((List)$1).add($2); }
	;

variableEstructura: 'IDENT' ':' tipo ';'		{ $$ = new DefCampo($1, $3); }
	;
	
definicionFuncion: 'IDENT' '(' parametros ')' retorno '{' definicionesVariables sentencias '}' 		{ $$ = new DefFuncion($1, $3, $5, $7, $8); }
	;

retorno:			{ $$ = null; }
	| ':' tipo		{ $$ = $2; }
	;

//Patron 0+SS: Listas de 0 o más elementos
definicionesVariables: 									{ $$ = new ArrayList(); }
	| definicionesVariables definicionVariableLocal		{ $$ = $1; ((List)$1).add($2); }
	;

definicionVariableLocal: 'VAR' 'IDENT' ':' tipo ';'		{ $$ = new DefVariable($2, $4, "LOCAL"); }
	;

//Patron 0+CS: cadenas con separadores de 0 más elementos
parametros: 				{ $$ = new ArrayList(); }
	| parametrosAux			{ $$ = $1; }
	;

parametrosAux: definicionParametro						{ $$ = new ArrayList(); ((List)$$).add($1); }
	| parametrosAux ',' definicionParametro				{ $$ = $1; ((List)$$).add($3); }
	;

definicionParametro: 'IDENT' ':' tipo					{ $$ = new DefVariable($1, $3, "PARAM"); }
	;

//Patron 1+SS: Listas de 0 o más elementos
sentencias: 									{ $$ = new ArrayList(); }
	| sentencias sentencia						{ $$ = $1; ((List)$$).add($2); }
	;

sentencia: expresion '=' expresion ';'			{ $$ = new Asignacion($1, $3); }
	| 'RETURN' expresion ';'					{ $$ = new Return($2); }
	| 'RETURN' ';'								{ $$ = new Return(null).setPositions($1); }
	| 'RETURN' 'NULL' ';'						{ $$ = new Return(null).setPositions($1); }
	| 'PRINTSP' expresion ';'					{ $$ = new PrintSp($2); }
	| 'PRINTLN' expresion ';'					{ $$ = new PrintLn($2); }
	| 'PRINTLN' ';'								{ $$ = new PrintLn(null).setPositions($1); }
	| 'PRINT' expresion ';'						{ $$ = new Print($2); }
	| 'READ' expresion ';'						{ $$ = new Read($2); }
	| invocacionSentencia ';'					{ $$ = $1; }
	| bucleWhile								{ $$ = $1; }
	| branch									{ $$ = $1; }
//	| cast										{ $$ = $1; }
	;

invocacionSentencia: 'IDENT' '(' parametrosUtilizados ')' { $$ = new Invocacion($1, $3, true); }

bucleWhile: 'WHILE' '(' expresion ')' '{' sentencias '}'			{ $$ = new While($3, $6); }
	; 

branch: 'IF' '(' expresion ')' '{' sentencias '}'							{ $$ = new If($3, $6, new ArrayList()); }
	| 'IF' '(' expresion ')' '{' sentencias '}' 'ELSE' '{' sentencias '}'	{ $$ = new If($3, $6, $10); }
	;

expresion: 'IDENT' 								{ $$ = new Variable($1); }
	| 'LITERALINT'								{ $$ = new LiteralInt($1, true); }
	| 'LITERALREAL'								{ $$ = new LiteralReal($1, true); }
	| 'LITERALCHAR'								{ $$ = new LiteralChar($1); }
	| '-' 'LITERALINT'							{ $$ = new LiteralInt($2, false); }
	| '-' 'LITERALREAL'							{ $$ = new LiteralReal($2, false); }
	| '(' expresion ')'							{ $$ = $2; }
	| expresion '[' expresion ']'				{ $$ = new Array($1, $3); }
	| expresion '.' 'IDENT'						{ $$ = new Navegacion($1, $3); }
	| expresion '*' expresion					{ $$ = new ExprAritmetica($1, "*", $3); }
	| expresion '/' expresion					{ $$ = new ExprAritmetica($1, "/", $3); }
	| expresion '+' expresion					{ $$ = new ExprAritmetica($1, "+", $3); }
	| expresion '-' expresion					{ $$ = new ExprAritmetica($1, "-", $3); }
	| expresion 'AND' expresion					{ $$ = new ExprBinaria($1, "&&", $3); }
	| expresion 'OR' expresion					{ $$ = new ExprBinaria($1, "||", $3); }
	| expresion 'MENORIGUAL' expresion			{ $$ = new ExprComparativa($1, "<=", $3); }
	| expresion 'DISTINTO' expresion			{ $$ = new ExprComparativa($1, "!=", $3); }
	| expresion 'MAYORIGUAL' expresion			{ $$ = new ExprComparativa($1, ">=", $3); }
	| expresion 'IGUALA' expresion				{ $$ = new ExprComparativa($1, "==", $3); }
	| expresion '<' expresion					{ $$ = new ExprComparativa($1, "<", $3); }
	| expresion '>' expresion					{ $$ = new ExprComparativa($1, ">", $3); }
	| '!' expresion								{ $$ = new Negacion($2); }
	| invocacionExpresion						{ $$ = $1; }
	| cast										{ $$ = $1; }
	;

invocacionExpresion: 'IDENT' '(' parametrosUtilizados ')' { $$ = new Invocacion($1, $3, false); }
	;

cast: 'CAST' '<' tipo '>' '(' expresion ')'	{ $$ = new Cast($3, $6); }
	;


//Patron 0+CS: cadenas con separadores de 0 o mas elementos
parametrosUtilizados: 					{ $$ = new ArrayList(); }
	| parametrosUtilizadosAux			{ $$ = $1; }
	;

parametrosUtilizadosAux: expresion					{ $$ = new ArrayList(); ((List)$$).add($1); }
	| parametrosUtilizadosAux ',' expresion			{ $$ = $1; ((List)$$).add($3); }
	;

%%
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
