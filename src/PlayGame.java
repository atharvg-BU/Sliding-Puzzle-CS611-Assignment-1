import java.util.*;
import java.util.Scanner;
class SlidingPuzzle {
    //    Function to get the initial puzzle
    public int[][] design_board(int m, int n, int[] zero_loc){
//        Initializing a 1D array to temporarily store the values from 1 to (m*n-1) with 0 as placeholder
        int[] arr_init=new int[m*n];

        Random r = new Random();
        while(true){
//          Loop to fill array with values 1 to (m*n-1) and 0 for empty space
            for (int i=0;i< arr_init.length;i++){
                int num=r.nextInt((m*n));
                boolean flag=false;
                for(int j=0;j<i;j++){
                    if(arr_init[j]==num){
                        flag=true;
                        break;
                    }
                }
                if(flag){
                    i--;
                    continue;
                }
                arr_init[i]=num;
            }
            if(checkSolvability(arr_init)){
                break;
            }
            else{
                System.out.println("System Generated an unsolvable Puzzle! Regenerating...");
            }
        }
//        2D array to store representation of sliding puzzle board
        int[][] board=new int[m][n];
        int k=0;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++){
                if(arr_init[k]==0){
                    zero_loc[0]=i;
                    zero_loc[1]=j;
                }
                board[i][j]= arr_init[k];
                k++;
            }
        }
        return board;
    }

    public boolean checkSolvability(int[]single_board){
        int count=0;
        for (int i=0;i< single_board.length;i++){
            if(single_board[i]==0){
                continue;
            }
            for(int j=i+1;j<single_board.length;j++){
                if(single_board[j]==0){
                    continue;
                }
                if(single_board[i]>single_board[j]){
                    count++;
                }
            }
        }
        if(count%2==0){
            return true;
        }
        else {
            return false;
        }
    }

    public void display_board(int[][] board){
        for(int i=0;i<board[0].length-1;i++){
            System.out.print("+__");
        }
        System.out.println("+");
        for(int i=0;i<board.length;i++){
            System.out.print("|");
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==0){
                    System.out.print(" |");
                }
                else {
                    System.out.print(board[i][j]+"|");
                }
            }
            System.out.println();
            for(int k=0;k<board[0].length-1;k++){
                System.out.print("+__");
            }
            System.out.println("+");
        }

    }

    public boolean checkWin(int[][]board){
        int k=1;
        for(int i=0;i< board.length;i++){
            for(int j=0;j< board[0].length;j++){
                if (board[i][j]==k){
                    k++;
                    continue;
                }
                else {
                    if(i==board.length-1 && j==board[0].length-1){
                        break;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean tileCorrect(int tile,int[][]board,int[]zero_coord){
        int m=-5;
        int n=-5;
        int x=zero_coord[0];
        int y =zero_coord[1];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if (board[i][j]==tile){
                    m=i;
                    n=j;
                }
            }
        }
        if ((m+1==x && n==y)||(m-1==x && n==y)||(m==x && n+1==y)||(m==x && n-1==y)){
            board[m][n]=0;
            board[zero_coord[0]][zero_coord[1]]=tile;
            zero_coord[0]=m;
            zero_coord[1]=n;
            return true;
        }
        else{
            return false;
        }
    }



}

public class PlayGame {

    public static void main(String[] args) {
        SlidingPuzzle obj= new SlidingPuzzle();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of rows");
        int m=sc.nextInt();
        System.out.println("Enter the number of columns");
        int n=sc.nextInt();
//      Store coordinates of zero and pass it to required function for faster checking
        int [] zero_coord=new int[2];
//        Creating the game board and checking if the random shuffling is correct
        int[][] board = obj.design_board(m,n,zero_coord);
        while(obj.checkWin(board)){
            System.out.println("System Generated a solved Puzzle! Regenerating...");
            board= obj.design_board(m,n,zero_coord);
        }
        obj.display_board(board);
        while(true){
            System.out.println("Player, which tile do you want to slide to the empty space?");
            int tile=sc.nextInt();

//            Check if tile chosen can be interchanged with zero
            boolean b=obj.tileCorrect(tile,board,zero_coord);
            if(!b){
                System.out.println("You cannot interchange "+tile+" with the empty space");
                continue;
            }
            obj.display_board(board);
            if(obj.checkWin(board)){
                System.out.println("Congratulation! You Win");
                break;
            }
        }
    }
}
