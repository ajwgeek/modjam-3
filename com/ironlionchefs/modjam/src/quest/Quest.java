package com.ironlionchefs.modjam.src.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketConsumeOneOfItemID;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketIncrementExperiance;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketPlayerBeginQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketPlayerEndQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketAddItemStackToInventory;
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
	public List<Tracker> trackers;
	public Map<Integer, Integer> blocksPlaced = new HashMap<Integer, Integer>();

	public Quest(String par2Str, String desc, int par3, int par4, ItemStack par5ItemStack, QuestPage list, Quest par6Quest, ItemStack[] toolOut, ItemStack[] requiredItems,
			ItemStack[] reward, Tracker[] trackers)
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
		this.trackers = new ArrayList<Tracker>(Arrays.asList(trackers));
		Validate.isTrue(!this.trackers.isEmpty());
		
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
		PacketDispatcher.sendPacketToServer(new ServerPacketPlayerBeginQuest(player, this).makePacket());
		if (toolOut != null)
		{
			for (ItemStack is : toolOut)
			{
				PacketDispatcher.sendPacketToServer(new ServerPacketAddItemStackToInventory(player, is).makePacket());
			}
		}
	}

	@Override
	public void onQuestEnd(EntityPlayer player, World world)
	{
		if (this.meetsCriteria(player))
		{
			QuestMod.instance.tickHandler.notifier.questComplete(this);
			PacketDispatcher.sendPacketToServer(new ServerPacketPlayerEndQuest(player, this).makePacket());
			PacketDispatcher.sendPacketToServer(new ServerPacketIncrementExperiance(player).makePacket());
			for (ItemStack is : reward)
			{
				PacketDispatcher.sendPacketToServer(new ServerPacketAddItemStackToInventory(player, is).makePacket());
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
		if (trackers.contains(Tracker.ITEMTURNIN) && requiredItems != null)
		{
			for (ItemStack is : requiredItems)
			{
				if (!ep.inventory.hasItemStack(is))
				{
					bad = true;
				}
			}
		}
		if (trackers.contains(Tracker.BLOCKPLACED))
		{
			
		}
		if (trackers.contains(Tracker.BLOCKBROKEN))
		{
		}
		if(trackers.contains(Tracker.ENTITYKILLED))
		{
		}
		return !bad;
	}
}