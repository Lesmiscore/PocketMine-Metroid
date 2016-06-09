package net.pocketmine.server.log.buttons;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.nao20010128nao.PM_Metroid.*;
import com.nao20010128nao.Wisecraft.misc.compat.*;
import java.io.*;
import net.pocketmine.server.*;
import net.pocketmine.server.Utils.*;

import com.nao20010128nao.PM_Metroid.R;
import android.support.v7.app.*;

public abstract class NameSelectAction extends BaseAction {
	EditText name;
	ListView online;
	Button submit;
	AlertDialog dialog;
	public NameSelectAction(LogActivity act) {
		super(act);
	}

	@Override
	public void onClick(View p1) {
		// TODO: Implement this method
		dialog = new AppCompatAlertDialog.Builder(this,R.style.AppAlertDialog)
			.setView(inflatePlayersView())
			.show();
	}
	private View inflatePlayersView() {
		View v=((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.log_players_base, null, false);
		online = (ListView)v.findViewById(R.id.players);
		name = (EditText)v.findViewById(R.id.playerName);
		submit = (Button)v.findViewById(R.id.ok);
		final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		online.setAdapter(aa);
		online.setOnItemClickListener(new ListView.OnItemClickListener(){
				public void onItemClick(AdapterView a, View v, int o, long i) {
					name.setText(aa.getItem(o).toString());
				}
			});
		submit.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					// TODO: Implement this method
					try {
						dialog.cancel();
						dialog.dismiss();
						dialog.hide();
						dialog = null;
					} finally {
						onSelected(name.getText().toString());
					}
				}
			});
		String hint=onPlayerNameHint();
		if (hint != null) {
			name.setHint(hint);
		}
		new AsyncTask<Void,Void,String[]>(){
			public String[] doInBackground(Void[] a) {
				try {
					return onPlayersList();
				} catch (Throwable e) {
					DebugWriter.writeToE("RCON-NSA",e);
				}
				return null;
			}
			public void onPostExecute(String[] s) {
				CompatArrayAdapter.addAll(aa,s);
			}
		}.execute();
		return v;
	}
	public String[] onPlayersList()throws IOException,InterruptedException {
		String[] old=HomeActivity.players;
		ServerUtils.refreshPlayers();
		while(HomeActivity.players==old)Thread.sleep(100);
		return HomeActivity.players;
	}
	public String onPlayerNameHint() {
		return null;
	}
	public abstract void onSelected(String name);
}
