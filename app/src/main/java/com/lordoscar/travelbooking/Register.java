package com.lordoscar.travelbooking;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Register extends DialogFragment {
    private EditText editTextName, editTextLastname, editTextAddress, editTextPhone, editTextMail;
    private Button btnRegister;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view){
        editTextName = view.findViewById(R.id.editTextName);
        editTextLastname = view.findViewById(R.id.editTextLastname);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextMail = view.findViewById(R.id.editTextMail);
        btnRegister = view.findViewById(R.id.btnRegister);
    }

    public void registerListeners(){
        btnRegister.setOnClickListener(new ButtonListener());
    }

    public void registerUser(){
        // registrera användare SLASH lägg till i db
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            registerUser();
        }
    }



}
