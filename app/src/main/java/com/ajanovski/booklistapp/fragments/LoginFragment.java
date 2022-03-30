package com.ajanovski.booklistapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.ajanovski.booklistapp.MainActivity;
import com.ajanovski.booklistapp.R;
import com.ajanovski.booklistapp.model.User;
import com.ajanovski.booklistapp.util.AppHolder;
import com.ajanovski.booklistapp.util.Constants;
import com.ajanovski.booklistapp.util.DbHelper;

public class LoginFragment extends Fragment {

    Button btnLogin;
    TextView tvRegister, tvForgotPassword, tvWebView;
    EditText etUsername, etPassword;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRegister = view.findViewById(R.id.tvRegister);
        btnLogin = view.findViewById(R.id.btnLogin);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
        tvWebView = view.findViewById(R.id.tvWebView);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentCotainer, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentCotainer, new WebViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
                dialog.setView(dialogView);
                AlertDialog alertDialog = dialog.create();

                EditText etForgotPassword = dialogView.findViewById(R.id.etForgotPassword);
                Button btnSubmit = dialogView.findViewById(R.id.btnSubmitForgottenPassword);

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String username = etForgotPassword.getText().toString();

                        DbHelper helper = ((MainActivity)getActivity()).getHelper();

                        User user = helper.getUser(username);

                        if(user.getUsername() == null) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                            dialog.setMessage("The username does not exists")
                                    .setTitle("Doesn't exist")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            dialog.show();
                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                            dialog.setMessage("Your password is " + user.getPassword())
                                    .setTitle("Username found")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = requireActivity()
                        .getSharedPreferences(getString(R.string.preferences_key), Context.MODE_PRIVATE);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                DbHelper helper = ((MainActivity)getActivity()).getHelper();

                User user = helper.getUser(username, password);

                if(user.getUsername() == null) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                        dialog.setMessage("Wrong username or password")
                                .setTitle("Wrong credentials")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        dialog.show();
                } else {
                        SharedPreferences.Editor editor = preferences.edit();
                        Gson gson = new Gson();
                        String jsonUser = gson.toJson(user);
                        editor.putString(Constants.PREF_USER, jsonUser);
                        editor.apply();

                        AppHolder.loggedInUser = user;

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentCotainer, new MainFragment())
                                .commit();
                }
            }
        });

    }
}