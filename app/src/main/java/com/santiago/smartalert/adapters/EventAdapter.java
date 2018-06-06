package com.santiago.smartalert.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.smartalert.R;
import com.santiago.smartalert.models.Event.Event;

import java.util.ArrayList;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = dataset.get(position);
        holder.nameTextView.setText(event.getNombreEvento());
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

        private TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            nameTextView = (TextView) itemView.findViewById(R.id.event_name);
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

    public Event getNodeByPosition(int position)
    {
        Event event = dataset.get(position);
        return event;
    }
}
