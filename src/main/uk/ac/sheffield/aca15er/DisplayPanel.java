package uk.ac.sheffield.aca15er;

import uk.ac.sheffield.aca15er.graphing.PressureGraph;
import uk.ac.sheffield.aca15er.graphing.TemperatureGraph;
import uk.ac.sheffield.aca15er.graphing.WindGraph;
import uk.ac.sheffield.aca15er.util.DPIScaling;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class DisplayPanel extends JPanel implements Display{

    @Override
    public void setSource(DataSource dataSource) {
        int dpiScaling = DPIScaling.getDpiScaling();

        setMinimumSize(new Dimension(800*dpiScaling,600*dpiScaling));
        setPreferredSize(getMinimumSize());

        //2*2 grid  layout
        setLayout(new GridLayout(2,2));
        //3 quadrants for our graphs
        add(new TemperatureGraph(dataSource));
        add(new PressureGraph(dataSource));
        add(new WindGraph(dataSource));
        JPanel textPanel = new JPanel();
        //layout our text  vertically inn the remaning quadrant
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
        textPanel.setBorder(new EmptyBorder(15,15,15,15));//some paddding
        add(textPanel);
        textPanel.add(new JLabel("Precipitation: "+dataSource.getTotalPrecipitiation() +" mm"));
        //if there's no data show a warning
        if(dataSource.getData().size() == 0){
            JLabel noDataLabel = new JLabel("NO DATA");
            noDataLabel.setForeground(Color.red);
            noDataLabel.setFont(noDataLabel.getFont().deriveFont(Font.BOLD));
            textPanel.add(noDataLabel);
        }
        setVisible(true);
    }
}
