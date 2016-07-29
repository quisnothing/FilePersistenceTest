package com.example.filepersistencetest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit=(EditText)findViewById(R.id.edit);
        String inputText=load();
        if(!TextUtils.isEmpty(inputText)){
            edit.setText(inputText);
            edit.setSelection(inputText.length());//设置光标始终在输入的最右侧
            Toast.makeText(this,"Restoring succced",Toast.LENGTH_SHORT).show();
        }
    }

    public void save(String inputtext){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter((new OutputStreamWriter(out)));
            writer.write(inputtext);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        String inputText=edit.getText().toString();
        save(inputText);
    }
    public String load(){
        FileInputStream in =null;
        BufferedReader reader=null;
        StringBuilder content = new StringBuilder();

        try{
            in=openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
