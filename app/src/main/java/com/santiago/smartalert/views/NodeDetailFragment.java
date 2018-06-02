package com.santiago.smartalert.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.smartalert.R;

public class NodeDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("ggtets", "estoy en el detalle y vine de: " + getArguments().getString("nodeName"));
        return inflater.inflate(R.layout.fragment_node_detail, container, false);
    }
}
