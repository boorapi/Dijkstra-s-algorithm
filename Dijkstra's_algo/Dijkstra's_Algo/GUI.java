
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GUI extends JFrame implements ActionListener
{
    //Class variable
    
    JMenuBar menuBar;// menu bar
    
    JMenu optionsMenu;// each menu
    
    JMenuItem helpItem;// element in the menu
    JMenuItem exitItem;
    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        switch(cmd){
            case "help" : createDialogBox("This is ow the program works...", false);
                break;
            case "exit" : System.exit(0);
        }
    }
    //This method wl create dialog box 
    //it will take in the text that will be printed out in the box
    //and boolean sto see if the box can be edit (add text)
    public void createDialogBox(String text, boolean edit){
        JDialog box = new JDialog(this);
        box.setBounds(750,380,400,200);
        TextArea area = new TextArea(text);
        if(!edit){
            area.setEditable(false);
        }
        box.add(area);
        box.toFront();
        box.setVisible(true);
        box.setTitle("How does it work?");
    }
    public GUI(){
        setTitle("Dijkstra's Algorithm");// set the title of the window
        this.getContentPane().setPreferredSize(new Dimension(1800, 980));//Set the lenght and width of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Exit when close the window
        //Make the window visible
        this.pack();
        this.toFront();
        this.setVisible(true);
        //setup menu bar
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        //add menu on the menu bar
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        //add items on the menu
        helpItem = new JMenuItem("help");
        optionsMenu.add(helpItem);
        exitItem = new JMenuItem("exit");
        optionsMenu.add(exitItem);
        
    }
}
