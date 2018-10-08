import entities.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andrei on 17/01/2017.
 */
public class GUIMain {
    public JPanel MainPanel;
    public JButton btnSummarize;
    public JButton btnSorted;
    private JComboBox cbAction;
    private JComboBox cbShuttle;
    private JButton btnPerformAction;

    public GUIMain()
    {
        Station station=new Station();
        Shuttle s1=new Shuttle("S1",15);
        Shuttle s2=new Shuttle("S2",12);
        Shuttle s3=new Shuttle("S3",20);
        Shuttle s4=new Shuttle("S4",25);
        Destination d1 = new Destination();
        Route route=new Route();
        route.addDestination(d1);
        Destination d2 = new Destination();
        route.addDestination(d2);
        Destination d3 = new Destination();
        route.addDestination(d3);
        Destination d4 = new Destination();
        route.addDestination(d4);
        s1.route=route;
        s2.route=route;
        s3.route=route;
        s4.route=route;

        s1.populateShuttle();
        s2.populateShuttle();
        s3.populateShuttle();
        s4.populateShuttle();
        station.addShuttle(s1);
        station.addShuttle(s2);
        station.addShuttle(s3);
        station.addShuttle(s4);
        btnSummarize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                station.Summary();
            }
        });
        btnSorted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                station.SortedSummary();
            }
        });
        btnPerformAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbAction.getSelectedIndex()==0)
                {
                    station.ShowShuttle(station.shuttles.get(cbShuttle.getSelectedIndex()));
                }
                else if(cbAction.getSelectedIndex()==1)
                {
                    station.makeShuttle(station.shuttles.get(cbShuttle.getSelectedIndex()), ShuttleState.DOCKED);
                }
                else if(cbAction.getSelectedIndex()==2)
                {
                    station.makeShuttle(station.shuttles.get(cbShuttle.getSelectedIndex()), ShuttleState.DEPARTED);
                }
                else if(cbAction.getSelectedIndex()==3)
                {
                    station.makeShuttle(station.shuttles.get(cbShuttle.getSelectedIndex()), ShuttleState.ARRIVED);
                }
                else if(cbAction.getSelectedIndex()==4)
                {
                    station.showPassengers(station.shuttles.get(cbShuttle.getSelectedIndex()));
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame=new JFrame("Station");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GUIMain().MainPanel);
        frame.pack();
        frame.setVisible(true);


    }
}
