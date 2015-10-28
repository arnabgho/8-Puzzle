import java.lang.*;

public class Board implements Comparable<Board>{
    int [][] tiles;
    int N;
    int moves;
    Board previous;
    public Board(int [][] blocks){
        N=blocks.length;
        tiles=new int[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                tiles[i][j]=blocks[i][j];
            }
        }    
    }
    public int getMoves(){
        return moves;
    }
    public int getPriority(){
        return moves+this.manhattan();
    }
    private boolean isValid(int i,int j){
        return (0<=i && i<N && 0<=j && j<N);
    }
    public int dimension(){
        return N;
    }
    public int hamming(){
        int cur=1;
        int ans=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(tiles[i][j]==0){
                    cur++;
                    continue;
                }    
                if(tiles[i][j]!=cur)
                    ans++;
                cur++;    
            }
        }
        return ans;
    }
    public int manhattan(){
        int cur=1;
        int ans=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(tiles[i][j]==0){
                    cur++;
                    continue;
                }    
                if(tiles[i][j]!=cur){
                    int num=tiles[i][j];
                    int x,y;
                    x=(num-1)/N;
                    y=num-1-N*x;
                    ans+=Math.abs(x-i)+Math.abs(y-j);
                }    
                cur++;    
            }
        }
        return ans;
    }
    public boolean isGoal(){
        int cur=1;
        boolean pos=true;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(i==N-1 && j==N-1){
                    cur++;
                    continue;
                }    
                if(tiles[i][j]!=cur)
                    return pos=false;
                cur++;    
            }
        }
        return pos;
    }
    public Board twin(){
        int i=0,j=0;
        boolean found=false;
        for(i=0;i<N ;i++){
            for(j=0;j<N-1;j++){
                if(tiles[i][j]!=0 && tiles[i][j+1]!=0 ){
                    found=true;
                    break;
                }    
            }
            if(found) break;
        }
        int x=tiles[i][j];
        int y=tiles[i][j+1];
        tiles[i][j+1]=x;
        tiles[i][j]=y;
        Board res=new Board(tiles);
        tiles[i][j]=x;
        tiles[i][j+1]=y;
        //System.out.println("Swapped "+res.toString());
        return res;
    }
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that=(Board) y;
        boolean pos=true;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(this.tiles[i][j]!=that.tiles[i][j])
                    return pos=false;
            }
        }
        return true;
    }
    public Iterable <Board> neighbors(){
        int i=0,j=0;
        boolean found=false;
        for(i=0;i<N ;i++){
            for(j=0;j<N;j++){
                if(tiles[i][j]==0){ 
                    found=true;
                    break;
                }    
            }
            if(found) break;
        }
      //  System.out.println("i "+i+" j "+j);
        int dx[]=new int[]{-1,0,1,0};
        int dy[]=new int[]{0,1,0,-1};
        Queue<Board> q=new Queue<Board>();
        for(int k=0;k<4;k++){
            if(isValid(i+dx[k],j+dy[k])){
                int num=tiles[i+dx[k]][j+dy[k]];
                tiles[i][j]=num;
                tiles[i+dx[k]][j+dy[k]]=0;
                Board b=new Board(tiles);
                q.enqueue(b);
                tiles[i][j]=0;
                tiles[i+dx[k]][j+dy[k]]=num;
            }
        }
       // System.out.println("size"+q.size());
        return q;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public int compareTo(Board that){
        int res=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                res+=Math.abs(tiles[i][j]-that.tiles[i][j]);
            }
        }
        return res;
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
        Board swapped=initial.twin();
        //System.out.println(swapped.toString());
        //StdOut.println(initial.toString());
        //for(Board board : initial.neighbors()){
          //  System.out.println(board.toString());
        //}    
    }     
}