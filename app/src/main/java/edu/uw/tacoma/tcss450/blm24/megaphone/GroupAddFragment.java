package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupAddFragment.OnGroupAddragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupAddFragment} factory method to
 * create an instance of this fragment.
 */
public class GroupAddFragment extends Fragment {

    /**
     * mListner the Fragment interaction listener
     */
    private OnGroupAddragmentInteractionListener mListener;

    /**
     * Empty Constructor
     */
    public GroupAddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView which creates the view of the GroupAddFragment
     * @param inflater - the inflater
     * @param container - the container
     * @param savedInstanceState - the instance
     * @return the fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((FloatingActionButton) getActivity().findViewById(R.id.fab)).hide();
        View view = inflater.inflate(R.layout.fragment_group_add, container, false);
        EditText nameET = view.findViewById(R.id.group_name_edit_text);
        Switch memberS = view.findViewById(R.id.group_member_message_switch);
        memberS.setEnabled(false);
        Switch privateS = view.findViewById(R.id.group_private_switch);
        privateS.setEnabled(false);
        SeekBar radiusSB = view.findViewById(R.id.radiusBar);
        TextView radiusText = view.findViewById(R.id.radiusBarText);
        Button addButton = view.findViewById(R.id.create_group_button);
        radiusText.setText(Integer.toString(radiusSB.getProgress()));
        addButton.setOnClickListener(v -> {
            String name = nameET.getText().toString();
            boolean isPrivate = privateS.isChecked();
            boolean canMembers = memberS.isChecked();
            int radius = radiusSB.getProgress();
            Location location = new Location("network");
            Group group = new Group(name, isPrivate, canMembers, radius, location.getLatitude() + 1, location.getLongitude() + 1);
            mListener.onGroupAddFragmentInteraction(group);
        });
        radiusSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radiusText.setText(10 + Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        ((FloatingActionButton) getActivity().findViewById(R.id.fab)).show();
        super.onDestroyView();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Group group) {
        if (mListener != null) {
            mListener.onGroupAddFragmentInteraction(group);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGroupAddragmentInteractionListener) {
            mListener = (OnGroupAddragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGroupAddragmentInteractionListener {
        // TODO: Update argument type and name
        void onGroupAddFragmentInteraction(Group group);
    }
}
