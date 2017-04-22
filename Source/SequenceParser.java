package umlparser.umlparser;

import java.io.*;
import java.util.*;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;

import net.sourceforge.plantuml.SourceStringReader;

public class SequenceParser {
    HashMap<String, String> classMap;
    ArrayList<CompilationUnit> c;
    HashMap<String, ArrayList<MethodCallExpr>> methodMap;
    final String incoming;
    final String outgoing;
    final String function;
    final String claassName;
    String s;

//constructor for SequenceParser
    SequenceParser(String incoming, String claassName, String function,
            String fileName) {
        this.incoming = incoming;
        this.outgoing = incoming + "\\" + fileName + ".png";
        this.claassName = claassName;
        this.function = function;
        classMap = new HashMap<String, String>();
        methodMap = new HashMap<String, ArrayList<MethodCallExpr>>();
        s = "@startuml\n";
    }



   

}
