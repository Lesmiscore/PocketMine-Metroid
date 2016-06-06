package net.pocketmine.server;
import android.support.v7.app.*;
import android.os.*;
import com.nao20010128nao.Wisecraft.misc.compat.*;
import java.util.*;
import android.widget.*;
import android.view.*;
import android.content.*;

public class PhpVersionSelectorActivity extends AppCompatListActivity {
	List<PhpVersionEntry> versions=new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		PhpVersionAdapter pva=new PhpVersionAdapter();
		setListAdapter(pva);
		pva.add(new PhpVersionEntry("PHP 5","data.zip"));
		pva.add(new PhpVersionEntry("PHP 7","php7.zip"));
		pva.add(new PhpVersionEntry("PHP 5 ARMv6(Experimental)","bin.5.armv6.zip"));
		pva.add(new PhpVersionEntry("PHP 5 ARMv7(Experimental)","bin.5.armv7.zip"));
		pva.add(new PhpVersionEntry("PHP 7 ARMv6(Experimental)","bin.7.armv6.zip"));
		pva.add(new PhpVersionEntry("PHP 7 ARMv7(Experimental)","bin.7.armv7.zip"));
		pva.add(new PhpVersionEntry("PHP 7 nao20010128nao's rebuilt(Experimental)","php_7_rebuilt.zip"));
		getListView().setOnItemClickListener(pva);
	}
	
	class PhpVersionAdapter extends ArrayAdapter<PhpVersionEntry> implements ListView.OnItemClickListener{
		public PhpVersionAdapter(){
			super(PhpVersionSelectorActivity.this,0,versions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO: Implement this method
			View v=getLayoutInflater().inflate(R.layout.text_view_only,null);
			((TextView)v.findViewById(R.id.text)).setText(getItem(position).version);
			return v;
		}

		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
			// TODO: Implement this method
			startActivity(new Intent(PhpVersionSelectorActivity.this,InstallActivity.class).putExtra("file",getItem(p3).file).putExtra("reinst",getIntent().getBooleanExtra("reinst",false)));
			finish();
		}
	}
	class PhpVersionEntry{
		public PhpVersionEntry(String ver,String f){version=ver;file=f;}
		public final String version,file;
	}
}
