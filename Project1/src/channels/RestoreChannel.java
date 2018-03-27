package channels;

import java.io.IOException;
import interfaces.Server;

public class RestoreChannel extends DefaultChannel {

	public RestoreChannel(String ip, String port, Server server) throws IOException {
		super(ip, port, server);
	}

}
