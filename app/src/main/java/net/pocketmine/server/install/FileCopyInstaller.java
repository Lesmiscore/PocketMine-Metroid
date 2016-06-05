package net.pocketmine.server.install;
import java.io.*;

public class FileCopyInstaller implements VersionInstallProvider
{
	public static final FileCopyInstaller POCKETMINE_MP_PHAR=new FileCopyInstaller("PocketMine-MP.phar");
	String fileName;
	public FileCopyInstaller(String fileName){
		this.fileName=fileName;
	}
	@Override
	public boolean install(File installer, File destDir)throws IOException {
		// TODO: Implement this method
		if(!destDir.exists()){
			destDir.mkdirs();
		}
		if(destDir.isDirectory()){
			destDir=new File(destDir,fileName);
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(installer);
			out = new FileOutputStream(destDir);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			return false;
		}finally{
			if(in!=null)
				in.close();
			if(out!=null)
				out.close();
		}
		return true;
	}
}
