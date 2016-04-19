import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class GUI extends JFrame{
   private JButton[] board;
   private Container content;
   private TextArea textarea;
   private Interface inter;//added to due necessity
   public GUI(Interface in){
      inter=in;
      setSize(400,400);
      content=getContentPane();
      setLocation(500,200);
      setResizable(false);
      setTitle("Hollywood Squares");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      //createFileMenu();
      setVisible(true);
      JPanel panel=new JPanel();
      panel.setLayout(new GridLayout(3,3));
      textarea=new TextArea("",100,100,TextArea.SCROLLBARS_VERTICAL_ONLY );
      content.add(panel);
      content.add(textarea);
      setLayout(new GridLayout(1,2));
      board=new JButton[9];
      for(int i=0;i<9;i++) {
         board[i]=new JButton();
         panel.add(board[i]);
         board[i].addActionListener(inter);
         board[i].setBackground(new Color(217,253,255));
      }
      setButton();//did so would reset
   }
   public TextArea getTextArea(){
      return textarea;
   }
   public JButton[] getBoard(){
      return board;
   }
   public void setButton(){
      for(int i=0;i<board.length;i++){
         board[i].setText(Integer.toString(i));
         board[i].setEnabled(true);
      }
   }
   /*public void createFileMenu() {
      JMenuBar menuBar = new JMenuBar();
      menuBar.setOpaque(true);
      menuBar.setBackground(new Color(217,253,255));
      JMenuItem item;//menu option
      JMenu fileMenu = new JMenu("File");//menu name
      fileMenu.setOpaque(true);
      fileMenu.setBackground(new Color(217,253,255));
      item = new JMenuItem("New Game"); //Open
      item.setBackground(new Color(217,253,255));
      item.addActionListener(inter);
      fileMenu.add( item);
      setJMenuBar(menuBar);
      menuBar.add(fileMenu);
   } */

}
