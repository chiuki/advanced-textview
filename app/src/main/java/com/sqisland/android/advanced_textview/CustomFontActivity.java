package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class CustomFontActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_font);

    TextView textView = (TextView) findViewById(R.id.text);
    Typeface typeface = Typeface.createFromAsset(getAssets(), "Ruthie.ttf");
    textView.setTypeface(typeface);
  }
}