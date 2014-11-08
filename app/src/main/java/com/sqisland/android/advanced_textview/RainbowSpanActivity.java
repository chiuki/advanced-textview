package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UpdateAppearance;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainbowSpanActivity extends Activity {
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rainbow_span);

    textView = (TextView) findViewById(R.id.text);
    TextView input = (TextView) findViewById(R.id.input);

    input.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        highlight(s.toString());
      }
    });

    highlight(input.getText().toString());
  }

  private void highlight(String query) {
    String text = textView.getText().toString();
    SpannableString spannableString = new SpannableString(text);

    Pattern pattern = Pattern.compile(query.toLowerCase());
    Matcher matcher = pattern.matcher(text.toLowerCase());
    while (matcher.find()) {
      spannableString.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(), matcher.end(), 0);
      spannableString.setSpan(new RainbowSpan(this), matcher.start(), matcher.end(), 0);
    }

    textView.setText(spannableString);
  }

  private static class RainbowSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] colors;

    public RainbowSpan(Context context) {
      colors = context.getResources().getIntArray(R.array.rainbow);
    }

    @Override
    public void updateDrawState(TextPaint paint) {
      paint.setStyle(Paint.Style.FILL);
      Shader shader = new LinearGradient(0, 0, 0, paint.getTextSize() * colors.length, colors, null,
          Shader.TileMode.MIRROR);
      Matrix matrix = new Matrix();
      matrix.setRotate(90);
      shader.setLocalMatrix(matrix);
      paint.setShader(shader);
    }
  }
}