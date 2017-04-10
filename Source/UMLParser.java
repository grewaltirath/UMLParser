
public class UMLParser {

    public static void main(String[] args) throws Exception {

        if (args[0].equals("class")) {
        //check if it wants the class diagram
            ClassParser cp = new ClassParser(args[1], args[2]);
            //calling the parse engine constructor for class diagram
            cp.start();
        } else if (args[0].equals(("seq"))) {
        //check if the argument is a sequence diagram
            SequenceParser sp = new SequenceParser(args[1], args[2], args[3], args[4]);
            //calling the parse sequence engine constructor for sequence diagram
            sp.start();
        } else {
            System.out.println("Inappropriate argument: " + args[0]);
        }

    }

}