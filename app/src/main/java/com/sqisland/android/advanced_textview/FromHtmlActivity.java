package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class FromHtmlActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_from_html);

    TextView textView = (TextView) findViewById(R.id.text);
    String html = getString(R.string.from_html_text);
    textView.setText(Html.fromHtml(html, new ResouroceImageGetter(this), null));
    textView.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private static class ResouroceImageGetter implements Html.ImageGetter {
    private final Context context;

    public ResouroceImageGetter(Context context) {
      this.context = context;
    }

    @Override
    public Drawable getDrawable(String source) {
      int path = context.getResources().getIdentifier(source, "drawable",
          BuildConfig.APPLICATION_ID);
      Drawable drawable = context.getResources().getDrawable(path);
      drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
          drawable.getIntrinsicHeight());
      return drawable;
    }
  }
}