package com.exchange.suppliesme;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Objects;

public abstract class MatchProvider extends ContentProvider {

    //Database columns
    public static final String COLUMN_USERID = "_id";
    public static final String COLUMN_MESSENGERNAME = "messengername";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ZIPCODE = "zip_code";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PURPOSE = "purpose";

    //Database related constants
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "users";

    //The database itself
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        //Grab a connection to our database
        db = new DatabaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    /**
     * A helper class which knows how to create and update our database.
     */
    protected static class DatabaseHelper extends SQLiteOpenHelper {
        static final String DATABASE_CREATE =
                "create table " + DATABASE_TABLE + " (" +
                        COLUMN_USERID + " integer primary key autoincrement, " +
                        COLUMN_MESSENGERNAME + " text not null, " +
                        COLUMN_EMAIL + " text not null, " +
                        COLUMN_ZIPCODE + " text not null, " +
                        COLUMN_PASSWORD + " text not null, " +
                        COLUMN_PURPOSE + " text not null);";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            throw new UnsupportedOperationException();
        }
    }

    //Content Provider Uri and Authority
    public static final String AUTHORITY
            = "com.exchange.suppliesme.MatchProvider";
    public static final Uri CONTENT_URI
            = Uri.parse("content://" + AUTHORITY + "/user");
    //MIME types used for listing users or looking up a single user
    private static final String USERS_MIME_TYPE
            = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.com.exchange.suppliesme.users.users";
    private static final String USER_MIME_TYPE
            = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.com.exchange.suppliesme.users.user";
    //UriMatcher stuff
    private static final int LIST_TASK = 0;
    private static final int ITEM_TASK = 1;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    /**
     * Builds up a UriMatcher for search suggestion and shortcut refresh queries.
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "user", LIST_TASK);
        matcher.addURI(AUTHORITY, "user/#", ITEM_TASK);
        return matcher;
    }
    /**
     * This method is required in order to query the supported types.
     */
    @Override
    public String getType(@NonNull Uri uri ){
        switch (URI_MATCHER.match(uri)) {
            case LIST_TASK:
                return USERS_MIME_TYPE;
            case ITEM_TASK:
                return USER_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    /**
     * This method is called when someone wants to insert something into our content provider.
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        //you can't choose your own task id
        assert values != null;
        if(values.containsKey(COLUMN_USERID))
            throw new UnsupportedOperationException();

        long id = db.insertOrThrow(DATABASE_TABLE, null,
                values);
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * This method is called when someone wants to update something in our content provider.
     */
    @Override
    public int update (@NonNull Uri uri, ContentValues values, String ignored1, String[] ignored2) {
        //you can't change a task id
        assert values != null;
        if(values.containsKey(COLUMN_USERID))
            throw new UnsupportedOperationException();

        int count = db.update(
                DATABASE_TABLE,
                values,
                COLUMN_USERID + "=?",
                new String[]{Long.toString(ContentUris.parseId(uri))});

        if (count > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        return count;
    }

    /**
     * This method is called when someone wants to delete something from our content provider.
     */
    @Override
    public int delete(@NonNull Uri uri, String ignored1, String[] ignored2) {
        int count = db.delete(
                DATABASE_TABLE,
                COLUMN_USERID + "=?",
                new String[]{Long.toString(ContentUris.parseId(uri))});

        if (count > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

        return count;
    }
    /**
     * This method is called when someone wants to read something from our content provider.  We'll turn around
     * and ask our database for the information, and then return it in a Cursor.
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] ignored1, String selection,
                        String[] selectionArgs, String sortOrder) {
        String[] projection = new String[]{
                COLUMN_USERID,
                COLUMN_MESSENGERNAME,
                COLUMN_EMAIL,
                COLUMN_ZIPCODE,
                COLUMN_PASSWORD,
                COLUMN_PURPOSE};

        Cursor c;
        switch (URI_MATCHER.match(uri)){
            case LIST_TASK:
                c = db.query(DATABASE_TABLE,
                        projection, selection,
                        selectionArgs,null, null, sortOrder);
                break;

            case ITEM_TASK:
                c = db.query(DATABASE_TABLE, projection,
                        COLUMN_USERID + "=?",
                        new String[]{Long.toString(ContentUris.parseId(uri))},
                        null, null, null, null);
                if (c.getCount() > 0) {
                    c.moveToFirst();
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        c.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return c;
    }
}