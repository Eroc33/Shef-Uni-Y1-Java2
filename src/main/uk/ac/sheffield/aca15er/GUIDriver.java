package uk.ac.sheffield.aca15er;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class GUIDriver extends JFrame implements Observer {
    private final Display dataView;
    private ParamSelectDialog select;
    private String ICAOCode;
    private Date date;

    public static void main(String[] args){
        new GUIDriver();
    }

    public GUIDriver(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        select = new ParamSelectDialog(this,false);
        select.watch(this);
        //needs to be set for the window listener to be called
        select.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        select.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);//if the dialog is closed then don't keep the program running
            }
        });
        select.setVisible(true);

        DisplayPanel dataView = new DisplayPanel();
        //start off maximized for best graph viewing
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
        add(dataView);
        this.dataView = dataView;
    }

    @Override
    public void update(Observable o, Object arg) {
        select.setVisible(false);
        ICAOCode = select.getPlace();
        date = select.getDate();
        select = null;//delete it, no longer needed

        APIDataSource dataSource = new APIDataSource(date,ICAOCode);
        //dataSource.prefetch();//start getting the data on a new thread ASAP to reduce user wait times
        this.dataView.setSource(dataSource);
        this.pack();
        this.setVisible(true);
    }
}
