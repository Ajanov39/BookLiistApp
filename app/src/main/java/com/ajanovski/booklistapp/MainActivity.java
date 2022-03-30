package com.ajanovski.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.gson.Gson;
import com.ajanovski.booklistapp.fragments.LoginFragment;
import com.ajanovski.booklistapp.fragments.MainFragment;
import com.ajanovski.booklistapp.model.User;
import com.ajanovski.booklistapp.util.AppHolder;
import com.ajanovski.booklistapp.util.Constants;
import com.ajanovski.booklistapp.util.DbHelper;

public class MainActivity extends AppCompatActivity {

    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences =
                getSharedPreferences(getString(R.string.preferences_key), MODE_PRIVATE);

        helper = new DbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

//        Toast.makeText(this, helper.getDatabaseName(), Toast.LENGTH_SHORT).show();

        String user = preferences.getString(Constants.PREF_USER, "");
        if(user.equals("")) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentCotainer, new LoginFragment())
                    .commit();
        } else {
            Gson gson = new Gson();

            User loggedUser = gson.fromJson(user, User.class);

            AppHolder.loggedInUser = loggedUser;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentCotainer, new MainFragment())
                    .commit();
        }
    }


    public DbHelper getHelper() {
        return helper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeDb();
    }
}