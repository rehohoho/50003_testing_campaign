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

| Requirement | Equivalence classes | Equivalence classes behavior | Middle and Boundary Values |
| ----------- |:--------------------------------------------- |:--------------------------------------------- |:--------------------------------------------- |
| Take two csv files | 1. Is CSV file <br>&nbsp;&nbsp;&nbsp; Is not CSV file | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws an I/O Exception due to invalid input file | 1. (valid) middle: "sample.csv" csv file; boundary: "sample.txt" csv file, <br>&nbsp;&nbsp;&nbsp; (invalid) middle: "sample.txt" txt file; boundary: "sample.csv" txt file
| All csv records have valid number of fields | 1. Record has strictly 5 columns <br>&nbsp;&nbsp;&nbsp; Record does not have 5 columns | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws a Value Exception due to invalid number of fields | 1. (valid) middle / boundary: 5 columns <br>&nbsp;&nbsp;&nbsp; (invalid) middle: 1 column, boundary: 4 or 6 columns
| All csv records contains valid customer ID | 1. First value in row is string <br>&nbsp;&nbsp;&nbsp; First value in row is not a string <br> 2. String is of form "IDx" where x is a positive integer <br>&nbsp;&nbsp;&nbsp; String is not of form "IDx" | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of customer ID <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect format of customer ID | 1. (valid) middle: "ID0"; boundary: "" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: 99; boundary: 0 or [] <br> 2. (valid) middle: "ID42"; boundary: "ID0" or "ID9999999" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: "asdf"; boundary: "ID" or "42" or ""
| All csv records contains valid account no | 1. Second value in row is string <br>&nbsp;&nbsp;&nbsp; Second value in row is not a string <br> 2. String is of form "BOSx" where x is a positive integer <br>&nbsp;&nbsp;&nbsp; String is not of form "BOSx" | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of account no <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect format of account no | 1. (valid) middle: "BOS42"; boundary: "" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: 99; boundary: 0 or [] <br> 2. (valid) middle: "BOS42"; boundary: "BOS0" or "BOS9999999" <br>&nbsp;&nbsp;&nbsp; middle: "asdf"; boundary: "BOS" or "42" or ""
| All csv records contains valid currency | 1. Third value in row is string <br>&nbsp;&nbsp;&nbsp; Third value in row is not a string <br> 2. String value is of a valid currency ticker <br>&nbsp;&nbsp;&nbsp; String is not of valid currency ticker | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of currency <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to invalid currency | 1. (valid) middle: "SGD"; boundary: "" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: 42; boundary: 0 or [] <br> 2. (valid) middle/boundary: "USD" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: "asdf"; boundary: "USDSGD" or "USDD"
| All csv records contains valid type | 1. Fourth value in row is string <br>&nbsp;&nbsp;&nbsp; Fourth value in row is not a string <br> 2. String value is in {"CURRENT", "SAVINGS"} <br>&nbsp;&nbsp;&nbsp; String is not in {"CURRENT", "SAVINGS"} | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of account type <br> 2. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to invalid account type | 1. (valid) middle: "CURRENT"; boundary: "" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: 42; boundary: 0 or [] <br> 2. (valid) middle/boundary: "CURRENT" or "SAVINGS" <br>&nbsp;&nbsp;&nbsp; (invalid) middle: "asdf", boundary: "current"
| All csv records contains valid balance | 1. Fifth value in row is positive integer <br>&nbsp;&nbsp;&nbsp; Fifth value in row is not a positive integer | 1. Continues running <br>&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of balance | 1. (valid) middle: 42; boundary: 0 or maxint <br>&nbsp;&nbsp;&nbsp; (invalid) middle: -42; boundary: -1 or [] or ""

### Definitions
* Equivalence Class: groups of input that behave similarly in the Program \
* Boundary Values: values at bounds of valid range OR edge cases

### Explanation / Rationale
* The design of each equivalence class is in accordance to one aspect of the requirement, each of these aspects splits the inputs into two subsets, one that fulfils the aspect, and one that does not.
* The design of each boundary / middle value is with respect to the aspect of the requirement. The boundary values are the bounds of the input range, corresponding to the requirement.
* We follow the above two points to systematically list the equivalent classes and boundary values.
* Hence, for each requirement in the table above, we
    1. Find corresponding equivalence classes 
    2. Define the behavior for them
    3. Define the boundary values for corresponding valid/invalid input ranges