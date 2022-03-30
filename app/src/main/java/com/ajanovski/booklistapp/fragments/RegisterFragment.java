package com.ajanovski.booklistapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.ajanovski.booklistapp.MainActivity;
import com.ajanovski.booklistapp.R;
import com.ajanovski.booklistapp.util.DbHelper;


public class RegisterFragment extends Fragment {

    EditText etUsername, etPassword, etRepeatPassword;
    Button btnRegister;


    public RegisterFragment() {

    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etRepeatPassword = view.findViewById(R.id.etRepeatPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        enableButton(false);

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

        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etRepeatPassword.addTextChangedListener(textWatcher);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                DbHelper helper = ((MainActivity)getActivity()).getHelper();

                long id = helper.createUser(username, password);
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());

                if(id > 0) {
                    dialog.setTitle("Success")
                            .setMessage("User created!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    requireActivity().onBackPressed();
                                }
                            });
                    dialog.show();
                } else {
                    dialog.setTitle("Failed")
                            .setMessage("User not created!")
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
    }


    private Boolean checkFields() {
        Boolean validFields = false;
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if(username.length() > 4 && password.length() > 4 && repeatPassword.length() > 4) {
            if(password.equals(repeatPassword)){
                validFields = true;
            }
        }

        return validFields;
    }

    private void enableButton(Boolean enabled) {
        btnRegister.setEnabled(enabled);
    }

}