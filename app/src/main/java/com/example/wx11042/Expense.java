package com.example.wx11042;

public class Expense {
    public long KEY_ID;
    public String description = "description";
    public String amount = "amount";
    public String category = "category";
    public String date = "date";

    public Expense(long keyid, String description, String amount, String category, String date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return KEY_ID;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(long id) {
        this.KEY_ID = id;
    }
}
