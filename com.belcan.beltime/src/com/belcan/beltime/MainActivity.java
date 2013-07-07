package com.belcan.beltime;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import org.eclipse.jdt.annotation.Nullable;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(@Nullable Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
