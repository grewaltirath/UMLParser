import java.io.*;
import java.util.*;


//importing the javaparser library
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;

public class ParseSequenceEngine{

    final String incomingPath;
    final String outgoingPath;
    final String incomingFunctionName;
    final String incomingClassName;
    String pumlCode;

    HashMap<String, String> mapMethodClass;
    ArrayList<CompilationUnit> cuArray;
    HashMap<String, ArrayList<MethodCallExpr>> mapMethodCalls;


//Initialize the constructor

    ParseSequenceEngine(String incomingPath, String incomingClassName, String incomingFunctionName,
            String outgoingFile) {
        this.incomingPath = inPath;
        this.outgoingPath = inPath + "\\" + outFile + ".png";
        this.incomingClassName = incomingClassName;
        this.incomingFunctionName = incomingFunctionName;
        mapMethodClass = new HashMap<String, String>();
        mapMethodCalls = new HashMap<String, ArrayList<MethodCallExpr>>();
        pumlCode = "@startuml\n";
    }
}