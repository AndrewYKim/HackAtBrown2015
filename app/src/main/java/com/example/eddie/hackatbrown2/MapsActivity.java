package com.example.eddie.hackatbrown2;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.content.Intent;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    //private Timer myTimer;
    //final Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
        setContentView(R.layout.main);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connect();
        setUpMapIfNeeded();
        setUpList(savedInstanceState);

        //myTimer = new Timer();
        //myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                UpdateGUI();
//            }
//        }, 0, 1000);
//
//        new Timer().scheduleAtFixedRate(t, 1000L, 1000L);
    }

//    private void UpdateGUI(){
//        myHandler.post(myRunnable);
//    }

//    final Runnable myRunnable = new Runnable() {
//        public void run(){
////            setUpMapIfNeeded();
////            //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
////            mMap.setMyLocationEnabled(true);
////            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Criteria cri = new Criteria();
//            String provider = locationManager.getBestProvider(cri, true);
//            Location myLocation = new Location(provider);
//            double myLat = myLocation.getLatitude();
//            double myLong = myLocation.getLongitude();
//            LatLng myLatLng =  new LatLng(myLat,myLong);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//        }
//    };

//    private void TimerMethod(){
//        this.runOnUiThread(Timer_Tick);
//    }
//
//    private Runnable Timer_Tick = new Runnable() {
//        public void run() {
//            onCreate(Bundle savedInstanceState);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
//        setUpList();
    }

    private void connect() {
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://www.google.com");
            HttpResponse response = client.execute(request);
        } catch (ClientProtocolException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    public void setUpMap() {
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        String provider = locationManager.getBestProvider(cri, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //double latitude = myLocation.getLatitude();
        //double longitude = myLocation.getLongitude();


    }

    public void setUpList(Bundle savedInstanceState){
        URLReader urlreader = new URLReader();
        String[] infoArray = urlreader.getInfo();

        mainListView = (ListView) findViewById(R.id.mainListView);

        // Create and populate a List of planet names.
        String[] planets = new String[] {};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);

        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.

        for (int j=0; j<infoArray.length; j++) {
            int indexMessage = infoArray[j].indexOf("\"text\"") + 9;
            int endIndexMessage = infoArray[j].indexOf("\",", indexMessage);
            String message = infoArray[j].substring(indexMessage, endIndexMessage);

            listAdapter.add(message);
// implement date later
//            int indexMessage2 = infoArray[j].indexOf("\"date\"") + 7;
//            int endIndexMessage2 = infoArray[j].indexOf("\"", indexMessage2);
//            message = message.concat("\n"+ infoArray[j].substring(indexMessage2, endIndexMessage2));
//
        }

        listAdapter.add("blah");
        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);
    }

    public void post(){
        Intent intent = new Intent(this, CommentActivity.class);
        this.startActivity(intent);

        EditText editText = (EditText) findViewById(R.id.edit_message);


    }
}
        //mMap.addMarker(new MarkerOptions().position(myLatLng));

