package net.pocketmine.server.log.buttons;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.nao20010128nao.PM_Metroid.R;
import com.nao20010128nao.Wisecraft.misc.compat.AppCompatAlertDialog;
import net.pocketmine.server.LogActivity;

public class Tp extends BaseAction {
	EditText cmd;
	AlertDialog dialog;
	public Tp(LogActivity r) {
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
		return R.id.tp;
	}
	public View inflateDialogView() {
		View v=((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.log_command_continue, null, false);
		((TextView)v.findViewById(R.id.commandStarts)).setText("/tp ");
		cmd = (EditText)v.findViewById(R.id.command);
		((Button)v.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					// TODO: Implement this method
					getActivity().performSend("tp " + cmd.getText());

				}
			});
		return v;
	}
}
