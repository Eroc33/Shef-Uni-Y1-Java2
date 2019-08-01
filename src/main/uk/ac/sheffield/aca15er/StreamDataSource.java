package uk.ac.sheffield.aca15er;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Reads a csv style datasource of the expected weatherdata format from an inputstream
 */
public class StreamDataSource extends DataSource {
    private List<Observation> data;
    public StreamDataSource(InputStream stream) throws ParseException {
        data = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        String columns = "";
        while(columns.isEmpty()){
            columns = scanner.nextLine();
        }
        scanner.nextLine();
        String[] columnNames = columns.split(",");
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(!line.isEmpty()) {
                data.add(parseObservation(columnNames, line));
            }
        }
    }

    Observation parseObservation(String[] columnNames, String s) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Observation point = new Observation();
        Scanner scanner = new Scanner(s);
        //remove & split at `,`, or `<br/>`
        scanner.useDelimiter(",|(<br \\/>)");
        for(int  i=0;i<columnNames.length && scanner.hasNext();i++) {
            if(columnNames[i].substring(0,4).equals("Time")){
                point.localTime = scanner.next().trim();
                continue;
            }
            switch(columnNames[i]){
                case "TemperatureC":
                    point.temperature = scanner.nextFloat();
                    break;
                case "Dew PointC":
                    point.dewPoint = scanner.nextFloat();
                    break;
                case "Humidity":
                    point.humidity = scanner.nextInt();
                    break;
                case "Sea Level PressurehPa":
                    if(scanner.hasNextFloat()) {
                        point.pressure = scanner.nextFloat();
                    }else {
                        String other = scanner.next().trim();
                        if(other.equals("")){
                            point.pressure = 0;
                        }else{
                            unexpectedValue(other,columnNames[i],scanner);
                        }
                    }
                    break;
                case "VisibilityKm":
                    point.visibility = scanner.nextFloat();
                    break;
                case "Wind Direction":
                    point.windDir = scanner.next().trim();
                    break;
                case "Wind SpeedKm/h":
                    if(scanner.hasNextFloat()) {
                        point.windSpeed = scanner.nextFloat();
                    }else{
                        String other = scanner.next().trim();
                        if(other.equalsIgnoreCase("calm")){
                            //presumably calm == 0
                            point.gustSpeed = 0;
                        }else{
                            unexpectedValue(other,columnNames[i],scanner);
                        }
                    }
                    break;
                case "Gust SpeedKm/h":
                    if(scanner.hasNextFloat()){
                        point.gustSpeed = scanner.nextFloat();
                    }else{
                        String other = scanner.next().trim();
                        if(other.equals("-")||other.isEmpty()){
                            //presumably -- or blank == same as windspeed
                            point.gustSpeed = point.getWindSpeed();
                        }else{
                            unexpectedValue(other,columnNames[i],scanner);
                        }
                    }
                    break;
                case "Precipitationmm":
                    if(scanner.hasNextFloat()) {
                        point.precipitation = scanner.nextFloat();
                    }else {
                        String other = scanner.next().trim();
                        if(other.equalsIgnoreCase("n/a")||other.isEmpty()){
                            //presumably n/a or blank == no rainfall == 0mm precipitation
                            point.precipitation = 0;
                        }else{
                            unexpectedValue(other,columnNames[i],scanner);
                        }
                    }
                    break;
                case "Events":
                    point.events = scanner.next().trim();
                    break;
                case "Conditions":
                    point.conditions = scanner.next().trim();
                    break;
                case "WindDirDegrees":
                    if(scanner.hasNextInt()) {
                        point.windDirDeg = scanner.nextInt();
                    }else{
                        point.windDirDeg = 0;
                        //skip
                        scanner.next();
                    }
                    break;
                case "DateUTC<br />":
                    point.date = dateFormat.parse(scanner.next().trim());
                    break;
                default:
                    System.out.print("[WARN]: unknown field: "+columnNames[i]);
            }
        }
        return point;
    }

    private void unexpectedValue(String value,String column,Scanner scanner) throws ParseException {
        throw new ParseException("Unexpected value '"+value+"' in column '"+column+"'",scanner.match().start());
    }

    public List<Observation> getData(){
        return data;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Observation point : data){
            builder.append(point.toString());
            builder.append("\r\n");
        }
        return builder.toString();
    }
}
