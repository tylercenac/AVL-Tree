import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AVLTree {
	public AVLNode root;

	//construct avl tree
	public AVLTree() {
		root = null;
	}
	
	//writes info to ouput file
	public static void write(String message) throws IOException { 
	      PrintWriter out = new PrintWriter(new FileWriter("output-avl.txt", true), true);
	      out.write(message);
	      out.close();
	    }

	//returns height of a node
	public int height(AVLNode n) {
		if (n.left == null && n.right == null) {
			return 0;
		} else if (n.left == null) {
			return 1 + height(n.right);
		} else if (n.right == null) {
			return 1 + height(n.left);
		} else {
			return 1 + maximum(height(n.left), height(n.right));
		}
	}

	//returns the greater of 2 numbers
	public int maximum(int x, int y) {
		if (x >= y) {
			return x;
		} else {
			return y;
		}
	}

	//returns balance factor of a node
	public void getBalanceFactor(AVLNode b) {
		b.bf = height(b.left) - height(b.right);
	}

	
	//calls to insert a node from a num
	public void insert(int key) {
		root = insert(key, root);
	}

	//inserts node and balances avl tree
	public AVLNode insert(int key, AVLNode t) {
		if (t == null) {
			t = new AVLNode(key, null, null);
		} else if (key < t.key) {
			AVLNode p = t;
			t.left = insert(key, t.left);
			t.left.parent = p;
			if (t.bf == 2) {
				if (key < t.left.key) {
					t = rightRotate(t);
				} else
					t = lrRotate(t);
			}
		} else if (key > t.key) {
			AVLNode p = t;
			t.right = insert(key, t.right);
			t.right.parent = p;
			if (t.bf == 2) {
				if (key > t.right.key) {
					t = leftRotate(t);
				} else
					t = rlRotate(t);
			}
		} else
			t.height = maximum(height(t.left), height(t.right));
		return t;
	}

	//swaps two nodes
	public void transplant(AVLNode x, AVLNode y) {
		if (x.parent == null) {
			x = y;
		} else if (x.parent.left == x) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		if (y != null) {
			y.parent = x.parent;
		}
	}

	//rotates avl tree to the right
	public AVLNode rightRotate(AVLNode x) {
		AVLNode y = x.left;
		transplant(y, y.right);
		transplant(x, y);
		y.right = x;
		x.parent = y;
		return y;
	}
	//rotates avl tree to the left
	public AVLNode leftRotate(AVLNode x) {
		AVLNode y = x.right;
		transplant(y, y.left);
		transplant(x, y);
		y.left = x;
		x.parent = y;
		return y;
	}
	//rotates avl tree to the left then the right
	public AVLNode lrRotate(AVLNode x) {
		leftRotate(x.left);
		return rightRotate(x);
	}
	//rotates avl tree to the right then the left
	public AVLNode rlRotate(AVLNode x) {
		rightRotate(x.right);
		return leftRotate(x);
	}

	//returns minimum node in avl tree
	public AVLNode min(AVLNode x) {
		while (x.left != null) {
			x = x.left;
		}
		return x;
	}

	//returns maximum node in avl tree
	public AVLNode max(AVLNode x) {
		while (x.right != null) {
			x = x.right;
		}
		return x;
	}
	
	//select ith element in avl tree
	public AVLNode select(AVLNode x, int i) {
		if (x == null) {
			return null;
		}
		if (size(x.left) >= i) {
			return select(x.left, i);
		}
		if (size(x.left) + 1 == i) {
			return x;
		}
		return select(x.right, i - 1 - (size(x.left)));
	}

	
	//returns bool value for if int k is in avl tree
	public boolean search(AVLNode x, int k) {
		if (x == null) {
			return false;
		}
		if (x.key == k) {
			return true;
		}
		if (x.key > k) {
			return search(x.left, k);
		} else {
			return search(x.right, k);
		}
	}

	//searches for avl node
	public AVLNode searchKey(int i, AVLNode x) {

		if (i > x.key) {
			return searchKey(i, x.right);
		}
		if (i < x.key) {
			return searchKey(i, x.left);
		}
		if (i == x.key) {
			return x;
		}
		return null;

	}

	//returns successor of avl node
	public AVLNode successor(AVLNode x) {
		if (x.right != null) {
			AVLNode y = x.right;
			while (y.left != null) {
				y = y.left;
			}
			return y;
		} else {
			AVLNode y = x.parent;
			while (y != null && x == y.right) {
				x = y;
				y = y.parent;
			}
			return y;
		}
	}
	
	//returns rank of avl node
	public int rank(AVLNode x, int k) {
		if (x == null) {
			return 0;
		}
		if (k < x.key) {
			return rank(x.left, k);
		}
		if (k == x.key) {
			return size(x.left) + 1;
		}
		return size(x.left) + 1 + rank(x.right, k);
	}

	//returns predecessor of avl node
	public AVLNode predecessor(AVLNode x) {
		if (x.left != null) {
			AVLNode y = x.left;
			while (y.right != null) {
				y = y.right;
			}
			return y;
		}
		AVLNode y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}
		return y;

	}
	
	//converts root node key to string
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Root: " + root.key);
		return sb.toString();

	}
	
	//inorder traversal of avl tree
	public void inorder(AVLNode x) throws IOException {

		if (x == null) {
			return;
		}
		inorder(x.left);
		write(x.key + ", ");
		inorder(x.right);
	}

	
	//returns size of avl node
	public int size(AVLNode x) {
		if(x == null)
			return 0;
		else
			return(size(x.left) + size(x.right) + 1);
	}

	

	
}
