package com.simplify.ink.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.simplify.ink.InkView;

public class MainActivity extends AppCompatActivity {

    /** Panel for physic signature. */
    private InkView ink;

    /** Button to save sign picture */
    private Button mSignButton;

    private ImageView mCleanButton;

    private FileOperations operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operations = new FileOperations(this);
        
        try {
            initComponents();
            
            // Deletes previous image and data coordinates (if any) 
            operations.deletePrevImage();
            operations.deletePrevCoordinates();
        } catch (FileOperationException e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    private void initComponents() {
        ink = (InkView) findViewById(R.id.ink);
        mSignButton = (Button) findViewById(R.id.button_sign);
        mCleanButton = (ImageView) findViewById(R.id.iv_clean);

        /** Button to clean ink panel */
        ClearActionListener clearActionListener = new ClearActionListener(this);
        mCleanButton.setOnClickListener(clearActionListener);

        /** perform `sign` action */
        SignActionListener signActionListener = new SignActionListener(this, operations);
        mSignButton.setOnClickListener(signActionListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    protected InkView getInkView() {
        return ink;
    }
}
