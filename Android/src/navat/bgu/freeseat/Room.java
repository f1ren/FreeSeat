package navat.bgu.freeseat;

public class Room {
	String room;
	String building;
	int occupied;
	int total;
	String link;
	int rate;
	
	@Override
	public String toString() {
		return String.valueOf(occupied) + "/" + String.valueOf(total) + " " + room;
	}
}