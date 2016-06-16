package net.pocketmine.server.Utils;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import com.nao20010128nao.OTC.OrderTrustedMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.pocketmine.server.ServerUtils;

public class Utils
{
	public static boolean isNullString(String s) {
		if (s == null) 
			return true;
		if ("".equals(s)) 
			return true;
		return false;
	}
	public static Map<String,String> readPropertiesFile(File f){
		Map<String,String> values = new HashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			try {
				String line;

				while ((line = reader.readLine()) != null) {
					if (!line.startsWith("#")) {
						int iof = line.indexOf("=");
						if (iof == -1) {

						} else {
							String name = line.substring(0, iof);
							String value = line.substring(iof + 1);
							values.put(name, value);
						}
					}
				}
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			// File not found, it's all
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	public static Map<String,String> readPropertiesFile() {
		return readPropertiesFile(new File(ServerUtils.getDataDirectory(), "/server.properties"));
	}
	public static Map<String,List<String>> readExPropertiesFile(File f){
		Map<String,List<String>> values = new OrderTrustedMap<String, List<String>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			try {
				String line;

				while ((line = reader.readLine()) != null) {
					if (!line.startsWith("#")) {
						int iof = line.indexOf("=");
						if (iof == -1) {

						} else {
							String name = line.substring(0, iof);
							String value = line.substring(iof + 1);
							if(!values.containsKey(name))values.put(name,new ArrayList<String>());
							values.get(name).add(value);
						}
					}
				}
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			// File not found, it's all
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	public static void prepareLooper(){
		try {
			Looper.prepare();
		} catch (Throwable e) {
			
		}
	}
	public static String[] lines(String s) {
		try {
			BufferedReader br=new BufferedReader(new StringReader(s));
			List<String> tmp=new ArrayList<>(4);
			String line=null;
			while (null != (line = br.readLine()))tmp.add(line);
			return tmp.toArray(new String[tmp.size()]);
		} catch (Throwable e) {
			return Constant.EMPTY_STRING_ARRAY;
		}
	}
	public static View[] getChildren(ViewGroup vg){
		View[] views=new View[vg.getChildCount()];
		for(int i=0;i<vg.getChildCount();i++)views[i]=vg.getChildAt(i);
		return views;
	}
}
