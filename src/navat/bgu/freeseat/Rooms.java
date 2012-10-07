package navat.bgu.freeseat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.util.Log;

public class Rooms {
	TagNode rootNode;
	Map<String, Room> roomsState = new HashMap<String, Room>();

    public Rooms(InputStream in) throws IOException
    {
        HtmlCleaner cleaner = new HtmlCleaner();
        rootNode = cleaner.clean(in);
    }
    
    public void getRoomsStates() {
    	List<TagNode> roomsList = getRoomsList();
    	for (Iterator<TagNode> iterator = roomsList.iterator(); iterator.hasNext();)
        {
            TagNode trElement = (TagNode) iterator.next();
            TagNode[] cells = trElement.getElementsByName("td", false);
            if (cells.length > 2) {
            	Room result = new Room();
            	
            	Pattern p = Pattern.compile("(\\d+)");
            	Matcher m = p.matcher(cells[3].getText().toString());
            	
            	result.building = cells[0].getText().toString();
            	result.room = cells[1].getText().toString();
            	Log.v("Rooms", result.room);
            	if (m.find()) {
            		result.occupied = Integer.parseInt(m.group());
            	} else {
            		result.occupied = 0;
            	}
            	if (m.find()) {
            		result.total = Integer.parseInt(m.group());
            	} else {
            		result.total = 0;
            	}
            	
        		TagNode[] links = cells[4].getElementsByName("a", false);
        		result.link = links[0].getAttributeByName("href");
            	roomsState.put(result.room, result);
            }
        }
    }

    List<TagNode> getRoomsList()
    {
        List<TagNode> roomsList = new ArrayList<TagNode>();

        TagNode rowElements[] = rootNode.getElementsByName("tr", true);
        for (int i = 1; rowElements != null && i < rowElements.length; i++)
        {
            roomsList.add(rowElements[i]);
        }

        return roomsList;
    }
    
    Room getRoom(String room) {
    	if (roomsState.isEmpty()) {
    		getRoomsStates();
    	}
    	return roomsState.get(room);
    }
}
