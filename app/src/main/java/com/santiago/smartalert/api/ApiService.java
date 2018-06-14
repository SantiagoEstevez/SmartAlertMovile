package com.santiago.smartalert.api;

import com.santiago.smartalert.models.Event.Event;
import com.santiago.smartalert.models.Node.NodeDrive;
import com.santiago.smartalert.models.Node.NodeHead;
import com.santiago.smartalert.models.Node.NodeRAM;
import com.santiago.smartalert.models.token;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Santiago on 22/5/2018.
 */

public interface ApiService {

    @GET("token") //seguridad/token
    Call<token> login();

    @GET("nodes") //infra/listarNodos
    Call<ArrayList<String>> getNodes();

    @GET("cabezal/{node}") //info/cabezal/{node}
    Call<NodeHead> getNodeHead(@Path(value = "node", encoded = true) String nodename);

    @GET("RAM/{node}") //info/free/{node}
    Call<NodeRAM> getNodeRAM(@Path(value = "node", encoded = true) String nodename);

    @GET("drive/{node}") //info/infoDisco/{node}
    Call<NodeDrive> getNodeDrive(@Path(value = "node", encoded = true) String nodename);

    @GET("events") //eventos/getListaEventosG
    Call<ArrayList<Event>> getEvents();

    @PUT("eventos/desactivarEG/{idEvento}")
    Call<ResponseBody> disableEvent(@Path(value = "idEvento", encoded = true) String idEvent);

    @PUT("eventos/activarEG/{idEvento}")
    Call<ResponseBody> activeEvent(@Path(value = "idEvento", encoded = true) String idEvent);
}
