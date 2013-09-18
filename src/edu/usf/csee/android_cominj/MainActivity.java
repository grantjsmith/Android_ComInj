package edu.usf.csee.android_cominj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	String input, output = "";
	File root = Environment.getExternalStorageDirectory();
	String filename = root + "/test.sh";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView Txt = (TextView) findViewById(R.id.textView2);
        Txt.setMovementMethod(ScrollingMovementMethod.getInstance());
        Txt.setScrollBarStyle(0x03000000);
        Txt.setVerticalScrollBarEnabled(true);
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				output = "";
				input = ((EditText) findViewById(R.id.editText1)).getText().toString();
				input = "echo " + input;
				writeToSD();
				readFromSD();
				runScript();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void echoInput(String input)
	{
		TextView t = ((TextView)findViewById(R.id.textView2));
		t.setText(input);
	}
	private void writeToSD()
	{
		try {
			FileWriter f = new FileWriter(filename);
			f.write(input);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFromSD()
	{
		try {
			FileReader f = new FileReader(filename);
			BufferedReader br = new BufferedReader(f);
			String line, fileContent = null;
			
			try {
				while ( (line = br.readLine()) != null)
				{
					fileContent = line;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runScript()
	{
		Runtime r = Runtime.getRuntime();
		try {
			Process proc = r.exec("sh " + filename);
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

	       while ((line = br.readLine()) != null) {
	         System.out.println(line);
	         output += line + "\n";
	       }
         echoInput(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
