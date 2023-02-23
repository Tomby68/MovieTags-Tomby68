# cs245-assignment01-Tomby68
Repository for Assignment 01, CS 245, Spring 2023. 

	 Takes in a csv file as the first and only argument, then prints out the tags of the 3 max frequency
	 movies and 3 min frequency movies. Then, it displays a text user interface, prompting the user to input either
	 T, C, or EXIT -> For T, it searches for and prints out the frequency the tag the user inputs
	 For C, it searches for all tags with the frequency the user inputs
	 For EXIT, the program exits
	  
	 @param args[0]: The name of the csv file
	 
Reading the data file:
	- Uses objs, an array of MovieObject objects -> MovieObject is a separate class with values String name and int frequency, as well as getters and an increment function to increase the frequency
	- For each line in the data file, the program uses inArr, a linear search function that checks if a given String target is in the objs array. 
		- If the tag is in objs, the program increments the frequency of that object.
		- If the tag is not in objs, the program adds a new MovieObject to objs with tag.
	- Running time: O(n^2) where n is the number of lines in the data file, because for each 	line of the file the program searches a separate array that may have up to n elements.
	
List Most and Least Popular Tags:
	- First, the program mergeSorts the objs array by frequency
		- Modification: If two frequencies are the same, the program sorts them alphabetically by tag
			- Running time: O(nlgn) time, it is mergeSort with one extra if
		- Next: Searches through the objs array for the top three highest maxes, alphabetically if they are the same
			- Running time: O(n) time, if all the frequencies are the same it will search the whole array
		- Finally, prints out the top 3 highest frequency tags and the bottom 3 lowest frequency tags
Find Tags by Count or Count by Tag:
	- Takes user input in a while loop -> Will either take in T, C, EXIT, or invalid input
		- T: Gives the frequency of the tag the user inputs -> Running Time: O(n), searches through objs for an object with that tag and outputs
		the frequency
		- C: Outputs tags of movies with frequency of the user input -> Running Time: O(n), searches through objs for any object with frequency equal to the user input, and outputs that object tag
		- EXIT: The program exits -> Running Time: O(1), just a break statement and scnr.close()
		- Invalid input: The program asks for valid user input -> Running Time: O(1), one print statement and reruns the while loop
