package com.ads.agropay.android.ocr_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;

public class ScanImageActivity extends AppCompatActivity {

    public Bitmap bitmapNrcFront;
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);
        cameraView = findViewById(R.id.surfaceView);
        textView = findViewById(R.id.textView);

        runNrcTextOcrProcessor();
    }

    public void runNrcTextOcrProcessor() {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Text Detector Dependencies are not yet available", Toast.LENGTH_SHORT).show();
            return;
        }


        /*Front Of NRC*/
        Bitmap bitmap = BitmapFactory.decodeResource(ScanImageActivity.this.getResources(), R.drawable.img1);
        System.out.println(" height:"+bitmap.getWidth()+" width:"+bitmap.getHeight());

        Bitmap newBitmap=Bitmap.createBitmap(bitmap,0,1500,7000,1000);


        Frame nrcFrontFrame = new Frame.Builder().setBitmap(newBitmap).build();
        SparseArray<TextBlock> nrcBackValues = textRecognizer.detect(nrcFrontFrame);
        if (nrcBackValues.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < nrcBackValues.size(); i++) {
                TextBlock item = nrcBackValues.valueAt(i);
                stringBuilder.append(item.getValue().trim());
                stringBuilder.append("\n");
            }

            textView.setText(stringBuilder.toString());
        }
    }
}
