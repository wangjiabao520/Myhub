package com.example;

import com.example.intent.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	private Button btn_one;
	private Button btn_two;
	private Button btn_three;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent);
		btn_one = (Button) findViewById(R.id.btn_one);
		btn_two = (Button) findViewById(R.id.btn_two);
		btn_three = (Button) findViewById(R.id.btn_three);
		btn_one.setOnClickListener( this);
		btn_two.setOnClickListener( this);
		btn_three.setOnClickListener(this);
		
	}
	public void onClick(View v){
		switch(v.getId()){
		case R.id.btn_one:
			Intent intent1 = new Intent();
			intent1.setClass(this,Activity01.class);
			startActivity(intent1);
			break;
			
		case R.id.btn_two:
			Intent intent2 = new Intent();
			intent2.setClassName(this,"com.example.Activity02");
			startActivity(intent2);
			break;
			
		case R.id.btn_three:
			Intent intent3 = new Intent();
			intent3.setClassName("com.example.intent","com.example.Activity03");
			startActivity(intent3);
			break;
			
			default:
			break;
			
		}
		
	}
	

}
