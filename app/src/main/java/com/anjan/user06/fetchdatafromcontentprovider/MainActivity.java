package com.anjan.user06.fetchdatafromcontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnFetch;
    public static final String PROVIDER_NAME = "com.anjan.user06.sqlitedbwithcontentprovider.contentprovider";
    public static final String CONTENT_PATH =  "students";
    public static final String URL = "content://" + PROVIDER_NAME + "/" + CONTENT_PATH;


    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DEPT = "dept";
    public static final String REG_ID = "reg_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFetch = findViewById(R.id.button_fetch);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri students = Uri.parse(URL);
                Cursor c = getContentResolver().query(students, null, null, null, "name");
                String result = "Results:";
                if (!c.moveToFirst()) {
                    Toast.makeText(MainActivity.this, result+" no content yet!", Toast.LENGTH_LONG).show();
                }else {
                    do {
                        result = result + "\n" + c.getString(c.getColumnIndex(NAME)) + " with id " + c.getString(c.getColumnIndex(_ID)) +
                                " has regId: " + c.getString(c.getColumnIndex(REG_ID));
                    } while (c.moveToNext());
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
