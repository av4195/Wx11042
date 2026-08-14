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
    private long expenseId = -1;

    private boolean isEditing = false;

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

        // Check for Intent extras
        if (getIntent().hasExtra("id")) {
            isEditing = true;
            expenseId = getIntent().getLongExtra("id", -1);
            if (expenseId != -1)
                loadExpenseData(expenseId);
        }

        btnAddExpanse.setOnClickListener(v -> addExpanse());
    }

    private void loadExpenseData(long id) {
        Expense expense = ExpansesList.getInstance().getExpenseById(id);
        if (expense != null) {
            etAmount.setText(expense.getAmount());
            etDescription.setText(expense.getDescription());
            etCategory.setText(expense.getCategory());
            etDate.setText(expense.getDate());
            btnAddExpanse.setText("Update Expense");
        }
    }

    private void addExpanse() {
        String amount = etAmount.getText().toString();
        String description = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        String date = etDate.getText().toString();

        if (isEditing) {
            // Update existing expense
            Expense updatedExpense = new Expense(expenseId, description, amount, category, date);
            dbHelper.updateExpanse(updatedExpense);
            finish();
        } else {
            // Create a new Expanse object
            Expense newExpense = new Expense(0, description, amount, category, date);
            // Insert the new Expanse into the database
            long id = dbHelper.addExpanse(newExpense);
            finish();
        }
    }


}