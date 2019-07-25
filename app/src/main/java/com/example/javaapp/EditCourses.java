package com.example.javaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditCourses extends AppCompatActivity {
    EditText course1;
    EditText course2;
    EditText course3;
    EditText course5;
    EditText course4;
    EditText course6;
    EditText course7;
    EditText course8;
    EditText course9;
    EditText course10;
    EditText course11;
    EditText course12;
    EditText Name;
    databasehelper mdb;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_courses);
        course1=(EditText) findViewById(R.id.Course1);
        course2=(EditText) findViewById(R.id.Course2);
        course3=(EditText) findViewById(R.id.Course3);
        course5=(EditText) findViewById(R.id.Course4);
        course4=(EditText) findViewById(R.id.Course5);
        course6=(EditText) findViewById(R.id.Course6);
        course7=(EditText) findViewById(R.id.Course7);
        course8=(EditText) findViewById(R.id.Course8);
        course9=(EditText) findViewById(R.id.Course9);
        course10=(EditText) findViewById(R.id.Course10);
        course11=(EditText) findViewById(R.id.Course11);
        course12=(EditText) findViewById(R.id.Course12);
        Name=(EditText) findViewById(R.id.editname);
        mdb=new databasehelper(this);
        showsavedcourses();
    }
    public void GoToTimetable(View view) {
        Intent i2= new Intent(getApplicationContext(), Timetable.class);
        startActivity(i2);
    }
    public void GoToEditSchedule(View view){
        Intent i2= new Intent(getApplicationContext(), EditSchedule.class);
        i2.putExtra("course1",course1.getText()+"");
        i2.putExtra("course2",course2.getText()+"");
        i2.putExtra("course3",course3.getText()+"");
        i2.putExtra("course4",course4.getText()+"");
        i2.putExtra("course5",course5.getText()+"");
        i2.putExtra("course6",course6.getText()+"");
        i2.putExtra("course7",course7.getText()+"");
        i2.putExtra("course8",course8.getText()+"");
        i2.putExtra("course9",course9.getText()+"");
        i2.putExtra("course10",course10.getText()+"");
        i2.putExtra("course11",course11.getText()+"");
        i2.putExtra("course12",course12.getText()+"");
        mdb.setdetails(Name.getText()+"","");
        startActivity(i2);
    }
    public void showsavedcourses(){
        String[][] courses=mdb.outputAttendence();
        String[] details=mdb.getdetails();
        if(courses[0][0]==null) return;
        int i,resID,k=1;
        String ID;
        EditText Course;
        for(i=0;i<12;i++){
            if (!(courses[i][0].equals("none"))){
                ID="Course"+k;k++;
                resID = getResources().getIdentifier(ID, "id", getPackageName());
                Course=(EditText) findViewById(resID);
                Course.setText(courses[i][0]);
            }
        }
        if(details[0]!="student") Name.setText(details[0]);
    }
}
