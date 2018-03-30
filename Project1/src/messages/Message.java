package messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class Message {
	private Header header;
	private Body body;
	private byte[] message;
	
	public Message(Header header, Body body) throws IOException {
		this.header = header;
		this.body = body;
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(header.getHeader(), 0, header.getHeader().length);
		outputStream.write(" \r\n\r\n".getBytes(), 0, " \r\n\r\n".length());
		outputStream.write(body.getBody(), 0, body.getBody().length);

		byte[] message = outputStream.toByteArray();
		this.message = message;
	}
	
	public Message(Header header) {
		this.header = header;
		this.body = null;
		this.message = header.getHeader();
	}
	
	public Message(String s) {
		this.message = s.getBytes();
	}
	
	public Message(DatagramPacket packet) {
		String received = new String(packet.getData(), 0, packet.getLength());
		
		String[] message = received.split(" \r\n\r\n");
		//System.out.println("Header: " + message[0]);
		//System.out.println("Body: " + message[1]);
		
		this.header = new Header(message[0]);
		if(this.body == null) {
			this.body = new Body(message[1]);
		}
	}
	
	public byte[] getMessage() {
		return this.message;
	}
	
	public Header getHeader() {
		return this.header;
	}
	
	public Body getBody() {
		return this.body;
	}
}
