package com.cortasys.note.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cortasys.note.R;

import static android.nfc.NfcAdapter.EXTRA_ID;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.cortasys.note.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.cortasys.note.EXTRA_TITLE";
    public static final String EXTRA_SH_DESC = "com.cortasys.note.EXTRA_SH_DESC";
    public static final String EXTRA_DESC = "com.cortasys.note.EXTRA_DESC";


    EditText edTitle, edShDesc, edDesc;
    Button btnSave;

    String title, shDesc, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent intent = getIntent();


        edTitle = findViewById(R.id.edTitle);
        edShDesc = findViewById(R.id.edShDesc);
        edDesc = findViewById(R.id.edDesc);

        btnSave = findViewById(R.id.btnSave);

        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            String title = intent.getStringExtra(EXTRA_TITLE);
            String shDesc = intent.getStringExtra(EXTRA_SH_DESC);
            String desc = intent.getStringExtra(EXTRA_DESC);

            edTitle.setText(title);
            edShDesc.setText(shDesc);
            edDesc.setText(desc);

        } else setTitle("Add Note");


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

    }

    private void addNote() {
        title = edTitle.getText().toString();
        shDesc = edShDesc.getText().toString();
        desc = edDesc.getText().toString();

        if (title.trim().isEmpty() || shDesc.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_SH_DESC, shDesc);
        intent.putExtra(EXTRA_DESC, desc);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
