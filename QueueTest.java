//-----------------------------------------------------------------------------
// QueueTest.java
// Test Client for the Queue class
//-----------------------------------------------------------------------------

public class QueueTest {
    public static void main(String[] args){
		Queue A = new Queue();
		A.enqueue(new Job(2,2));
		A.enqueue(new Job(3,4));
		A.enqueue(new Job(5,2));
		A.enqueue(new Job(3,3));
		System.out.println(A);
		System.out.println(A.length()+"\n");
		A.dequeue();
		System.out.println(A);
		System.out.println(A.length()+"\n");
		A.dequeue();
		A.dequeue();
		A.dequeue();
		System.out.println(A);
		System.out.println(A.length()+"\n");
		System.out.println(A.peek());
		A.dequeueAll();
		A.enqueue(new Job(2,2));
		System.out.println(A);
		A.dequeueAll();
		//A.dequeue();
		//A.dequeueAll();
		//A.peek();
		System.out.println(A.length()+"\n");
    }
}