package uk.ac.sheffield.aca15er.graphing;

import uk.ac.sheffield.aca15er.APIDataSource;
import uk.ac.sheffield.aca15er.DataSource;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class WindGraph extends Graph {
    public WindGraph(DataSource dataSource) {
        super();
        setYLabel("Km/h");
        add_series(dataSource.getData().stream().map((ob)->new DataPoint((double)ob.getDate().toInstant().getEpochSecond()*1000/*millis*/,ob.getWindSpeed())),"Wind speed");
        add_series(dataSource.getData().stream().map((ob)->new DataPoint((double)ob.getDate().toInstant().getEpochSecond()*1000/*millis*/,ob.getGustSpeed())),"Gust speed");
    }
}
