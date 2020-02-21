package com.apogee.fleetsurvey.Fragments;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apogee.fleetsurvey.Fragments.OperationListFragment.OnListFragmentInteractionListener;
import com.apogee.fleetsurvey.Fragments.dummy.DummyContent.DummyItem;
import com.apogee.fleetsurvey.R;
import com.apogee.fleetsurvey.model.Operation;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyOperationListRecyclerViewAdapter extends RecyclerView.Adapter<MyOperationListRecyclerViewAdapter.ViewHolder> {

    private final List<Operation> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyOperationListRecyclerViewAdapter(List<Operation> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_operationlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Operation operation = mValues.get(position);
        String html = "<b>Device name:</b>" + operation.getDevicename() + "<br><b>BLE Device name:</b>" +
                operation.getBlename() + "<br><b>DGPS Device name:</b>" + operation.getDgpsname();
        holder.mContentView.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(operation);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
