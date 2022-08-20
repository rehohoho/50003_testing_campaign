
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CheckErrors {
	
	public CheckErrors() {
		
	}
	
	/**
	* An instance method to read and compare 2 files
	* Precondition: The name of the .csv file must be valid from file1 and file2; must have 2 files
	* Postcondition: null
	* @return null
	* @param String
	*/
	public CheckErrors(String file1, String file2) throws IOException {
		String pathToFolder;
		String pathToFile1;
		String pathToFile2;
		String outputPath;
		
		
		ArrayList<String[]> dataset1 = new ArrayList<String[]>();
		ArrayList<String[]> dataset2 = new ArrayList<String[]>();
		
		pathToFolder = "src/input/";
		pathToFile1 = createPath(pathToFolder, checkForCSVInName(file1));
		pathToFile2 = createPath(pathToFolder, checkForCSVInName(file2));
		
		dataset1 = reader(pathToFile1);
		dataset2 = reader(pathToFile2);
		
		outputPath = "src/output/output_comparing_" + fileName(file1) + "_and_" + fileName(file2)  + ".csv";
		csvWrite(dataset1, dataset2,  outputPath);
		
	}
	
	/**
	* Checks if filename has .csv in it. If there is none at the end, it adds it in.
	* Precondition: The name of the .csv file must be valid from args.
	* Postcondition: null
	* @return String filename
	* @param String
	*/
	public String checkForCSVInName(String filename) {
		if(filename.length() > 4) {
			if(filename.substring(filename.length()-4).equals(".csv")){
				return filename;
			}
		}
		return filename + ".csv";
	}
	
	
	/**
	* Reads a .csv file and stores each line as a String[]. All lines are then stored in an ArrayList.
	* Precondition: The name of the .csv file must be valid from args.
	* Postcondition: null
	* @return ArrayList<String[]> dataset
	* @param String pathToFile
	*/
	public ArrayList<String[]> reader(String pathToFile) throws IOException{
		String row;
		String[] data ;
		ArrayList<String[]> dataset = new ArrayList<String[]>();
		
		BufferedReader csvReader = new BufferedReader(new FileReader(pathToFile));
		while ((row = csvReader.readLine()) != null) {
		    data = row.split(",");
		    dataset.add(data);
		}
		csvReader.close();
		return dataset;
	}
	
	/**
	* Uses the name of .csv file from args to create the file path
	* Precondition: The name of the .csv file must be valid.
	* Postcondition: null
	* @return String
	* @param String base, String arg
	*/
	public String createPath(String base, String arg) {
		return base + arg;
	}
	
	/**
	* Takes the contents of two .csv files and compares them, then writes the differences in new .csv file
	* Precondition: dataset1 and dataset2 as well as outputPath should not be empty
	* Postcondition: null
	* @return null
	* @param ArrayList<String[]> dataset1, ArrayList<String[]> dataset2, String outputPath
	*/
	public void csvWrite(ArrayList<String[]> dataset1, ArrayList<String[]> dataset2, String outputPath) throws IOException{
		FileWriter csvWriter = new FileWriter(outputPath);
		for(int i = dataset1.size() - 1; i >= 0 ; i--) {
			boolean done = false;
			//Checks for same value entries in dataset2
			for(int j = dataset2.size() - 1; j >= 0 ; j--) {
				if(Arrays.equals(dataset1.get(i), dataset2.get(j))) {
					dataset2.set(j, null);
					done = true;
				}
				
			}
			//Checks for duplicates entries in dataset1
			if(done) {
				for(int k = dataset1.size() - 1; k >= 0 ; k--) {
					if(Arrays.equals(dataset1.get(i), dataset1.get(k)) &&  i != k) {
						dataset1.set(k, null);
					}
				}
				dataset1.set(i, null);
			}
		}
		//remove method
		dataset1 = removeNull(dataset1);
		dataset2 = removeNull(dataset2);
		
		writeToCSV(csvWriter, dataset1);
		writeToCSV(csvWriter, dataset2);

		csvWriter.flush();
		csvWriter.close();
	}
	
	
	/**
	* Removes the null values from the input dataset that were changed for same values
	* Precondition: dataset1 and dataset2 as well as outputPath should not be empty
	* Postcondition: null
	* @return ArrayList<String[]> dataset
	* @param ArrayList<String[]> dataset
	*/
	private ArrayList<String[]> removeNull(ArrayList<String[]> dataset) {
		for(int i = dataset.size() - 1; i >= 0 ; i--) {
			if(dataset.get(i) == null) {
				dataset.remove(i);
			}
		}
		return dataset;
	}
	
	/**
	* writes the different values to the new opened .csv file
	* Precondition: triggered by csvWrite method
	* Postcondition: null
	* @return null
	* @param FileWriter csvWriter, ArrayList<String[]> dataset
	*/
	private void writeToCSV(FileWriter csvWriter, ArrayList<String[]> dataset) throws IOException{
		for(int i = 0; i < dataset.size(); i++) {
			int dsLength = dataset.get(i).length;
			int starter = 0;
			while(dsLength > 0) {
				dsLength -= 1;
				csvWriter.append(dataset.get(i)[starter]);
			    csvWriter.append(",");
				starter += 1;
			}
		    csvWriter.append("\n");
		}
	}
	
	/**
	* Removes .csv from filename
	* Precondition: null
	* Postcondition: null
	* @return String file
	* @param String file
	*/
	public String fileName(String file) {
		if(file.length() > 4) {
			if(file.substring(file.length()-4).equals(".csv")){
				return file.substring(0,file.length()-4);
			}
		}
		return file;
	}
}


