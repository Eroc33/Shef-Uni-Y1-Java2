package uk.ac.sheffield.aca15er;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class APIDataSource  extends DataSource {
    private static final String URL_TEMPLATE = "https://www.wunderground.com/history/airport/%s/%s/DailyHistory.html?HideSpecis=1&format=1";
    private final Date date;
    private final String ICAOCode;

    private boolean requestCached  = false;
    private List<Observation> results;

    public APIDataSource(Date date, String ICAOCode){
        this.date = date;
        this.ICAOCode = ICAOCode;
    }


    @Override
    public List<Observation> getData() {
        //making the same request several times shouldn't
        //change the result, so we cache it to prevent accidentally wasting time
        //and un-needed network usage
        if(!requestCached){
            results  = makeRequest();
            requestCached = true;
        }
        return results;
    }

    private List<Observation> makeRequest() {
        try {
            HttpURLConnection connection = (HttpURLConnection) makeURL().openConnection();
            StreamDataSource internalSource = new StreamDataSource(connection.getInputStream());
            return internalSource.getData();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            //TODO: better error handling
        }
        return Collections.emptyList();
    }

    private URL makeURL() throws MalformedURLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String url = String.format(URL_TEMPLATE,ICAOCode,dateFormat.format(date));
        System.out.println(url);
        return new URL(url);
    }
}
