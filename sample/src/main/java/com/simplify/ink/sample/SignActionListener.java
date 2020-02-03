package com.simplify.ink.sample;

import android.view.View;

public class SignActionListener implements View.OnClickListener {
  
  private MainActivity mainActivity;
  
  private FileOperations operations;
  
  public ClearActionListener(MainActivity mainActivity, FileOperations operations) {
    this.mainActivity = mainActivity;
    this.operations = operations;
  }
 
  @Override
  public void onClick(View v) {
    
  }
}
