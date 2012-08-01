package me.BlockCat.RankUp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
	
	private static RankUp plugin;
	private static HashMap<String, ConfigFile> mapping = new HashMap<String, ConfigFile>();

	public Configuration(RankUp instance) {
		this.plugin = instance;		
	}
	
	protected static void load() {
		File f = plugin.getDataFolder();
		
		if (!f.exists()) {
			f.mkdirs();
		}
		
		for (String name : f.list()) {
			if(name.endsWith(".group")) {
				String rName = name.replace(".group", "");				
				ConfigFile cf = new ConfigFile(new File(plugin.getDataFolder(), name));
				mapping.put(rName.toLowerCase(), cf);
			}
		}
	}
	
	public static ConfigFile getConfig(String name) {
		if (mapping.containsKey(name)) {
			if (mapping.get(name) != null) {
		return mapping.get(name);
			} else {
				return null;
				
			}
		} else {
			return null;
		}
	}
	
	public static List<String> getLadders() {
		List<String> temp = new ArrayList<String>();
		for (Map.Entry<String, ConfigFile> x : mapping.entrySet()) {
			temp.add(x.getKey());
		}
		return temp;
	}
	
	
	
	

}
