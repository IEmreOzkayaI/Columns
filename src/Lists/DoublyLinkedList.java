package Lists;

import Lists.Nodes.DoubleNode;

public class DoublyLinkedList {
 
    private DoubleNode head;
    private DoubleNode tail;
    private int size = 0;
 
    public boolean isEmpty() {
        return (head == null);
    }
    
    public DoubleNode getHead() {
    	return head;
    }
    
    public DoubleNode getTail() {
    	return tail;
    }
    
    public int size() {
    	return size;
    }
 
    // used to insert a node at the start of linked list
    public void insertFirst(Object data) {
    	DoubleNode newNode = new DoubleNode(head);
        newNode.setData(data);
        newNode.setNext(head);
        newNode.setPrev(null);
        if(head!=null)
            head.setPrev(newNode);
        head = newNode;
        if(tail==null)
            tail=newNode;
        size++;
    }
    
    public void insert(Object data, int position) {
        // fix the position
        if (position < 0) {
            position = 0;
        }
        if (position > size) {
            position = size;
        }

        // if the list is empty, make it be the only element
        if (head == null) {
            head = new DoubleNode(data);
            tail = head;
            
        }
        // if adding at the front of the list...
        else if (position == 0) {
        	DoubleNode temp = new DoubleNode(data);
            temp.setNext(head);
            head = temp;
            
        }
        // else find the correct position and insert
        else {
        	DoubleNode temp = head;
            for (int i = 1; i < position; i += 1) {
                temp = temp.getNext();
            }
            DoubleNode newNode = new DoubleNode(data);
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
        // the list is now one value longer
        size ++;
    }
    
     
 
    // used to insert a node at the end of linked list
    public void insertLast(Object data) {
    	DoubleNode newNode = (DoubleNode) data;
        newNode.setNext(null);
        newNode.setPrev(tail);
        if(tail!=null)
            tail.setNext(newNode);
        tail = newNode;
        if(head==null)
            head=newNode;
        size++;
    }
    
  
    
    // used to delete node from start of Doubly linked list
    public DoubleNode deleteFirst() {
 
        if (size == 0) 
            throw new RuntimeException("Doubly linked list is already empty");
        DoubleNode temp = head;
        head = head.getNext();
        head.setPrev(null);
        size --;
        return temp;
    }
 
    // used to delete node from last of Doubly linked list
    public DoubleNode deleteLast() {
 
    	DoubleNode temp = tail;
        tail = tail.getPrev();
        tail.setNext(null);
        size --;
        return temp;
    }
    
    public DoubleNode getIndex(int index) {
    	DoubleNode temp = head;
    	if (index > size - 1) {
            return null;
        }

        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        return temp;
    }
 
    
 
  
}