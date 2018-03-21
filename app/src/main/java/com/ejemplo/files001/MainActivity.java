package com.ejemplo.files001;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.print.PrintDocumentAdapter;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    EditText textBox;
    Button btnSave, btnLoad;

    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textBox = (EditText) findViewById(R.id.editText);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "HOliiii", Toast.LENGTH_SHORT).show();
                String state;
                state = Environment.getExternalStorageState();
                if(Environment.MEDIA_MOUNTED.equals(state)){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Permission is not granted
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.READ_CONTACTS)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_ENABLE_WRITE_AHEAD_LOGGING);

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        // Permission has already been granted
                    }
                    File Root = Environment.getExternalStorageDirectory();
                    File Dir = new File(Root.getAbsolutePath()+"/MyAppFile");
                    if(!Dir.exists()){
                        Dir.mkdir();
                    }
                    File file = new File(Dir,"MyMessage.txt");
                    String Message = textBox.getText().toString();
                    try{
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(Message.getBytes());
                        fileOutputStream.close();
                        textBox.setText("");
                        Toast.makeText(getApplicationContext(), "Mensaje Guardado", Toast.LENGTH_SHORT).show();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else{
                    Toast.makeText(getApplicationContext(), "La SD card no funciona", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Boolean chkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
    public void onClickSave(View view) {
        String state;
        state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath()+"/MyAppFile");
            if(!Dir.exists()){
                Dir.mkdir();
            }
            File file = new File(Dir,"MyMessage.txt");
            String Message = textBox.getText().toString();
            try{
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.close();
                textBox.setText("");
                Toast.makeText(this, "Mensaje Guardado", Toast.LENGTH_SHORT).show();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else{
            Toast.makeText(this, "La SD card no funciona", Toast.LENGTH_SHORT).show();
        }


        /*
        String str = textBox.getText().toString();
        try {
            FileOutputStream fOut = openFileOutput("textfile.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            //---write the string to the file---
            try {
                osw.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            osw.flush();
            osw.close();
            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
            textBox.setText("");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }*/
    }

    public void onClickLoad(View view) {
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File(Root.getAbsolutePath()+"/MyAppFile");
        File file = new File(Dir,"MyMessage.txt");
        String Message;
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while((Message=bufferedReader.readLine()) != null){
                stringBuffer.append(Message+"\n");
            }
            textBox.setText(stringBuffer.toString());
            textBox.setVisibility(View.VISIBLE);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            FileInputStream fIn = openFileInput("textfile.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0,
                                charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            textBox.setText(s);
            Toast.makeText(getBaseContext(), "File loaded successfully!",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        */
    }
}


