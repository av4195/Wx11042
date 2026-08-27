package com.example.wx11042;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvExpanses = findViewById(R.id.rvExpanses);
        rvExpanses.setLayoutManager(new LinearLayoutManager(this));

        expansesList = ExpansesList.getInstance();
        expenseList = expansesList.getExpanses();

        dbHelper = new HelperDB(this);
        
        adapter = new ExpenseAdapter(expenseList);
        adapter.setOnItemClickListener(expense -> {
            setupItemAlertDialog(expense);
        });
        rvExpanses.setAdapter(adapter);

        findViewById(R.id.btn_add_expense).setOnClickListener(v -> {
            Intent intent = new Intent(ActivityHome.this, AddExpenseActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Expense> updatedList = dbHelper.getAllExpenses();
        expenseList.clear();
        expenseList.addAll(updatedList);
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
                Intent intent = new Intent(ActivityHome.this, AddExpenseActivity.class);
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