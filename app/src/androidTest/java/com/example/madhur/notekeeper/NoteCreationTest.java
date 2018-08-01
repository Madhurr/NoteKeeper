package com.example.madhur.notekeeper;

import android.provider.ContactsContract;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matcher.*;
import static org.junit.Assert.*;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.*;

/**
 * Created by madhur on 12/6/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NoteCreationTest {
    static DataManager sDataManager = DataManager.getInstance();
    @Rule
  public ActivityTestRule<NoteListActivity> mNoteListActivityRule
          = new ActivityTestRule<> (NoteListActivity.class);
    @Test
    public  void createNewNote() {
        final CourseInfo course = DataManager.getInstance().getCourse("java_lang");
        final String noteTitle = "Test Note Tittle";
        final String notetext = "This is body of our test note";
        //ViewInteraction fabnewnote = onView(withId(R.id.fab));
        //fabnewnote.perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class),equalTo(course))).perform(click());
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString(course.getTitle())
        )));

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));
        onView(withId(R.id.text_note_text)).perform(typeText(notetext),
                closeSoftKeyboard());
        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(notetext))));
        pressBack();

        int noteIndex = sDataManager.getNotes().size() - 1;
        NoteInfo note = sDataManager.getNotes().get(noteIndex);
        assertEquals(course,note.getCourse());
        assertEquals(notetext,note.getText());
        assertEquals(noteTitle,note.getTitle());
    }
    }