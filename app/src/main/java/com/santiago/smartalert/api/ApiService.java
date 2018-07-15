package com.santiago.smartalert.api;

import com.santiago.smartalert.models.Event.Event;
import com.santiago.smartalert.models.Event.EventType;
import com.santiago.smartalert.models.Logs.IpData;
import com.santiago.smartalert.models.Logs.LogApp;
import com.santiago.smartalert.models.Node.NodeDrive;
import com.santiago.smartalert.models.Node.NodeHead;
import com.santiago.smartalert.models.Node.NodeRAM;
import com.santiago.smartalert.models.Notif.Notif;
import com.santiago.smartalert.models.token;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Santiago on 22/5/2018.
 */

public interface ApiService {

    //@GET("token")
    @GET("seguridad/token")
    Call<token> login();

    //-------------- NODES -------------------
    //@GET("nodes")
    @GET("infra/listarNodos")
    Call<ArrayList<String>> getNodes();

    //@GET("cabezal/{node}")
    @GET("info/cabezal/{node}")
    Call<NodeHead> getNodeHead(@Path(value = "node", encoded = true) String nodename);

    //@GET("RAM/{node}")
    @GET("info/free/{node}")
    Call<NodeRAM> getNodeRAM(@Path(value = "node", encoded = true) String nodename);

    //@GET("drive/{node}")
    @GET("info/infoDisco/{node}")
    Call<NodeDrive> getNodeDrive(@Path(value = "node", encoded = true) String nodename);
    //-------------- NODES -------------------

    //-------------- LOG APLICACIONES -------------------
    @GET("info/infoAgente/{node}/{from}/{to}")
    Call<ArrayList<LogApp>> getLogsApp(@Path(value = "node", encoded = true) String node,
                                       @Path(value = "from", encoded = true) String from,
                                       @Path(value = "to", encoded = true) String to);

    @Headers("Accept: application/json")
    @GET("http://ipinfo.io/{ip}")
    Call<IpData> getIpData(@Path(value = "ip", encoded = true) String node);
    //-------------- LOG APLICACIONES -------------------

    //-------------- EVENTS -------------------
    @GET("eventos/getTiposEventos")
    Call<ArrayList<EventType>> getEventTypes();

    //@GET("events")
    @GET("eventos/getListaEventosG")
    Call<ArrayList<Event>> getEvents();

    //@PUT("events/active/{idEvento}")
    @PUT("eventos/activarEG/{idEvento}")
    Call<ResponseBody> activeEvent(@Path(value = "idEvento", encoded = true) String idEvent);

    //@PUT("events/disable/{idEvento}")
    @PUT("eventos/desactivarEG/{idEvento}")
    Call<ResponseBody> disableEvent(@Path(value = "idEvento", encoded = true) String idEvent);

    //@PUT("events/suscribe/{idEvento}")
    @PUT("eventos/sus_eg/{idEvento}")
    Call<ResponseBody> suscribeEvent(@Path(value = "idEvento", encoded = true) String idEvent);

    //@PUT("events/unsuscribe/{idEvento}")
    @PUT("eventos/cancela_sus_evento_global/{idEvento}")
    Call<ResponseBody> unsuscribeEvent(@Path(value = "idEvento", encoded = true) String idEvent);
    //-------------- NODES -------------------

    //-------------- NOTIFICACIONES -------------------
    @GET("notis/getNotisTodas")
    Call<ArrayList<Notif>> getNotifications();
    //-------------- NOTIFICACIONES -------------------
}
