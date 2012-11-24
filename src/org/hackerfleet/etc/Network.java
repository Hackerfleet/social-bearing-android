package org.hackerfleet.etc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hackerfleet.model.Buoy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Network {

  public interface ResultListener {
    public void onResponse(StatusLine statusLine);

    public void onResult(String result);
  }

  public static boolean isNetworkAvailable(Context context) {

    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    // if no network is available networkInfo will be null, otherwise check if we are connected
    if (networkInfo != null && networkInfo.isConnected()) {
      return true;
    }
    return false;
  }

  public static void upload(final ResultListener listener, Buoy... buoys) throws IOException, JSONException {
    new AsyncTask<Buoy, Void, HttpResponse>() {

      @Override
      protected HttpResponse doInBackground(Buoy... buoys) {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(AppDefs.POST_URL);
        try {
          JSONObject requestObject = new JSONObject();
          requestObject.put("device_uuid", "your mom");
          requestObject.put("device_model", "guess what");

          JSONArray jsonArray = new JSONArray();
          for (Buoy buoy : buoys) {
            jsonArray.put(buoy.toJSON());
          }
          requestObject.put("buoys", jsonArray);
          request.setEntity(new StringEntity(requestObject.toString()));
          HttpResponse response =
              client.execute(request);
          return response;
        } catch (JSONException jsonE) {
          Log.e(AppDefs.TAG, "It's kaputt!", jsonE);
        } catch (UnsupportedEncodingException uee) {
          Log.e(AppDefs.TAG, "It's kaputt!", uee);
        } catch (ClientProtocolException cpe) {
          Log.e(AppDefs.TAG, "It's kaputt!", cpe);
        } catch (IOException ioe) {
          Log.e(AppDefs.TAG, "It's kaputt!", ioe);
        }
        return null;
      }

      @Override
      protected void onPostExecute(HttpResponse response) {

        if (listener != null)
          listener.onResponse(response.getStatusLine());
        try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(
              response.getEntity().getContent()));
          String line = "";
          StringBuilder responseValue = new StringBuilder();
          while ((line = reader.readLine()) != null) {
            responseValue.append(line);
            listener.onResult(responseValue.toString());
          }
        } catch (IOException ioe) {
          Log.e(AppDefs.TAG, "It's kaputt!", ioe);
        }

      }
    }.execute(buoys);

  }
}
