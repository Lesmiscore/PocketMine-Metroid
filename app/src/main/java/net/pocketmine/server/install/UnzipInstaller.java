package net.pocketmine.server.install;
import java.io.*;
import java.util.zip.*;

public class UnzipInstaller implements VersionInstallProvider
{
	public static final UnzipInstaller UNZIP_SRC_DIR=new UnzipInstaller("src/");
	String pathPrefix;
	public UnzipInstaller(String pathPrefix){
		this.pathPrefix=pathPrefix;
	}
	@Override
	public boolean install(File installer, File destDir) throws IOException {
		// TODO: Implement this method
		if(!destDir.exists()){
			destDir.mkdirs();
		}
		if(destDir.isFile()){
			return false;
		}
		ZipInputStream in = null;
		try {
			in = new ZipInputStream(new BufferedInputStream(new FileInputStream(installer)));
			ZipEntry ze=null;
			while(null!=(ze=in.getNextEntry())){
				if(!"".equals(ze.getName()))if(!ze.getName().startsWith(pathPrefix))continue;
				new File(destDir,ze.getName()).getParentFile().mkdirs();
				FileOutputStream out=null;
				try{
					out=new FileOutputStream(new File(destDir,ze.getName()));
					byte[] buffer = new byte[1024];
					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
				}finally{
					if(out!=null)out.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(in!=null)
				in.close();
		}
		return true;
	}
}
