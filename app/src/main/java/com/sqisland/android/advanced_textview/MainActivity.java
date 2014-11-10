package com.sqisland.android.advanced_textview;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView footer = (TextView) LayoutInflater.from(this).inflate(
        android.R.layout.simple_list_item_1, getListView(), false);
    footer.setText(R.string.about);
    getListView().addFooterView(footer);

    final ArrayList<Demo> demos = new ArrayList<Demo>();

    demos.add(new Demo(this, AnimatedCompoundDrawableActivity.class,
        R.string.animated_compound_drawable, R.string.animated_compound_drawable_desc));
    demos.add(new Demo(this, ShadowTextActivity.class,
        R.string.shadow_text, R.string.shadow_text_desc));
    demos.add(new Demo(this, CustomFontActivity.class,
        R.string.custom_font, R.string.custom_font_desc));
    demos.add(new Demo(this, GradientTextActivity.class,
        R.string.gradient_text, R.string.gradient_text_desc));
    demos.add(new Demo(this, PatternedTextActivity.class,
        R.string.patterned_text, R.string.patterned_text_desc));
    demos.add(new Demo(this, FromHtmlActivity.class,
        R.string.from_html, R.string.from_html_desc));

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      demos.add(new Demo(this, FractionActivity.class,
          R.string.fraction, R.string.fraction_desc));
    }

    demos.add(new Demo(this, StyledStringActivity.class,
        R.string.styled_string, R.string.styled_string_desc));
    demos.add(new Demo(this, AlignmentSpanActivity.class,
        R.string.alignment_span, R.string.alignment_span_desc));
    demos.add(new Demo(this, RainbowSpanActivity.class,
        R.string.rainbow_span, R.string.rainbow_span_desc));
    demos.add(new Demo(this, AnimatedRainbowSpanActivity.class,
        R.string.animated_rainbow_span, R.string.animated_rainbow_span_desc));
    demos.add(new Demo(this, ClickableSpanActivity.class,
        R.string.clickable_span, R.string.clickable_span_desc));
    demos.add(new Demo(this, LinedPaperActivity.class,
        R.string.lined_paper, R.string.lined_paper_desc));
    demos.add(new Demo(this, EmojiActivity.class,
        R.string.emoji, R.string.emoji_desc));

    SimpleAdapter adapter = new SimpleAdapter(
        this,
        demos,
        android.R.layout.simple_list_item_2,
        new String[]{Demo.KEY_TITLE, Demo.KEY_SUBTITLE},
        new int[]{android.R.id.text1, android.R.id.text2});
    getListView().setAdapter(adapter);

    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position < demos.size()) {
          Demo demo = demos.get(position);
          startActivity(new Intent(MainActivity.this, demo.activityClass));
        } else {
          startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
      }
    });
  }

  public static class Demo extends HashMap<String, String> {
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";

    public final Class<?> activityClass;

    public Demo(Context context, Class<?> activityClass, int titleId, int subtitleId) {
      this.activityClass = activityClass;

      put(KEY_TITLE, context.getString(titleId));
      put(KEY_SUBTITLE, context.getString(subtitleId));
    }
  }
}