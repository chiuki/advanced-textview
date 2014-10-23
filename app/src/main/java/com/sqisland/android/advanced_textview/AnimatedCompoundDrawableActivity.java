package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class AnimatedCompoundDrawableActivity extends Activity {
  private TextView textView;

  private enum Operation {
    START, STOP
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animated_compound_drawable);
    textView = (TextView) findViewById(R.id.text);
  }

  @Override
  protected void onStart() {
    super.onStart();
    changeAnimation(Operation.START);
  }

  @Override
  protected void onStop() {
    super.onStop();
    changeAnimation(Operation.STOP);
  }

  private void changeAnimation(Operation operation) {
    Drawable[] drawables = textView.getCompoundDrawables();
    for (Drawable drawable : drawables) {
      if (drawable != null && drawable instanceof Animatable) {
        Animatable animatable = ((Animatable) drawable);
        switch (operation) {
          case START:
            animatable.start();
            break;
          case STOP:
            animatable.stop();
            break;
        }
      }
    }
  }
}