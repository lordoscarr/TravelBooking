package com.lordoscar.travelbooking.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lordoscar.travelbooking.Models.ScheduledTrip;
import com.lordoscar.travelbooking.Models.Trip;
import com.lordoscar.travelbooking.R;
import com.lordoscar.travelbooking.Views.MainActivity;

import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<ScheduledTrip> implements AdapterView.OnItemClickListener {
    public ScheduleAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_list_item, parent, false);
        }

        ScheduledTrip trip = getItem(position);

        TextView departureDateText = convertView.findViewById(R.id.departureDateText);
        TextView arrivalDateText = convertView.findViewById(R.id.arrivalDateText);

        departureDateText.setText(trip.getDeparture().toString());
        arrivalDateText.setText(trip.getArrival().toString());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("KLICK", "Klickade på " + getItem(i).toString());
        ((MainActivity) getContext()).openDetail(getItem(i));
    }
}
