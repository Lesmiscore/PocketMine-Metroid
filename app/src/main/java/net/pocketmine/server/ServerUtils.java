/**
 * This file is part of DroidPHP
 *
 * (c) 2013 Shushant Kumar
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package net.pocketmine.server;

import android.content.Context;
import android.util.Log;
import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.RCon;
import com.nao20010128nao.PM_Metroid.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.pocketmine.server.Utils.Constant;
import net.pocketmine.server.Utils.Utils;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class ServerUtils {

	final static String TAG = "com.MrARM.DroidPocketMine.ServerUtils";
	static Context mContext;
	private static java.io.OutputStream stdin;
	private static java.io.InputStream stdout;
	public static RCon controller;

	final public static void setContext(Context mContext) {
		ServerUtils.mContext = mContext;
	}

	final public static String getAppDirectory() {
		return mContext.getApplicationInfo().dataDir;
	}

	final public static String getDataDirectory() {
		return PreferenceManager.getDefaultSharedPreferences(mContext).getString("data", android.os.Environment.getExternalStorageDirectory().getPath() + "/PocketMine");
	}

	final public static Boolean killProcessByName(String mProcessName) {
		return execCommand(getAppDirectory() + "/killall " + mProcessName);
	}

	final public static void stopServer() {
		killProcessByName("php");
	}

	static Process serverProc;

	public static Boolean isRunning() {
		try {
			serverProc.exitValue();
		} catch (Exception e) {
			// do there the rest
			return true;
		}
		return false;
	}

	final public static void runServer() {
		File f = new File(getDataDirectory(), "tmp/");
		if (!f.exists()) {
			f.mkdir();
		} else if (!f.isDirectory()) {
			f.delete();
			f.mkdir();
		}
		setPermission();
		updatePhpIniIfNeeded();
		String file = "/PocketMine-MP.php";
		if (new File(getDataDirectory(), "/PocketMine-MP.phar").exists()) {
			file = "/PocketMine-MP.phar";
		}else if (new File(getDataDirectory(), "/src/pocketmine/PocketMine.php").exists()) {
			file = "/src/pocketmine/PocketMine.php";
		}
		String[] serverCmd = { getAppDirectory() + "/php",
				getDataDirectory() + file };

		ProcessBuilder builder = new ProcessBuilder(serverCmd);
		builder.redirectErrorStream(true);
		builder.directory(new File(getDataDirectory()));
		builder.environment().put("TMPDIR", getDataDirectory() + "/tmp");
		try {
			serverProc = builder.start();
			stdout = serverProc.getInputStream();
			stdin = serverProc.getOutputStream();
			Log.i(TAG, "PHP is started");

			LogActivity.log(mContext.getResources().getString(R.string.log_starting));

			Thread tMonitor = new Thread() {
				public void run() {
					InputStreamReader reader = new InputStreamReader(stdout,
							Charset.forName("UTF-8"));
					BufferedReader br = new BufferedReader(reader);
					LogActivity.log(mContext.getResources().getString(R.string.log_started));
					LogActivity.updateRunningState(true);
					while (isRunning()) {
						try {
							char[] buffer = new char[8192];
							int size = 0;
							while ((size = br.read(buffer, 0, buffer.length)) != -1) {
								StringBuilder s = new StringBuilder();
								for (int i = 0; i < size; i++) {
									char c = buffer[i];
									if (c == '\r') {
									} //
									else if (c == '\n' || c == '\u0007') {
										String line = s.toString();
										Log.d(TAG, line);

										if (c == '\u0007'
												&& line.startsWith("\u001B]0;")) {
											line = line.substring(4);
											System.out
													.println("[Stat] " + line);
											HomeActivity.setStats(
													getStat(line, "Online"),
													getStat(line, "RAM"),
													getStat(line, "U"),
													getStat(line, "D"),
													getStat(line, "TPS"));
										} else {
											LogActivity.log("[Server] "
													+ (line.replace("&",
															"&amp;").replace(
															"<", "&lt;")
															.replace(">",
																	"&gt;")));
											if (line.contains("] logged in with entity id ")
													|| line.contains("] logged out due to ")) {
												refreshPlayers();
											}
										}
										s = new StringBuilder();
									} else {
										s.append(buffer[i]);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								br.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					controller=null;
					LogActivity.updateRunningState(false);
					LogActivity.log(mContext.getResources().getString(R.string.log_stopped));
					HomeActivity.stopNotifyService();
					HomeActivity.hideStats();
				}
			};
			tMonitor.start();
		} catch (java.lang.Exception e) {
			Log.e(TAG, "Unable to start PHP", e);
			LogActivity.log(mContext.getResources().getString(R.string.log_php_error));
			HomeActivity.stopNotifyService();
			HomeActivity.hideStats();
			killProcessByName("php");
		}
		return;
	}

	public static String getStat(String line, String stat) {
		stat = stat + " ";
		String result = line.substring(line.indexOf(stat) + stat.length());
		int iof = result.indexOf(" ");
		if (iof != -1) {
			result = result.substring(0, iof);
		}
		return result;
	}

	public static void refreshPlayers() {
		System.out.println("Refreshing player list");
		Utils.prepareLooper();
		new Thread(){
			public void run(){
				String s=executeCmdRcon("list");
				if(s==null)return;
				String[] lines=Utils.lines(s);
				String[] players;
				if(lines.length==2){
					if("".equals(lines[1].trim())){
						players=Constant.EMPTY_STRING_ARRAY;
					}else{
						players=lines[1].split(", ");
					}
				}else{
					players=Constant.EMPTY_STRING_ARRAY;
				}
				HomeActivity.updatePlayerList(players);
			}
		}.start();
	}
	
	public static void refreshBanlist() {
		System.out.println("Refreshing banned player list");
		Utils.prepareLooper();
		new Thread(){
			public void run(){
				String s=executeCmdRcon("banlist players");
				if(s==null)return;
				String[] lines=Utils.lines(s);
				String[] players;
				if(lines.length==2){
					if("".equals(lines[1].trim())){
						players=Constant.EMPTY_STRING_ARRAY;
					}else{
						players=lines[1].split(", ");
					}
				}else{
					players=Constant.EMPTY_STRING_ARRAY;
				}
				HomeActivity.updateBanList(players);
			}
		}.start();
	}
	
	public static void refreshBannedIps() {
		System.out.println("Refreshing banned ips list");
		Utils.prepareLooper();
		new Thread(){
			public void run(){
				String s=executeCmdRcon("banlist ips");
				if(s==null)return;
				String[] lines=Utils.lines(s);
				String[] players;
				if(lines.length==2){
					if("".equals(lines[1].trim())){
						players=Constant.EMPTY_STRING_ARRAY;
					}else{
						players=lines[1].split(", ");
					}
				}else{
					players=Constant.EMPTY_STRING_ARRAY;
				}
				HomeActivity.updateBanIpsList(players);
			}
		}.start();
	}
	
	final public static boolean execCommand(String mCommand) {
		Runtime r = Runtime.getRuntime();
		try {
			r.exec(mCommand);
		} catch (java.io.IOException e) {
			Log.e(TAG, "execCommand", e);
			r = null;
			return false;
		}
		return true;
	}

	final static private void setPermission() {
		try {
			for(File f:new File(getAppDirectory()).listFiles()){
				Log.d("setPermission",f.getAbsolutePath());
				execCommand("/system/bin/chmod 777 "+f.getAbsolutePath());
			}
		} catch (Throwable e) {
			Log.e(TAG, "setPermission", e);
		}
	}
	
	final static private void updatePhpIniIfNeeded(){
		try{
			File php_ini=new File(getAppDirectory(),"php.ini");
			if(!php_ini.exists()){
				return;
			}
			Map<String,List<String>> values=Utils.readExPropertiesFile(php_ini);
			values.put("phar.readonly",Arrays.asList("0"));
			StringWriter sw=new StringWriter();
			for(String k:values.keySet())
				for(String v:values.get(k))
					sw.append(k).append('=').append(v).append('\n');
			FileWriter fw=null;
			try{
				fw=new FileWriter(php_ini);
				fw.append(sw.toString());
			}finally{
				if(fw!=null)fw.close();
			}
		}catch(Throwable e){
			Log.e(TAG, "setPermission", e);
		}
	}

	public static boolean checkPhpInstalled() {

		File mPhp = new File(getAppDirectory(), "/php");
		
		int saveVer = HomeActivity.prefs != null ? HomeActivity.prefs.getInt(
				"filesVersion", 0) : 0;

		if (mPhp.exists() && saveVer == 6) {

			return true;

		}

		return false;

	}
	
	public static boolean checkPMInstalled() {
		File mPM = new File(getDataDirectory(), "/PocketMine-MP.php");
		File mPMPhar = new File(getDataDirectory(), "/PocketMine-MP.phar");
		File mPMSrc = new File(getDataDirectory(), "/src/pocketmine/PocketMine.php");

		int saveVer = HomeActivity.prefs != null ? HomeActivity.prefs.getInt(
			"filesVersion", 0) : 0;
			
		if ((mPM.exists() || mPMPhar.exists() || mPMSrc.exists()) && saveVer == 6) {

			return true;

		}

		return false;

	}
	
	public static void executeCMD(String CCmd) {

		try {
			stdin.write((CCmd + "\r\n").getBytes());
			stdin.flush();
		} catch (Exception e) {
			// stdin.close();
			Log.e(TAG, "Cannot execute: " + CCmd, e);

		}
	}
	
	public static String executeCmdRcon(String c){
		if(!rconSupported())return null;
		Map<String,String> prop=Utils.readPropertiesFile();
		if(controller==null){
			try {
				controller = new RCon("localhost", Integer.valueOf(prop.get("server-port")), prop.get("rcon.password").toCharArray());
			} catch (AuthenticationException|NumberFormatException|IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		try {
			return controller.send(c);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean rconSupported(){
		Map<String,String> prop=Utils.readPropertiesFile();
		if("on".equals(prop.get("enable-rcon"))){
			if(!"".equals(prop.get("rcon.password"))){
				return true;
			}
		}
		return false;
	}
}
