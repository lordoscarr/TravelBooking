package com.lordoscar.travelbooking.Views;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lordoscar.travelbooking.R;

public class DetailFragment extends DialogFragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeComponents(view);
        setCancelable(false);
        return view;
    }

    private void initializeComponents(View view) {
        
    }
}
