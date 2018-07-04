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
import com.santiago.smartalert.models.Notif.Notif;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

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

        getNotifications();

        return rootView;
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
