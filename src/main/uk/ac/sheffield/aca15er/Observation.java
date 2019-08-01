package uk.ac.sheffield.aca15er;

import java.util.Date;

/**
 * Represents a data point in localTime, which can be read from a datasource
 */
public class Observation {
    String localTime;
    float temperature;
    float dewPoint;
    int humidity;
    float pressure;
    float visibility;
    String windDir;
    float windSpeed;
    float gustSpeed;
    //not clear what precipitation should actually be as it is always N/A in this file, but probably an int as fractions of mm are nor usually used
    float precipitation; //if N/A then that is the same as 0
    String events; //also never set?, can only assume this would be a string
    String conditions;
    int windDirDeg;
    Date date;

    /**
     * Formats an observation as a string consisting of a given set of reading types
     */
    public void format(StringBuilder builder,Column[] columns)
    {
        builder.append(localTime)
                .append(":\t\t");
        for(Column col : columns){
            builder.append(getColumn(col)).append(" ");
        }
    }

    /**
     * Formats a given reading type as a string
     */
    private String getColumn(Column col) {
        switch(col){
            case PRESSURE:
                return Float.toString(pressure);
            case CONDITIONS:
                return conditions;
            case WIND_DIR:
                return windDir;
            case TEMPERATURE:
                return Float.toString(temperature);
            case WIND_SPEED:
                return Float.toString(windSpeed);
            case WIND_DIR_DEG:
                return Integer.toString(windDirDeg);
            case PRECIPITATION:
                return Float.toString(precipitation);
            default:
                //unreachable
                return null;
        }
    }

    public String getLocalTime() {
        return localTime;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getDewPoint() {
        return dewPoint;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getVisibility() {
        return visibility;
    }

    public String getWindDir() {
        return windDir;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getGustSpeed() {
        return gustSpeed;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public String getEvents() {
        return events;
    }

    public String getConditions() {
        return conditions;
    }

    public int getWindDirDeg() {
        return windDirDeg;
    }

    public Date getDate() {
        return date;
    }
}
