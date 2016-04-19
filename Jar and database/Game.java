import java.util.Random;

import javax.swing.JOptionPane;


public class Game {
   
   //need to include computer can't be first for other stuff to work
   //including in constructor int to see if computer there or not
   private Interface Model;
   private Player firstPlayer;
   private Player X;
   private Player O;
   public Game(Interface in, int t){
      Model=in;
      X=new Human();
      X.setRepresentation(1);
      if(t==1) O=new Computer();
      else O=new Human();
      O.setRepresentation(2);
      //need to call determineFirst()
      while(!decideGameWin()){//needs to be not otherwise never enter
         firstPlayer=determineFirst();
         permuteRound();
      }
   }
   public Player determineFirst(){
      //only applies when no computer. If computer human first always.
      if(O instanceof Computer) return X;
      
      if(X.getScore()!=0 || O.getScore()!=0) {
         if(X==firstPlayer) return O;
         else return X;
      }
      else{
         Random r=new Random();
         int c=r.nextInt(2);
         if(c==0) return X;
         else return O;
      }
   }
   public void permuteRound(){
      Round round;
      //System.out.println(firstPlayer.getRepresentation()+ "   "+X.getRepresentation()+"   "+O.getRepresentation());
      if(firstPlayer.getRepresentation()==1) round=new Round(X,O,Model);
      else round=new Round(O,X,Model);
      synchronized(round){
         while(!round.checkRoundWin()){
            try{
               round.wait();
            }catch(InterruptedException e){
               return;
            }
         }
      }
      round.calculatePoints();
      
   }
   public boolean decideGameWin(){
      
      /*if(X.getScore()>900 || O.getScore()>900) {
         return true; 
      }*/
      if(X.getScore()>900 && O.getScore()<=900) return true;
      else if(O.getScore()>900 && X.getScore()<=900) return true;
      else if(X.getScore()>900 && O.getScore()>900) return true;
      else return false;
   }
   public Player getPlayerX(){
      return X;
   }
   public Player getPlayerO(){
      return O;
   }

}
