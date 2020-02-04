package com.simplify.ink.sample;

import android.view.View;
import android.util.Log;

public class SignActionListener implements View.OnClickListener {
  
  private MainActivity mainActivity;
  
  private FileOperations operations;
  
  public SignActionListener(MainActivity mainActivity, FileOperations operations) {
    this.mainActivity = mainActivity;
    this.operations = operations;
  }
 
  @Override
  public void onClick(View v) {
    try {
      operations.saveImage();
      operations.saveCoordinates();
    } catch (FileOperationException e) {
      // Fatal error
      Log.e("FATAL", e.getMessage());
    } finally {
      mainActivity.finishAffinity();
      System.exit(0);
    }
  }
}
