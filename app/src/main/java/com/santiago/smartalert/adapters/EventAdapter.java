package com.santiago.smartalert.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Event event = dataset.get(position);
        holder.title.setText(event.getNombreEvento());
        holder.status.setChecked(event.isActivo());

        holder.status.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "mi posicion es: " + position, Toast.LENGTH_LONG).show();

                changeEvent(position, !event.isActivo());
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
        void onItemLongClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView title;
        private Switch status;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            title = (TextView) itemView.findViewById(R.id.event_name);
            status = (Switch) itemView.findViewById(R.id.event_status);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
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

    private void changeEvent(int position, final boolean newStatus)
    {
        Call<ResponseBody> respuesta;
        ApiService service = ServiceGenerator.createService(ApiService.class, AuthService.getToken(context));

        if (newStatus) {
            respuesta = service.activeEvent(String.valueOf(position));
        } else {
            respuesta = service.disableEvent(String.valueOf(position));
        }

        respuesta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    if (newStatus) {
                        Toast.makeText(context, "Activado", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Desactivado", Toast.LENGTH_LONG).show();
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
