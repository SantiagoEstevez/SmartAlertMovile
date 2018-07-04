package com.santiago.smartalert.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.santiago.smartalert.R;
import com.santiago.smartalert.api.ApiService;
import com.santiago.smartalert.api.AuthService;
import com.santiago.smartalert.api.ServiceGenerator;
import com.santiago.smartalert.models.Event.Event;
import com.santiago.smartalert.models.Notif.Notif;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Santiago on 5/6/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Notif> dataset;
    private static ClickListener clickListener;

    public NotificationAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<Notif>();
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Notif notif = dataset.get(position);
        holder.title.setText("Id de evento: "+ notif.getId_evento_global() + " por " + notif.getCondicion_dispara());
        holder.fecha.setText(notif.getFecha_dispara());
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    public void addList(ArrayList<Notif> notifs) {
        dataset.addAll(notifs);
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView fecha;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.notification_name);
            fecha = (TextView) itemView.findViewById(R.id.notification_fecha);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NotificationAdapter.clickListener = clickListener;
    }

    public Notif getNotifByPosition(int position)
    {
        Notif notif = dataset.get(position);
        return notif;
    }
}
