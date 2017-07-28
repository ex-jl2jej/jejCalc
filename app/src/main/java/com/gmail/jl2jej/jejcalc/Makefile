all:  Parser.class

Parser.class : Parser.java
	javac Parser.java

Parser.java : scalc.y
	bjyacc -J -Jsemantic=Lval -Jthrows=JcalcException,JcalcRuntimeException -Jnorun -Jnoconstruct scalc.y

