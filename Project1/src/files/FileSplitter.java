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

import utilities.Utils;

import static java.lang.Math.toIntExact;

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
	
	public ArrayList<byte[]> split(File file) {
		
		ArrayList<byte[]> chunks = new ArrayList<byte[]>();
		
		try {
		
		BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		 
		String name = file.getName();
		String date = attr.creationTime().toString();
		String path = file.getAbsolutePath();
		String data = readFile(path);
		
		System.out.println("\nFILE SIZE: " + file.length());
		
		int size = 64000;
		for(int i = 0; i*size < file.length(); i++) {
			byte[] chunk = Arrays.copyOfRange(data.getBytes(), i*size, toIntExact(Math.min(file.length(), (i+1)*size)));
			chunks.add(chunk);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	    return chunks;
	}
	
	public boolean fileSavior(ArrayList<byte[]> chunks, int peers, File file) {
		
		try {
			Utils util = new Utils();
			BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			String encodedId = util.sha256(file.getName() + "/" + attr.creationTime().toString() + "/" + file.getAbsolutePath());
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		for(int i = 0; i < peers; i++) {
			
		}
		return true;
	}

}
