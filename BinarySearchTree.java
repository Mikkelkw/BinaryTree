/**
 * Binary search tree implementation.
 * 
 * We do not allow duplicates.
 * 
 * @author Greg Gagne
 * 
 * Mikkel Klingenberg Wahl
 * 
 */
import java.util.Iterator;


import bridges.base.BSTElement;
import bridges.base.BinTreeElement;

public class BinarySearchTree <K extends Comparable<? super K>> implements SearchTreeInterface<K>, Iterable<K>
{
	// the root of the binary search tree
	private BSTElement<K, String> root;

	/**
	 * Create an empty binary search tree
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * This method has nothing to do with a binary search tree,
	 * but is necessary to provide the bridges visualization.
	 */
	public BSTElement<K, String> getRoot() {
		return root;
	}

	public boolean isEmpty() {
		if(root == null) {
			return true;
		}else {

		return false;
		}
	}

	/**
	 * Solution that uses recursive helper method.
	 * We disallow duplicate elements in the tree. 
	 */
	public K add(K key) {
		if (contains(key))
			return null;
		else {
			root = add(key, root);

			return key;
		}
	}


	/**
	 * private helper method for adding a node to the binary search tree
	 * @param key
	 * @param subtree
	 * @return the root of the tree
	 */
	private BSTElement<K, String> add(K key, BSTElement<K,String> subtree) {
		if (subtree == null) {
			// we have found the spot for the addition

			// create the new node
			// parameters are (1) label (2) key (3) empty string [not used]
			BSTElement<K, String> newNode = new BSTElement<K, String>(key.toString(), key, "");

			// we also set the color of a new node to red
			newNode.getVisualizer().setColor("red");

			return newNode;
		}

		int direction = key.compareTo(subtree.getKey());

		if (direction < 0) {
			subtree.setLeft( add(key, subtree.getLeft()) );
		}
		else if (direction > 0) {
			subtree.setRight( add(key, subtree.getRight()) );
		}

		return subtree;
	}


	/**
	 * Non-recursive algorithm for addition
	 * This only serves the purpose of demonstrating the
	 * differences between the recursive and non-recursive approaches.
	 */
	/*
	public K add(K key) {
		// we disallow duplicates
		if (contains(key))
			return null;

		// create the new node
		// parameters are (1) label (2) key (3) null [not used]
		BSTElement<K, String> newNode = new BSTElement<K, String>(key.toString(), key, "");
		newNode.getVisualizer().setColor("red");

		// if the tree is empty, set the root to the new node
		if (isEmpty()) {
			root = newNode;
		}
		else {
			// else treat it like an unsuccessful search
			BSTElement<K, String> node = root;
			boolean keepLooking = true;

			while (keepLooking) {
				int direction = key.compareTo(node.getKey());

				if (direction < 0) {
					// go left
					if (node.getLeft() == null) {
						// we found the place for the insert
						node.setLeft(newNode);
						keepLooking = false;
					}
					else
						node = node.getLeft();
				}
				else if (direction > 0) {
					// go right
					if (node.getRight() == null) {
						// we found the place for the insert
						node.setRight(newNode);
						keepLooking = false;
					}
					else
						node = node.getRight();
				}
			}
		}

		return key;
	}
	 */

	public K getLargest() {
		

		return getLargest(root);
	}
	public K getLargest(BSTElement<K, String> node) {
		BSTElement<K, String> current = node;
		if(current.getRight() != null) {
		
		return getLargest(current.getRight());
	}else {
		current.getVisualizer().setColor("red");
		return current.getKey();
	}
	}

	public K getSmallest() {

		return getSmallest(root);
		
	}
	
	
	public K getSmallest(BSTElement<K, String> node) {
		BSTElement<K, String> current = node;
		if(current.getLeft() != null) {
		
		return getSmallest(current.getLeft());
	}else {
		current.getVisualizer().setColor("yellow");
		return current.getKey();
	}
	}

	public boolean contains(K key) {

		for(K i: this) {
			if(key.compareTo(i) == 0) {
				
				return true;
			}
		}
		return false;
	}

	public K remove(K key) {
		
		BSTElement<K,String> s = remove(key, root);
		return s.getKey();
	}
	public BSTElement<K,String> remove(K key, BSTElement<K, String> node ) {
		if(node == null) {
		return null;
		}else if((key.compareTo(node.getKey()) < 0)) {
			BSTElement<K,String> current = remove(key,node.getLeft());
			node.setLeft(current);
		}else if((key.compareTo(node.getKey()) > 0)) {
			BSTElement<K,String> current = remove(key,node.getRight());
			node.setRight(current);
			
		}else if((key.compareTo(node.getKey()) == 0)) {
			if(node.getLeft() == null && node.getRight() == null) {
				return null;
			}
			if(node.getLeft() != null && node.getRight() != null) {
				K k = getSmallest(node.getRight());
				
				node.setKey(k);
				BSTElement<K,String> current = remove(node.getKey(),node.getRight());
				
				node.setRight(current);
		
				
			}
			else
				if(node.getLeft() != null) {
					node = node.getLeft();
				}
				else {
					
					node = node.getRight();
					
				}
					
					
			
		}
		
		return node;
		
	}
	
		
	

	

	public int size() {
		if(root==null){
			return 0;
		}
		return 1 + size(root.getRight()) + size(root.getLeft());
	}
	private int size(BSTElement<K, String> node) { 
		  if (node == null) return(0); 
		  else { 
		    return(size(node.getLeft()) + 1 + size(node.getRight())); 
		  } 
		} 


	public Iterator<K> iterator() {
		return new InOrderIterator();
	}
	private class InOrderIterator implements Iterator<K>
	{
		private K[] elements;
		private int next;

		private InOrderIterator() {
			// create an array large enough to hold all elements in the tree
			elements = (K[])new Comparable[size()];
			next = 0;

			// now perform an inorder traversal
			inOrder(root);

			// reset next back to the beginning of the array
			next = 0;
		}

		private void inOrder(BSTElement<K, String> root) {
			if (root != null) {
				
				inOrder(root.getLeft());
				elements[next++] = root.getKey();
				inOrder(root.getRight());
			}
		}

		public boolean hasNext() {
			return (next < size(root));
		}

		public K next() {
			return elements[next++];
		}
	}
	
		
}

