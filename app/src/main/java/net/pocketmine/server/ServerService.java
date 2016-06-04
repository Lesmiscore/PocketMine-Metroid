/**
 * This file is part of DroidPHP
 *
 * (c) 2013 Shushant Kumar
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package net.pocketmine.server;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.nao20010128nao.PM_Metroid.R;
import android.support.v7.app.*;

public class ServerService extends Service {
	private boolean isRunning = false;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		run();

		return (START_NOT_STICKY);
	}

	@Override
	public void onDestroy() {
		stop();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return (null);
	}

	@SuppressWarnings("deprecation")
	private void run() {
		if (!isRunning) {

			isRunning = true;

			Context context = getApplicationContext();
			
			android.support.v4.app.NotificationCompat.Builder note=new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("PocketMine-MP is running")
				.setWhen(System.currentTimeMillis());
			Intent i = new Intent(context, HomeActivity.class);

			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);

			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

			note.setContentText("Tap here to open PocketMine-MP for Android.");
			note.setContentIntent(pi);
			note.setOngoing(true);

			startForeground(1337, note.build());
		}
	}

	private void stop() {
		if (isRunning) {

			isRunning = false;
			stopForeground(true);
		}
	}
}
