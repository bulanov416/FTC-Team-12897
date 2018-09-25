package org.firstinspires.ftc.teamcode;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by urijd on 10/22/2017.
 */

public class StaticLog {
    static String filename = "robotLog.txt";
    synchronized public static void addLine (String line) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, filename);

            try {
                if (!file.exists()){
                    file.createNewFile();
                }

                InputStream input = new FileInputStream(file);
                byte[] content = new byte[input.available()];
                input.read(content);
                input.close();

                byte[] newLine = (line + "\r\n").getBytes();
                byte[] newContent = new byte [content.length + newLine.length];

                System.arraycopy(content, 0, newContent, 0, content.length);
                System.arraycopy(newLine, 0, newContent, content.length, newLine.length);

                OutputStream output = new FileOutputStream(file);
                output.write(newContent);
                output.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Log.v("StaticLog", "Cannot write to external storage!");
        }
    }

    synchronized public static void clearLog(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                File file = new File(path, filename);
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Log.v("StaticLog", "Cannot write to external storage!");
        }
    }
}
