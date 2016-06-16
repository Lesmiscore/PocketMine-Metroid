package net.pocketmine.server;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;

public class TheApplication extends Application
{
	public static  TheApplication instance=null;
	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		instance=this;
		IntentFilter infi=new IntentFilter();
		infi.addAction(getPackageName()+".EX_COMMAND");
		registerReceiver(new CommandExecutorReceiver(),infi);
	}
	
	class CommandExecutorReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context p1, Intent p2) {
			// TODO: Implement this method
			String cmd=p2.getStringExtra("cmd");
			ServerUtils.executeCMD(cmd);
		}
	}
}
