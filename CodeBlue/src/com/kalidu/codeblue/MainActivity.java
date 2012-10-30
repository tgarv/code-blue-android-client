package com.kalidu.codeblue;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ((Button) findViewById(R.id.launch_login)).setOnClickListener(
        	new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					MainActivity.this.startActivity(intent);
				}
        	}
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
