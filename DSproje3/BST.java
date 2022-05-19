import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BST implements WordCounter {
	TreeNode root = null;
	private double meanFrequency; 
	private int distinct;
	private int counter;
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	} 
	private boolean stop = false;
	private ArrayList<String> stopWords = new ArrayList<String>();

	//add node function: if the BST is empty, first node is added to the root.
	//Else if there tree is not empty, the new node will be compared to the current
	//node. If the new node is less than the current node, then the new node will
	//get down the left branch. If the new node is greater than the current node,
	//then the new node will go down the right branch. This will continue recursively
	//until it finds a null node, and that is where it will be added.

	
	private void insertAsRoot(String w, int count)
	{
		WordFreq wf = new WordFreq(w, count);
		TreeNode tn = new TreeNode(wf);
		tn.setLeft(null);
		tn.setRight(getRoot());
		setRoot(tn);
	}
	@Override
	public void insert(String w) {
		stop = false;
		WordFreq werd = new WordFreq(w, 1);
		TreeNode werdNode = new TreeNode(werd);
		for(int i = 0; i < stopWords.size(); i++) {
			if(stopWords.get(i).contains(w)) {
				stop = true;
			}
		}
		if(stop == false) {
			add(werdNode, getRoot());
		}
	}
	private void add(TreeNode newNode, TreeNode rootNode) {
		TreeNode current = rootNode;
		if(this.root == null) {
			this.root = newNode; 
			distinct ++;
			return;
		}
		
		else if(current.getData().compareTo(newNode.getData()) > 0) {
			if(current.getLeft() == null) {
				current.setLeft(newNode);
			}
			else {
				add(newNode, current.getLeft());
			}
		}
		
		else if(current.getData().compareTo(newNode.getData()) < 0) {
			if(current.getRight() == null) {
				current.setRight(newNode);
			}
			else {
				add(newNode, current.getRight());
			}
		}
		
		else if(current.getData().compareTo(newNode.getData()) == 0) {
			WordFreq data = current.getData();
			int count = data.getWordCount();
			count++;
			data.setWordCount(count);
		}
	} //end add


	//searches the BST, if the target node is found, return true, otherwise,
	//go down the BST and search the children nodes. If the target is greater
	//than the current node, then search the right node. If the target is smaller
	//than the current node, search the left node. If the whole tree is searched
	//and node is not found, return false. 
	public boolean find(String target, TreeNode current) {
		TreeNode returnNode = null;
		if(current == null) {
			System.out.println("Node not found.");
			return false;
		}
		String currentData = current.getData().key();
		if(currentData.equals(target.toLowerCase())) {
			System.out.println("Found node! " +current.getData());
			return true;
		}
		else if(currentData.compareTo(target.toLowerCase()) > 0) {
			find(target, current.getLeft());
		}
		else if(currentData.compareTo(target.toLowerCase()) < 0) {
			find(target, current.getRight());
		}
		return false;
	} //end find

	@Override
	public WordFreq search(String w) {
		// TODO Auto-generated method stub
		TreeNode toBeSearched = findNode(w, getRoot());

		if(toBeSearched.getData().getWordCount() > getMeanFrequency())	
		{
			int keepFreq = toBeSearched.getData().getWordCount();
			remove(toBeSearched.getData().key());
			insertAsRoot(toBeSearched.getData().key(), keepFreq); 
		}

		return toBeSearched.getData();
	}
	public TreeNode findNode(String target, TreeNode current) {
		TreeNode returnNode = null;

		if(current == null) {
			System.out.println("Node not found.");
			returnNode = null;
			return returnNode;
		}
		String currentData = current.getData().key();
		if(currentData.compareTo(target) == 0) {
			returnNode = current;
			return returnNode;
		}
		else if(currentData.compareTo(target.toLowerCase()) > 0) {
			returnNode = findNode(target, current.left);
		}
		else if(currentData.compareTo(target.toLowerCase()) < 0) {
			returnNode = findNode(target, current.right);
		}
		return returnNode;
	} //end findNode

	//this method traverses through the BST, it checks the left node to see if it matches
	//the target. if it does, it returns the current node. if not, then it checks the right
	//node to see if it matches the target. if it does, it returns the current node. if none
	//of the conditions are met, it goes down the left subtree and repeats. then right subtree
	//and repeats until target is found. 
	public TreeNode findParent(String target, TreeNode current) {
		TreeNode returnNode = null; 
		if(current == null) {
			return returnNode;
		}
		//		System.out.println("**" + current + " L:" + current.getLeft() + " R:" + current.getRight());
		if(current.getData().key().compareTo(target) == 0){
			//			System.out.println("Found it!!" + current);
			return current; 
		}

		if(current.getLeft() != null) {
			int compareResult = current.getData().key().compareTo(target) ; 
			if(current.getLeft().getData().key().compareTo(target) == 0) {
				return current;
			}
			else if(compareResult > 0) {
				returnNode = findParent(target, current.left);  /// changing to current.left was .right 
			}
			else if(compareResult < 0) {
				returnNode = findParent(target, current.right);				
			}
		}

		if(current.getRight() != null) {
			int compareRight = current.getData().key().compareTo(target) ; 

			if(compareRight < 0) {
				returnNode = findParent(target, current.right);
			}
			if(compareRight > 0) {
				returnNode = findParent(target, current.left);
			}
			if(current.getRight().getData().key().compareTo(target) == 0) {
				return current;
			}
		}

		return returnNode;
	} //end find parent


	//so long as getLeft() does not equal null, the method will recursively
	//call itself to keep getting left. if getLeft() is null, then the current
	//node is the minimum.
	public TreeNode findMin(TreeNode current) {
		if(current == null) {
			System.out.println("Root is null.");
			return null;
		}
		if(current.getLeft() == null) {
			return current;
		}
		else{
			return findMin(current.getLeft());
		}
	} //end find min


	//so long as getRight() does not equal null, the method will recursively
	//call itself to keep getting right. if getRight() is null, then the current
	//node is the maximum.
	public TreeNode findMax(TreeNode current) {
		if(current == null) {
			System.out.println("Root is null.");
			return null;
		}
		if(current.getRight() == null) {
			return current;
		}
		else {
			return findMax(current.getRight());
		}
	} //end find Max


	public void remove(String target) {
		distinct --;
		TreeNode current = getRoot();
		TreeNode removeNode = findNode(target, this.root);
		//		System.out.println("Remove node: " +removeNode.getData());
		//				System.out.println("Root: " +root);
		TreeNode parentNode = findParent(removeNode.getData().key(), root);
		//		System.out.println("Parent node:" +parentNode.getData());
		if(removeNode.getRight() == null && removeNode.getLeft() == null) {
			if(removeNode.getData().key().compareTo(parentNode.getData().key()) < 0) {
				//				System.out.println("no child left");
				parentNode.setLeft(null);
			}
			else if(removeNode.getData().key().compareTo(parentNode.getData().key()) > 0) {
				//				System.out.println("no child right");
				parentNode.setRight(null);
			}
		} //end remove if leaf node

		else if(removeNode.getRight() != null && removeNode.getLeft() != null) {
			TreeNode minNode = findMin(removeNode.right);
			//			System.out.println("Min node: " +minNode);
			TreeNode minParent = findParent(minNode.getData().key(), root);
			//			System.out.println("Min parent: " +minParent);
			removeNode.setData(minNode.getData());
			//			System.out.println("removeNode: " +removeNode.getData());
			if(minNode.getData().key().compareTo(minParent.getData().key()) > 0) {
				//				System.out.println("minParent setting right: " +minParent.getRight());
				minParent.setRight(null);
			}
			if(minNode.getData().key().compareTo(minParent.getData().key()) < 0) {
				//				System.out.println("minParent setting left: " +minParent.getLeft());
				minParent.setLeft(null);
			}
			if(minNode.getData().key().compareTo(minParent.getData().key()) == 0) {
				//				System.out.println("equalsequalsequals");
				minParent.setRight(null);
			}
		} //end remove if two childs


		//begin remove if node has one child
		else if(removeNode.getData().key().compareTo(parentNode.getData().key()) > 0 && removeNode.getRight() != null) {
			//			System.out.println("setting right: " +removeNode.right);
			parentNode.right = removeNode.right;
		}
		else if(removeNode.getData().key().compareTo(parentNode.getData().key()) > 0 && removeNode.getLeft() != null) {
			//			System.out.println("setting left:" +removeNode.left);
			parentNode.right = removeNode.left;
		}

		else if(removeNode.getData().key().compareTo(parentNode.getData().key()) < 0 && removeNode.getRight() != null) {
			//			System.out.println("setting right: " +removeNode.right);
			parentNode.left = removeNode.right;
		}
		else if(removeNode.getData().key().compareTo(parentNode.getData().key()) < 0 && removeNode.getLeft() != null) {
			//			System.out.println("setting left:" +removeNode.left);
			parentNode.left = removeNode.left;
		} //end remove if node has one child


		else {
			System.out.println("Could not find node to be removed.");
			distinct ++;
		}
	} // end remove

 

	
	@Override
	public void load(String filename) {
		String space = " ";
		String regex = "[.,;/]";
		Scanner scan;
		try {
			scan = new Scanner(new File(filename));
			//each time a new word is scanned in, it is checked with the ArrayList of stop words,
			//if the word is found in the stop words ArrayList, it will not be added to the BST
			while(scan.hasNext()) { 
				String nextWord = scan.next().toLowerCase().replaceAll(regex, space).trim();
				insert(nextWord); 
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	@Override
	public int getTotalWords() {
		// TODO Auto-generated method stub
		counter = 0;
		countTotalWordsInOrder(getRoot());
		return counter;
	}
	private void countTotalWordsInOrder(TreeNode current) {
		if(current == null) {
			return;
		}
		countTotalWordsInOrder(current.getLeft());
		counter += current.getData().getWordCount();
		countTotalWordsInOrder(current.getRight());
	} //end inOrder

	@Override
	public int getDistinctWords() { 
		return distinct;//to run in O(1)
	}
	 
	@Override
	public int getFrequency(String w) {  
		if(!find(w, getRoot()))
		{
			return 0;
		}
		return findNode(w, getRoot()).getData().getWordCount(); 
	}

	private int maxFreq = -1;
	private WordFreq winningFreq = null;

	public void inOrderGetMaximumFrequency(TreeNode current) {
		if(current == null) {
			return;
		}
		inOrder(current.getLeft());  
		if(current.getData().getWordCount() > maxFreq) 
		{
			maxFreq = current.getData().getWordCount();
			winningFreq = current.getData();
		}
		inOrder(current.getRight());
	} //end inOrder

	@Override
	public WordFreq getMaximumFrequency() { 
		maxFreq = -1;
		inOrderGetMaximumFrequency(getRoot());
		return winningFreq; 
	}


	@Override
	public double getMeanFrequency() {
		meanFrequency = 0;
		counter = 0;
		calculateMeanFrequency(getRoot());
		meanFrequency = meanFrequency / counter; 
		return meanFrequency;
	}
	private void calculateMeanFrequency(TreeNode current) {
		if(current == null) {
			return;
		}
		calculateMeanFrequency(current.getLeft());
		meanFrequency += current.getData().getWordCount();
		counter ++;
		calculateMeanFrequency(current.getRight());
	} //end inOrder
	public void inOrder(TreeNode current) {
		if(current == null) {
			return;
		}
		inOrder(current.getLeft()); 
			System.out.println(current.getData()); 
		inOrder(current.getRight());
	} //end inOrder
	@Override
	public void addStopWord(String w) {
		stopWords.add(w);
		
	}
	@Override
	public void removeStopWord(String w) {
		stopWords.remove(w);  
	}
	@Override
	public void printΤreeAlphabetically(PrintStream stream) {
		inOrder(getRoot());  
	}
	@Override
	public void printΤreeByFrequency(PrintStream stream) {
		// TODO Auto-generated method stub
		
	}
}