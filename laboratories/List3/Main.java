import OneWayArrayList.OneWayArrayList;

public class Main {
    public static void main(String[] args) {
        OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        list.add(2);
        list.add(2);
        list.add(3);
        list.remove(1);
        list.add(2, 4);
        for(int i = 0; i < 3; ++i) {
            System.out.println(list.get(i));
        }
        System.out.println(list.getNodeCount());
        list.remove(list.size() - 1);
        System.out.println(list.getNodeCount());
    }    
}
