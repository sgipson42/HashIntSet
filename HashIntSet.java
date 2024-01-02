import java.util.Arrays;
//add method runs in O(MN) time, not O(N)

public class HashIntSet {
//HashTable is the array still, each element in array has a linkedlist
	private class Node{
		public int data;
		public Node next;

		public Node(int value) {
			data = value;
			next = null;
		}
		public Node(int value, Node next) {
			data = value;
			this.next = next;
		}
	}

	private Node[] elements;
	private int size;
	
	//array of linkedlists
	public HashIntSet() {
		elements = new Node[10];
		size = 0;
	}
	
	//uses array length to find indices
	public int hash(int i) {
		return (Math.abs(i) % elements.length);
	}
	
	//first need to find the index
	//if an element there, either change front or next field
	//and make sure no duplicates since its a set
	public void add(int value) {
		if(!contains(value)) {//value not already in the set
			int h = hash(value);//find index with hash method
			Node newNode = new Node(value);//make a new Node for the value
			newNode.next = elements[h];//make the new node point to the rest of the list(link at the front)
			elements[h] = newNode;//make the front of the list at the index be the new node
			size++;//increase the number of elements in the set

		}
	}
	public boolean contains(int value) {
		Node current = elements[hash(value)];
		//find index of your value that needs to be searched
		//make the current node be the front of the list at the element
		//now search through the index list
		while(current != null) {
			if(current.data == value) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	public String toString() {
		String s = "";
		for(Node n:elements) {
			Node current = n;
			while(current != null) {
				s += current.data + " ";
				current = current.next;
			}
		}
		return s;
	}

	public void remove(int value) {
		int h = hash(value);
		//same thing, call hash to find the index we need to search the list of
		//type 1: remove the first node
		//type 2: remove the middle node
		
		if(elements[h] != null && elements[h].data == value) {//if first node is value
			elements[h] = elements[h].next; //make the front be the next value, which will be null if the front
			size--;
		}else {
			Node current = elements[h];
			while(current != null && current.next != null) {
				if(current.next.data == value) {
					current.next = current.next.next;
					size--;
					return;
				}
				current = current.next;
			}
		}	
	}

	/* BJP4 Exercise 18.1: addAllHashIntSet
	 * Write a method named addAll that could be placed inside 
	 * the HashIntSet class. This method accepts another HashIntSet 
	 * as a parameter and adds all elements from that set into the 
	 * current set, if they are not already present. 
	 *
	 * For example, if a set s1 contains [1, 2, 3] and another set 
	 * s2 contains [1, 7, 3, 9], the call of s1.addAll(s2); would change 
	 * s1 to store [1, 2, 3, 7, 9] in some order.
	 *
	 * You are allowed to call methods on your set and/or the other set. 
	 * Do not modify the set passed in. This method should run in O(N) time 
	 * where N is the number of elements in the parameter set passed in. 
	 */
	public void addAll(HashIntSet set) {
		//loop through elements of input set
		for (Node n : set.elements) {
			Node current = n;
			//loop through elements at each index
			while (current != null) {
				//add elements not contained in first set
				//contains method is internal to the add method
				add(current.data);
				current = current.next;
			}
		}
	}

	/* BJP4 Exercise 18.3: equalsHashIntSet
	 * Write a method in the HashIntSet class called equals that accepts 
	 * another object as a parameter and returns true if the object is 
	 * another HashIntSet that contains exactly the same elements. 
	 * The internal hash table size and ordering of the elements does not matter, 
	 * only the sets of elements themselves.
	 */
	public boolean equals(Object object) {//double containment
		//check if it is a HashIntSet
		if (!(object instanceof HashIntSet)) {
			return false;
		}
		//cast the type to HashIntSet from Object
		HashIntSet set = (HashIntSet) object;
		
		if (set.size != size) {
			return false;
		} else {//if same size, need to check the elements
			//check that elements in first set belong to the second
			for (int i = 0; i<size; i++) {
				Node current = elements[i];
				while (current != null) {
					if (!set.contains(current.data)) {
						return false;
					} 
					current = current.next;
				}
			}
			//check that elements in second set belong to the first
			for (int i = 0; i<size; i++) {
				Node current = set.elements[i];
				while (current != null) {
					if (!contains(current.data)) {
						return false;
					} 
					current = current.next;
				}
			}
			return true;
		}
	}

	/* BJP4 Exercise 18.4: removeAllHashIntSet
	 * Write a method in the HashIntSet class called removeAll that accepts 
	 * another hash set as a parameter and ensures that this set does not contain 
	 * any of the elements from the other set. For example, if the set stores 
	 * [-2, 3, 5, 6, 8] and the method is passed [2, 3, 6, 8, 11], your set would 
	 * store [-2, 5] after the call.
	 */
	public void removeAll(HashIntSet set) {
		//loop through elements of input set
		for (Node n : set.elements) {
			Node current = n;
			//loop through elements at each index
			while (current != null) {
				//if first set contains data from the input set, remove it from the first set
				if (contains(current.data)) {
					remove(current.data);
				}
				current = current.next;
			}
		}
	}

	/* BJP4 Exercise 18.5: retainAllHashIntSet
	 * Write a method in the HashIntSet class called retainAll that accepts 
	 * another hash set as a parameter and removes all elements from this set that 
	 * are not contained in the other set. For example, if the set stores [-2, 3, 5, 6, 8] 
	 * and the method is passed [2, 3, 6, 8, 11], your set would store [3, 6, 8].
	 */
	public void retainAll(HashIntSet set) {
		//loop through elements of first set
		for (Node n : elements) {
			Node current = n;
			//loop through elements at each index
			while (current != null) {
				//if first set contains data that is not in the input set, remove it from the first set
				if (!(set.contains(current.data))) {
					remove(current.data);
				}
				current = current.next;
			}
		}
	}
		

	/* BJP4 Exercise 18.6: toArrayHashIntSet
	 * Write a method in the HashIntSet class called toArray that returns the elements of 
	 * the set as a filled array. The order of the elements in the array is not important 
	 * as long as all elements from the set are present in the array, with no extra empty 
	 * slots before or afterward.
	 */
	public int[] toArray() {
		int[] list= new int[size];
		int index= 0; //used for array index
		
		for (int i = 0; i<elements.length; i++) {
			Node current = elements[i];
			while (current != null) {
				list[index] = current.data;
				index++;
				current = current.next;
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		HashIntSet set = new HashIntSet();
		set.add(37);
		set.add(-2);
		set.add(49);
		set.add(47);
		set.add(57);
		set.add(58);
		
		HashIntSet set2 = new HashIntSet();
		set2.add(37);
		set2.add(-2);
		set2.add(33);
		set2.add(47);
		set2.add(57);
		set2.add(83);
		//System.out.println(set);
		//System.out.println(set.contains(57));
		//set.remove(7);
		//System.out.println(set);

		//System.out.println(Arrays.toString(set.toArray()));
		
		//System.out.println(set.equals(set2));
		//System.out.println(set2.equals(set1));
		
		/*System.out.println("Set: " + set);
		System.out.println("Set2: " + set2);
		set.addAll(set2);
		System.out.println("Set after addAll: " + set);
		System.out.println("Set2 after addAll: " + set2);*/
		
		/*System.out.println("Set: " + set);
		System.out.println("Set2: " + set2);
		set.removeAll(set2);
		System.out.println("Set after removeAll: " + set);
		System.out.println("Set2 after removeAll: " + set2);*/
		
		/*System.out.println("Set: " + set);
		System.out.println("Set2: " + set2);
		set.retainAll(set2);
		System.out.println("Set after retainAll: " + set);
		System.out.println("Set2 after retainAll: " + set2);*/
	}

}

