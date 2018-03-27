package channels;

import java.io.IOException;
import interfaces.Server;

public class BackupChannel extends DefaultChannel {

	protected BackupChannel(String ip, String port, Server server) throws IOException {
		super(ip, port, server);
	}

}
