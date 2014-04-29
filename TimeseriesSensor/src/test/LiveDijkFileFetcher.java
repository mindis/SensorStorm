package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import nl.tno.sensortimeseries.fetcher.SensorFetcher;
import nl.tno.sensortimeseries.model.Measurement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;


public class LiveDijkFileFetcher extends SensorFetcher {
	private static final long serialVersionUID = -2668870366211319578L;
	private Logger logger = LoggerFactory.getLogger(LiveDijkFileFetcher.class);
	private BufferedReader bufferedReader;
	private boolean fileEnded;


	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map conf, TopologyContext context) throws Exception {
		super.prepare(conf, context);
		String filename = conf.get(SRCConfig.LIFEDIJK_FILENAME) == null ? "" : (String)conf.get(SRCConfig.LIFEDIJK_FILENAME);
		
		if (!filename.isEmpty()) {
			try {
				bufferedReader = new BufferedReader(new FileReader(filename));
			} catch (FileNotFoundException e) {
				System.out.println("File "+filename+" not found") ;
				bufferedReader = null;
			}
		} else {
			bufferedReader = null;
		}
		
		fileEnded = false;
		if (bufferedReader == null) {
			logger.error("LiveDijkFileFetcher prepare finish. File "+SRCConfig.LIFEDIJK_FILENAME+" can not be read.");
		}
	}

	
	@Override
	protected Measurement getNextSensorMeasurement() {
		if (bufferedReader == null) {
			return null;
		}
		
		try {
			String line = bufferedReader.readLine();
			if (line != null) {
				String[] elements = line.split(",");
				if (elements.length == 3) {
					long timestamp = new Long(elements[0]);
					String sensorid = new String(elements[1]);
					Double value = new Double(elements[2]); 
					Measurement sm = new Measurement(sensorid, timestamp, value, Double.class);
//					System.out.println("Spout upload measurement "+sm);
					return sm;
				}
			} else {
				if (!fileEnded)
					System.out.println("File ended.");
				fileEnded = true;
			}
		} catch (IOException e) {
			System.out.println("File read error : "+e);
		}
		
		return null;
	}
	

}
