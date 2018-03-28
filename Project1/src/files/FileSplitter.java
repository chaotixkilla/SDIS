package files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSplitter {
	
	private FileObject file;
	private int replicationDegree;
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	public FileSplitter() {}
	public FileSplitter(FileObject file, int replicationDegree) {
		this.file = file;
		this.replicationDegree = replicationDegree;
	}
	
	public FileObject getFile() { return this.file; }
	public int getReplicationDegree() { return this.replicationDegree; }
	public ArrayList<Chunk> getChunks(){ return this.chunks; }
	
	public void setFile(FileObject file) { this.file = file; }
	public void setReplicationDegree(int replicationDegree) { this.replicationDegree = replicationDegree; }
	public void setChunks(ArrayList<Chunk> chunks) { this.chunks = chunks; }
	
	public boolean split() {
		
		//check if the maximum chunk size is a multiple of the total file size
		if((file.getSize() % 64000) == 0) {
			//last chunk will have size = 0
			int numberOfChunks = file.getSize() / 64000;
			int chunkSize = file.getSize() / numberOfChunks;
			
			for(int i = 0; i < numberOfChunks; i++) {
				chunks.add(new Chunk(file.getFileId(), i));
			}

			Byte[] dataBytes = new Byte[file.getData().length];
			
			for(int i = 0; i < file.getData().length; i++){
				dataBytes[i++] = file.getData()[i];
			}
			
			List<Byte> dataBytesList = Arrays.asList(dataBytes);
			
		    for (int start = 0; start < dataBytesList.size(); start += chunkSize) {
		        int end = Math.min(start + chunkSize, dataBytesList.size());
		        List<Byte> sublist = dataBytesList.subList(start, end);
		        System.out.println(sublist);
		    }
			
			
		    
		    /*
		    
		    public static ArrayList<byte[]> chunk_file(byte[] file){
		    	
		    	arrayList<byte[]> chunks = new ArrayList<byte[]>();
		    	
		    	int size = Chunk.CHUNK_SIZE;
		    	for(int i = 0; i*Chunk.CHUNK_SIZE < file.length; i++) {
		    		byte[] chunk = Arrays.copyOfRange(file, i*size, Math.min(file.length,  (i+1)*size));
		    		chuncks.add(chunk);
		    	}
		    	return chunks;
		    	
		    }
		    
		    */
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
			
		}
		System.out.print("Splitter.split() failed"); 
		return true;
	}
	
	
	
}
