package com.lordoscar.travelbooking.Views;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lordoscar.travelbooking.Models.Booking;
import com.lordoscar.travelbooking.Models.ScheduledTrip;
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
        //setCancelable(false);
        return view;
    }

    private void initializeComponents(View view) {
        final ScheduledTrip scheduledTrip = ((MainActivity) getActivity()).getSelectedScheduledTrip();

        TextView tripText = view.findViewById(R.id.tripText);

        TextView departureDateText = view.findViewById(R.id.departureDateText);
        TextView arrivalDateText = view.findViewById(R.id.arrivalDateText);
        TextView totalSeatsText = view.findViewById(R.id.totalSeatsText);
        TextView freeSeatsText = view.findViewById(R.id.freeSeatsText);
        TextView priceText = view.findViewById(R.id.priceText);
        final EditText seatCountBox = view.findViewById(R.id.seatCountBox);
        Button bookButton = view.findViewById(R.id.bookButton);

        tripText.setText(scheduledTrip.getTrip().getOrigin().getName() + " to " + scheduledTrip.getTrip().getDestination().getName());
        departureDateText.setText(scheduledTrip.getDeparture().toString());
        arrivalDateText.setText(scheduledTrip.getArrival().toString());
        totalSeatsText.setText(scheduledTrip.getSeats() + "");
        freeSeatsText.setText(scheduledTrip.getFreeSeats() + "");
        priceText.setText(scheduledTrip.getPrice() + " kr");


        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bookCount = Integer.parseInt(seatCountBox.getText().toString());
                if(bookCount <= scheduledTrip.getFreeSeats()){
                    boolean result = ((MainActivity) getActivity()).bookTrip(bookCount);
                    if(result){
                        Toast.makeText(getActivity(), "Booking complete!", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).closeFragments();
                    }
                }else {
                    Toast.makeText(getActivity(), "Booking failed, make sure there's enough seats and that you have an internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
