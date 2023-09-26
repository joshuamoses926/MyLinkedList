package linkedlist;

import java.util.ArrayList;

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
	 /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }
    
    @SuppressWarnings("unused")
	private void clear( )
    {
        doClear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
        modCount++;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }
    
    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;       
        }
    }
    
    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
    //item count method: 
    //Receives a value and return a count of the number of times this item is found in the list.
    public int itemcount(AnyType element)
    {
    	int count = 0;
    	//for loop will compare the index with an element of the list.
    	for(int i = 0; i < size(); i++) 
    	{
    		//if the the index equals an element the count variable is incremented by 1.
    		if(get(i).equals(element))
    		{
    			count++;
    		}
    	}
    	return count;
    }
    
    //swap method:
    //receives two index positions as parameters and swaps the two nodes.
    public void swap(int node1, int node2)
    {
    	//I used a basic swapping method
    	//temporary variable is of type AnyType and it is compared to the integer nodes.
    	AnyType temp = getNode(node1).data;
    	getNode(node1).data = getNode(node2).data;
    	getNode(node2).data = temp;
    }
    
    //sublist method: 
    //receives two indexes and returns an ArrayList of node values from the 1st index to the 2nd index.
    ArrayList<AnyType> sublist(int listBegining, int listEnd)
    {
    	ArrayList<AnyType> newList = new ArrayList<AnyType>();
    	for(int i = listBegining; i <= listEnd; i++)
    	{
    		newList.add(get(i));
    	}
    	return newList;
    }
    
    //select method:
    //receives a variable number of indexes, and returns an ArrayList of node values
    //corresponding to each index given.
    ArrayList<AnyType> select(int listBegining, int listEnd)
    {
    	ArrayList<AnyType> newList = new ArrayList<AnyType>();
    	for(int i = listBegining; i< listEnd; i++)
    	{
    		newList.add(get(i));
    	}
    	return newList;
    }
    
    //reverse method:
    //returns a new MyLinkList that has the elements in reverse order.
    ArrayList<AnyType> reverse(int listBegining, int listEnd)
    {
    	ArrayList<AnyType> newList = new ArrayList<AnyType>();
    	//This for loop reverses the list and puts it in the newList.
    	for(int i = listEnd; i-- > listBegining;)
    	{
    		newList.add(get(i));
    	}
    	return newList;
    }
    
    //erase method:
    //receives an index position and number of elements as parameters,
    //and removes elements beginning at the index position for the 
    //number of elements specified.
    ArrayList<AnyType> erase(int listBegining, int listEnd)
    {	
    	ArrayList<AnyType> newList = new ArrayList<AnyType>();
    	for(int i = listBegining; i <= listEnd; i++)
    	{
    		newList.add(get(i));
    	}
    	
    	return newList;
    }
    
    
    // insertList method:
    //receives a list and an index position as parameters, 
    //and copies all of the passed list into the existing list
    //at the position specified by the parameter.
    public void insertList(MyLinkedList<AnyType> list, int index)
    {
    	//Returns an iterator for the elements in this collection.
    	//Performs an action for each element in this iterator.
    	java.util.Iterator<AnyType> itr = list.iterator();
    	//hasnext() --> boolean
    	while(itr.hasNext())
    	{
    		//adds my list into this list.
    		add(index++,itr.next());
    	}
    }
    
    //shift method:
    //receives an integer and shifts the list this many nodes forward or backward,
    public void shift(int amountShifted)
    {
    	Node<AnyType> first = beginMarker.next;
    	Node<AnyType> end = endMarker.prev;
    	
    	end.next = first;
    	first.prev = end;
    	
    	if(amountShifted > 0)
    	{
    		for(int i = 0; i<amountShifted; i++)
    		{
    			first = first.next;
    			end = end.next;
    		}
    	}
    	else
    	{
    		for(int i = 0; i>amountShifted; i--)
    		{
    			first=first.prev;
    			end=end.prev;
    		}
    	}
    	beginMarker.next = first;
    	first.prev = beginMarker;
    	endMarker.prev = end;
    	end.next = endMarker;
    }
    
    
    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}


class TestLinkedList 
{

	public static void main(String[] args) 
	{

		MyLinkedList<Integer> lst = new MyLinkedList<>();
		for (int i = 0; i < 10; i++)
			lst.add(i);

		for (int i = 20; i < 30; i++)
			lst.add(0, i);
		
		//prints out the original list
		System.out.println("Original List:");
		System.out.print(lst);
		System.out.println("\n");
		
		//for loop is used to demonstrate the count of different items 
		//throughout the linked list 
		for(int i=0; i<30; i++) 
		{
			System.out.println("\n itemcount for: " + i);
			System.out.println(lst.itemcount(i));
		}
		
		//swap method swaps the first and last element in the list
		lst.swap(0, lst.size()-1);
		System.out.println("\n swap:");
		System.out.println(lst);
		
		
		
		System.out.println("\n sublist:");
		//sublist is made using items from node 1 and node 2
		System.out.println(lst.sublist(1, 2));
		
		//for loop is used to demonstrate all elements in 
		//each node
		for(int i=0; i<lst.size(); i++)
		{
			System.out.println("\n select for node: " + i);
			System.out.println(lst.select(i,i+1));
		}
		
	    
		//Reverses the elements in list 
	    System.out.println("\n reverse");
	    System.out.println(lst.reverse(0,19));
	    
	    //erases the 1st an last element in the list    
		System.out.println("\n erase:");
		System.out.println(lst.erase(1,18));
		
		//making a new list to put into the list
		MyLinkedList<Integer> myList = new MyLinkedList<Integer>();
		myList.add(25);
		myList.add(50);
		myList.add(100);
		System.out.println("\n new list:");
		System.out.println(myList);
		
		//puts the new list in the 10th position of the list
		lst.insertList(myList, 10);
		System.out.println("\n insertList:");
		System.out.println(lst);
		
		//shifts the list by 5 nodes.
		lst.shift(5);
		System.out.println("\n Shift:");
		System.out.println(lst);
		
	 }
}