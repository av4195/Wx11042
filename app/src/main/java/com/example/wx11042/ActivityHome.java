package com.example.wx11042;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
    private ExpenseAdapter adapter;
    private List<Expense> expenseList;
    private AlertDialog.Builder adb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvExpanses = findViewById(R.id.rvExpanses);
        rvExpanses.setLayoutManager(new LinearLayoutManager(this));

        expenseList = new ArrayList<>();
        //Read data from database
        // Add some dummy data using Expense class
        expenseList.add(new Expense(1L, "Grocery shopping", "50.00", "Food", "2026-08-10"));
        expenseList.add(new Expense(2L, "Gas station", "40.00", "Transport", "2026-08-09"));
        expenseList.add(new Expense(3L, "Netflix subscription", "15.00", "Entertainment", "2026-08-01"));
        
        adapter = new ExpenseAdapter(expenseList);
        adapter.setOnItemClickListener(expense -> {
            adb = new AlertDialog.Builder(this);
            adb.setTitle("What do you want to do?");

            adb.setPositiveButton("Edit" , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            adb.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position;
                    position = expenseList.indexOf(expense);
                    expenseList.remove(expense);
                    //expenseList.remove(expense);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, expenseList.size());
                    Toast.makeText(ActivityHome.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog ad = adb.create();
            ad.show();

        });
        rvExpanses.setAdapter(adapter);
    }
}