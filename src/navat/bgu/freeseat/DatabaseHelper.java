package navat.bgu.freeseat;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	static final String dbName="FreeSeatDB";
	static final String roomsTable="Rooms";
	static final String colRoom="Room";
	static final String colBuilding="Building";
	static final String colOccupied="occupied";
	static final String colTotal="Total";
	static final String colLink="Link";
	static final String colRate="Rate";
	
	public DatabaseHelper(Context context) {
		  super(context, dbName, null, 1); 
		  }
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		  
		  db.execSQL("CREATE TABLE "+roomsTable+
				  " ("+colRoom+ " TEXT PRIMARY KEY , "+
				  colBuilding+ " TEXT, " + colOccupied + " INTEGER, " +
				  colTotal + " INTEGER, " + colLink + " TEXT, " +
				  colRate + " TEXT)");
		 }
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  // TODO Auto-generated method stub
		  db.execSQL("DROP TABLE IF EXISTS "+roomsTable);

		  onCreate(db);
		 }
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> result = new ArrayList<Room>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(roomsTable, new String[] { colRoom,
				colBuilding, colOccupied, colTotal, colLink, colRate },
				null, null, null, null, null, null);
	    if (cursor != null && cursor.getCount() > 0) {
	    	cursor.moveToFirst();
	    	while (!cursor.isAfterLast()) { 
			    Room room = new Room();
			    room.room = cursor.getString(0);
			    room.building = cursor.getString(1);
			    room.occupied = Integer.parseInt(cursor.getString(2));
			    room.total = Integer.parseInt(cursor.getString(3));
			    room.link = cursor.getString(4);
			    room.rate = Integer.parseInt(cursor.getString(5));
			    
			    result.add(room);
			    
		    	if (!cursor.moveToNext()) {
		    		// TODO check return value! raise exception on false!
		    	}
	    	}
	    }
	    return result;
	}
	public Room getRoom(String roomName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(roomsTable, new String[] { colRoom,
				colBuilding, colOccupied, colTotal, colLink, colRate }, colRoom + "=?",
	            new String[] { roomName }, null, null, null, null);
	    if (cursor != null && cursor.getCount() > 0) {
	        cursor.moveToFirst();
	 
		    Room room = new Room();
		    room.room = cursor.getString(0);
		    room.building = cursor.getString(1);
		    room.occupied = Integer.parseInt(cursor.getString(2));
		    room.total = Integer.parseInt(cursor.getString(3));
		    room.link = cursor.getString(4);
		    room.rate = Integer.parseInt(cursor.getString(5));
		    
		    // return contact
		    return room;
	    } else {
	    	return null;
	    }
	}
	public void setRoom(Room room) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (getRoom(room.room) == null) {
			ContentValues values = new ContentValues();
			
			values.put(colRoom, room.room);
			values.put(colBuilding, room.building);
			values.put(colOccupied, room.occupied);
			values.put(colTotal, room.total);
			values.put(colLink, room.link);
			values.put(colRate, room.rate);
			
			db.insert(roomsTable, null, values);
		} else {
			ContentValues values = new ContentValues();
			
			values.put(colRoom, room.room);
			values.put(colBuilding, room.building);
			values.put(colOccupied, room.occupied);
			values.put(colTotal, room.total);
			values.put(colLink, room.link);
			values.put(colRate, room.rate);
			
			db.update(roomsTable, values, colRoom + " = ?",
		            new String[] { room.room });
		}
	}
	public boolean isRoomsTableEmpty() {
		boolean result = true;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + roomsTable, null);
		if (cur == null) {
			result = true;
		} else {
		    cur.moveToFirst();                       // Always one row returned.
		    if (cur.getInt (0) == 0) {               // Zero count means empty table.
		        result = true;
		    } else {
		    	result = false;
		    }
		}
		return result;
	}
}
