package net.rjbatista.retryview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ricardo on 19/09/16.
 */
public class RetryView extends ViewGroup {

    private TextView textView;
    private String messageText;
    private int messageTextColor;
    private int messageBackgroundColor;

    private Button button;
    private String buttonText;
    private int buttonTextColor;
    private int buttonBackgroundColor;

    private RetryViewListener listener;

    private boolean showText;
    private int textPos;

    final int MESSAGE_PADDING = 50;
    final int BUTTON_HEIGHT = 135;

    public RetryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RetryView,
                0, 0);

        try {
            messageText = typedArray.getString(R.styleable.RetryView_messageText);
            messageTextColor = typedArray.getColor(R.styleable.RetryView_messageTextColor, Color.BLACK);
            messageBackgroundColor = typedArray.getColor(R.styleable.RetryView_messageBackgroundColor, Color.WHITE);

            buttonText = typedArray.getString(R.styleable.RetryView_buttonText);
            buttonTextColor = typedArray.getColor(R.styleable.RetryView_buttonTextColor, Color.BLACK);
            buttonBackgroundColor = typedArray.getColor(R.styleable.RetryView_buttonBackgroundColor, Color.WHITE);

            showText = typedArray.getBoolean(R.styleable.RetryView_showText, false);
            textPos = typedArray.getInteger(R.styleable.RetryView_labelPosition, 0);
        } finally {
            typedArray.recycle();
        }

        textView = new TextView(context);
        textView.setText(messageText);
        textView.setPadding(MESSAGE_PADDING, MESSAGE_PADDING, MESSAGE_PADDING, MESSAGE_PADDING);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setTextColor(messageTextColor);
        textView.setBackgroundColor(messageBackgroundColor);
        textView.setGravity(Gravity.CENTER);
        addView(textView);

        button = new Button(context);
        button.setText(buttonText);
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setTextColor(buttonTextColor);
        button.setBackgroundColor(buttonBackgroundColor);
        addView(button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRetryButtonClicked();
                }
            }
        });

        setVisibility(View.GONE);
    }

    public void setListener(RetryViewListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i("RMB", "onLayout " + left + "," + top + "," + right + "," + bottom);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getWidth() - MESSAGE_PADDING, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        final int MESSAGE_HEIGHT = textView.getMeasuredHeight();

        Log.i("RMB", "measure height " + MESSAGE_HEIGHT);

        textView.layout(0, bottom/2 - MESSAGE_HEIGHT, right, bottom/2);
        button.layout(0, bottom/2, right, bottom/2 + BUTTON_HEIGHT);
    }

    // Getters and Setters

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
        invalidate();
        requestLayout();
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        invalidate();
        requestLayout();
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        invalidate();
        requestLayout();
    }

    // Size
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("RMB", "onSizeChanged " + w + "," + h);

        // padding
        float xPadding = (float)(getPaddingLeft() + getPaddingRight());
        float yPadding = (float)(getPaddingTop() + getPaddingBottom());

        Log.i("RMB", "padding " + xPadding + "," + yPadding);

    }

}
