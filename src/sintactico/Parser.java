//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short OR=257;
public final static short AND=258;
public final static short DISTINTO=259;
public final static short IGUALA=260;
public final static short MENORIGUAL=261;
public final static short MAYORIGUAL=262;
public final static short VAR=263;
public final static short IDENT=264;
public final static short INT=265;
public final static short REAL=266;
public final static short CHAR=267;
public final static short LITERALINT=268;
public final static short STRUCT=269;
public final static short RETURN=270;
public final static short NULL=271;
public final static short PRINTSP=272;
public final static short PRINTLN=273;
public final static short PRINT=274;
public final static short READ=275;
public final static short WHILE=276;
public final static short IF=277;
public final static short ELSE=278;
public final static short LITERALREAL=279;
public final static short LITERALCHAR=280;
public final static short CAST=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    6,    6,    6,
    6,    6,    4,    7,    7,    8,    5,   10,   10,   11,
   11,   13,    9,    9,   14,   14,   15,   12,   12,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   18,   19,   20,   20,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   22,
   23,   21,   21,   24,   24,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    5,    1,    1,    1,
    1,    4,    6,    0,    2,    4,    9,    0,    2,    0,
    2,    5,    0,    1,    1,    3,    3,    0,    2,    4,
    3,    2,    3,    3,    3,    2,    3,    3,    2,    1,
    1,    4,    7,    7,   11,    1,    1,    1,    1,    2,
    2,    3,    4,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    1,    1,    4,
    7,    0,    1,    1,    3,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    3,    4,    5,    6,    0,
    0,    0,    0,    0,    0,    0,   25,   14,    0,   11,
    8,    9,   10,    0,    0,    0,    0,    0,    0,    7,
   27,    0,    0,   26,    0,    0,   15,    0,   19,   20,
    0,   13,   12,    0,    0,    0,    0,   21,   16,    0,
    0,    0,    0,    0,   47,   17,    0,    0,    0,    0,
    0,    0,    0,   48,   49,    0,   29,    0,    0,   40,
   41,   68,   69,    0,   50,   51,    0,    0,    0,    0,
   32,    0,    0,    0,   36,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,    0,    0,   52,
    0,    0,    0,   33,   31,   34,   35,   37,   38,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   54,    0,   22,    0,    0,
    0,    0,    0,    0,   53,   30,   70,    0,   28,   28,
    0,    0,    0,    0,   43,    0,   71,    0,   28,    0,
   45,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,    9,   24,   28,   37,   15,   33,
   44,   47,   48,   16,   17,   67,   68,   69,   70,   71,
  112,   72,   73,  113,
};
final static short yysindex[] = {                         0,
    0, -226, -261,  -35, -254,    0,    0,    0,    0,  -47,
 -237,  -94,  -71,  -17,   -9,    1,    0,    0, -224,    0,
    0,    0,    0,  -13,  -71,  -10, -237, -124,  -43,    0,
    0,  -71,  -72,    0,   -6,   -4,    0,  -71,    0,    0,
  -71,    0,    0, -206,   -1, -205,  -33,    0,    0,    2,
 -262,   60,   60,   21,    0,    0,   51,   60,   57,   60,
   60,   22,   24,    0,    0,    6,    0,  461,    8,    0,
    0,    0,    0,  -71,    0,    0,   29,  -44,  159,   60,
    0,   12,  488,  494,    0,  524,  545,  682,   60,   60,
  -71,   60,   60,   60,   60,   60,   60,   60,   60,   60,
   60,   60,   60,   60, -191,   60,    0,   15,   60,    0,
  772,   40,   39,    0,    0,    0,    0,    0,    0,  316,
  352,   25,  779,  802,  809,  809,   33,   33,   33,   33,
  -38,  -38,  -44,  -44,  717,    0,  729,    0,   44,    0,
   60,  -37,  -29,   48,    0,    0,    0,  772,    0,    0,
   60,  -19,   -5,  363,    0, -183,    0,  -25,    0,    9,
    0,
};
final static short yyrindex[] = {                         0,
    0,   99,    0,    0,    0,    0,    0,    0,    0,    0,
   62,    0,    0,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -94,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   23,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  751,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   85,   94,    0,   66,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   66,    0,
  -26,    0,   67,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  501,  -28,  799,  824,  397,  420,  426,  432,
  385,  391,  122,  130,    0,    0,    0,    0,    0,  455,
    0,    0,    0,    0,    0,    0,    0,  -22,    0,    0,
    0,    0,    0,    0,    0,   37,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,   -2,    0,    0,    0,    0,
    0, -125,    0,    0,   74,    0,  829,    0,    0,    0,
    3,    0,    0,    0,
};
final static int YYTABLESIZE=1084;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   36,  105,   10,  102,   11,   75,   53,  105,  103,   12,
   13,   51,   59,   52,   74,   59,   76,   74,   75,   19,
   53,   75,   31,  152,  153,   51,   14,   52,   18,   39,
   59,   26,   59,  160,   53,   43,    3,    4,   45,   51,
   25,   52,    5,   29,   27,   30,  104,   32,   53,   38,
   40,   41,  104,   51,   42,   28,   46,   49,   50,   74,
   80,   89,   28,   90,   59,   91,  107,   28,  109,   44,
  114,  108,  136,  138,  102,  100,   44,  101,  105,  103,
  140,   44,  141,   52,  147,  149,  144,  151,  122,   52,
   53,   56,   52,  150,  158,   51,   53,  159,    1,   53,
   34,   51,   23,   24,   51,  155,   72,   73,    0,   81,
    0,  139,    0,    0,    0,   85,    0,    0,    0,  156,
    0,    0,    0,  104,    0,   46,   46,   46,   46,   46,
   46,   46,    0,  161,   67,   67,   67,   67,   67,   35,
   67,    0,    0,   46,   46,   46,   46,   28,    0,    0,
    0,    0,   67,   67,   67,   67,    0,    0,    0,    0,
    0,   44,   55,   55,   55,   55,   55,    0,   55,    0,
   56,   56,   56,   56,   56,   46,   56,   46,    0,    0,
   55,   55,   55,   55,    0,    0,   67,    0,   56,   56,
   56,   56,   20,   21,   22,   23,    0,    0,    0,  110,
  102,  100,    0,  101,  105,  103,    0,    0,    0,    0,
    0,    0,    0,    0,   55,    0,    0,    0,   98,    0,
   99,    0,   56,    0,    0,    0,    0,    0,   59,   59,
   54,    0,    0,    0,   55,    0,   57,    0,   58,   59,
   60,   61,   62,   63,   54,   64,   65,   66,   55,  104,
   57,    0,   58,   59,   60,   61,   62,   63,   54,   64,
   65,   66,   55,    0,   57,    0,   58,   59,   60,   61,
   62,   63,   54,   64,   65,   66,   55,    0,   57,    0,
   58,   59,   60,   61,   62,   63,   28,   64,   65,   66,
   28,    0,   28,    0,   28,   28,   28,   28,   28,   28,
   44,   28,   28,   28,   44,    0,   44,    0,   44,   44,
   44,   44,   44,   44,   77,   44,   44,   44,   55,    0,
   77,   82,    0,   77,   55,    0,    0,   55,    0,   64,
   65,   66,    0,    0,    0,   64,   65,   66,   64,   65,
   66,   46,   46,   46,   46,   46,   46,    0,    0,    0,
   67,   67,   67,   67,   67,   67,  142,  102,  100,    0,
  101,  105,  103,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   98,    0,   99,   55,   55,
   55,   55,   55,   55,    0,    0,   56,   56,   56,   56,
   56,   56,  143,  102,  100,    0,  101,  105,  103,    0,
    0,    0,    0,  157,  102,  100,  104,  101,  105,  103,
    0,   98,    0,   99,    0,   92,   93,   94,   95,   96,
   97,    0,   98,    0,   99,   57,    0,   57,   57,   57,
    0,   58,    0,   58,   58,   58,    0,   61,    0,    0,
   61,    0,  104,   57,   57,   57,   57,    0,    0,   58,
   58,   58,   58,  104,    0,   61,   61,   61,   61,    0,
   63,    0,    0,   63,    0,    0,   65,    0,    0,   65,
    0,    0,   66,    0,    0,   66,    0,   57,   63,   63,
   63,   63,    0,   58,   65,   65,   65,   65,    0,   61,
   66,   66,   66,   66,    0,    0,   70,   70,    0,   70,
   70,   70,  102,  100,    0,  101,  105,  103,    0,    0,
    0,    0,   63,   42,   70,   70,   70,    0,   65,    0,
   98,  106,   99,    0,   66,    0,    0,    0,    0,  102,
  100,    0,  101,  105,  103,  102,  100,    0,  101,  105,
  103,   60,    0,    0,   60,   70,  115,   98,    0,   99,
    0,  104,  116,   98,    0,   99,    0,    0,    0,   60,
    0,   60,    0,    0,    0,  102,  100,    0,  101,  105,
  103,    0,   92,   93,   94,   95,   96,   97,  104,    0,
    0,    0,  117,   98,  104,   99,  102,  100,    0,  101,
  105,  103,    0,   60,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  118,   98,    0,   99,    0,   92,   93,
   94,   95,   96,   97,  104,    0,    0,    0,    0,   92,
   93,   94,   95,   96,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  104,    0,    0,    0,    0,
    0,   57,   57,   57,   57,   57,   57,   58,   58,   58,
   58,   58,   58,   61,   61,   61,   61,   61,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   63,   63,   63,   63,
   63,   63,   65,   65,   65,   65,   65,   65,   66,   66,
   66,   66,   66,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,   70,   70,   70,   70,   70,   92,   93,   94,
   95,   96,   97,  102,  100,    0,  101,  105,  103,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  119,   98,    0,   99,   92,   93,   94,   95,   96,   97,
   92,   93,   94,   95,   96,   97,    0,   60,  102,  100,
    0,  101,  105,  103,    0,    0,    0,    0,    0,    0,
  102,  100,  104,  101,  105,  103,   98,    0,   99,    0,
   92,   93,   94,   95,   96,   97,    0,  146,   98,    0,
   99,    0,   46,   46,    0,   46,   46,   46,    0,    0,
    0,   92,   93,   94,   95,   96,   97,  104,    0,  145,
   46,   46,   46,  102,  100,    0,  101,  105,  103,  104,
  102,  100,    0,  101,  105,  103,    0,    0,    0,    0,
    0,   98,    0,   99,    0,    0,    0,    0,   98,   62,
   99,   46,   62,  102,  100,    0,  101,  105,  103,    0,
  102,  100,    0,  101,  105,  103,    0,   62,    0,   62,
    0,   98,  104,   99,   64,    0,    0,   64,   98,  104,
   99,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   78,   79,   64,    0,   64,   83,   84,   86,   87,   88,
    0,   62,  104,    0,    0,    0,    0,    0,    0,  104,
    0,    0,    0,    0,    0,    0,    0,    0,  111,    0,
    0,    0,    0,    0,    0,    0,   64,  120,  121,    0,
  123,  124,  125,  126,  127,  128,  129,  130,  131,  132,
  133,  134,  135,    0,  137,    0,    0,  111,   92,   93,
   94,   95,   96,   97,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  148,
    0,    0,    0,   92,   93,   94,   95,   96,   97,  154,
    0,    0,    0,    0,    0,   92,   93,   94,   95,   96,
   97,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,   46,   46,
   46,   46,   46,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   92,   93,
   94,   95,   96,   97,    0,    0,   93,   94,   95,   96,
   97,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   62,   62,   62,   62,    0,
   94,   95,   96,   97,    0,    0,    0,    0,    0,   96,
   97,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   64,   64,   64,   64,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
  125,   46,  264,   42,   40,  268,   40,   46,   47,  264,
   58,   45,   41,   33,   41,   44,  279,   44,   41,   91,
   40,   44,   25,  149,  150,   45,  264,   33,  123,   32,
   59,   41,   61,  159,   40,   38,  263,  264,   41,   45,
   58,   33,  269,  268,   44,   59,   91,   58,   40,   93,
  123,   58,   91,   45,   59,   33,  263,   59,  264,   58,
   40,   40,   40,   40,   93,   60,   59,   45,   40,   33,
   59,   74,  264,   59,   42,   43,   40,   45,   46,   47,
   41,   45,   44,   33,   41,  123,   62,   40,   91,   33,
   40,  125,   33,  123,  278,   45,   40,  123,    0,   40,
   27,   45,   41,   41,   45,  125,   41,   41,   -1,   59,
   -1,  109,   -1,   -1,   -1,   59,   -1,   -1,   -1,  125,
   -1,   -1,   -1,   91,   -1,   41,   42,   43,   44,   45,
   46,   47,   -1,  125,   41,   42,   43,   44,   45,  264,
   47,   -1,   -1,   59,   60,   61,   62,  125,   -1,   -1,
   -1,   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,  125,   41,   42,   43,   44,   45,   -1,   47,   -1,
   41,   42,   43,   44,   45,   91,   47,   93,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,   93,   -1,   59,   60,
   61,   62,  264,  265,  266,  267,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   60,   -1,
   62,   -1,   93,   -1,   -1,   -1,   -1,   -1,  257,  258,
  264,   -1,   -1,   -1,  268,   -1,  270,   -1,  272,  273,
  274,  275,  276,  277,  264,  279,  280,  281,  268,   91,
  270,   -1,  272,  273,  274,  275,  276,  277,  264,  279,
  280,  281,  268,   -1,  270,   -1,  272,  273,  274,  275,
  276,  277,  264,  279,  280,  281,  268,   -1,  270,   -1,
  272,  273,  274,  275,  276,  277,  264,  279,  280,  281,
  268,   -1,  270,   -1,  272,  273,  274,  275,  276,  277,
  264,  279,  280,  281,  268,   -1,  270,   -1,  272,  273,
  274,  275,  276,  277,  264,  279,  280,  281,  268,   -1,
  264,  271,   -1,  264,  268,   -1,   -1,  268,   -1,  279,
  280,  281,   -1,   -1,   -1,  279,  280,  281,  279,  280,
  281,  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,  257,  258,
  259,  260,  261,  262,   -1,   -1,  257,  258,  259,  260,
  261,  262,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   41,   42,   43,   91,   45,   46,   47,
   -1,   60,   -1,   62,   -1,  257,  258,  259,  260,  261,
  262,   -1,   60,   -1,   62,   41,   -1,   43,   44,   45,
   -1,   41,   -1,   43,   44,   45,   -1,   41,   -1,   -1,
   44,   -1,   91,   59,   60,   61,   62,   -1,   -1,   59,
   60,   61,   62,   91,   -1,   59,   60,   61,   62,   -1,
   41,   -1,   -1,   44,   -1,   -1,   41,   -1,   -1,   44,
   -1,   -1,   41,   -1,   -1,   44,   -1,   93,   59,   60,
   61,   62,   -1,   93,   59,   60,   61,   62,   -1,   93,
   59,   60,   61,   62,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   93,   59,   60,   61,   62,   -1,   93,   -1,
   60,   61,   62,   -1,   93,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   42,   43,   -1,   45,   46,
   47,   41,   -1,   -1,   44,   91,   59,   60,   -1,   62,
   -1,   91,   59,   60,   -1,   62,   -1,   -1,   -1,   59,
   -1,   61,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,  257,  258,  259,  260,  261,  262,   91,   -1,
   -1,   -1,   59,   60,   91,   62,   42,   43,   -1,   45,
   46,   47,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,  257,  258,
  259,  260,  261,  262,   91,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  257,  258,  259,
  260,  261,  262,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,  257,  258,  259,  260,  261,  262,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  257,  258,  259,
  260,  261,  262,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,   -1,  257,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   42,   43,   91,   45,   46,   47,   60,   -1,   62,   -1,
  257,  258,  259,  260,  261,  262,   -1,   59,   60,   -1,
   62,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   91,   -1,   93,
   60,   61,   62,   42,   43,   -1,   45,   46,   47,   91,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   60,   41,
   62,   91,   44,   42,   43,   -1,   45,   46,   47,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   59,   -1,   61,
   -1,   60,   91,   62,   41,   -1,   -1,   44,   60,   91,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   52,   53,   59,   -1,   61,   57,   58,   59,   60,   61,
   -1,   93,   91,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   80,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   89,   90,   -1,
   92,   93,   94,   95,   96,   97,   98,   99,  100,  101,
  102,  103,  104,   -1,  106,   -1,   -1,  109,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  141,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  151,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,   -1,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"OR\"","\"AND\"","\"DISTINTO\"",
"\"IGUALA\"","\"MENORIGUAL\"","\"MAYORIGUAL\"","\"VAR\"","\"IDENT\"","\"INT\"",
"\"REAL\"","\"CHAR\"","\"LITERALINT\"","\"STRUCT\"","\"RETURN\"","\"NULL\"",
"\"PRINTSP\"","\"PRINTLN\"","\"PRINT\"","\"READ\"","\"WHILE\"","\"IF\"",
"\"ELSE\"","\"LITERALREAL\"","\"LITERALCHAR\"","\"CAST\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : definiciones",
"definiciones :",
"definiciones : definiciones definicion",
"definicion : definicionVariableGlobal",
"definicion : definicionEstructura",
"definicion : definicionFuncion",
"definicionVariableGlobal : \"VAR\" \"IDENT\" ':' tipo ';'",
"tipo : \"INT\"",
"tipo : \"REAL\"",
"tipo : \"CHAR\"",
"tipo : \"IDENT\"",
"tipo : '[' \"LITERALINT\" ']' tipo",
"definicionEstructura : \"STRUCT\" \"IDENT\" '{' cuerpoEstructura '}' ';'",
"cuerpoEstructura :",
"cuerpoEstructura : cuerpoEstructura variableEstructura",
"variableEstructura : \"IDENT\" ':' tipo ';'",
"definicionFuncion : \"IDENT\" '(' parametros ')' retorno '{' definicionesVariables sentencias '}'",
"retorno :",
"retorno : ':' tipo",
"definicionesVariables :",
"definicionesVariables : definicionesVariables definicionVariableLocal",
"definicionVariableLocal : \"VAR\" \"IDENT\" ':' tipo ';'",
"parametros :",
"parametros : parametrosAux",
"parametrosAux : definicionParametro",
"parametrosAux : parametrosAux ',' definicionParametro",
"definicionParametro : \"IDENT\" ':' tipo",
"sentencias :",
"sentencias : sentencias sentencia",
"sentencia : expresion '=' expresion ';'",
"sentencia : \"RETURN\" expresion ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : \"RETURN\" \"NULL\" ';'",
"sentencia : \"PRINTSP\" expresion ';'",
"sentencia : \"PRINTLN\" expresion ';'",
"sentencia : \"PRINTLN\" ';'",
"sentencia : \"PRINT\" expresion ';'",
"sentencia : \"READ\" expresion ';'",
"sentencia : invocacionSentencia ';'",
"sentencia : bucleWhile",
"sentencia : branch",
"invocacionSentencia : \"IDENT\" '(' parametrosUtilizados ')'",
"bucleWhile : \"WHILE\" '(' expresion ')' '{' sentencias '}'",
"branch : \"IF\" '(' expresion ')' '{' sentencias '}'",
"branch : \"IF\" '(' expresion ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"expresion : \"IDENT\"",
"expresion : \"LITERALINT\"",
"expresion : \"LITERALREAL\"",
"expresion : \"LITERALCHAR\"",
"expresion : '-' \"LITERALINT\"",
"expresion : '-' \"LITERALREAL\"",
"expresion : '(' expresion ')'",
"expresion : expresion '[' expresion ']'",
"expresion : expresion '.' \"IDENT\"",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion \"AND\" expresion",
"expresion : expresion \"OR\" expresion",
"expresion : expresion \"MENORIGUAL\" expresion",
"expresion : expresion \"DISTINTO\" expresion",
"expresion : expresion \"MAYORIGUAL\" expresion",
"expresion : expresion \"IGUALA\" expresion",
"expresion : expresion '<' expresion",
"expresion : expresion '>' expresion",
"expresion : '!' expresion",
"expresion : invocacionExpresion",
"expresion : cast",
"invocacionExpresion : \"IDENT\" '(' parametrosUtilizados ')'",
"cast : \"CAST\" '<' tipo '>' '(' expresion ')'",
"parametrosUtilizados :",
"parametrosUtilizados : parametrosUtilizadosAux",
"parametrosUtilizadosAux : expresion",
"parametrosUtilizadosAux : parametrosUtilizadosAux ',' expresion",
};

//#line 162 "sintac.y"
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
//#line 573 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 28 "sintac.y"
{ raiz = new Programa(val_peek(0)); }
break;
case 2:
//#line 32 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 3:
//#line 33 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 4:
//#line 36 "sintac.y"
{ yyval = val_peek(0); }
break;
case 5:
//#line 37 "sintac.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 38 "sintac.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 41 "sintac.y"
{ yyval = new DefVariable(val_peek(3), val_peek(1), "GLOBAL"); }
break;
case 8:
//#line 45 "sintac.y"
{ yyval = new IntType(); }
break;
case 9:
//#line 46 "sintac.y"
{ yyval = new RealType(); }
break;
case 10:
//#line 47 "sintac.y"
{ yyval = new CharType(); }
break;
case 11:
//#line 48 "sintac.y"
{ yyval = new Estructura(val_peek(0)); }
break;
case 12:
//#line 49 "sintac.y"
{ yyval = new ArrayType(val_peek(2), val_peek(0)); }
break;
case 13:
//#line 52 "sintac.y"
{ yyval = new DefEstructura(val_peek(4), val_peek(2)); }
break;
case 14:
//#line 56 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 15:
//#line 57 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 16:
//#line 60 "sintac.y"
{ yyval = new DefCampo(val_peek(3), val_peek(1)); }
break;
case 17:
//#line 63 "sintac.y"
{ yyval = new DefFuncion(val_peek(8), val_peek(6), val_peek(4), val_peek(2), val_peek(1)); }
break;
case 18:
//#line 66 "sintac.y"
{ yyval = null; }
break;
case 19:
//#line 67 "sintac.y"
{ yyval = val_peek(0); }
break;
case 20:
//#line 71 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 21:
//#line 72 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 22:
//#line 75 "sintac.y"
{ yyval = new DefVariable(val_peek(3), val_peek(1), "LOCAL"); }
break;
case 23:
//#line 79 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 24:
//#line 80 "sintac.y"
{ yyval = val_peek(0); }
break;
case 25:
//#line 83 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); }
break;
case 26:
//#line 84 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
case 27:
//#line 87 "sintac.y"
{ yyval = new DefVariable(val_peek(2), val_peek(0), "PARAM"); }
break;
case 28:
//#line 91 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 29:
//#line 92 "sintac.y"
{ yyval = val_peek(1); ((List)yyval).add(val_peek(0)); }
break;
case 30:
//#line 95 "sintac.y"
{ yyval = new Asignacion(val_peek(3), val_peek(1)); }
break;
case 31:
//#line 96 "sintac.y"
{ yyval = new Return(val_peek(1)); }
break;
case 32:
//#line 97 "sintac.y"
{ yyval = new Return(null).setPositions(val_peek(1)); }
break;
case 33:
//#line 98 "sintac.y"
{ yyval = new Return(null).setPositions(val_peek(2)); }
break;
case 34:
//#line 99 "sintac.y"
{ yyval = new PrintSp(val_peek(1)); }
break;
case 35:
//#line 100 "sintac.y"
{ yyval = new PrintLn(val_peek(1)); }
break;
case 36:
//#line 101 "sintac.y"
{ yyval = new PrintLn(null).setPositions(val_peek(1)); }
break;
case 37:
//#line 102 "sintac.y"
{ yyval = new Print(val_peek(1)); }
break;
case 38:
//#line 103 "sintac.y"
{ yyval = new Read(val_peek(1)); }
break;
case 39:
//#line 104 "sintac.y"
{ yyval = val_peek(1); }
break;
case 40:
//#line 105 "sintac.y"
{ yyval = val_peek(0); }
break;
case 41:
//#line 106 "sintac.y"
{ yyval = val_peek(0); }
break;
case 42:
//#line 110 "sintac.y"
{ yyval = new Invocacion(val_peek(3), val_peek(1), true); }
break;
case 43:
//#line 112 "sintac.y"
{ yyval = new While(val_peek(4), val_peek(1)); }
break;
case 44:
//#line 115 "sintac.y"
{ yyval = new If(val_peek(4), val_peek(1), new ArrayList()); }
break;
case 45:
//#line 116 "sintac.y"
{ yyval = new If(val_peek(8), val_peek(5), val_peek(1)); }
break;
case 46:
//#line 119 "sintac.y"
{ yyval = new Variable(val_peek(0)); }
break;
case 47:
//#line 120 "sintac.y"
{ yyval = new LiteralInt(val_peek(0), true); }
break;
case 48:
//#line 121 "sintac.y"
{ yyval = new LiteralReal(val_peek(0), true); }
break;
case 49:
//#line 122 "sintac.y"
{ yyval = new LiteralChar(val_peek(0)); }
break;
case 50:
//#line 123 "sintac.y"
{ yyval = new LiteralInt(val_peek(0), false); }
break;
case 51:
//#line 124 "sintac.y"
{ yyval = new LiteralReal(val_peek(0), false); }
break;
case 52:
//#line 125 "sintac.y"
{ yyval = val_peek(1); }
break;
case 53:
//#line 126 "sintac.y"
{ yyval = new Array(val_peek(3), val_peek(1)); }
break;
case 54:
//#line 127 "sintac.y"
{ yyval = new Navegacion(val_peek(2), val_peek(0)); }
break;
case 55:
//#line 128 "sintac.y"
{ yyval = new ExprAritmetica(val_peek(2), "*", val_peek(0)); }
break;
case 56:
//#line 129 "sintac.y"
{ yyval = new ExprAritmetica(val_peek(2), "/", val_peek(0)); }
break;
case 57:
//#line 130 "sintac.y"
{ yyval = new ExprAritmetica(val_peek(2), "+", val_peek(0)); }
break;
case 58:
//#line 131 "sintac.y"
{ yyval = new ExprAritmetica(val_peek(2), "-", val_peek(0)); }
break;
case 59:
//#line 132 "sintac.y"
{ yyval = new ExprBinaria(val_peek(2), "&&", val_peek(0)); }
break;
case 60:
//#line 133 "sintac.y"
{ yyval = new ExprBinaria(val_peek(2), "||", val_peek(0)); }
break;
case 61:
//#line 134 "sintac.y"
{ yyval = new ExprComparativa(val_peek(2), "<=", val_peek(0)); }
break;
case 62:
//#line 135 "sintac.y"
{ yyval = new ExprComparativa(val_peek(2), "!=", val_peek(0)); }
break;
case 63:
//#line 136 "sintac.y"
{ yyval = new ExprComparativa(val_peek(2), ">=", val_peek(0)); }
break;
case 64:
//#line 137 "sintac.y"
{ yyval = new ExprComparativa(val_peek(2), "==", val_peek(0)); }
break;
case 65:
//#line 138 "sintac.y"
{ yyval = new ExprComparativa(val_peek(2), "<", val_peek(0)); }
break;
case 66:
//#line 139 "sintac.y"
{ yyval = new ExprComparativa(val_peek(2), ">", val_peek(0)); }
break;
case 67:
//#line 140 "sintac.y"
{ yyval = new Negacion(val_peek(0)); }
break;
case 68:
//#line 141 "sintac.y"
{ yyval = val_peek(0); }
break;
case 69:
//#line 142 "sintac.y"
{ yyval = val_peek(0); }
break;
case 70:
//#line 145 "sintac.y"
{ yyval = new Invocacion(val_peek(3), val_peek(1), false); }
break;
case 71:
//#line 148 "sintac.y"
{ yyval = new Cast(val_peek(4), val_peek(1)); }
break;
case 72:
//#line 153 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 73:
//#line 154 "sintac.y"
{ yyval = val_peek(0); }
break;
case 74:
//#line 157 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); }
break;
case 75:
//#line 158 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
//#line 1021 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
