package com.delta.delta_organiser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLReminders {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	public static final String KEY_EVENT = "event";
	public static final String KEY_DESC = "description";

	private static final String DATABASE_NAME = "Remindersdb";
	private static final String DATABASE_TABLE = "Reminder";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE
			+ " TEXT NOT NULL, " + KEY_TIME +" TEXT NOT NULL, "+ KEY_EVENT + " TEXT NOT NULL, " + KEY_DESC +");";
	
	ContactClass cc;
	SQLiteDatabase db;
	Context c;
	
	private class ContactClass extends SQLiteOpenHelper{

		public ContactClass(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION );
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	public SQLReminders(Context context){
		c=context;
	}
	public SQLReminders open() throws SQLException{
		cc=new ContactClass(c);
		db=cc.getWritableDatabase();
		return this;
		
	}
	
	public SQLReminders close(){
		cc.close();
		return this;
	}
	public long insertData(String date, String time, String event, String desc) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(KEY_DATE, date);
		cv.put(KEY_TIME, time);
		cv.put(KEY_EVENT, event);
		cv.put(KEY_DESC, desc);
		return db.insert(DATABASE_TABLE, null, cv);
	}
	public String getData() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID,KEY_TIME,KEY_DATE,KEY_EVENT, KEY_DESC };
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";
		
		int iDate = c.getColumnIndex(KEY_DATE);
		int iEvent = c.getColumnIndex(KEY_EVENT);
		int iTime=c.getColumnIndex(KEY_TIME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iDate)
					+ "\t\t" + c.getString(iTime)+ "\t\t\t" + c.getString(iEvent) + "::";
		}
		return result;
	}
	public String getData(String date) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_DATE,KEY_TIME,KEY_EVENT, KEY_DESC };
		Cursor c = db.query(DATABASE_TABLE, columns, KEY_DATE + "=" + "'"+date+"'", null,null, null, null);
		String result;
		int iEvent = c.getColumnIndex(KEY_EVENT);
		int iDesc = c.getColumnIndex(KEY_DESC);
		int iTime=c.getColumnIndex(KEY_TIME);
		
			c.moveToFirst();
			result =c.getString(iTime)+";"+c.getString(iEvent) + ";"+c.getString(iDesc);
			c.close();
			return result;		
	}
	public String getData(String date, String time) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_DATE,KEY_TIME,KEY_EVENT, KEY_DESC };
		Cursor c = db.query(DATABASE_TABLE, columns, KEY_DATE + "=" + "'"+date+"'", null,null, null, null);
		String result;
		int iEvent = c.getColumnIndex(KEY_EVENT);
		int iDesc = c.getColumnIndex(KEY_DESC);
		int iTime=c.getColumnIndex(KEY_TIME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = c.getString(iTime)+";" + c.getString(iEvent) + ";"+c.getString(iDesc);
			if(result.contains(time+";")){
				c.close();
				return result;
		}}
		return null;
			
	}
	public String getTimes(String date) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_DATE,KEY_TIME};
		Cursor c = db.query(DATABASE_TABLE, columns, KEY_DATE + "=" + "'"+date+"'", null, null, null,null);
		String result = "";
		int iTime=c.getColumnIndex(KEY_TIME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iTime)+"::";
		}
		return result;
	}

	
}
