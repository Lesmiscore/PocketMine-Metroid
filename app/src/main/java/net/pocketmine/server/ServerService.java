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
import net.pocketmine.server.R;

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
			
			Notification note = new Notification(R.drawable.ic_launcher,
					"PocketMine-MP is running",
					System.currentTimeMillis());
			Intent i = new Intent(context, HomeActivity.class);

			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);

			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

			note.setLatestEventInfo(this, "PocketMine-MP is running", "Tap here to open PocketMine-MP for Android.", pi);
			note.flags |= Notification.FLAG_NO_CLEAR;

			startForeground(1337, note);
		}
	}

	private void stop() {
		if (isRunning) {

			isRunning = false;
			stopForeground(true);
		}
	}
}