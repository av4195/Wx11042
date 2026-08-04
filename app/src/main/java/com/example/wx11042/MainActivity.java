package com.example.wx11042;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    EditText etDescription;
    EditText etCategory;
    EditText etDate;
    Button btnAddExpanse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.editTextDescription);
        etCategory = findViewById(R.id.editTextCatagory);
        etDate = findViewById(R.id.editTextDate);
        btnAddExpanse = findViewById(R.id.button);

        btnAddExpanse.setOnClickListener(v -> addExpanse());
    }

    private void addExpanse() {
        String amount = etAmount.getText().toString();
        String description = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        String date = etDate.getText().toString();

        // Create a new Expanse object
        Expanse newExpanse = new Expanse(amount, description, category, date);
        // Insert the new Expanse into the database
    }


}