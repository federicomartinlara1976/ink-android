package com.simplify.ink.sample;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.simplify.ink.InkView;
import com.simplify.ink.InkPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

import java.awt.Dimension;

public class FileOperations {

    private MainActivity mainActivity;
    
    private String dataDir;

    public FileOperations(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        // Gets resources needed for operations (canvas and data dir)
        // Read / Write in external storage
        dataDir = mainActivity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * save ink signature bitmap to internal storage
     */
    public void saveImage() throws FileOperationException {
        FileOutputStream out = null;
        try {
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
            pw = new PrintWriter(new File(dataDir + "/coordinates.txt"));
            
            InkView ink = mainActivity.getInkView();
            Dimension d = ink.getDimension();
            List<InkPoint> points = ink.getPoints();
            int index = 0;
            
            for (InkPoint inkPoint : points) {
                String line = "";
                // El primer item de la lista son las dimensiones del área de dibujo
                if (index == 0) {
                    line = String.format(Locale.ROOT, "%f,%f;", d.getWidth(), d.getHeight());
                }
                else {
                    // Write the point line (format: x,y,c1x,c1y,c2x,c2y,velocity,time;)
                    line = String.format(Locale.ROOT, "%f,%f,%f,%f,%f,%f,%f,%d;",
                            inkPoint.getX(), inkPoint.getY(),
                            inkPoint.getC1x(), inkPoint.getC1y(),
                            inkPoint.getC2x(), inkPoint.getC2y(),
                            inkPoint.getVelocity(), inkPoint.getTime());
                }
                pw.println(line);
                index++;
            }
            
        } catch (FileNotFoundException e) {
            throw new FileOperationException(e);
        } finally {
            if (pw != null) {
                pw.close();
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
