package com.ajanovski.booklistapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajanovski.booklistapp.R;
import com.ajanovski.booklistapp.interfaces.BooksInterface;
import com.ajanovski.booklistapp.model.Book;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private ArrayList<Book> books;
    private BooksInterface listener;

    public BooksAdapter(ArrayList<Book> books, BooksInterface listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_book, null);

        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvBookName.setText(book.getName());
        holder.tvBookAuthor.setText(book.getAuthor());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteBook(book);
            }
        });

        holder.btnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.editBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookName, tvBookAuthor;
        ImageButton btnDelete, btnEditBook;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            btnDelete = itemView.findViewById(R.id.btnDeleteBook);
            btnEditBook = itemView.findViewById(R.id.btnEditBook);

        }
    }

}
