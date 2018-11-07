package com.lordoscar.travelbooking.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lordoscar.travelbooking.Models.Trip;
import com.lordoscar.travelbooking.R;

import java.util.List;

public class TripAdapter extends ArrayAdapter<Trip> {
    public TripAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_list_item, parent, false);
        }

        Trip trip = getItem(position);

        TextView originName = convertView.findViewById(R.id.originName);
        TextView originCountry = convertView.findViewById(R.id.originCountry);
        TextView destinationName = convertView.findViewById(R.id.destinationName);
        TextView destinationCountry = convertView.findViewById(R.id.destinationCountry);

        originName.setText(trip.getOrigin().getName());
        originCountry.setText(trip.getOrigin().getCountry());
        destinationName.setText(trip.getDestination().getName());
        destinationCountry.setText(trip.getDestination().getCountry());

        return convertView;
    }
}
