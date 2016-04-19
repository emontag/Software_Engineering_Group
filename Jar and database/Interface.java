import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

//Interface needs to know about Player too otherwise can't utilize its resources
public class Interface implements ActionListener, Runnable{
   private GUI awesomeGUI; 
   private Game awesomeGame; 
   private TextArea awesomeTA; 
   private JButton[] benjaminButtons;
   private Thread awesomeThread; 
   private boolean[] numQuest;
   private Round round; 
   private boolean showedPane;
   private boolean ans;//this is extra needed to keep track of whether answer from database is correct 
   public void actionPerformed(ActionEvent e) {
      String event=e.getActionCommand();
      if(event.equals("New Game")){
         awesomeGame=null;
         round=null;
         awesomeGUI.dispose();;
         new Interface();
      }
      //using else as otherwise it does...
      else{
         if(round.checkSecretSquare(Integer.parseInt(event))){
            JOptionPane.showMessageDialog(null, "Congratulations you chose a secret square");
         }
         String questans=loadQuestion();
         //questans+=loadAnswer(questans);
         boolean answer=chooseAnswer(questans);
         answer=checkAnswer(answer, Integer.parseInt(event));
         round.assignSquare(Integer.parseInt(event), answer);
         if(round.checkRoundWin()) return;
            //need enter for notification to occur
         Player O;
         O=round.getActivePlayer();//get Player O not work as waiting so need to change 
         if(O.getScore()>900) return;
         round.switchPlayer(O);//actual method is this not swapPlayer()
         if(!(O instanceof Computer))  O=round.getActivePlayer();
         if(O instanceof Computer) {
            computerTurn();
            if(round.checkRoundWin()) return;//need enter for notification to occur
            round.switchPlayer(O);
         }    
         else{
            awesomeTA.append("Player "+round.getActivePlayer().getRepresentation()+" turn.\n");
         }
      }
      
   } 
   public String loadQuestion(){
      //using try catch since only way get it to work
      
      try{
           BufferedReader buffer = new BufferedReader(new FileReader("database.txt"));
         boolean used=true;
           //expanding upon detail as needed
           for(int i=0;i<numQuest.length;i++){
              if(numQuest[i]==false) {
                 used=false;
                 break;
              }
           }
           if(used){
              for(int i=0;i<numQuest.length;i++){
                 numQuest[i]=false;
              }
           }
           int num;
           do{
              Random r=new Random();
              num=r.nextInt(numQuest.length);
           }while(numQuest[num]==true);
           numQuest[num]=true;
           String line=buffer.readLine();
           for(int i=0;i<num;i++){
              for(int j=0;j<4;j++) buffer.readLine();
              line=buffer.readLine();
           }
           String question=line+" \n";
           Random r=new Random();
           num=r.nextInt(4);
           line=buffer.readLine();
           for(int i=0;i<num;i++) line=buffer.readLine();
           //Separate answer and whether true for ans
           String[] sp=new String[2];
           sp[0]=line.substring(0, 1);
           sp[1]=line.substring(1, line.length()).trim();
           if(sp[0].equals("T")) ans=true;
           else ans=false;
           buffer.close();
           return question+sp[1];
         
      }catch(IOException e){
         e.printStackTrace();
         System.exit(1);
      }
      return null;
   }
   /*
   
   public String loadAnswer() {
      
   }*/
   //in chooseAnsweer() string must add to string what 'computers' choice is
   //need square otherwise not have way update gui and check for win
   public boolean checkAnswer(boolean choice, int square){
      boolean check;
      if(choice==ans) check=true;
      else check=false;
      //need to figure out which player so requires method
      String rep=Integer.toString(round.getActivePlayer().getRepresentation());
      if(rep.equals("1")) rep="X";
      else rep="O";
      if(check) {
         benjaminButtons[square].setText(rep);
         benjaminButtons[square].setEnabled(false);
         awesomeTA.append("You are right. The square has been assigned to you.\n");
      }
      else if (!check && !round.checkOpponentWin(square)){
         if(rep=="X") rep="O";
         else rep="X";
         benjaminButtons[square].setText(rep);
         benjaminButtons[square].setEnabled(false);
         awesomeTA.append("Wrong. The square has been assigned to the opponent.\n");
      }
      else{
         awesomeTA.append("Wrong. The square has not been assigned.\n");
      }
      
      return check;
   }
   public void run() {
      for(int i=15;i>0;i--){
         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {         }
       //added statement so returns if pane closed
         if(!showedPane) return;
         awesomeTA.append("Timer: "+ Integer.toString(i)+" Seconds Remaining"+"\n"); 
      }
      showedPane=false;
      JOptionPane.getRootFrame().dispose();
   }
   public int computerChooseSquare(){
      if(benjaminButtons[4].isEnabled()) return 4;
      if(benjaminButtons[0].isEnabled()) return 0;
      if(benjaminButtons[2].isEnabled()) return 2;
      if(benjaminButtons[6].isEnabled()) return 6;
      if(benjaminButtons[8].isEnabled()) return 8;
      //int[] array=new int[4];
      int[] array={1,3,5,7};
      int [] a2=new int[4];
      int count=0;
      for(int i=0;i<4;i++){
         if(benjaminButtons[array[i]].isEnabled()){
            a2[count]=array[i];
            count++;
         }
      }
      int c;
      do{
         Random r=new Random();
         c= r.nextInt(a2.length);
         c=array[c];
      }while(!benjaminButtons[c].isEnabled());
      return c;      
   }
   public boolean chooseAnswer(String question){
      awesomeThread=new Thread(this);
      showedPane=true;
      String [] array={"True","False"};
      awesomeThread.start();
      int rep=JOptionPane.showOptionDialog(null, question, "Choice", JOptionPane.YES_NO_CANCEL_OPTION 
                                   , JOptionPane.QUESTION_MESSAGE,null , array, null);
      showedPane=false;
      if(rep==-1) {
         awesomeTA.append("Bad luck! You're wrong!\n");
         ans=true;
         return false;
      }
      boolean b=rep==0;
      if(b==ans) awesomeTA.append("You are Correct!\n");
      else awesomeTA.append("You Fool!\n");
      return b;
   }
   public Interface(){
      startGame();
      return;
   }
   public void startGame(){
      String[] modes={"No Computer", "Have Computer"};
      int rep = JOptionPane.showOptionDialog(null,
              "Choose whether to have a computer!",
              "Mode",
               JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
               null,
               modes,
                null);
      if(rep==-1) return;//adding to exit on abrupt termination to take care of contingencies
      awesomeGUI=new GUI(this);
      //need to initiliize numQuest
      numQuest=new boolean[18];
      for(int i=0;i<numQuest.length;i++) numQuest[i]=false;
      awesomeTA=awesomeGUI.getTextArea();
      benjaminButtons=awesomeGUI.getBoard();
      awesomeTA.append("Rules: Play Hollywood Squares (see internet for more details)\n");
      awesomeGame=new Game(this, rep);
      awesomeTA.append("Game Over! \n");
      Player X=awesomeGame.getPlayerX();
      Player O=awesomeGame.getPlayerO();
      //need to getTextArea to display stuff
      awesomeTA.append("Player 1 score: "+ X.getScore()+"\n");
      awesomeTA.append("Player 2 score: "+ O.getScore()+"\n");
      for(int i=0;i<benjaminButtons.length;i++){
         if(benjaminButtons[i].isEnabled()) benjaminButtons[i].setEnabled(false);//adding so game can't continue;
      }
   }
   public void resetBoard(){
      awesomeGUI.setButton();
   }
   public void computerTurn(){
      int s=computerChooseSquare();
      String q=loadQuestion();
      awesomeTA.append("Computer Question: "+q+"\n");
      Random r=new Random(2);
      int c=r.nextInt();
      boolean choice;
      if(c==0) choice=true;
      else choice=false;
      choice=checkAnswer(choice, s);
      round.assignSquare(s, choice);
      //only displaying unique information to TextArea
   }
   public void assignsRound(Round r){
      round=r;
   }
   



}
