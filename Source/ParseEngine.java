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
    ArrayList<CompilationUnit> list;

//Initializing the constructor
    ParseEngine(String incomingPath, String outgoingFile) {
        this.incomingPath = incomingPath;
        this.outgoingPath = incomingPath + "\\" + outgoingFile + ".png";
        map = new HashMap<String, Boolean>();
        mapClassConn = new HashMap<String, String>();
        yumlCode = "";
    }

//Returns an array list of type Compilation Unit
    private ArrayList<CompilationUnit> getArrayList(String incomingPath)
            throws Exception {
                File f1 = new File(incomingPath);
        ArrayList<CompilationUnit> cu = new ArrayList<CompilationUnit>();
        //creating arraylist of type Compilation Unit
        for (final File 2 : folder.listFiles()) {
            //check for java files inside the folder
            if (f2.isFile() && f2.getName().endsWith(".java")) {
                //Checking for files and getting all the java files
                FileInputStream input = new FileInputStream(f2);
                CompilationUnit c;
                try {
                    //calling the Java parser library to parse the code in the file
                    c = JavaParser.parse(input);
                    cu.add(c);//adding to the arraylist
                } finally {
                    input.close();//close the FileInput Stream
                }
            }
        }
        //returning the compilation unit type array list
        return cu;
        
    }
//createDict() function
      private void createDict(ArrayList<CompilationUnit> list1) {
        for (CompilationUnit c : list1) {
            List<TypeDeclaration> list2 = c.getTypes(); //return the list of types declared in this compilation unit.
            for (Node n : list2) {
                ClassOrInterfaceDeclaration var = (ClassOrInterfaceDeclaration) n;
                map.put(var.getName(), var.isInterface()); 
                //put to the map the name and boolean value if it is interface or not
                                                           
            }
        }
    }


    public void start() throws Exception {
        list = getArrayList(incomingPath);
        //getting the compilation unit array list
        createDict(list);
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
        for (String i : keys) {
            String[] classes = i.split("-");
            if (map.get(classes[0]))
                result += "[<<interface>>;" + classes[0] + "]";
            else
                result += "[" + classes[0] + "]";
            result += mapClassConn.get(i); // Add connection
            if (map.get(classes[1]))
                result += "[<<interface>>;" + classes[1] + "]";
            else
                result += "[" + classes[1] + "]";
            result += ",";
        }
        return result;
    }
    private String yumlCodeUniquer(String code) {
        String[] codeLines = code.split(",");
        String[] uniqueCodeLines = new LinkedHashSet<String>(
                Arrays.asList(codeLines)).toArray(new String[0]);
        String result = String.join(",", uniqueCodeLines);
        return result;
    }