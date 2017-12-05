package com.dbmsjack.dbms;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dbmsjack.dbms.NetworkUtils.ConnectNetwork;
import com.dbmsjack.dbms.NetworkUtils.NetworkContract;
import com.dbmsjack.dbms.objects.QueryAdapter;
import com.dbmsjack.dbms.objects.QueryObject;
import com.dbmsjack.dbms.objects.SharedContractClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QueryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QueryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueryFragment newInstance(String param1, String param2) {
        QueryFragment fragment = new QueryFragment();
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
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        final SharedPreferences preferences = getActivity().getSharedPreferences(SharedContractClass.shared_pref_name,
                SharedContractClass.shred_pref_mode);

        if(preferences.contains(SharedContractClass.server_address)){

        }
        else{
            // Raise An Error That Server address isn't Specified
            Toast.makeText(getContext(),"No server address Specified",Toast.LENGTH_LONG).show();
        }
        final EditText inputQuery = (EditText) view.findViewById(R.id.input_query);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.result_out);
        Button submitQuery = (Button) view.findViewById(R.id.query_submit_button);
        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(preferences.contains(SharedContractClass.server_address)){

                    String query = inputQuery.getText().toString();
                    if (query.isEmpty())
                        Toast.makeText(getContext(), "Enter All details !", Toast.LENGTH_SHORT).show();
                    else {
                        ConnectNetwork network = new ConnectNetwork(getContext(), NetworkContract.QUERY_ADD);

                        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
                        ArrayList<String> arr_fields = new ArrayList<String>(), arr_vals = new ArrayList<String>();

                        arr_fields.add(NetworkContract.JSON_QUERY);
                        arr_vals.add(query);

                        String result = network.postData(arr_fields, arr_vals);

                        if (result != null) {
                            if (result.isEmpty() == false) {
                                //resultShow.setText(result);
                                decodeJSON(result,recyclerView);
                                //Toast.makeText(getContext(), "DONE !", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"Connection Failed !",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    // Raise An Error That Server address isn't Specified
                    Toast.makeText(getContext(),"No server address Specified",Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    public void decodeJSON(String s, RecyclerView recyclerView){

        try{
            JSONObject object = new JSONObject(s);
            int total = object.getInt(NetworkContract.JSON_TOTAL);
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            ArrayList<String> strings = new ArrayList<>();
            for(int j=0;j<total;j++)
                jsonObjects.add(object.getJSONObject(""+j));
            JSONObject student = object.getJSONObject("0");
            JSONArray jsonArray = student.names();
            for(int i=0;i<jsonArray.length();i++)
                strings.add(jsonArray.getString(i));
            QueryObject queryObject = new QueryObject(strings,jsonObjects,total);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new QueryAdapter(getContext(),queryObject));

        }
        catch (JSONException e){
            e.printStackTrace();
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
