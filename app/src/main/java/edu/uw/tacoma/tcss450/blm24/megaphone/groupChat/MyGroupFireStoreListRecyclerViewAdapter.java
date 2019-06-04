package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.tacoma.tcss450.blm24.megaphone.groupChat.GroupFireStoreListFragment.OnListFragmentInteractionListener;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;
import edu.uw.tacoma.tcss450.blm24.megaphone.utils.LocationHelper;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Group} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGroupFireStoreListRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupFireStoreListRecyclerViewAdapter.ViewHolder> {

    private final List<Group> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGroupFireStoreListRecyclerViewAdapter(List<Group> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_groupfirestorelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Group group = mValues.get(position);
        double lat = group.getGeoPoint().getLatitude();
        double lon = group.getGeoPoint().getLongitude();
        holder.mIdView.setText(group.getName());
        StringBuilder builder = new StringBuilder();
        builder.append(LocationHelper.distance(lat, lon));
        builder.append("m away");
        holder.mContentView.setText(builder.toString());

        Log.d("FIRESTORE_RecylerView", "In onBindViewHolder");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public Group mItem;

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
