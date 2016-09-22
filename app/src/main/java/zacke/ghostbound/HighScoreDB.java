package zacke.ghostbound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Databse class which holds all player data for the game for when a player
 * chooses to save it's high score. It saves the player name and player score
 * which is inserted into a SQLite databse.
 *
 * Created by Zacke on 2016-09-22.
 */
public class HighScoreDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "HIGHSCORE.db";
    public static final String TABLE_NAME = "HIGHSCORETABLE";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_SCORE = "SCORE";


    public HighScoreDB(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, NAME TEXT, SCORE INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * This method is used to clear the data in the database
     */
    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    /**
     * Inserts data into the database
     *
     * @param name player name to insert into databse
     * @param score player score to insert into database
     * @return true or false weither the data was inserted correctly
     */
    public boolean insertData(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL_NAME, name);
        content.put(COL_SCORE, score);
        long result = db.insert(TABLE_NAME, null, content);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Returns the data from the database
     *
     * @return Cursor containing database data
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY "
                + COL_SCORE + " DESC", null);
        return data;

    }
}
