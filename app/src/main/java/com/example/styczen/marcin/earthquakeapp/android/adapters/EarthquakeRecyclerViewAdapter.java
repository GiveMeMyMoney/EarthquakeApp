package com.example.styczen.marcin.earthquakeapp.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.listeners.OnListFragmentInteractionListener;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link Earthquake} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EarthquakeRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder> {

    private final List<Earthquake> earthquakeList;
    private final OnListFragmentInteractionListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mTime;
        public final TextView mMagnitude;
        public Earthquake earthquake;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.title_tv);
            mTime = (TextView) view.findViewById(R.id.subtitle_tv);
            mMagnitude = (TextView) view.findViewById(R.id.additional_info);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }

    public EarthquakeRecyclerViewAdapter(List<Earthquake> items, OnListFragmentInteractionListener listener) {
        earthquakeList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.earthquake = earthquakeList.get(position);
        holder.mName.setText(earthquakeList.get(position).getName());
        holder.mTime.setText(earthquakeList.get(position).getTime());
        holder.mMagnitude.setText(String.valueOf(earthquakeList.get(position).getMagnitude()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Log.e("Click", "lohohoho");
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.earthquake);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (earthquakeList != null) ? earthquakeList.size() : 0;
    }


}
