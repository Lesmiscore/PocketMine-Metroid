package net.pocketmine.server.Utils;
import java.util.*;
import java.io.*;
import net.pocketmine.server.*;
import android.util.*;
import android.os.*;

public class Utils
{
	public static boolean isNullString(String s) {
		if (s == null) 
			return true;
		if ("".equals(s)) 
			return true;
		return false;
	}
	public static Map<String,String> readPropertiesFile() {
		Map<String,String> values = new LinkedHashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
														   ServerUtils.getDataDirectory() + "/server.properties"));
			try {
				String line;

				while ((line = reader.readLine()) != null) {
					if (!line.startsWith("#")) {
						int iof = line.indexOf("=");
						if (iof == -1) {
							Log.e("Configuration parser", "Invalid entry: "
								  + line);
						} else {
							String name = line.substring(0, iof);
							String value = line.substring(iof + 1);
							Log.d("Configuration parser", "[Parsing] Name: "
								  + name + " Value: " + value);
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
}
