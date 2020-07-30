public class LinkedQueueOfStrings {
    private Node first, last;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void enqueue(String item){
        Node old = last;
        last = new Node();
        first.item = item;
        first.next = null;
        if(isEmpty()) first = last;
        else old.next = last;
    }

    public String dequeue(){
        String item = first.item;
        first = first.next;
        if(isEmpty()) first = last;
        return item;
    }
}