package com.example.madhur.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private NoteRecyclerAdapter noteadapter;

//    private ArrayAdapter<NoteInfo> adapterNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  adapterNotes.notifyDataSetChanged();
        noteadapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
    final RecyclerView recyclernotes = (RecyclerView) findViewById(R.id.list_notes);
    final LinearLayoutManager listlayoutmanager = new LinearLayoutManager(this);
    recyclernotes.setLayoutManager(listlayoutmanager);
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        noteadapter = new NoteRecyclerAdapter(this,notes);
        recyclernotes.setAdapter(noteadapter);

    }
}