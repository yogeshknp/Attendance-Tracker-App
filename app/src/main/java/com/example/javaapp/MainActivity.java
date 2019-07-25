package com.example.javaapp;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    databasehelper mdb;
    TextView day;
    TextView display2;
    SimpleDateFormat form=new SimpleDateFormat("EEE");
    SimpleDateFormat DATE=new SimpleDateFormat("yyyy.MM.dd");
    public String[][] timetable;
    public String[] details;
    public String[] radioresponse=new String[8];
    public String date;
    public String week_day;
    public String[][] attendence;
    public RadioGroup[] radio=new RadioGroup[8];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_name);
        day =(TextView) findViewById(R.id.day);
        week_day=form.format(new Date());
        date=DATE.format(new Date());
        display2=(TextView) findViewById(R.id.display2);
        mdb=new databasehelper(this);
        timetable=mdb.outputtimetable();
        mdb.setdetails("student","");
        details=mdb.getdetails();
        setviews(getweekin(week_day));
    }

    public void ShowTimetable(View view) {
        Intent i2= new Intent(getApplicationContext(), Timetable.class);
        startActivity(i2);
    }
    public void ShowAttendence(View view) {
        Intent i3= new Intent(getApplicationContext(), Attendence.class);
        startActivity(i3);
    }
    public void SubmitAttendence(View view){
        attendence = mdb.outputAttendence();
        if(attendence[0][0].equals("none")) {display2.setText("Click Timetable");return;}
        int i = getweekin(week_day),j;
        int[][] p=new int[8][2];
        int x,y;
        if(date.equals(details[1])){
            if(!(getrespose())) {display2.setText("Not submitted!\nMark all responses"); return;}
            int[][] r=new int[12][2];
            for(j=0;j<12;j++) {r[j][0]=0;r[j][1]=0;}
            for(j=0;j<8;j++){
                if(!(timetable[i][j].equals("None"))){
                    int k=findin(timetable[i][j]);
                    if(radioresponse[j].equals("going ")) {
                        x=Integer.parseInt(attendence[k][1]);
                        y=Integer.parseInt(attendence[k][2]);
                        r[k][0]=r[k][0]+1;
                        r[k][1]=r[k][1]+1;
                        x=(x/1000)*1000+(x/1000)+r[k][0];
                        y=(x/1000)*1000+(y/1000)+r[k][1];
                        attendence[k][1]=x+"";
                        attendence[k][2]=y+"";
                    }
                    else if(radioresponse[j].equals("not going ")){
                        x=Integer.parseInt(attendence[k][1]);
                        y=Integer.parseInt(attendence[k][2]);
                        x=(x/1000)*1000+(x/1000)+r[k][0];
                        r[k][1]=r[k][1]+1;
                        y=(y/1000)*1000+(y/1000)+r[k][1];
                        attendence[k][1]=x+"";
                        attendence[k][2]=y+"";
                    }
                    else if(radioresponse[j].equals("not happened ")){
                        x=Integer.parseInt(attendence[k][1]);
                        y=Integer.parseInt(attendence[k][2]);
                        x=(x/1000)*1000+(x/1000)+r[k][0];
                        y=(y/1000)*1000+(y/1000)+r[k][1];
                        attendence[k][1]=x+"";
                        attendence[k][2]=y+"";
                    }
                }
            }
        }
        else{
            if(!(getrespose())) { display2.setText("Not submitted!\nMark all responses"); return;}
            for(j=0;j<8;j++) {
                if (!(timetable[i][j].equals("None"))){
                    x = Integer.parseInt(attendence[findin(timetable[i][j])][1]);
                    y = Integer.parseInt(attendence[findin(timetable[i][j])][2]);
                    p[j][0] = (x % 1000);
                    p[j][1] = (y % 1000);
                }
            }
            for(j=0;j<8;j++) {
                if (!(timetable[i][j].equals("None"))){
                    if(radioresponse[j].equals("going ")) {
                        x=Integer.parseInt(attendence[findin(timetable[i][j])][1]);
                        y=Integer.parseInt(attendence[findin(timetable[i][j])][2]);
                        x=(p[j][0])*1000+(x%1000)+1;
                        y=(p[j][1])*1000+(y%1000)+1;
                        attendence[findin(timetable[i][j])][1]=x+"";
                        attendence[findin(timetable[i][j])][2]=y+"";
                    }
                    else if(radioresponse[j].equals("not going ")){
                        x=Integer.parseInt(attendence[findin(timetable[i][j])][1]);
                        y=Integer.parseInt(attendence[findin(timetable[i][j])][2]);
                        x=(p[j][0])*1000+(x%1000);
                        y=(p[j][1])*1000+(y%1000)+1;
                        attendence[findin(timetable[i][j])][1]=x+"";
                        attendence[findin(timetable[i][j])][2]=y+"";
                    }
                    else if(radioresponse[j].equals("not happened ")){
                        x=Integer.parseInt(attendence[findin(timetable[i][j])][1]);
                        y=Integer.parseInt(attendence[findin(timetable[i][j])][2]);
                        x=(p[j][0])*1000+(x%1000);
                        y=(p[j][1])*1000+(y%1000);
                        attendence[findin(timetable[i][j])][1]=x+"";
                        attendence[findin(timetable[i][j])][2]=y+"";
                    }
                }
            }
        }
        mdb.setdetails(details[0],date);
        mdb.setAttendence(attendence);
        attendence=mdb.outputAttendence();
        display2.setText("Attendence submitted!");
    }
    public int findin(String s){
        int i;
        for(i=0;i<12;i++){
            if(attendence[i][0].equals(s)) return i;
        }
        return -1;
    }
    public boolean getrespose(){
        int i;
        RadioButton rad;
        for(i=0;i<8;i++){
            if(radio[i]!=null) {
                if (radio[i].getCheckedRadioButtonId() != -1) {
                    rad = (RadioButton) findViewById(radio[i].getCheckedRadioButtonId());
                    radioresponse[i] = rad.getText() + " ";
                }
                else return false;
            }
            else radioresponse[i]="no response";
        }
        return true;
    }

    public int getweekin(String ss){
        if     (ss.equals("Mon")) return 0;
        else if(ss.equals("Tue")) return 1;
        else if(ss.equals("Wed")) return 2;
        else if(ss.equals("Thu")) return 3;
        else if(ss.equals("Fri")) return 4;
        return -1;
    }
    public void setviews(int j){
        if(timetable[0][0]==null) {day.setText("Set Your Timetable First\nClick Timetable");return;}
        if(j==(-1)) {day.setText("Hey "+details[0]+"! have Fun!,\n it's Weekend");return;}
        LayoutInflater inflater=LayoutInflater.from(this);
        LinearLayout options_layout = (LinearLayout) findViewById(R.id.list);
        int[] s={8,9,10,11,12,2,3,4};
        String buttonID;
        int resID,i;
        Drawable timeimage;
        for (i = 0; i < 8; i++) {
            if(!(timetable[j][i].equals("None"))){
                View to_add = inflater.inflate(R.layout.day_list_item, options_layout, false);
                buttonID = "time" + s[i]+ "";
                resID = getResources().getIdentifier(buttonID, "drawable", getPackageName());
                timeimage = getResources().getDrawable(resID);
                ImageView image = (ImageView) to_add.findViewById(R.id.timeimage);
                image.setImageDrawable(timeimage);
                TextView text2 = (TextView) to_add.findViewById(R.id.classname);
                text2.setText(timetable[j][i]);
                RadioGroup rad=(RadioGroup) to_add.findViewById(R.id.radiobutton);
                radio[i]=rad;
                options_layout.addView(to_add);
            }
        }
    }
}