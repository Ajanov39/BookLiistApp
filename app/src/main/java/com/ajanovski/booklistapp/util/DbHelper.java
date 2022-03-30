package com.ajanovski.booklistapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ajanovski.booklistapp.model.Book;
import com.ajanovski.booklistapp.model.User;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {




    public DbHelper(Context context) {
        super(context,
                Constants.DB_NAME, null, Constants.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.SQL_QUERY_TABLE_USER);
        sqLiteDatabase.execSQL(Constants.SQL_QUERY_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_BOOK);
        onCreate(sqLiteDatabase);
    }

    public long createUser(String username, String password) {
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put(Constants.USER_USERNAME, username);
        values.put(Constants.USER_PASSWORD, password);
        long id = db.insert("User", null, values);
        return id;
    }

    public long createBook(String name, String author, int createdBy) {
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put(Constants.BOOK_NAME, name);
        values.put(Constants.BOOK_AUTHOR, author);
        values.put(Constants.BOOK_CREATED_BY, createdBy);

        long id = db.insert(Constants.TABLE_BOOK, null, values);
        return id;

    }


    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username = "
                + "\"" + username + "\"" + " AND password = "+ "\"" + password + "\"", null);
        User user = new User();
        if(cursor.moveToFirst()) {
            do {
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
            } while (cursor.isAfterLast());
        }
        return user;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username = "
                + "\"" + username + "\"", null);
        User user = new User();
        if(cursor.moveToFirst()) {
            do {
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
            } while (cursor.isAfterLast());
        }
        return user;
    }

    public ArrayList<Book> getBooksByUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BOOK WHERE createdBy = "
                + "\"" + id + "\"", null);
        ArrayList<Book> booksList = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setName(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setInsertedByUser(Integer.parseInt(cursor.getString(3)));
                booksList.add(book);
            } while (cursor.moveToNext());
        }
        return booksList;
    }


    public void closeDb(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }

}
