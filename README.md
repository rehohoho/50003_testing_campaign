# 50003_testing_campaign
Software Testing Mini Campaign for SUTD course 50.003 Element of Software Construction.

Name: Ho Rui En
Student ID: 1005000

## Usage
The following compares two csv files and returns records that do not match.

### Java only
First command is to compile, second is to run. \
Syntax: `java -cp build/ CompareRecords [csv-path-1] [csv-path-2] [output-path]`
```
javac -d build src/main/java/*.java
java -cp build/ CompareRecords ./assets/sample_file_1.csv ./assets/sample_file_3.csv res.csv
```

### Maven
```
mvn compile # compile
mvn exec:java -Dexec.mainClass="CompareRecords" # execute
```

## Use Case Diagram
<div align="center">
    <img src="images/CE8_UseCaseDiagram.jpg">
</div>
