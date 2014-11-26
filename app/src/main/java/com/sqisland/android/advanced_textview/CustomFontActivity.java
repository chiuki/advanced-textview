package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.util.AttributeSet;

public class CustomFontActivity extends Activity {

  private Typeface typeface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    typeface = Typeface.createFromAsset(getAssets(), "Ruthie.ttf");
    setContentView(R.layout.activity_custom_font);
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    //this would apply to all textviews in the app
    if (name.equals("TextView")) {
      TextView view = new TextView(this, attrs);
      view.setTypeface(typeface);
      return view;
    }

    return super.onCreateView(parent, name, context, attrs);
  }

  @Override
  public View onCreateView(String name, Context context, AttributeSet attrs) {
    //this would apply to all textviews in the app
    if (name.equals("TextView")) {
      TextView view = new TextView(this, attrs);
      view.setTypeface(typeface);
      return view;
    }

    return super.onCreateView(name, context, attrs);
  }

}
