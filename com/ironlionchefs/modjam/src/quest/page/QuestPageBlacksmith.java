package com.ironlionchefs.modjam.src.quest.page;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.bouncycastle.util.Arrays;

import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.tracker.Tracker;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class QuestPageBlacksmith extends QuestPage implements IQuestPage
{
	/* Mining Stone */
	private static ItemStack[] miningStoneTools = null;
	private static ItemStack[] miningStoneItems = new ItemStack[] { new ItemStack(Block.cobblestone, 64) };
	private static ItemStack[] miningStoneReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest miningStone = (new Quest("Mining Stone", "Mine a full stack of cobblestone", 0, 0, new ItemStack(Block.cobblestone), new QuestPageBlacksmith(), (Quest) null,
			miningStoneTools, miningStoneItems, miningStoneReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Stone Pick */
	private static ItemStack[] stonePickTools = new ItemStack[] { new ItemStack(Block.cobblestone, 3) };
	private static ItemStack[] stonePickItems = new ItemStack[] { new ItemStack(Item.pickaxeStone, 1) };
	private static ItemStack[] stonePickRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest stonePick = (new Quest("Stone Pick", "Craft a stone pickaxe", -2, 2, new ItemStack(Item.pickaxeStone), new QuestPageBlacksmith(), (Quest) miningStone,
			stonePickTools, stonePickItems, stonePickRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Stone Shovel */
	private static ItemStack[] stoneShovelTools = new ItemStack[] { new ItemStack(Block.cobblestone, 1) };
	private static ItemStack[] stoneShovelItems = new ItemStack[] { new ItemStack(Item.shovelStone, 1) };
	private static ItemStack[] stoneShovelRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest stoneShovel = (new Quest("Stone Shovel", "Craft a stone shovel", 0, 2, new ItemStack(Item.shovelStone), new QuestPageBlacksmith(), (Quest) miningStone,
			stoneShovelTools, stoneShovelItems, stoneShovelRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));	
	
	/* Cooking */
	private static ItemStack[] cookingTools = new ItemStack[] { new ItemStack(Block.cobblestone, 8) };
	private static ItemStack[] cookingItems = new ItemStack[] { new ItemStack(Block.furnaceIdle, 1) };
	private static ItemStack[] cookingRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest cooking = (new Quest("Cooking", "Craft a furnace", 2, 2, new ItemStack(Block.furnaceIdle), new QuestPageBlacksmith(), (Quest) miningStone,
			cookingTools, cookingItems, cookingRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));	
	
	/* Mining iron */
	private static ItemStack[] miningIronTools = null;
	private static ItemStack[] miningIronItems = new ItemStack[] { new ItemStack(Block.oreIron, 32) };
	private static ItemStack[] miningIronRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest miningIron = (new Quest("Mining Iron", "Mine 32 iron ore", -2, 4, new ItemStack(Block.oreIron), new QuestPageBlacksmith(), (Quest) stonePick,
			miningIronTools, miningIronItems, miningIronRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Iron Pick */
	private static ItemStack[] ironPickTools = new ItemStack[] { new ItemStack(Item.ingotIron, 3) };
	private static ItemStack[] ironPickItems = new ItemStack[] { new ItemStack(Item.pickaxeIron, 1) };
	private static ItemStack[] ironPickRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest ironPick = (new Quest("Upgrading", "Craft an Iron Pickaxe", -2, 6, new ItemStack(Item.pickaxeIron), new QuestPageBlacksmith(), (Quest) miningIron,
			ironPickTools, ironPickItems, ironPickRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Crystal Blue */
	private static ItemStack[] brightBlueTools = null;
	private static ItemStack[] brightBlueItems = new ItemStack[] { new ItemStack(Item.diamond, 1) };
	private static ItemStack[] brightBlueRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest brightBlue = (new Quest("Crystal Blue", "Mine 1 diamond", -4, 8, new ItemStack(Item.diamond), new QuestPageBlacksmith(), (Quest) ironPick,
			brightBlueTools, brightBlueItems, brightBlueRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Circuit */
	private static ItemStack[] circuitTools = null;
	private static ItemStack[] circuitItems = new ItemStack[] { new ItemStack(Item.redstone, 64) };
	private static ItemStack[] circuitRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest circuit = (new Quest("Circuitry", "Mine a stack of redstone", -2, 8, new ItemStack(Item.redstone), new QuestPageBlacksmith(), (Quest) ironPick,
			circuitTools, circuitItems, circuitRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Shiny Pebbles */
	private static ItemStack[] shinyPebblesTools = null;
	private static ItemStack[] shinyPebblesItems = new ItemStack[] { new ItemStack(Block.oreGold, 8) };
	private static ItemStack[] shinyPebblesRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest shinyPebbles = (new Quest("Shiny Pebbles", "Mine 8 gold ore", 0, 8, new ItemStack(Block.oreGold), new QuestPageBlacksmith(), (Quest) ironPick,
			shinyPebblesTools, shinyPebblesItems, shinyPebblesRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Music */
	private static ItemStack[] musicTools = new ItemStack[] { new ItemStack(Item.diamond, 1) };
	private static ItemStack[] musicItems = new ItemStack[] { new ItemStack(Block.jukebox, 8) };
	private static ItemStack[] musicRewards = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest music = (new Quest("Music", "Create a jukebox", -2, 10, new ItemStack(Block.jukebox), new QuestPageBlacksmith(), (Quest) brightBlue,
			musicTools, musicItems, musicRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Mining Coal */
	private static ItemStack[] miningCoalTools = null;
	private static ItemStack[] miningCoalItems = new ItemStack[] { new ItemStack(Item.coal, 64) };
	private static ItemStack[] miningCoalReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest miningCoal = (new Quest("Mining Coal", "Mine a full stack of coal", 2, 0, new ItemStack(Item.coal), new QuestPageBlacksmith(), (Quest) null,
			miningCoalTools, miningCoalItems, miningCoalReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	/* Light Source */
	private static ItemStack[] lightTools = new ItemStack[] { new ItemStack(Item.coal, 64) };
	private static ItemStack[] lightItems = new ItemStack[] { new ItemStack(Block.torchWood, 64) };
	private static ItemStack[] lightReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest light = (new Quest("Light Source", "Craft a stack of torches", 4, 0, new ItemStack(Block.torchWood), new QuestPageBlacksmith(), (Quest) miningCoal,
			lightTools, lightItems, lightReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));
	
	@Override
	public String getTitle()
	{
		return "Blacksmithing";
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
		quests.add(miningStone);
		quests.add(stonePick);
		quests.add(stoneShovel);
		quests.add(cooking);
		quests.add(miningIron);
		quests.add(ironPick);
		quests.add(brightBlue);
		quests.add(circuit);
		quests.add(shinyPebbles);
		quests.add(music);
		quests.add(miningCoal);
		quests.add(light);
		return quests;
	}
}