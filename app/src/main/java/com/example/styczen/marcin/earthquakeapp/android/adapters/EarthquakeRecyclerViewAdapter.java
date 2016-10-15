package com.example.styczen.marcin.earthquakeapp.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.listeners.OnListFragmentInteractionListener;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;

import java.util.ArrayList;
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
        public final ImageView mImage;
        public final TextView mName;
        public final TextView mTime;
        public final TextView mMagnitude;
        public Earthquake earthquake;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.icon_iv) ;
            mName = (TextView) view.findViewById(R.id.title_tv);
            mTime = (TextView) view.findViewById(R.id.subtitle_tv);
            mMagnitude = (TextView) view.findViewById(R.id.additional_info_tv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }

    public EarthquakeRecyclerViewAdapter(List<Earthquake> items, OnListFragmentInteractionListener listener) {
        earthquakeList = new ArrayList<>(items);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //TODO ViewHolder albo cos
        holder.earthquake = earthquakeList.get(position);
        String title = holder.earthquake.getName();
        setImage(title, holder);
        holder.mName.setText(title);
        holder.mTime.setText(holder.earthquake.getTime());
        holder.mMagnitude.setText(String.valueOf(holder.earthquake.getMagnitude()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Log.e("Click", "lohohoho");
                    mListener.onListFragmentInteraction(holder.earthquake);
                }
            }
        });
    }

    private void setImage(String title, ViewHolder holder) {
        String firstLetter = title.charAt(0) + "";
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(firstLetter);

        TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color);
        holder.mImage.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return (earthquakeList != null) ? earthquakeList.size() : 0;
    }

    public void changeDataListAndNotify(List<Earthquake> eList) {
        earthquakeList.clear();
        earthquakeList.addAll(eList);
        this.notifyDataSetChanged();
    }


}
