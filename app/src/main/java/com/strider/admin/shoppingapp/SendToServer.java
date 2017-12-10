package com.strider.admin.shoppingapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Admin on 12/10/2017.
 */

public abstract class SendToServer {

    public final ServerConnect serverConnect;

    public SendToServer(Context context, ProgressBar progressBar, HashMap<String, String> payLoad) {
        this.serverConnect = new ServerConnect(context, progressBar, payLoad);
    }

    public void connect(String url) {
        serverConnect.execute(url);
    }

    public abstract void getResult(String result);

    private class ServerConnect extends AsyncTask<String, Void, String> {
        private HashMap<String, String> payLoads;
        private ProgressBar progressBar;

        public ServerConnect(Context context, ProgressBar progressBar, HashMap<String, String> payLoad) {
            payLoads = payLoad;
            this.progressBar = progressBar;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder();
                for (HashMap.Entry<String, String> temp : payLoads.entrySet()) {
                    builder.appendQueryParameter(temp.getKey(), temp.getValue());
                }

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(builder.build().getEncodedQuery());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(ProgressBar.GONE);

        }
    }
}
