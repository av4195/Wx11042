package com.example.wx11042;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Adi Waizman
 * @version 1.0
 * @since 01/08/2026
 *
 * this class is the main activity of the
 */

public class AddExpenseActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_expense);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.editTextDescription);
        etCategory = findViewById(R.id.editTextCatagory);
        etDate = findViewById(R.id.editTextDate);
        btnAddExpanse = findViewById(R.id.button);
        dbHelper = new HelperDB(this);

        setupDatePicker();

        // Check for Intent extras
        if (getIntent().hasExtra("id")) {
            isEditing = true;
            expenseId = getIntent().getLongExtra("id", -1);
            if (expenseId != -1)
                loadExpenseData(expenseId);
        }

        btnAddExpanse.setOnClickListener(v -> addExpanse());
    }

    private void setupDatePicker() {
        etDate.setOnClickListener(v -> {
            String currentDate = etDate.getText().toString();
            long selection = MaterialDatePicker.todayInUtcMilliseconds();
            if (!currentDate.isEmpty()) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    selection = format.parse(currentDate).getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(selection)
                    .build();

            datePicker.addOnPositiveButtonClickListener(sel -> {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(sel);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedDate = format.format(calendar.getTime());
                etDate.setText(formattedDate);
            });

            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });
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
//
        if (isEditing) {
            // Update existing expense
            Expense updatedExpense = new Expense(expenseId, description, amount, category, date);
            dbHelper.updateExpanse(updatedExpense);
            finish();
        }
        else {
            // Create a new Expanse object
            Expense newExpense = new Expense(0, description, amount, category, date);
            // Insert the new Expanse into the database
            long id = dbHelper.addExpanse(newExpense);
            Intent intent = new Intent(this, ActivityHome.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (MenuNavigation.NavigateOnItemSelected(item, this))
            return true;
        return super.onOptionsItemSelected(item);
    }
}

