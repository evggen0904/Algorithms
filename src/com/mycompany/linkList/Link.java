package com.mycompany.linkList;


class Link
{
    public long iData;              // data item
    public Link next;              // next link in list
    public Link previous;
    // -------------------------------------------------------------
    public Link(long data) // constructor
    {
        iData = data;                 // initialize data
    }                           //  set to null)
    // -------------------------------------------------------------
    public void displayLink()      // display ourself
    {
        System.out.print("{" + iData + "} ");
    }
}  // end class Link
////////////////////////////////////////////////////////////////
class LinkList
{
    private Link current;
    private Link last;

    // -------------------------------------------------------------
    public LinkList()              // constructor
    {
        current = null;
    }
    // -------------------------------------------------------------
    public boolean isEmpty()       // true if list is empty
    {
        return (current==null);
    }

    public Link getCurrent(){
        return current;
    }

    public Link setCurrent(Link newCurrent){
        Link link = current;
        do{
            if (link == newCurrent){
                current = link;
                return current;
            }
            link = link.next;
        } while (link != current );
        return current;
    }
    // -------------------------------------------------------------
    // insert at start of list
    public void insert(int data)
    {                           // make new link
        Link newLink = new Link(data);
        if (isEmpty()) {
            current = newLink;
            current.next = newLink;
            last = newLink;
        }
        else {
            current.next = newLink;
            newLink.next = last;
            current = newLink;
        }
    }

    public Link nextStep(){
        current = current.next;
        return current;
    }
    // -------------------------------------------------------------
    public Link delete(int data)
    {
        Link link = current;
        do{
            if (link.next.iData == data){
                current = link.next.next;
                link.next = current;
                return current;
            }
            link = link.next;
        } while (link != current );
        return null;
    }
    // -------------------------------------------------------------
    public void displayList()
    {
        System.out.print("List (current-->last): ");
        Link link = current;       // start at beginning of list

        do{
            link.displayLink();
            link = link.next;
        } while (link != current );

        System.out.println("");
    }
// -------------------------------------------------------------
}  // end class LinkList
////////////////////////////////////////////////////////////////
class LinkListApp
{
    public static void main(String[] args)
    {
        LinkList theList = new LinkList();  // make new list

        theList.insert(22);      // insert four items
        theList.displayList();
        theList.insert(44);
        theList.insert(66);
        theList.insert(88);

        theList.displayList();              // display list

        theList.delete(44);
        theList.displayList();
        theList.delete(66);
        theList.displayList();

    }  // end main()
}  // end class LinkListApp
////////////////////////////////////////////////////////////////

