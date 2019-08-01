package uk.ac.sheffield.aca15er.graphing;

import uk.ac.sheffield.aca15er.APIDataSource;
import uk.ac.sheffield.aca15er.DataSource;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class PressureGraph extends Graph {
    public PressureGraph(DataSource dataSource) {
        super();
        setYLabel("Pa");
        add_series(dataSource.getData().stream().map((ob)->new DataPoint((double)ob.getDate().toInstant().getEpochSecond()*1000/*millis*/,ob.getPressure())),"Pressure");
    }
}
