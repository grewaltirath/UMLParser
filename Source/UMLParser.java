
public class UMLParser {

    public static void main(String[] args) throws Exception {
        if (args[0].equals("class")) {
        //check if it wants the class diagram
            ParseEngine parse = new ParseEngine(args[1], args[2]);
            parse.start();
        } else if (args[0].equals(("seq"))) {
        //check if the argument is a sequence diagram
            ParseSeqEngine seq = new ParseSeqEngine(args[1], args[2], args[3], args[4]);
            seq.start();
        } else {
            System.out.println("What you entered is invalid: " + args[0]);
        }

    }

}