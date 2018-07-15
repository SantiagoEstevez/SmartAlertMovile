package com.santiago.smartalert.views;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santiago.smartalert.R;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.Logs.IpData;
import com.santiago.smartalert.models.Logs.LogApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng uruguay = new LatLng(-34, -56);
        //mMap.addMarker(new MarkerOptions().position(uruguay).title(""));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(uruguay));

        Log.e("ipLog", "Voy a cargar las marcas");
        setMarks();
    }

    private void setMarks()
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(this));
        Call<ArrayList<String>> respuesta = service.getNodes();

        respuesta.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<String> nodes = response.body();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());

                    c.add(Calendar.DATE, 1);
                    String to = dateFormat.format(c.getTime());

                    c.add(Calendar.DATE, -61);
                    String from = dateFormat.format(c.getTime());

                    Log.e("ipLog", "Paso 1 casi completado. from: " + from + " to: " + to);

                    for (String node : nodes) {
                        getLogsByNode(node, from, to);
                    }
                }
                else
                {
                    Log.e("ipLog", "Error en obtner los nodes");
                    android.util.Log.e("ipLog", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                android.util.Log.e("ipLog", "error0");
            }
        });
    }

    private void getLogsByNode(final String node, String from, String to)
    {
        Log.e("ipLog", "Llamo a la url: " + node + " - " + from + " - " + to);

        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(this));
        Call<ArrayList<LogApp>> respuesta = service.getLogsApp(node, from, to);
        Log.e("ipLog", "Esta es la url " + respuesta.request().url());

        respuesta.enqueue(new Callback<ArrayList<LogApp>>() {
            @Override
            public void onResponse(Call<ArrayList<LogApp>> call, Response<ArrayList<LogApp>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<LogApp> logs = response.body();

                    for (LogApp logapp : logs) {
                        String ip = logapp.getFromHostIp();

                        if (!ip.isEmpty()) {
                            String[] ipFragments = ip.split("\\.");

                            String hola = ipFragments[0];

                            //Solo paso ips que no sean privadas.
                            if (!ipFragments[0].equals("10")) {
                                getIpData(node, ip);
                            }
                        }
                    }
                }
                else
                {
                    Log.e("ipLog", "Error en obtner los logs");
                    Log.e("ipLog", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LogApp>> call, Throwable t) {
                Log.e("ipLog", t.toString());
            }
        });
    }

    private void getIpData(final String node, String ip)
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(this));
        Call<IpData> respuesta = service.getIpData(ip);
        Log.e("ipLog", "Esta es la url " + respuesta.request().url());

        respuesta.enqueue(new Callback<IpData>() {
            @Override
            public void onResponse(Call<IpData> call, Response<IpData> response) {
                if (response.isSuccessful())
                {
                    IpData ip = response.body();

                    Log.e("ipLog", "paso 3");
                    String loc = ip.getLoc();
                    if (loc != null && !loc.isEmpty()) {
                        String[] latlng = ip.getLoc().split(",");
                        LatLng newMark = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
                        mMap.addMarker(new MarkerOptions().position(newMark).title(node + " | " + ip.getCity() + " | " + ip.getRegion() + " | " + ip.getIp()));
                    }
                }
                else
                {
                    Log.e("ipLog", "Error en obtner la ip data");
                    Log.e("ipLog", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<IpData> call, Throwable t) {
                Log.e("ipLog", t.toString());
            }
        });
    }
}
