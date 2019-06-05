package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.uw.tacoma.tcss450.blm24.megaphone.groupChat.GroupMessageListFragment.OnListFragmentInteractionListener;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;

import java.util.Calendar;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GroupMessage} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MessageListRecyclerViewAdapter extends RecyclerView.Adapter<MessageListRecyclerViewAdapter.ViewHolder> {

    private final List<GroupMessage> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MessageListRecyclerViewAdapter(List<GroupMessage> items, OnListFragmentInteractionListener listener) {
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
        GroupMessage message = holder.mItem;
        holder.messageTextView.setText(message.getText());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(message.getTimestamp());
        StringBuilder builder = new StringBuilder();
        builder.append(message.getName()).append(" - ");
        builder.append(calendar.get(Calendar.HOUR_OF_DAY)).append(':');
        builder.append(String.format("%2d", calendar.get(Calendar.MINUTE))
                .replace(' ', '0'));
        holder.messengerTextView.setText(builder.toString());
        //holder.messageImageView.setColorFilter(name.hashCode());
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
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
        public final ImageView messageImageView;
        public GroupMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            messageTextView = view.findViewById(R.id.messageTextView);
            messengerTextView = view.findViewById(R.id.messengerTextView);
            messageImageView = view.findViewById(R.id.messengerImageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + messengerTextView.getText() + "'";
        }
    }
}
