package net.pocketmine.server.install;
import java.io.*;

public interface VersionInstallProvider
{
	public boolean install(File installer,File destDir) throws IOException;
}
