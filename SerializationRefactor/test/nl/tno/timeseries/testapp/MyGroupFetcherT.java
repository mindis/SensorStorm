package nl.tno.timeseries.testapp;

import java.util.Map;

import nl.tno.timeseries.annotation.FetcherDeclaration;
import nl.tno.timeseries.interfaces.DataParticle;
import nl.tno.timeseries.interfaces.Fetcher;
import backtype.storm.task.TopologyContext;

@FetcherDeclaration(outputs={MeasurementT.class})
public class MyGroupFetcherT implements Fetcher {
	private static final long serialVersionUID = -4783593429530609215L;
	long time = 0;
	private String[] channels = { "S1", "S2", "S3" };
	private int channelIndex = 0;
	
	@Override
	public void prepare(@SuppressWarnings("rawtypes")Map conf, TopologyContext context) throws Exception {
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

	@Override
	public DataParticle fetchParticle() {
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		time = time + 1;
		return new MeasurementT<Double>(getChannel(), time, 1.0);
	}
	
	private String getChannel() {
		String channel = channels[channelIndex];
		channelIndex++;
		if (channelIndex == channels.length) {
			channelIndex = 0;
		}
		return channel;
	}

}
