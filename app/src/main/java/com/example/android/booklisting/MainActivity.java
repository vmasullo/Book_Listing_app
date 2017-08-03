package com.example.android.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.cerca_libro);
        editText = (EditText) findViewById(R.id.inserire_libro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTitle = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(),Result.class);
                intent.putExtra("titolo",bookTitle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
    }
}
