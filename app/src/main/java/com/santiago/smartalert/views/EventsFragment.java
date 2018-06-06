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
import com.santiago.smartalert.adapters.NodeAdapter;

public class EventsFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerView;
    private NodeAdapter nodeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_events, container, false);

        nodeAdapter = new NodeAdapter(rootView.getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.events);
        recyclerView.setAdapter(nodeAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));

        return rootView;
    }
}
