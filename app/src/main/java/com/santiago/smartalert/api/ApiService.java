package com.santiago.smartalert.api;

import com.santiago.smartalert.models.Node.NodeDrive;
import com.santiago.smartalert.models.Node.NodeHead;
import com.santiago.smartalert.models.Node.NodeRAM;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Santiago on 22/5/2018.
 */

public interface ApiService {
    @GET("token")
    Call<ResponseBody> login();

    @GET("nodes")
    Call<ArrayList<String>> getNodes();

    @GET("cabezal/{node}")
    Call<NodeHead> getNodeHead(@Path(value = "node", encoded = true) String nodename);

    @GET("RAM/{node}")
    Call<NodeRAM> getNodeRAM(@Path(value = "node", encoded = true) String nodename);

    @GET("drive/{node}")
    Call<NodeDrive> getNodeDrive(@Path(value = "node", encoded = true) String nodename);
}
