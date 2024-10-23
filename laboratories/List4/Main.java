import TwoWayLinkedList.TwoWayLinkedList;

public class Main {
    public static final void main(String[] args) {
        TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);
        for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
    }
}
