package nl.tno.timeseries.testapp;

import java.util.Map;

import nl.tno.storm.configuration.api.ZookeeperStormConfigurationAPI;
import nl.tno.timeseries.annotation.FetcherDeclaration;
import nl.tno.timeseries.interfaces.DataParticle;
import nl.tno.timeseries.interfaces.Fetcher;
import backtype.storm.task.TopologyContext;

@FetcherDeclaration(outputs = { MyDataParticle.class })
public class MyGracefullShutdownFetcher implements Fetcher {
	private static final long serialVersionUID = -4783593429530609215L;
	long time = 0;

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormNativeConfig,
			ZookeeperStormConfigurationAPI stormConfiguration,
			TopologyContext context) throws Exception {
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

	@Override
	public DataParticle fetchParticle() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		time = time + 1000;
		return new MyDataParticle<Double>("Channel_1", time, 1.0);
	}

}