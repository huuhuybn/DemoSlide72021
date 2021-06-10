package com.poly.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    // Tải slide 7 và tạo dự án mới
    // Lưu dữ liệu vào bộ nhớ trong
    // --------------- bộ nhớ ngoài (sd card)
    // --------------- bộ nhớ cache ( tối đa 1MB cho 1 file)
    // --------------- bộ nhớ trong dạng Shared Preference (cặp key và value)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String data = "ahsdfsdfhsdhfsdjk0";

    public void themVaoBoNhoTrong(View view) throws IOException {
        FileOutputStream stream = openFileOutput("tenFile", MODE_PRIVATE);
        stream.write(data.getBytes());
        stream.close();
    }

    public void layTuBoNhoTrong(View view) throws FileNotFoundException {
        FileInputStream stream = openFileInput("tenFile");
        Scanner scanner = new Scanner(stream);
        String duLieu = "";
        while (scanner.hasNext()) {
            duLieu += scanner.nextLine();
        }
        scanner.close();
        Toast.makeText(this, duLieu, Toast.LENGTH_SHORT).show();
    }

    public void themVaoBoNhoNgoai(View view) throws IOException {
        if (ContextCompat.
                checkSelfPermission
                        (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // ghi vao the nho sdCard
            String sdCardFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/tenFile.txt";
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(sdCardFile));
            writer.write(data);
            writer.close();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        }
    }

    public void layTuBoNhoNgoai(View view) throws FileNotFoundException {
        if (ContextCompat.
                checkSelfPermission
                        (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // ghi vao the nho sdCard
            String sdCardFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/tenFile.txt";
            Scanner scanner = new Scanner(new File(sdCardFile));
            String duLieu = "";
            while (scanner.hasNext()) {
                duLieu += scanner.nextLine();
            }
            scanner.close();
            Toast.makeText(this, duLieu, Toast.LENGTH_SHORT).show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        }
    }

    public void themVaoBoNhoCache(View view) throws IOException {
        File cacheFile = new File(getCacheDir(), "fileCache.cache");
        cacheFile.createNewFile();
        FileOutputStream stream = new FileOutputStream(cacheFile.getAbsolutePath());
        stream.write(data.getBytes());
        stream.close();
    }

    public void layTuBoNhoCache(View view) throws FileNotFoundException {
        File cacheFile = new File(getCacheDir(), "fileCache.cache");
        Scanner scanner = new Scanner(cacheFile);
        String duLieu = "";
        while (scanner.hasNext()) {
            duLieu += scanner.nextLine();
        }
        scanner.close();
        Toast.makeText(this, duLieu, Toast.LENGTH_LONG).show();
    }

    public void themVaoBoNhoShared(View view) {
        SharedPreferences preferences = getSharedPreferences("filycuaminh", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dat", data);
        editor.putInt("num", 1000);
        editor.commit();
    }

    public void layTuBoNhoShared(View view) {
        SharedPreferences preferences = getSharedPreferences("filycuaminh", MODE_PRIVATE);
        String dat = preferences.getString("dat", "macdinh");
        int num = preferences.getInt("num", -1);
        Toast.makeText(this, dat + " : " + num, Toast.LENGTH_SHORT).show();
    }
}