/**
 * Auto Generated Java Class.
 */
import java.util.*;
class BoardComparator implements Comparator<Board>
{
     public int compare(Board b1, Board b2)
     {
         return b1.getPriority()-b2.getPriority();
     }
 }


public class Solver {
    
    /* ADD YOUR CODE HERE */
    TreeSet<Board> hmA,hmB;
    int minMoves=-1;
    Board initial,swapped;
    Queue<Board> S;
    MinPQ<Board> A,B;
    boolean existsSol=true;
    Board FINAL;
    public Solver(Board initial){
        hmA=new TreeSet<Board>();
        hmB=new TreeSet<Board>();
        S=new Queue<Board>();
        this.initial=initial;
        this.swapped=initial.twin();
        A=new MinPQ<Board>( new BoardComparator());
        B=new MinPQ<Board>( new BoardComparator());
        initial.moves=0;initial.previous=null;
        swapped.moves=0;swapped.previous=null;
        A.insert(initial);
        B.insert(swapped);
        hmA.add(initial);
        hmB.add(swapped);
        while(true){
            Board tA=(Board)A.delMin();
            Board tB=(Board)B.delMin();
           // System.out.println(tA.toString());
           // System.out.println(tB.toString());
            if(tB.isGoal()){
                existsSol=false;
                break;
            }
            if(tA.isGoal()){
                minMoves=tA.moves;
                FINAL=tA;
                break;
            }
            for(Board board : tA.neighbors()){
                if(hmA.contains(board))
                    continue;
                hmA.add(board);
                board.previous=tA;
                board.moves=tA.moves+1;
                A.insert(board);
            }
            for(Board board : tB.neighbors()){
                if(hmB.contains(board))
                    continue;
                hmB.add(board);
                board.moves=tB.moves+1;
                B.insert(board);
            }    
        }
    }
    
    public boolean isSolvable(){
        return existsSol;
    }
    public int moves(){
        return minMoves;
    }
    public Iterable<Board> solution(){
        if(!existsSol)
            return null;
        Stack<Board> stack=new Stack<Board>();
        Board cur=FINAL;
        while(cur!=null){
            stack.push(cur);
            cur=cur.previous;
        }
        while(!stack.isEmpty()){
            cur=stack.pop();
            S.enqueue(cur);
        }
        return S;
    }
    public static void main(String[] args) {

    // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
