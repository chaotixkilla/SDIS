package utilities;

public class Pair<String, Integer> {

	private String left;
	private  Integer right;
	
	public Pair() {}
	public Pair(String left, Integer right) {
		this.left = left;
		this.right = right;
	}
	
	public String getLeft() { return this.left; }
	public Integer getRight() { return this.right; }
	
	public void setLeft(String left) { this.left = left; }
	public void setRight(Integer right) { this.right = right; }
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Pair)) return false;
		Pair pair = (Pair) obj;
		return(
			this.left.equals(pair.getLeft()) &&
			this.right.equals(pair.getRight())
				);	
	}
	
}
