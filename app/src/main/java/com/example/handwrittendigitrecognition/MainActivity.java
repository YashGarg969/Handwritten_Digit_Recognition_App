package com.example.handwrittendigitrecognition;

import static com.example.handwrittendigitrecognition.PaintView.colorList;
import static com.example.handwrittendigitrecognition.PaintView.pathList;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.Toast;

import com.example.handwrittendigitrecognition.ml.ModelUnquant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {
    ImageButton blackButton,redButton,eraseButton;
    public static Path path;
    public static Paint paintbrush;
    PaintView paintView;
    TextView textView;
    FloatingActionButton predictImage;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        blackButton=findViewById(R.id.blackColor);
        redButton=findViewById(R.id.redColor);
        eraseButton=findViewById(R.id.clear);
        textView=findViewById(R.id.getPrediction);
        predictImage=findViewById(R.id.PredictImage);
        paintView=findViewById(R.id.paintView);
        paintbrush=new Paint();
        blackButton.setOnClickListener(View->{
            paintbrush.setColor(Color.BLACK);
            setCurrentColor(paintbrush.getColor());});
        redButton.setOnClickListener(View->{
            paintbrush.setColor(Color.RED);
            setCurrentColor(paintbrush.getColor());});
        eraseButton.setOnClickListener(View->{
            Toast.makeText(MainActivity.this,"Canvas Is Cleared",Toast.LENGTH_SHORT).show();
            pathList.clear();
            colorList.clear();
            });
        predictImage.setOnClickListener(View->{
            try {
                Bitmap bitmap = paintView.getBitmapFromView();
                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false);
                PredictImage(bitmap);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setCurrentColor(int color) {
        PaintView.currColor = color;
        path = new Path();
    }
    private void PredictImage(Bitmap bitmap)
    {


        try {
            // Creates inputs for reference.
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int[] intValue = new int[224 * 224];
            bitmap.getPixels(intValue, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            int pixel = 0;
            for (int i = 0; i < 224; i++) {
                for (int j = 0; j < 224; j++) {
                    int val = intValue[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidence = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }
            String[] classes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            textView.setText("The drawn digit appears to be: "+classes[maxPos]);
            // Releases model resources if no longer used.
            model.close();
        }catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }



    }
}