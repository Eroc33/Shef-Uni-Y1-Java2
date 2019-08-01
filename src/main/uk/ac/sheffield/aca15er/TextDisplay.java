package uk.ac.sheffield.aca15er;

import uk.ac.sheffield.aca15er.APIDataSource;
import uk.ac.sheffield.aca15er.Display;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by euan on 27/02/2016.
 */
public class TextDisplay implements Display {
    private final String lineSep = System.getProperty("line.separator");

    /**
     * The set of commands (an enum seems appropirate as the set of commands is fixed, and needs to be switched on)
     */
    public enum Command{
        TEMPERATURE,
        PRESSURE,
        WEATHER,
        WIND,
        TOTAL_PRECIPITATION,
        AVERAGE_TEMPERATURE,
        PRESSURE_TREND,
        QUIT;

        public static List<String> names(){
            List<String> names = new ArrayList<String>();
            for (Command command : values()) {
                names.add(command.name());
            }
            return names;
        }
    }

    //Our input & output
    private DataSource dataSource;

    /**
     * Simple columnwise display
     * @param columns which columns to display
     */
    //TODO: (Could perhaps be refactored/partly refactored into display in some way)
    private void displayColumns(Column[] columns)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date, lastDate = null;
        StringBuilder builder = new StringBuilder();
        for(Observation point : dataSource.getData()){
            date = point.getDate();
            if(!sameDay(date,lastDate)){
                lastDate = date;
                builder.append("data from ").append(dateFormat.format(date)).append(lineSep);
            }
            point.format(builder,columns);
            builder.append(lineSep);
        }
        System.out.println(builder.toString());
    }

    /**
     * Whether `date` falls on the same day as `otherDate` in the gregorian calendar
     * @param date first date to compare
     * @param lastDate seconds date to compare
     * @return whether the dates fall on the same day in the gregorian calendar
     */
    private boolean sameDay(Date date, Date lastDate) {
        if(date == lastDate){
            return true;
        }
        if(date == null || lastDate == null){
            return false;
        }
        if(date.equals(lastDate)){
            return true;
        }
        //all this song & dance because date.getDay etc. are deprecated
        Calendar a = new GregorianCalendar();
        a.setTime(date);
        Calendar b = new GregorianCalendar();
        b.setTime(lastDate);
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR) && a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Send pressure trend data to the display
     */
    private void displayPressureTrend() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date, lastDate = null;
        StringBuilder builder = new StringBuilder();
        float startPressure = 0,endPressure = 0;
        List<Observation> data = dataSource.getData();

        for(int i=0;i<data.size();i++){
            Observation point = dataSource.getData().get(i);
            //start pressure is the first one
            if(i==0){
                startPressure = point.getPressure();
            }
            date = point.getDate();
            //if the next point is not on the same day
            if(!sameDay(date,lastDate) || i==data.size()-1){
                //if the dates are actually set, then do our output
                if(date!=null && lastDate != null) {
                    builder.append("data from ").append(dateFormat.format(date)).append(lineSep)
                            .append("pressure ");
                    if (startPressure == endPressure) {
                        builder.append("static");
                    } else {
                        builder.append(startPressure > endPressure ? "rise" : "fall");
                    }
                    builder.append(" from ").append(startPressure).append(" to ").append(endPressure).append(lineSep);
                }
                //start pressure is also the first one in a new day
                lastDate = date;
                startPressure = point.getPressure();
            }
            endPressure = point.getPressure();
        }
        System.out.println(builder.toString());
    }

    /**
     * Send average temperature data to the display
     */
    private void displayTempMean() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date, lastDate = null;
        StringBuilder builder = new StringBuilder();
        float sum = 0;
        int count = 0;

        for(Observation point : dataSource.getData()){
            date = point.getDate();
            sum += point.getTemperature();
            count++;
            //add an output per day for which we have data
            if(!sameDay(date,lastDate)){
                lastDate = date;
                float mean = sum/count;
                sum = 0;
                count = 0;
                builder.append("data from ").append(dateFormat.format(date)).append(lineSep)
                        .append("Displaying average ").append(Column.TEMPERATURE.columnName).append(lineSep)
                        .append(mean).append(lineSep);
            }
        }
        System.out.println(builder.toString());
    }

    /**
     * Send cumulative precipitation data to the display
     */
    private void displayPrecipSum() {
        float sum = 0;
        for(Observation point : dataSource.getData()){
            sum += point.getPrecipitation();
        }
        StringBuilder builder = new StringBuilder();
        System.out.println(Float.toString(sum));
    }

    /**
     * The repl loop of the viewer
     */
    public void run(){
        System.out.println("What dataSource would you like to display?");
        while(true) {
            System.out.print(">>");
            //Read
            Scanner scanner = new Scanner(System.in);
            String read = scanner.nextLine().trim().toUpperCase().replace(" ", "_");
            while (!isCommand(read)) {
                System.out.println("input not recognised, try again");
                System.out.println();
                System.out.print(">>");
                read = scanner.next().trim().toUpperCase().replace(" ", "_");
            }
            //Evaluate
            Command command = Command.valueOf(read);
            if (command == Command.QUIT) {
                return;
            }
            //Print
            switch (command) {
                case TEMPERATURE:
                    displayColumns(new Column[]{Column.TEMPERATURE});
                    break;
                case PRESSURE:
                    displayColumns(new Column[]{Column.PRESSURE});
                    break;
                case WEATHER:
                    displayColumns(new Column[]{Column.CONDITIONS});
                    break;
                case WIND:
                    displayColumns(new Column[]{Column.WIND_DIR, Column.WIND_SPEED, Column.WIND_DIR_DEG});
                    break;
                case TOTAL_PRECIPITATION:
                    displayPrecipSum();
                    break;
                case AVERAGE_TEMPERATURE:
                    displayTempMean();
                    break;
                case PRESSURE_TREND:
                    displayPressureTrend();
                    break;
                case QUIT:
                    throw new IllegalArgumentException("Quit is not a dataSource command");
            }
        }
    }

    /**
     * Wherther `tok` can be parsed as a valid command
     * @param tok a srting to check if it will be parsed as a valid command
     * @return whether `tok` can be parsed as a valid command
     */
    public boolean isCommand(String tok){
        return Command.names().contains(tok);
    }

    @Override
    public void setSource(DataSource dataSource) {
        this.dataSource = dataSource;
        run();
    }
}
