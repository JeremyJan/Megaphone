package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import edu.uw.tacoma.tcss450.blm24.megaphone.R;
import edu.uw.tacoma.tcss450.blm24.megaphone.utils.LocationHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GroupFireStoreListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private List<Group> groups = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupFireStoreListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GroupFireStoreListFragment newInstance(int columnCount) {
        GroupFireStoreListFragment fragment = new GroupFireStoreListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    private void getGroups() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groupfirestorelist_list, container, false);
        // Set the adapter

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Rooms").orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    groups.clear();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Group mGroup = snapshot.toObject(Group.class);
                        Log.d("GROUPLISTFRAG", mGroup.getName() + " " + snapshot.getId());
                        mGroup.setGroupID(snapshot.getId());
                        Log.d("GROUPLISTFRAG", "My Name: "
                                + mGroup.getName() + " MyID: " + mGroup.getGroupID());
                        if(LocationHelper.setup(getActivity()) && LocationHelper.hasLocation()) {
                            int radius = mGroup.getRadius();
                            double lat = mGroup.getGeoPoint().getLatitude();
                            double lon = mGroup.getGeoPoint().getLongitude();
                            if(LocationHelper.distance(lat, lon) <= radius) {
                                groups.add(mGroup);
                            }
                        }
                    }
                    recyclerView.setAdapter(new MyGroupFireStoreListRecyclerViewAdapter(groups, mListener));
                }

            });

        }
        return view;
    }

    private void updateRooms() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Group item);
    }
}
