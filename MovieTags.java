import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieTags {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> arr = new ArrayList<String>();			// Array of Movies
		ArrayList<MovieObject> objs = new ArrayList<MovieObject>();	// Array of MovieObjects
		readFile(arr, objs, args[0]);
		mergeSort(objs);
		printHighLow(objs);
		takeUserInput(objs);
		
	}
	
	
	// merge, mergeSort, get_left, and getRight are all to merge sort the movie objects array:
	// Sorts by frequency, but if the frequencies are the same it sorts alphabetically
	public static void merge(ArrayList<MovieObject> a, ArrayList<MovieObject> b, ArrayList<MovieObject> target) {	// Time: O(n)
		int ia = 0, ib = 0, it = 0;
		
		while (ia < a.size() && ib < b.size()) {
			if (a.get(ia).getFrequency() < b.get(ib).getFrequency()) {
				target.set(it++, a.get(ia++));
			} else if (a.get(ia).getFrequency() == b.get(ib).getFrequency()) {
				if (a.get(ia).getName().compareTo(b.get(ib).getName()) <= 0) {
					target.set(it++, a.get(ia++));
				} else {
					target.set(it++, b.get(ib++));
				}
			} else {
				target.set(it++, b.get(ib++));
			}
		}
		while (ia < a.size()) {
			target.set(it++, a.get(ia++));
		}
		while (ib < b.size()) {
			target.set(it++, b.get(ib++));
		}
	}
	
	public static void mergeSort(ArrayList<MovieObject> arr) {	

		if (arr.size() > 1) {
			ArrayList<MovieObject> left = get_left(arr);
			ArrayList<MovieObject> right = get_right(arr);
			
			mergeSort(left);
			mergeSort(right);
			merge(left, right, arr);
		}	
	}
	
	public static ArrayList<MovieObject> get_left(ArrayList<MovieObject> arr) {
		int size = arr.size()/2;
		ArrayList<MovieObject> left = new ArrayList<MovieObject>();
		for (int i = 0; i < size; i++) {
			left.add(i, arr.get(i));
		}
		return left;
	}
	
	public static ArrayList<MovieObject> get_right(ArrayList<MovieObject> arr) {
		int size = arr.size() - arr.size()/2;	
		ArrayList<MovieObject> right = new ArrayList<MovieObject>();
		for (int i = arr.size()/2; i < arr.size(); i++) {
			right.add(i - (arr.size()/2), arr.get(i));
		}
		return right;
	}
	
	// Read file with BufferedReader
	public static void readFile(ArrayList<String> arr, ArrayList<MovieObject> objs, String fileName) {
		System.out.println("Reading data from file .....");
		try {
			Reader f = new FileReader(fileName);					// Create new FileReader for the csv file
			BufferedReader next = new BufferedReader(f);			// Create new BufferedReader for the file
			next.readLine();										// Read in and throw away the header
			String[] tmpArr;										// Create a temporary array to store each new line
			
			String line = next.readLine();
			while (line != null) {
				tmpArr = line.split(",");
				
				// Check if the new line is a movie with a tag already in the MovieObject array (objs)
				// If it is already in the object array: increase its frequency by one and add it to the full movie array
				// If it is not in the object array: create a new object with the tag and starting frequency of 1, then add it to the full movie array
				int index = inArr(objs, tmpArr[2]);
				if (index >= 0) {
					objs.get(index).increment();
				} else {
					objs.add(new MovieObject(tmpArr[2]));
				}
				arr.add(tmpArr[2]);
				
				line = next.readLine();
			}
			next.close();

		} catch(FileNotFoundException e) {
			System.out.println("file not found");
		} catch(IOException e2) {
			System.out.println("unable to read file");
		}
	}
	
	// If the target tagName is in the array of movie objects, returns the index of that object in arr - Otherwise, returns -1
	public static int inArr(ArrayList<MovieObject> arr, String target) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getName().equals(target)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void printHighLow(ArrayList<MovieObject> objs) {
		System.out.println(" ========================================== ");
		System.out.println("*** Highest 3 Movies by Count ***");
		
		int[] highestFrequencies = {objs.get(objs.size() - 1).getFrequency(), 
									objs.get(objs.size() - 2).getFrequency(), 
									objs.get(objs.size() - 3).getFrequency()};
		// 4 possibilities: 1) All three maxes are equal, 2) The max and the second max are equal,
		// 3) The second max and the third max are equal, 4) All 3 maxes are different
		if (highestFrequencies[0] == highestFrequencies[1] && highestFrequencies[1] == highestFrequencies[2]) {
			int lAlph = findAlph(objs, highestFrequencies[0]);	// First tag alphabetically with the highest frequency
			System.out.println(objs.get(lAlph).getFrequency() + ": " + objs.get(lAlph).getName());
			System.out.println(objs.get(lAlph + 1).getFrequency() + ": " + objs.get(lAlph + 1).getName());
			System.out.println(objs.get(lAlph + 2).getFrequency() + ": " + objs.get(lAlph + 2).getName());
		} else if (highestFrequencies[0] == highestFrequencies[1]) {
			int lAlph = findAlph(objs, highestFrequencies[0]);
			System.out.println(objs.get(lAlph).getFrequency() + ": " + objs.get(lAlph).getName());
			System.out.println(objs.get(lAlph + 1).getFrequency() + ": " + objs.get(lAlph + 1).getName());
			lAlph = findAlph(objs, highestFrequencies[2]);
			System.out.println(objs.get(lAlph).getFrequency() + ": " + objs.get(lAlph).getName());
		} else if (highestFrequencies[1] == highestFrequencies[2]) {
			int lAlph = findAlph(objs, highestFrequencies[0]);
			System.out.println(objs.get(lAlph).getFrequency() + ": " + objs.get(lAlph).getName());
			lAlph = findAlph(objs, highestFrequencies[1]);
			System.out.println(objs.get(lAlph).getFrequency() + ": " + objs.get(lAlph).getName());
			System.out.println(objs.get(lAlph + 1).getFrequency() + ": " + objs.get(lAlph + 1).getName());
		} else {
			for (int i = 0; i < 3; i++) {
				int lAlph = findAlph(objs, highestFrequencies[i]);
				System.out.println(objs.get(lAlph).getFrequency() + ": " + objs.get(lAlph).getName());
			}
		}
		
		System.out.println("*** Lowest 3 movies by count ***");
		System.out.println(objs.get(0).getFrequency() + ": " + objs.get(0).getName());
		System.out.println(objs.get(1).getFrequency() + ": " + objs.get(1).getName());
		System.out.println(objs.get(2).getFrequency() + ": " + objs.get(2).getName());
		System.out.println(" ========================================== ");
	}
	
	public static void takeUserInput(ArrayList<MovieObject> objs) {
		Scanner scnr = new Scanner(System.in);
		String user = "";
		while (!user.equals("EXIT")) {
			System.out.print("Search by Tag or Tag Count? (Enter T or C... or EXIT to exit): ");
			user = scnr.nextLine(); 		// User input: Either T, C, EXIT, or invalid
			if (user.equals("T")) {
				System.out.print("Tag to search for: ");
				user = scnr.nextLine();
				int count = frequencyByTag(objs, user);
				if (count == 0) {
					System.out.println("Tag \"" + user + "\" does not exist.");
				} else if (count == 1) {
					System.out.println("Tag \"" + user + "\" occurred " + count + " time.");
				} else {
					System.out.println("Tag \"" + user + "\" occurred " + count + " times.");
				}
			} else if (user.equals("C")) {
				System.out.print("Count to search for: ");
				if (scnr.hasNextInt()) {
					int frequency = scnr.nextInt();
					scnr.nextLine();
					tagByFrequency(objs, frequency);
				} else {
					System.out.println("Is " + scnr.nextLine() + " even a number? C'mon!");
				}

			} else if (user.equals("EXIT")) {
				break;
			} else {
				System.out.println("Invalid input");
			}
		}
		scnr.close();
	}
	
	public static int frequencyByTag(ArrayList<MovieObject> objs, String tag) {
		for (int i = 0; i < objs.size(); i++) {
			if (objs.get(i).getName().equals(tag)) {
				return objs.get(i).getFrequency();
			}
		}
		return 0;
	}
	
	public static void tagByFrequency(ArrayList<MovieObject> objs, int frequency) {
		boolean exist = false;
		System.out.println("Tags with " + frequency + " occurrences:");
		for (int i = 0; i < objs.size(); i++) {
			if (objs.get(i).getFrequency() == frequency) {
				System.out.println("* " + objs.get(i).getName());
				exist = true;
			}
		}
		if (!exist) {
			System.out.println("There are no tags that occur " + frequency + " times.");
		}
	}
	
	public static int findAlph(ArrayList<MovieObject> objs, int frequency) {
		for (int i = objs.size() - 1; i >= 1; i--) {
			if (objs.get(i).getFrequency() == frequency) {
				if (objs.get(i).getFrequency() != objs.get(i - 1).getFrequency()) {
					return i;
				}
			}
		}
		return 0;
	}
	
	public static void print(ArrayList<String> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}
	
	public static void printObjs(ArrayList<MovieObject> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i).getName() + " " + arr.get(i).getFrequency());
		}
	}
	
}
