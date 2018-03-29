package messages;

public class Header {
	private byte[] header;
	private String headerString;
	
	//backup header
	public Header(String messageType, String version, String senderID, String fileID, String chunkNum, String replicationDegree) {
		String header = messageType + " " + version + " " + senderID + " " + fileID + " " + chunkNum + " " + replicationDegree;
		this.header = header.getBytes();
		this.headerString = header;
	}
	
	//restore header
	public Header(String messageType, String version, String senderID, String fileID, String chunkNum) {
		String header = messageType + " " + version + " " + senderID + " " + fileID + " " + chunkNum;
		this.header = header.getBytes();
		this.headerString = header;
	}
	
	//delete header
	public Header(String messageType, String version, String senderID, String fileID) {
		String header = messageType + " " + version + " " + senderID + " " + fileID;
		this.header = header.getBytes();
		this.headerString = header;
	}
	
	//full header received
	public Header(String header) {
		this.header = header.getBytes();
		this.headerString = header;
	}
	
	public byte[] getHeader() {
		return this.header;
	}
	
	public String getHeaderString() {
		return this.headerString;
	}
}
