package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
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
	
	public String readFile(String path) { 
		
		String output = "";
		Charset encoding;
		
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			output = new String(encoded, Charset.defaultCharset());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public boolean split(File file) {
		
		try {
		
		BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		 
		String name = file.getName();
		String date = attr.creationTime().toString();
		String path = file.getAbsolutePath();
		String data = readFile(path);
		
		//check if the maximum chunk size is a multiple of the total file size
		if((data.length() % 64000) != 0) {
			//last chunk will have size = 0
			int numberOfChunks = data.length() / 64000;
			int chunkSize = data.length() / numberOfChunks;
			
			for(int i = 0; i < numberOfChunks; i++) {
				chunks.add(new Chunk(name, i));
			}

			Byte[] dataBytes = new Byte[data.length()];
			
			for(int i = 0; i < data.length(); i++){
				dataBytes[i++] = dataBytes[i];
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
		}
		catch (IOException e){
			e.printStackTrace();
			return false;
		}
		System.out.print("Splitter.split() failed"); 
		return true;
	}
	

}
