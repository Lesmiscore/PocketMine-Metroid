package net.pocketmine.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nao20010128nao.PM_Metroid.R;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.*;
import net.pocketmine.server.install.*;

public class VersionManagerActivity extends AppCompatActivity {
	public ArrayAdapter<CharSequence> adapter;
	private Boolean install = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		install = getIntent().getBooleanExtra("install", false);
		setContentView(R.layout.version_manager_new);

		start();
	}

	public String getPageContext(String url) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL(
				url).openStream()));
		StringBuilder sb = new StringBuilder();
		String str;
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		return sb.toString();
	}

	private void start() {
		final ProgressBar pbar = (ProgressBar) findViewById(R.id.loadingBar);
		final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
		final Button skip = (Button) findViewById(R.id.skipBtn);
		skip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		pbar.setVisibility(View.VISIBLE);
		scrollView.setVisibility(View.GONE);
		skip.setVisibility(View.GONE);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					JSONObject softObj = (JSONObject) JSONValue
							.parse(getPageContext("http://pocketmine.net/api/?channel=soft"));
					Long date = (Long) softObj.get("date");
					Date d = new Date(date * 1000);
					
					JSONObject stableObj = (JSONObject) JSONValue
							.parse(getPageContext("http://pocketmine.net/api/?channel=stable"));
					final String stableVersion = (String) stableObj
							.get("version");
					final String stableAPI = (String) stableObj
							.get("api_version");
					date = (Long) stableObj.get("date");
					d = new Date(date * 1000);
					final String stableDate = SimpleDateFormat
							.getDateInstance().format(d);
					final String stableDownloadURL = (String) stableObj.get("download_url");

					JSONObject betaObj = (JSONObject) JSONValue
							.parse(getPageContext("http://pocketmine.net/api/?channel=beta"));
					final String betaVersion = (String) betaObj.get("version");
					final String betaAPI = (String) betaObj.get("api_version");
					date = (Long) betaObj.get("date");
					d = new Date(date * 1000);
					final String betaDate = SimpleDateFormat.getDateInstance()
							.format(d);
					final String betaDownloadURL = (String) betaObj.get("download_url");

					JSONObject devObj = (JSONObject) JSONValue
							.parse(getPageContext("http://pocketmine.net/api/?channel=development"));
					final String devVersion = (String) devObj.get("version");
					final String devAPI = (String) devObj.get("api_version");
					date = (Long) devObj.get("date");
					d = new Date(date * 1000);
					final String devDate = SimpleDateFormat.getDateInstance()
							.format(d);
					final String devDownloadURL = (String) devObj.get("download_url");
					
					final String genisysVersion=SoftwareKind.GENISYS_LATEST.getVersion();
					final String genisysDate=SimpleDateFormat.getInstance().format(SoftwareKind.GENISYS_LATEST.getReleaseDate())+" UTC";
					
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							TextView stableVersionView = (TextView) findViewById(R.id.stable_version);
							TextView stableDateView = (TextView) findViewById(R.id.stable_date);
							Button stableDownload = (Button) findViewById(R.id.download_stable);
							TextView betaVersionView = (TextView) findViewById(R.id.beta_version);
							TextView betaDateView = (TextView) findViewById(R.id.beta_date);
							Button betaDownload = (Button) findViewById(R.id.download_beta);
							TextView devVersionView = (TextView) findViewById(R.id.dev_version);
							TextView devDateView = (TextView) findViewById(R.id.dev_date);
							Button devDownload = (Button) findViewById(R.id.download_dev);
							TextView genisysVersionView = (TextView) findViewById(R.id.genisys_version);
							TextView genisysDateView = (TextView) findViewById(R.id.genisys_date);
							Button genisysDownload = (Button) findViewById(R.id.download_genisys);
							
							stableVersionView.setText(getResources().getString(R.string.version)+": "
									+ stableVersion + " (API: " + stableAPI
									+ ")");
							stableDateView.setText(stableDate);
							stableDownload.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									download(SoftwareKind.POCKETMINE_STABLE);
								}
							});

							betaVersionView.setText(getResources().getString(R.string.version)+": " + betaVersion
									+ " (API: " + betaAPI + ")");
							betaDateView.setText(betaDate);
							betaDownload.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									download(SoftwareKind.POCKETMINE_BETA);
								}
							});

							devVersionView.setText(getResources().getString(R.string.version)+": " + devVersion
									+ " (API: " + devAPI + ")");
							devDateView.setText(devDate);
							devDownload.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									download(SoftwareKind.POCKETMINE_DEV);
								}
							});
							
							genisysVersionView.setText(getResources().getString(R.string.version)+": " + genisysVersion);
							genisysDateView.setText(genisysDate);
							genisysDownload.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										download(SoftwareKind.GENISYS_LATEST);
									}
								});
							
							pbar.setVisibility(View.GONE);
							scrollView.setVisibility(View.VISIBLE);
							if (install) {
								skip.setVisibility(ServerUtils
										.checkPMInstalled() ? View.VISIBLE
										: View.GONE);
							}
						}
					});
				} catch (Exception err) {
					err.printStackTrace();
					if (install) {
						showToast(getResources().getString(R.string.failed_load_version));
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									finish();
								}
							});
							return;
						}
						start();
					} else {
						showToast(getResources().getString(R.string.failed_load_version_2));
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								finish();
							}
						});
					}
				}
			}
		}).start();
	}

	private void download(final SoftwareKind sk) {
		File vdir = new File(ServerUtils.getDataDirectory(), "/versions/");
		if(!vdir.exists()){
			vdir.mkdirs();
		}
		
		final VersionManagerActivity ctx = this;
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				final ProgressDialog dlDialog = new ProgressDialog(ctx);
				dlDialog.setMax(100);
				dlDialog.setTitle(R.string.dl_this_version);
				dlDialog.setMessage(getResources().getString(R.string.please_wait));
				dlDialog.setIndeterminate(false);
				dlDialog.setCancelable(false);
				dlDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dlDialog.show();
				dlDialog.setProgress(0);
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							URL url = new URL(sk.getDownloadAddress());
							URLConnection connection = url.openConnection();
							connection.connect();
							int fileLength = connection.getContentLength();

							InputStream input = new BufferedInputStream(url
									.openStream());
							OutputStream output = new FileOutputStream(
									ServerUtils.getDataDirectory()
											+ "/versions/" + sk.getVersion() + ".phar");

							byte data[] = new byte[1024];
							long total = 0;
							int count;
							int lastProgress = 0;
							while ((count = input.read(data)) != -1) {
								total += count;
								int progress = (int) (total * 100 / fileLength);
								if (progress != lastProgress) {
									dlDialog.setProgress(progress);
									lastProgress = progress;
								}
								output.write(data, 0, count);
							}

							output.flush();
							output.close();
							input.close();
						} catch (Exception e) {
							e.printStackTrace();
							showToast(getResources().getString(R.string.dl_this_version_failed));
							dlDialog.dismiss();
							return;
						}

						dlDialog.dismiss();

						try {
							install(sk);
						} catch (IOException e) {
							
						}
					}

				}).start();
			}
		});
	}

	private void install(final SoftwareKind sk)throws IOException{
		final VersionManagerActivity ctx = this;
		final CharSequence fver = sk.getVersion();

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				final ProgressDialog iDialog = new ProgressDialog(ctx);
				iDialog.setMax(0);
				iDialog.setTitle(R.string.ins_this_version);
				iDialog.setMessage(getResources().getString(R.string.please_wait));
				iDialog.setIndeterminate(true);
				iDialog.setCancelable(false);
				iDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				iDialog.show();
				iDialog.setProgress(0);

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							new File(ServerUtils.getDataDirectory(), "PocketMine-MP.php").delete();
						} catch (Exception e) {
						}
						try {
							delete(new File(ServerUtils.getDataDirectory(), "src/"));
						} catch (Exception e) {
						}
						try {
							new File(ServerUtils.getDataDirectory(), "PocketMine-MP.phar").delete();
						} catch (Exception e) {
						}
						try {
							new File(ServerUtils.getDataDirectory(), "src/pocketmine/PocketMine-MP.php").delete();
						} catch (Exception e) {
						}
						
						File installer=new File(ServerUtils.getDataDirectory(), "versions/" + fver + ".phar");
						
						try {
							if(!sk.getInstaller().install(installer,new File(ServerUtils.getDataDirectory())))throw new RuntimeException();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									if (install) {
										Intent ver = new Intent(
												VersionManagerActivity.this,
												ConfigActivity.class);
										ver.putExtra("install", true);
										startActivity(ver);
									}

									ctx.finish();
								}
							});
						} catch (Exception e) {
							showToast(getResources().getString(R.string.dl_this_version_failed));
							e.printStackTrace();
						}
					}
				}).start();
			}
		});

	}

	public void delete(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				delete(file);
			}
		}
		f.delete();
	}

	public void showToast(String msg) {
		final String fmsg = msg;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), fmsg,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && install) {

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
