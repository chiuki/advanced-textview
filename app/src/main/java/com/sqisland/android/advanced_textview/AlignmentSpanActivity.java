package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;

public class AlignmentSpanActivity extends Activity {
  private ScrollView scroll;
  private TextView textView;
  private TextView input;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alignment_span);

    scroll = (ScrollView) findViewById(R.id.scroll);
    textView = (TextView) findViewById(R.id.text);
    input = (TextView) findViewById(R.id.input);

    for (int id : new int[] { R.id.add_to_left_button, R.id.add_to_right_button }) {
      View button = findViewById(id);
      button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          // input.getText() returns a CharSequence, which contains formatting such as underline
          // when the text contains a typo. Use toString() to get the plain text.
          String text = input.getText().toString();
          appendText(text, view.getId() == R.id.add_to_right_button ?
              Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL);

          hideSoftKeyboard();
          input.setText(null);
          scroll.fullScroll(View.FOCUS_DOWN);
        }
      });
    }

    appendText(getString(R.string.alignment_span_line_1), Layout.Alignment.ALIGN_NORMAL);
    appendText(getString(R.string.alignment_span_line_2), Layout.Alignment.ALIGN_OPPOSITE);
  }

  private void appendText(CharSequence text, Layout.Alignment align) {
    if (text == null || text.toString().trim().length() == 0) {
      return;
    }

    AlignmentSpan span = new AlignmentSpan.Standard(align);
    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(span, 0, text.length(), 0);

    if (textView.length() > 0) {
      textView.append("\n\n");
    }
    textView.append(spannableString);
  }

  private void hideSoftKeyboard() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
  }
}