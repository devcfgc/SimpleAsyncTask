package com.devcfgc.simpleasynctask.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SimpleAsyncTaskActivity extends Activity {

    private Button mBtnLoadWeb;
    private Button mBtnShowMsg;
    private TextView mTextView;
    private String mWebUrl = "http://www.devcfgc.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_async_task);

        //getting the ui elements
        mBtnLoadWeb = (Button) findViewById(R.id.btnLoadWeb);
        mBtnShowMsg = (Button) findViewById(R.id.btnShowMsg);
        mTextView = (TextView) findViewById(R.id.textViewWeb);

        //When click on the BtnLoadWeb the web is loaded in a background process
        mBtnLoadWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadWebAsyncTask task = new LoadWebAsyncTask();
                task.execute(new String[] { mWebUrl });
            }
        });

        //When click on the mBtnShowMsg show a Toast message
        mBtnShowMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SimpleAsyncTaskActivity.this,
                                getString(R.string.msg_toast),
                                Toast.LENGTH_LONG).show();
            }
        });
    }

    //AsyncTask - get the html code from http://www.devcfgc.com
    private class LoadWebAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //show the html code in the textview
            mTextView.setText(result);
        }
    }
}