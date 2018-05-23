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

import java.util.ArrayList;

public class HRStateHistory extends AppCompatActivity{

    String name, userid, dateHRState, hrstateValue, hrstateTime, hrstateTimestamp;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;
    ArrayList<String> array5; //array untuk date hrstate
    ArrayList<String> array6; //array untuk hrstate
    ArrayList<String> array7; //array untuk time
    ArrayList<String> array8; //array utk timestamp
    LineGraphSeries<DataPoint> series ;
    double y,x ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrsstate);

        final GraphView graph1 = (GraphView)findViewById(R.id.graph1);
        series = new LineGraphSeries<DataPoint>();
        graph1.addSeries(series);
        Viewport vp = graph1.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(1);
        vp.setMaxX(150); //yg ditunjukin max berapa
        graph1.getViewport().setScrollable(true); // enables horizontal scrolling
        graph1.getViewport().setScrollableY(true); // enables vertical scrolling
        graph1.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph1.getViewport().setScalableY(true); // enables vertical zooming and scrolling


        array5 = new ArrayList<>(); //array untuk Date hrstate
        array6 = new ArrayList<>(); //array untuk hrstate value
        array7 = new ArrayList<>(); //array untuk hrstate time
        array7 = new ArrayList<>(); //array untuk hrstate timestamp

        Intent intent = getIntent();
        if(intent!=null)
        {
            userid = intent.getStringExtra("userid");
        }


        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        ref.child("fallstate").child("nilaifallstate").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dateHRState = dataSnapshot.child("Date").getValue(String.class);
                hrstateValue = dataSnapshot.child("hrstate").getValue(String.class);
                hrstateTime = dataSnapshot.child("Time").getValue(String.class);
                hrstateTimestamp = dataSnapshot.child("Timestamp").getValue(String.class);
                Log.i("date hrstate", "date hrstate " + dateHRState);
                Log.i("hrstate value", "hrstate value " + hrstateValue);
                showData(dataSnapshot);
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
        array5.add(dateHRState);
        array6.add(hrstateValue);
        array7.add(hrstateTime);
        array8.add(hrstateTimestamp);
    }

}
