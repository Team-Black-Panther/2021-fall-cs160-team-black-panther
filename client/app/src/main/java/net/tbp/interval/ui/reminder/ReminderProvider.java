package net.tbp.interval.ui.reminder;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

// class that will support to CRUD reminderDB
public class ReminderProvider extends ContentProvider {
    // provider name
    public static final String PROVIDER_NAME = "net.tbp.interval.mycontentprovider.ReminderInfo";
    // URL
    public static final String URL = "content://" + PROVIDER_NAME + "/reminders";
    // set content uri
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // index that will store in the database
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String DUEDATE = "duedate";
    public static final String COMPLETEDDATE = "completeddate";

    private static HashMap<String, String> REMINDERS_PROJECTION_MAP;
    public static final int REMINDERS = 1;
    public static final int REMINDERS_ID = 2;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "reminders", REMINDERS);
        uriMatcher.addURI(PROVIDER_NAME, "REMINDERS/#", REMINDERS_ID);
    }

    // Database specific constant declaration
    private SQLiteDatabase db;
    // reminder database name
    static final String DATABASE_NAME = "ReminderDB";
    // table name
    static final String REMINDER_TABLE_NAME = "Reminders";
    // database version
    static final int DATABASE_VERSION = 1;
    // to create reminder table
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + REMINDER_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " description TEXT, " +
                    " status BOOLEAN, " +
                    " priority INTEGER, "+
                    " duedate DATE, "+
                    " completeddate DATE );";

    // Helper class that actually create and manage the provider's underlying data repository
    private static class DatabaseHelper extends SQLiteOpenHelper{
        // structure
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + REMINDER_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        // Create a write able database which will trigger its creation if it does not already exists
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(REMINDER_TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case REMINDERS:
                qb.setProjectionMap(REMINDERS_PROJECTION_MAP);
                break;
            case REMINDERS_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            // by default sort on id
            sortOrder = _ID;
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null,null, sortOrder);

        // register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            // Get all reminder records
            case REMINDERS:
                return  "vnd.android.cursor.dir/vnd.example.reminders";
            // get particular cursor
            case REMINDERS_ID:
                return "vnd.android.cursor.item/vnd.example.reminders_id";
            default:
                throw new IllegalArgumentException("Unsoutport URI: " + uri);
        }
    }

    // func to insert into db
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //Add a new student record
        long rowID = db.insert(REMINDER_TABLE_NAME, "", values);
        // If record is add successfully
        if(rowID > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return  _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    // fuc to delete data
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] stringsArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case REMINDERS:
                count = db.delete(REMINDER_TABLE_NAME, selection, stringsArgs);
                break;
            case REMINDERS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(REMINDER_TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), stringsArgs);
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
        return count;
    }

    // func to update data
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case REMINDERS:
                count = db.update(REMINDER_TABLE_NAME, values, selection, selectionArgs);
                break;
            case REMINDERS_ID:
                count = db.update(REMINDER_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
