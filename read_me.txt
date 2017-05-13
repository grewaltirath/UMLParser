You need Java installed and a internet connection
You have to give arguments to execute this: 

"class" to execute class diagram
"sequence" to execute sequence diagram

You need to specify the path of the java files:

This is for Test case 1

Eg. /Users/Tirath/Desktop/OneDrive/CMPE_202/Project/Test_Classes/Test1 


Then, you need to give the name of the output file
Eg. imgagess 
The output will be a png file, no need to include the extension

For sequence diagram, you need 2 additional arguments class name and method name

Now, to generate class diagram give the following command: 
java -jar umlparser.jar class "/Users/Tirath/Desktop/OneDrive/CMPE_202/Project/Test_Classes/sequence-diagram-test-1" imaaagee

A imaaagee.png will be created


Now, to generate sequence diagram, give the following command:

java -jar umlparser.jar sequence "/Users/Tirath/Desktop/OneDrive/CMPE_202/Project/Test_Classes/sequence-diagram-test-1" Customer depositMoney diagram

Customer is the name of the class
depositMoney is the name of the method




There are 2 parts of this UML parser program:

I have used javaparser as the library to parse the grammar and yUML as UML generator.


Javaparser: https://github.com/javaparser/javaparser


UML Generator: http://yuml.me/


A HTTP GET request is made to the URL: http://yuml.me/diagram/plain/class/<Grammar>
and the diagram is generated.

For generating the sequence diagram, plantUML is integrated in the code: http://plantuml.com/



