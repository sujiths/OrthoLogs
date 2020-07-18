package com.ss.orthologs;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
    private final Context context;
    private final List<String> values;
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ImagesAdapter(Context context, List<String> values, ImagesAdapter.OnItemClickListener listener) {
        this.context = context;
        this.values = values;
        this.mListener = listener;

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView imageView;
        private OnItemClickListener mListener;


        public MyViewHolder(ImageView v) {
            super(v);
            imageView = v;
            imageView.setOnClickListener(this);

        }

        public MyViewHolder(ImageView itemView, OnItemClickListener listener) {
            this(itemView);
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view,  getAdapterPosition());
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_recycler_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageView.setImageResource(R.drawable.bg);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}