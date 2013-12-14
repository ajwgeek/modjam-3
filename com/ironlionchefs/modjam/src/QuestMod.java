package com.ironlionchefs.modjam.src;

import java.io.File;

import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.Packet0BasePacket;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "QuestMod", name = "QuestMod", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = { Packet0BasePacket.CHANNEL }, packetHandler = QuestModPacketHandler.class)
public class QuestMod
{
	public static final QuestModTickHandler tickHandler = new QuestModTickHandler();
	public static QuestMod instance;
	
	@SideOnly(Side.CLIENT)
	public static Quest currentQuestForPlayer = null;
	
	public QuestMod()
	{
		instance = this;
	}
	
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new com.ironlionchefs.modjam.src.QuestModEventHandler());
		TickRegistry.registerTickHandler(tickHandler, Side.CLIENT);
		NetworkRegistry.instance().registerGuiHandler(this, new QuestModGuiHandler());
	}
}