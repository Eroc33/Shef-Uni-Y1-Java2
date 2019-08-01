package uk.ac.sheffield.aca15er.graphing;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class SeriesDescriptor {
    private final String name;
    private final Color color;

    public List<DataPoint> getData() {
        return data;
    }

    public double getMaxY() {
        return y_max;
    }

    public double getMaxX() {
        return x_max;
    }

    public double getMinY() {
        return y_min;
    }

    public double getMinX() {
        return x_min;
    }

    public String getName(){
        return name;
    }

    public Color getColor(){
        return color;
    }

    private List<DataPoint> data;
    private double y_max, x_max, y_min, x_min;

    public SeriesDescriptor(List<DataPoint> data,String name){
        this.color = Color.getHSBColor((float)Math.random(),0.75f,0.5f);
        this.name = name;
        y_min = data.stream().map((pt)->pt.y_pos).min(Double::compareTo).orElse(Double.MAX_VALUE);
        x_min = data.stream().map((pt)->pt.x_pos).min(Double::compareTo).orElse(Double.MAX_VALUE);
        y_max = data.stream().map((pt)->pt.y_pos).max(Double::compareTo).orElse(Double.MIN_VALUE);
        x_max = data.stream().map((pt)->pt.x_pos).max(Double::compareTo).orElse(Double.MIN_VALUE);
        this.data = data.stream().sorted((a,b)->Double.compare(a.x_pos,b.x_pos)).collect(Collectors.toList());
    }
}
