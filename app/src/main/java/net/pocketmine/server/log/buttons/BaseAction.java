package net.pocketmine.server.log.buttons;
import android.content.ContextWrapper;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.nao20010128nao.PM_Metroid.R;
import net.pocketmine.server.LogActivity;

public abstract class BaseAction extends ContextWrapper implements OnClickListener {
	private LogActivity ra;
	public BaseAction(LogActivity act) {
		super(act);
		ra = act;
		act.findViewById(getViewId()).setOnClickListener(this);
	}
	public abstract int getViewId();
	public LogActivity getActivity() {
		return ra;
	}
	public void setCommandText(CharSequence cs) {
		((EditText)ra.findViewById(R.id.command)).setText(cs);
	}
	public String getResString(int id) {
		return getResources().getString(id);
	}
}
