package edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat.GroupMessageListFragment.OnListFragmentInteractionListener;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GroupMessage} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGroupMessageListRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupMessageListRecyclerViewAdapter.ViewHolder> {

    private final List<GroupMessage> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGroupMessageListRecyclerViewAdapter(List<GroupMessage> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_groupmessagelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.messageTextView.setText(mValues.get(position).getText());
        holder.messengerTextView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final TextView messageTextView;
        public final TextView messengerTextView;
        public GroupMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            messageTextView = (TextView) view.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) view.findViewById(R.id.messengerTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + messengerTextView.getText() + "'";
        }
    }
}
