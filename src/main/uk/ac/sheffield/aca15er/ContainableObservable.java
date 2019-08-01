package uk.ac.sheffield.aca15er;

import java.util.Observable;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class ContainableObservable extends Observable {
    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }
}
