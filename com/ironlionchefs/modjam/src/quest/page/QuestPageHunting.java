package com.ironlionchefs.modjam.src.quest.page;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.bouncycastle.util.Arrays;

import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.tracker.Tracker;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class QuestPageHunting extends QuestPage implements IQuestPage
{
	/* Kill sheep quest */
	private static ItemStack[] killSheepTools = new ItemStack[] { new ItemStack(Item.swordStone) };
	private static ItemStack[] killSheepItems = new ItemStack[] { new ItemStack(Block.cloth, 3)};
	private static ItemStack[] killSheepReward = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest killSheep = (new Quest("Kill Sheep", "Kill 3 sheep and collect the wool.", 0, 0, new ItemStack(Block.cloth), new QuestPageHunting(), (Quest) null,
			killSheepTools, killSheepItems, killSheepReward, Tracker.ENTITYKILLED, 0, 0, 0, 0));
	/* Sleep quest */
	private static ItemStack[] sleepTools = new ItemStack[] { new ItemStack(Block.cloth, 3), new ItemStack(Block.planks, 3)};
	private static ItemStack[] sleepItems = new ItemStack[] { new ItemStack(Item.bed, 3)};
	private static ItemStack[] sleepReward = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest sleep = (new Quest("Sleep", "Craft a bed from the harvested wool", -2, 2, new ItemStack(Item.bed), new QuestPageHunting(), killSheep,
			sleepTools, sleepItems, sleepReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	@Override
	public String getTitle()
	{
		return "Hunting";
	}

	public Quest getByName(String name)
	{
		for (Quest q : getQuests())
		{
			if (q.getName().equals(name))
			{
				return q;
			}
		}
		return null;
	}

	@Override
	public List<Quest> getQuests()
	{
		List<Quest> quests = new ArrayList<Quest>();
		quests.add(killSheep);
		quests.add(sleep);
		return quests;
	}
}