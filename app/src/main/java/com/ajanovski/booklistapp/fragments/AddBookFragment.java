package com.ajanovski.booklistapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ajanovski.booklistapp.MainActivity;
import com.ajanovski.booklistapp.R;
import com.ajanovski.booklistapp.model.Book;
import com.ajanovski.booklistapp.util.AppHolder;
import com.ajanovski.booklistapp.util.Constants;
import com.ajanovski.booklistapp.util.DbHelper;


public class AddBookFragment extends Fragment {

    Button btnAddBook;
    EditText etBookName, etBookAuthor;
    Book book;

    public AddBookFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(Constants.EXTRA_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAddBook = view.findViewById(R.id.btnAddBook);
        etBookName = view.findViewById(R.id.etBookName);
        etBookAuthor = view.findViewById(R.id.etBookAuthor);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(checkFields()) {
                    enableButton(true);
                } else {
                    enableButton(false);
                }
            }
        };


        etBookName.addTextChangedListener(textWatcher);
        etBookAuthor.addTextChangedListener(textWatcher);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etBookName.getText().toString();
                String author = etBookAuthor.getText().toString();

                DbHelper helper = ((MainActivity)getActivity()).getHelper();

                long id = helper.createBook(name, author, AppHolder.loggedInUser.getId());

                if(id > 0) {
                    Toast.makeText(requireContext(), "Successful book creation!", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireContext(), "Error book creation!", Toast.LENGTH_SHORT).show();
                }

//                SharedPreferences sharedPreferences =
//                        requireActivity().getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
//                Gson gson = new Gson();
//
//                String jsonBooks = sharedPreferences.getString(Constants.PREF_BOOKS, "");
//                String jsonArray = "";
//
//                if (book != null) {
//                    Type type = new TypeToken<ArrayList<Book>>() {}.getType();
//                    ArrayList<Book> outputList = gson.fromJson(jsonBooks, type);
//
//                    int bookIndex = -1;
//
//                    for(int i = 0; i< outputList.size(); i++) {
//                        Book booktmp = outputList.get(i);
//                        if(booktmp.getName().equals(book.getName()) &&
//                                booktmp.getAuthor().equals(book.getAuthor()) &&
//                                    booktmp.getInsertedByUser() == book.getInsertedByUser()) {
//                            bookIndex = i;
//                        }
//                    }
//
//                    book.setName(etBookName.getText().toString());
//                    book.setAuthor(etBookAuthor.getText().toString());
//                    outputList.set(bookIndex, book);
//
//                    jsonArray = gson.toJson(outputList);
//                } else {
//                    Book book = new Book(name, author, AppHolder.loggedInUser.getId());
//
//                    if (jsonBooks.equals("")) {
//                        AppHolder.books.add(book);
//                        jsonArray = gson.toJson(AppHolder.books);
//                    } else {
//                        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
//                        ArrayList<Book> outputList = gson.fromJson(jsonBooks, type);
//
//                        outputList.add(book);
//
//                        jsonArray = gson.toJson(outputList);
//
//                    }
//                }
//
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString(Constants.PREF_BOOKS, jsonArray);
//                editor.apply();
//
//                requireActivity().onBackPressed();
            }
        });

        if(book != null) {
            etBookName.setText(book.getName());
            etBookAuthor.setText(book.getAuthor());
            btnAddBook.setText(R.string.update_book);
        } else {
            btnAddBook.setText(R.string.add_book);
        }

    }


    private void enableButton(Boolean enabled) {
        btnAddBook.setEnabled(enabled);
    }

    private boolean checkFields() {
        if(etBookName.getText().toString().length() > 3 && etBookAuthor.getText().toString().length() > 3) {
            return true;
        } else {
            return false;
        }
    }

}