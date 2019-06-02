package edu.uw.tacoma.tcss450.blm24.megaphone.Depreciated;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat.Group;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Group} and makes a call to the
 * specified {@link GroupListFragment}.
 *
 * Recycles the fragments for reuse
 */
public class MyGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder> {

    /**
     * Groups that were recieved, and will be stored
     */
    private final List<Group> mValues;


    /**
     * The listener that handles change in the group list.
     */
    private final GroupListFragment.OnGroupListFragmentInteractionListener mListener;

    public MyGroupRecyclerViewAdapter(List<Group> items,
                                      GroupListFragment.OnGroupListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    /**
     * Hook on when this is created as a viewHolder.
     *
     * @param parent the parent view
     * @param viewType the type of that parent view
     * @return this, as a groupholder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Hook on when this binds to a viewHolder
     *
     * @param holder the holder it was bound to
     * @param position the position of this view
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getRadius());
        Log.d("GROUP_RECYLER", mValues.get(position).getName());
        //TODO change

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onGroupListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    /**
     * @return the number of groups this contains
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Model class for the view in lists.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Group mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        /**
         * @return string representation of each model
         */
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
