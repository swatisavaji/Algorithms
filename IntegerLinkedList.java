public class IntegerLinkedList {
    VertexSub first, last;
    int number_of_nodes;

    IntegerLinkedList(){
        first = new VertexSub(-1, -1);
        last = new VertexSub(-1, -1);
        first.next = last;
        last.prev = first;
        number_of_nodes = 0;
    }

    public void addLast(VertexSub v){
        v.next = last;
        v.prev = last.prev;
        last.prev.next = v;
        last.prev = v;
        number_of_nodes++;
    }

    public VertexSub removeFirst(){
        VertexSub v = null;
        if (!isEmpty()){
            v = first.next;
            first.next = v.next;
            v.next.prev = first;
            v.prev = null;
            v.next = null;
            number_of_nodes--;
        }
        return v;
    }

    public void remove(VertexSub v){
        if (!isEmpty()){
            v.prev.next = v.next;
            v.next.prev = v.prev;
            v.prev = null;
            v.next = null;
            number_of_nodes--;
        }
    }

    public boolean isEmpty() {
        boolean isEmpty = false;
        if (number_of_nodes == 0)
            isEmpty = true;
        return isEmpty;
    }

}
