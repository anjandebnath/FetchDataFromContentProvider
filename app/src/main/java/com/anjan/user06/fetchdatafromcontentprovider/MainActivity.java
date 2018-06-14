package com.anjan.user06.fetchdatafromcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnFetch;
    Button btnAdd;
    Button btnSingleFetch;
    Button btnUpdate;
    Button btnDelete;

    TextView txtName;
    TextView txtDept;
    TextView txtRegId;

    String name, dept, regId;

    public static final String PROVIDER_NAME = "com.anjan.user06.sqlitedbwithcontentprovider.contentprovider";
    public static final String CONTENT_PATH =  "students";
    public static final String URL = "content://" + PROVIDER_NAME + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(URL);


    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DEPT = "dept";
    public static final String REG_ID = "reg_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.button_insert);
        btnFetch = findViewById(R.id.button_fetch);
        btnSingleFetch = findViewById(R.id.button_single_fetch);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        txtName = findViewById(R.id.editTextName);
        txtDept = findViewById(R.id.editTextDept);
        txtRegId = findViewById(R.id.editTextRegId);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = txtName.getEditableText().toString();
                dept = txtDept.getEditableText().toString();
                regId = txtRegId.getEditableText().toString();

                ContentValues values = new ContentValues();
                values.put(NAME, name);
                values.put(DEPT, dept);
                values.put(REG_ID, regId);
                Uri uri = getContentResolver().insert(
                        CONTENT_URI, values);
                Toast.makeText(getBaseContext(),
                        "Example: " + uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
            }
        });

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = getContentResolver().query(CONTENT_URI, null, null, null, "name");
                String result = "Results:";
                if (!c.moveToFirst()) {
                    Toast.makeText(MainActivity.this, result+" no content yet!", Toast.LENGTH_LONG).show();
                }else {
                    do {
                        result = result + "\n" + c.getString(c.getColumnIndex(NAME)) +
                                " with id " + c.getString(c.getColumnIndex(_ID)) +
                                " has regId: " + c.getString(c.getColumnIndex(REG_ID)) +
                                " of dept: " + c.getString(c.getColumnIndex(DEPT));
                    } while (c.moveToNext());
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSingleFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txtName.getEditableText().toString();

                String[] projection = {
                        _ID,
                        NAME,
                        DEPT,
                        REG_ID
                };
                String selectionString = NAME + "=?";
                String[] selectionArgs = {name};
                String sortOrder = "name";

                Cursor c = getContentResolver().query(CONTENT_URI, projection, selectionString, selectionArgs, sortOrder);
                String result = "Results:";
                if (!c.moveToFirst()) {
                    Toast.makeText(MainActivity.this, result+" no content yet!", Toast.LENGTH_LONG).show();
                }else {
                    do {
                        result = result + "\n" + c.getString(c.getColumnIndex(NAME)) +
                                " with id " + c.getString(c.getColumnIndex(_ID)) +
                                " has regId: " + c.getString(c.getColumnIndex(REG_ID)) +
                                " of dept: " + c.getString(c.getColumnIndex(DEPT));
                    } while (c.moveToNext());
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dept = txtDept.getEditableText().toString();
                String name = txtName.getEditableText().toString();

                ContentValues values = new ContentValues();
                values.put(NAME, name);
                values.put(DEPT, dept);

                String whereClause = NAME + "=?";
                String[] selectionArgs = {name};

                int UpdateUri = getContentResolver().update(
                        CONTENT_URI, values, whereClause, selectionArgs);

            }
        });
    }
}
