package com.santiago.smartalert.views;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.santiago.smartalert.R;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.Node.NodeDrive;
import com.santiago.smartalert.models.Node.NodeHead;
import com.santiago.smartalert.models.Node.NodeRAM;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeDetailFragment extends Fragment {
    View rootView;
    PieChart chartRAM;
    PieChart chartDrive;
    private ProgressBar progress;
    private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_node_detail, container, false);
        progress = (ProgressBar) rootView.findViewById(R.id.progressbar_node_detail);
        layout = (LinearLayout) rootView.findViewById(R.id.layout_node);

        String nodeName = getArguments().getString("nodeName");

        getNodeHead(nodeName);
        getNodeRam(nodeName);
        getNodeDrive(nodeName);
        return rootView;
    }

    private void getNodeHead(final String nodeName)
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<NodeHead> respuesta = service.getNodeHead(nodeName);

        respuesta.enqueue(new Callback<NodeHead>() {
            @Override
            public void onResponse(Call<NodeHead> call, Response<NodeHead> response) {
                if (response.isSuccessful())
                {
                    NodeHead oHead = response.body();

                    TextView title = (TextView) rootView.findViewById(R.id.title);
                    TextView distro = (TextView) rootView.findViewById(R.id.distro);
                    TextView cpus = (TextView) rootView.findViewById(R.id.cpus);
                    TextView ipPub = (TextView) rootView.findViewById(R.id.ipPublic);
                    TextView ipPriv = (TextView) rootView.findViewById(R.id.ipPrivate);

                    title.setText(nodeName);
                    distro.setText(distro.getText() + oHead.getDistro());
                    cpus.setText(cpus.getText() + String.valueOf(oHead.getCantCpus()));
                    ipPub.setText(ipPub.getText() + oHead.getIpPublica());
                    ipPriv.setText(ipPriv.getText() + oHead.getIpAddress());
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<NodeHead> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }

    private void getNodeRam(String nodeName)
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<NodeRAM> respuesta = service.getNodeRAM(nodeName);

        respuesta.enqueue(new Callback<NodeRAM>() {
            @Override
            public void onResponse(Call<NodeRAM> call, Response<NodeRAM> response) {
                if (response.isSuccessful())
                {
                    NodeRAM oRam = response.body();
                    createGraphRam(oRam);
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<NodeRAM> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }

    private void getNodeDrive(String nodeName)
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(rootView.getContext()));
        Call<NodeDrive> respuesta = service.getNodeDrive(nodeName);

        respuesta.enqueue(new Callback<NodeDrive>() {
            @Override
            public void onResponse(Call<NodeDrive> call, Response<NodeDrive> response) {
                if (response.isSuccessful())
                {
                    NodeDrive oDrive = response.body();
                    createGraphDrive(oDrive);
                }
                else
                {
                    Log.e("ggtets", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<NodeDrive> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }

    private void createGraphRam(NodeRAM oRam){
        chartRAM = (PieChart) rootView.findViewById(R.id.ram_chart);
        //chart.setUsePercentValues(true);
        chartRAM.getDescription().setEnabled(false);

        chartRAM.setDragDecelerationFrictionCoef(0.15f);

        chartRAM.setDrawHoleEnabled(true);
        chartRAM.setHoleColor(Color.WHITE);
        chartRAM.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(oRam.getMemoriaEnUso(), getString(R.string.graph_ram_usada)));
        yValues.add(new PieEntry(oRam.getMemoriaLibre(), getString(R.string.graph_ram_libre)));

        PieDataSet dataSet = new PieDataSet(yValues, getString(R.string.graph_ram_total) + " " + oRam.getMemoriaTotal());
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        chartRAM.setData(data);
        chartRAM.invalidate();
    }

    private void createGraphDrive(NodeDrive oDrive) {
        chartDrive = (PieChart) rootView.findViewById(R.id.drive_chart);
        //chart.setUsePercentValues(true);
        chartDrive.getDescription().setEnabled(false);

        chartDrive.setDragDecelerationFrictionCoef(0.15f);

        chartDrive.setDrawHoleEnabled(true);
        chartDrive.setHoleColor(Color.WHITE);
        chartDrive.setTransparentCircleRadius(61f);

        float driveFree = (float) oDrive.getEspacioDisponible();
        float driveTotal = (float) oDrive.getEspacioTotal();
        float driveUsed = (driveTotal - driveFree);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(driveFree, getString(R.string.graph_drive_usada)));
        yValues.add(new PieEntry(driveUsed, getString(R.string.graph_drive_libre)));

        PieDataSet dataSet = new PieDataSet(yValues, getString(R.string.graph_drive_total) + " " + driveTotal);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        chartDrive.setData(data);
        chartDrive.invalidate();

        progress.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
}
