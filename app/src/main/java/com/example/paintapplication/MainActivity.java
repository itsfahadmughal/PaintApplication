package com.example.paintapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.UUID;

import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton currpaint, drawbtn, erase, save, baru;
    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing);
        baru = findViewById(R.id.new_btn);
        drawbtn = findViewById(R.id.draw_btn);
        erase = findViewById(R.id.erase_btn);
        save = findViewById(R.id.save_btn);
        LinearLayout paintLayout = findViewById(R.id.pain_colors);
        currpaint = (ImageButton) paintLayout.getChildAt(0);

        currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        drawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setupDrawing();
            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setErase(true);
                drawingView.setBrushSize(drawingView.getBrushSize());
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Save Drawing");
                builder.setMessage("Save drawing tp device Gallery ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawingView.setDrawingCacheEnabled(true);
                        String imagsaved = MediaStore.Images.Media.insertImage(getContentResolver(), drawingView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");
                        if (imagsaved != null) {
                            Toast.makeText(MainActivity.this, "Image Saved...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Image Not Saved...", Toast.LENGTH_SHORT).show();
                        }
                        drawingView.destroyDrawingCache();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder newDialog = new AlertDialog.Builder(MainActivity.this);
                newDialog.setTitle("New Drawing");
                newDialog.setMessage("Start New Drawing");
                newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawingView.startNew();
                        dialog.dismiss();
                    }
                });
                newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                newDialog.show();
            }
        });


    }

    public void painClicked(View v) {
        if (v != currpaint) {
            ImageButton imageButton = (ImageButton) v;
            String color = v.getTag().toString();
            drawingView.setColor(color);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currpaint = (ImageButton) v;
        }
    }


}