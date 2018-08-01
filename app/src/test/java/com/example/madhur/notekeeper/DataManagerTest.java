package com.example.madhur.notekeeper;

import android.provider.ContactsContract;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by madhur on 5/6/18.
 */
public class DataManagerTest {
    @Test
    public void createNewNote() throws Exception {
        DataManager instance = DataManager.getInstance();
        final CourseInfo course = instance.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is my new note and i can write with or with out seeing";
        int noteIndex = instance.createNewNote();
        NoteInfo newNote = instance.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);
        NoteInfo comparenote = instance.getNotes().get(noteIndex);
        assertEquals(comparenote.getCourse(), course);
        assertEquals(comparenote.getTitle(), noteTitle);
        assertEquals(comparenote.getText(), noteText);
    }

    @Test
    public void findSimilarNotes() throws Exception {
        DataManager dm = DataManager.getInstance();
        final CourseInfo course = dm.getCourse("android_async");
        final String noteTitle = "TestNoteTitle";
        final String noteText1 = "Hello Testing 1";
        final String noteText2 = "Hello Testing 2" ;

        int noteIndex1 = dm.createNewNote();
        NoteInfo newNote1 = dm.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setText(noteText1);
        newNote1.setTitle(noteTitle);

        int noteindex2 = dm.createNewNote();
        NoteInfo newNote2 = dm.getNotes().get(noteindex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundindex = dm.findNote(newNote1);
        assertEquals(noteIndex1,foundindex);

        int foundindex2 = dm.findNote(newNote2);
        assertEquals(noteindex2,foundindex2);
    }

}