package com.ajanovski.booklistapp.interfaces;

import com.ajanovski.booklistapp.model.Book;

public interface BooksInterface {
    void deleteBook(Book book);
    void editBook(Book book);
}
