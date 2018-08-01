package com.example.madhur.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION = "com.example.madhur.notekeeper.NOTE_POSITION";
    public static final String ORIGINAL_NOTE_COURSE_ID =  "com.example.madhur.notekeeper.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.example.madhur.notekeeper.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.example.madhur.notekeeper.ORIGINAL_NOTE_TEXT";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo note;
    private boolean isNewNote;
    private Spinner spinnerCourses;
    private EditText textNoteTitle;
    private EditText textNoteText;
    private int noteposition;
    private boolean iscancling;
    private String originalNoteCourseID;
    private String originalNoteCourseTitle;
    private String originalNoteCourseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerCourses = (Spinner) findViewById(R.id.spinner_courses);
        List<CourseInfo> courses;
        courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapterCourses);
        readDisplayStateValue();
        if(savedInstanceState == null) {
            saveOriginalNoteValue();
        }
        else {
            restoreOriginalNoteValue(savedInstanceState);
        }
        textNoteTitle = (EditText) findViewById(R.id.text_note_title);
        textNoteText = (EditText) findViewById(R.id.text_note_text);

        if(!isNewNote )
        displayNote(spinnerCourses, textNoteTitle, textNoteText);

    }

    private void restoreOriginalNoteValue(Bundle savedInstanceState) {
        originalNoteCourseID = savedInstanceState.getString(originalNoteCourseID);
        originalNoteCourseText = savedInstanceState.getString(originalNoteCourseText);
        originalNoteCourseTitle = savedInstanceState.getString(originalNoteCourseTitle);
    }

    private void saveOriginalNoteValue() {
        if(isNewNote)
            return;
        originalNoteCourseID = note.getCourse().getCourseId();
        originalNoteCourseID = note.getText();
        originalNoteCourseID = note.getTitle();

    }



    @Override
    protected void onPause() {
        super.onPause();
        if(iscancling){
            if(isNewNote) {
                DataManager.getInstance().removeNote(noteposition);
            }
            else{
                storePreviousnotevalues();
            }
        }else {
            saveNote();
        }

    }

    private void storePreviousnotevalues() {
        CourseInfo course = DataManager.getInstance().getCourse(originalNoteCourseID);
        note.setCourse(course);
        note.setText(originalNoteCourseText);
        note.setTitle(originalNoteCourseTitle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORIGINAL_NOTE_COURSE_ID , originalNoteCourseID);
        outState.putString(ORIGINAL_NOTE_TEXT , originalNoteCourseText);
        outState.putString(ORIGINAL_NOTE_TITLE, originalNoteCourseTitle);
    }

    private void saveNote() {
        note.setCourse((CourseInfo) spinnerCourses.getSelectedItem());
        note.setTitle(textNoteTitle.getText().toString());
        note.setText(textNoteText.getText().toString());
    }

    private void displayNote(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(note.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteText.setText(note.getText());
        textNoteTitle.setText(note.getTitle());
    }

    private void readDisplayStateValue() {
        Intent intent = getIntent();
        int position  = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
         isNewNote = position == POSITION_NOT_SET;
        if(isNewNote){
         createNewNote();
        }
        else {
            note = DataManager.getInstance().getNotes().get(position);
        }
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        noteposition = dm.createNewNote();
        note = dm.getNotes().get(noteposition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        }
        else if(id == R.id.action_cancel ){
            iscancling = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) spinnerCourses.getSelectedItem();
        String subject = textNoteTitle.getText().toString();
        String text = "check out what i learned at pluralsite course \" " +
                course.getTitle() + "\"\n" + textNoteText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(intent);
    }
}
