package com.ajanovski.booklistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int id;
    private String name;
    private String author;
    private int insertedByUser;

    public Book() {

    }

    public Book(String name, String author, int insertedByUser) {
        this.name = name;
        this.author = author;
        this.insertedByUser = insertedByUser;
    }

    protected Book(Parcel in) {
        name = in.readString();
        author = in.readString();
        insertedByUser = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getInsertedByUser() {
        return insertedByUser;
    }

    public void setInsertedByUser(int insertedByUser) {
        this.insertedByUser = insertedByUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(author);
        parcel.writeInt(insertedByUser);
    }
}
