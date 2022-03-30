package com.ajanovski.booklistapp.util;

public final class Constants {

    //Shared preferences constants
    public static final String PREF_ID_COUNTER = "PREF_ID_COUNTER";
    public static final String PREF_USERS = "PREF_USERS";
    public static final String PREF_USER = "PREF_USER";
    public static final String PREF_BOOKS = "PREF_BOOKS";

    //Bundle constants
    public static final String EXTRA_BOOK = "EXTRA_BOOK";


    //SQLite Constants
    public static final String DB_NAME = "Books";
    public static final int DB_VERSION = 3;


    public static final String TABLE_BOOK = "Book";
    public static final String TABLE_USER = "User";

    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";

    public static final String BOOK_ID = "id";
    public static final String BOOK_NAME = "name";
    public static final String BOOK_AUTHOR = "author";
    public static final String BOOK_CREATED_BY = "createdBy";

    public static final String SQL_QUERY_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_USERNAME + " VARCHAR(40),"
            + USER_PASSWORD + " VARCHAR(40))";

    public static final String SQL_QUERY_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "("
            + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BOOK_NAME + " VARCHAR(60),"
            + BOOK_AUTHOR + " VARCHAR(60),"
            + BOOK_CREATED_BY + " INTEGER)";
}
