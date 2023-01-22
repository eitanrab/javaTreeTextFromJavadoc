# javaTreeTextFromJavadoc
Java Tree Generator
Java Tree Generator is a program that generates a text tree of all modules and classes in a Java project, including a short explanation of each class (using the javadoc). The program uses the JavaParser library to parse the Java code and extract the relevant information.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
Java 8 or later     
Maven   
IntelliJ IDEA (optional)    

### Installing
Clone or download the repository.
Open the project in IntelliJ IDEA (if using) and make sure that the JavaParser library is added to the classpath (if not you can add it through the pom.xml file)
Update the root variable in the main method with the path to the root folder of your Java project.
Run the program by executing the main method in the TreeGenerator class.
### Output
The program will output the tree representation of the modules and classes in the project, with each class or module indented to indicate its level in the tree. The javadoc of each class will also be included in the output, following the class or module name, and indented as well.

## Example
```
root
    oop.ex6.main.parsing
        DeclarationException 
            *The DeclarationException class represents an exception that is thrown when
            *there is an issue with variable or method declarations.
        Parser 
            *The Parser class is responsible for parsing the input code.
        SymbolTable 
            *reposible for variables values and method. 
```
