package net.pocketmine.server.log.buttons;
import android.content.DialogInterface;
import android.view.View;
import com.nao20010128nao.PM_Metroid.R;
import com.nao20010128nao.Wisecraft.misc.compat.AppCompatAlertDialog;
import net.pocketmine.server.LogActivity;
import net.pocketmine.server.Utils.Constant;

public class Stop extends BaseAction {
	public Stop(LogActivity act) {
		super(act);
	}

	@Override
	public void onClick(View p1) {
		// TODO: Implement this method
		new AppCompatAlertDialog.Builder(this,R.style.AppAlertDialog)
			.setMessage(R.string.auSure)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface di, int w) {
					getActivity().performSend("stop");
				}
			})
			.setNegativeButton(android.R.string.cancel, Constant.BLANK_DIALOG_CLICK_LISTENER)
			.show();
	}

	@Override
	public int getViewId() {
		// TODO: Implement this method
		return R.id.stopServer;
	}
}
