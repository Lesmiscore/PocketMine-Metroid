package net.pocketmine.server.log.buttons;

import android.content.*;
import android.view.*;
import com.nao20010128nao.PM_Metroid.*;
import com.nao20010128nao.Wisecraft.misc.compat.*;
import net.pocketmine.server.Utils.*;

import com.nao20010128nao.PM_Metroid.R;
import net.pocketmine.server.*;

public class Save_On extends BaseAction {
	public Save_On(LogActivity act) {
		super(act);
	}

	@Override
	public void onClick(View p1) {
		// TODO: Implement this method
		new AppCompatAlertDialog.Builder(this,R.style.AppAlertDialog)
			.setMessage(R.string.auSure)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface di, int w) {
					getActivity().performSend("save-on");
				}
			})
			.setNegativeButton(android.R.string.cancel, Constant.BLANK_DIALOG_CLICK_LISTENER)
			.show();
	}

	@Override
	public int getViewId() {
		// TODO: Implement this method
		return R.id.saveon;
	}
}
