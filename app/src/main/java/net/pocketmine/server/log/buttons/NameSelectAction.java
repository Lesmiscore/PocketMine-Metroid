package net.pocketmine.server.log.buttons;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.rconclient.rcon.AuthenticationException;
import com.nao20010128nao.PM_Metroid.R;
import com.nao20010128nao.Wisecraft.misc.compat.AppCompatAlertDialog;
import com.nao20010128nao.Wisecraft.misc.compat.CompatArrayAdapter;
import java.io.IOException;
import net.pocketmine.server.LogActivity;
import net.pocketmine.server.ServerUtils;
import net.pocketmine.server.Utils.Constant;
import net.pocketmine.server.Utils.DebugWriter;

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
	public String[] onPlayersList()throws IOException,AuthenticationException,InterruptedException {
		if(ServerUtils.controller==null)return Constant.EMPTY_STRING_ARRAY;
		return ServerUtils.controller.list();
	}
	public String onPlayerNameHint() {
		return null;
	}
	public abstract void onSelected(String name);
}
