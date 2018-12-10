package net.termer.tconfig.multiconfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.termer.tconfig.tConfig;

/**
 * Utility class to manage a directory of config files
 * @author termer
 * @since 1.1
 */
public class MultiConfig {
	// Config file contents
	private HashMap<String, HashMap<String, String>> _Configs = new HashMap<String, HashMap<String, String>>();
	private HashMap<String, HashMap<String, String>> _Registered = new HashMap<String, HashMap<String, String>>();
	private String _Dir = null;
	private String _Separate = null;
	private String _Comment = null;
	private String _Extension = "ini";
	
	/**
	 * Instantiates a new MultiConfig
	 * File extension is "ini" by default
	 * @param dir the directory to read and store configs
	 * @param separate the config field and value separator String
	 * @param comment the config comment String
	 * @since 1.1
	 */
	public MultiConfig(String dir, String separate, String comment) {
		_Dir = dir;
		if(!_Dir.endsWith("/")) {
			_Dir+='/';
		}
		_Separate = separate;
		_Comment = comment;
	}
	
	/**
	 * Instantiates a new MultiConfig
	 * @param dir the directory to read and store configs
	 * @param separate the config field and value separator String
	 * @param comment the config comment String
	 * @param extension the file extension of config files in this MultiConfig
	 * @since 1.1
	 */
	public MultiConfig(String dir, String separate, String comment, String extension) {
		_Dir = dir;
		if(!_Dir.endsWith("/")) {
			_Dir+='/';
		}
		_Separate = separate;
		_Comment = comment;
		_Extension = extension;
	}
	
	/**
	 * Registers a config to be created if it does not exist with default values
	 * @param name the config name
	 * @param values the default values to be written to the config if it does not exist
	 * @since 1.1
	 */
	public void registerConfig(String name, HashMap<String, String> values) {
		_Registered.put(name, values);
	}
	
	/**
	 * Re-initializes all config files
	 * @throws IOException if creating or reading configs fails
	 * @since 1.1
	 */
	public void refresh() throws IOException {
		// Create configs directory
		File configsDir = new File(_Dir);
		if(!configsDir.exists() || !configsDir.isDirectory()) {
			configsDir.mkdirs();
		}
		
		// Scan for configs
		for(File f : configsDir.listFiles()) {
			if(f.isFile() && f.getName().endsWith('.'+_Extension)) {
				// Extract config name
				String name = f.getName().substring(0, f.getName().length()-1-_Extension.length());
				
				// Create config entry if it doesn't exist
				if(!_Configs.containsKey(name)) {
					_Configs.put(name, new HashMap<String, String>());
				}
				
				// Load the config file
				tConfig.parseConfig(f, _Separate, _Comment);
			}
		}
		
		// Create registered configs if they have not been created
		for(String name : _Registered.keySet()) {
			if(!_Configs.containsKey(name)) {
				create(name, _Registered.get(name));
			}
		}
	}
	
	/**
	 * Initializes all config files
	 * Alias of refresh()
	 * @throws IOException if creating or reading configs fails
	 * @since 1.1
	 */
	public void initialize() throws IOException {
		refresh();
	}
	
	/**
	 * Creates a config file with the specified contents
	 * @param name the config name
	 * @param contents the config's contents
	 * @throws IOException if writing the config file fails
	 * @since 1.1
	 */
	public void create(String name, HashMap<String, String> contents) throws IOException {
		File configFile = new File(_Dir+name+'.'+_Extension);
		tConfig.createConfig(configFile, contents, ":");
		_Configs.put(name, contents);
	}
	
	/**
	 * Returns the config with the specified name
	 * @param name the config name
	 * @return the contents of the config, null if it doesn't exist
	 * @since 1.1
	 */
	public HashMap<String, String> get(String name) {
		return _Configs.get(name);
	}
	
	/**
	 * Returns whether the specified config exists
	 * @param name the name of the config
	 * @return whether it exists
	 */
	public boolean exists(String name) {
		return _Configs.containsKey(name);
	}
	
	/**
	 * Returns all configs
	 * @return all configs
	 * @since 1.1
	 */
	public HashMap<String, HashMap<String, String>> getAll() {
		return _Configs;
	}
}