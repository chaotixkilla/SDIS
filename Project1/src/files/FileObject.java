package files;

import utilities.Utils;

public class FileObject {

	private String fileId;
	private String encodedFileId;
	private String path;
	private int size;
	private String creationDate;
	private byte[] data;
	
	public FileObject() {}
	public FileObject(String fileId, String path, int size, String creationDate, byte[] data) {
		this.fileId = fileId;
		this.path = path;
		this.size = size;
		this.creationDate = creationDate;
		this.data = data;
	}
	public FileObject(String fileId, String path, String creationDate, byte[] data) {
		this.fileId = fileId;
		this.path = path;
		this.creationDate = creationDate;
		this.data = data;
	}
	public FileObject(String fileId, String path, String creationDate) {
		this.fileId = fileId;
		this.path = path;
		this.creationDate = creationDate;
	}
	public FileObject(String fileId, String path) {
		this.fileId = fileId;
		this.path = path;
	}
	public FileObject(String fileId) {
		this.fileId = fileId;
	}
	
	public String getFileId() { return this.fileId; }
	public String getEncodedFileId() { return this.encodedFileId; }
	public String getPath() { return this.path; }
	public int getSize() { return this.size; }
	public String getCreationDate() { return this.creationDate; }
	public byte[] getData() { return this.data; }
	
	public void setFileId(String fileId) { this.fileId = fileId; }
	public void setPath(String path) { this.path = path; }
	public void setSize(int size) { this.size = size; }
	public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
	public void setData(byte[] data) { this.data = data; }
	
	public void encodeSHA256() {
		
		String newFileId = this.fileId + "/" + this.path + "/" + this.creationDate;
		Utils util = new Utils();
		
		this.encodedFileId = util.sha256(newFileId);
	}
	
}
