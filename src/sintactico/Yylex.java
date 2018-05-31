/* The following code was generated by JFlex 1.4.1 on 17/05/18 0:16 */

// @author Ra�l Izquierdo

/* -- No es necesario modificar esta parte ----------------------------------------------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 17/05/18 0:16 from the specification file
 * <tt>lexico.l</tt>
 */
public class Yylex {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\43\1\31\2\0\1\42\22\0\1\42\1\37\4\0\1\32"+
    "\1\27\2\1\1\41\3\1\1\26\1\40\12\25\2\1\1\36\1\35"+
    "\1\34\2\0\32\23\1\1\1\30\1\1\1\0\1\24\1\0\1\3"+
    "\1\23\1\10\1\16\1\15\1\21\1\23\1\20\1\12\2\23\1\14"+
    "\1\23\1\13\1\22\1\11\1\23\1\4\1\5\1\6\1\7\1\2"+
    "\1\17\3\23\1\1\1\33\1\1\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\13\3\1\4\1\1\1\5\2\1"+
    "\5\2\1\6\7\3\1\7\4\3\3\0\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\5\1\0\1\16\6\3"+
    "\1\17\4\3\1\20\1\21\1\0\1\22\1\23\2\3"+
    "\1\24\1\25\1\3\1\26\1\27\4\3\1\30\1\31"+
    "\1\32\1\33\2\3\1\34\1\35";

  private static int [] zzUnpackAction() {
    int [] result = new int[84];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\44\0\44\0\110\0\154\0\220\0\264\0\330"+
    "\0\374\0\u0120\0\u0144\0\u0168\0\u018c\0\u01b0\0\u01d4\0\u01f8"+
    "\0\44\0\u021c\0\u0240\0\u0264\0\u0288\0\u02ac\0\u02d0\0\u02f4"+
    "\0\44\0\u0318\0\u033c\0\u0360\0\u0384\0\u03a8\0\u03cc\0\u03f0"+
    "\0\154\0\u0414\0\u0438\0\u045c\0\u0480\0\u04a4\0\u04c8\0\u04ec"+
    "\0\44\0\44\0\44\0\44\0\44\0\44\0\u0510\0\u0534"+
    "\0\154\0\u0558\0\u057c\0\u05a0\0\u05c4\0\u05e8\0\u060c\0\154"+
    "\0\u0630\0\u0654\0\u0678\0\u069c\0\u04a4\0\44\0\u06c0\0\154"+
    "\0\154\0\u06e4\0\u0708\0\154\0\154\0\u072c\0\154\0\154"+
    "\0\u0750\0\u0774\0\u0798\0\u07bc\0\u07e0\0\154\0\154\0\154"+
    "\0\u0804\0\u0828\0\154\0\154";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[84];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\2\5\1\10"+
    "\1\11\1\12\1\13\1\5\1\14\1\5\1\15\1\5"+
    "\1\16\2\5\1\2\1\17\1\3\1\20\1\2\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\3"+
    "\1\21\1\31\46\0\1\5\1\32\22\5\20\0\24\5"+
    "\20\0\13\5\1\33\10\5\20\0\4\5\1\34\17\5"+
    "\20\0\1\5\1\35\14\5\1\36\5\5\20\0\2\5"+
    "\1\37\21\5\20\0\11\5\1\40\5\5\1\41\4\5"+
    "\20\0\5\5\1\42\16\5\20\0\12\5\1\43\11\5"+
    "\20\0\16\5\1\44\5\5\20\0\12\5\1\45\11\5"+
    "\43\0\1\17\1\46\15\0\30\47\1\50\1\0\12\47"+
    "\32\0\1\51\44\0\1\52\45\0\1\53\43\0\1\54"+
    "\43\0\1\55\43\0\1\56\46\0\1\57\1\60\4\0"+
    "\2\5\1\61\21\5\20\0\1\5\1\62\2\5\1\63"+
    "\17\5\20\0\2\5\1\64\21\5\20\0\3\5\1\65"+
    "\20\5\20\0\1\5\1\66\22\5\20\0\10\5\1\67"+
    "\13\5\20\0\4\5\1\70\17\5\20\0\12\5\1\71"+
    "\11\5\20\0\3\5\1\72\20\5\20\0\10\5\1\73"+
    "\13\5\20\0\20\5\1\74\3\5\43\0\1\75\45\0"+
    "\1\76\27\0\1\47\13\0\1\76\14\0\31\57\1\0"+
    "\12\57\41\60\1\77\2\60\2\0\12\5\1\100\1\5"+
    "\1\101\7\5\20\0\5\5\1\102\16\5\20\0\5\5"+
    "\1\103\16\5\20\0\4\5\1\104\17\5\20\0\2\5"+
    "\1\105\21\5\20\0\11\5\1\106\12\5\20\0\12\5"+
    "\1\107\11\5\20\0\13\5\1\110\10\5\20\0\12\5"+
    "\1\111\11\5\20\0\1\5\1\112\22\5\16\0\40\60"+
    "\1\21\1\77\2\60\2\0\2\5\1\113\21\5\20\0"+
    "\6\5\1\114\15\5\20\0\4\5\1\115\17\5\20\0"+
    "\13\5\1\116\10\5\20\0\4\5\1\100\17\5\20\0"+
    "\11\5\1\117\12\5\20\0\4\5\1\120\17\5\20\0"+
    "\3\5\1\121\6\5\1\122\11\5\20\0\7\5\1\123"+
    "\14\5\20\0\11\5\1\124\12\5\16\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2124];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\2\11\15\1\1\11\7\1\1\11\14\1\3\0"+
    "\6\11\1\1\1\0\15\1\1\11\1\0\25\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[84];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
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


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Yylex(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Yylex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 112) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzPushbackPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead < 0) {
      return true;
    }
    else {
      zzEndRead+= numRead;
      return false;
    }
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 21: 
          { return Parser.CHAR;
          }
        case 30: break;
        case 7: 
          { return Parser.IF;
          }
        case 31: break;
        case 19: 
          { return Parser.READ;
          }
        case 32: break;
        case 2: 
          { return yytext().charAt(0);
          }
        case 33: break;
        case 3: 
          { return Parser.IDENT;
          }
        case 34: break;
        case 27: 
          { return Parser.STRUCT;
          }
        case 35: break;
        case 1: 
          { gestor.error("L�xico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column()));
          }
        case 36: break;
        case 8: 
          { return Parser.AND;
          }
        case 37: break;
        case 24: 
          { return Parser.PRINT;
          }
        case 38: break;
        case 16: 
          { return Parser.LITERALREAL;
          }
        case 39: break;
        case 12: 
          { return Parser.MENORIGUAL;
          }
        case 40: break;
        case 26: 
          { return Parser.RETURN;
          }
        case 41: break;
        case 28: 
          { return Parser.PRINTSP;
          }
        case 42: break;
        case 23: 
          { return Parser.ELSE;
          }
        case 43: break;
        case 9: 
          { return Parser.OR;
          }
        case 44: break;
        case 17: 
          { return Parser.LITERALCHAR;
          }
        case 45: break;
        case 11: 
          { return Parser.IGUALA;
          }
        case 46: break;
        case 20: 
          { return Parser.CAST;
          }
        case 47: break;
        case 25: 
          { return Parser.WHILE;
          }
        case 48: break;
        case 6: 
          { yycolumn += 3;
          }
        case 49: break;
        case 22: 
          { return Parser.NULL;
          }
        case 50: break;
        case 18: 
          { return Parser.REAL;
          }
        case 51: break;
        case 13: 
          { return Parser.DISTINTO;
          }
        case 52: break;
        case 29: 
          { return Parser.PRINTLN;
          }
        case 53: break;
        case 4: 
          { return Parser.LITERALINT;
          }
        case 54: break;
        case 15: 
          { return Parser.INT;
          }
        case 55: break;
        case 10: 
          { return Parser.MAYORIGUAL;
          }
        case 56: break;
        case 14: 
          { return Parser.VAR;
          }
        case 57: break;
        case 5: 
          { 
          }
        case 58: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return 0; }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
