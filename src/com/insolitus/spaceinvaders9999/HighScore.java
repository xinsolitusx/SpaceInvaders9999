package com.insolitus.spaceinvaders9999;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScore {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_HIGHSCORE = "persons_highscore";

	private static final String DATABASE_NAME = "HighScoreDB";
	private static final String DATABASE_TABLE = "highScoreTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL, " + KEY_HIGHSCORE + " INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public HighScore(Context c) {
		ourContext = c;
		//ourContext.deleteDatabase(DATABASE_NAME);
	}

	public HighScore open() {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String name, int score) {
		// TODO Auto-generated method stub
		long n = 0;
		ContentValues contentVal = new ContentValues();
		contentVal.put(KEY_NAME, name);
		contentVal.put(KEY_HIGHSCORE, score);			
		
		ourDatabase.beginTransaction();
		try {
			n = ourDatabase.insert(DATABASE_TABLE, null, contentVal);
		    ourDatabase.setTransactionSuccessful();
		} catch (Exception e){
		    //Error in between database transaction 
			e.printStackTrace();
		} finally {
		    ourDatabase.endTransaction();
		}
		
		return n;
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_HIGHSCORE };
		Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, KEY_HIGHSCORE + " DESC", "10");		
		String result = "";

		int iRow = cursor.getColumnIndex(KEY_ROWID);
		int iName = cursor.getColumnIndex(KEY_NAME);
		int iHighScore = cursor.getColumnIndex(KEY_HIGHSCORE);
		if (iRow >= 10) {
			for (cursor.moveToFirst(); iRow <= 10; cursor.moveToNext()) {
				result = result + cursor.getString(iHighScore) + " " + cursor.getString(iName) + "\n\n";
			}
		} else {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				result = result + cursor.getString(iHighScore) + " " + cursor.getString(iName) + "\n\n";
			}
		}

		return result;
	}

}
