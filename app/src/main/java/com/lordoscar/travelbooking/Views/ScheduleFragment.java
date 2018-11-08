package com.lordoscar.travelbooking.Views;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lordoscar.travelbooking.Helpers.ScheduleAdapter;
import com.lordoscar.travelbooking.R;

public class ScheduleFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view){
        ListView scheduledTripsList = view.findViewById(R.id.scheduledTripsList);

        ScheduleAdapter adapter = new ScheduleAdapter(getActivity(), R.layout.schedule_list_item, ((MainActivity) getActivity()).getSchedule());
        scheduledTripsList.setAdapter(adapter);
        scheduledTripsList.setOnItemClickListener(adapter);

    }
}
