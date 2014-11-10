package com.sqisland.android.advanced_textview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.widget.TextView;

import org.xml.sax.XMLReader;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class FractionActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fraction);

    TextView textView = (TextView) findViewById(R.id.text);
    Typeface typeface = Typeface.createFromAsset(getAssets(), "Nutso2.otf");
    textView.setTypeface(typeface);

    String html = getString(R.string.fraction_text);
    textView.setText(Html.fromHtml(html, null, new FractionTagHandler()));
  }

  // http://stackoverflow.com/questions/4044509/android-how-to-use-the-html-taghandler
  private static class FractionTagHandler implements Html.TagHandler {
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
      if (!"afrc".equalsIgnoreCase(tag)) {
        return;
      }

      int len = output.length();
      if (opening) {
        output.setSpan(new FractionSpan(), len, len, Spannable.SPAN_MARK_MARK);
      } else {
        Object obj = getLast(output, FractionSpan.class);
        int where = output.getSpanStart(obj);

        output.removeSpan(obj);

        if (where != len) {
          output.setSpan(new FractionSpan(), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
      }
    }

    private Object getLast(Editable text, Class kind) {
      Object[] objs = text.getSpans(0, text.length(), kind);

      if (objs.length == 0) {
        return null;
      } else {
        for (int i = objs.length - 1; i >= 0; --i) {
          if(text.getSpanFlags(objs[i]) == Spannable.SPAN_MARK_MARK) {
            return objs[i];
          }
        }
        return null;
      }
    }
  }

  private static class FractionSpan extends MetricAffectingSpan {
    private static final String FONT_FEATURE_SETTINGS = "afrc";

    @Override
    public void updateMeasureState(TextPaint textPaint) {
      textPaint.setFontFeatureSettings(FONT_FEATURE_SETTINGS);
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
      textPaint.setFontFeatureSettings(FONT_FEATURE_SETTINGS);
    }
  }
}