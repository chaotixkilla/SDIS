package files;
import utilities.Pair;
import java.util.ArrayList;

public class Chunk {
	
	private int maxSize; //default = 64000 Byte
	
	private Pair<String, Integer> id = new Pair();
	private String path;
	private int replicationDegree; //number of peers backing up the chunk
	private byte[] data;
	private ArrayList<Integer> peers = new ArrayList();

	
	public Chunk(String fileId, int chunkNo) {
		Integer no = new Integer(chunkNo);
		this.id = new Pair(fileId, chunkNo);
	}
	
	public Chunk(String fileId, int chunkNo, byte[] data) {
		Integer no = new Integer(chunkNo);
		this.id = new Pair(fileId, chunkNo);
		this.data = data;
	}
	
	public Chunk(String fileId, int chunkNo, int replicationDegree) {
		Integer no = new Integer(chunkNo);
		this.id = new Pair(fileId, chunkNo);
		this.replicationDegree = replicationDegree;
	}
	
	public Chunk(String fileId, int chunkNo,  int replicationDegree, String path) {
		Integer no = new Integer(chunkNo);
		this.id = new Pair(fileId, chunkNo);
		this.path = path;
		this.replicationDegree = replicationDegree;
		this.data = data;
	}
	
	public Chunk(String fileId, int chunkNo, int replicationDegree, String path, byte[] data) {
		Integer no = new Integer(chunkNo);
		this.id = new Pair(fileId, chunkNo);
		this.path = path;
		this.replicationDegree = replicationDegree;
		this.data = data;
	}
	
	public int getMaxSize() { return this.maxSize; }
	public String getFileId() { return this.id.getLeft(); }
	public int getChunkNo() { return this.id.getRight().intValue(); }
	public int getReplicationDegree() { return this.replicationDegree; }
	public String getPath() { return this.path; }
	public byte[] getData() { return this.data; }
	public ArrayList<Integer> getPeers() { return this.peers; }
	
	public void setMaxSize(int maxSize) { this.maxSize = maxSize; }
	public void setFileId(String fileId) { this.id.setLeft(fileId); }
	public void setChunkNo(int chunkNo) {
		Integer no = new Integer(chunkNo);
		this.id.setRight(no); 
		}
	public void setReplicationDegree(int replicationDegree) { this.replicationDegree = replicationDegree; }
	public void setPath(String path) { this.path = path; }
	public void setData(byte[] data) { this.data = data; }
	
	public boolean addPeer(Integer peer) {
		if(this.peers.contains(peer)) return false;
		this.peers.add(peer);
		this.replicationDegree += 1;
		return true;
	}
	
	public boolean removePeer(Integer peer) {
		if(this.peers.indexOf(peer) == -1) return false;
		this.peers.remove(this.peers.indexOf(peer));
		this.replicationDegree -= 1;
		return true;
	}
	
	
}
