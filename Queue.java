//-----------------------------------------------------------------------------
//Gio Lapid
//CMPS12B Summer 13
//PA4 
//Queue.java
//Implementation of the interface for the QueueInterface ADT
//-----------------------------------------------------------------------------
//change all strings to objects

public class Queue implements QueueInterface{
    
	//private inner node class
    private class Node{
		Object item;
		Node next;
		
		Node(Object x){
			this.item = x;
			this.next = null;
		}	
    }

    //private method for the toString() method using recursion 
    private String myString(Node H){
	if(H == null)
	    return "";
	else 
	    return (H.item + " " + myString(H.next));
    }
    private Node front;     //reference to the very first Node in the queue
    private Node rear;     //reference to the very last Node in the queue
    private int numItems;  //number of items in the Queue
    
    //Queue
    //default constructor for the Queue class
    public Queue(){
		front = null;
		rear = null;
		numItems = 0;
    }

    //ADT Operations
    //-----------------------------------------------------------------------------------
 
    // isEmpty()
    // pre: none
    // post: returns true if this Queue is empty, false otherwise
    public boolean isEmpty(){
		return(numItems == 0);
    }

    // length()
    // pre: none
    // post: returns the length of this Queue.
    public int length(){
		return numItems;
    }

    // enqueue()
    // adds newItem to back of this Queue
    // pre: none
    // post: !isEmpty()
    public void enqueue(Object newItem){
		if(numItems == 0)
			front = new Node(newItem);
		else if(numItems == 1){
			Node N = front;
			N.next = new Node(newItem);
			rear = N.next;
		}	
		else{
			Node N = rear;
			N.next = new Node(newItem);
			rear = N.next;
		}
		numItems++;
    }

    // dequeue()
    // deletes and returns item from front of this Queue
    // pre: !isEmpty()
    // post: this Queue will have one fewer element
    public Object dequeue() throws QueueEmptyException{
		Node N = null;
		if(numItems == 0)
			throw new QueueEmptyException("cannot dequeue() empty queue");
		if(!isEmpty()) {
			N = front; 
			front = front.next;
			numItems--; 
		}
		return N.item;
	}

    // peek()
    // pre: !isEmpty()
    // post: returns item at front of Queue
    public Object peek() throws QueueEmptyException{
	if(numItems == 0)
	    throw new QueueEmptyException("cannot peek() empty queue");
	return front.item;
    }

    // dequeueAll()
    // sets this Queue to the empty state
    // pre: !isEmpty()
    // post: isEmpty()
    public void dequeueAll() throws QueueEmptyException{
	if(numItems == 0)
	    throw new QueueEmptyException("cannot dequeueAll() empty queue");
	numItems = 0;
	front = null;
	rear = null;
    }

    // toString()
    // overrides Object's toString() method
    public String toString(){
		return myString(front);
    }

    // equals
    // overrides Object's equals
    public boolean equals(Object rhs){
		boolean eq = false;
		Queue R;

		if(rhs instanceof Queue){
			R = (Queue)rhs;
			eq = (this.numItems==R.numItems);
			Node N = this.front;
			Node M = R.front;
			while(eq && N!=null){
				eq = (N.item==M.item);
				N = N.next;
				M = M.next;
			}		
		}
		return eq;
    }
}