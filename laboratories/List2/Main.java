import Ex1.Table;
import Ex2.PascalIterator;
import Mod.Mod;

class Main {
    public static void main(String[] args) {
        Table<Integer> table = new Table<Integer>(10);
        for(int i = 0; i < 10; ++i) {
            table.set(i, i*100 + i);
        }
        for (int v : table) {
            System.out.println(v);
        }
        
        System.out.println();
        
        for(int i = 1; i <= 10; ++i) {
            for (int v : PascalIterator.newPascalIterator(i).get()) {
                System.out.printf("%d ", v);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        for(Integer x : new Mod(12)) {
            System.out.println(x);
        }
    }
}
