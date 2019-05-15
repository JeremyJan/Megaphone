package edu.uw.tacoma.tcss450.blm24.megaphone;

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


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnGroupListFragmentInteractionListener}
 * interface.
 */
public class GroupListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String GROUP_LIST_FRAG = "grouplistfrag";
    private OnGroupListFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;

    private List<GroupModel> groups;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GroupListFragment getGroupListFrag(GroupModel group) {
        GroupListFragment fragment = new GroupListFragment();
        Bundle args = new Bundle();
        args.putSerializable(GROUP_LIST_FRAG, group);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            new DownloadGroupsTask().execute(getString(R.string.get_group_list)); //TODO add URL
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
        // TODO: Update argument type and name
        void onGroupListFragmentInteraction(GroupModel item);
    }

    private class DownloadGroupsTask extends AsyncTask<String, Void, String> {

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
                    //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject resultObject = new JSONObject(s);
                if(resultObject.getBoolean("success")) {
                    groups = GroupModel.parseCourseJson(resultObject.getString("names"));
                }
                if(!groups.isEmpty()) {
                    Log.w("Nulls", (groups == null) +" "+ (mListener == null));
                    mRecyclerView.setAdapter(new MyGroupRecyclerViewAdapter(groups, mListener));
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), e.getMessage() +": "+ s, Toast.LENGTH_LONG).show();
                Log.e("JSON", e.getMessage() +": "+ s+'\"');
            }
        }
    }
}
