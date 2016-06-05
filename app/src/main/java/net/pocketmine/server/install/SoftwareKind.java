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
	public SoftwareKind(){
		
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
