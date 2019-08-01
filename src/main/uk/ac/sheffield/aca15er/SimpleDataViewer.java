package uk.ac.sheffield.aca15er;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleDataViewer {

    public static void main(String[] args) {
        String filename = "./WeatherData.txt";
        System.out.println("Welcome to the simple weather record viewer\n" +
                "Reading dataSource from "+filename);

        File file = new File(filename);
        //Open the datasource (model), and display (view) (in this case a file, and console respectively).
        Date date = new Calendar.Builder().setDate(2015,10,4).build().getTime();
        DataSource dataSource = new APIDataSource(date,"EBAM");
        Display disp = new TextDisplay();
        //create the viewer (controller)
        disp.setSource(dataSource);
    }
}