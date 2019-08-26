package com.ads.agropay.android.ocr_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanImageActivity extends AppCompatActivity {

    public Bitmap bitmapNrcFront;
    SurfaceView cameraView;
    TextView fullNameTextView;
    TextView dobTextView;
    TextView genderTextView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);
        cameraView = findViewById(R.id.surfaceView);

        fullNameTextView = findViewById(R.id.fullNameTextView);
        dobTextView = findViewById(R.id.dobTextView);
        genderTextView = findViewById(R.id.genderTextView);

        getNrcFullName();
        getDob();
        getGender();
    }

    public void getNrcFullName() {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Text Detector Dependencies are not yet available", Toast.LENGTH_SHORT).show();
            return;
        }

        /*Read first name*/
        Bitmap bitmap = BitmapFactory.decodeResource(ScanImageActivity.this.getResources(), R.drawable.img1);
        System.out.println("width:"+bitmap.getWidth()+" height:"+bitmap.getHeight());

        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,2500,13500,1000);
        //storeImage(newBitmap);



        Frame nrcFrontFrame = new Frame.Builder().setBitmap(newBitmap).build();
        SparseArray<TextBlock> nrcBackValues = textRecognizer.detect(nrcFrontFrame);
        if (nrcBackValues.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < nrcBackValues.size(); i++) {
                TextBlock item = nrcBackValues.valueAt(i);
                stringBuilder.append(item.getValue().trim());
                stringBuilder.append("\n");
            }

            fullNameTextView.setText(stringBuilder.toString());
        }
    }

    public void getDob() {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Text Detector Dependencies are not yet available", Toast.LENGTH_SHORT).show();
            return;
        }

        /*Read first name*/
        Bitmap bitmap = BitmapFactory.decodeResource(ScanImageActivity.this.getResources(), R.drawable.img1);
        System.out.println("width:"+bitmap.getWidth()+" height:"+bitmap.getHeight());

        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,3600,4300,1100);
        storeImage(newBitmap);



        Frame nrcFrontFrame = new Frame.Builder().setBitmap(newBitmap).build();
        SparseArray<TextBlock> nrcBackValues = textRecognizer.detect(nrcFrontFrame);
        if (nrcBackValues.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < nrcBackValues.size(); i++) {
                TextBlock item = nrcBackValues.valueAt(i);
                stringBuilder.append(item.getValue().trim());
                stringBuilder.append("\n");
            }

            dobTextView.setText(stringBuilder.toString());
        }
    }

    public void getGender() {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Text Detector Dependencies are not yet available", Toast.LENGTH_SHORT).show();
            return;
        }

        /*Read first name*/
        Bitmap bitmap = BitmapFactory.decodeResource(ScanImageActivity.this.getResources(), R.drawable.img1);
        System.out.println("width:"+bitmap.getWidth()+" height:"+bitmap.getHeight());

        Bitmap newBitmap = Bitmap.createBitmap(bitmap,10500,3600,3500,1100);
        //storeImage(newBitmap);



        Frame nrcFrontFrame = new Frame.Builder().setBitmap(newBitmap).build();
        SparseArray<TextBlock> nrcBackValues = textRecognizer.detect(nrcFrontFrame);
        if (nrcBackValues.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < nrcBackValues.size(); i++) {
                TextBlock item = nrcBackValues.valueAt(i);
                stringBuilder.append(item.getValue().trim());
                stringBuilder.append("\n");
            }

            genderTextView.setText(stringBuilder.toString());
        }
    }

    /*Saves Cropped File*/
    public void storeImage(Bitmap bitmap) {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null){
            Toast.makeText(this,"Error Creating Media File",Toast.LENGTH_SHORT);
            return;
        }
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG,90,fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e){
            Toast.makeText(this,"File not Found",Toast.LENGTH_SHORT).show();
            System.out.println("File not Found: "+ e.getMessage());
        } catch (IOException e){
            Toast.makeText(this,"Error Accessing File",Toast.LENGTH_SHORT).show();
            System.out.println("Error Accessing File: "+e.getMessage());
        }
    }

    /*Creates File That Is to Be Saved*/
    public File getOutputMediaFile() {

        String pathname = "/Android/data/"+getApplicationContext().getPackageName()+"/files";
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + pathname);

        /*Creates Directory if it does not Exist*/
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        /*Sets File Name*/
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String mImageName = "MI_"+ timeStamp +".jpg";/*Sets Image file name and format*/
        File mediaFile = new File(mediaStorageDir.getPath()+File.separator+mImageName);
        return mediaFile;
    }
}
