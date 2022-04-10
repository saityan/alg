public class HomeworkFourth {
    public static void main(String[] args) {
        //Input
        Deque deck = new Deque();
        deck.insertLeft(new Link("Dan", 20));
        deck.insertLeft(new Link("Todd", 53));
        deck.insertLeft(new Link("Joe", 48));
        deck.insertRight(new Link("Max", 34));
        deck.insertRight(new Link("Bob", 75));

        Link bob = deck.removeRight();
        Link joe = deck.removeLeft();

        //Output
        deck.display();
        System.out.println();

        bob.display();
        joe.display();

        deck.getFirst().display();
        deck.getLast().display();

        System.out.println();
        System.out.println(deck.contains("Todd"));
        System.out.println(deck.contains("Joe"));

        //Iterator
        System.out.println();
        LinkIterator iterator = deck.getIterator();
        iterator.reset();
        iterator.insertAfter("Ann", 31);
        iterator.insertAfter("John", 43);
        iterator.reset();
        iterator.deleteCurrent();
        deck.display();
        deck.removeRight();
        deck.removeRight();
        deck.removeLeft();
        deck.display();
    }
}

class Link {
    private final String name;
    private final int age;
    private Link next;
    private Link previous;

    public Link(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setNext(Link next) { this.next = next; }

    public void setPrevious(Link previous) { this.previous = previous; }

    public String getName() { return name; }

    public int getAge() { return age; }

    public Link getNext() { return next; }

    public Link getPrevious() { return previous; }

    public void display(){
        System.out.println("Name: "+this.name+", age: "+this.age);
    }
}

class LinkedList {
    protected Link first;
    protected int size;

    public LinkIterator iterator() { return new LinkIterator(this); }

    public LinkedList() { size = 0; }

    public void setFirst(Link first) { this.first = first; }

    public void insertFirst(Link link) {
        if (isEmpty()) {
            first = new Link(link.getName(), link.getAge());
        } else {
            Link currentFirst = first;
            Link newLink = new Link(link.getName(), link.getAge());
            first.setPrevious(newLink);
            first = newLink;
            first.setNext(currentFirst);
        }
        size++;
    }

    public Link removeFirst() {
        if (isEmpty()) { return null; }
        Link removedLink = first;
        first = removedLink.getNext();
        removedLink.setNext(null);
        removedLink.setPrevious(null);
        size--;
        return removedLink;
    }

    public int size() { return size; }

    private Link indexOf(String name) {
         Link currentLink = first;
         while (currentLink != null) {
             if (currentLink.getName().equals(name))
                 return currentLink;
             currentLink = currentLink.getNext();
         }
         return null;
    }

    public boolean contains(String name) { return indexOf(name) != null; }

    public boolean isEmpty() { return size == 0; }

    public void display() { System.out.println(this); }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[\t");
        Link currentLink = first;
        while (currentLink != null) {
            stringBuilder.append(currentLink.getName());
            stringBuilder.append(": ");
            stringBuilder.append(currentLink.getAge());
            stringBuilder.append(";\t");
            currentLink = currentLink.getNext();
        }

        return stringBuilder.append("]").toString();
    }

    public Link getFirst() { return first; }
}

class TwoSidedLinkedList extends LinkedList {
    private Link last;

    @Override
    public void insertFirst(Link link) {
        super.insertFirst(link);
        if (size() == 1) {
            last = super.first;
        }
    }

    public void insertLast (Link link) {
        if (isEmpty()) {
            insertFirst(link);
            return;
        }
        Link currentLast = last;
        Link newLink = new Link(link.getName(), link.getAge());
        last.setNext(newLink);
        last = newLink;
        last.setPrevious(currentLast);
        super.size++;
    }

    @Override
    public Link removeFirst() {
        Link removedLink = super.removeFirst();
        if (isEmpty()) {
            last = null;
        }
        return removedLink;
    }

    public Link removeLast() {
        if (isEmpty()) { return null; }
        if (size() == 1) { return removeFirst(); }
        Link removedLink = last;
        last = removedLink.getPrevious();
        last.setNext(null);
        removedLink.setNext(null);
        removedLink.setPrevious(null);
        size--;
        return removedLink;
    }

    public Link getLast() { return last; }
}

class Deque {
    private final TwoSidedLinkedList data;

    public Deque() { this.data = new TwoSidedLinkedList(); }

    public boolean insertLeft(Link link) {
        data.insertFirst(link);
        return true;
    }

    public boolean insertRight(Link link) {
        data.insertLast(link);
        return true;
    }

    public Link getFirst() { return data.getFirst(); }

    public Link getLast() { return data.getLast(); }

    public Link removeLeft() { return data.removeFirst(); }

    public Link removeRight() { return data.removeLast(); }

    public int size() { return data.size(); }

    public boolean isEmpty() { return data.isEmpty(); }

    public void display() { data.display(); }

    public boolean contains(String name) { return data.contains(name); }

    public LinkIterator getIterator() { return data.iterator(); }
}


class LinkIterator {
    private Link current;
    private Link previous;
    private Link next;
    private final LinkedList list;

    public LinkIterator(LinkedList list){
        this.list = list;
        this.reset();
    }

    public void reset() {
        current = list.getFirst();
        next = list.getFirst().getNext();
        previous = null;
    }

    public boolean atEnd(){ return (current.getNext() == null); }

    public boolean atStart() { return (current.getPrevious() == null); }

    public void nextLink() {
        previous = current;
        current = current.getNext();
        next = current.getNext().getNext();
    }

    public Link getCurrent() { return current; }

    public void insertAfter(String name, int age){
        Link newLink = new Link(name, age);
        if (list.isEmpty()) {
            list.setFirst(newLink);
            current = newLink;
        } else {
            newLink.setNext(current.getNext());
            newLink.setPrevious(current.getPrevious());
            current.setNext(newLink);
            next.setPrevious(newLink);
            nextLink();
        }
    }

    public void insertBefore(String name, int age){
        Link newLink = new Link(name, age);
        if(previous == null) {
            newLink.setNext(list.getFirst());
            list.getFirst().setPrevious(newLink);
            list.setFirst(newLink);
            reset();
        }
        else {
            newLink.setNext(previous.getNext());
            previous.setNext(newLink);
            next.setPrevious(newLink);
            current = newLink;
        }
    }

    public String deleteCurrent() {
        String name = current.getName();
        if (previous == null){
            list.setFirst(current.getNext());
            reset();
        } else {
            previous.setNext(current.getNext());
            next.setPrevious(current.getPrevious());
            if (atEnd()){
                reset();
            } else {
                current = current.getNext();
            }
        }
        return name;
    }
}
