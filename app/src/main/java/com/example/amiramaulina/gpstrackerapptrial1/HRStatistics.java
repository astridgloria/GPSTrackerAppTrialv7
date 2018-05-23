package com.example.amiramaulina.gpstrackerapptrial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class HRStatistics extends AppCompatActivity {

    String name, userid, hrDate, hrValue, hrTime, hrTimestamp;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref2;
    ArrayList<String> array1; //array untuk date hrstate
    ArrayList<String> array2, array3, array4; //array untuk hrstate, time, timestamp
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


        array1 = new ArrayList<>(); //array untuk Date HR
        array2 = new ArrayList<>(); //array untuk hrvalue
        array3 = new ArrayList<>();
        array4 = new ArrayList<>();

        Intent intent = getIntent();
        if(intent!=null)
        {
            userid = intent.getStringExtra("userid");
        }


        ref2 = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        ref2.child("hrvalue").child("nilaihr").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                hrDate = dataSnapshot.child("Date").getValue(String.class);
                hrValue = dataSnapshot.child("tmpHR").getValue(String.class);
                hrTime = dataSnapshot.child("Time").getValue(String.class);
                hrTimestamp = dataSnapshot.child("Timestamp").getValue(String.class);

                Log.i("date hrstate", "date hrstate " + hrDate);
                Log.i("hrstate value", "hrstate value " + hrValue);
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
        array1.add(hrDate);
        array2.add(hrValue);
        array3.add(hrTime);
        array4.add(hrTimestamp);
    }

}
