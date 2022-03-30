package com.ajanovski.booklistapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ajanovski.booklistapp.MainActivity;
import com.ajanovski.booklistapp.R;
import com.ajanovski.booklistapp.adapters.BooksAdapter;
import com.ajanovski.booklistapp.interfaces.BooksInterface;
import com.ajanovski.booklistapp.model.Book;
import com.ajanovski.booklistapp.util.AppHolder;
import com.ajanovski.booklistapp.util.Constants;
import com.ajanovski.booklistapp.util.DbHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainFragment extends Fragment implements BooksInterface {

    FloatingActionButton btnAddBook;
    RecyclerView rvBooks;
    TextView tvNoBooks;
    Button btnLogout;
    SharedPreferences preferences;


    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBooks = view.findViewById(R.id.rvBooks);
        btnAddBook = view.findViewById(R.id.btnAddBook);
        tvNoBooks = view.findViewById(R.id.tvNoBooks);
        btnLogout = view.findViewById(R.id.btnLogout);

        preferences = requireActivity()
                .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = requireActivity()
                        .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.PREF_USER, "");
                editor.apply();

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentCotainer, new LoginFragment())
                        .commit();
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentCotainer, new AddBookFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public void deleteBook(Book book) {
        Gson gson = new Gson();

        String jsonArrayBooks = preferences.getString(Constants.PREF_BOOKS, "");

        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
        ArrayList<Book> outputList = gson.fromJson(jsonArrayBooks, type);

        ArrayList<Book> booksByUser = new ArrayList<>();

        for(int i = 0; i< outputList.size(); i++) {
            Book booktmp = outputList.get(i);
            if(booktmp.getName().equals(book.getName()) && booktmp.getAuthor().equals(book.getAuthor())
                    && booktmp.getInsertedByUser() == AppHolder.loggedInUser.getId()) {
                outputList.remove(i);
            }
        }

        for(int i = 0; i< outputList.size(); i++) {
            Book booktmp = outputList.get(i);
            if(booktmp.getInsertedByUser() == AppHolder.loggedInUser.getId()) {
                booksByUser.add(booktmp);
            }
        }// Shows books by logged in user

        booksByUser.remove(book);
        String outputListJson = gson.toJson(outputList);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PREF_BOOKS, outputListJson);
        editor.apply();

        rvBooks.setAdapter(null);
        BooksAdapter adapter = new BooksAdapter(booksByUser, this);
        rvBooks.setAdapter(adapter);

    }

    @Override
    public void editBook(Book book) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_BOOK, book);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentCotainer, AddBookFragment.class, bundle)
                .addToBackStack(null)
                .commit();
    }

    private void setupBooks() {

        DbHelper helper = ((MainActivity) getActivity()).getHelper();
        ArrayList<Book> books = helper.getBooksByUser(AppHolder.loggedInUser.getId());

        if (books.size() == 0) {
            rvBooks.setVisibility(View.GONE);
            tvNoBooks.setVisibility(View.VISIBLE);
        } else {
            rvBooks.setVisibility(View.VISIBLE);
            tvNoBooks.setVisibility(View.GONE);

            BooksAdapter adapter = new BooksAdapter(books, this);
            rvBooks.setAdapter(adapter);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        setupBooks();
    }
}