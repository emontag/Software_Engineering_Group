
public abstract class Player {
   protected int scores=0;
   protected int representation;
   public int getScore(){
      return scores;
   }
   public void setScore(int s){
      scores+=s;
   }
   public int getRepresentation(){
      return representation;
   }
   public void setRepresentation(int s){
      if (s==1 || s==2 ) representation=s;
   }
   

}
