package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

public class StyledStringActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_styled_string);

    SpannableStringBuilder builder = new SpannableStringBuilder()
        .append(formatString(this, R.string.big_red, R.style.BigRedTextAppearance))
        .append("\n")
        .append(formatString(this, R.string.medium_green, R.style.MediumGreenTextAppearance))
        .append("\n")
        .append(formatString(this, R.string.small_blue, R.style.SmallBlueTextAppearance));
    CharSequence styledString = builder.subSequence(0, builder.length());

    TextView textView = (TextView) findViewById(R.id.text);
    textView.setText(styledString);
  }

  private static SpannableString formatString(Context context, int textId, int style) {
    String text = context.getString(textId);
    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(), 0);
    return spannableString;
  }
}