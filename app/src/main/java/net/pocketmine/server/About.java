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
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.nao20010128nao.PM_Metroid.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import net.pocketmine.server.Utils.MinecraftFormattingCodeParser;

public class About extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		TextView text = (TextView) findViewById(R.id.about_text);
		
		
		StringWriter sw=new StringWriter();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.about)));
			char[] buf=new char[200];int r;
			while(true){
				r=br.read(buf);
				if(r<=0)break;
				sw.write(buf,0,r);
			}
			MinecraftFormattingCodeParser mfcp=new MinecraftFormattingCodeParser();
			mfcp.loadFlags(sw.toString(),(byte)0);
			text.setText(mfcp.build());
		} catch (IOException e) {
			
		}finally{
			try {
				if (br != null)br.close();
			} catch (IOException e) {}
		}
	}
}
