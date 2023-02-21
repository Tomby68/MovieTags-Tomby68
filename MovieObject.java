package for_assignment_1;


public class MovieObject {
	private String name;
	private int frequency;
	
	public MovieObject(String theName) {
		name = theName;
		frequency = 1;
	}
	
	public String getName() {
		return name;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public void increment() {
		frequency++;
	}
}

