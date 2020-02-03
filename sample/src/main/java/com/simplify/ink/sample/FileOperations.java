package com.simplify.ink.sample;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.simplify.ink.InkView;
import com.simplify.ink.InkPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileOperations {

    private MainActivity mainActivity;

    public FileOperations(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * save ink signature bitmap to internal storage
     */
    public void saveImage() throws FileOperationException {
        FileOutputStream out = null;
        try {
            String dataDir = getDataDir();
            out = new FileOutputStream(dataDir + "/image.png");

            InkView ink = mainActivity.getInkView();
            // Antes de guardar, pone el fondo transparente
            ink.setBackgroundColor(Color.TRANSPARENT);
            ink.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (FileNotFoundException e) {
            throw new FileOperationException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e("FATAL", e.getMessage());
            }
        }
    }
    
    /**
     * save ink signature coordinates to internal storage
     */
    public void saveCoordinates() throws FileOperationException {
        PrintWriter pw = null;
        try {
            String dataDir = getDataDir();
            pw = new PrintWriter(new File(dataDir + "/coordinates.txt"));
            
            InkView ink = mainActivity.getInkView();
            // Write the coordinates
            for (InkPoint inkPoint : ink.getPoints()) {
                // TODO - Write the point line (x y x y time)
                pw.println("");
            }
            
        } catch (FileNotFoundException e) {
            throw new FileOperationException(e);
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (IOException e) {
                Log.e("FATAL", e.getMessage());
            }
        }
    }

    /**
     * Delete prev generated image on data dir
     */
    public void deletePrevImage() throws FileOperationException {
        try {
            String dataDir = getDataDir();
            deleteFile(dataDir + "/image.png");
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            throw new FileOperationException(e);
        }
    }
    
    /**
     * Delete prev generated coordinates on data dir
     */
    public void deletePrevCoordinates() throws FileOperationException {
        try {
            String dataDir = getDataDir();
            deletefile(dataDir + "/coordinates.txt");
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            throw new FileOperationException(e);
        }
    }
    
    private String getDataDir() {
        return appCompatActivity.getApplicationContext().getFilesDir().getAbsolutePath();   
    }
    
    private void deleteFile(String fileName) throws IOException {
        File file = new File(fileName);
        Files.delete(file.toPath());
    }
}
