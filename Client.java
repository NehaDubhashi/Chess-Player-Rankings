import java.util.Random;
import java.util.*;

public class Client {
    public static void main(String[] args){
        minHeapTester();
        maxHeapTester();
        topKTester();
    }

    public static void minHeapTester(){
        Random r = new Random(332);
        MyPriorityQueue<ChessPlayer> pq = new BinaryMinHeap<>();
        int minscore = 1001;
        for(int i = 0; i < 100; i++){
            ChessPlayer cp = new ChessPlayer("Player"+i);
            cp.elo = r.nextInt(1000);
            minscore = Math.min(minscore, cp.elo);
            pq.insert(cp);
        }
        ChessPlayer cp2 = pq.peek();
        if(cp2.elo != minscore){
            System.out.println("minimum is not the root!");
            return;
        }
        cp2 = pq.extract();
        if(cp2.elo != minscore){
            System.out.println("minimum is not the root!");
            return;
        }
        int lastScore = cp2.elo;
        while(!pq.isEmpty()){
            cp2 = pq.extract();
            if(lastScore > cp2.elo){
                System.out.println("items extracted out of order!");
                return;
            }
        }
        System.out.println("Super basic tests for BinaryMinHeap look good! I encourage you to test more of the methods!");
    }

    public static void maxHeapTester(){
        Random r = new Random(332);
        MyPriorityQueue<ChessPlayer> pq = new BinaryMaxHeap<>();
        int maxscore = -1;
        for(int i = 0; i < 100; i++){
            ChessPlayer cp = new ChessPlayer("Player"+i);
            cp.elo = r.nextInt(1000);
            maxscore = Math.max(maxscore, cp.elo);
            pq.insert(cp);
        }
        ChessPlayer cp2 = pq.peek();
        if(cp2.elo != maxscore){
            System.out.println("maximum is not the root!");
            return;
        }
        cp2 = pq.extract();
        if(cp2.elo != maxscore){
            System.out.println("maximum is not the root!");
            return;
        }
        int lastScore = cp2.elo;
        while(!pq.isEmpty()){
            cp2 = pq.extract();
            if(lastScore < cp2.elo){
                System.out.println("items extracted out of order!");
                return;
            }
        }
        System.out.println("Super basic tests for BinaryMaxHeap look good! I encourage you to test more of the methods!");
    }

    public static void topKTester(){
        Random r = new Random(332);
        int k = 7;
        TopKHeap<ChessPlayer> tkh = new TopKHeap<>(k);
        List<ChessPlayer> all = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            ChessPlayer cp = new ChessPlayer("Player"+i);
            cp.elo = r.nextInt(1000);
            tkh.insert(cp);
            all.add(cp);
        }
        List<ChessPlayer> topk = tkh.topK();
        if(topk.size() != k){
            System.out.println("There aren't k players!");
            return;
        }
        all.sort(null);
        for(int i = 0; i < k; i++){
            if(!topk.contains(all.get(all.size()-i-1))){
                System.out.println("topk omits one of the top k!");
                return;
            }
        }
        System.out.println("Super basic tests for TopKHeap look good! I encourage you to test more of the methods!");
    }    
}
