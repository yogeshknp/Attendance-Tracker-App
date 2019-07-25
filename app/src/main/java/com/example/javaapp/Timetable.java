package com.example.javaapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.security.PublicKey;
import com.example.javaapp.databasehelper;

public class Timetable extends AppCompatActivity {
    public String[][] timetable=new String[5][8];
    databasehelper mdb;
    TextView me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        me=(TextView) findViewById(R.id.me);
        mdb=new databasehelper(this);
        timetable=mdb.outputtimetable();
        if(timetable[0][0]==null) me.setText("Click EDIT to set your timetable");
        else displaytimetable();
    }
    public void GoToMainActivity(View view) {
        Intent i2= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i2);
    }
    public void GoToEditCourses(View view) {
        Intent i3= new Intent(getApplicationContext(), EditCourses.class);
        startActivity(i3);
    }

    public void displaytimetable(){
        int i,j,resID;
        String buttonID;
        for(i=0;i<5;i++){
            for(j=0;j<8;j++){
                buttonID = "t"+i+ j;
                resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                me=(TextView) findViewById(resID);
                if(!(timetable[i][j].equals("None"))) {
                    try {
                        me.setText(timetable[i][j]);
                    } finally {
                    }
                }
                else me.setText("-");
            }
        }
    }

}