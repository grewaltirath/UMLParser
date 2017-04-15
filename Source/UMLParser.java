package umlparser.umlparser;

public class UMLParser {

    public static void main(String[] args) throws Exception {
        //command line for class diagram
        if (args.length>0 && args[0].equals("class")) {
            ClassParser c = new ClassParser(args[1], args[2]);
            c.run();
        }
        //command line for sequence diagram
        else if (args[0].equals(("sequence")) && args.length>0) {
            SequenceParser s = new SequenceParser(args[1], args[2], args[3], args[4]);
            s.run();
        }
        else {
            System.out.println("Inappropriate argument " + args[0]);
        }

    }

}