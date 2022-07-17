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

## Equivalence Class Partitioning and Boundary Value Analysis

### Definitions
Equivalence Class: groups of input that behave similarly in the Program \
Boundary Values: values at bounds of valid range OR edge cases

For each requirement, we find the equivalence classes, define the behavior for each class, define the boundary values, and rationale.

| Requirement | Equivalence classes | Equivalence class behavior |
| ------------------ |:--------------------------------------------- |:--------------------------------------------- |
| Take two csv files | 1. Is CSV file <br>&nbsp;&nbsp;&nbsp; Is not CSV file | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws an I/O Exception due to invalid input file
| All csv records have valid number of fields | 1. Record has strictly 5 columns <br>&nbsp;&nbsp;&nbsp; Record does not have 5 columns | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws a Value Exception due to invalid number of fields |
| All csv records contains valid customer ID | 1. First value in row is string <br>&nbsp;&nbsp;&nbsp; First value in row is not a string <br> 2. String is of form "IDx" where x is a positive integer <br>&nbsp;&nbsp;&nbsp; String is not of form "IDx" | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of customer ID <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect format of customer ID |
| All csv records contains valid account no | 1. Second value in row is string <br>&nbsp;&nbsp;&nbsp; Second value in row is not a string <br> 2. String is of form "BOSx" where x is a positive integer <br>&nbsp;&nbsp;&nbsp; String is not of form "BOSx" | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of account no <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect format of account no |
| All csv records contains valid currency | 1. Third value in row is string <br>&nbsp;&nbsp;&nbsp; Third value in row is not a string <br> 2. String value is of a valid currency ticker <br>&nbsp;&nbsp;&nbsp; String is not of valid currency ticker | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of currency <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to invalid currency |
| All csv records contains valid type | 1. Fourth value in row is string <br>&nbsp;&nbsp;&nbsp; Fourth value in row is not a string <br> 2. String value is in {"CURRENT", "SAVINGS"} <br>&nbsp;&nbsp;&nbsp; String is not in {"CURRENT", "SAVINGS"} | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of account type <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to invalid account type |
| All csv records contains valid balance | 1. Fifth value in row is positive integer <br>&nbsp;&nbsp;&nbsp; Fifth value in row is not a positive integer | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of balance |
