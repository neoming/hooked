package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.BookAdapter;
import com.main.hooker.hooker.adapter.FollowAdapter;
import com.main.hooker.hooker.components.LoginFragment;
import com.main.hooker.hooker.components.ProfileDetailFragment;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.BookModel;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private  SearchView book_searchView=null;
    private  List<Book> searched_books=new ArrayList<>();
    private  BookAdapter bookAdapter=null;
    private  RecyclerView recyclerView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        View header = View.inflate(this,R.layout.header_add_book,null);
        bookAdapter = new BookAdapter(R.layout.item_serach_book,searched_books);
        bookAdapter.addHeaderView(header);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);
        book_searchView = header.findViewById(R.id.bookSearchView);
        if(book_searchView == null){
            Toast.makeText(this,"can not find bookSearchVie",Toast.LENGTH_SHORT).show();
        }else {
            book_searchView.setIconifiedByDefault(true);
            book_searchView.setSubmitButtonEnabled(true);
            book_searchView.setIconifiedByDefault(true);
            book_searchView.setQueryHint("Find stories");
            book_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    new Thread(() -> {
                        try {
                            searched_books = BookModel.getSearchList(query,0);
                            if(!searched_books.isEmpty()){
                                runOnUiThread(()->{
                                    bookAdapter.setNewData(searched_books);
                                });
                            }
                        } catch (ApiFailException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    new Thread(() -> {
                        try {
                            searched_books = BookModel.getSearchList(newText,0);
                            if(!searched_books.isEmpty()){
                                runOnUiThread(()->{
                                    bookAdapter.setNewData(searched_books);
                                });
                            }
                        } catch (ApiFailException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
