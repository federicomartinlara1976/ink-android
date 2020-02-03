/*
 * Copyright (c) 2016 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplify.ink.sample;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.simplify.ink.InkView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

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
            initSignCanvas();
            initButtons();
            operations.deletePrevImage();
            operations.deletePrevCoordinates();
        } catch (FileOperationException e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    private void initSignCanvas() {
        ink = (InkView) findViewById(R.id.ink);
    }

    private void initButtons() {
        mSignButton = (Button) findViewById(R.id.button_sign);
        mCleanButton = (ImageView) findViewById(R.id.iv_clean);

        /** Button to clean ink panel */
        ClearActionListener clearActionListener = new ClearActionListener(ink);
        mCleanButton.setOnClickListener(clearActionListener);

        /** perform `sign` action */
        mSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    operations.saveImage();
                    operations.saveCoordinates();
                    finish();
                } catch (FileOperationException e) {
                    // Exit program with fatal error
                    Log.e("FATAL", e.getMessage());
                    finishAffinity();
                    System.exit(0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    protected InkView getInkView() {
        return ink;
    }
}
