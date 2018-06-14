package com.anjan.user06.fetchdatafromcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anjan.user06.fetchdatafromcontentprovider.adapter.StudentsAdapter;
import com.anjan.user06.fetchdatafromcontentprovider.model.Students;

import java.util.ArrayList;

import static com.anjan.user06.fetchdatafromcontentprovider.MainActivity.CONTENT_URI;
import static com.anjan.user06.fetchdatafromcontentprovider.MainActivity.DEPT;
import static com.anjan.user06.fetchdatafromcontentprovider.MainActivity.NAME;
import static com.anjan.user06.fetchdatafromcontentprovider.MainActivity.REG_ID;
import static com.anjan.user06.fetchdatafromcontentprovider.MainActivity._ID;

public class CursorLoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 1;

    private EditText editName;
    private EditText editDept;
    private EditText editRegId;
    private Button btnAdd;
    private Button btnFetch;


    private RecyclerView recyclerView;
    private StudentsAdapter mAdapter;

    ArrayList<Students> studentsList;

    CursorLoader cursorLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_loader);

        editName = findViewById(R.id.editName);
        editDept = findViewById(R.id.textDept);
        editRegId = findViewById(R.id.editReg);
        btnAdd = findViewById(R.id.buttonAdd);
        btnFetch = findViewById(R.id.buttonFetch);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        studentsList = new ArrayList<>();


        /**
         * insert record to database
         */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getEditableText().toString();
                String dept = editDept.getEditableText().toString();
                String regId = editRegId.getEditableText().toString();

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

        /**
         * Fetch all records from database and display
         */
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This work sequence of this class a nutshell:
                // initLoader -> onCreateLoader -> loadInBackground -> deliverResult -> onLoadFinished
                getSupportLoaderManager().initLoader(LOADER_ID, null, CursorLoaderActivity.this).forceLoad();
            }
        });


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                _ID,
                NAME,
                DEPT,
                REG_ID
        };

        String sortOrder = "name";

        cursorLoader = new CursorLoader(this, CONTENT_URI, projection, null, null, sortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        do {
           String name = data.getString(data.getColumnIndex(NAME));
           String regId =  data.getString(data.getColumnIndex(REG_ID));
           String dept = data.getString(data.getColumnIndex(DEPT));

           Students students = new Students();
           students.setName(name);
           students.setDept(dept);
           students.setRegId(regId);
           studentsList.add(students);

        } while (data.moveToNext());


        initRecyclerView(studentsList);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void initRecyclerView(ArrayList<Students> students){

        mAdapter = new StudentsAdapter(students);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
