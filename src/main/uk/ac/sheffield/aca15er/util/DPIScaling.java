package uk.ac.sheffield.aca15er.util;

/**
 * Created by euan on 10/05/2016.
 */
public class DPIScaling {
    private static final int dpiScaling;

    static {
        //standard dpi is 86, if we know a different dpi (such as on hidpi screens) use that
        dpiScaling = java.awt.Toolkit.getDefaultToolkit().getScreenResolution()/86;
    }

    public static int getDpiScaling(){
        return dpiScaling;
    }
}
