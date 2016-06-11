package net.pocketmine.server.install;
import org.json.simple.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.*;
import org.jsoup.select.*;
import java.text.*;

public enum SoftwareKind
{
	POCKETMINE_STABLE{
		JSONObject cache;
		@Override
		public String getDownloadAddress()throws IOException
		{
			return (String)jsonObject().get("download_url");
		}

		@Override
		public String getVersion() throws IOException
		{
			// TODO: Implement this method
			return (String)jsonObject().get("version");
		}

		@Override
		public VersionInstallProvider getInstaller()
		{
			// TODO: Implement this method
			return FileCopyInstaller.POCKETMINE_MP_PHAR;
		}

		@Override
		public Date getReleaseDate() throws IOException
		{
			// TODO: Implement this method
			return new Date(1000*((Long)jsonObject().get("date")));
		}

		
		private JSONObject jsonObject()throws IOException{
			if(cache!=null)return cache;
			JSONObject stableObj=cache=(JSONObject)JSONValue.parse(getPageContext("http://pocketmine.net/api/?channel=stable"));
			return stableObj;
		}
	},
	POCKETMINE_BETA{
		JSONObject cache;
		@Override
		public String getDownloadAddress()throws IOException
		{
			return (String)jsonObject().get("download_url");
		}

		@Override
		public String getVersion() throws IOException
		{
			// TODO: Implement this method
			return (String)jsonObject().get("version");
		}
		
		@Override
		public VersionInstallProvider getInstaller()
		{
			// TODO: Implement this method
			return FileCopyInstaller.POCKETMINE_MP_PHAR;
		}
		
		@Override
		public Date getReleaseDate() throws IOException
		{
			// TODO: Implement this method
			return new Date(1000*((Long)jsonObject().get("date")));
		}
		
		
		private JSONObject jsonObject()throws IOException{
			if(cache!=null)return cache;
			JSONObject stableObj=cache=(JSONObject)JSONValue.parse(getPageContext("http://pocketmine.net/api/?channel=beta"));
			return stableObj;
		}
	},
	POCKETMINE_DEV{
		JSONObject cache;
		@Override
		public String getDownloadAddress()throws IOException
		{
			return (String)jsonObject().get("download_url");
		}

		@Override
		public String getVersion() throws IOException
		{
			// TODO: Implement this method
			return (String)jsonObject().get("version");
		}
		
		@Override
		public VersionInstallProvider getInstaller()
		{
			// TODO: Implement this method
			return FileCopyInstaller.POCKETMINE_MP_PHAR;
		}
		
		@Override
		public Date getReleaseDate() throws IOException
		{
			// TODO: Implement this method
			return new Date(1000*((Long)jsonObject().get("date")));
		}
		
		
		private JSONObject jsonObject()throws IOException{
			if(cache!=null)return cache;
			JSONObject stableObj=cache=(JSONObject)JSONValue.parse(getPageContext("http://pocketmine.net/api/?channel=development"));
			return stableObj;
		}
	},
	GENISYS_LATEST{
		String lastVers;
		Date date;
		@Override
		public String getDownloadAddress()
		{
			// TODO: Implement this method
			return "https://github.com/iTXTech/Genisys/archive/master.zip";
		}

		@Override
		public String getVersion() throws IOException
		{
			// https://raw.githubusercontent.com/iTXTech/Genisys/master/src/pocketmine/PocketMine.php
			if(lastVers!=null)return lastVers;
			BufferedReader br=null;
			try{
				br=new BufferedReader(new InputStreamReader(new BufferedInputStream(new URL("https://raw.githubusercontent.com/iTXTech/Genisys/master/src/pocketmine/PocketMine.php").openConnection().getInputStream())));
				String oneLine,vers=null,api=null,codename=null;
				while(null!=(oneLine=br.readLine())){
					if(oneLine.contains("const VERSION = ")){
						vers=oneLine.substring(oneLine.indexOf("\"")+1,oneLine.lastIndexOf("\""));
					}
					if(oneLine.contains("const API_VERSION = ")){
						api=oneLine.substring(oneLine.indexOf("\"")+1,oneLine.lastIndexOf("\""));
					}
					if(oneLine.contains("const CODENAME = ")){
						codename=oneLine.substring(oneLine.indexOf("\"")+1,oneLine.lastIndexOf("\""));
					}
					lastVers=vers+" (API: "+api+") "+codename;
				}
			}finally{
				if(br!=null)br.close();
			}
			return lastVers;
		}
		
		@Override
		public VersionInstallProvider getInstaller()
		{
			// TODO: Implement this method
			return UnzipInstaller.UNZIP_SRC_DIR_FOR_GITHUB;
		}

		@Override
		public Date getReleaseDate() throws IOException,ParseException
		{
			// TODO: Implement this method
			if(date!=null)return date;
			Document doc=Jsoup.connect("https://gitlab.com/itxtech/genisys").userAgent("PocketMineMetroid").get();
			Elements elms=doc.select("div>div>div>div.content>div.clearfix>div.content-block.second-block.white>div.container-fluid.container-limited>div.project-last-commit>time.time_ago.js-timeago");
			if(elms.size()==0)return new Date(System.currentTimeMillis());
			//2016-06-11T10:08:20Z
			date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(elms.get(0).attr("datetime").replace('T',' ').replace("Z",""));
			return date;
		}
	},
	CLEARSKY_LATEST{
		@Override
		public String getDownloadAddress()
		{
			// TODO: Implement this method
			return null;
		}

		@Override
		public String getVersion() throws IOException
		{
			// TODO: Implement this method
			return null;
		}
		
		@Override
		public VersionInstallProvider getInstaller()
		{
			// TODO: Implement this method
			return UnzipInstaller.UNZIP_SRC_DIR_FOR_GITHUB;
		}

		@Override
		public Date getReleaseDate() throws IOException
		{
			// TODO: Implement this method
			return null;
		}
	};
	SoftwareKind(){
		
	}
	public abstract String getDownloadAddress()throws IOException;
	public abstract String getVersion() throws IOException;
	public abstract VersionInstallProvider getInstaller();
	public abstract Date getReleaseDate()throws IOException,ParseException;
	
	private static String getPageContext(String url) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		StringWriter sb = new StringWriter();
		char[] str=new char[1000];int r;
		while ((r=in.read(str)) != -1) {
			sb.write(str,0,r);
		}
		in.close();
		return sb.toString();
	}
}
