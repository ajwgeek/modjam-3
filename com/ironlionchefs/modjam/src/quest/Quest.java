package com.ironlionchefs.modjam.src.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestConsumeFromInventory;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestIncrementExperiance;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestPlayerBeginQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestPlayerEndQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestItemAddToInventory;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksBroken;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksPlaced;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.page.QuestPageAgriculture;
import com.ironlionchefs.modjam.src.quest.tracker.Tracker;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatBase;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class Quest implements IQuest
{
	public final int x, y;
	public final Quest parentQuest;
	public final ItemStack questIcon;
	private final String questDescription;
	private final String questName;
	private QuestPage page;
	public boolean complete;
	public ItemStack[] toolOut;
	public ItemStack[] requiredItems;
	public ItemStack[] reward;
	public Tracker tracker;
	public int blocksPlaced;
	public int neededToBePlaced;
	public int neededToBePlacedItemID;
	public int neededToBeBroken;
	public int neededToBeBrokenItemID;
	public int blocksBroken;

	public Quest(String par2Str, String desc, int par3, int par4, ItemStack par5ItemStack, QuestPage list, Quest par6Quest, ItemStack[] toolOut, ItemStack[] requiredItems,
			ItemStack[] reward, Tracker tracker, int neededToBePlaced, int neededToBePlacedItemID, int neededToBeBroken, int neededToBeBrokenItemID)
	{
		this.questIcon = par5ItemStack;
		this.questDescription = desc;
		this.x = par3;
		this.y = par4;
		this.questName = par2Str;
		this.parentQuest = par6Quest;
		this.page = list;
		this.toolOut = toolOut;
		this.requiredItems = requiredItems;
		this.reward = reward;
		this.tracker = tracker;
		this.neededToBePlaced = neededToBePlaced;
		this.neededToBePlacedItemID = neededToBePlacedItemID;
		this.neededToBeBroken = neededToBeBroken;
		this.neededToBeBrokenItemID = neededToBeBrokenItemID;
	}

	@SideOnly(Side.CLIENT)
	public String getDescription()
	{
		return this.questDescription;
	}

	@SideOnly(Side.CLIENT)
	public String getName()
	{
		return this.questName;
	}

	@Override
	public void onQuestBegin(EntityPlayer player, World world)
	{
		QuestMod.instance.tickHandler.notifier.questStarted(this);
		PacketDispatcher.sendPacketToServer(new PacketRequestPlayerBeginQuest(player, this).makePacket());
		if (toolOut != null)
		{
			for (ItemStack is : toolOut)
			{
				PacketDispatcher.sendPacketToServer(new PacketRequestItemAddToInventory(player, is).makePacket());
			}
		}
	}

	@Override
	public void onQuestEnd(EntityPlayer player, World world)
	{
		if (this.meetsCriteria(player))
		{
			QuestMod.instance.tickHandler.notifier.questComplete(this);
			PacketDispatcher.sendPacketToServer(new PacketRequestPlayerEndQuest(player, this).makePacket());
			PacketDispatcher.sendPacketToServer(new PacketRequestIncrementExperiance(player).makePacket());
			for (ItemStack is : reward)
			{
				PacketDispatcher.sendPacketToServer(new PacketRequestItemAddToInventory(player, is).makePacket());
			}
		}
		else
		{
			return;
		}
	}

	@Override
	public QuestPage getPage()
	{
		return page;
	}

	@Override
	public boolean meetsCriteria(EntityPlayer ep)
	{
		boolean bad = false;
		if (tracker == Tracker.ITEMTURNIN && requiredItems != null)
		{
			for (ItemStack is : requiredItems)
			{
				if (!ep.inventory.hasItemStack(is))
				{
					bad = true;
				}
			}
		}
		if (tracker == Tracker.BLOCKPLACED)
		{
			PacketDispatcher.sendPacketToServer(new PacketRequestTotalBlocksPlaced(ep, this, this.neededToBePlacedItemID).makePacket());
			if (this.neededToBePlaced > blocksPlaced)
			{
				bad = true;
			}
		}
		if (tracker == Tracker.BLOCKBROKEN)
		{
			PacketDispatcher.sendPacketToServer(new PacketRequestTotalBlocksBroken(ep, this, this.neededToBeBrokenItemID).makePacket());
			if (this.neededToBeBroken > blocksBroken)
			{
				bad = true;
			}
		}
		if(tracker == Tracker.ENTITYKILLED)
		{
		}
		return !bad;
	}
}