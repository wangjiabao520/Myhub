package activity;

import com.example.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btn_opeActivity;
protected void onCreate(android.os.Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	btn_opeActivity=(Button) findViewById(R.id.btn_openActivity);
	btn_opeActivity.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent();
			
			intent.setClass(MainActivity.this, Activity_1.class);
			startActivity(intent);
		}
	});
	
 }
	
}
