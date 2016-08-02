package com.projectsling.fitconduit.ui;

/**
 * A class to be used to hold the states of each row in the WireAdapter
 * */
public class RowState {
    private int mSpinnerPos;
    private String mAmount;
    private boolean mIsRecycled;

    public RowState() {
        mSpinnerPos = 0;
        mAmount = "0";
        mIsRecycled = false;
    }

    public boolean isRecycled() {
        return mIsRecycled;
    }

    public void setRecycled(boolean recycled) {
        mIsRecycled = recycled;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public int getSpinnerPos() {
        return mSpinnerPos;
    }

    public void setSpinnerPos(int spinnerPos) {
        mSpinnerPos = spinnerPos;
    }
}
