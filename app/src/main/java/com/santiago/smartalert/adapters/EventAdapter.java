package com.santiago.smartalert.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.santiago.smartalert.R;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.Event.Event;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Santiago on 5/6/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Event> dataset;
    private static ClickListener clickListener;

    public EventAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<Event>();
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Event event = dataset.get(position);
        holder.title.setText(event.getNombreEvento());
        holder.suscripto.setChecked(event.isEstoySuscripto());
        holder.activo.setChecked(event.isActivo());

        if (event.isSoyCreador()) {
            holder.activo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    event.setActivo(!event.isActivo());
                    //Toast.makeText(context, "Activo es: " + event.isActivo() + " y mi pos es: " + position, Toast.LENGTH_LONG).show();

                    holder.activo.setChecked(event.isActivo());
                    changeEventStatus(position, event);
                }
            });
        } else {
            holder.activo.setEnabled(false);
        }

        holder.suscripto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                event.setEstoySuscripto(!event.isEstoySuscripto());
                //Toast.makeText(context, "Suscripto es : " + event.isEstoySuscripto() + " y mi pos es: " + position, Toast.LENGTH_LONG).show();

                changeEventSuscribe(position, event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    public void addList(ArrayList<Event> events) {
        dataset.addAll(events);
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private Switch suscripto;
        private RadioButton activo;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);

            title = (TextView) itemView.findViewById(R.id.event_name);
            suscripto = (Switch) itemView.findViewById(R.id.event_suscripto);
            activo = (RadioButton) itemView.findViewById(R.id.event_status);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        EventAdapter.clickListener = clickListener;
    }

    public Event getEventByPosition(int position)
    {
        Event event = dataset.get(position);
        return event;
    }

    //El evento ya llega con el nuevo estatus de activo/desactivo
    private void changeEventStatus(int position, final Event event)
    {
        Call<ResponseBody> respuesta;
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(context));

        if (event.isActivo()) {
            respuesta = service.activeEvent(String.valueOf(event.getIdEvento()));
        } else {
            respuesta = service.disableEvent(String.valueOf(event.getIdEvento()));
        }

        respuesta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    if (event.isActivo()) {
                        Toast.makeText(context, "Se activo el evento: " + event.getNombreEvento(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Se desactivo el evento: " + event.getNombreEvento(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "No autorizado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    //El evento ya llega con el nuevo estatus de suscripto/desuscripto
    private void changeEventSuscribe(int position, final Event event)
    {
        Call<ResponseBody> respuesta;
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(context));

        if (event.isEstoySuscripto()) {
            respuesta = service.suscribeEvent(String.valueOf(event.getIdEvento()));
        } else {
            respuesta = service.unsuscribeEvent(String.valueOf(event.getIdEvento()));
        }

        respuesta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    if (event.isEstoySuscripto()) {
                        Toast.makeText(context, "Se suscribio al evento " + event.getNombreEvento(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Se desuscribio del evento " + event.getNombreEvento(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "No autorizado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
