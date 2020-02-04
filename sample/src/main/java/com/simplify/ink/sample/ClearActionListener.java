package com.simplify.ink.sample;

import android.view.View;
import com.simplify.ink.InkView;

public class ClearActionListener implements View.OnClickListener {
  
  private MainActivity mainActivity;
  
  public ClearActionListener(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }
 
  @Override
  public void onClick(View v) {
    mainActivity.getInkView().clear();
  }
}
