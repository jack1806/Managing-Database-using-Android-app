package com.dbmsjack.dbms.NetworkUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.dbmsjack.dbms.objects.SharedContractClass;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.dbmsjack.dbms.NetworkUtils.NetworkContract;
import com.dbmsjack.dbms.objects.ViewListItem;

/**
 * Created by root on 14/9/17.
 */

public class ConnectNetwork {

    public String url;
    public String const_address_1 = "http://";
    public String const_address_2 = "/dbms/";
    public SharedPreferences preferences;

    public ConnectNetwork(Context context,String url_additional){

        preferences = context.getSharedPreferences(SharedContractClass.shared_pref_name,
                SharedContractClass.shred_pref_mode);

        url = preferences.getString(SharedContractClass.server_address,
                SharedContractClass.default_value);

        url = const_address_1+url+const_address_2+url_additional;

    }

    public ConnectNetwork(Context context){

        preferences = context.getSharedPreferences(SharedContractClass.shared_pref_name,
                SharedContractClass.shred_pref_mode);

        url = preferences.getString(SharedContractClass.server_address,
                SharedContractClass.default_value);
        url = const_address_1+url+const_address_2;

    }

    public String postData(ArrayList<String> arr_fields,
                            ArrayList<String> arr_vals){

        httpPostRequest postRequest = new httpPostRequest();
        String result = "";
        ArrayList<ArrayList<String>> postArray = new ArrayList<>();
        arr_fields.add(0,url);
        postArray.add(arr_fields);
        postArray.add(arr_vals);
        try {
            result = postRequest.execute(postArray).get();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(ExecutionException e){
            e.printStackTrace();
        }

        //Log.d("RESULT : ",result);
        return result;

    }

    public String getData(){

        httpGetRequest getRequest = new httpGetRequest();
        String result = null;
        try {
            result = getRequest.execute(url).get();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(ExecutionException e){
            e.printStackTrace();
        }

        return result;
    }

    public class httpGetRequest extends AsyncTask<String,Void,String>{
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url_s = strings[0];
            String result;
            try{
                Log.d("Connect Network Class ", "doInBackground: "+url_s);
                URL url = new URL(url_s);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod(NetworkContract.REQUEST_GET_METHOD);
                httpURLConnection.setReadTimeout(NetworkContract.READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(NetworkContract.CONNECTION_TIMEOUT);
                httpURLConnection.connect();

                InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String inputLine;
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                httpURLConnection.disconnect();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
        }
    }

    /*
    public class httpPostRequest extends AsyncTask<ArrayList<String>,Void,String>{

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {
            String url_s = arrayLists[0].get(0);
            String name = arrayLists[0].get(1);
            String reg = arrayLists[0].get(2);
            String school = arrayLists[0].get(3);
            String cgpa = arrayLists[0].get(4);
            String result;
            try{
                Log.d("Connect Network Class ", "doInBackground: "+url_s);
                URL url = new URL(url_s);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod(NetworkContract.REQUEST_POST_METHOD);
                httpURLConnection.setReadTimeout(NetworkContract.READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(NetworkContract.CONNECTION_TIMEOUT);

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                sb.append(URLEncoder.encode(NetworkContract.JSON_NAME, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(name, "UTF-8"));
                sb.append("&");
                sb.append(URLEncoder.encode(NetworkContract.JSON_REG, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(reg, "UTF-8"));
                sb.append("&");
                sb.append(URLEncoder.encode(NetworkContract.JSON_SCHOOL, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(school, "UTF-8"));
                sb.append("&");
                sb.append(URLEncoder.encode(NetworkContract.JSON_CGPA, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(cgpa, "UTF-8"));
                writer.write(sb.toString());
                writer.flush();
                writer.close();
                os.close();

                httpURLConnection.connect();

                InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String inputLine;
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                httpURLConnection.disconnect();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            catch (RuntimeException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }*/

    public class httpPostRequest extends AsyncTask<ArrayList<ArrayList<String>>,Void,String>{

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(ArrayList<ArrayList<String>>... arrayLists) {
            ArrayList<ArrayList<String>> list;
            String url_s;
            int itter_arrayLists,itter_vals,len_vals,
                    len_arraylists = arrayLists.length;
            ArrayList<String> arr_fields , arr_values;
            for(itter_arrayLists=0;itter_arrayLists<len_arraylists;itter_arrayLists++){
                list = arrayLists[itter_arrayLists];
                arr_fields = list.get(0);
                arr_values = list.get(1);
                url_s = arr_fields.get(0);
                len_vals = arr_values.size();
                String result;
                try{
                    Log.d("Connect Network Class ", "doInBackground: "+url_s);
                    URL url = new URL(url_s);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod(NetworkContract.REQUEST_POST_METHOD);
                    httpURLConnection.setReadTimeout(NetworkContract.READ_TIMEOUT);
                    httpURLConnection.setConnectTimeout(NetworkContract.CONNECTION_TIMEOUT);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    for(itter_vals=0;itter_vals<len_vals;itter_vals++){
                        sb.append(URLEncoder.encode(arr_fields.get(itter_vals+1), "UTF-8"));
                        sb.append("=");
                        sb.append(URLEncoder.encode(arr_values.get(itter_vals), "UTF-8"));
                        sb.append("&");
                    }
                    writer.write(sb.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    httpURLConnection.connect();

                    InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String inputLine;
                    //Check if the line we are reading is not null
                    while((inputLine = reader.readLine()) != null){
                        stringBuilder.append(inputLine);
                    }

                    //Close our InputStream and Buffered reader
                    reader.close();
                    streamReader.close();

                    //Set our result equal to our stringBuilder
                    result = stringBuilder.toString();
                    httpURLConnection.disconnect();
                }
                catch(IOException e){
                    e.printStackTrace();
                    result = null;
                }
                catch (RuntimeException e){
                    e.printStackTrace();
                    result = null;
                }
                return result;
            }
           return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
