package com.dragonphase.Kits.Util;

public final class KitManager {
	private KitManager() {}
	
	public static boolean kitExists(String kitName){
		return Collections.GetKit(kitName) != null;
	}
	
	public static Kit getKit(String kitName){
		return Collections.GetKit(kitName);
	}
	
	public static void addKit(String kitName){
		
	}
	
	public static void removeKit(String kitName){
		if (kitExists(kitName))
			Collections.KitList.remove(Collections.GetKit(kitName));
		else throw new NullPointerException();
	}
}
