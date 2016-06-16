package net.pocketmine.server.Utils;
import android.content.DialogInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constant {
	public static final List ONE_LENGTH_NULL_LIST=Collections.unmodifiableList(Arrays.asList(new Object[1]));
	public static final List TEN_LENGTH_NULL_LIST=Collections.unmodifiableList(Arrays.asList(new Object[10]));
	public static final List ONE_HUNDRED_LENGTH_NULL_LIST=Collections.unmodifiableList(Arrays.asList(new Object[100]));
	public static final List ONE_THOUSAND_LENGTH_NULL_LIST=Collections.unmodifiableList(Arrays.asList(new Object[1000]));
	public static final String[] EMPTY_STRING_ARRAY=new String[0];
	public static final DialogInterface.OnClickListener BLANK_DIALOG_CLICK_LISTENER=new DialogInterface.OnClickListener(){public void onClick(DialogInterface di, int w) {}};
}
