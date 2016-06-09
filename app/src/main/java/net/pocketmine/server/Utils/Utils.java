package net.pocketmine.server.Utils;

public class Utils
{
	public static boolean isNullString(String s) {
		if (s == null) 
			return true;
		if ("".equals(s)) 
			return true;
		return false;
	}
}
