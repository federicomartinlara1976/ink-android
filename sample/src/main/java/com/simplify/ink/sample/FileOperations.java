package com.simplify.ink.sample;

import android.graphics.Bitmap;
import android.graphics.Color;
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
    
    private String dataDir;
    
    private InkView ink;

    public FileOperations(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        // Gets resources needed for operations (canvas and data dir)
        dataDir = mainActivity.getApplicationContext().getFilesDir().getAbsolutePath();
        ink = mainActivity.getInkView();
    }

    /**
     * save ink signature bitmap to internal storage
     */
    public void saveImage() throws FileOperationException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dataDir + "/image.png");

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
            pw = new PrintWriter(new File(dataDir + "/coordinates.txt"));
            
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
        deleteFile(dataDir + "/image.png");
    }
    
    /**
     * Delete prev generated coordinates on data dir
     */
    public void deletePrevCoordinates() throws FileOperationException {
        deleteFile(dataDir + "/coordinates.txt");
    }
    
    /**
     * Safety delete
     */
    private void deleteFile(String fileName) throws FileOperationException {
        try {
            File file = new File(fileName);
            Files.delete(file.toPath());
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            throw new FileOperationException(e);
        }
    }
}
