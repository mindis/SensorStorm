package nl.tno.sensorstorm.fetchers;

import java.io.Serializable;
import java.util.Map;

import nl.tno.sensorstorm.particles.DataParticle;
import nl.tno.storm.configuration.api.ExternalStormConfiguration;
import backtype.storm.task.TopologyContext;

/**
 * A fetcher is a retrieves data from a specific source. It is called from the
 * spout and being passed in the spout constructor. The spout also takes care of
 * meta particles and serialization.
 * 
 * @author waaijbdvd
 * 
 */
public interface Fetcher extends Serializable {

	/**
	 * Prepare method for this fetcher. Init streams, open connections, files,
	 * etc. It is called from the spout.open method
	 * 
	 * @param stormConfiguration
	 * @param context
	 * @throws Exception
	 */
	public void prepare(@SuppressWarnings("rawtypes") Map stormNativeConfig,
			ExternalStormConfiguration stormConfiguration,
			TopologyContext context) throws Exception;

	/**
	 * Activate the fetcher. It is called from the spout.activate
	 */
	public void activate();

	/**
	 * Deactivate the fetcher. It is called from the spout.deactivate
	 */
	public void deactivate();

	/**
	 * Main method to return the next particle to be emited by the spout. The
	 * fetcher should declare its DataParticle types using the
	 * FetcherDeclaration annotation.
	 * 
	 * @return Returns the next particle, or null indicating no particle has to
	 *         be emitted.
	 */
	public DataParticle fetchParticle();

}
