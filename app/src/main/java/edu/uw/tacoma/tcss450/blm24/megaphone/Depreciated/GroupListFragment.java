package edu.uw.tacoma.tcss450.blm24.megaphone.Depreciated;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat.Group;
import edu.uw.tacoma.tcss450.blm24.megaphone.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnGroupListFragmentInteractionListener}
 * interface.
 */
public class GroupListFragment extends Fragment {

    private static final String GROUP_LIST_FRAG = "grouplistfrag";
    /**
     * The listener
     */
    private OnGroupListFragmentInteractionListener mListener;

    /**
     * the RecyclerView
     */
    private RecyclerView mRecyclerView;

    /**
     * the groups
     */
    private List<Group> groups;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupListFragment() {

    }

    @SuppressWarnings("unused")
    public static GroupListFragment getGroupListFrag(Group group) {
        GroupListFragment fragment = new GroupListFragment();
        Bundle args = new Bundle();
        args.putSerializable(GROUP_LIST_FRAG, group);
        fragment.setArguments(args);
        return fragment;
    }

    private void getGroups() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Rooms").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               groups.clear();
               for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                   Group mGroup = snapshot.toObject(Group.class);
                   Log.d("GROUPLISTFRAG", mGroup.getName());
                   groups.add(mGroup);
               }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * onCreateView method which creates the view of the list fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        groups = new ArrayList<>();
        getGroups();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(new MyGroupRecyclerViewAdapter(groups, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGroupListFragmentInteractionListener) {
            mListener = (OnGroupListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGroupListFragmentInteractionListener {

        void onGroupListFragmentInteraction(Group item);
    }

    /**
     * DownloadGroupsTask innerclass which does an AsyncTask
     */
    private class DownloadGroupsTask extends AsyncTask<String, Void, String> {

        /**
         * Receives the list of groups
         * @param urls
         * @return the response
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                Log.i("AsyncRequest", url);
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new
                            InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to download the list of groups, Reason: " + e.getMessage();
                    Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * This method sends the JSON to be parsed if it successfully gets the
         * JSON elements.
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {

        }
    }
}