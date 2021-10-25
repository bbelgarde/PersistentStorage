package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesScreen extends AppCompatActivity {

    TextView welcome;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_screen);

        //1, display welcome message. fetch username fromSharedPreferences.

        welcome = (TextView) findViewById(R.id.Welcome);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        welcome.setText("Welcome " + username);

        //2. get sqlitedatabase instance

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        //3. initiate the "notes" class variable using readNotes method implemented in DBHelper
        //   class, use the username you got from sharedPreferences as a parameter to readNotes method

        notes = sqLiteDatabase

        //4. create and ArrayList<String> object by iterating over notes objects.

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        //5. use ListView view to display notes on screen.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        //6. Add onItemClickListener for ListView item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //initialise intent to take user to third activity (NoteActivity in this case).
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                //add the position of the item that was clicked on as "noteid"
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent = (new Intent(this,MainActivity.class));
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
                startActivity(intent);
                return true;
            case R.id.newNote:
                Intent intent2 = (new Intent(this,ThirdActivity.class));
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}