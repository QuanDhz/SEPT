package Utils;

import Model.Station;
import Model.StationData;
import org.joda.time.LocalDateTime;

public final class BOMUtils
{
    private BOMUtils()
    {
        // disallow instantiating
        throw new AssertionError();
    }


    public static StationData getStationData(Station station)
    {
        // TODO implement pseudo code
        // try get json file from web using station.url
        // failing that try get locally cached json file
        // failing that the UI should display an unavailable data message

        return null;
    }

    public static LocalDateTime toDateTime(String bomDt)
    {
        // convert BOM formatted date time to a form recognized by joda time
        String dt = bomDt.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$1-$2-$3T$4:$5:$6");
        return new LocalDateTime(dt);
    }
}