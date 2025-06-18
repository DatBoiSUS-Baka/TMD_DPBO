## How to compile the program

```
// Compile the file and put the classes in the bin folder
javac -d bin -cp "lib/mysql-connector-j-9.3.0.jar" src/model/*.java src/presenter/*.java src/view/*.java src/App.java

// Run the compiled classes and library
java -cp "bin;lib\*;resources" App
```
