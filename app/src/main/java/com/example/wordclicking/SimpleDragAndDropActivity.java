package com.example.wordclicking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleDragAndDropActivity extends AppCompatActivity {
    TextView theView, theView2;
    LinearLayout llTop;
    CardView llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_drag_and_drop);

        theView = findViewById(R.id.theText);
        theView2 = findViewById(R.id.theText2);
        llTop = findViewById(R.id.llTop);
        llBottom = findViewById(R.id.llBottom);

        llTop.setOnDragListener(new MyDragListener());
        llBottom.setOnDragListener(new MyDragListener());

        theView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("mytag", "On long click");
                String clipText = "This is out ClipData text";
                ClipData.Item item = new ClipData.Item(clipText);
                String[] mimeType = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(clipText, mimeType, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(data, dragShadowBuilder, v, 0);

                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        theView2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("mytag", "On long click");
                String clipText = "This is out ClipData text";
                ClipData.Item item = new ClipData.Item(clipText);
                String[] mimeType = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(clipText, mimeType, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(data, dragShadowBuilder, v, 0);

                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });
    }
}