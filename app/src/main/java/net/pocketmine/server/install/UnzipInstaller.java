package net.pocketmine.server.install;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipInstaller implements VersionInstallProvider
{
	public static final UnzipInstaller UNZIP_SRC_DIR_FOR_GITHUB=new UnzipInstaller("src/",true);
	public static final UnzipInstaller UNZIP_SRC_DIR=new UnzipInstaller("src/",false);
	String pathPrefix;
	boolean githubZip;
	public UnzipInstaller(String pathPrefix,boolean githubZip){
		this.pathPrefix=pathPrefix;
		this.githubZip=githubZip;
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
				String internal=githubZip?ze.getName().substring(ze.getName().indexOf("/")):ze.getName();
				if(internal.startsWith("/"))internal=internal.substring(1);
				if(internal.length()==0)continue;
				if(!"".equals(internal))if(!internal.startsWith(pathPrefix))continue;
				if(internal.endsWith("/")){
					new File(destDir,internal).mkdirs();
					continue;
				}
				Log.d("UnzipInstaller",internal);
				new File(destDir,internal).getParentFile().mkdirs();
				FileOutputStream out=null;
				try{
					out=new FileOutputStream(new File(destDir,internal));
					byte[] buffer = new byte[1024];
					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
				}catch(Throwable e){
					e.printStackTrace();
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
