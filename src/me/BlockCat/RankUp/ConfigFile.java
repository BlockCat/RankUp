package me.BlockCat.RankUp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigFile {

	private File file;
	private final HashMap<String, String> d1 = new HashMap<String, String>();
	private final HashMap<String, String> data;

	public ConfigFile (File f) {
		this.file = f;
		load();
		data = d1;
	}
	private void load() {
		try {
			Scanner scan = new Scanner(file);

			while(scan.hasNextLine()) {
				String prop = scan.nextLine();

				if (!prop.contains("="))
					continue;

				String[] props = prop.split("=");				
				d1.put(props[0], props[1]);				
			}
			scan.close();
		} catch (FileNotFoundException e) {			
		}
	}

	public String[] getOrder() {		
		HashMap<String, String> d = data;
		if (d.containsKey("order")) {
			
			return d.get("order").split("-");
			
		} else {
			
			return null;
		}		
	}
	
	public HashMap<String,String> getGroupsMap() {
		HashMap<String, String> temp = (HashMap<String, String>) data.clone();
		
		if (temp.containsKey("order")) {
			temp.remove("order");
		}
		
		return temp;		
	}
	
	public List<String> getGroups() {
		List<String> temp = new ArrayList<String>();
		HashMap<String, String> d = (HashMap<String, String>) data.clone();
		d.remove("order");
		for (Map.Entry<String, String> x : d.entrySet()) {
			temp.add(x.getKey());
		}
		return temp;
	}
	
	public double getCost (String name) {

		if (data.containsKey(name)) {
			return Double.parseDouble(data.get(name));
		} else {
			return 0.0;			
		}
		
	}

}
