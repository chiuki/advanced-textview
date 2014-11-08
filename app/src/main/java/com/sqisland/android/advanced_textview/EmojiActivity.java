package com.sqisland.android.advanced_textview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MetricAffectingSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_emoji);

    final TextView textView = (TextView) findViewById(R.id.text);
    findViewById(R.id.render_emoji_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        renderEmoji(textView);
        view.setEnabled(false);
      }
    });
  }

  private void renderEmoji(TextView textView) {
    String text = textView.getText().toString();
    SpannableString spannableString = new SpannableString(text);

    // Icon font
    Pattern pattern = Pattern.compile("\u26F7");    // skier
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
          getResources().getColor(R.color.blue));
      IconFontSpan iconFontSpan = new IconFontSpan(textView.getContext());
      spannableString.setSpan(iconFontSpan, matcher.start(), matcher.end(), 0);
      spannableString.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), 0);
    }

    // ImageSpan from resource
    pattern = Pattern.compile(":octopus:");
    matcher = pattern.matcher(text);

    Bitmap octopus = null;
    int size = (int) (-textView.getPaint().ascent());
    while (matcher.find()) {
      if (octopus == null) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.octopus);
        octopus = Bitmap.createScaledBitmap(bitmap, size, size, true);
        bitmap.recycle();
      }
      ImageSpan span = new ImageSpan(this, octopus, ImageSpan.ALIGN_BASELINE);
      spannableString.setSpan(span, matcher.start(), matcher.end(), 0);
    }

    // ImageSpan with dynamic drawable
    pattern = Pattern.compile(":speed_(\\d\\d\\d?):");
    matcher = pattern.matcher(text);

    while (matcher.find()) {
      SpeedSignDrawable drawable = new SpeedSignDrawable(textView, matcher.group(1));
      ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
      spannableString.setSpan(span, matcher.start(), matcher.end(), 0);
    }

    textView.setText(spannableString);
  }

  private static class IconFontSpan extends MetricAffectingSpan {
    private static Typeface typeface = null;

    public IconFontSpan(Context context) {
      if (typeface == null) {
        typeface = Typeface.createFromAsset(
            context.getAssets(), "icomoon.ttf");
      }
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
       textPaint.setTypeface(typeface);
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
      textPaint.setTypeface(typeface);
    }
  }

  private static class SpeedSignDrawable extends Drawable {
    private final float ascent;
    private final float descent;
    private final float textSize;
    private final float strokeWidth;
    private final String number;

    public SpeedSignDrawable(TextView textView, String number) {
      this.ascent = textView.getPaint().ascent();
      this.descent = textView.getPaint().descent();
      this.textSize = textView.getTextSize();
      this.strokeWidth = textView.getPaint().getStrokeWidth();

      this.number = number;

      int size = (int) -ascent;
      this.setBounds(0, 0, size, size);
    }

    @Override
    public void draw(Canvas canvas) {
      int size = (int) -ascent;

      Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

      // Draw the circle
      paint.setStyle(Paint.Style.FILL);
      paint.setColor(Color.YELLOW);
      canvas.drawCircle(size/2, size/2, size/2 , paint);

      // Draw the ring
      paint.setStyle(Paint.Style.STROKE);
      paint.setColor(Color.RED);
      float ringWidth = size / 10;
      paint.setStrokeWidth(ringWidth);
      canvas.drawCircle(size/2, size/2, size/2 - ringWidth/2, paint);

      // Draw the text
      float ratio = 0.4f;
      paint.setStyle(Paint.Style.FILL);
      paint.setColor(Color.BLACK);
      paint.setTextAlign(Paint.Align.CENTER);
      paint.setTextSize(textSize * ratio);
      paint.setStrokeWidth(strokeWidth);
      paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      int x = size / 2;
      int y = (int) (size/2 - ((descent + ascent)/2) * ratio);
      canvas.drawText(number, x, y, paint);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
      return 0;
    }
  }
}