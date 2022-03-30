package com.ajanovski.booklistapp.util;

import com.ajanovski.booklistapp.model.Book;
import com.ajanovski.booklistapp.model.User;

import java.util.ArrayList;

public final class AppHolder {

   public static  ArrayList<User> users = new ArrayList<>();
   public static  ArrayList<Book> books = new ArrayList<>();

   public static User loggedInUser = new User();

}
