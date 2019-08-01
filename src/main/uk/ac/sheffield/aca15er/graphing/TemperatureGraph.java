package uk.ac.sheffield.aca15er.graphing;

import uk.ac.sheffield.aca15er.DataSource;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class TemperatureGraph extends Graph {
    public TemperatureGraph(DataSource dataSource) {
        super();
        setYLabel("°C");
        add_series(dataSource.getData().stream().map((ob)->new DataPoint((double)ob.getDate().toInstant().getEpochSecond()*1000/*millis*/,ob.getTemperature())),"Temperature");
    }
}
