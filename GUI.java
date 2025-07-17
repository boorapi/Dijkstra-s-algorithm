
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener
{

    JMenuBar menuBar;// menu bar
    JMenu optionsMenu;// each menu
    JMenu nodes;//nodes menu
    
    JMenuItem helpItem;// element in the menu
    JMenuItem exitItem;

    int width = 1800;// width of the window/jframe
    int height = 980;// height of the window/jframe

    GraphicPanel myGraphic;


    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        switch(cmd){
            case "help" : createDialogBox("This is how the program works...", false);
                break;
            case "exit" : System.exit(0);
        }
    }
    //This method will create dialog box 
    //it will take in the text that will be printed out in the box
    //and boolean to see if the box can be edit (add text)
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
        this.getContentPane().setPreferredSize(new Dimension(width, height));//Set the lenght and width of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Exit when close the window

        this.myGraphic = new GraphicPanel();//Create a new graphic panel
        this.getContentPane().add(this.myGraphic, BorderLayout.CENTER);//Add the graphic panel to the center of the window
        
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
        helpItem.addActionListener(this);//Link item to action listener
        optionsMenu.add(helpItem);
        exitItem = new JMenuItem("exit");
        exitItem.addActionListener(this);//Link item to acction listener
        optionsMenu.add(exitItem);
        //menu and menu items for nodes
        nodes = new JMenu("Nodes");
        menuBar.add(nodes);
        for(String name:)

        

        this.pack();
    }
}
