package com.santiago.smartalert.views;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.santiago.smartalert.R;
import com.santiago.smartalert.adapters.NodeAdapter;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodesFragment extends Fragment {
    private RecyclerView recyclerView;
    private NodeAdapter nodeAdapter;
    private ProgressBar progress;
    Comunicador comunicador;
    View rootView;

    public interface Comunicador {
        public void navegateToNodeDetail(String node_name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_nodes, container, false);
        progress = (ProgressBar) rootView.findViewById(R.id.progressbar_node);

        nodeAdapter = new NodeAdapter(rootView.getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(nodeAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));

        nodeAdapter.setOnItemClickListener(new NodeAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                comunicador.navegateToNodeDetail(nodeAdapter.getNodeByPosition(position).getName());

                //Intent frmMain = new Intent(MainActivity.this, NodeDetailActivity.class);
                //frmMain.putExtra("NODE_NAME", nodeAdapter.getNodeByPosition(position).getName());
                //startActivity(frmMain);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.e("ggtets", "onItemLongClick pos = " + position);
            }
        });

        getNodes();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        comunicador = (Comunicador) context;
        super.onAttach(context);
    }

    private void getNodes()
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<ArrayList<String>> respuesta = service.getNodes();

        respuesta.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<String> nodes = response.body();
                    nodeAdapter.addList(nodes);
                    progress.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }
}
