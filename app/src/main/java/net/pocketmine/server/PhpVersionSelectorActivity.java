package net.pocketmine.server;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.nao20010128nao.Wisecraft.misc.compat.AppCompatListActivity;
import com.nao20010128nao.Wisecraft.misc.compat.R;
import java.util.ArrayList;
import java.util.List;

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
		getListView().setOnItemClickListener(pva);
	}
	
	class PhpVersionAdapter extends ArrayAdapter<PhpVersionEntry> implements ListView.OnItemClickListener{
		public PhpVersionAdapter(){
			super(PhpVersionSelectorActivity.this,0,versions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO: Implement this method
			View v=getLayoutInflater().inflate(com.nao20010128nao.PM_Metroid.R.layout.text_view_only,null);
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
