package com.blakebr0.pickletweaks.proxy;

import com.blakebr0.pickletweaks.GuiHandler;
import com.blakebr0.pickletweaks.PickleTweaks;
import com.blakebr0.pickletweaks.config.ModConfig;
import com.blakebr0.pickletweaks.feature.FeatureBowInfo;
import com.blakebr0.pickletweaks.feature.FeatureHoeInfo;
import com.blakebr0.pickletweaks.feature.FeatureRightClickHarvest;
import com.blakebr0.pickletweaks.feature.FeatureSwordInfo;
import com.blakebr0.pickletweaks.feature.FeatureToolInfo;
import com.blakebr0.pickletweaks.feature.item.ItemMagnet;
import com.blakebr0.pickletweaks.feature.item.ItemNightvisionGoggles;
import com.blakebr0.pickletweaks.registry.ModBlocks;
import com.blakebr0.pickletweaks.registry.ModItems;
import com.blakebr0.pickletweaks.registry.ModRecipes;
import com.blakebr0.pickletweaks.tweaks.TweakFlintDrop;
import com.blakebr0.pickletweaks.tweaks.TweakHoeUselessifier;
import com.blakebr0.pickletweaks.tweaks.TweakSharpeningKit;
import com.blakebr0.pickletweaks.tweaks.TweakToolBreaking;
import com.blakebr0.pickletweaks.tweaks.TweakToolUselessifier;
import com.blakebr0.pickletweaks.tweaks.TweakWeaponUselessifier;
import com.blakebr0.pickletweaks.tweaks.tools.ToolTweaks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ModConfig.init(e.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ModConfig());
		MinecraftForge.EVENT_BUS.register(PickleTweaks.REGISTRY);

		ModBlocks.init();
		ModItems.init();

		MinecraftForge.EVENT_BUS.register(new ItemMagnet.Handler());
		MinecraftForge.EVENT_BUS.register(new ItemNightvisionGoggles.Handler());

		MinecraftForge.EVENT_BUS.register(new FeatureToolInfo());
		MinecraftForge.EVENT_BUS.register(new FeatureSwordInfo());
		MinecraftForge.EVENT_BUS.register(new FeatureHoeInfo());
		MinecraftForge.EVENT_BUS.register(new FeatureBowInfo());

		MinecraftForge.EVENT_BUS.register(new FeatureRightClickHarvest());

		MinecraftForge.EVENT_BUS.register(new TweakToolBreaking());
	}

	public void init(FMLInitializationEvent e) {
		ModRecipes.init();
		FMLInterModComms.sendMessage("waila", "register", "com.blakebr0.pickletweaks.feature.FeatureWailaTooltips.callbackRegister");
		NetworkRegistry.INSTANCE.registerGuiHandler(PickleTweaks.instance, new GuiHandler());
		
		if (ModConfig.confSharpeningKits && Loader.isModLoaded("tconstruct")) {
			MinecraftForge.EVENT_BUS.register(new TweakSharpeningKit());
		}
	}

	public void postInit(FMLPostInitializationEvent e) {
		ModConfig.postInit();
		ModRecipes.postInit();

		ToolTweaks.findToolsFromConfig();

		MinecraftForge.EVENT_BUS.register(new TweakFlintDrop());
		MinecraftForge.EVENT_BUS.register(new TweakToolUselessifier());
		MinecraftForge.EVENT_BUS.register(new TweakHoeUselessifier());
		MinecraftForge.EVENT_BUS.register(new TweakWeaponUselessifier());
	}
}
