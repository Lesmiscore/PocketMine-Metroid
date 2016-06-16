package com.ipaulpro.afilechooser;
import android.view.*;
import java.io.*;
import android.support.v4.view.*;
import android.net.*;
import android.content.*;
import android.os.*;

public class DirectoryChooserActivity extends FileChooserActivity
{
	File lastDir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		lastDir=mPath;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: Implement this method
		MenuItem select=menu.add(Menu.NONE,0,0,R.string.selectDir).setIcon(R.drawable.ic_action_accept_dark);
		MenuItemCompat.setShowAsAction(select,MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO: Implement this method
		switch(item.getItemId()){
			case 0:
				finishWithResult(lastDir);
				return true;
			case android.R.id.home:
				lastDir=lastDir.getParentFile();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFileSelected(File file) {
		// Ignore selection
		if(file.isDirectory()){
			lastDir=file;
			super.onFileSelected(file);
		}
	}
	
	private void finishWithResult(File file) {
        if (file != null) {
            Uri uri = Uri.fromFile(file);
            setResult(RESULT_OK, new Intent().setData(uri).putExtra(PATH,file.toString()));
        } else {
            setResult(RESULT_CANCELED);
        }
		finish();
    }
}
