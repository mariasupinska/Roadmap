package com.example.roadmap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendedBooksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_books);
        fetchBooksData("Git");
    }

    private void fetchBooksData(String query) {
        String finalQuery = prepareQuery(query);
        BookService bookService = RetrofitInstance.getRetrofitInstance().create(BookService.class);

        Call<BookContainer> booksApiCall = bookService.findBooks(finalQuery);

        booksApiCall.enqueue(new Callback<BookContainer>() {
            @Override
            public void onResponse(retrofit2.Call<BookContainer> call, Response<BookContainer> response) {
                if ( response.body() != null ) {
                    setupBookListView(response.body().getBookList());
                } else {
                    Log.d("Books", response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BookContainer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong. Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String prepareQuery(String query) {
        String[] queryParts = query.split("\\s+");
        return TextUtils.join("+", queryParts);
    }

    private void setupBookListView(List<Book> books) {
        recyclerView = findViewById(R.id.recycler_view);
        final BookAdapter adapter = new BookAdapter();

        List<Book> tmpBooks = new LinkedList<>();
        for ( int i = 0; i < 10; i++ ) {
            tmpBooks.add(books.get(i));
        }

        adapter.setBooks(tmpBooks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class BookHolder extends RecyclerView.ViewHolder {
        private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";

        private final TextView bookTitleTextView;
        private final TextView bookAuthorTextView;
        private final ImageView bookCover;

        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.single_book, parent, false));

            bookTitleTextView = itemView.findViewById(R.id.book_title);
            bookAuthorTextView = itemView.findViewById(R.id.book_author);
            bookCover = itemView.findViewById(R.id.img_cover);
        }

        public void bind(Book book) {
            if (book != null && checkNullOrEmpty(book.getTitle()) && book.getAuthors() != null) {
                bookTitleTextView.setText(book.getTitle());
                bookAuthorTextView.setText(TextUtils.join(", ", book.getAuthors()));

                View itemContainer = itemView.findViewById(R.id.book_item_container);
                itemContainer.setOnClickListener(v -> {
                    Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.com/search?q=" + prepareQuery(book.getTitle())));
                    startActivity(viewIntent);
                });

                if (book.getCover() != null) {
                    Picasso.with(itemView.getContext())
                            .load(IMAGE_URL_BASE + book.getCover() + "-S.jpg")
                            .placeholder(R.drawable.ic_baseline_book).into(bookCover);
                } else {
                    bookCover.setImageResource(R.drawable.ic_baseline_book);
                }
            }
        }
    }

    public boolean checkNullOrEmpty(String text) {
        return text != null && !TextUtils.isEmpty(text);
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> books;

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            return new BookHolder(inflate, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            if (books != null) {
                Book book = books.get(position);
                holder.bind(book);
            } else {
                Log.d("BooksActivity", "No books");
            }
        }

        void setBooks(List<Book> books) {
            this.books = books;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }
}