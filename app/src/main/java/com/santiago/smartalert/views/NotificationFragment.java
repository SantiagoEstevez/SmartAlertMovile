package com.santiago.smartalert.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.smartalert.R;
import com.santiago.smartalert.adapters.EventAdapter;
import com.santiago.smartalert.adapters.NotificationAdapter;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.Event.Event;
import com.santiago.smartalert.models.Event.EventType;
import com.santiago.smartalert.models.Notif.Notif;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private ArrayList<EventType> eventTypes;
    private ArrayList<Event> events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationAdapter = new NotificationAdapter(rootView.getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.notification);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));

        getEventTypes();

        return rootView;
    }

    private void getEventTypes()
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<ArrayList<EventType>> respuesta = service.getEventTypes();

        respuesta.enqueue(new Callback<ArrayList<EventType>>() {
            @Override
            public void onResponse(Call<ArrayList<EventType>> call, Response<ArrayList<EventType>> response) {
                if (response.isSuccessful())
                {
                    eventTypes = response.body();
                    getEvents();
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventType>> call, Throwable t) {
                Log.e("test", t.toString());
            }
        });
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
                    events = response.body();
                    getNotifications();
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.e("test", t.toString());
            }
        });
    }

    private void getNotifications()
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<ArrayList<Notif>> respuesta = service.getNotifications();

        respuesta.enqueue(new Callback<ArrayList<Notif>>() {
            @Override
            public void onResponse(Call<ArrayList<Notif>> call, Response<ArrayList<Notif>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<Notif> notifs = response.body();
                    for (Notif notif : notifs) {
                        for (Event event : events) {
                            if (event.getIdEvento() == notif.getId_evento_global()) {
                                notif.setNombre_evento_global(event.getNombreEvento());
                            }
                        }

                        for (EventType eventTyoe : eventTypes) {
                            if (eventTyoe.getId_tipo() == notif.getTipo()) {
                                notif.setNombre_tipo(eventTyoe.getNombre_tipo());
                            }
                        }
                    }
                    notificationAdapter.addList(notifs);
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notif>> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }
}
