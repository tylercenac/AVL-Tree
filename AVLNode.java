
public class AVLNode {
	int key; //value of node
	int bf; //balance factor of node
	AVLNode left; //node's left child
	AVLNode right; //node's right child
	AVLNode parent; //node's parent
	boolean heightInc; //boolean value that stores whether the height of a node increases or not
	int height; //height of a node

	//constructs avl node using a number and its left and right children
	public AVLNode(int k, AVLNode l, AVLNode r) {
		key = k;
		left = l;
		right = r;
		height = 0;
	}

	//constructs avl node from just a num
	public AVLNode(int num) {
		this(num, null, null);
	}

	//converts node's key to string
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Key: " + key);
		return sb.toString();
	}
}
