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

//run() method is called first
    public void run() throws Exception {
        c = getArrayList(incoming);
        createDictionary();
        s=s+"actor user #black\n";
        s=s+"user" + " -> " + claassName + " : " + function + "\n";
        s=s+"activate " + classMap.get(function) + "\n";
        generateGrammar(function);
        s=s+"@enduml";
        draw(s);
        System.out.println("Code: \n" + s);
    }

  

//same as class diagram getting the array list of compliation unit type
    private ArrayList<CompilationUnit> getArrayList(String incoming)
            throws Exception {
        File f1 = new File(incoming);
        ArrayList<CompilationUnit> list = new ArrayList<CompilationUnit>();
        for (final File f : f1.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".java")) {
                FileInputStream fileInput = new FileInputStream(f);
                CompilationUnit compilation;
                try {
                    compilation = JavaParser.parse(fileInput);
                    list.add(compilation);
                } finally {
                    fileInput.close();
                }
            }
        }
        return list;
    }

   

   

}
