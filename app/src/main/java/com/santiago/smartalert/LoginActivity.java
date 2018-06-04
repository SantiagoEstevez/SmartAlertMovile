package com.santiago.smartalert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.santiago.smartalert.adapters.NodeAdapter;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.token;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Boton login
        final Button btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.username)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
                }
                else{
                    getAccess(username, password);
                }
            }
        });
    }

    private void getAccess(String username, String password)
    {
        ApiService service = ServiceGenerator.createService(ApiService.class, username, password);
        Call<token> respuesta = service.login();

        respuesta.enqueue(new Callback<token>() {
            @Override
            public void onResponse(Call<token> call, Response<token> response) {
                if (response.isSuccessful())
                {
                    //Headers headers = response.headers();
                    //String cookie = response.headers().get("SecurityToken");

                    token oToken = response.body();
                    AuthService.setToken(LoginActivity.this, oToken.getSecurityToken());

                    Intent frmMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(frmMain);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<token> call, Throwable t) {
                Log.e("test", "error2");
            }
        });
    }
}
