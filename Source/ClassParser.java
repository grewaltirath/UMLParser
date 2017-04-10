import java.io.*;
import java.util.*;
import java.lang.*;


//importing javaparser library
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class ClassParser {
    final String incomingPath;
    final String outgoingPath;
    HashMap<String, Boolean> map;
    HashMap<String, String> cc;
    String s;
    ArrayList<CompilationUnit> list;

//Initializing the constructor
    ClassParser(String incomingPath, String outgoingFile) {
        this.incomingPath = incomingPath;
        this.outgoingPath = incomingPath + "\\" + outgoingFile + ".png";
        map = new HashMap<String, Boolean>();
        cc = new HashMap<String, String>();
        yumlCode = "";
    }

//Returns an array list of type Compilation Unit
    private ArrayList<CompilationUnit> getArrayList(String incomingPath)
            throws Exception {
                File f1 = new File(incomingPath);
        ArrayList<CompilationUnit> cu = new ArrayList<CompilationUnit>();
        //creating arraylist of type Compilation Unit
        for (final File f2 : folder.listFiles()) {
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
        for (CompilationUnit c : list)
        {
            s += javaCodeParserr(c);
        }
        s += add();
        s = yUML(s);
        System.out.println("Code: " + s);
        GenerateDiagram.generatePNG(s, outgoingPath);
        //generate diagram as PNG file
    }
    


private String javaCodeParserr(CompilationUnit cu) {
        String ans = "";
        String name = "";
        String classShortName = "";
        String functions = "";
        String variables = "";
        String additions = ",";

        ArrayList<String> createPublic = new ArrayList<String>();
        List<TypeDeclaration> list3 = cu.getTypes(); 
        Node node = list3.get(0); //considering that there are no more classes nested within

       
        ClassOrInterfaceDeclaration cl = (ClassOrInterfaceDeclaration) node;
        //getting the name of the class
        if (cl.isInterface()) {
            //if it is an interface, append interface 
            name = "[" + "<<interface>>;";
        } else {
            //class
            name = "[";
        }
        //get name
        name += cl.getName();
        classShortName = cl.getName();

        // for the constructor method
        boolean secondParameter = false;
        for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
            // check if instance of ConstructorDeclaration

            if (bd instanceof ConstructorDeclaration) {
                ConstructorDeclaration cd = ((ConstructorDeclaration) bd);
                if (cd.getDeclarationAsString().startsWith("public")
                        && !cl.isInterface()) {
                    //check if it starts with public and is not a interface
                    if (secondParameter)
                        functions += ";";
                    functions += "+ " + cd.getName() + "(";
                        //get name of the method
                    for (Object gcn : cd.getChildrenNodes()) {
                        //for teh method parameters
                        if (gcn instanceof Parameter) {
                            Parameter paramCast = (Parameter) gcn;
                            String paramClass = paramCast.getType().toString();
                            String paramName = paramCast.getChildrenNodes()
                                    .get(0).toString();
                            functions += paramName + " : " + paramClass;
                            if (map.containsKey(paramClass)
                                    && !map.get(classShortName)) {
                                additions += "[" + classShortName
                                        + "] uses -.->";
                                if (map.get(paramClass))
                                    additions += "[<<interface>>;" + paramClass
                                            + "]";
                                else
                                    additions += "[" + paramClass + "]";
                            }
                            additions += ",";
                        }
                    }
                    functions += ")";
                    secondParameter = true;
                }
            }
        }

        //Parsing the java code for methods
for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
            if (bd instanceof MethodDeclaration) {
                //check instance of MethodDeclaration
                MethodDeclaration md = ((MethodDeclaration) bd);
            
                if (md.getDeclarationAsString().startsWith("public")
                        && !cl.isInterface()) {
                    //check for public methods and not interfaces
                    if (md.getName().startsWith("set")
                            || md.getName().startsWith("get")) {
                        String varName = md.getName().substring(3);
                        makeFieldPublic.add(varName.toLowerCase());
                        //for setters and getters
                    } else {
                        if (secondParameter)
                            functions += ";";
                        functions += "+ " + md.getName() + "(";
                        for (Object gcn : md.getChildrenNodes()) {
                            if (gcn instanceof Parameter) {
                                Parameter paramCast = (Parameter) gcn;
                                String paramClass = paramCast.getType()
                                        .toString();
                                String paramName = paramCast.getChildrenNodes()
                                        .get(0).toString();
                                functions += paramName + " : " + paramClass;
                                if (map.containsKey(paramClass)
                                        && !map.get(classShortName)) {
                                    additions += "[" + classShortName
                                            + "] uses -.->";
                                    if (map.get(paramClass))
                                        additions += "[<<interface>>;"
                                                + paramClass + "]";
                                    else
                                        additions += "[" + paramClass + "]";
                                }
                                additions += ",";
                            } else {
                                String methodBody[] = gcn.toString().split(" ");
                                for (String foo : methodBody) {
                                    if (map.containsKey(foo)
                                            && !map.get(classShortName)) {
                                        additions += "[" + classShortName
                                                + "] uses -.->";
                                        if (map.get(foo))
                                            additions += "[<<interface>>;" + foo
                                                    + "]";
                                        else
                                            additions += "[" + foo + "]";
                                        additions += ",";
                                    }
                                }
                            }
                        }
                        functions += ") : " + md.getType();
                        secondParameter = true;
                    }
                }
            }
        }
        
       //parsing the instance variables
        boolean secondParameter = false;
        for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
            //check for instance of Field Declaration
            if (bd instanceof FieldDeclaration) {
                FieldDeclaration fd = ((FieldDeclaration) bd);
                String fieldScope = amSymbol(
                        bd.toStringWithoutComments().substring(0,
                                bd.toStringWithoutComments().indexOf(" ")));
                String fieldClass = swap(fd.getType().toString());
                String fieldName = fd.getChildrenNodes().get(1).toString();
                if (fieldName.contains("="))
                    fieldName = fd.getChildrenNodes().get(1).toString()
                            .substring(0, fd.getChildrenNodes().get(1)
                                    .toString().indexOf("=") - 1);
                // Change scope of getter, setters
                if (fieldScope.equals("-")
                        && makeFieldPublic.contains(fieldName.toLowerCase())) {
                    fieldScope = "+";
                }
                String getDepen = "";
                boolean getDepenMultiple = false;
                if (fieldClass.contains("(")) {
                    getDepen = fieldClass.substring(fieldClass.indexOf("(") + 1,
                            fieldClass.indexOf(")"));
                    getDepenMultiple = true;
                } else if (map.containsKey(fieldClass)) {
                    getDepen = fieldClass;
                }
                if (getDepen.length() > 0 && map.containsKey(getDepen)) {
                    String connection = "-";

                    if (cc
                            .containsKey(getDepen + "-" + classShortName)) {
                        connection = cc
                                .get(getDepen + "-" + classShortName);
                        if (getDepenMultiple)
                            connection = "*" + connection;
                        cc.put(getDepen + "-" + classShortName,
                                connection);
                    } else {
                        if (getDepenMultiple)
                            connection += "*";
                        cc.put(classShortName + "-" + getDepen,
                                connection);
                    }
                }
                if (fieldScope == "+" || fieldScope == "-") {
                    if (nextField)
                        variables += "; ";
                    variables += fieldScope + " " + fieldName + " : " + fieldClass;
                    secondParameter = true;
                }
            }

        }
        //checking for extends and implements
        if (cl.getExtends() != null) {
            additions += "[" + classShortName + "] " + "-^ " + coi.getExtends();
            additions += ",";
        }
        if (cl.getImplements() != null) {
            List<ClassOrInterfaceType> interfaceList = (List<ClassOrInterfaceType>) coi
                    .getImplements();
            for (ClassOrInterfaceType intface : interfaceList) {
                additions += "[" + classShortName + "] " + "-.-^ " + "["
                        + "<<interface>>;" + intface + "]";
                additions += ",";
            }
        }
        // integrating everything
        ans=ans+name;
        boolean flag1=fields.isEmpty();
        boolean flag2=functions.isEmpty();
        if (!flag1) {
            ans=ans+"|"+swap(variables);
        }
        if (!flag2) {
            ans=ans+"|"+swap(functions); 
        }
        ans=ans+"]";
        ans=ans+additions;
        return ans; 
    }

//swapping the brackets method
     private String swap(String s1) {
        s1 = s1.replace("<", "(");
        s1 = s1.replace(">", ")");
        s1 = s1.replace("[", "(");
        s1 = s1.replace("]", ")");
        return s1;
    }

//Symbols for access modifiers: Public and Private
private String amSymbol(String s1) {
    if(s1.equals("public"))
    {
        return "+";
    }
    else if(s1.equals("private")){
        return "-";
    }
    else{
        return "";
    }
    }



    private String add() {
        String a = "";
        Set<String> keys = cc.keySet(); 
        for (String s1 : keys) {
            String[] classes = s1.split("-");
            if (map.get(classes[0]))
            {
                a += "[<<interface>>;" + classes[0] + "]";
            }
            else{
                a += "[" + classes[0] + "]";
            }
            a += cc.get(s1);
            if (map.get(classes[1]))
            {
                a += "[<<interface>>;" + classes[1] + "]";
            }
            else{
                a += "[" + classes[1] + "]";
            }
            a += ",";
        }
        return a;
    }


    private String yUML(String s3) {
        String[] s1 = s3.split(",");
        String[] s2 = new LinkedHashSet<String>(
        Arrays.asList(s1)).toArray(new String[0]);
        String answer = String.join(",", s2);
        return answer;
    }

}