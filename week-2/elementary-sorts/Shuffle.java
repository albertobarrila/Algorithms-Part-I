public class Shuffle {
    public static void shuffle(Object[] a){
        int N = a.length;
        for(int i = 0; i < N; i++){
            int r = StdRandom.uniform(i+1);
            exch(a, i, r);
        }
    }
}