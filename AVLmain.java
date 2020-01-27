import java.io.*;
import java.util.*;

public class AVLmain {
	


	static List<String> input; //used to store info from input file

	//main function to choose input file, create an AVL Tree, modify info, and report elapsed time
	public static void main(String[] args) throws IOException {
	
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter input file location: ");
		String file = sc.nextLine();
		AVLmain inp = new AVLmain();
		inp.load(file);
		AVLTree t = new AVLTree();
		double startTime = System.nanoTime();
		for(String str : input) {
			inp.operation(str, t);
		}
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		write(elapsed/1000.0 + " micro-sec");
	}
	
	//function used to write to output file
	 public static void write(String message) throws IOException { 
	      PrintWriter out = new PrintWriter(new FileWriter("output-avl.txt", true), true);
	      out.write(message);
	      out.close();
	    }
	
	 //imports info from input file
	public boolean load(String file) {
		input = new ArrayList<String>();
		Scanner filescanner;
		int x = 0;
		try {
			filescanner = new Scanner(new File(file));
			while(filescanner.hasNextLine()) {
				String inp = filescanner.nextLine();
				input.add(inp);
				x++;
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		if(input.size()==x)
			return true;
		else
			return false;
	}
	
	//modifies info from input file based on instructions
	public void operation(String str, AVLTree t) throws IOException {
		String[] oper = str.split(" ");
		if(oper[0].compareTo("IN")==0) {
			t.insert(Integer.parseInt(oper[1]));
		}
		if(oper[0].compareTo("MI")==0) {
			AVLNode n = t.min(t.root);
			write(n.key+"		//Minimum\n");
		}
		if(oper[0].compareTo("MA")==0) {
			AVLNode n = t.max(t.root);
			write(n.key+"		//Maximum\n");
		}
		if(oper[0].compareTo("TR")==0) {
			t.inorder(t.root);
			write("	//Traversal\n");
			
		}
		if(oper[0].compareTo("SR")==0) {
			int x = Integer.parseInt(oper[1]);
			boolean exists = t.search(t.root, x);
			write(exists+"		//Search\n");
		}
		if(oper[0].compareTo("SC")==0) {
			int x = Integer.parseInt(oper[1]);
			AVLNode succ = t.successor(t.searchKey(x, t.root));
			write(succ.key+"		//Successor of "+x+"\n");
		}
		if(oper[0].compareTo("PR")==0) {
			int x = Integer.parseInt(oper[1]);
			AVLNode pred = t.predecessor(t.searchKey(x, t.root));
			write(pred.key + "		//Predecessor of "+x+"\n");
		}
		if(oper[0].compareTo("SE")==0) {
			int x = Integer.parseInt(oper[1]);
			AVLNode select = t.select(t.root, x);
			write(select.key+"		//Select "+x+"\n");
		}
		if(oper[0].compareTo("RA")==0) {
			int x = t.rank(t.root, Integer.parseInt(oper[1]));
			write(x+"		//Rank "+oper[1]+"\n");
		}
			
	}
}
