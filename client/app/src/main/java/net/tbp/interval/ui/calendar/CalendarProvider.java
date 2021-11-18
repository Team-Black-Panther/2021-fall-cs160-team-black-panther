package net.tbp.interval.ui.calendar;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.util.HashMap;

// class that will support to CRUD calendarDB
public class CalendarProvider extends ContentProvider {
    // provider name
    static final String PROVIDER_NAME = "net.tbp.interval.mycontentprovider.CalendarInfo";
    // URL
    static final String URL = "content://" + PROVIDER_NAME + "/calendar";
    // set content uri
    static final Uri CONTENT_URI = Uri.parse(URL);

    // index that will store in the database
    static final String _ID = "_id";
    static final String NAME = "name";
    static final String DUEDATE = "enddate";
    //static final DateTime TIME;


    private static HashMap<String, String> CALENDAR_PROJECTION_MAP;
    static final int CALENDAR = 1;
    static final int CALENDAR_ID = 2;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "calendar", CALENDAR);
        uriMatcher.addURI(PROVIDER_NAME, "CALENDAR/#", CALENDAR_ID);
    }

    // Database specific constant declaration
    private SQLiteDatabase db;
    // reminder database name
    static final String DATABASE_NAME = "CalendarDB";
    // table name
    static final String CALENDAR_TABLE_NAME = "Calendar";
    // database version
    static final int DATABASE_VERSION = 1;
    // to create reminder table
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + CALENDAR_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " enddate DATE );";

    // Helper class that actually create and manage the provider's underlying data repository
    private static class DatabaseHelper extends SQLiteOpenHelper {
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
            db.execSQL("DROP TABLE IF EXISTS " + CALENDAR_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        net.tbp.interval.ui.calendar.CalendarProvider.DatabaseHelper dbHelper = new net.tbp.interval.ui.calendar.CalendarProvider.DatabaseHelper(context);

        // Create a write able database which will trigger its creation if it does not already exists
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(CALENDAR_TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case CALENDAR:
                qb.setProjectionMap(CALENDAR_PROJECTION_MAP);
                break;
            case CALENDAR_ID:
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
            case CALENDAR:
                return  "vnd.android.cursor.dir/vnd.example.calendar";
            // get particular cursor
            case CALENDAR_ID:
                return "vnd.android.cursor.item/vnd.example.calendar_id";
            default:
                throw new IllegalArgumentException("Unsoutport URI: " + uri);
        }
    }

    // func to insert into db
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //Add a new student record
        long rowID = db.insert(CALENDAR_TABLE_NAME, "", values);
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
            case CALENDAR:
                count = db.delete(CALENDAR_TABLE_NAME, selection, stringsArgs);
                break;
            case CALENDAR_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(CALENDAR_TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), stringsArgs);
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
            case CALENDAR:
                count = db.update(CALENDAR_TABLE_NAME, values, selection, selectionArgs);
                break;
            case CALENDAR_ID:
                count = db.update(CALENDAR_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
