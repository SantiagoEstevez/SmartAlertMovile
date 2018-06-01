package com.santiago.smartalert.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.smartalert.R;
import com.santiago.smartalert.models.Node.Node;

import java.util.ArrayList;

/**
 * Created by Santiago on 27/5/2018.
 */

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Node> dataset;
    private static ClickListener clickListener;

    public NodeAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<Node>();
    }

    @Override
    public NodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NodeAdapter.ViewHolder holder, int position) {
        Node node = dataset.get(position);
        holder.nameTextView.setText(node.getName());

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Recycle Click" + node.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    public void addList(ArrayList<String> nodes) {
        for (String nodeName : nodes) {
            dataset.add(new Node(nodeName));
        }
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

            nameTextView = (TextView) itemView.findViewById(R.id.node_name);
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
        NodeAdapter.clickListener = clickListener;
    }

    public Node getNodeByPosition(int position)
    {
        Node node = dataset.get(position);
        return node;
    }
}
