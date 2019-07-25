package com.example.javaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Attendence extends AppCompatActivity {
    String[][] attendence=new String[12][3];
    databasehelper mdb;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        display=(TextView) findViewById(R.id.display);
        mdb=new databasehelper(this);
        attendence=mdb.outputAttendence();
        setviews();
    }
    public void GoToMainActivity(View view) {
        Intent i2= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i2);
    }
    public void ShowMessage(View view) {
        String s="Sorry! ,You can't edit your destity";
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    public void setviews(){
        if(attendence[0][0].equals("none")) {display.setText("Set Your Timetable First\nClick Timetable");return;}
        LayoutInflater inflater=LayoutInflater.from(this);
        LinearLayout options_layout = (LinearLayout) findViewById(R.id.attendenceView);
        int i;
        for(i=0;i<12;i++){
            if (!(attendence[i][0].equals("none")||attendence[i][1].equals("none")|| attendence[i][2].equals("none"))){
                int x=0,y=0;
                if(attendence[i][1]!="") x=Integer.parseInt(attendence[i][1]);
                if(attendence[i][2]!="") y=Integer.parseInt(attendence[i][2]);
                View to_add = inflater.inflate(R.layout.attendence_list_item, options_layout, false);
                TextView text2 = (TextView) to_add.findViewById(R.id.details);
                text2.setText("Attended " + x%1000 + " Out Of " + y%1000+"");
                TextView text = (TextView) to_add.findViewById(R.id.coursename);
                text.setText(attendence[i][0]);
                TextView text3 = (TextView) to_add.findViewById(R.id.percent);
                text3.setText(percent(x%1000, y%1000));
                options_layout.addView(to_add);
            }
        }
    }
    public String percent(int i,int j){
        if(j==0||i==0) return "0.0%";
        double a = i,b=j;
        double ans=(a/b)*100;
        ans=Math.round(ans*10.0)/10.0;
        String S=""+ans+"%";
        return S;
    }
}
