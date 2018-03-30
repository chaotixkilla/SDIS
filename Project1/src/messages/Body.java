package messages;

public class Body {
	private byte[] body;
	
	public Body(String body) {
		this.body = body.getBytes();
	}
	
	public Body(byte[] body) {
		this.body = body;
	}
	
	public byte[] getBody() {
		return this.body;
	}
}
