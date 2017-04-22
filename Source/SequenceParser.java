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

    private void generateGrammar(String functionCaller) {

        for (MethodCallExpr methodCall : methodMap.get(functionCaller)) {
            String cc = classMap.get(functionCaller);
            String functionCallee = methodCall.getName();
            String calleeClass = classMap.get(functionCallee);
            if (classMap.containsKey(functionCallee)) {
                s=s+ cc + " -> " + calleeClass + " : "
                        + methodCall.toStringWithoutComments() + "\n";
                s=s+"activate " + calleeClass + "\n";
                generateGrammar(functionCallee);
                s=s+calleeClass + " -->> " + cc + "\n";
                s=s+"deactivate " + calleeClass + "\n";
            }
        }
    }

    private void createDictionary() {
        for (CompilationUnit compilation : c) {
            String nameOfClass = "";
            List<TypeDeclaration> l = compilation.getTypes();
            for (Node n : l) {
                ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) n;
                nameOfClass = cid.getName();
                for (BodyDeclaration body : ((TypeDeclaration) cid)
                        .getMembers()) {
                    //checking for insatnce of MethodDeclaration
                    if (body instanceof MethodDeclaration) {
                        MethodDeclaration dec = (MethodDeclaration) body;
                        ArrayList<MethodCallExpr> list3 = new ArrayList<MethodCallExpr>();
                        for (Object block : dec.getChildrenNodes()) {
                            //checking for insatnce of Block declaration
                            if (block instanceof BlockStmt) {
                                for (Object expr : ((Node) block)
                                        .getChildrenNodes()) {
                                    //checking for insatnce of Expression statement
                                    if (expr instanceof ExpressionStmt) {
                                        if (((ExpressionStmt) (expr))
                                                .getExpression() instanceof MethodCallExpr) {
                                            list3.add(
                                                    (MethodCallExpr) (((ExpressionStmt) (expr))
                                                            .getExpression()));
                                        }
                                    }
                                }
                            }
                        }
                        methodMap.put(dec.getName(), list3);
                        classMap.put(dec.getName(), nameOfClass);
                    }
                }
            }
        }
        //printMaps();
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
