package channels;

import java.io.IOException;
import interfaces.Server;

public class ControlChannel extends DefaultChannel {

	protected ControlChannel(String ip, String port, Server server) throws IOException {
		super(ip, port, server);
	}

}
