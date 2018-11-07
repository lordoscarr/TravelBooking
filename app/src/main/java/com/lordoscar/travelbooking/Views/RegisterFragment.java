package com.lordoscar.travelbooking.Views;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lordoscar.travelbooking.R;


public class RegisterFragment extends DialogFragment {
    private EditText editTextName, editTextLastname, editTextAddress, editTextPhone, editTextMail;
    private Button btnRegister;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initializeComponents(view);
        setCancelable(false);
        return view;
    }

    private void initializeComponents(View view){
        editTextName = view.findViewById(R.id.editTextName);
        editTextLastname = view.findViewById(R.id.editTextLastname);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextMail = view.findViewById(R.id.editTextMail);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    public void registerUser(){
        int id = ((MainActivity) getActivity()).registerUser(editTextName.getText().toString() + " " + editTextLastname.getText().toString(),
                editTextAddress.getText().toString(),
                editTextMail.getText().toString(),
                editTextPhone.getText().toString());

        if(id == -1){
            //Registration failed
            Toast.makeText(getActivity(), "Registration failed, maybe the e-mail is already in use.", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences preferences = getActivity().getSharedPreferences("com.lordoscar.p1", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTime", false);
            editor.putInt("travelerId", id);
            editor.apply();
            dismiss();
        }
    }
}
