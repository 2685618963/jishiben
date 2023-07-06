package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.test2.adapter.MyAdapter;
import com.example.test2.bean.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mBtnAdd;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initEvent();

    }
    protected void onResume() {
        super.onResume();
        refreshDataFromDb();

    }
    private void refreshDataFromDb() {
        mNotes = getDataFromDB();
        mMyAdapter.refreshData(mNotes);
    }
    private void initEvent(){
        mMyAdapter=new MyAdapter(this,mNotes);
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
    private void initData(){
        mNotes=new ArrayList<>();
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
       /* for(int i = 0;i < 30;i++) {
            Note note=new Note();
            note.setTitle("biaoti" +i);
            note.setContent("nierong"+i);
            note.setCreatedTime(getCurrentTimeFormat());
            mNotes.add(note);
        }*/
        //mNotes=getDataFromDB();
    }
    private List<Note> getDataFromDB() {
        return mNoteDbOpenHelper.queryAllFromDb();
    }
    private  String getCurrentTimeFormat(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date=new Date();
        return simpleDateFormat.format(date);
    }
    private void initView(){
        mRecyclerView=findViewById(R.id.rlv);
    }
    public void add(View view){
        Intent intent=new Intent(this,add.class);
        startActivity(intent);
    }
}