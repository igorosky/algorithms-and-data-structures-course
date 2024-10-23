class Main {
    public static void main(String[] args) {
        Ex1.drawZigZag(-30, 6);
        System.out.println();
        System.out.println();
        System.out.println();
        Ex1.drawScarf(50, 7);

        final int[] ex2 = { 1, 2, 8, -3, -3, -4, 7, 10, 0 };
        final int[][] a = Ex2.longestNondecreasingSubstrings(ex2);
        for(int i = 0; i < a.length; ++i) {
            for(int j = 0; j < a[i].length; ++j) {
                System.out.printf("%d ", a[i][j]);
            }
            System.out.println();
        }
    }
}
