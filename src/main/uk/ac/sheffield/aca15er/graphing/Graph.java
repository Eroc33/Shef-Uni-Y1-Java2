package uk.ac.sheffield.aca15er.graphing;

import uk.ac.sheffield.aca15er.util.DPIScaling;

import java.awt.geom.AffineTransform;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class Graph extends JComponent {

    public static final int PADDING = -15;
    List<SeriesDescriptor> series;
    DateFormat dateFormat;
    private double maxY = 0, maxX = 0, minY = Double.MAX_VALUE, minX = Double.MAX_VALUE;
    String yLabel = "";

    public void setYLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public Graph(){
        series = new ArrayList<>();
        dateFormat = new SimpleDateFormat("kk:mm");
    }

    protected void add_series(Stream<DataPoint> dataPointStream,String name) {
        add_series(dataPointStream.collect(Collectors.toList()),name);
    }

    protected void add_series(List<DataPoint> data,String name) {
        add_series(new SeriesDescriptor(data,name));
    }

    protected void add_series(SeriesDescriptor descriptor) {
        series.add(descriptor);
        //recompute new max & min values for scaling graphs
        maxY = Math.max(maxY,descriptor.getMaxY());
        minY = Math.min(minY,descriptor.getMinY());
        maxX = Math.max(maxX,descriptor.getMaxX());
        minX = Math.min(minX,descriptor.getMinX());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //bigger stroke on high dpi screens so it doesn't look awful
        g2d.setStroke(new BasicStroke(DPIScaling.getDpiScaling()));
        //anti-aliasing so angled lines & text don't look so bad
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawAxis(g2d);
        drawLegend(g2d);
        plotData(g2d);
    }

    private void drawLegend(Graphics2D g) {
        Rectangle clip = g.getClipBounds();
        clip.grow(PADDING*2,PADDING*2);//apply some padding
        int offset = 0;
        double x = clip.getMaxX()-100, y= clip.getMaxY();
        for(SeriesDescriptor seriesDesc : series) {
            g.setColor(seriesDesc.getColor());
            g.drawString(seriesDesc.getName(),(int)x,(int)y+offset);
            offset -= 30;
        }
    }

    private void plotData(Graphics2D g) {
        Rectangle clip = g.getClipBounds();
        clip.grow(PADDING*2, PADDING*2);//apply some padding
        double y_scale = clip.getHeight() / (maxY - minY);
        double x_scale = clip.getWidth() / (maxX - minX);

        for(SeriesDescriptor seriesDesc : series) {
            double last_x = 0, last_y = 0;
            boolean first = true;
            for (DataPoint point : seriesDesc.getData()) {
                if (!first) {
                    g.setColor(seriesDesc.getColor());
                    g.drawLine((int) ((last_x - seriesDesc.getMinX()) * x_scale) - PADDING, (int) ((seriesDesc.getMaxY() - last_y) * y_scale) - PADDING, (int) ((point.x_pos - seriesDesc.getMinX()) * x_scale) - PADDING, (int) ((seriesDesc.getMaxY() - point.y_pos) * y_scale) - PADDING);
                } else {
                    first = false;
                }
                last_x = point.x_pos;
                last_y = point.y_pos;
            }
        }
    }

    private void drawAxis(Graphics2D g) {
        g.setColor(Color.black);
        Rectangle clip = g.getClipBounds();
        clip.grow(PADDING,PADDING);//apply some padding
        g.drawLine(clip.x,(int)clip.getMaxY(),(int)clip.getMaxX(), (int) clip.getMaxY());
        g.drawLine(clip.x,clip.y,clip.x,(int)clip.getMaxY());

        double y_scale = clip.getHeight() / (maxY - minY);
        double x_scale = clip.getWidth() / (maxX - minX);

        // =======================
        // Drawing x-axis & Labels
        // =======================

        //maximum of 10 points of change between markings
        long diff = (int)((maxX-minX)/10);
        //minimum of 1 point of change between markings
        diff = diff == 0? 1: diff;
        for(long i = (long)minX; i<maxX;i+=diff){
            g.drawLine((int) ((i - minX) * x_scale) - PADDING, (int) clip.getMaxY(),(int)((i - minX) * x_scale) - PADDING, (int) clip.getMaxY()+PADDING);
            //save the transform
            AffineTransform orig = g.getTransform();
            int x = (int) ((i - minX) * x_scale) - PADDING +5;
            int y = (int) clip.getMaxY()-5;
            //rotate to better fit the labels
            g.rotate(-45,x,y);
            g.drawString(dateFormat.format(new Date(i))+" UTC",x,y);
            //restore the saved transform
            g.setTransform(orig);
        }

        // =======================
        // Drawing y-axis & Labels
        // =======================

        //maximum of 10 points of change between markings
        diff = (long)((maxY-minY)/10);
        //minimum of 1 point of change between markings
        diff = diff == 0? 1: diff;
        for(long i = (long)minY; i<maxY;i+=diff){
            g.drawLine((int) clip.getMinX(),(int) ((maxY-i) * y_scale) - PADDING, (int) clip.getMinX()+PADDING,(int)((maxY-i) * y_scale) - PADDING);
            //maxY-i because y increases downwards
            g.drawString(""+i+yLabel,(int) clip.getMinX(),(int) ((maxY-i) * y_scale) - PADDING);
        }
    }
}
