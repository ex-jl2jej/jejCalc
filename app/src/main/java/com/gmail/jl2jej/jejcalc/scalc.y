%{
package com.gmail.jl2jej.jejcalc;

import java.lang.Character;
import java.lang.String;
import java.lang.Math;
import java.util.*;
%}

%token	<val>	NUMBER
%token	<id>	UNDEF VAR CONST FUNC1 FUNC2 LISTCONST LISTVAR HELP CLEAR FORMAT ECHO UNIT UFORMAT IF THEN ELSE
%token	<radix>	RADIX
%token	<op>	ASGNOP
%token	<string>	STRING
%type	<val>	expr asgn
%right	ASGNOP
%left	EQUAL '>' GE '<' LE
%left	'+' '-' '|'
%left	'*' '/' '%' '&' '^' SHL SHR
%left	UNARY
%right	POWER
%left   '!'
%left	FUNC
%left	INC DEC
%%
list	: /* empty */
	| list eol
	| list LISTCONST eol	{ ident.listId(CONST);  }
	| list LISTVAR eol	{ ident.listId(VAR);	}
/*  | list LISTVAR '>' STRING eol	{ saveList( VAR );	} */
/*  | list LISTCONST '>' STRING eol	{ saveList( CONST );	} */
/*  | list '<' STRING eol	{ redirInput( string ); } */
	| list ECHO STRING eol	{ result.append( $3 + "\n" );	}
	| list CLEAR VAR eol	{ $3.type = UNDEF;	}
	| list CLEAR CONST eol	{ $3.type = UNDEF;	}
	| list CLEAR eol	{ ident.clearVarAll();		}
	| list LISTCONST VAR eol	{ $3.type = CONST;	}
	| list LISTVAR CONST eol	{ $3.type = VAR;	}
	| list FORMAT STRING eol	{ PRINT_FORMAT = $3;	}
    | list FORMAT eol	{ result.append( "format \"" + PRINT_FORMAT.toString() + "\"\n" ); }
    | list UFORMAT STRING eol	{ UNIT_FORMAT = $3;	}
    | list UFORMAT eol	{ result.append( "format \"" + UNIT_FORMAT.toString() + "\"\n" ); }
/*	| list STRING eol	{ execCommand(string); 	} */
	| list HELP eol		{ help();		}
	| list '-' HELP eol	{ help();		}
    | list RADIX eol	{ changeORadix( $2 ); 		}
	| list UNIT eol		{ printUnit();		}
    | list UNIT VAR eol	{
        if( $3.v == 0.0 ) {
            execerror( "Zero is Illegal Unit.", "" );
        }
        unit = $3.v;
        unitName = $3.name;
        printUnit();
      }
	| list UNIT CONST eol	{
        if( $3.v == 0.0 ) {
            execerror( "Zero is Illegal Unit.", "" );
        }
        unit = $3.v;
        unitName = $3.name;
        printUnit();
      }
	| list UNIT expr eol	{
        if( $3 == 0.0 ) {
            execerror( "Zero is Illegal Unit.", "" );
        }
        unit = $3;
        unitName = new StringBuilder("");
        printUnit();
       }
    | list asgn eol { printval(oradix, $2, unit, unitName); setPrevious($2); }
    | list expr '@' VAR eol { printval(oradix,$2,$4.v,$4.name); setPrevious($2);	}
	| list expr '@' CONST eol { printval(oradix,$2,$4.v,$4.name); setPrevious($2);	}
    | list expr '@' expr eol { printval(oradix,$2,$4,new StringBuilder("") ); setPrevious($2);	}
	| list expr RADIX eol	{ printval($3, $2, unit, unitName); setPrevious($2);	}
	| list expr eol		{ printval(oradix, $2, unit, unitName); setPrevious($2);	}
    | list error eol	{ yyerror("");		}
	;

eol	: '\n'
	| ';'
	| ','
        | '\0'
	;

asgn	: VAR ASGNOP expr
		{ $$ = asgnop($2, $1, $3);  }
	| UNDEF ASGNOP expr	
		{ $$ = asgnop($2, $1, $3); $1.type = VAR; }
	| CONST ASGNOP expr
         	{ execerror( "const cannot be changed.", $1.name.toString() ); }
	| NUMBER ASGNOP expr
		{ execerror( "const cannot be changed.", "" ); }
	;

expr	: NUMBER			{ $$ = $1;	}
    | UNDEF				{ execerror( "variable not defined.", $1.name.toString() ); }
	| CONST				{ $$ = $1.v;	}
	| VAR				{ $$ = $1.v;	}
	| NUMBER VAR			{ $$ = $1 * $2.v;	}
	| NUMBER CONST			{ $$ = $1 * $2.v;	}
    | NUMBER UNDEF			{ execerror( "variable not defined.", $2.name.toString() ); }
	| asgn				{ $$ = $1;	}
    | expr '+' expr			{ $$ = $1 + $3; }
	| expr '-' expr			{ $$ = $1 - $3; }
	| expr '*' expr			{ $$ = $1 * $3; }
	| expr '/' expr			{
		if( $3 == 0.0 ) {
			execerror("division by zero", "" );
        }
		$$ = $1 / $3; }
	| expr '%' expr			{ $$ = fmod($1, $3);	}
	| expr '&' expr			{ $$ =(double)((int)$1 & (int)$3);}
	| expr '^' expr			{ $$ =(double)((int)$1 ^ (int)$3);}
	| expr '|' expr			{ $$ =(double)((int)$1 | (int)$3);}
	| expr SHL expr			{ $$ =(double)((int)$1 << (int)$3);}
	| expr SHR expr			{ $$ =(double)((int)$1 >> (int)$3);}
    | expr EQUAL expr		{ $$ =(((double)$1 == (double)$3) ? 1.0 : 0.0);}
    | expr '>' expr			{ $$ =(((double)$1 >  (double)$3) ? 1.0 : 0.0 );}
    | expr GE expr			{ $$ =(((double)$1 >= (double)$3) ? 1.0 : 0.0 );}
    | expr '<' expr			{ $$ =(((double)$1 <  (double)$3) ? 1.0 : 0.0 );}
    | expr LE expr			{ $$ =(((double)$1 <= (double)$3) ? 1.0 : 0.0 );}
	| expr '!'			{ $$ = fact( (int)$1 );	}
	| IF expr THEN expr ELSE expr	{ $$ = ((int)($2) == 0 ? ($6) : ($4)); }
    | expr POWER expr		{ $$ = Math.pow($1, $3);	}
	| '-'	expr %prec UNARY	{ $$ = -$2;	}
	| '+'	expr %prec UNARY	{ $$ = $2;	}
	| '~'	expr %prec UNARY	{ $$ = (double)(~(long)$2);	}
    | FUNC1 expr %prec FUNC		{ $$ = calcFunc( $1.name, $2 ); }
    | FUNC2 '(' expr ',' expr ')'	{ $$ = calcFunc( $1.name, $3, $5 );	}
	| VAR INC			{ $$ = $1.v; $1.v += 1.0; }
	| VAR DEC			{ $$ = $1.v; $1.v -= 1.0; }
	| INC VAR			{ $$ = $2.v += 1.0;	}
	| DEC VAR			{ $$ = $2.v -= 1.0;	}
	| '(' expr ')'			{ $$ = $2;	}
	;

%%

String ins;
int insAt;
StringBuilder result;
List<Integer> radixtab = new RadixTable();
String radixnam = "cCbBoOdDxX";
String digittab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
String PREVIOUS	= "p";			/* var of previous value */
final int RADIX_ON = 1;
final int RADIX_OFF = -1;
final int RADIX_QUERY = 0;
final int STRING_MAX_LENGTH = 4096;
final int CHAR_MAX = 65536;
int iradix = 10;
int oradix = 10;
int radixon = RADIX_ON;
int bitwidth = 32;
final int NUMLEN = 80;
final int IDLEN = 80;
final int NULL = 0;
IdentTree ident = new IdentTree();
StringBuilder PRINT_FORMAT = new StringBuilder( "%.15g" );
StringBuilder UNIT_FORMAT = new StringBuilder( "%12.4e" );
double unit = 1.0;
StringBuilder unitName = new StringBuilder( "" );
boolean isWideScreen = true;

private class RadixTable extends ArrayList<Integer> {
    static final long serialVersionUID = -4684615628971053908L;
    RadixTable () {
        add(new Integer(1)); add(new Integer(1));
        add(new Integer(2)); add(new Integer(2));
        add(new Integer(8)); add(new Integer(8));
        add(new Integer(10)); add(new Integer(10));
        add(new Integer(16)); add(new Integer(16));
    }
}

class JcalcException extends Exception {
    static final long serialVersionUID = -5141661047935852323L;
    public JcalcException() {
        super();
    }
    public JcalcException(String message) {
        super(message);
    }
    public JcalcException(String message, Throwable cause) {
        super( message, cause );
    }
    public JcalcException(Throwable cause) {
        super(cause);
    }
}

class JcalcRuntimeException extends RuntimeException {
    static final long serialVersionUID = -8646210782157489379L;
    public JcalcRuntimeException() {
        super();
    }
    public JcalcRuntimeException(String message) {
        super(message);
    }
}

private class IdentTree extends TreeMap<String,Ident> {
    static final long serialVersionUID = -4245188664305635686L;
    private void clearVar( Ident id ) {
        id.type = UNDEF;
        id.v = 0.0;
    }
    private void clearVarAll() {
        for( String key : ident.keySet() ) {
            Ident id = ident.get( key );
            if( id.type == VAR ) {
                clearVar( id );
            }
        }
    }
    private IdentTree() {
        super();
        this.init();
    }
    private void install( String name, int type, double value )
    {
        Ident ni = new Ident();
        ni.name = new StringBuilder( name );
        ni.type = type;
        ni.v = value;

        this.put( name, ni );
    }
    private void install( String name, int type )
    {
        Ident ni = new Ident();
        ni.name = new StringBuilder( name );
        ni.type = type;

        this.put( name, ni );
    }
    private void init()
    {
        install( "abs",	FUNC1 );
        install( "sin",	FUNC1 );
        install( "cos",	FUNC1 );
        install( "tan",	FUNC1 );
        install( "asin", FUNC1 );
        install( "acos", FUNC1 );
        install( "atan", FUNC1 );
        install( "atan2", FUNC2 );
        install( "sinh", FUNC1 );
        install( "cosh", FUNC1 );
        install( "tanh", FUNC1 );
        install( "int", FUNC1 );
        install( "log", FUNC1 );
        install( "log10", FUNC1 );
        install( "exp", FUNC1 );
        install( "sqrt", FUNC1 );
        install( "rad", FUNC1 );
        install( "deg", FUNC1 );
        install( "comp2", FUNC1 );
        install( "jis2sft", FUNC1 );
        install( "sft2jis", FUNC1 );
        install( "euc2sft", FUNC1 );
        install( "sft2euc", FUNC1 );
        install( "jis2euc", FUNC1 );
        install( "euc2jis", FUNC1 );
        install( "iradix", FUNC1 );
        install( "oradix", FUNC1 );
        install( "bitw", FUNC1 );
        install( "radixonoff", FUNC1 );

        install("p",  VAR, 0.0 );
        install("const", LISTCONST);
        install("var", LISTVAR);
        install("help", HELP );
        install("clear", CLEAR );
        install("shl", SHL);
        install("shr", SHR);
        install("format", FORMAT);
        install("echo", ECHO);
        install("unit", UNIT);
        install("uformat", UFORMAT );
        install("if", IF);
        install("then", THEN);
        install("else", ELSE);

        install( "_PI", CONST, Math.PI ); //3.14159265358979323846
        install( "_E",  CONST, Math.E  ); //2.71828182845904523536
        install( "_GAMMA", CONST, 0.57721566490153286060 );
        install( "_RADIX_ON", CONST, 1.0 );
        install( "_RADIX_OFF",	CONST, -1.0 );
        install( "_RADIX_QUERY", CONST, 0.0 );
    }
    private void listId( int type ) {
        for( String key : ident.keySet() ) {
            Ident id = ident.get( key );
            if( id.type == type ) {
                result.append( String.format( "%s = ", key ) );
                printval(oradix, id.v, unit, unitName);
            }
        }
    }
}

private void help()
{
    String cr;
    if( isWideScreen == true ) {
        cr = "";
    } else {
        cr = "\n     ";
    }
    result.append(
                  //...+....1....+....2....+..\n" +
                  //...+
                  "jejcalc:this is calculator" + cr + 
                       " Version 1.0\n" +
                  "operators : +,-,*,/,**,%,!," + cr +
                       "~,&,|,^,<<,>>,shl,shr," + cr +
                       "if x then y else z,\n" +
                  "     =,+=,-=,*=,/=,%=,&=," + cr +
                       "|=,^=,<<=,>>=," + cr +
                       "==,>=,>,<=,<\n" +
                  "functions:abs,sin,cos,tan," + cr +
                       "asin,acos,atan,atan2,\n" +
                  //"            sft2jis,jis2sft,euc2jis,jis2euc,euc2sft,sft2euc,\n" +
                  "     comp2,sinh,cosh,tanh," + cr +
                       "int,log,log10,exp," + cr +
                       "sqrt,deg,rad,\n" +
                  "     iradix, oradix," + cr +
                       "radixonoff, bitw\n" +
                  //...+....1....+....2....+..\n" +
                  //...+
                  "commands\n" +
                  " ?,help:print this message.\n" +
                  " [const|var]:" + cr +
                       "print [Constants|Var]\n" +
                  //"   [const | var] >\"file name\" : output [Constants | Variables] to file.\n" +
                  //"   < \"filename\"               : input expr from file.\n" +
                  " echo \"string\":" + cr +
                       "echo string.\n" +
                  " const variable:" + cr +
                       "change var to const.\n" +
                  " var constant:" + cr +
                       "change const to var.\n" +
                  " clear:clear all variables\n" +
                  " clear var(const):" + cr +
                       "clear var(const).\n" +
                  " format \"format\":" + cr +
                       "set print format" + cr +
                       "(same C).\n" +
                  " format:" + cr +
                       "display print format.\n" +
                  " uformat \"format\":" + cr +
                       "set unit format" + cr +
                       "(same C).\n" +
                  " uformat:" + cr +
                       "display unit format.\n" +
                  //"   \"command\"                  : execute command on switch charactar '/'.\n" +
                  " :B :O :D :X :C =>" + cr +
                       " change default print" + cr +
                       " radix(2,8,10,16,char)\n" +
                  " expr[:r] : calculate expr." + cr +
                       "[and print radix = r.]\n" +
                  //...+....1....+....2....+..\n" +
                  //...+
                  " unit:print unit.\n" +
                  " unit [const | var | expr]:"+ cr +
                       "set unit.\n" +
                  " expr @ [const|var|expr]:" + cr +
                       "calculate expr. and" + cr +
                       " print unit = [value].\n" +
                  " var = expr:calculate expr," + cr +
                       "and assign to var.\n" +
                  "Note:\n" +
                  " 1.Var p is previous answer\n" +
                  " 2.';' and ',' is delimiter\n" +
                  " 3. 'X' is ASCII code of X.\n" +
                  " 4. '$' is symbol prefix.\n" +
                  " 5. '#' is symbol prefix.\n" +
                  " 6. '//' is commnet prefix.\n" );

}

private void printUnit()
{
    if( unitName.length() > 0 ) {
        result.append( unitName.toString() );
        result.append( " = " );
    }
    printval(oradix,unit,1.0,new StringBuilder(""));
}

private void printCharacter( int val )
{
    val &= 0xff;
    if( val < ' ' ) {
        result.append( String.format("^%c", val +'@' ) );
    } else {
        result.append( String.format( "%c", val ) );
    }
}

private void setPrevious(double val) {
    Ident	id;

    id = ident.get(PREVIOUS);
    if( id == null ) {
        ident.install( "p",  VAR, val );
    } else {
        id.v = val;
        id.type = VAR;
    }
}

private void printval( int rad, double val, double un, StringBuilder unitName ) throws JcalcRuntimeException
{
    int	c;
    double	n, l, d;

    if( un ==  0.0 ) {
        throw new JcalcRuntimeException( "Illegal Unit ( Unit is Zero )" );
    } else {
        val = val /un;
    }
    if(rad == 10) {
        result.append( String.format(PRINT_FORMAT.toString(), val) );
    } else if( rad == 1 ) {
        result.append( "'" );
        n = (double)CHAR_MAX;
        c = (int)Math.floor(Math.log(val)/Math.log(n));
        l = Math.pow(n, (double)c);
        do{
            d = Math.floor(val/l);
            val -= d*l;
            l /= n;
            printCharacter( (int)d );
            if( d == '\'' ) {
                result.append( "'" );
            }
        }while(--c >= 0);
        result.append( "'" );
    } else {
        while( val < 0.0 ) {
            val = Math.pow( 2.0, (double)bitwidth ) + val;
        }
        if( radixon == RADIX_ON ) {
            int ir;
            ir = radixtab.indexOf(rad);
            if( ir > 0 ) {
                result.append( String.format("0%s", radixnam.substring( ir, ir+1 ) ) );
            } else {
                result.append( String.format("0(%d)", oradix ) );
            }
        }
        if(val == 0.0) {
            result.append("0" );
        } else {
            n = (double)rad;
            c = (int)Math.floor(Math.log(val)/Math.log(n));
            l = Math.pow(n, (double)c);
            if( c < 0 ) {
                result.append( "0" );
            } else {
                do{
                    d = Math.floor(val/l);
                    val -= d*l;
                    l /= n;
                    result.append( digittab.substring((int)d,(int)d+1) );
                }while(--c >= 0);
            }
        }
    }
    if( unitName.length() > 0 ) {
        result.append( " " );
        result.append( unitName );
    } else if( un != 1.0 ) {
        result.append( String.format( UNIT_FORMAT.toString(), un ) );
    }
    result.append( "\n" );
}

double changeORadix( double v ) throws JcalcRuntimeException
{
    int	l;
    l = (int)v;
    if( v == 0.0 ) {
        return( (double)oradix );
    } else if( l <= 0 || (double)l != v || l > 36 ) {
        throw new JcalcRuntimeException( String.format( "Illeagal print radix = %g, not change.", v ) );
    } else {
        oradix = l;
    }
    return( (double)oradix );
}

double changeIRadix( double v ) throws JcalcRuntimeException
{
    int	l;

    l = (int)v;
    if( v == 0.0 ) {
        return( (double)iradix );
    } else if( l <= 0 || (double)l != v || l > 36 ) {
        throw new JcalcRuntimeException( String.format( "Illeagal input radix = %g, not change.", v ) );
    } else {
        iradix = l;
    }
    return( (double)iradix );
}

double radixOnOff( double v )
{
    if( v == RADIX_QUERY ) {
    } else if( v > 0.0 ) {
        radixon = RADIX_ON;
    } else {
        radixon = RADIX_OFF;
    }
    return( (double)radixon );
}

double changeBitw( double v ) throws JcalcRuntimeException
{
    int	l;

    l = (int)v;
    if( v == 0.0 ) {
        return( (double)bitwidth );
    } else if( l <= 0 || (double)l != v || l > 32 ) {
        throw new JcalcRuntimeException( String.format( "Illeagal bitwidth = %g, not change.", v ) );
    } else {
        bitwidth = l;
    }
    return( (double)bitwidth );
}

double asgnop( int ope, Ident id, double val )
{
    switch( ope ) {
    case '=':	id.v = val;				break;
    case '+':	id.v += val;				break;
    case '-':	id.v -= val;				break;
    case '/':	id.v /= val;				break;
    case '*':	id.v *= val;				break;
    case '|':	id.v = (double)((long)id.v | (long)val);	break;
    case '%':	id.v = fmod( id.v, val );			break;
    case '&':	id.v = (double)((long)id.v & (long)val);	break;
    case '^':	id.v = (double)((long)id.v ^ (long)val);	break;
    case SHR:	id.v = (double)((long)id.v >> (long)val);	break;
    case SHL:	id.v = (double)((long)id.v << (long)val);	break;
    default:	result.append( "bug : asgnop" );	break;
    }
    return( id.v );
}

private double calcFunc( StringBuilder name, double a ) throws JcalcException
{
    if( name.toString().contentEquals("abs") ) {
        return Math.abs(a);
    } else if( name.toString().contentEquals( "sin" ) ) {
        return Math.sin(a);
    } else if( name.toString().contentEquals( "cos" ) ) {
        return Math.cos(a);
    } else if( name.toString().contentEquals( "tan" ) ) {
        return Math.tan(a);
    } else if( name.toString().contentEquals( "asin" ) ) {
        return Math.asin(a);
    } else if( name.toString().contentEquals( "acos" ) ) {
        return Math.acos(a);
    } else if( name.toString().contentEquals( "atan" ) ) {
        return Math.atan(a);
    } else if( name.toString().contentEquals( "sinh" ) ) {
        return Math.sinh(a);
    } else if( name.toString().contentEquals( "cosh" ) ) {
        return Math.cosh(a);
    } else if( name.toString().contentEquals( "tanh" ) ) {
        return Math.tanh(a);
    } else if( name.toString().contentEquals( "int" ) ) {
        return (double)((int)a);
    } else if( name.toString().contentEquals( "log" ) ) {
        return Math.log(a);
    } else if( name.toString().contentEquals( "log10" ) ) {
        return Math.log10(a);
    } else if( name.toString().contentEquals( "exp" ) ) {
        return Math.exp(a);
    } else if( name.toString().contentEquals( "sqrt" ) ) {
        return Math.sqrt(a);
    } else if( name.toString().contentEquals( "deg" ) ) {
        return (a * 180.0 / Math.PI);
    } else if( name.toString().contentEquals( "rad" ) ) {
        return (a * Math.PI / 180.0 );
    } else if( name.toString().contentEquals("comp2") ) {
        if( a >= 0.0 ) {
            return a;
        } else {
            return Math.pow( 2.0, (double)bitwidth ) + a;
        }
    } else if( name.toString().contentEquals("iradix") ) {
        return changeIRadix( a );
    } else if( name.toString().contentEquals("oradix") ) {
        return changeORadix( a );
    } else if( name.toString().contentEquals("radixonoff") ) {
        return radixOnOff( a );
    } else if( name.toString().contentEquals("bitw") ) {
        return changeBitw( a );
    } else {
        throw new JcalcException( name.toString() + " is not defined." );
    }
}

private double calcFunc( StringBuilder name, double a, double b ) throws JcalcException
{
    if( name.toString().contentEquals("atan2") ) {
        return Math.atan2( a, b );
    } else {
        throw new JcalcException( name.toString() + " is not defined." );
    }
}

private double fact( int n ) throws JcalcException
{
    int i;
    double s = 1.0;

    if( n < 0 ) {
        execerror( "Negative number's factorial is not exist\n", "" );
    }
    for( i = 2 ; i <= n ; i++ ) {
        s *= (double)i;
    }
    return( s );
}

private char GET()
{
    char c;
    if( ins.length() == insAt ) {
        return '\0';
    } else {
        c = ins.charAt( insAt );
        insAt++;
        return c;
    }
}

private void UNGET()
{
    insAt--;
    if( insAt < 0 ) {
        insAt = 0;
    }
}

private int ikutsu( int c )
{
    int	a;
    String digitStr = digittab.substring( 0, iradix );

    if( Character.isDigit( (char)c ) ) {
        a = digitStr.indexOf( (new Character((char)c)).toString() );
    } else if( Character.isLetter( (char)c ) ) {
        a = digitStr.indexOf( (new Character( Character.toUpperCase((char)c) )).toString() );
    } else {
        return( -1 );
    }
    if( (double)a >= iradix ) {
        return( -1 );
    } else {
        return( a );
    }
}

private boolean isNameFirst( int c )
{
    if( Character.isLetter( (char)c ) ) {
        return( true );
    } else if( c == '_' ) {
        return( true );
    } else {
        return( false );
    }
}

private boolean  isName( int c )
{
    if( Character.isLetterOrDigit( (char)c ) ) {
        return( true );
    } else if( c == '#' ) {
        return( true );
    } else if( c == '_' ) {
	    return( true );
    } else {
        return( false );	
    }
}

private void yyerrorNR( String s )
{
    result.append( s );
}

private void yyerror( String s )
{
    yyerrorNR( s );
    yyerrorNR( "\n" );
}

private double fmod( double a, double b )
{
    int n = (int)Math.floor( a / b );

    return a - b * (double)n;
}

private void execerror( String s, String p ) throws JcalcException
{
    if( ! p.equals("") ) {
        throw new JcalcException( p + " : " + s );
    } else {
        throw new JcalcException( s );
    }
}

double getfloat() throws JcalcException
{
    int	c;
    StringBuilder buf = new StringBuilder("");

    if((c = GET()) == '0') {
        int p;
        String digitStr;
        int	n, val;

        if((c = GET()) != '\0' && (p = radixnam.indexOf( new Character((char)c).toString())) != -1 ){
            n = radixtab.get(p).intValue();
            digitStr = digittab.substring( 0, n );
            val = 0;
            while((c = GET()) != '\0' &&
                  (p = digitStr.indexOf( new Character( Character.toUpperCase((char)c) ).toString() ) ) != -1 )
                val = val*n+p;
            UNGET();
            return( (double)val );
        } else if( c == '(' ) {
            n = 0;
            while( Character.isDigit( c = GET() ) ) {
                n = n * 10 + c - '0';
            }
            if( c == ')' ) {
                val = 0;
                if( n > digittab.length() ) {
                    throw new JcalcException( "radix " + n + " is too large." );
                }
                digitStr = digittab.substring( 0, n );
                while((c = GET()) != '\0' &&
                      (p = digitStr.indexOf( new Character( Character.toUpperCase((char)c) ).toString() ) ) != -1 ) {
                    val = val*n+p;
                }
                UNGET();
                return( (double)val );
            } else {
                throw new JcalcException( "')' is expected." );
            }
        } else {
            UNGET();
        }
    }
    UNGET();

    if( iradix == 10 ) {
        while((c = GET()) != '\0' && Character.isDigit( c ) ) {
            if(buf.length() < NUMLEN ) {
                buf.append( new Character((char)c).toString() );
            }
        }
        if(c == '.'){
            do{
                if( buf.length() < NUMLEN ) {
                    buf.append( new Character((char)c).toString() );
                }
            }while((c = GET()) != '\0' && Character.isDigit(c));
        }
        if(c == 'e' || c == 'E'){
            if( buf.length() < NUMLEN ) {
                buf.append( new Character((char)c).toString() );
            }
            if((c = GET()) != '\0' &&  ( c == '+' || c == '-' || Character.isDigit(c) ) ) {
                do{
                    if( buf.length() < NUMLEN ) {
                        buf.append( new Character((char)c).toString() );
                    }
                }while((c = GET()) != '\0' && Character.isDigit(c));
            }
        }
        UNGET();
        return( Double.parseDouble(buf.toString()) );
    } else {
        double	s;
        int	d;

        s = 0.0;
        while( ( d = ikutsu( c = GET() ) ) >= 0 ) {
            s = s * iradix + (double)d;
        }
        UNGET();
        return( s );
    }
}

private class Ident {
    int type;
    double v;
    StringBuilder name;
}

private class Lval extends Object {
    int op;
    int radix;
    Ident id;
    StringBuilder string;
    double val;
    //int ival;
    //double dval;

    Lval() {
        op = 0;
        radix = 10;
        string = new StringBuilder("");
        val = 0.0;
    }
}

int yylex() throws JcalcException
{
    yylval = new Lval();
    int c;
    int c1;

    while( ( c = GET() ) == ' ' || c == '\t' )
        ;

	if (c == '\0' ){
		return( '\0' );
	} else if( c == '/' ) {			    // コメント？
        if( ( c1 = GET() ) == '/' ) {	// コメント確定
            while( (c = GET()) != '\n' && c != '\0' )
                ;
            return( c );
        } else {
            UNGET();					// コメントでなかった
        }
	}

    switch( c ) {
    case '=':
        if((c1 = GET()) == '=') {
            yylval.op = EQUAL;
            return( EQUAL );
        } else {
            UNGET();
            yylval.op = c;
            return( ASGNOP );
        }
    case '+': case '-': case '*':
        if((c1 = GET()) == c) {
            switch( c ) {
            case '+': return INC;
            case '-': return DEC;
            case '*': return POWER;
            default:
                throw new JcalcException( String.format( "unexpected error(%s, %d)",
                                                       new Throwable().getStackTrace()[1].getFileName(),
                                                       new Throwable().getStackTrace()[1].getLineNumber() ));
            }
        } else if(c1 == '=') {
            yylval.op = c;
            return ASGNOP;
        } else {
            UNGET();
            return c;
        }
    case '/': case '%': case '&': case '^': case '|':
        if((c1 = GET()) == '=' ) {
            yylval.op = c;
            return ASGNOP;
        } else {
            UNGET();
            return c;
        }
    case '<': case '>':
        if((c1 = GET()) == c ) {
            if((c1 = GET()) == '=' ) {
                yylval.op = ((c=='<')?SHL:SHR);
                return ASGNOP;
            } else {
                UNGET();
                return ((c=='<')?SHL:SHR);
            }
        } else if( c1 == '=' ) {
            yylval.op = ((c=='<')?LE:GE);
            return(yylval.op);
        } else {
            UNGET();
            return c;
        }
    }

    if ( Character.isDigit(c) || c == '.' || c == '#' ) {
        if( c != '#' ) {
            UNGET();
        }
        yylval.val = getfloat();
        return (NUMBER);
    } else if (c == '\'') {
        yylval.val = 0.0;
        for( ; ; ) {
            c = GET();
            if( c == '\'' ) {
                if( (c = GET()) == '\'' ) {
                    yylval.val = yylval.val * (double)CHAR_MAX + (double)c;
                } else {
                    UNGET();
                    return( NUMBER );
                }
            } else if( c == '\0' ) {
                return( NUMBER );
            } else {
                yylval.val = yylval.val *(double)CHAR_MAX + (double)c;
            }
        }
    } else if(c == ':'){
        int p;
        if((c = GET()) != '\0' && (p = radixnam.indexOf( new Character((char)c).toString() )) != -1 ){
            yylval.radix = radixtab.get(p).intValue();
            return RADIX;
        }
        execerror( "Illegal Radix.", "" );
    } else if( isNameFirst(c) || ( c == '$' ) ) {
        StringBuilder buf = new StringBuilder("");
	
        //char	buf[IDLEN+1], *s = buf;

        if( c == '$' ) {
            c = GET();
        }
        do{
            if(buf.length() <= IDLEN) {
                buf.append( new Character((char)c).toString() );
            }
        }while((c = GET()) != '\0' && isName(c));

        UNGET();
        yylval.id = ident.get( buf.toString() );
        if( yylval.id == null ) {
            ident.install( buf.toString(), UNDEF, 0.0 );
            yylval.id = ident.get( buf.toString() );
        }
        return( yylval.id.type );
    } else if( c == '?' ) {
        return( HELP );
    } else if( c == '\"' ) {
        int i = 0;
        int c2;

        while((c=GET()) != '\0' ) {
            if(c=='\"') {
                if((c2=GET())=='\0') {
                    UNGET();
                    break;
                }
                if(c2!='\"') {
                    UNGET();
                    break;
                }
            } else if(c=='\\') {
                if((c2=GET())=='\0') {
                    break;
                }
                if(c2=='\"') {
                    c = c2;
                } else {
                    UNGET();
                }
            }
            if( i < STRING_MAX_LENGTH ) {
                yylval.string.append( new Character((char)c).toString() );
                i++;
            }
        }
        return STRING;
    } else {
        return (c);
    }
    return c;
}

public void clearAllMemory()
{
    ident = new IdentTree();
}

public String doYyparse(String inp)
{
    result = new StringBuilder( "" );
    ins = inp + ";";
    insAt = 0;
    try {
        yyparse();
    } catch( JcalcException e ) {
        result.append( "syntax error : " + e.getMessage() + "\n" );
    } catch( JcalcRuntimeException e ) {
        result.append( "runtime error : " + e.getMessage() + "\n" );
    }

    return result.toString();
}

public Parser()
{
  //nothing to do
}

public Parser(boolean debugMe)
{
  yydebug=debugMe;
}

public Parser(boolean debugMe, boolean widescreen)
{
  yydebug = debugMe;
  isWideScreen = widescreen;
}
