import java.util.Random;

import javax.swing.JOptionPane;


public class Round {
   //need a method getActivePlayer for comparison to use to know what to update jbuttons with
   private Interface Model;
   private Player currentPlayer;
   private Player otherPlayer;
   private int[] squares=new int[9];
   //added thing there for reason above
   public Player getActivePlayer(){
      return currentPlayer;
   }
   //switchPlayer should not have a Player argument unneeded 
   public void switchPlayer(Player p){
      Player temp=currentPlayer;
      currentPlayer=otherPlayer;
      otherPlayer=temp;
   }
   public boolean checkSecretSquare(int s){
      if(squares[s]==7) return true;
      else return false;
   }
   public void assignSquare(int s, boolean b){
     if(b && !checkSecretSquare(s)) squares[s]=currentPlayer.getRepresentation();
     else if (b && checkSecretSquare(s)) squares[s]=currentPlayer.getRepresentation()+7;
     else if(!b && !checkOpponentWin(s) && !checkSecretSquare(s)) squares[s]=otherPlayer.getRepresentation();
     else if (!b && !checkOpponentWin(s) && checkSecretSquare(s)) squares[s]=otherPlayer.getRepresentation()+7;
   }
   public boolean checkRoundWin(){
      boolean win=false;
      if((squares[0]==1|| squares[0]==8) &&(squares[1]==1|| squares[1]==8)&& (squares[2]==1|| squares[2]==8))
         win=true;
      else if((squares[3]==1|| squares[3]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[5]==1|| squares[5]==8))
         win=true;
      else if((squares[6]==1|| squares[6]==8)&&(squares[7]==1|| squares[7]==8)&& (squares[8]==1|| squares[8]==8))
         win=true;
      else if((squares[2]==1|| squares[2]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[6]==1|| squares[6]==8))
         win=true;
      else if((squares[0]==1|| squares[0]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[8]==1|| squares[8]==8))
         win=true;
      else if((squares[0]==1|| squares[0]==8)&&(squares[3]==1|| squares[3]==8)&& (squares[6]==1|| squares[6]==8))
         win=true;
      else if((squares[1]==1|| squares[1]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[7]==1|| squares[7]==8))
         win=true;
      else if((squares[2]==1|| squares[2]==8)&&(squares[5]==1|| squares[5]==8)&& (squares[8]==1|| squares[8]==8))
         win=true;
      
      //other winner
      else if((squares[0]==2|| squares[0]==9) &&(squares[1]==2|| squares[1]==9)&& (squares[2]==2|| squares[2]==9))
         win=true;
      else if((squares[3]==2|| squares[3]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[5]==2|| squares[5]==9))
         win=true;
      else if((squares[6]==2|| squares[6]==9)&&(squares[7]==2|| squares[7]==9)&& (squares[8]==2|| squares[8]==9))
         win=true;
      else if((squares[2]==2|| squares[2]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[6]==2|| squares[6]==9))
         win=true;
      else if((squares[0]==2|| squares[0]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[8]==2|| squares[8]==9))
         win=true;
      else if((squares[0]==2|| squares[0]==9)&&(squares[3]==2|| squares[3]==9)&& (squares[6]==2|| squares[6]==9))
         win=true;
      else if((squares[1]==2|| squares[1]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[7]==2|| squares[7]==9))
         win=true;
      else if((squares[2]==2|| squares[2]==9)&&(squares[5]==2|| squares[5]==9)&& (squares[8]==2|| squares[8]==9))
         win=true;
      
      int count1=0, count2=0;
      for(int i=0;i<9;i++){
         if(squares[i]==2 || squares[i]==9) count2++;
         if(squares[i]==1 || squares[i]==8) count1++;
      }
      if(count1>=5) win=true;
      if(count2>=5) win=true;
      if(win){
         synchronized(this){
            notifyAll();
         }
      }
      return win;
   }
   public boolean checkOpponentWin(int sq){
      int [] copy=new int[9];
      for(int i=0;i<copy.length;i++){
         copy[i]=squares[i];
      }
      copy[sq]=otherPlayer.getRepresentation();
      boolean win=false;
      if(otherPlayer.getRepresentation()==1){
         if((copy[0]==1|| copy[0]==8) &&(copy[1]==1|| copy[1]==8)&& (copy[2]==1|| copy[2]==8))
            win=true;
         else if((copy[3]==1|| copy[3]==8)&&(copy[4]==1|| copy[4]==8)&& (copy[5]==1|| copy[5]==8))
            win=true;
         else if((copy[6]==1|| copy[6]==8)&&(copy[7]==1|| copy[7]==8)&& (copy[8]==1|| copy[8]==8))
            win=true;
         else if((copy[2]==1|| copy[2]==8)&&(copy[4]==1|| copy[4]==8)&& (copy[6]==1|| copy[6]==8))
            win=true;
         else if((copy[0]==1|| copy[0]==8)&&(copy[4]==1|| copy[4]==8)&& (copy[8]==1|| copy[8]==8))
            win=true;
         else if((copy[0]==1|| copy[0]==8)&&(copy[3]==1|| copy[3]==8)&& (copy[6]==1|| copy[6]==8))
            win=true;
         else if((copy[1]==1|| copy[1]==8)&&(copy[4]==1|| copy[4]==8)&& (copy[7]==1|| copy[7]==8))
            win=true;
         else if((copy[2]==1|| copy[2]==8)&&(copy[5]==1|| copy[5]==8)&& (copy[8]==1|| copy[8]==8))
            win=true; 
         int count=0;
         for(int i=0;i<9;i++){
            if(squares[i]==1 || squares[i]==8) count++;
         }
         if(count>=5) win=true;
      }
      else{
       //other winner
         if((copy[0]==2|| copy[0]==9) &&(copy[1]==2|| copy[1]==9)&& (copy[2]==2|| copy[2]==9))
            win=true;
         else if((copy[3]==2|| copy[3]==9)&&(copy[4]==2|| copy[4]==9)&& (copy[5]==2|| copy[5]==9))
            win=true;
         else if((copy[6]==2|| copy[6]==9)&&(copy[7]==2|| copy[7]==9)&& (copy[8]==2|| copy[8]==9))
            win=true;
         else if((copy[2]==2|| copy[2]==9)&&(copy[4]==2|| copy[4]==9)&& (copy[6]==2|| copy[6]==9))
            win=true;
         else if((copy[0]==2|| copy[0]==9)&&(copy[4]==2|| copy[4]==9)&& (copy[8]==2|| copy[8]==9))
            win=true;
         else if((copy[0]==2|| copy[0]==9)&&(copy[3]==2|| copy[3]==9)&& (copy[6]==2|| copy[6]==9))
            win=true;
         else if((copy[1]==2|| copy[1]==9)&&(copy[4]==2|| copy[4]==9)&& (copy[7]==2|| copy[7]==9))
            win=true;
         else if((copy[2]==2|| copy[2]==9)&&(copy[5]==2|| copy[5]==9)&& (copy[8]==2|| copy[8]==9))
            win=true;
         int count=0;
         for(int i=0;i<9;i++){
            if(squares[i]==2 || squares[i]==9) count++;
         }
         if(count>=5) win=true;
      }
      
      return win;
      
   }
   public void calculatePoints(){
      Player X,O;
      if(currentPlayer.getRepresentation()==1) {
         X=currentPlayer;
         O=otherPlayer;
      }
      else {
         O=currentPlayer;
         X=otherPlayer;
      }
      int repWin=0;
      if((squares[0]==1|| squares[0]==8) &&(squares[1]==1|| squares[1]==8)&& (squares[2]==1|| squares[2]==8))
         repWin=1;
      else if((squares[3]==1|| squares[3]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[5]==1|| squares[5]==8))
         repWin=1;
      else if((squares[6]==1|| squares[6]==8)&&(squares[7]==1|| squares[7]==8)&& (squares[8]==1|| squares[8]==8))
         repWin=1;
      else if((squares[2]==1|| squares[2]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[6]==1|| squares[6]==8))
         repWin=1;
      else if((squares[0]==1|| squares[0]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[8]==1|| squares[8]==8))
         repWin=1;
      else if((squares[0]==1|| squares[0]==8)&&(squares[3]==1|| squares[3]==8)&& (squares[6]==1|| squares[6]==8))
         repWin=1;
      else if((squares[1]==1|| squares[1]==8)&&(squares[4]==1|| squares[4]==8)&& (squares[7]==1|| squares[7]==8))
         repWin=1;
      else if((squares[2]==1|| squares[2]==8)&&(squares[5]==1|| squares[5]==8)&& (squares[8]==1|| squares[8]==8))
         repWin=1;
      //next part
      else if((squares[0]==2|| squares[0]==9) &&(squares[1]==2|| squares[1]==9)&& (squares[2]==2|| squares[2]==9))
         repWin=2;
      else if((squares[3]==2|| squares[3]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[5]==2|| squares[5]==9))
         repWin=2;
      else if((squares[6]==2|| squares[6]==9)&&(squares[7]==2|| squares[7]==9)&& (squares[8]==2|| squares[8]==9))
         repWin=2;
      else if((squares[2]==2|| squares[2]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[6]==2|| squares[6]==9))
         repWin=2;
      else if((squares[0]==2|| squares[0]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[8]==2|| squares[8]==9))
         repWin=2;
      else if((squares[0]==2|| squares[0]==9)&&(squares[3]==2|| squares[3]==9)&& (squares[6]==2|| squares[6]==9))
         repWin=2;
      else if((squares[1]==2|| squares[1]==9)&&(squares[4]==2|| squares[4]==9)&& (squares[7]==2|| squares[7]==9))
         repWin=2;
      else if((squares[2]==2|| squares[2]==9)&&(squares[5]==2|| squares[5]==9)&& (squares[8]==2|| squares[8]==9))
         repWin=2;
      //one of these conditions must be true for game to have ended
      else{
         int count1=0, count2=0;
         for(int i=0;i<9;i++){
            if(squares[i]==2 || squares[i]==9) count2++;
            if(squares[i]==1 || squares[i]==8) count1++;
         }
         if(count1>=5) repWin=1;;
         if(count2>=5) repWin=2;
      }
      for(int i=0;i<squares.length;i++){
         if(squares[i]==1 && repWin==1) X.setScore(100);
         if(squares[i]==1 && repWin==2) X.setScore(25);
         if(squares[i]==2 && repWin==1) O.setScore(25);
         if(squares[i]==2 && repWin==2) O.setScore(100);
         if(squares[i]==8) X.setScore(50);
         if(squares[i]==9) O.setScore(50);
      }
      
   }
   public Round(Player p1, Player p2, Interface m){
      currentPlayer=p1;
      otherPlayer=p2;
      Model=m;
      Model.assignsRound(this);
      resetBoard();
      JOptionPane.showMessageDialog(null, "Player "+currentPlayer.getRepresentation()+" Goes first");
   }
   public void resetBoard(){
      Random r=new Random();
      for(int i=0;i<9;i++) {
         squares[i]=0;
      }
      int c=r.nextInt(2);
      if(c!=0) {
         r=new Random();
         c=r.nextInt(8);
         squares[c]=7;
      }
      Model.resetBoard();
   }
   
}
