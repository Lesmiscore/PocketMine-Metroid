package net.pocketmine.server.install;
import org.json.simple.*;
import java.io.*;
import java.net.*;

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
		private JSONObject jsonObject()throws IOException{
			if(cache!=null)return cache;
			JSONObject stableObj=cache=(JSONObject)JSONValue.parse(getPageContext("http://pocketmine.net/api/?channel=development"));
			return stableObj;
		}
	},
	GENISYS_LATEST{
		String lastVers;
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
						vers=oneLine.substring(oneLine.indexOf("\""),oneLine.lastIndexOf("\""));
					}
					if(oneLine.contains("const API_VERSION = ")){
						api=oneLine.substring(oneLine.indexOf("\""),oneLine.lastIndexOf("\""));
					}
					if(oneLine.contains("const CODENAME = ")){
						codename=oneLine.substring(oneLine.indexOf("\""),oneLine.lastIndexOf("\""));
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
			return UnzipInstaller.UNZIP_SRC_DIR;
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
			return UnzipInstaller.UNZIP_SRC_DIR;
		}
	};
	SoftwareKind(){
		
	}
	public abstract String getDownloadAddress()throws IOException;
	public abstract String getVersion() throws IOException;
	public abstract VersionInstallProvider getInstaller();
	
	private static String getPageContext(String url) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		StringBuilder sb = new StringBuilder();
		String str;
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		return sb.toString();
	}
}
