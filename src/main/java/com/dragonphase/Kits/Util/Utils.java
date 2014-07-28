package com.dragonphase.Kits.Util;

import java.util.Arrays;

public class Utils {
    
    public static String[] Trim(String[] args){
    	return Arrays.copyOfRange(args, 1, args.length);
    }
	
	public static String Capitalize(String string){
        String[] nameList = string.toLowerCase().replace("_", " ").replace("-", " ").split(" ");
        String name = "";
        
        for (String word : nameList){
            name += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }
        
        if (name.endsWith(" ")) name = name.substring(0, name.length()-1);
        return name;
    }
}
