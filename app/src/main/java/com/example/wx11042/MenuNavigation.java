package com.example.wx11042;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

public class MenuNavigation{
    public static boolean NavigateOnItemSelected(MenuItem item, Context context)
    {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            Intent intent = new Intent(context, ActivityHome.class);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.menu_add) {
            Intent intent = new Intent(context, AddExpenseActivity.class);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.menu_search) {
            Intent intent = new Intent(context, ActivitySearch.class);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.menu_credits) {
            Intent intent = new Intent(context, CreditsActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
