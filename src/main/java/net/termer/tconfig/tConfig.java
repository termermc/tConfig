package net.termer.tconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tConfig {
	
	/**
	 * Parse a configuration file into a HashMap of values and keys.
	 * Ignores comments and blank lines, as well as comments.
	 * @param configFile - the configuration File
	 * @param separate - the String in the configuration File that separates the key from the value, such as ":"
	 * @param comment - the String in the configuration File that is used to start a comment, such as "#"
	 * @return the HashMap of all keys and values from the configuration file
	 * @throws IOException if reading the configuration File fails
	 * @since 1.0
	 */
	public static HashMap<String,String> parseConfig(File configFile, String separate, String comment) throws IOException {
		HashMap<String,String> map = new HashMap<String,String>();
		// Setup readers
		FileInputStream fin = new FileInputStream(configFile);
		InputStreamReader inputReader = new InputStreamReader(fin);
		BufferedReader reader = new BufferedReader(inputReader);
		
		while(reader.ready()) {
			String ln = reader.readLine();
			if(!ln.startsWith(comment)) {
				if(ln.contains(separate)) {
				String field = ln.split(separate)[0].trim();
					if(!map.containsKey(field)) {
						String value = ln.substring(ln.indexOf(separate)+1).trim();
						map.put(field, value);
					}
				}
			}
		}
			
		// Close all readers
		reader.close();
		inputReader.close();
		fin.close();
		
		return map;
	}
	
	/**
	 * Adds a field (key) and value to config
	 * @param configFile - the configuration File
	 * @param field - the field to add
	 * @param value - the value to add along with the field
	 * @param separate - the String in the configuration File that separates the key from the value, such as ":"
	 * @param comment - the String in the configuration File that is used to start a comment, such as "#"
	 * @throws IOException if reading or writing the configuration File fails
	 * @since 1.0
	 */
	public static void addField(File configFile, String field, String value, String separate, String comment) throws IOException {
		// Read config
		HashMap<String,String> map = parseConfig(configFile, separate, comment);
		
		// Add field and value
		map.put(field, value);
		
		// Make changes to file string
		StringBuilder cfgStr = new StringBuilder();
		for(String key : map.keySet()) {
			if(cfgStr.length()>0) {
				cfgStr.append("\n");
			}
			cfgStr.append(key);
			cfgStr.append(separate);
			cfgStr.append(map.get(key));
		}
		
		// Write changes
		Writer.print(cfgStr.toString(), configFile);
	}
	
	/**
	 * Remove field (key) and value from a configuration File
	 * @param configFile - the configuration File
	 * @param field - the field to remove from the configuration File
	 * @param separate - the String in the configuration File that separates the key from the value, such as ":"
	 * @param comment - the String in the configuration File that is used to start a comment, such as "#"
	 * @throws IOException if reading or writing the configuration File fails
	 * @since 1.0
	 */
	public static void removeField(File configFile, String field, String separate, String comment) throws IOException {
		// Read config
		HashMap<String,String> map = parseConfig(configFile, separate, comment);
		
		// Add field and value
		map.remove(field);
		
		// Make changes to file string
		StringBuilder cfgStr = new StringBuilder();
		for(String key : map.keySet()) {
			if(cfgStr.length()>0) {
				cfgStr.append("\n");
			}
			cfgStr.append(key);
			cfgStr.append(separate);
			cfgStr.append(map.get(key));
		}
		
		// Write changes
		Writer.print(cfgStr.toString(), configFile);
	}
	
	/**
	 * Create configuration File from a Map
	 * @param configFile - the configuration File
	 * @param map - the Map to generate the configuration File from
	 * @param separate - the String in the configuration File that separates the key from the value, such as ":"
	 * @throws IOException if writing the configuration File fails
	 * @since 1.0
	 */
	public static void createConfig(File configFile, Map<String,String> map, String separate) throws IOException {
		// Convert into string
		StringBuilder cfgStr = new StringBuilder();
		for(String key : map.keySet()) {
			if(cfgStr.length()>0) {
				cfgStr.append("\n");
			}
			cfgStr.append(key);
			cfgStr.append(separate);
			cfgStr.append(map.get(key));
		}
		
		// Write config
		Writer.print(cfgStr.toString(), configFile);
	}
	
	/**
	 * Change the value of a field (key)
	 * @param configFile - the configuration File
	 * @param field - the field to change the value of
	 * @param value - the value to apply to the field
	 * @param separate - the String in the configuration File that separates the key from the value, such as ":"
	 * @param comment - the String in the configuration File that is used to start a comment, such as "#"
	 * @throws IOException - if reading or writing the configuration File fails
	 * @since 1.0
	 */
	public static void changeValue(File configFile, String field, String value, String separate, String comment) throws IOException {
		// Read config
		HashMap<String,String> map = parseConfig(configFile, separate, comment);
		
		// change field's value
		map.put(field, value);
		
		// Make changes to file string
		StringBuilder cfgStr = new StringBuilder();
		for(String key : map.keySet()) {
			if(cfgStr.length()>0) {
				cfgStr.append("\n");
			}
			cfgStr.append(key);
			cfgStr.append(separate);
			cfgStr.append(map.get(key));
		}
		
		// Write changes
		Writer.print(cfgStr.toString(), configFile);
	}
	
	/**
	 * Get the lines of a configuration File in an ArrayList
	 * @param configFile - the configuration File
	 * @param comment - the String in the configuration File that is used to start a comment, such as "#"
	 * @return the lines of the configuration File
	 * @throws IOException if reading the configuration File fails
	 * @since 1.0
	 */
	public static ArrayList<String> getLines(File configFile, String comment) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		
		StringBuilder tmpIn = new StringBuilder();
		FileInputStream fin = new FileInputStream(configFile);
		while(fin.available()>0) {
			tmpIn.append((char)fin.read());
		}
		fin.close();
		
		String tmp = tmpIn.toString();
		
		if(tmp.contains("\n")) {
			for(String str : tmp.split("\n")) {
				if(!(str.startsWith(comment) || str.length()<1)) {
					lines.add(str);
				}
			}
		} else {
			if(!(tmp.startsWith(comment) || tmp.length()<1)) {
				lines.add(tmp);
			}
		}
		
		return lines;
	}
	
	/**
	 * Get the lines of a configuration File in an Array
	 * @param configFile - the configuration File
	 * @param comment - the String in the configuration File that is used to start a comment, such as "#"
	 * @return the lines of the configuration File
	 * @throws IOException if reading the configuration File fails
	 * @since 1.0
	 */
	public static String[] getLinesArray(File configFile, String comment) throws IOException {
		return getLines(configFile, comment).toArray(new String[0]);
	}
}
