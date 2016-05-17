package View;

import Model.LatestReading;
import Model.Station;
import Model.StationData;
import Data.DataManager;
import net.miginfocom.swing.MigLayout;
import com.alee.laf.label.WebLabel;

import Utils.StationDataWorker;
import Utils.StationDataWorker.OnTaskCompleteListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A Station Cell for repeating in favorite list.
 */
public final class FavoriteCell extends JPanel implements OnTaskCompleteListener
{
    private static final Color COL_NORMAL = new Color(248, 248, 255);
    private static final Color COL_SELECTED = new Color(88, 186, 199);

    private final WebLabel wblblTemp;

    private final Station station;
    private StationData data;

    private boolean isSelected = false;

    private OnStationSelectListener _listenerSelect;
    private OnDataLoadListener _listenerDataLoad;

    /**
     * Create the panel.
     */
    public FavoriteCell(Station station)
    {
        this.station = station;

        StationDataWorker dataWorker = new StationDataWorker(station);
        dataWorker.setOnTaskCompleteListener(this);
        dataWorker.execute();

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter()
        {
            public final void mouseClicked(MouseEvent e)
            {
                if (_listenerSelect != null)
                    _listenerSelect.onStationSelect(FavoriteCell.this.station, data);
            }

            // hover effect
            public final void mouseEntered(MouseEvent e)
            {
                setBackground(isSelected ? COL_SELECTED : new Color(240, 248, 255));
            }
            public final void mouseExited(MouseEvent e)
            {
                setBackground(isSelected ? COL_SELECTED : COL_NORMAL);
            }
        });

        setBorder(null);
        setBackground(new Color(248, 248, 255));
        setLayout(new MigLayout("", "[grow][][5%][]", "[][][]"));

        WebLabel wblblStation = new WebLabel();
        wblblStation.setForeground(new Color(255, 69, 0));
        wblblStation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        wblblStation.setText(station.getName());
        add(wblblStation, "cell 0 0 1 2,alignx left,grow");

        wblblTemp = new WebLabel();
        wblblTemp.setFont(new Font("Futura", Font.PLAIN, 20));
        wblblTemp.setForeground(new Color(34, 139, 34));

        add(wblblTemp, "cell 1 0 1 3,alignx center,aligny center");

        WebLabel wblblState = new WebLabel();
        wblblState.setFont(new Font("Bender", Font.PLAIN, 12));
        wblblState.setText(station.getState().getName());
        add(wblblState, "cell 0 2");

        setStationData(DataManager.getCachedStationData(station));
    }

    public final void updateSelected(Station selectedStation)
    {
        isSelected = station == selectedStation;
        setBackground(isSelected ? COL_SELECTED : COL_NORMAL);
    }

    private void setStationData(StationData data)
    {
        this.data = data;
        wblblTemp.setText("-");

        if (data != null)
        {
            List<LatestReading> readings = data.getLatestReadings();

            if (readings.size() > 0 && readings.get(0).airTemp != null)
                wblblTemp.setText(readings.get(0).airTemp.toString());
            wblblTemp.setForeground(new Color(34, 139, 34));
        }
        else
            wblblTemp.setForeground(new Color(155, 155, 155));
    }


    public final void setOnStationSelectListener(OnStationSelectListener listener)
    {
        _listenerSelect = listener;
    }
    public final void setOnDataLoadListener(OnDataLoadListener listener)
    {
        _listenerDataLoad = listener;
    }


    /**
     * On success callback for StationWorker
     */
    public final void onTaskSuccess(StationData data)
    {
        if (_listenerDataLoad != null)
            _listenerDataLoad.onDataLoadSuccess(station, data);

        setStationData(data);
    }

    /**
     * On fail call back for StationWorker
     */
    public final void onTaskFail()
    {
        if (_listenerDataLoad != null)
            _listenerDataLoad.onDataLoadFail();
    }


    public interface OnStationSelectListener
    {
        void onStationSelect(Station station, StationData data);
    }
    public interface OnDataLoadListener
    {
        void onDataLoadSuccess(Station station, StationData data);
        void onDataLoadFail();
    }
}
