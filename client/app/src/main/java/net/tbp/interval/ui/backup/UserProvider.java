package net.tbp.interval.ui.backup;

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

import java.util.HashMap;

public class UserProvider extends ContentProvider {
    // provider name
    public static final String PROVIDER_NAME = "net.tbp.interval.mycontentprovider.UserInfo";
    // URL
    public static final String URL = "content://" + PROVIDER_NAME + "/user";
    // set content uri
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // index that will store in the database
    public static final String _ID = "_id";
    public static final String IDENTIFIER = "identifier";
    public static final String UID = "uid";

    private static HashMap<String, String> USER_PROJECTION_MAP;
    public static final int USER = 1;
    public static final int USER_ID = 2;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "user", USER);
        uriMatcher.addURI(PROVIDER_NAME, "USER/#", USER_ID);
    }

    // Database specific constant declaration
    private SQLiteDatabase db;
    // user database name
    static final String DATABASE_NAME = "UserDB";
    // table name
    static final String USER_TABLE_NAME = "Users";
    // database version
    static final int DATABASE_VERSION = 1;
    // to create user table
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + USER_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " identifier TEXT NOT NULL, " +
                    " uid TEXT );";

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
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new UserProvider.DatabaseHelper(context);

        // Create a write able database which will trigger its creation if it does not already exists
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(USER_TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case USER:
                qb.setProjectionMap(USER_PROJECTION_MAP);
                break;
            case USER_ID:
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
            // Get all user records
            case USER:
                return  "vnd.android.cursor.dir/vnd.example.user";
            // get particular cursor
            case USER_ID:
                return "vnd.android.cursor.item/vnd.example.user_id";
            default:
                throw new IllegalArgumentException("Unsupport URI: " + uri);
        }
    }

    // func to insert into db
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //Add a new student record
        long rowID = db.insert(USER_TABLE_NAME, "", values);
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
            case USER:
                count = db.delete(USER_TABLE_NAME, selection, stringsArgs);
                break;
            case USER_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(USER_TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), stringsArgs);
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
            case USER:
                count = db.update(USER_TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_ID:
                count = db.update(USER_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

