package com.example.wx11042;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Adi Waizman
 * @version 1.0
 * @since 01/08/2026
 *
 * this class is the main activity of the
 */

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    EditText etDescription;
    EditText etCategory;
    EditText etDate;
    Button btnAddExpanse;
    private HelperDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.editTextDescription);
        etCategory = findViewById(R.id.editTextCatagory);
        etDate = findViewById(R.id.editTextDate);
        btnAddExpanse = findViewById(R.id.button);
        dbHelper = new HelperDB(this);

        btnAddExpanse.setOnClickListener(v -> addExpanse());
    }

    private void addExpanse() {
        String amount = etAmount.getText().toString();
        String description = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        String date = etDate.getText().toString();

        // Create a new Expanse object
        Expense newExpense = new Expense(0, amount, description, category, date);
        // Insert the new Expanse into the database
        long id = dbHelper.addExpanse(newExpense);
    }


}