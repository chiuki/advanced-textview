package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class ClickableSpanActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clickable_span);

    TextView textView = (TextView) findViewById(R.id.text);
    String text = textView.getText().toString();

    String goToSettings = getString(R.string.go_to_settings);
    int start = text.indexOf(goToSettings);
    int end = start + goToSettings.length();

    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new GoToSettingsSpan(), start, end, 0);
    textView.setText(spannableString);

    textView.setMovementMethod(new LinkMovementMethod());
  }

  private static class GoToSettingsSpan extends ClickableSpan {
    @Override
    public void onClick(View view) {
      view.getContext().startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
    }
  }
}