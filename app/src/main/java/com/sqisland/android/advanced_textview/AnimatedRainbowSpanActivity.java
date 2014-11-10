package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.util.Property;

public class AnimatedRainbowSpanActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animated_rainbow_span);

    final TextView textView = (TextView) findViewById(R.id.text);
    String text = textView.getText().toString();

    AnimatedColorSpan span = new AnimatedColorSpan(this);

    final SpannableString spannableString = new SpannableString(text);
    String substring = getString(R.string.animated_rainbow_span).toLowerCase();
    int start = text.toLowerCase().indexOf(substring);
    int end = start + substring.length();
    spannableString.setSpan(span, start, end, 0);

    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
        span, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
    objectAnimator.setEvaluator(new FloatEvaluator());
    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        textView.setText(spannableString);
      }
    });
    objectAnimator.setInterpolator(new LinearInterpolator());
    objectAnimator.setDuration(DateUtils.MINUTE_IN_MILLIS * 3);
    objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    objectAnimator.start();
  }

  private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY
      = new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
    @Override
    public void set(AnimatedColorSpan span, Float value) {
      span.setTranslateXPercentage(value);
    }
    @Override
    public Float get(AnimatedColorSpan span) {
      return span.getTranslateXPercentage();
    }
  };

  private static class AnimatedColorSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] colors;
    private Shader shader = null;
    private Matrix matrix = new Matrix();
    private float translateXPercentage = 0;

    public AnimatedColorSpan(Context context) {
      colors = context.getResources().getIntArray(R.array.rainbow);
    }

    public void setTranslateXPercentage(float percentage) {
      translateXPercentage = percentage;
    }

    public float getTranslateXPercentage() {
      return translateXPercentage;
    }

    @Override
    public void updateDrawState(TextPaint paint) {
      paint.setStyle(Paint.Style.FILL);
      float width = paint.getTextSize() * colors.length;
      if (shader == null) {
        shader = new LinearGradient(0, 0, 0, width, colors, null,
            Shader.TileMode.MIRROR);
      }
      matrix.reset();
      matrix.setRotate(90);
      matrix.postTranslate(width * translateXPercentage, 0);
      shader.setLocalMatrix(matrix);
      paint.setShader(shader);
    }
  }
}