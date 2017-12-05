package com.dbmsjack.dbms;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dbmsjack.dbms.NetworkUtils.ConnectNetwork;
import com.dbmsjack.dbms.NetworkUtils.NetworkContract;
import com.dbmsjack.dbms.objects.SharedContractClass;
import com.dbmsjack.dbms.objects.ViewListAdapter;
import com.dbmsjack.dbms.objects.ViewListItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewFragment newInstance(String param1, String param2) {
        ViewFragment fragment = new ViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        //final TextView resultOut = (TextView) view.findViewById(R.id.result_out);
        final SharedPreferences preferences = getActivity().getSharedPreferences(SharedContractClass.shared_pref_name,
                SharedContractClass.shred_pref_mode);
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.view_list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.refresh_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferences.contains(SharedContractClass.server_address)) {
                    ConnectNetwork network = new ConnectNetwork(getContext());
                    String result = network.getData();
                    //resultOut.setText(result);
                    if (result != null) {
                        ArrayList<ViewListItem> items = decodeJSON(result);
                        if (items != null) {
                            ViewListAdapter adapter = new ViewListAdapter(items);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);
                        } else {
                            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),"Connection Failed !",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    // Raise An Error That Server address isn't Specified
                    Toast.makeText(getContext(),"No server address Specified",Toast.LENGTH_LONG).show();
                }
            }
        });
        if(preferences.contains(SharedContractClass.server_address)){
            ConnectNetwork network = new ConnectNetwork(getContext());
            String result = network.getData();
            //resultOut.setText(result);
            ArrayList<ViewListItem> items = decodeJSON(result);
            if(items!=null){
                ViewListAdapter adapter = new ViewListAdapter(items);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
            else{
                Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
            }
        }
        else{
            // Raise An Error That Server address isn't Specified
            Toast.makeText(getContext(),"No server address Specified",Toast.LENGTH_LONG).show();
        }
        return view;
    }


    public ArrayList<ViewListItem> decodeJSON(String s){

        try {

            JSONObject object = new JSONObject(s);
            if (object.getBoolean(NetworkContract.JSON_ERROR))
                return null;
            int i, total = object.getInt(NetworkContract.JSON_TOTAL);
            ArrayList<ViewListItem> items = new ArrayList<>();
            for (i = 0; i < total; i++) {

                JSONObject singleRow = object.getJSONObject("" + i);
                items.add(new ViewListItem(singleRow.getString(NetworkContract.JSON_NAME),
                        singleRow.getString(NetworkContract.JSON_REG),
                        singleRow.getString(NetworkContract.JSON_SCHOOL),
                        singleRow.getString(NetworkContract.JSON_CGPA)));

            }

            return items;
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
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
