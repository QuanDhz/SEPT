package Model;

import java.util.TreeMap;

public final class State
{
    private final String name;
    private final String abbr;
    private final TreeMap<String, Station> stations = new TreeMap<>();


    public State(String name, String abbr)
    {
        this.name = name;
        this.abbr = abbr;
    }


    public final Station addStation(String name, String url)
    {
        // only add station if it does not already exist
        if (!stations.containsKey(name))
        {
            Station station = new Station(this, name, url);
            stations.put(name, station);
            return station;
        }

        return null;
    }


    public final String getName()
    {
        return name;
    }
    public final String getAbbr()
    {
        return abbr;
    }
    public final Station getStation(String name)
    {
        return stations.get(name);
    }
}
