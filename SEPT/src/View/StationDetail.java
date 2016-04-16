package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import Model.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;

import Utils.AppDefine;
import Utils.AppStateManager;
import Utils.FavoritesManager;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Detail information UI in Main Screen
 */
public class StationDetail extends JPanel {

	private static final long serialVersionUID = 1L;

	private WebLabel wblblStation;
	private WebLabel wblblHumid;
	private WebLabel wblblState;
	private WebLabel wblblWindSse;
	private WebLabel wblblRainSinceam;
	private WebLabel wblblc;
	private WebLabel wblblPressQmh;
	private WebLabel wblblPress;
	private WebLabel wblblAirTemp;
	private WebLabel wblblDewPoint;
	private WebLabel wblblLastUpdate;
	private DateTimeFormatter dtfOut;
	private WebButton wbtnViewWeatherHistory;
	private WebLabel wblblRemoveFromFavourites;
	private JPanel panel;
	private JPanel panel_1;
	private WebButton wbtnViewChart;
	private MainPanel mainPanel;

	/**
	 * Create the panel.
	 */
	public StationDetail(final MainPanel m) {

		mainPanel = m;

		setBackground(new Color(176, 196, 222));

		setLayout(new MigLayout("", "[30%,grow][grow][30%]", "[grow][][][][][][][][grow][]"));

		panel = new JPanel();
		panel.setVisible(false);
		add(panel, "cell 0 0,grow");

		wblblStation = new WebLabel();
		wblblStation.setText("-");
		wblblStation.setForeground(new Color(255, 69, 0));
		wblblStation.setFont(new Font("Century Gothic", Font.PLAIN, 30));

		add(wblblStation, "cell 0 1 2 1");

		wblblHumid = new WebLabel();
		wblblHumid.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblHumid.setText("-");
		add(wblblHumid, "cell 2 1,aligny bottom");

		wblblState = new WebLabel();
		wblblState.setFont(new Font("Bender", Font.PLAIN, 16));
		wblblState.setText("-");
		add(wblblState, "cell 0 2 2 1");

		wblblWindSse = new WebLabel();
		wblblWindSse.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblWindSse.setText("-");
		add(wblblWindSse, "cell 2 2");

		wblblRainSinceam = new WebLabel();
		wblblRainSinceam.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblRainSinceam.setText("Rain since 9am: -");
		add(wblblRainSinceam, "cell 2 3");

		wblblc = new WebLabel();
		wblblc.setForeground(new Color(255, 255, 255));
		wblblc.setFont(new Font("Futura", Font.PLAIN, 50));
		wblblc.setText("-°C");
		add(wblblc, "cell 1 3 1 3,alignx left,aligny top");

		wblblPressQmh = new WebLabel();
		wblblPressQmh.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblPressQmh.setText("Press QNH hPa: -");
		add(wblblPressQmh, "cell 2 4");

		wblblPress = new WebLabel();
		wblblPress.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblPress.setText("Press MSL hPa: -");
		add(wblblPress, "cell 2 5,aligny top");

		wblblAirTemp = new WebLabel();
		wblblAirTemp.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblAirTemp.setText("App temp: -°C");
		add(wblblAirTemp, "cell 1 6,aligny bottom");

		wbtnViewChart = new WebButton();
		wbtnViewChart.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wbtnViewChart.setDefaultButtonShadeColor(new Color(240, 255, 255));
		wbtnViewChart.setBottomSelectedBgColor(new Color(224, 255, 255));
		wbtnViewChart.setBottomBgColor(new Color(240, 248, 255));
		wbtnViewChart.setDrawShade(false);
		wbtnViewChart.setText("View Chart");
		add(wbtnViewChart, "cell 2 6,alignx left,aligny bottom");

		wblblDewPoint = new WebLabel();
		wblblDewPoint.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wblblDewPoint.setText("Dew Point: -°C");
		add(wblblDewPoint, "cell 1 7");

		dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");

		wbtnViewWeatherHistory = new WebButton();
		wbtnViewWeatherHistory.setText("View Weather History");
		wbtnViewWeatherHistory.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		wbtnViewWeatherHistory.setDefaultButtonShadeColor(new Color(240, 255, 255));
		wbtnViewWeatherHistory.setBottomSelectedBgColor(new Color(224, 255, 255));
		wbtnViewWeatherHistory.setBottomBgColor(new Color(240, 248, 255));
		wbtnViewWeatherHistory.setDrawShade(false);
		add(wbtnViewWeatherHistory, "cell 2 7");

		panel_1 = new JPanel();
		panel_1.setVisible(false);
		add(panel_1, "cell 0 8,grow");

		wblblLastUpdate = new WebLabel();
		wblblLastUpdate.setFont(new Font("Century Gothic", Font.ITALIC, 11));
		wblblLastUpdate.setText("Last update: -");
		add(wblblLastUpdate, "cell 0 9 2 1,aligny bottom");

		wblblRemoveFromFavourites = new WebLabel();
		wblblRemoveFromFavourites.setForeground(Color.RED);
		wblblRemoveFromFavourites.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		wblblRemoveFromFavourites.setText("Remove from Favourites");

		add(wblblRemoveFromFavourites, "cell 2 9,alignx right,aligny bottom");

		addListeners();

		if (AppDefine.currentStation != null) {
			setTexts(AppDefine.currentStation.getName(), AppDefine.currentStation.getState().getName());
		}
	}

	private void addListeners() {

		wbtnViewChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.showState(AppDefine.VIEW_CHART, this.getClass().getName());
			}
		});

		wbtnViewWeatherHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.showState(AppDefine.VIEW_HISTORY, this.getClass().getName());
			}
		});

		wblblRemoveFromFavourites.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					AppDefine.favorites.delete(AppDefine.currentStation);
					FavoritesManager.save(AppDefine.favorites);
				} catch (IOException e1) {
				} finally {
					AppState.getInstance().state = "";
					AppState.getInstance().station = "";
					AppStateManager.trySave();
				}
				mainPanel.frmMain.showMainScreen();
			}
		});

	}

	private void setTexts(String station, String state) {
		wblblStation.setText(station);
		wblblState.setText(state);
	}

	/**
	 * set station information to this Panel
	 */
	public void setStation() {

		Station station = AppDefine.currentStation;
		StationData data = AppDefine.currentStationData;

		if (data.getLatestReadings().size() > 0) {
			LatestReading reading = data.getLatestReadings().get(0);

			// change colours by Temperature
			try {
				if (reading.getAirTemp() < AppDefine.TEMP_FREEZING) {
					setBackground(new Color(176, 196, 222));
					wblblc.setForeground(new Color(255, 255, 255));
				} else if (reading.getAirTemp() < AppDefine.TEMP_COOL) {
					setBackground(new Color(240, 248, 255));
					wblblc.setForeground(new Color(30, 144, 255));
				} else {
					setBackground(new Color(255, 248, 220));
					wblblc.setForeground(new Color(255, 99, 71));
				}
				wblblc.setText(reading.getAirTemp() + "°C");
			} catch (Exception e) {
				setBackground(new Color(176, 196, 222));
				wblblc.setForeground(Color.black);
				wblblc.setText("-°C");
			}

			// set Text
			wblblHumid.setText("Humid: " + reading.getRelativeHumidity() + "%");
			wblblStation.setText(station.getName());
			wblblState.setText(station.getState().getName());
			wblblWindSse.setText("Wind: " + reading.getWindDir() + " " + reading.getWindSpdKmH() + "-"
					+ reading.getWindGustKmH() + " km/h");
			wblblRainSinceam.setText("Rain since 9am: " + reading.getRainTrace() + "mm");
			wblblPressQmh.setText("Press QNH hPa: " + reading.getPressureQNH());
			wblblPress.setText("Press MSL hPa: " + reading.getPressureMSL());
			wblblAirTemp.setText("App temp: " + reading.getApparentTemp() + "°C");
			wblblDewPoint.setText("Dew Point: " + reading.getDewPt() + "°C");
			wblblLastUpdate.setText("Last update: " + dtfOut.print(reading.getLocalDateTime()));

		}
	}

}
