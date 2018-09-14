package com.example.marti.amiclient.settings.address;

import android.os.AsyncTask;
import android.util.Log;

import com.example.marti.amiclient.settings.Constant;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marti on 13/09/18.
 */

public class DownloadAddressData extends AsyncTask<String,Void,String> {

    String numeroCalle="";
    String numeroCarrera="";

    public DownloadAddressData(){

    }

    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try {
            data = downloadUrl(url[0]);
            Log.d("Background Task data", data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject googleMapResponse = new JSONObject(result);
            JSONArray results = (JSONArray) googleMapResponse.get("results");

            JSONObject subResult = results.getJSONObject(0);

            if (subResult.has("address_components"))
            {
                JSONArray address_component = (JSONArray) subResult.get("address_components");

                for (int i = 0; i < address_component.length(); i++)
                {
                    JSONObject addressCompRes = address_component.getJSONObject(i);

                    if (addressCompRes.has("types"))
                    {
                        JSONArray types = addressCompRes.getJSONArray("types");

                        for (int k = 0; k < types.length(); k++)
                        {
                            if ("street_number".equals(types.getString(k)))
                            {
                                if (addressCompRes.has("long_name"))
                                {
                                    numeroCalle = addressCompRes.getString("long_name");
                                }
                            }

                            if ("route".equals(types.getString(k)))
                            {
                                if (addressCompRes.has("long_name"))
                                {
                                    numeroCarrera = addressCompRes.getString("long_name");
                                }
                            }
                        }

                    }

                }
            }

            Constant.DIRECCION_ACTUAL_GOOGLE = numeroCarrera + " " + "CLL " + numeroCalle;

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
