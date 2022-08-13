# 50003_testing_campaign
Software Testing Mini Campaign for SUTD course 50.003 Element of Software Construction.

Name: Ho Rui En
Student ID: 1005000

## Usage
The following compares two csv files and returns records that do not match.

* <b>Compile</b>: Generates all class files to run on JVM
* <b>Run</b>: Compare two csv files containing records, output set of records that do not match only if they are valid account records. Also prints out lines that denote invalid account records.
* <b>Fuzz</b>: Generate `[file count]` instances of csv files of valid and invalid account records, each of `[line count]` number of entries. By default, the instances will be generates in ./fuzzTests
* <b>Test</b>: Runs all tests. For system tests using inputs from ./fuzzTests, the outputs will be in ./fuzzTests.

### Usage (Java only)

```
## Compile
javac -cp "./target/dependency/*" -d build src/main/java/*.java src/test/java/*.java

## Run
## Syntax: java -cp build/ CompareRecords [csv-path-1] [csv-path-2] [output-path]
java -cp build/ CompareRecords ./assets/sample_file_1.csv ./assets/sample_file_3.csv res.csv

## Fuzz
## Syntax: java -cp build/ FuzzInputs random [line count] [file count]
java -cp build/ FuzzInputs random 100 10

## Test
java -cp "target/dependency/*;build/" TestRunner
```

### Usage (Maven)
```
## Compile
mvn compile

## Run
## Syntax: mvn exec:java -Dexec.mainClass="CompareRecords" -Dexec.args="[csv-path-1] [csv-path-2] [output-path]"
mvn exec:java -Dexec.mainClass="CompareRecords" -Dexec.args="./assets/sample_file_1.csv ./assets/sample_file_3.csv res.csv"

## Fuzz
## Syntax: mvn exec:java -Dexec.mainClass="FuzzInputs" -Dexec.args="random [line count] [file count]"  
mvn exec:java -Dexec.mainClass="FuzzInputs" -Dexec.args="random 100 10"

## Test
mvn test -Dtest=RecordTest,SerialiserTest,SerialiserFieldsTest,CompareRecordsTest,FuzzerTest
```

To generate dependencies
```
mvn dependency:copy-dependencies
```

## Use Case Diagram
<div align="center">
    <img src="images/CE8_UseCaseDiagram.jpg">
</div>

## Equivalence Class Partitioning and Boundary Value Analysis

[For image view of this table](images/CE9_equivclass_boundaryvalues_table.png)

| Requirement | Equivalence classes | Equivalence classes behavior | Middle and Boundary Values |
| ----------- |:--------------------------------------------- |:--------------------------------------------- |:--------------------------------------------- |
| Take two csv files | 1. Is CSV file | 1. Continues running | 1. (valid) middle: "sample.csv" csv file; boundary: "sample.txt" csv file
|                    | &nbsp;&nbsp;&nbsp; Is not CSV file | &nbsp;&nbsp;&nbsp; Throws an I/O Exception due to invalid input file | &nbsp;&nbsp;&nbsp; (invalid) middle: "sample.txt" txt file; boundary: "sample.csv" txt file
| All csv records have valid number of fields | 2. Record has strictly 5 columns | 2. Continues running | 2. (valid) middle / boundary: 5 columns
|                                             | &nbsp;&nbsp;&nbsp; Record does not have 5 columns | &nbsp;&nbsp;&nbsp; Throws a Value Exception due to invalid number of fields | &nbsp;&nbsp;&nbsp; (invalid) middle: 1 column, boundary: 4 or 6 columns
| All csv records contains valid customer ID  | 3a. First value in row is string | 3a. Continues running | 3a. (valid) middle: "ID0"; boundary: "" 
|                                             | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; First value in row is not a string | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of customer ID | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: 99; boundary: 0 or [] 
|                                            | 3b. String is of form "IDx" where x is a positive integer | 3b. Continues running | 3b. (valid) middle: "ID42"; boundary: "ID0" or "ID9999999" 
|                                            | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String is not of form "IDx" | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect format of customer ID | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: "asdf"; boundary: "ID" or "42" or ""
| All csv records contains valid account no | 4a. Second value in row is string | 4a. Continues running | 4a. (valid) middle: "BOS42"; boundary: "" 
|                                           | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Second value in row is not a string | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of account no | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: 99; boundary: 0 or [] 
|                                           | 4b. String is of form "BOSx" where x is a positive integer | 4b. Continues running | 4b. (valid) middle: "BOS42"; boundary: "BOS0" or "BOS9999999" 
|                                           | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String is not of form "BOSx" | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect format of account no | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; middle: "asdf"; boundary: "BOS" or "42" or ""
| All csv records contains valid currency | 5a. Third value in row is string | 5a. Continues running | 5a. (valid) middle: "SGD"; boundary: "" 
|                                         | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Third value in row is not a string | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of currency | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: 42; boundary: 0 or [] 
|                                         | 5b. String value is of a valid currency ticker | 5b. Continues running | 5b. (valid) middle/boundary: "USD" 
|                                         | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String is not of valid currency ticker | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to invalid currency | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: "asdf"; boundary: "USDSGD" or "USDD"
| All csv records contains valid type | 6a. Fourth value in row is string | 6a. Continues running | 6a. (valid) middle: "CURRENT"; boundary: "" 
|                                     | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Fourth value in row is not a string | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of account type | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: 42; boundary: 0 or [] 
|                                     | 6b. String value is in {"CURRENT", "SAVINGS"} | 6b. Continues running | 6b. (valid) middle/boundary: "CURRENT" or "SAVINGS" 
|                                     | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String is not in {"CURRENT", "SAVINGS"} | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Throws Value Exception due to invalid account type | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (invalid) middle: "asdf", boundary: "current"
| All csv records contains valid balance | 7. Fifth value in row is positive integer | 7. Continues running | 7. (valid) middle: 42; boundary: 0 or maxint 
|                                        | &nbsp;&nbsp;&nbsp; Fifth value in row is not a positive integer | &nbsp;&nbsp;&nbsp; Throws Value Exception due to incorrect typing of balance | &nbsp;&nbsp;&nbsp; (invalid) middle: -42; boundary: -1 or [] or ""

### Definitions
* Equivalence Class: groups of input that behave similarly in the Program
* Boundary Values: values at bounds of valid range OR edge cases

### Explanation / Rationale
* The design of each equivalence class is in accordance to one aspect of the requirement, each of these aspects splits the inputs into two subsets, one that fulfils the aspect, and one that does not.
* The design of each boundary / middle value is with respect to the aspect of the requirement. The boundary values are the bounds of the input range, corresponding to the requirement.
* We follow the above two points to systematically list the equivalent classes and boundary values.
* Hence, for each requirement in the table above, we
    1. Find corresponding equivalence classes 
    2. Define the behavior for them
    3. Define the boundary values for corresponding valid/invalid input ranges

## Testing Coverage
Record Test: `src/test/java/RecordTest.java`
1. Valid path
2. Invalid path
3. Non-csv files: inclusive of extension and no extensions

Serialiser Test: `src/test/java/SerialiserTest.java`
Serialising and deserialising for
1. Strings
2. Numerics
3. Symbols
4. Empty values

Serialiser Fields Test: `src/test/java/SerialiserFieldsTest.java`
1. Field Count
2. Id validity
3. Account Number validty
4. Currency validity
5. Account type vality
6. Balance validity

For details of each point, see [section on equivalence class partitioning and boundary value analysis](#equivalence-class-partitioning-and-boundary-value-analysis)

System tests: `src/test/java/CompareRecordsTest.java` \
For each pair of input csvs, perform the following checks
1. Csv output
2. Exception logging to stdout


## Third Party Testing

### bxverley
See bxverley's implementation and rehohoho's written tests on: https://github.com/bxverley/Mini_testing_campaign/tree/rehohoho/Tests

Tests added: system test, instructions to run is added in the README in the branch
```
javac -cp "./target/dependency/*" -d build Test\ files/*.java
java -cp build/ compareCSV ./Test\ files/sample_file_1.csv ./Test\ files/sample_file_3.csv
java -cp "target/dependency/*;build/" TestRunner
```

Bugs found:
1. Does not flag out invalid csv lines, in `https://github.com/bxverley/Mini_testing_campaign/blob/rehohoho/Tests/fuzzTests/sample_file_4.csv`. In particular, the following lines should not be processed due to invalid fields.
```
a
a,b,c,d
a,b,c,d,e,f
iD,BOS,SGD,CURRENT,0
asdf,BOS,SGD,CURRENT,0
42,BOS,SGD,CURRENT,0
 ,BOS,SGD,CURRENT,0
ID,asdf,SGD,CURRENT,0
ID,BOs,SGD,CURRENT,0
ID,42,SGD,CURRENT,0
ID, ,SGD,CURRENT,0
ID,BOS,asdf,CURRENT,0
ID,BOS,sGD,CURRENT,0
ID,BOS,42,CURRENT,0
ID,BOS, ,CURRENT,0
ID,BOS,SGD,asdf,0
ID,BOS,SGD,cURRENT,0
ID,BOS,SGD,cURRENT,-42
ID,BOS,SGD,cURRENT,-1
ID,BOS,SGD,CURRENT,0
ID,BOS,SGD,CURRENT, 
```

2. Non-csv files that are named as "*.csv" are not flagged. See test case `https://github.com/bxverley/Mini_testing_campaign/blob/rehohoho/Tests/fuzzTests/testNoncsv.csv`
3. Non-csv files are not flagged. See test case `https://github.com/bxverley/Mini_testing_campaign/blob/rehohoho/Tests/fuzzTests/testNoncsv.txt`

### LHJJosh

See LHJJosh's implementation and rehohoho's written tests on: https://github.com/LHJJosh/ESC_Mini_Campaign/tree/rehohoho/test

Tests added: system test, instructions to run is added in the README in the branch
```
javac -cp "./target/dependency/*" -d build src/data_reconciliation/*.java #compile
java -cp "target/dependency/*;build/" TestRunner #run test
```

Bugs found:
1. Does not flag out invalid csv lines, in `https://github.com/bxverley/Mini_testing_campaign/blob/rehohoho/Tests/fuzzTests/sample_file_4.csv`. In particular, the following lines should not be processed due to invalid fields.
```
a
a,b,c,d
a,b,c,d,e,f
iD,BOS,SGD,CURRENT,0
asdf,BOS,SGD,CURRENT,0
42,BOS,SGD,CURRENT,0
 ,BOS,SGD,CURRENT,0
ID,asdf,SGD,CURRENT,0
ID,BOs,SGD,CURRENT,0
ID,42,SGD,CURRENT,0
ID, ,SGD,CURRENT,0
ID,BOS,asdf,CURRENT,0
ID,BOS,sGD,CURRENT,0
ID,BOS,42,CURRENT,0
ID,BOS, ,CURRENT,0
ID,BOS,SGD,asdf,0
ID,BOS,SGD,cURRENT,0
ID,BOS,SGD,cURRENT,-42
ID,BOS,SGD,cURRENT,-1
ID,BOS,SGD,CURRENT,0
ID,BOS,SGD,CURRENT, 
```
2. Non-csv files named as "*.csv" does not raise an exception. See test case `https://github.com/LHJJosh/ESC_Mini_Campaign/blob/rehohoho/test/src/input/fuzz_testNoncsv.csv`