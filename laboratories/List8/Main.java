import java.util.Comparator;

import BST.BST;

public class Main {

    public static void main(String[] args) {
        final BST<Integer> bst = new BST<>(new Comparator<Integer>() {
    
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
            
        });
    
        System.out.println(bst.insert(5));
        System.out.println(bst.insert(2));
        System.out.println(bst.findNext(2));
        System.out.println(bst.insert(7));
        System.out.println(bst.insert(1));
        System.out.println(bst.insert(3));
        System.out.println(bst.insert(8));
        System.out.println(bst.insert(6));

        System.out.println(bst.remove(8));
        System.out.println(bst.remove(7));
        System.out.println(bst.remove(2));
        
        System.out.println("Done");
        
    }
    
}
