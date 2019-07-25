package com.example.javaapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.javaapp.databasehelper;

public class EditSchedule extends AppCompatActivity {
    public String[] courses = new String[13];
    public String[][] timetable=new String[5][8];
    int i,j;
    databasehelper mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        Intent in=getIntent();
        courses [0]= "None";
        courses [1]=""+  in.getStringExtra("course1");
        courses [2]=""+  in.getStringExtra("course2");
        courses [3]=""+  in.getStringExtra("course3");
        courses [4]=""+  in.getStringExtra("course4");
        courses [5]=""+  in.getStringExtra("course5");
        courses [6]=""+  in.getStringExtra("course6");
        courses [7]=""+  in.getStringExtra("course7");
        courses [8]=""+  in.getStringExtra("course8");
        courses [9]=""+  in.getStringExtra("course9");
        courses[10]=""+  in.getStringExtra("course10");
        courses[11]=""+  in.getStringExtra("course11");
        courses[12]=""+  in.getStringExtra("course12");
        courses = setcourses();
        SetSpinners();
        mdb= new databasehelper(this);
    }
    public void SaveAndGoToTimetable(View view) {
        savedata();
        Intent i2= new Intent(getApplicationContext(), Timetable.class);
        startActivity(i2);
    }
    public void GoToEditCourses(View view) {
        Intent i3= new Intent(getApplicationContext(), EditCourses.class);
        startActivity(i3);
    }
    public void SetSpinners(){
        Spinner[][] spin = new Spinner[5][8] ;
        String[] days = {"mon","tue","wed","thu","fri"};
        String buttonID;
        int resID;
        ArrayAdapter<String> adapt= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,courses);
        adapt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        for(i=0; i<5; i++) {
            for(j=1; j<=8; j++) {
                buttonID = days[i]+ j+"";
                resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                spin[i][j-1] = (Spinner) findViewById(resID);
                spin[i][j-1].setAdapter(adapt);
                final int a=i,b=j;
                spin[a][b-1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        timetable[a][b-1] = (String) parent.getItemAtPosition(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        timetable[a][b-1]="None";
                    }
                });
            }
        }
    }
    public String[] setcourses() {
        int k=0,i;
        for(i=0;i<13;i++){
            if(courses[i].equals("")) k++;
        }
        String[] cour=new String[13-k];
        k=0;
        for(i=0;i<13;i++){
            if(!(courses[i].equals(""))) {cour[k]=courses[i];k++;}
        }
        return cour;
    }
    public String[] setcourses2() {
        int k=0,i,l=courses.length;
        for(i=0;i<l;i++){
            if(courses[i].equals("None")) k++;
        }
        String[] cour=new String[12];
        k=0;
        for(i=0;i<l;i++){
            if(!(courses[i].equals("None"))) {cour[k]=courses[i];k++;}
        }
        for(;k<12;k++){
            cour[k]="";
        }
        return cour;
    }
    public void savedata(){
        courses=setcourses2();
         if (mdb.setschedule(timetable)&&mdb.setcourses(courses)) Toast.makeText(this,"saved successfully!", Toast.LENGTH_SHORT).show();
         else Toast.makeText(this,"Sorry!, error happened", Toast.LENGTH_SHORT).show();
    }
}