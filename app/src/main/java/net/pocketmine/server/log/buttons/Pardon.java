package net.pocketmine.server.log.buttons;
import android.content.*;
import com.nao20010128nao.PM_Metroid.*;
import com.nao20010128nao.Wisecraft.misc.compat.*;
import java.io.*;
import net.pocketmine.server.*;
import net.pocketmine.server.Utils.*;

import com.nao20010128nao.PM_Metroid.R;

public class Pardon extends NameSelectAction {
	public Pardon(LogActivity a) {
		super(a);
	}

	@Override
	public void onSelected(final String s) {
		// TODO: Implement this method
		new AppCompatAlertDialog.Builder(this,R.style.AppAlertDialog)
			.setMessage(getResString(R.string.pardonAsk).replace("[PLAYER]", s))
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface di, int w) {
					getActivity().performSend("pardon " + s);
				}
			})
			.setNegativeButton(android.R.string.cancel, Constant.BLANK_DIALOG_CLICK_LISTENER)
			.show();
	}

	@Override
	public String[] onPlayersList() throws IOException,InterruptedException {
		// TODO: Implement this method
		String[] old=HomeActivity.banlist;
		ServerUtils.refreshPlayers();
		while(HomeActivity.banlist==old)Thread.sleep(100);
		return HomeActivity.players;
	}

	@Override
	public int getViewId() {
		// TODO: Implement this method
		return R.id.pardon;
	}
}
