package uk.ac.sheffield.aca15er;

import java.util.List;

/**
 * Created by euan on 05/03/2016.
 */
public abstract class DataSource {
    public abstract List<Observation> getData();
    public float getTotalPrecipitiation(){
        return getData().stream().map(Observation::getPrecipitation).reduce(0f,(a, b)->a+b);
    }
}
