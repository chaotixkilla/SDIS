package messages;

public class Header {
	private byte[] header;
	
	public Header(String messageType, String version, String senderID, String fileID, String chunkNum, String replicationDegree) {
		String header = messageType + " " + version + " " + senderID + " " + fileID + " " + chunkNum + " " + replicationDegree;
		this.header = header.getBytes();
	}
	
	public byte[] getHeader() {
		return this.header;
	}
}
