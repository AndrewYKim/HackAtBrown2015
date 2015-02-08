package com.example.eddie.hackatbrown2;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

public class CommentActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.brown.hack.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        String provider = lm.getBestProvider(cri, true);
        Location myLocation = lm.getLastKnownLocation(provider);
        String message = editText.getText().toString();
        String date = "";
        Calendar c = Calendar.getInstance();
        date += Integer.toString(c.get(Calendar.MONTH));
        date += Integer.toString(c.get(Calendar.DATE));
        date += Integer.toString(c.get(Calendar.YEAR));
        date += ":";
        date += Integer.toString(c.get(Calendar.HOUR));
        date += Integer.toString(c.get(Calendar.MINUTE));
        int upvotes = 0;
        int downvotes = 0;
        float longitude = (float) myLocation.getLongitude();
        float latitude = (float) myLocation.getLatitude();
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("https://safe-dawn-3761.herokuapp.com/comments");
        postMethod.addParameter("text", message);
        postMethod.addParameter("date", date);
        postMethod.addParameter("upvotes", Integer.toString(upvotes));
        postMethod.addParameter("downvotes", Integer.toString(downvotes));
        postMethod.addParameter("longitude", String.valueOf(longitude));
        postMethod.addParameter("latitude", String.valueOf(latitude));
        try {
            httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return "<html><form method = \"post\" action=\"https://safe-dawn-3761.herokuapp.com/comments\"> <input type = \"text\" name = \"text\" value = \"" + message + "\"><br><input type \"text\" name=\"date\" value=\"" + date + "\"<br> <input type = \"text\" name = \"lo\" value = \"" + longitude + "\"<br> <input type = \"text\" name = \"la\" value = \"" + latitude + "\"<br> <input type = \"text\" name = \"up\" value = \"" + upvotes + "\"<br> <input type = \"text\"  name = \"do\" value = \"" + downvotes + "\"<br> <input type = \"submit\" value = \"submit\"></form></html>";
        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
    }
}