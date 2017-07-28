//### This file created by BYACC 1.8(/Java extension  1.15)
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






//#line 2 "scalc.y"
package com.gmail.jl2jej.jejcalc;

import java.lang.Character;
import java.lang.String;
import java.lang.Math;
import java.util.*;
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
//## **user defined:Lval
String   yytext;//user variable to return contextual strings
Lval yyval; //used to return semantic vals from action routines
Lval yylval;//the 'lval' (result) I got from yylex()
Lval valstk[] = new Lval[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Lval();
  yylval=new Lval();
  valptr=-1;
}
final void val_push(Lval val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Lval[] newstack = new Lval[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Lval val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Lval val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Lval dup_yyval(Lval val)
{
  return val;
}
//#### end semantic value section ####
public final static short NUMBER=257;
public final static short UNDEF=258;
public final static short VAR=259;
public final static short CONST=260;
public final static short FUNC1=261;
public final static short FUNC2=262;
public final static short LISTCONST=263;
public final static short LISTVAR=264;
public final static short HELP=265;
public final static short CLEAR=266;
public final static short FORMAT=267;
public final static short ECHO=268;
public final static short UNIT=269;
public final static short UFORMAT=270;
public final static short IF=271;
public final static short THEN=272;
public final static short ELSE=273;
public final static short RADIX=274;
public final static short ASGNOP=275;
public final static short STRING=276;
public final static short EQUAL=277;
public final static short GE=278;
public final static short LE=279;
public final static short SHL=280;
public final static short SHR=281;
public final static short UNARY=282;
public final static short POWER=283;
public final static short FUNC=284;
public final static short INC=285;
public final static short DEC=286;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    3,    3,
    3,    3,    2,    2,    2,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    4,    4,    4,    3,    4,    4,
    4,    3,    4,    3,    3,    4,    3,    3,    4,    4,
    4,    3,    5,    5,    5,    4,    3,    3,    1,    1,
    1,    1,    3,    3,    3,    3,    1,    1,    1,    1,
    2,    2,    2,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    6,    3,    2,    2,    2,    2,    6,    2,    2,    2,
    2,    3,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   29,   30,   31,   32,    0,    0,    0,    0,
    2,   28,   43,   41,   42,    0,    0,    0,   68,   69,
    0,    0,   66,   44,    0,    0,    3,    0,    4,   15,
    0,    0,    8,    0,   12,    0,    0,    0,    0,   18,
    0,   14,    0,   17,    0,    0,    0,   70,   71,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   60,    0,
   27,   22,    0,    0,    0,    0,    0,    9,   10,    6,
    7,   11,    5,   19,   20,   21,   13,    0,   16,   72,
   26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,   24,   25,    0,    0,   67,    0,
};
final static short yydgoto[] = {                          1,
   67,   44,   31,
};
final static short yysindex[] = {                         0,
    1,   33, -254, -273, -260, -272, 1385,  -31,   55,   14,
   33,  140,    8, -264,   19,   13, 1385,   33, 1385, 1316,
 -243, -237,    0,    0,    0,    0, 1385, 1385,  398,   33,
    0,    0,    0,    0,    0, 1385, 1385, 1385,    0,    0,
 1385, 1385,    0,    0, 1385,   33,    0,   33,    0,    0,
   33,   33,    0,   33,    0,   33,    7,   10,  434,    0,
   33,    0, 1242,    0,  -33,   33,  -33,    0,    0,  -33,
 1277,   33, 1385, 1385, 1385, 1385, 1385, 1385, 1385, 1385,
 1385, 1385, 1385, 1385, 1385, 1385, 1385, 1385,    0, 1453,
    0,    0, 1407, 1407, 1407, 1407, 1305,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1385,    0,    0,
    0,  202,  202,  202,  202,  202,  902,  902,  902,  -33,
  -33,  -33,  -33,  -33,  -33,  -33,  -33,    7,   10,  434,
 1385, 1340,    0,    0,    0, 1371, 1385,    0, 1407,
};
final static short yyrindex[] = {                         0,
    0,    0,   38,   74,  110,  149,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1207,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, 1470, 1501,    0,    0,
    0,    0,    0,    0,  465,    0,  501,    0,    0,  537,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  178,  297,  333, 1009,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  915,  948, 1021, 1056, 1117,  664,  858,  985,  573,
  609,  725,  761,  797,  833,  869,  924, 1470, 1501,    0,
    0,    0,    0,    0,    0,    0,    0,    0, 1165,
};
final static short yygindex[] = {                         0,
 1718,   26, 1805,
};
final static int YYTABLESIZE=1935;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         89,
   26,   37,   41,   33,   34,   35,   26,   26,   45,   26,
   23,   56,   26,   26,   38,   68,   23,   23,   26,   23,
   36,   69,   23,   23,   39,   40,   30,    0,   23,    0,
    0,    0,   26,    0,    0,    0,    0,   37,    0,    0,
   28,    0,   23,   19,   25,   20,    0,   37,    0,    0,
   25,   25,    0,   25,   26,    0,   25,   25,   28,   24,
    0,   19,   25,   42,   23,   24,   24,    0,   24,    0,
   37,   24,   24,   38,   37,   37,   25,   24,   37,   37,
   37,   37,   37,   38,   37,    0,    0,    0,    0,    0,
    0,   24,    0,    0,    0,    0,   37,   37,   25,   37,
    0,   37,    0,    0,    0,    0,   38,    0,    0,   40,
   38,   38,    0,   24,   38,   38,   38,   38,   38,   40,
   38,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,   37,   38,   38,    0,   38,    0,   38,    0,   26,
    0,    0,   40,    0,   27,    0,   40,   40,   39,   23,
   40,   40,   40,   40,   40,    0,   40,    0,   39,    0,
    0,   37,    0,    0,    0,    0,    0,   38,   40,   40,
    0,   40,    0,   40,    0,    0,    0,   36,    0,    0,
    0,   39,    0,   25,    0,   39,   39,   36,    0,   39,
   39,   39,   39,   39,    0,   39,    0,   38,   24,    0,
    0,    0,    0,   40,    0,    0,    0,   39,   39,    0,
   39,    0,   39,    0,    0,    0,    0,    0,   36,    0,
    0,   36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   40,   89,    0,   36,    0,   83,   84,
    0,   36,   39,   81,   78,    0,   79,    0,   82,   88,
    0,    0,    0,    0,    0,    0,    2,    3,    4,    5,
    6,    7,    8,    9,   10,   11,   12,   13,   14,   15,
   16,   17,   39,   48,   18,    3,    4,   57,   58,    7,
    8,   38,    0,   54,   41,   21,   22,    0,   61,   17,
    0,   39,   40,    0,    0,   85,   34,    0,    0,    0,
    0,    0,    0,   21,   22,    0,   34,    0,    0,   37,
   37,   37,    0,   46,   37,   37,   37,   37,   37,    0,
   37,    0,    0,    0,    0,   80,    0,    0,    0,    0,
    0,    0,   33,    0,    0,    0,    0,   34,    0,    0,
   34,    0,   33,    0,    0,   38,   38,   38,    0,    0,
   38,   38,   38,   38,   38,   34,   38,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   33,    0,    0,   33,    0,    0,    0,
    0,   40,   40,   40,    0,    0,   40,   40,   40,   40,
   40,   33,   40,    0,    0,    0,   33,   26,   51,   52,
    0,    0,    0,    0,    0,    0,    0,   23,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,   39,   39,    0,    0,   39,   39,   39,   39,   39,
   89,   39,    0,   26,   83,   84,    0,    0,    0,   81,
   78,   25,   79,   23,   82,    0,    0,    0,    0,   36,
   36,   36,    0,    0,    0,    0,   24,   76,    0,   74,
    0,   90,    0,    0,   64,    0,   89,    0,    0,    0,
   83,   84,    0,    0,   64,   81,   78,   25,   79,    0,
   82,   86,   87,    0,   88,    0,    0,    0,    0,    0,
    0,   85,   24,   76,    0,   74,    0,    0,    0,    0,
   63,   64,   64,    0,    0,   64,   64,   64,   64,   64,
   63,   64,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   80,    0,   64,   64,    0,   64,   85,   64,    0,
    0,    0,    0,    0,    0,    0,   65,   63,   63,    0,
    0,   63,   63,   63,   63,   63,   65,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,   64,   63,
   63,    0,   63,    0,   63,    0,    0,    0,   34,   34,
   34,    0,   47,   65,   65,    0,    0,   65,   65,   65,
   65,   65,   47,   65,    0,    0,    0,    0,   64,    0,
    0,    0,    0,    0,   63,   65,   65,    0,   65,    0,
   65,    0,    0,    0,   33,   33,   33,    0,   48,   47,
   47,    0,    0,   47,   47,   47,   47,   47,   48,   47,
    0,    0,    0,    0,   63,    0,    0,    0,    0,    0,
   65,   47,   47,    0,   47,    0,   47,    0,    0,    0,
    0,    0,    0,    0,    0,   48,   48,    0,    0,   48,
   48,   48,   48,   48,    0,   48,    0,    0,    0,    0,
   65,    0,    0,   45,    0,    0,   47,   48,   48,    0,
   48,   72,   48,   45,   73,   75,   77,   86,   87,    0,
   88,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,    0,    0,    0,
    0,    0,   48,    0,   45,    0,   45,   45,   45,    0,
   73,   75,   77,   86,   87,    0,   88,    0,    0,    0,
    0,    0,   45,   45,   49,   45,    0,   45,    0,    0,
    0,    0,   48,    0,   49,    0,   64,   64,   64,    0,
    0,   64,   64,   64,   64,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   50,   49,   49,    0,    0,   49,   49,   49,   49,   49,
   50,   49,   63,   63,   63,    0,    0,   63,   63,   63,
   63,   63,    0,   49,   49,    0,   49,   45,   49,    0,
    0,    0,    0,    0,    0,    0,   51,   50,   50,    0,
    0,   50,   50,   50,   50,   50,   51,   50,   65,   65,
   65,    0,    0,   65,   65,   65,   65,   65,   49,   50,
   50,    0,   50,    0,   50,    0,    0,    0,    0,    0,
    0,    0,   53,   51,   51,    0,    0,   51,   51,   51,
   51,   51,   53,   51,   47,   47,   47,    0,   49,   47,
   47,   47,   47,   47,   50,   51,   51,   46,   51,    0,
   51,    0,    0,    0,    0,    0,    0,   46,   54,   53,
   53,    0,    0,   53,   53,   53,   53,   53,   54,   53,
   48,   48,   48,    0,   50,   48,   48,   48,   48,   48,
   51,   53,   53,    0,   53,    0,   53,    0,   46,    0,
   46,   46,   46,    0,    0,   54,   54,    0,    0,   54,
   54,   54,   54,   54,   55,   54,   46,   46,    0,   46,
   51,   46,    0,   62,   55,    0,   53,   54,   54,    0,
   54,    0,   54,   62,   89,   45,   45,   45,   83,   84,
   45,   45,   45,   81,    0,    0,    0,   56,   82,    0,
    0,    0,    0,    0,    0,   55,   53,   56,   55,    0,
   62,   62,   54,    0,   62,   62,   62,   62,   62,    0,
   62,    0,    0,   55,   55,    0,   55,    0,   55,    0,
    0,   46,   62,   62,   52,   62,    0,   62,   56,    0,
    0,   56,   54,    0,   52,   85,   49,   49,   49,    0,
    0,   49,   49,   49,   49,   49,   56,   56,   35,   56,
    0,   56,    0,    0,    0,    0,    0,   62,   35,    0,
   57,    0,    0,    0,    0,   52,    0,   52,   52,   52,
   57,    0,   50,   50,   50,    0,    0,   50,   50,   50,
   50,   50,    0,   52,   52,    0,   52,   62,   52,   35,
    0,    0,   35,    0,    0,   58,    0,    0,    0,    0,
    0,   57,    0,    0,   57,   58,    0,   35,   51,   51,
   51,    0,   35,   51,   51,   51,   51,   51,    0,   57,
   57,    0,   57,    0,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,    0,    0,   58,
    0,    0,    0,    0,   53,   53,   53,    0,   52,   53,
   53,   53,   53,   53,   58,   58,   59,   58,    0,   58,
    0,    0,    0,    0,    0,    0,   59,    0,    0,   46,
   46,   46,    0,    0,   46,   46,   46,    0,    0,    0,
   54,   54,   54,    0,    0,   54,   54,   54,   54,   54,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
   59,    0,    0,    0,   61,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   61,   59,   59,    0,   59,    0,
   59,   86,   87,    0,   88,    0,   55,   55,   55,    0,
    0,   55,   55,   55,    0,   62,   62,   62,    0,    0,
   62,   62,   62,   62,   62,   61,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   56,
   56,   56,    0,   61,   56,   56,   56,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   44,
    0,    0,    0,   44,   44,    0,    0,    0,   44,   44,
    0,   44,    0,   44,    0,    0,   52,   52,   52,    0,
    0,   52,   52,   52,    0,    0,   44,    0,   44,    0,
   44,    0,    0,    0,   89,    0,    0,    0,   83,   84,
   35,   35,   35,   81,   78,    0,   79,    0,   82,    0,
    0,    0,   57,   57,   57,    0,    0,   57,   57,   57,
   44,   76,    0,   74,    0,    0,    0,    0,    0,   89,
    0,    0,    0,   83,   84,    0,    0,  110,   81,   78,
    0,   79,    0,   82,    0,    0,    0,   58,   58,   58,
   44,    0,   58,   58,   58,   85,   76,   89,   74,    0,
    0,   83,   84,    0,    0,    0,   81,   78,  131,   79,
    0,   82,    0,    0,    0,   28,    0,    0,   19,    0,
   42,    0,    0,    0,   76,   80,   74,    0,    0,    0,
   85,    0,   89,    0,    0,    0,   83,   84,    0,    0,
    0,   81,   78,    0,   79,    0,   82,    0,   59,   59,
   59,    0,    0,   59,   59,   59,    0,    0,   85,   76,
   80,   74,    0,   89,    0,    0,    0,   83,   84,    0,
    0,  138,   81,   78,    0,   79,    0,   82,    0,    0,
    0,    0,    0,    0,   28,    0,    0,   19,   80,   42,
   76,    0,   74,   85,    0,    0,   61,   61,   61,   89,
    0,   27,    0,   83,   84,    0,    0,    0,   81,   78,
    0,   79,    0,   82,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   80,   85,    0,   76,    0,   74,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   44,    0,    0,   44,   44,   44,   44,   44,    0,   44,
    0,    0,   28,    0,   80,   19,    0,   42,    0,    0,
   85,    0,   40,    0,    0,    0,   40,   40,    0,    0,
   27,   40,   40,  108,   40,    0,   40,    0,   73,   75,
   77,   86,   87,    0,   88,    0,    0,    0,    0,   40,
   80,   40,    0,   39,    0,    0,    0,   39,   39,    0,
    0,    0,   39,   39,    0,   39,    0,   39,    0,    0,
    0,    0,    0,   73,   75,   77,   86,   87,    0,   88,
   39,    0,   39,   40,    0,    0,    0,    0,    0,    0,
    0,    0,    3,    4,    5,    6,    7,    8,   27,    0,
   66,   73,   75,   77,   86,   87,   17,   88,    0,    0,
    0,    0,    0,   40,   39,    0,    0,    0,    0,    0,
   21,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  137,    0,    0,    0,   73,   75,   77,   86,
   87,    0,   88,    0,   39,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    3,    4,    5,    6,    7,    8,   73,   75,   77,
   86,   87,    0,   88,    0,   17,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   21,
   22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   73,   75,   77,   86,   87,    0,   88,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    4,  128,  129,    7,    8,    0,    0,    0,   29,    0,
    0,    0,    0,   17,   43,    0,    0,    0,    0,    0,
    0,    0,   59,    0,   63,    0,   65,   21,   22,    0,
    0,    0,    0,    0,   70,   71,   40,   40,   40,   40,
   40,    0,   40,   93,   94,   95,    0,    0,   96,    0,
    0,    0,   97,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,   39,   39,
   39,   39,    0,   39,    0,    0,    0,    0,    0,    0,
  112,  113,  114,  115,  116,  117,  118,  119,  120,  121,
  122,  123,  124,  125,  126,  127,   32,  130,    0,    0,
    0,    0,    0,   47,   49,   50,   53,   55,    0,   60,
   62,    0,   64,    0,    0,  132,    0,    0,    0,    0,
    0,    0,    0,   91,   92,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  136,    0,
   98,    0,   99,    0,  139,  100,  101,    0,  102,    0,
  103,  104,  105,  106,    0,  107,    0,    0,    0,    0,
  109,    0,    0,    0,    0,    0,  111,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  133,  134,  135,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
    0,  275,  275,  258,  259,  260,    0,    0,   40,    0,
   10,  276,    0,    0,  275,  259,   10,   10,    0,   10,
  275,  259,   10,   10,  285,  286,    1,   -1,   10,   -1,
   -1,   -1,    0,   -1,   -1,   -1,   -1,    0,   -1,   -1,
   40,   -1,   10,   43,   44,   45,   -1,   10,   -1,   -1,
   44,   44,   -1,   44,    0,   -1,   44,   44,   40,   59,
   -1,   43,   44,   45,   10,   59,   59,   -1,   59,   -1,
   33,   59,   59,    0,   37,   38,   44,   59,   41,   42,
   43,   44,   45,   10,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   59,   -1,   -1,   -1,   -1,   59,   60,   44,   62,
   -1,   64,   -1,   -1,   -1,   -1,   33,   -1,   -1,    0,
   37,   38,   -1,   59,   41,   42,   43,   44,   45,   10,
   47,   -1,   -1,   -1,   -1,   -1,  126,   -1,   -1,   -1,
   -1,   94,   59,   60,   -1,   62,   -1,   64,   -1,    0,
   -1,   -1,   33,   -1,  126,   -1,   37,   38,    0,   10,
   41,   42,   43,   44,   45,   -1,   47,   -1,   10,   -1,
   -1,  124,   -1,   -1,   -1,   -1,   -1,   94,   59,   60,
   -1,   62,   -1,   64,   -1,   -1,   -1,    0,   -1,   -1,
   -1,   33,   -1,   44,   -1,   37,   38,   10,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,  124,   59,   -1,
   -1,   -1,   -1,   94,   -1,   -1,   -1,   59,   60,   -1,
   62,   -1,   64,   -1,   -1,   -1,   -1,   -1,   41,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  124,   33,   -1,   59,   -1,   37,   38,
   -1,   64,   94,   42,   43,   -1,   45,   -1,   47,  283,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  124,  260,  274,  257,  258,  259,  260,  261,
  262,  275,   -1,  276,  275,  285,  286,   -1,  276,  271,
   -1,  285,  286,   -1,   -1,   94,    0,   -1,   -1,   -1,
   -1,   -1,   -1,  285,  286,   -1,   10,   -1,   -1,  272,
  273,  274,   -1,  259,  277,  278,  279,  280,  281,   -1,
  283,   -1,   -1,   -1,   -1,  124,   -1,   -1,   -1,   -1,
   -1,   -1,    0,   -1,   -1,   -1,   -1,   41,   -1,   -1,
   44,   -1,   10,   -1,   -1,  272,  273,  274,   -1,   -1,
  277,  278,  279,  280,  281,   59,  283,   -1,   -1,   -1,
   64,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,  272,  273,  274,   -1,   -1,  277,  278,  279,  280,
  281,   59,  283,   -1,   -1,   -1,   64,    0,  259,  260,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   10,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,  274,   -1,   -1,  277,  278,  279,  280,  281,
   33,  283,   -1,    0,   37,   38,   -1,   -1,   -1,   42,
   43,   44,   45,   10,   47,   -1,   -1,   -1,   -1,  272,
  273,  274,   -1,   -1,   -1,   -1,   59,   60,   -1,   62,
   -1,   64,   -1,   -1,    0,   -1,   33,   -1,   -1,   -1,
   37,   38,   -1,   -1,   10,   42,   43,   44,   45,   -1,
   47,  280,  281,   -1,  283,   -1,   -1,   -1,   -1,   -1,
   -1,   94,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
    0,   37,   38,   -1,   -1,   41,   42,   43,   44,   45,
   10,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  124,   -1,   59,   60,   -1,   62,   94,   64,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,    0,   37,   38,   -1,
   -1,   41,   42,   43,   44,   45,   10,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  124,   94,   59,
   60,   -1,   62,   -1,   64,   -1,   -1,   -1,  272,  273,
  274,   -1,    0,   37,   38,   -1,   -1,   41,   42,   43,
   44,   45,   10,   47,   -1,   -1,   -1,   -1,  124,   -1,
   -1,   -1,   -1,   -1,   94,   59,   60,   -1,   62,   -1,
   64,   -1,   -1,   -1,  272,  273,  274,   -1,    0,   37,
   38,   -1,   -1,   41,   42,   43,   44,   45,   10,   47,
   -1,   -1,   -1,   -1,  124,   -1,   -1,   -1,   -1,   -1,
   94,   59,   60,   -1,   62,   -1,   64,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   37,   38,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,   -1,
  124,   -1,   -1,    0,   -1,   -1,   94,   59,   60,   -1,
   62,  274,   64,   10,  277,  278,  279,  280,  281,   -1,
  283,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  124,   -1,   -1,   -1,
   -1,   -1,   94,   -1,   41,   -1,   43,   44,   45,   -1,
  277,  278,  279,  280,  281,   -1,  283,   -1,   -1,   -1,
   -1,   -1,   59,   60,    0,   62,   -1,   64,   -1,   -1,
   -1,   -1,  124,   -1,   10,   -1,  272,  273,  274,   -1,
   -1,  277,  278,  279,  280,  281,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
    0,   37,   38,   -1,   -1,   41,   42,   43,   44,   45,
   10,   47,  272,  273,  274,   -1,   -1,  277,  278,  279,
  280,  281,   -1,   59,   60,   -1,   62,  124,   64,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,    0,   37,   38,   -1,
   -1,   41,   42,   43,   44,   45,   10,   47,  272,  273,
  274,   -1,   -1,  277,  278,  279,  280,  281,   94,   59,
   60,   -1,   62,   -1,   64,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,    0,   37,   38,   -1,   -1,   41,   42,   43,
   44,   45,   10,   47,  272,  273,  274,   -1,  124,  277,
  278,  279,  280,  281,   94,   59,   60,    0,   62,   -1,
   64,   -1,   -1,   -1,   -1,   -1,   -1,   10,    0,   37,
   38,   -1,   -1,   41,   42,   43,   44,   45,   10,   47,
  272,  273,  274,   -1,  124,  277,  278,  279,  280,  281,
   94,   59,   60,   -1,   62,   -1,   64,   -1,   41,   -1,
   43,   44,   45,   -1,   -1,   37,   38,   -1,   -1,   41,
   42,   43,   44,   45,    0,   47,   59,   60,   -1,   62,
  124,   64,   -1,    0,   10,   -1,   94,   59,   60,   -1,
   62,   -1,   64,   10,   33,  272,  273,  274,   37,   38,
  277,  278,  279,   42,   -1,   -1,   -1,    0,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   41,  124,   10,   44,   -1,
   37,   38,   94,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   59,   60,   -1,   62,   -1,   64,   -1,
   -1,  124,   59,   60,    0,   62,   -1,   64,   41,   -1,
   -1,   44,  124,   -1,   10,   94,  272,  273,  274,   -1,
   -1,  277,  278,  279,  280,  281,   59,   60,    0,   62,
   -1,   64,   -1,   -1,   -1,   -1,   -1,   94,   10,   -1,
    0,   -1,   -1,   -1,   -1,   41,   -1,   43,   44,   45,
   10,   -1,  272,  273,  274,   -1,   -1,  277,  278,  279,
  280,  281,   -1,   59,   60,   -1,   62,  124,   64,   41,
   -1,   -1,   44,   -1,   -1,    0,   -1,   -1,   -1,   -1,
   -1,   41,   -1,   -1,   44,   10,   -1,   59,  272,  273,
  274,   -1,   64,  277,  278,  279,  280,  281,   -1,   59,
   60,   -1,   62,   -1,   64,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,
   -1,   -1,   -1,   -1,  272,  273,  274,   -1,  124,  277,
  278,  279,  280,  281,   59,   60,    0,   62,   -1,   64,
   -1,   -1,   -1,   -1,   -1,   -1,   10,   -1,   -1,  272,
  273,  274,   -1,   -1,  277,  278,  279,   -1,   -1,   -1,
  272,  273,  274,   -1,   -1,  277,  278,  279,  280,  281,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,
   44,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   10,   59,   60,   -1,   62,   -1,
   64,  280,  281,   -1,  283,   -1,  272,  273,  274,   -1,
   -1,  277,  278,  279,   -1,  272,  273,  274,   -1,   -1,
  277,  278,  279,  280,  281,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,  274,   -1,   59,  277,  278,  279,   -1,   64,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   33,
   -1,   -1,   -1,   37,   38,   -1,   -1,   -1,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,  272,  273,  274,   -1,
   -1,  277,  278,  279,   -1,   -1,   60,   -1,   62,   -1,
   64,   -1,   -1,   -1,   33,   -1,   -1,   -1,   37,   38,
  272,  273,  274,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,  272,  273,  274,   -1,   -1,  277,  278,  279,
   94,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   33,
   -1,   -1,   -1,   37,   38,   -1,   -1,   41,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,  272,  273,  274,
  124,   -1,  277,  278,  279,   94,   60,   33,   62,   -1,
   -1,   37,   38,   -1,   -1,   -1,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   -1,   40,   -1,   -1,   43,   -1,
   45,   -1,   -1,   -1,   60,  124,   62,   -1,   -1,   -1,
   94,   -1,   33,   -1,   -1,   -1,   37,   38,   -1,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   -1,  272,  273,
  274,   -1,   -1,  277,  278,  279,   -1,   -1,   94,   60,
  124,   62,   -1,   33,   -1,   -1,   -1,   37,   38,   -1,
   -1,   41,   42,   43,   -1,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   43,  124,   45,
   60,   -1,   62,   94,   -1,   -1,  272,  273,  274,   33,
   -1,  126,   -1,   37,   38,   -1,   -1,   -1,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  124,   94,   -1,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  274,   -1,   -1,  277,  278,  279,  280,  281,   -1,  283,
   -1,   -1,   40,   -1,  124,   43,   -1,   45,   -1,   -1,
   94,   -1,   33,   -1,   -1,   -1,   37,   38,   -1,   -1,
  126,   42,   43,  272,   45,   -1,   47,   -1,  277,  278,
  279,  280,  281,   -1,  283,   -1,   -1,   -1,   -1,   60,
  124,   62,   -1,   33,   -1,   -1,   -1,   37,   38,   -1,
   -1,   -1,   42,   43,   -1,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,  277,  278,  279,  280,  281,   -1,  283,
   60,   -1,   62,   94,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  126,   -1,
  265,  277,  278,  279,  280,  281,  271,  283,   -1,   -1,
   -1,   -1,   -1,  124,   94,   -1,   -1,   -1,   -1,   -1,
  285,  286,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,   -1,   -1,   -1,  277,  278,  279,  280,
  281,   -1,  283,   -1,  124,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  277,  278,  279,
  280,  281,   -1,  283,   -1,  271,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  285,
  286,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,  279,  280,  281,   -1,  283,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,   -1,    1,   -1,
   -1,   -1,   -1,  271,    7,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   15,   -1,   17,   -1,   19,  285,  286,   -1,
   -1,   -1,   -1,   -1,   27,   28,  277,  278,  279,  280,
  281,   -1,  283,   36,   37,   38,   -1,   -1,   41,   -1,
   -1,   -1,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,  279,
  280,  281,   -1,  283,   -1,   -1,   -1,   -1,   -1,   -1,
   73,   74,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,   85,   86,   87,   88,    2,   90,   -1,   -1,
   -1,   -1,   -1,    9,   10,   11,   12,   13,   -1,   15,
   16,   -1,   18,   -1,   -1,  108,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   29,   30,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  131,   -1,
   46,   -1,   48,   -1,  137,   51,   52,   -1,   54,   -1,
   56,   57,   58,   59,   -1,   61,   -1,   -1,   -1,   -1,
   66,   -1,   -1,   -1,   -1,   -1,   72,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  128,  129,  130,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=286;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'!'",null,null,null,"'%'","'&'",null,"'('","')'","'*'",
"'+'","','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,
null,"';'","'<'",null,"'>'",null,"'@'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'^'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'|'",null,"'~'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"NUMBER","UNDEF","VAR",
"CONST","FUNC1","FUNC2","LISTCONST","LISTVAR","HELP","CLEAR","FORMAT","ECHO",
"UNIT","UFORMAT","IF","THEN","ELSE","RADIX","ASGNOP","STRING","EQUAL","GE","LE",
"SHL","SHR","UNARY","POWER","FUNC","INC","DEC",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list eol",
"list : list LISTCONST eol",
"list : list LISTVAR eol",
"list : list ECHO STRING eol",
"list : list CLEAR VAR eol",
"list : list CLEAR CONST eol",
"list : list CLEAR eol",
"list : list LISTCONST VAR eol",
"list : list LISTVAR CONST eol",
"list : list FORMAT STRING eol",
"list : list FORMAT eol",
"list : list UFORMAT STRING eol",
"list : list UFORMAT eol",
"list : list HELP eol",
"list : list '-' HELP eol",
"list : list RADIX eol",
"list : list UNIT eol",
"list : list UNIT VAR eol",
"list : list UNIT CONST eol",
"list : list UNIT expr eol",
"list : list asgn eol",
"list : list expr '@' VAR eol",
"list : list expr '@' CONST eol",
"list : list expr '@' expr eol",
"list : list expr RADIX eol",
"list : list expr eol",
"list : list error eol",
"eol : '\\n'",
"eol : ';'",
"eol : ','",
"eol : '\\000'",
"asgn : VAR ASGNOP expr",
"asgn : UNDEF ASGNOP expr",
"asgn : CONST ASGNOP expr",
"asgn : NUMBER ASGNOP expr",
"expr : NUMBER",
"expr : UNDEF",
"expr : CONST",
"expr : VAR",
"expr : NUMBER VAR",
"expr : NUMBER CONST",
"expr : NUMBER UNDEF",
"expr : asgn",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr '%' expr",
"expr : expr '&' expr",
"expr : expr '^' expr",
"expr : expr '|' expr",
"expr : expr SHL expr",
"expr : expr SHR expr",
"expr : expr EQUAL expr",
"expr : expr '>' expr",
"expr : expr GE expr",
"expr : expr '<' expr",
"expr : expr LE expr",
"expr : expr '!'",
"expr : IF expr THEN expr ELSE expr",
"expr : expr POWER expr",
"expr : '-' expr",
"expr : '+' expr",
"expr : '~' expr",
"expr : FUNC1 expr",
"expr : FUNC2 '(' expr ',' expr ')'",
"expr : VAR INC",
"expr : VAR DEC",
"expr : INC VAR",
"expr : DEC VAR",
"expr : '(' expr ')'",
};

//#line 140 "scalc.y"

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
                       " Version 0.01)\n" +
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
//#line 1592 "Parser.java"
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
throws JcalcException,JcalcRuntimeException
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
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 28 "scalc.y"
{ ident.listId(CONST);  }
break;
case 4:
//#line 29 "scalc.y"
{ ident.listId(VAR);	}
break;
case 5:
//#line 33 "scalc.y"
{ result.append( val_peek(1).string + "\n" );	}
break;
case 6:
//#line 34 "scalc.y"
{ val_peek(1).id.type = UNDEF;	}
break;
case 7:
//#line 35 "scalc.y"
{ val_peek(1).id.type = UNDEF;	}
break;
case 8:
//#line 36 "scalc.y"
{ ident.clearVarAll();		}
break;
case 9:
//#line 37 "scalc.y"
{ val_peek(1).id.type = CONST;	}
break;
case 10:
//#line 38 "scalc.y"
{ val_peek(1).id.type = VAR;	}
break;
case 11:
//#line 39 "scalc.y"
{ PRINT_FORMAT = val_peek(1).string;	}
break;
case 12:
//#line 40 "scalc.y"
{ result.append( "format \"" + PRINT_FORMAT.toString() + "\"\n" ); }
break;
case 13:
//#line 41 "scalc.y"
{ UNIT_FORMAT = val_peek(1).string;	}
break;
case 14:
//#line 42 "scalc.y"
{ result.append( "format \"" + UNIT_FORMAT.toString() + "\"\n" ); }
break;
case 15:
//#line 44 "scalc.y"
{ help();		}
break;
case 16:
//#line 45 "scalc.y"
{ help();		}
break;
case 17:
//#line 46 "scalc.y"
{ changeORadix( val_peek(1).radix ); 		}
break;
case 18:
//#line 47 "scalc.y"
{ printUnit();		}
break;
case 19:
//#line 48 "scalc.y"
{
        if( val_peek(1).id.v == 0.0 ) {
            execerror( "Zero is Illegal Unit.", "" );
        }
        unit = val_peek(1).id.v;
        unitName = val_peek(1).id.name;
        printUnit();
      }
break;
case 20:
//#line 56 "scalc.y"
{
        if( val_peek(1).id.v == 0.0 ) {
            execerror( "Zero is Illegal Unit.", "" );
        }
        unit = val_peek(1).id.v;
        unitName = val_peek(1).id.name;
        printUnit();
      }
break;
case 21:
//#line 64 "scalc.y"
{
        if( val_peek(1).val == 0.0 ) {
            execerror( "Zero is Illegal Unit.", "" );
        }
        unit = val_peek(1).val;
        unitName = new StringBuilder("");
        printUnit();
       }
break;
case 22:
//#line 72 "scalc.y"
{ printval(oradix, val_peek(1).val, unit, unitName); setPrevious(val_peek(1).val); }
break;
case 23:
//#line 73 "scalc.y"
{ printval(oradix,val_peek(3).val,val_peek(1).id.v,val_peek(1).id.name); setPrevious(val_peek(3).val);	}
break;
case 24:
//#line 74 "scalc.y"
{ printval(oradix,val_peek(3).val,val_peek(1).id.v,val_peek(1).id.name); setPrevious(val_peek(3).val);	}
break;
case 25:
//#line 75 "scalc.y"
{ printval(oradix,val_peek(3).val,val_peek(1).val,new StringBuilder("") ); setPrevious(val_peek(3).val);	}
break;
case 26:
//#line 76 "scalc.y"
{ printval(val_peek(1).radix, val_peek(2).val, unit, unitName); setPrevious(val_peek(2).val);	}
break;
case 27:
//#line 77 "scalc.y"
{ printval(oradix, val_peek(1).val, unit, unitName); setPrevious(val_peek(1).val);	}
break;
case 28:
//#line 78 "scalc.y"
{ yyerror("");		}
break;
case 33:
//#line 88 "scalc.y"
{ yyval.val = asgnop(val_peek(1).op, val_peek(2).id, val_peek(0).val);  }
break;
case 34:
//#line 90 "scalc.y"
{ yyval.val = asgnop(val_peek(1).op, val_peek(2).id, val_peek(0).val); val_peek(2).id.type = VAR; }
break;
case 35:
//#line 92 "scalc.y"
{ execerror( "const cannot be changed.", val_peek(2).id.name.toString() ); }
break;
case 36:
//#line 94 "scalc.y"
{ execerror( "const cannot be changed.", "" ); }
break;
case 37:
//#line 97 "scalc.y"
{ yyval.val = val_peek(0).val;	}
break;
case 38:
//#line 98 "scalc.y"
{ execerror( "variable not defined.", val_peek(0).id.name.toString() ); }
break;
case 39:
//#line 99 "scalc.y"
{ yyval.val = val_peek(0).id.v;	}
break;
case 40:
//#line 100 "scalc.y"
{ yyval.val = val_peek(0).id.v;	}
break;
case 41:
//#line 101 "scalc.y"
{ yyval.val = val_peek(1).val * val_peek(0).id.v;	}
break;
case 42:
//#line 102 "scalc.y"
{ yyval.val = val_peek(1).val * val_peek(0).id.v;	}
break;
case 43:
//#line 103 "scalc.y"
{ execerror( "variable not defined.", val_peek(0).id.name.toString() ); }
break;
case 44:
//#line 104 "scalc.y"
{ yyval.val = val_peek(0).val;	}
break;
case 45:
//#line 105 "scalc.y"
{ yyval.val = val_peek(2).val + val_peek(0).val; }
break;
case 46:
//#line 106 "scalc.y"
{ yyval.val = val_peek(2).val - val_peek(0).val; }
break;
case 47:
//#line 107 "scalc.y"
{ yyval.val = val_peek(2).val * val_peek(0).val; }
break;
case 48:
//#line 108 "scalc.y"
{
		if( val_peek(0).val == 0.0 ) {
			execerror("division by zero", "" );
        }
		yyval.val = val_peek(2).val / val_peek(0).val; }
break;
case 49:
//#line 113 "scalc.y"
{ yyval.val = fmod(val_peek(2).val, val_peek(0).val);	}
break;
case 50:
//#line 114 "scalc.y"
{ yyval.val =(double)((int)val_peek(2).val & (int)val_peek(0).val);}
break;
case 51:
//#line 115 "scalc.y"
{ yyval.val =(double)((int)val_peek(2).val ^ (int)val_peek(0).val);}
break;
case 52:
//#line 116 "scalc.y"
{ yyval.val =(double)((int)val_peek(2).val | (int)val_peek(0).val);}
break;
case 53:
//#line 117 "scalc.y"
{ yyval.val =(double)((int)val_peek(2).val << (int)val_peek(0).val);}
break;
case 54:
//#line 118 "scalc.y"
{ yyval.val =(double)((int)val_peek(2).val >> (int)val_peek(0).val);}
break;
case 55:
//#line 119 "scalc.y"
{ yyval.val =(((double)val_peek(2).val == (double)val_peek(0).val) ? 1.0 : 0.0);}
break;
case 56:
//#line 120 "scalc.y"
{ yyval.val =(((double)val_peek(2).val >  (double)val_peek(0).val) ? 1.0 : 0.0 );}
break;
case 57:
//#line 121 "scalc.y"
{ yyval.val =(((double)val_peek(2).val >= (double)val_peek(0).val) ? 1.0 : 0.0 );}
break;
case 58:
//#line 122 "scalc.y"
{ yyval.val =(((double)val_peek(2).val <  (double)val_peek(0).val) ? 1.0 : 0.0 );}
break;
case 59:
//#line 123 "scalc.y"
{ yyval.val =(((double)val_peek(2).val <= (double)val_peek(0).val) ? 1.0 : 0.0 );}
break;
case 60:
//#line 124 "scalc.y"
{ yyval.val = fact( (int)val_peek(1).val );	}
break;
case 61:
//#line 125 "scalc.y"
{ yyval.val = ((int)(val_peek(4).val) == 0 ? (val_peek(0).val) : (val_peek(2).val)); }
break;
case 62:
//#line 126 "scalc.y"
{ yyval.val = Math.pow(val_peek(2).val, val_peek(0).val);	}
break;
case 63:
//#line 127 "scalc.y"
{ yyval.val = -val_peek(0).val;	}
break;
case 64:
//#line 128 "scalc.y"
{ yyval.val = val_peek(0).val;	}
break;
case 65:
//#line 129 "scalc.y"
{ yyval.val = (double)(~(long)val_peek(0).val);	}
break;
case 66:
//#line 130 "scalc.y"
{ yyval.val = calcFunc( val_peek(1).id.name, val_peek(0).val ); }
break;
case 67:
//#line 131 "scalc.y"
{ yyval.val = calcFunc( val_peek(5).id.name, val_peek(3).val, val_peek(1).val );	}
break;
case 68:
//#line 132 "scalc.y"
{ yyval.val = val_peek(1).id.v; val_peek(1).id.v += 1.0; }
break;
case 69:
//#line 133 "scalc.y"
{ yyval.val = val_peek(1).id.v; val_peek(1).id.v -= 1.0; }
break;
case 70:
//#line 134 "scalc.y"
{ yyval.val = val_peek(0).id.v += 1.0;	}
break;
case 71:
//#line 135 "scalc.y"
{ yyval.val = val_peek(0).id.v -= 1.0;	}
break;
case 72:
//#line 136 "scalc.y"
{ yyval.val = val_peek(1).val;	}
break;
//#line 2030 "Parser.java"
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
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
