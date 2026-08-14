package com.example.wx11042;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ActivityHome extends AppCompatActivity {

    private RecyclerView rvExpanses;
    ExpenseAdapter adapter;
    private List<Expense> expenseList;
    AlertDialog.Builder adb;
    AlertDialog.Builder adb2;
    LinearLayout myDialog;
    EditText etd, eta, etc, etDate;

    private ExpansesList expansesList;
    private HelperDB dbHelper;


    /**
     *this method is for
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rvExpanses = findViewById(R.id.rvExpanses);
        rvExpanses.setLayoutManager(new LinearLayoutManager(this));

        expansesList = ExpansesList.getInstance();
        expenseList = expansesList.getExpanses();

        dbHelper = new HelperDB(this);
//        if (expenseList.isEmpty()) {
//            // Add some dummy data using Expense class if list is empty
//            expenseList.add(new Expense(1L, "Grocery shopping", "50.00", "Food", "2026-08-10"));
//            expenseList.add(new Expense(2L, "Gas station", "40.00", "Transport", "2026-08-09"));
//            expenseList.add(new Expense(3L, "Netflix subscription", "15.00", "Entertainment", "2026-08-01"));
//        }
        expansesList.getExpanses().clear();
        expansesList.getExpanses().addAll(dbHelper.getAllExpenses());
        
        adapter = new ExpenseAdapter(expenseList);
        adapter.setOnItemClickListener(expense -> {
            setupItemAlertDialog(expense);
        });
        rvExpanses.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void setupItemAlertDialog(Expense expense)
    {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("What do you want to do?");

        adb.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ActivityHome.this, MainActivity.class);
                intent.putExtra("id", expense.getId());
                startActivity(intent);
            }
        });

        adb.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = expenseList.indexOf(expense);
                expenseList.remove(expense);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, expenseList.size());
                dbHelper.deleteExpanse(expense.getId());
                Toast.makeText(ActivityHome.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog ad1 = adb.create();
        ad1.show();
    }
}