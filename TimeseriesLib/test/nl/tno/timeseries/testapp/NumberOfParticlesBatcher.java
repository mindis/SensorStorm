package nl.tno.timeseries.testapp;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.tno.storm.configuration.api.ZookeeperStormConfigurationAPI;
import nl.tno.timeseries.channels.ParticleCache;
import nl.tno.timeseries.interfaces.Batcher;
import nl.tno.timeseries.interfaces.BatcherException;
import nl.tno.timeseries.interfaces.DataParticle;
import nl.tno.timeseries.interfaces.DataParticleBatch;

public class NumberOfParticlesBatcher implements Batcher, Serializable {

	private static final long serialVersionUID = 2852865728648428422L;
	private int nrOfParticlesToBatch;
	private List<DataParticle> buffer;
	private ParticleCache cache;

	@Override
	public void init(String channelID, ParticleCache cache,
			@SuppressWarnings("rawtypes") Map stormNativeConfig,
			ZookeeperStormConfigurationAPI stormConfiguration) {
		this.cache = cache;

		// TODO haal dit uit de stormConfig
		nrOfParticlesToBatch = 2;

		buffer = new ArrayList<DataParticle>();
	}

	@Override
	public List<DataParticleBatch> batch(DataParticle inputParticle) {
		ArrayList<DataParticleBatch> result = new ArrayList<DataParticleBatch>();

		buffer.add(inputParticle);
		while (buffer.size() >= nrOfParticlesToBatch) {
			DataParticleBatch batchedParticles = new DataParticleBatch(
					buffer.subList(0, nrOfParticlesToBatch));
			buffer.removeAll(batchedParticles);
			for (DataParticle particle : batchedParticles)
				cache.remove(particle);
			result.add(batchedParticles);
		}

		return result;
	}

	@Override
	public void prepareForFirstParticle(long startTimestamp)
			throws BatcherException {
	}

}