package com.santiago.smartalert.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.smartalert.R;
import com.santiago.smartalert.adapters.EventAdapter;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.Event.Event;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_events, container, false);

        eventAdapter = new EventAdapter(rootView.getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.events);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));

        getEvents();

        return rootView;
    }

    private void getEvents()
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<ArrayList<Event>> respuesta = service.getEvents();

        respuesta.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<Event> events = response.body();
                    eventAdapter.addList(events);
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }
}
