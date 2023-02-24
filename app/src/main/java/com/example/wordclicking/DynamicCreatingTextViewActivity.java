package com.example.wordclicking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DynamicCreatingTextViewActivity extends AppCompatActivity {
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_creating_text_view);

        ll = findViewById(R.id.llMain);
        for (int i = 0; i < 2; i++) {
            TextView textView = new TextView(this);
            textView.setText("I am added dynamically to the view");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(params);
            ll.addView(textView);
        }
    }
}