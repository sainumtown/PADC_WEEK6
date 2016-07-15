package xyz.sainumtown.padc_week6.datas.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by aung on 7/10/16.
 */
public class AttractionProvider extends ContentProvider {

    public static final int ATTRACTION = 100;
    public static final int ATTRACTION_IMAGE = 200;
    public static final int ATTRACTION_USER = 300;

    private static final String sAttractionTitleSelection = AttractionsContract.AttractionEntry.COLUMN_TITLE + " = ?";
    private static final String sAttractionImageSelectionWithTitle = AttractionsContract.AttractionImageEntry.COLUMN_ATTRACTION_TITLE + " = ?";

    private static final String sAttractionUserEmailSelection = AttractionsContract.UserEntry.COLUMN_EMAIL + " = ?";

    private static final UriMatcher sUriMatcher = buildUriMatcher();//1
    private AttractionDBHelper mAttractionDBHelper;

    @Override
    public boolean onCreate() {//2
        mAttractionDBHelper = new AttractionDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {//6 projection means return column name, selection means where condition
        Cursor queryCursor;

        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case ATTRACTION:
                String attractionTitle = AttractionsContract.AttractionEntry.getTitleFromParam(uri);
                if (!TextUtils.isEmpty(attractionTitle)) {
                    selection = sAttractionTitleSelection;
                    selectionArgs = new String[]{attractionTitle};
                }
                queryCursor = mAttractionDBHelper.getReadableDatabase().query(AttractionsContract.AttractionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case ATTRACTION_IMAGE:
                String title = AttractionsContract.AttractionImageEntry.getAttractionTitleFromParam(uri);
                if (title != null) {
                    selection = sAttractionImageSelectionWithTitle;
                    selectionArgs = new String[]{title};
                }
                queryCursor = mAttractionDBHelper.getReadableDatabase().query(AttractionsContract.AttractionImageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case ATTRACTION_USER:
                String email = AttractionsContract.UserEntry.getUserEmailFromParam(uri);
                if (email != null) {
                    selection = sAttractionUserEmailSelection;
                    selectionArgs = new String[]{email};
                }
                queryCursor = mAttractionDBHelper.getReadableDatabase().query(AttractionsContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {//3
        final int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case ATTRACTION:
                return AttractionsContract.AttractionEntry.DIR_TYPE;
            case ATTRACTION_IMAGE:
                return AttractionsContract.AttractionImageEntry.DIR_TYPE;
            case ATTRACTION_USER:
                return AttractionsContract.UserEntry.DIR_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {//4
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        final int matchUri = sUriMatcher.match(uri);
        Uri insertedUri;

        switch (matchUri) {
            case ATTRACTION: {
                long _id = db.insert(AttractionsContract.AttractionEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = AttractionsContract.AttractionEntry.buildAttractionUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ATTRACTION_IMAGE: {
                long _id = db.insert(AttractionsContract.AttractionImageEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = AttractionsContract.AttractionImageEntry.buildAttractionImageUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ATTRACTION_USER: {
                long _id = db.insert(AttractionsContract.UserEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = AttractionsContract.UserEntry.buildUserUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();//
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);// automatic notify the ui
        }// when call getCOntentResolver means call the content Provider

        return insertedUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {//5 (optional) handle to crud return network data
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {//7
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {//6
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {//1.1 important match the constant for part( table )
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AttractionsContract.CONTENT_AUTHORITY, AttractionsContract.PATH_ATTRACTIONS, ATTRACTION);
        uriMatcher.addURI(AttractionsContract.CONTENT_AUTHORITY, AttractionsContract.PATH_ATTRACTION_IMAGES, ATTRACTION_IMAGE);
        uriMatcher.addURI(AttractionsContract.CONTENT_AUTHORITY, AttractionsContract.PATH_USERS, ATTRACTION_USER);
        return uriMatcher;
    }

    private String getTableName(Uri uri) {//5.1
        final int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case ATTRACTION:
                return AttractionsContract.AttractionEntry.TABLE_NAME;
            case ATTRACTION_IMAGE:
                return AttractionsContract.AttractionImageEntry.TABLE_NAME;
            case ATTRACTION_USER:
                return AttractionsContract.UserEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}