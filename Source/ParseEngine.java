import java.io.*;
import java.util.*;
import java.lang.*;


//importing javaparser library
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class ParseEngine {
    final String incomingPath;
    final String outgoingPath;
    HashMap<String, Boolean> map;
    HashMap<String, String> mapClassConn;
    String yumlCode;
    ArrayList<CompilationUnit> cuArray;

//Initializing the constructor
    ParseEngine(String incomingPath, String outgoingFile) {
        this.incomingPath = incomingPath;
        this.outgoingPath = incomingPath + "\\" + outgoingFile + ".png";
        map = new HashMap<String, Boolean>();
        mapClassConn = new HashMap<String, String>();
        yumlCode = "";
    }

//Returns an array list of type Compilation Unit
    private ArrayList<CompilationUnit> getArr(String incomingPath)
            throws Exception {
                File f1 = new File(incomingPath);
        ArrayList<CompilationUnit> cu = new ArrayList<CompilationUnit>();
        for (final File f : folder.listFiles()) {
            //check for java files inside the folder
            if (f.isFile() && f.getName().endsWith(".java")) {
                //Reading the java file
                FileInputStream in = new FileInputStream(f);
                CompilationUnit c;
                try {
                    //calling the Java parser library to parse the code in the file
                    c = JavaParser.parse(in);
                    cu.add(c);
                } finally {
                    in.close();
                }
            }
        }
        //returning the compilation unit array list
        return cu;
        
    }
//buildMap() function
      private void buildMap(ArrayList<CompilationUnit> cu1) {
        for (CompilationUnit cu : cu1) {
            List<TypeDeclaration> cl = cu.getTypes();
            for (Node n : cl) {
                ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) n;
                map.put(coi.getName(), coi.isInterface()); 
                //put to the map
                                                           
            }
        }
    }


    public void start() throws Exception {
        cu = getArr(inPath);
        //getting the compilation unit array list
        buildMap(cu);
        //build map
        for (CompilationUnit c : cu)
        {
            yumlCode += parser(c);
        }
        yumlCode += parseAdditions();
        yumlCode = yumlCodeUniquer(yumlCode);
        System.out.println("Code: " + yumlCode);
        GenerateDiagram.generatePNG(yumlCode, outgoingPath);
        //generate diagram as PNG file
    }
    }

//parseAdditions
    private String parseAdditions() {
        String result = "";
        Set<String> keys = mapClassConn.keySet(); // get all keys
        
    }