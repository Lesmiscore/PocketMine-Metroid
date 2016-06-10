package net.pocketmine.server.log.buttons;
import android.view.*;
import android.widget.*;
import com.nao20010128nao.PM_Metroid.*;
import com.nao20010128nao.Wisecraft.misc.compat.*;
import java.io.*;
import net.pocketmine.server.*;
import net.pocketmine.server.Utils.*;

import com.nao20010128nao.PM_Metroid.R;

import static net.pocketmine.server.Utils.Utils.*;
import android.support.v7.app.*;
import com.google.rconclient.rcon.*;

public class Xp extends NameSelectAction {
	String   amount       ,player       ;
	Button   changeAmount ,changePlayer ;
	TextView amountView   ,playerView   ;
	String   amountHint   ,playerHint   ;
	/*      |             ,             |*/

	Button executeButton;
	AlertDialog dialog;

	String[] list;
	String hint;
	int selecting;

	public Xp(LogActivity r) {
		super(r);
	}

	@Override
	public void onClick(View p1) {
		// TODO: Implement this method
		dialog = new AppCompatAlertDialog.Builder(this,R.style.AppAlertDialog)
			.setView(inflateDialogView())
			.show();
	}

	@Override
	public int getViewId() {
		// TODO: Implement this method
		return R.id.xp;
	}

	@Override
	public void onSelected(String s) {
		// TODO: Implement this method
		switch (selecting) {
			case 0:
				amount = s;
				amountView.setText(s);
				break;
			case 1:
				player = s;
				playerView.setText(s);
				break;
		}
		selecting = -1;
	}

	public View inflateDialogView() {
		View v=((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.log_xp_screen, null, false);
		changeAmount = (Button)v.findViewById(R.id.changeAmount);
		changePlayer = (Button)v.findViewById(R.id.changePlayer);

		executeButton = (Button)v.findViewById(R.id.execute);

		amountView = (TextView)v.findViewById(R.id.amountView);
		playerView = (TextView)v.findViewById(R.id.playerName);

		changeAmount.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v) {
					hint = getResString(R.string.giveAmountHint);
					list = getResources().getStringArray(R.array.giveAmountConst);
					selecting = 0;
					Xp.super.onClick(v);
				}
			});
		changePlayer.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v) {
					hint = getResString(R.string.givePlayerHint);
					list = null;
					selecting = 1;
					Xp.super.onClick(v);
				}
			});
		executeButton.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v) {
					if (isNullString(amount) || isNullString(player)) {
						AppCompatAlertDialog.Builder b=new AppCompatAlertDialog.Builder(Xp.this);
						String mes="";
						if (isNullString(amount)) {
							mes += getResString(R.string.giveSelectAmount) + "\n";
						}
						if (isNullString(player)) {
							mes += getResString(R.string.giveSelectPlayer) + "\n";
						}
						b.setMessage(mes);
						b.setPositiveButton(android.R.string.ok, Constant.BLANK_DIALOG_CLICK_LISTENER);
						b.show();
					} else {
						getActivity().performSend("xp " + amount + "L " + player);
						dialog.dismiss();
					}
				}
			});
		return v;
	}

	@Override
	public String[] onPlayersList() throws IOException,AuthenticationException,InterruptedException {
		// TODO: Implement this method
		if (list == null) {
			return super.onPlayersList();
		} else {
			return list;
		}
	}

	@Override
	public String onPlayerNameHint() {
		// TODO: Implement this method
		return hint;
	}
}
