package Utils;

import Data.DataManager;
import Model.Station;
import Model.StationData;

import javax.swing.*;

/**
 * Used for downloading BOM station data asynchronously.
 */
public final class StationDataWorker extends SwingWorker<StationData, Void>
{
    private final Station station;
    private OnTaskCompleteListener listener;


    /**
     * Creates a new worker instance.
     *
     * @param station the station for which to obtain data
     */
    public StationDataWorker(Station station)
    {
        this.station = station;
    }


    /**
     * Run in a background thread when {@code execute()} is run.
     *
     * @return the data associated to {@code station}
     * @throws Exception if data is null meaning it could not be obtained
     */
    protected final StationData doInBackground() throws Exception
    {
        StationData data = DataManager.getNetStationData(station);

        // fail when data is null
        if (data == null)
            throw new Exception("Could not download station data");

        return data;
    }

    /**
     * Called when the worker completes. Notifies listeners of success/failure.
     */
    protected final void done()
    {
        // call success or fail depending on result
        try
        {
            Log.info(getClass(), station.getName() + " data downloaded");

            if (listener != null)
                listener.onTaskSuccess(get());
        }
        catch (Exception e)
        {
            Log.warn(getClass(), e.getMessage());

            if (listener != null)
                listener.onTaskFail();
        }
    }


    /**
     * Set the success/fail listener.
     *
     * @param listener the listener to set
     */
    public final void setOnTaskCompleteListener(OnTaskCompleteListener listener)
    {
        this.listener = listener;
    }


    /**
     * The success/fail listener interface.
     */
    public interface OnTaskCompleteListener
    {
        /**
         * Called when data has be obtained successfully.
         *
         * @param data the obtained data
         */
        void onTaskSuccess(StationData data);

        /**
         * Called when data could not be obtained.
         */
        void onTaskFail();
    }
}
