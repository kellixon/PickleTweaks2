package com.blakebr0.pickletweaks.tweaks;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TweakToolHarvest {
	
	public static void configure(Configuration config){
		ConfigCategory category = config.getCategory("tweaks");
		String[] values = config.get(category.getName(), "mining_level", new String[0]).getStringList();
		category.get("mining_level").setComment("Here you can override the mining level of tools."
				+ "\n- Syntax: modid:itemid;harvestlevel;toolclass"
				+ "\n- Example: minecraft:stone_pickaxe;3;pickaxe"
				+ "\n- 'toolclass' can be 'pickaxe', 'axe', 'shovel', or 'null'.");
		
		for(String value : values){
			String[] parts = value.split(";");
			
			if(parts.length != 3){
				continue;
			}
			
			String itemName = parts[0];
			int level;
			String tool = parts[2];
			
			try {
				level = Integer.valueOf(parts[1]);
			} catch(NumberFormatException e){
				continue;
			}
			
			if(!ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))){
				continue;
			}
			
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
			
			item.setHarvestLevel(tool, level);
		}
	}
}
