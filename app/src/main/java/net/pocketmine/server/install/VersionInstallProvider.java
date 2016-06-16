package net.pocketmine.server.install;
import java.io.File;
import java.io.IOException;

public interface VersionInstallProvider
{
	public boolean install(File installer,File destDir) throws IOException;
}
