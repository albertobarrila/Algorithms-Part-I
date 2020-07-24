public class LinkedStackOfStrings {
    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void push(String item){
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
    }

    public String pop(){
        String item = first.item;
        first = first.next;
        return item;
    }
}
