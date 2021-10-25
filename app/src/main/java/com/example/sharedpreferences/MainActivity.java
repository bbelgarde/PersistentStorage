package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String usernameKey = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")){
            String username = sharedPreferences.getString(usernameKey,"");
            goToNotes(username);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void clickFunction(View view){
        EditText myUsername = (EditText) findViewById(R.id.Username);
        EditText myPassword = (EditText) findViewById(R.id.Password);
        String strUsername = myUsername.getText().toString();
        String strPassword = myPassword.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", strUsername).apply();
        goToNotes(strUsername);
    }

    public void goToNotes(String s){
        Intent intent = new Intent(this,NotesScreen.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }
}