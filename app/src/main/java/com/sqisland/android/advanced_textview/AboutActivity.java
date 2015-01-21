package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.List;

public class AboutActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);

    TextView textView = (TextView) findViewById(R.id.text);
    CharSequence html = Html.fromHtml(getString(R.string.about_text));

    if (canOpenLinks()) {
      textView.setText(html);
      textView.setMovementMethod(LinkMovementMethod.getInstance());
    } else {
      textView.setText(html.toString());
    }
  }

  public boolean canOpenLinks() {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://d.android.com"));
    final PackageManager packageManager = getPackageManager();
    List<ResolveInfo> list = packageManager.queryIntentActivities(
        intent, PackageManager.MATCH_DEFAULT_ONLY);
    return list.size() > 0;
  }
}