package nl.tno.storm.configuration.impl;

import java.util.Map;

import nl.tno.storm.configuration.api.ConfigurationListener;
import nl.tno.storm.configuration.api.ZookeeperStormConfigurationAPI;
import nl.tno.storm.configuration.api.StormConfigurationException;

public class LocalStormConfiguration implements ZookeeperStormConfigurationAPI {

	@Override
	public Map<String, String> getTopologyConfiguration() throws StormConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerTopologyConfigurationListener(ConfigurationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterTopologyConfigurationListener(ConfigurationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getTaskConfiguration(String taskId) throws StormConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerTaskConfigurationListener(String taskId, ConfigurationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterTaskConfigurationListener(String taskId, ConfigurationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getStreamConfiguration(String streamId) throws StormConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerStreamConfigurationListener(String streamId, ConfigurationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterStreamConfigurationListener(String streamId, ConfigurationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getChannelConfiguration(String channelId)
			throws StormConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerChannelConfigurationListener(String channelId,
			ConfigurationListener listener) throws StormConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterChannelConfigurationListener(String channelId,
			ConfigurationListener listener) throws StormConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getOperationConfiguration(String operationId)
			throws StormConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerOperationConfigurationListener(String operationId,
			ConfigurationListener listener) throws StormConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterOperationConfigurationListener(String operationId,
			ConfigurationListener listener) throws StormConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getFetcherConfiguration(String fetcherId)
			throws StormConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerFetcherConfigurationListener(String fetcherId,
			ConfigurationListener listener) throws StormConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterFetcherConfigurationListener(String fetcherId,
			ConfigurationListener listener) throws StormConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setNativeStormConfig(Map nativeStormConfig) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getNativeStormConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}