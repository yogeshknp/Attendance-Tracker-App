package com.example.javaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasehelper  extends SQLiteOpenHelper {
    public static final String CREATE_TABLE_Timetable ="CREATE TABLE TIMETABLE ( " +
            "day  INTEGER," +
            "EIGHT TEXT ,"+
            "NINE TEXT ," +
            "TEN TEXT," +
            "ELEVEN TEXT," +
            "TWELVE TEXT," +
            "TWO TEXT," +
            "THREE TEXT ," +
            "FOUR TEXT);";
    public static final String CREATE_TABLE_Attendence="CREATE TABLE ATTENDENCE(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "COURSE TEXT," +
            "ATTENDED TEXT," +
            "HAPPENED TEXT);";
    public static final String CREATE_TABLE_Details="CREATE TABLE DETAILS("+
            "ID INTEGER,"+
            "NAME TEXT," +
            "LOGINDATE TEXT);";

    public databasehelper(Context context){
        super(context, "track.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_Timetable);
        db.execSQL(CREATE_TABLE_Attendence);
        db.execSQL(CREATE_TABLE_Details);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean setdetails(String name,String logindate){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS"+" DETAILS");
        db.execSQL(CREATE_TABLE_Details);
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("LOGINDATE",logindate);
        long result = db.insert("DETAILS" , null, contentValues);
        if (result== -1) return false;
        return true;
    }
    public String[] getdetails(){
        String[] a=new String[2];
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT * FROM " + "DETAILS";
        Cursor data = db.rawQuery(query, null);
        if(data.moveToFirst()) {
            int c = data.getColumnIndex("NAME");
            int b = data.getColumnIndex("LOGINDATE");
            a[0] = data.getString(c);
            a[1] = data.getString(b);
        }
        else a[0]="student";
        return a;
    }

    public boolean setschedule(String[][] a){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE"+" TIMETABLE");
        db.execSQL(CREATE_TABLE_Timetable);
        int i;
        for(i=0;i<5;i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("day"   ,i);
            contentValues.put("EIGHT" ,a[i][0]);
            contentValues.put("NINE"  ,a[i][1]);
            contentValues.put("TEN"   ,a[i][2]);
            contentValues.put("ELEVEN",a[i][3]);
            contentValues.put("TWELVE",a[i][4]);
            contentValues.put("TWO"   ,a[i][5]);
            contentValues.put("THREE" ,a[i][6]);
            contentValues.put("FOUR"  ,a[i][7]);
            long result = db.insert("TIMETABLE" , null, contentValues);
            if (result== -1) return false;
        }
        return true;
    }
    public String[][] outputtimetable(){
        String ans[][]=new String[5][8];
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "TIMETABLE";
        Cursor data = db.rawQuery(query, null);
        if(data.moveToFirst()){
            int a=data.getColumnIndex("day");
            int b=data.getColumnIndex("EIGHT");
            int c=data.getColumnIndex("NINE");
            int d=data.getColumnIndex("TEN");
            int e=data.getColumnIndex("ELEVEN");
            int f=data.getColumnIndex("TWELVE");
            int g=data.getColumnIndex("TWO");
            int h=data.getColumnIndex("THREE");
            int i=data.getColumnIndex("FOUR");
            do {int j=data.getInt(a);
                ans[j][0]=data.getString(b);
                ans[j][1]=data.getString(c);
                ans[j][2]=data.getString(d);
                ans[j][3]=data.getString(e);
                ans[j][4]=data.getString(f);
                ans[j][5]=data.getString(g);
                ans[j][6]=data.getString(h);
                ans[j][7]=data.getString(i);
            }while(data.moveToNext());
        }
        return ans;
    }

    public boolean setcourses(String[] a){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE"+" ATTENDENCE");
        db.execSQL(CREATE_TABLE_Attendence);
        int i;
        for(i=0;i<12;i++){
            if(!(a[i].equals(""))) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("COURSE", a[i]);
                contentValues.put("ATTENDED", "0");
                contentValues.put("HAPPENED", "0");
                long result = db.insert("ATTENDENCE", null, contentValues);
                if (result == -1) return false;
            }
        }
        return true;
    }
    public boolean setAttendence(String[][] a){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE"+" ATTENDENCE");
        db.execSQL(CREATE_TABLE_Attendence);
        int i,n=a.length;
        for(i=0;i<n;i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("COURSE",a[i][0]);
            contentValues.put("ATTENDED",a[i][1]);
            contentValues.put("HAPPENED",a[i][2]);
            long result = db.insert("ATTENDENCE" , null, contentValues);
            if (result== -1) return false;
        }
        return true;
    }
    public String[][] outputAttendence(){
        String ans[][]=new String [12][3];
        int i=0;
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT * FROM " + "ATTENDENCE";
        Cursor data = db.rawQuery(query, null);
        if(data.moveToFirst()) {
            int a=data.getColumnIndex("COURSE");
            int b=data.getColumnIndex("ATTENDED");
            int c=data.getColumnIndex("HAPPENED");
            do{
                ans[i][0]=data.getString(a)+"";
                ans[i][1]=data.getString(b)+"";
                ans[i][2]=data.getString(c)+"";
                i++;
            }while(data.moveToNext());
        }
        for(;i<12;i++){
            ans[i][0]="none";
            ans[i][1]="none";
            ans[i][2]="none";
        }
        return ans;
    }
}