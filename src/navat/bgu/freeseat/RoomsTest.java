package navat.bgu.freeseat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

public class RoomsTest extends TestCase {

	public void testRooms() {
		try {
			FileInputStream fileIn = new FileInputStream("E:\\code\\Java\\FreeSeat\\src\\navat\\bgu\\freeseat\\compclass.htm");
			Rooms rooms = new Rooms(fileIn);
			rooms.getRoomsStates();
			
			assert(!rooms.roomsState.isEmpty());
		} catch (FileNotFoundException e) {
			fail("Could not find sample file");
		} catch (IOException e) {
			fail("Error reading from sample file");
		}
	}

}
