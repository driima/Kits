package com.dragonphase.kits.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;

public final class Utils {
    
    private Utils(){}
    
    public static <T> T[] trim(T[] args){
    	return Arrays.copyOfRange(args, 1, args.length);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] clean(T[] args){
        List<T> list = new ArrayList<T>(Arrays.asList(args));
        list.removeAll(Arrays.asList(null,""));
        return (T[]) list.toArray();
    }
	
	public static String capitalize(String string){
        return WordUtils.capitalizeFully(string);
    }
	
	public static String getLocationationAsString(Location location){
	    return "world: " + location.getWorld().getName() + ", x: " + location.getBlockX() + ", y: "+location.getBlockY()+", z: " + location.getBlockZ();
	}
}
