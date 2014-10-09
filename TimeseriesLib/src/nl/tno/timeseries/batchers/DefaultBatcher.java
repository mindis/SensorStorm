package nl.tno.timeseries.batchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tno.storm.configuration.api.ZookeeperStormConfigurationAPI;
import nl.tno.timeseries.channels.ParticleCache;
import nl.tno.timeseries.interfaces.Batcher;
import nl.tno.timeseries.interfaces.BatcherException;
import nl.tno.timeseries.interfaces.DataParticle;
import nl.tno.timeseries.interfaces.DataParticleBatch;
import nl.tno.timeseries.mapper.annotation.TupleField;
import backtype.storm.tuple.Fields;

public abstract class DefaultBatcher implements Batcher {

	private HashMap<String, List<DataParticle>> particleSets;
	private final ArrayList<Field> fields;

	public DefaultBatcher(Fields groupBy, Class<? extends DataParticle> clazz) {
		this.fields = new ArrayList<Field>();
		for (Field f : clazz.getDeclaredFields()) {
			f.setAccessible(true);
			for (Annotation a : f.getAnnotations()) {
				if (a instanceof TupleField) {
					String name = ((TupleField) a).name();
					if (name == null || name.length() == 0) {
						name = f.getName();
					}
					if (groupBy.contains(name))
						fields.add(f);
				}
			}
		}
	}

	@Override
	public void init(String channelID, long startTimestamp,
			Map stormNativeConfig,
			ZookeeperStormConfigurationAPI zookeeperStormConfiguration)
			throws BatcherException {
		this.init(channelID, startTimestamp, stormNativeConfig,
				zookeeperStormConfiguration);
	}

	/**
	 * Generates the key for the provided tuple using the fields provided at
	 * construction time
	 * 
	 * @param tuple
	 * @return key created for this tuple or NULL if no key could be created
	 *         (i.e. tuple does not contain any of groupBy Fields)
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private String generateGroup(DataParticle particle)
			throws IllegalArgumentException, IllegalAccessException {
		String group = new String();
		for (Field field : fields) {
			group += "_" + field.get(particle);
		}
		return group;
	}

	@Override
	public List<DataParticleBatch> batch(ParticleCache cache,
			DataParticle particle) throws BatcherException {
		return null;
	}

}