package tecorb.hafijur.calendar.SQLDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tecorb.hafijur.calendar.Model.DBModel;

import java.util.ArrayList;
import java.util.List;

public class HRDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "demo1.db";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE = "kDate";
    private static final String KEY_ID = "id";
    private static final int DATABASE_VERSION = 1;

    public HRDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " text," + KEY_DESC + " text,"
                + KEY_DATE + " text" + ")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);

    }

    public void insertTitle(DBModel information) {

        SQLiteDatabase dataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, information.getTitle());
        values.put(KEY_DATE, information.getDate());

        long id = dataBase.insert(TABLE_NOTES, null, values);
        Log.i("id", "" + id);
        close();
    }

    public List<DBModel> viewNotes(String date) {

        SQLiteDatabase database = this.getReadableDatabase();
        List<DBModel> setList = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_DATE + "='" + date + "'";
        // String selectQuery = "SELECT  * FROM " + TABLE_NOTES ;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DBModel dbModelClass = new DBModel();
                dbModelClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                dbModelClass.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                dbModelClass.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                setList.add(dbModelClass);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return setList;
    }

    public void deleteNotes(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NOTES, "" + KEY_TITLE + " = '" + title + "'", null);
        close();
    }

    public void close() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void updateData(DBModel modelClass) {

        SQLiteDatabase dataBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, modelClass.getTitle());
        values.put(KEY_DATE, modelClass.getDate());
        /* dataBase.update(TABLE_NOTES, values, "" + KEY_ID + " = ?", new String[]{String.valueOf(modelClass.getId())});*/
        dataBase.update(TABLE_NOTES, values, "" + KEY_ID + " = '" + modelClass.getId() + "'", null);
        close();
    }

    public List<String> getAllDate() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<String> setList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT " + KEY_DATE + "  FROM " + TABLE_NOTES;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DBModel dbModelClass = new DBModel();
                dbModelClass.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                //setList.add(dbModelClass.date);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return setList;
    }

    public String deleteDate() {
        String date = null;
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NOTES, "" + KEY_TITLE + " = '" + date + "'", null);
        close();
        return date;
    }
}
