import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		BST a = new BST();
		a.addStopWord("hey");
		a.addStopWord("sky");
		a.load("test2.txt");
		Scanner userinput = new Scanner(System.in);
		int choice = 0;
		
		do {
			System.out.println("\nChoose from the following options...");
			System.out.println("1. Print the Binary Search Tree");
			System.out.println("2. Remove a word from Binary Search Tree");
			System.out.println("3. Find a word from Binary Search Tree");
			System.out.println("4. Exit the program.");
			choice = userinput.nextInt();
			
			switch(choice) {
			case 1:
				a.inOrder(a.getRoot());
				break;
			case 2:
				System.out.println("Please enter the word you are looking to remove.");
				String removeWord = userinput.next();
				a.remove(removeWord);
				System.out.println("\nBST after removing node.");
				a.inOrder(a.getRoot());
				break;
			case 3:
				System.out.println("Please enter the word you are looking for...");
				String targetword = userinput.next();
				a.search(targetword);
				break;
			case 4:
				System.out.println("Exiting program...");
				System.exit(0);
				break;
			default:
				System.out.println("Please enter a valid choice.");
				break;
			}
		} while(choice != 4);
		
	} //end main
	
}
