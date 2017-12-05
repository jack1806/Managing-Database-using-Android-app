package com.dbmsjack.dbms;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dbmsjack.dbms.NetworkUtils.ConnectNetwork;
import com.dbmsjack.dbms.NetworkUtils.NetworkContract;
import com.dbmsjack.dbms.objects.SharedContractClass;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DeleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteFragment newInstance(String param1, String param2) {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences(SharedContractClass.shared_pref_name,
                SharedContractClass.shred_pref_mode);
        final EditText reg_input_box = (EditText) view.findViewById(R.id.set_delete_reg_input);
        Button button = (Button) view.findViewById(R.id.delete_reg_no_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg_number = reg_input_box.getText().toString();
                if(reg_number.isEmpty())
                    Toast.makeText(getContext(),"Enter the Register Number!",Toast.LENGTH_SHORT).show();
                else{
                    ConnectNetwork network = new ConnectNetwork(getContext(), NetworkContract.DELETE_ADD);
                    ArrayList<String> fields = new ArrayList<String>(),
                    values = new ArrayList<String>();

                    fields.add(NetworkContract.JSON_REG);
                    values.add(reg_number);

                    String result = network.postData(fields,values);
                    if(result != null)
                        if(result.isEmpty()!=true)
                            Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"Connection failed !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(preferences.contains(SharedContractClass.server_address)){

        }
        else{
            // Raise An Error That Server address isn't Specified
            Toast.makeText(getContext(),"No server address Specified",Toast.LENGTH_LONG).show();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
