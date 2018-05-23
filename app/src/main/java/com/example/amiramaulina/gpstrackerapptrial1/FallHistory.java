package com.example.amiramaulina.gpstrackerapptrial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class FallHistory extends AppCompatActivity{

    String name, userid, prevdate, dateFallState, fallstateValue, fallstateTime, fallstateTimestamp;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;
    ArrayList<String> array9; //array untuk date fallstate
    ArrayList<String> array10; //array untuk fallstateValue
    ArrayList<String> array11; //array untuk fallstateTime
    ArrayList<String> array12; //array untuk fallstateTimestamp
    LineGraphSeries<DataPoint> series ;
    PointsGraphSeries<DataPoint> xySeries;
    GraphView mScatterPlot;
    double y,x ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_history);

        final GraphView graph = (GraphView)findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        Viewport vp = graph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(1);
        vp.setMaxX(150); //yg ditunjukin max berapa
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling


        array9 = new ArrayList<>(); //array untuk Date fallstate
        array10 = new ArrayList<>(); //array untuk fallstate value

        Intent intent = getIntent();
        if(intent!=null)
        {
            name = intent.getStringExtra("name");
            userid = intent.getStringExtra("userid");
            prevdate = intent.getStringExtra("date");
        }


        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        ref.child("fallstate").child("nilaifallstate").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //  Toast.makeText(getApplicationContext(),"onAdded",Toast.LENGTH_SHORT).show();
                dateFallState = dataSnapshot.child("Date").getValue(String.class);
                fallstateValue = dataSnapshot.child("fallstate").getValue(String.class);
                fallstateTime = dataSnapshot.child("Time").getValue(String.class);
                fallstateTimestamp = dataSnapshot.child("Timestamp").getValue(String.class);
                int fsValue = 500;
                Log.i("date fallstate", "date fallstate " + dateFallState);
                Log.i("fallstate value", "fallstate value " + fallstateValue);
                showData(dataSnapshot);
                x = x+1;
                y = fsValue;
                series.appendData(new DataPoint(x, y), false, 1000);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        array9.add(dateFallState);
        array10.add(fallstateValue);
        array11.add(fallstateTime);
        array12.add(fallstateTimestamp);
    }










}
