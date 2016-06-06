/**
 * This file is part of DroidPHP
 *
 * (c) 2013 Shushant Kumar
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package net.pocketmine.server;

import android.os.Bundle;
import android.webkit.WebView;

import com.nao20010128nao.PM_Metroid.R;
import android.support.v7.app.*;

public class About extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		WebView wView = (WebView) findViewById(R.id.about_us_webview);
		wView.loadUrl("file:///android_asset/about.html");
	}
}
