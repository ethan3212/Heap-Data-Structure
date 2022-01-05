package com.company;

class Node
{
    private int iData;

    public Node(int key)
    { iData = key; }

    public int getKey()
    { return iData; }

    public void setKey(int id)
    { iData = id; }

} // end class Node

class Heap
{
    private Node[] heapArray;
    private int maxSize;
    private int currentSize;

    public Heap(int mx)
    {
        maxSize = mx;
        currentSize = 0;

        // create array
        heapArray = new Node[maxSize];
    }

    public boolean isEmpty()
    { return currentSize==0; }

    public boolean insert(int key)
    {
        if(currentSize==maxSize)
            return false;
        Node newNode = new Node(key);
        heapArray[currentSize] = newNode;
        trickleUp(currentSize++);
        return true;

    } // end insert()

    public void trickleUp(int index)
    {
        int parent = (index-1) / 2;
        Node bottom = heapArray[index];

        while( index > 0 &&
                heapArray[parent].getKey() < bottom.getKey() )
        {
            // move it down
            heapArray[index] = heapArray[parent];
            index = parent;
            parent = (parent-1) / 2;

        } // end while
        heapArray[index] = bottom;

    } // end trickleUp()

    public Node remove()
    {
        // delete item with max key
        Node root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        trickleDown(0);
        return root;

    } // end remove()

    public void trickleDown(int index)
    {
        int largerChild;
        Node top = heapArray[index];
        while(index < currentSize/2)
        {
            //  save root while node has at least one child, find larger child
            int leftChild = 2*index+1;
            int rightChild = leftChild+1;


            // (rightChild exists?)
            if(rightChild < currentSize &&
                    heapArray[leftChild].getKey() <
                            heapArray[rightChild].getKey())
                largerChild = rightChild;
            else
                largerChild = leftChild;

            // top >= largerChild?
            if( top.getKey() >= heapArray[largerChild].getKey() )
                break;

            // shift child up
            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        } // end while

        // root to index
        heapArray[index] = top;

    } // end trickleDown()

    public boolean change(int index, int newValue)
    {
        if(index<0 || index>=currentSize)
            return false;

        // remember old
        int oldValue = heapArray[index].getKey();

        // change to new
        heapArray[index].setKey(newValue);

        // if raised
        if(oldValue < newValue)
            trickleUp(index);

        // if lowered
        else
            trickleDown(index);
        return true;
    } // end change()

    public void displayHeap()
    {
        // array format
        System.out.print("heapArray: ");
        for(int m=0; m<currentSize; m++)
            if(heapArray[m] != null)
                System.out.print( heapArray[m].getKey() + " ");
            else
                System.out.print( "-- ");
        System.out.println();

        // heap format
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;
        String dots = "...............................";
        System.out.println(dots+dots);

        // for each heap item
        while(currentSize > 0)
        {
            // first item in row?
            if(column == 0)
                for(int k=0; k<nBlanks; k++)
                    System.out.print(' ');

            // display item
            System.out.print(heapArray[j].getKey());

            // done?
            if(++j == currentSize)
                break;

            // end of row?
            if(++column==itemsPerRow)
            {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            }

            // next item on row
            else
                for(int k=0; k<nBlanks*2-2; k++)
                    // interim blanks
                    System.out.print(' ');
        } // end for

        // dotted bottom line
        System.out.println("\n"+dots+dots);

    } // end displayHeap()

} // end class Heap

class PriorityQ
{
    // array in sorted order, from max at 0 to min at size-1
    private int maxSize;
    private Heap theHeap;
    private int nItems;

    public PriorityQ(int s)
    {
        maxSize = s;
        theHeap = new Heap(s);
        nItems = 0;
    }

    // insert item
    public void insert(int item)
    {
        int j;
        theHeap.insert(item);
        nItems++;
    } // end insert()

    // remove minimum item
    public int remove()
    {
        Node root = theHeap.remove();
        nItems--;
        return root.getKey();
    }

    // true if queue is empty
    public boolean isEmpty()
    { return (nItems==0); }

    // true if queue is full
    public boolean isFull()
    { return (nItems == maxSize); }

} // end class PriorityQ

public class Main
{
    public static void main(String[] args)
    {
        PriorityQ thePQ = new PriorityQ(5);
        thePQ.insert(30);
        thePQ.insert(50);
        thePQ.insert(10);
        thePQ.insert(40);
        thePQ.insert(20);

        while( !thePQ.isEmpty() )
        {
            int item = thePQ.remove();
            System.out.print(item + " ");
            
        } // end while

        System.out.println("");

    } // end main()

} // end class PriorityQApp
