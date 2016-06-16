package net.pocketmine.server.install;
import com.nao20010128nao.OTC.OrderTrustedSet;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
			String vers=null,api=null,codename=null,commit="00000000";
			BufferedReader br=null;
			try{
				br=new BufferedReader(new InputStreamReader(new BufferedInputStream(new URL("https://raw.githubusercontent.com/iTXTech/Genisys/master/src/pocketmine/PocketMine.php").openConnection().getInputStream())));
				String oneLine;
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
				}
			}finally{
				if(br!=null)br.close();
			}
			Document doc=Jsoup.connect("https://github.com/itxtech/genisys/commits/master").userAgent("Mozilla").get();
			Elements elms=doc.select("a");
			List<String> tmp=new ArrayList<>();
			for(Element el:elms){
				String href=el.attr("href");
				if(href.startsWith("/iTXTech/Genisys/commit/"))tmp.add(href.substring(href.lastIndexOf("/")+1));
			}
			tmp=new ArrayList<String>(new OrderTrustedSet<String>(tmp));
			if(tmp.size()!=0){
				commit=tmp.get(0).substring(0,8);
			}
			return lastVers=vers+" (API: "+api+") "+codename+" Commit "+commit;
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
		String lastVers;
		Date date;
		@Override
		public String getDownloadAddress()
		{
			// TODO: Implement this method
			return "https://github.com/ClearSkyTeam/ClearSky/archive/master.zip";
		}

		@Override
		public String getVersion() throws IOException
		{
			// https://raw.githubusercontent.com/ClearSkyTeam/ClearSky/master/src/pocketmine/PocketMine.php
			if(lastVers!=null)return lastVers;
			String vers=null,api=null,codename=null,commit="00000000";
			BufferedReader br=null;
			try{
				br=new BufferedReader(new InputStreamReader(new BufferedInputStream(new URL("https://raw.githubusercontent.com/ClearSkyTeam/ClearSky/master/src/pocketmine/PocketMine.php").openConnection().getInputStream())));
				String oneLine;
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
				}
			}finally{
				if(br!=null)br.close();
			}
			Document doc=Jsoup.connect("https://github.com/ClearSkyTeam/ClearSky/commits/master").userAgent("Mozilla").get();
			Elements elms=doc.select("a");
			List<String> tmp=new ArrayList<>();
			for(Element el:elms){
				String href=el.attr("href");
				if(href.startsWith("/ClearSkyTeam/ClearSky/commit/"))tmp.add(href.substring(href.lastIndexOf("/")+1));
			}
			tmp=new ArrayList<String>(new OrderTrustedSet<String>(tmp));
			if(tmp.size()!=0){
				commit=tmp.get(0).substring(0,8);
			}
			return lastVers=vers+" (API: "+api+") "+codename+" Commit "+commit;
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
			Document doc = Jsoup.connect("https://github.com/ClearSkyTeam/ClearSky"/*/commits/master"*/).userAgent("Mozilla").get();
			Elements elms_base = doc.select("time");
			OrderTrustedSet<String> elems=new OrderTrustedSet<String>();
			for(Element element:elms_base){
				elems.add(element.attr("datetime"));
			}
			ArrayList<String> elms=new ArrayList<String>(elems);
			if(elms.size()==0)return new Date(System.currentTimeMillis());
			//2016-06-11T10:08:20Z
			return date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(elms.get(0).replace('T',' ').replace("Z",""));
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
