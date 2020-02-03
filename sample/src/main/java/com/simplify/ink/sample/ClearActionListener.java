package com.simplify.ink.sample;

import android.view.View;
import com.simplify.ink.InkView;

public class ClearActionListener implements View.OnClickListener {
  
  private InkView ink;
  
  public ClearActionListener(InkView ink) {
    this.ink = ink;
  }
 
  @Override
  public void onClick(View v) {
    ink.clear();
  }
}
