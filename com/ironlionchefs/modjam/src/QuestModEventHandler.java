package com.ironlionchefs.modjam.src;

import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.gui.GuiQuestMap;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateTotalBlocksPlaced;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksBrokenUpdate;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksPlacedUpdate;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.page.QuestPageAgriculture;
import com.ironlionchefs.modjam.src.quest.tracker.Tracker;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;

public class QuestModEventHandler
{
	@ForgeSubscribe
	public void onEntityInteractEvent(EntityInteractEvent event)
	{
		if (event.target instanceof EntityVillager)
		{
			EntityVillager ev = (EntityVillager) event.target;
			event.entityPlayer.openGui(QuestMod.instance, ev.getProfession(), event.entityPlayer.worldObj, (int) event.entityPlayer.posX, (int) event.entityPlayer.posY,
					(int) event.entityPlayer.posZ);
			event.setCanceled(true);
		}
	}

	@ForgeSubscribe
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		if (QuestMod.currentQuestForPlayer != null)
		{
			if (QuestMod.currentQuestForPlayer.tracker == Tracker.BLOCKBROKEN && event.block.blockID == QuestMod.currentQuestForPlayer.neededToBeBrokenItemID)
			{
				PacketDispatcher.sendPacketToServer(new PacketRequestTotalBlocksBrokenUpdate(event.getPlayer(), QuestMod.currentQuestForPlayer, event.block.blockID,
						QuestMod.currentQuestForPlayer.blocksBroken + 1).makePacket());
			}
		}
	}

	@ForgeSubscribe
	public void onBlockPlace(PlayerInteractEvent event)
	{
		if (QuestMod.currentQuestForPlayer != null)
		{
			if (event.entityPlayer.getHeldItem() != null)
			{
				if (event.action == Action.RIGHT_CLICK_BLOCK && (event.entityPlayer.getHeldItem().itemID != 0))
				{
					if (QuestMod.currentQuestForPlayer.tracker == Tracker.BLOCKPLACED
							&& event.entityPlayer.getHeldItem().itemID == QuestMod.currentQuestForPlayer.neededToBePlacedItemID)
					{
						PacketDispatcher.sendPacketToServer(new PacketRequestTotalBlocksPlacedUpdate(event.entityPlayer, QuestMod.currentQuestForPlayer, event.entityPlayer
								.getHeldItem().itemID, QuestMod.currentQuestForPlayer.blocksPlaced + 1).makePacket());
					}
				}
			}
		}
	}
}