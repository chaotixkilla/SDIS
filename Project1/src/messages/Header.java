package messages;

public class Header {
	private byte[] header;
	
	//backup header
	public Header(String messageType, String version, String senderID, String fileID, String chunkNum, String replicationDegree) {
		String header = messageType + " " + version + " " + senderID + " " + fileID + " " + chunkNum + " " + replicationDegree;
		this.header = header.getBytes();
	}
	
	//restore header
	public Header(String messageType, String version, String senderID, String fileID, String chunkNum) {
		String header = messageType + " " + version + " " + senderID + " " + fileID + " " + chunkNum;
		this.header = header.getBytes();
	}
	
	//delete header
	public Header(String messageType, String version, String senderID, String fileID) {
		String header = messageType + " " + version + " " + senderID + " " + fileID;
		this.header = header.getBytes();
	}
	
	public byte[] getHeader() {
		return this.header;
	}
}
