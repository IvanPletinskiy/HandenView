package com.handen.handenview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handen.handenview.ParamsFragment.OnListFragmentInteractionListener;
import com.handen.handenview.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ParamAdapter extends RecyclerView.Adapter<ParamAdapter.ViewHolder> {

    private final ArrayList<String> mValues;
    private final ArrayList<Integer> ids;
    private final OnListFragmentInteractionListener mListener;

    public ParamAdapter(ArrayList<String> items, ArrayList<Integer> ids, OnListFragmentInteractionListener listener) {
        mValues = items;
        this.ids = ids;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_param, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mValues.get(position));
        Float fl = MODBUSTCP.getCurrentValue(ids.get(position));
        holder.value.setText(Float.toString(fl));

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name, value;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            value = view.findViewById(R.id.valueTextView);
        }
    }
}
