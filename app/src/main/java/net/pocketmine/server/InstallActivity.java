package net.pocketmine.server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import net.pocketmine.server.R;
import android.support.v7.app.*;

public class InstallActivity extends AppCompatActivity {
	String filePath;
	boolean installing=true;
	boolean reinstall;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.install_activity);
		filePath=getIntent().getStringExtra("file");
		reinstall=getIntent().getBooleanExtra("reinst",false);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		if (true) {
			HomeActivity.btn_runServer.setEnabled(false);
			HomeActivity.btn_stopServer.setEnabled(false);
			InstallerAsync ia = new InstallerAsync();
			ia.ctx = this;
			ia.fromWhichAct = 0;
			ia.fromAssets = true;
			ia.toLoc = ServerUtils.getAppDirectory() + "/";
			ia.orgLoc = filePath;
			ia.tv_install_exec = (TextView) findViewById(R.id.installProgress);
			ia.tv_install_exec.setVisibility(1);
			ia.execute();
		}
	}
	
	public void contiuneInstall(){
		if(reinstall){
			finish();
			return;
		}
		if(HomeActivity.prefs!=null){
			SharedPreferences.Editor spe = HomeActivity.prefs.edit();
			spe.putInt("filesVersion", 6);
			spe.commit();
		}
		if(!ServerUtils.checkPMInstalled()){
			Intent ver = new Intent(this, VersionManagerActivity.class);
			ver.putExtra("install", true);
			startActivity(ver);
		}
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO: Implement this method
		if(!installing)finish();
	}
}
