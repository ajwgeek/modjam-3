package com.ironlionchefs.modjam.src.quest.page;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.bouncycastle.util.Arrays;

import com.ironlionchefs.modjam.src.quest.Quest;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class QuestPageAgriculture extends QuestPage implements IQuestPage
{
	/* Logging Quest */
	private static ItemStack[] loggingTools = new ItemStack[] { new ItemStack(Item.axeIron) };
	private static ItemStack[] loggingReqdItems = new ItemStack[] { new ItemStack(Block.wood, 64), new ItemStack(Block.sapling, 8) };
	private static ItemStack[] loggingReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest logging = (new Quest("Logging", "Cut down 64 logs and collect 8 saplings", 0, 0, new ItemStack(Block.wood), new QuestPageAgriculture(), (Quest) null,
			loggingTools, loggingReqdItems, loggingReward));
	/* Make Small Orchard Quest */
	private static ItemStack[] makeSmallOrchardTools = new ItemStack[] { new ItemStack(Block.dirt, 8), new ItemStack(Block.fence, 64), new ItemStack(Block.fenceGate, 2) };
	private static ItemStack[] makeSmallOrchardReqdItems = null;
	private static ItemStack[] makeSmallOrchardReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest makeSmallOrchard = (new Quest("Make Small Orchard", "Plant 8 saplings in a small orchard", 0, 2, new ItemStack(Block.sapling),
			new QuestPageAgriculture(), logging, makeSmallOrchardTools, makeSmallOrchardReqdItems, makeSmallOrchardReward));
	/* Gather Apples Quest */
	private static ItemStack[] gatherApplesTools = new ItemStack[] { new ItemStack(Item.dyePowder, 32) };
	private static ItemStack[] gatherApplesReqdItems = new ItemStack[] { new ItemStack(Item.appleRed, 16) };
	private static ItemStack[] gatherApplesReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest gatherApples = (new Quest("Gather Apples", "Gather 16 apples from the orchard", 0, 4, new ItemStack(Item.appleRed), new QuestPageAgriculture(),
			makeSmallOrchard, gatherApplesTools, gatherApplesReqdItems, gatherApplesReward));
	/* Renewable Food Quest */
	private static ItemStack[] renewableFoodTools = null;
	private static ItemStack[] renewableFoodReqdItems = new ItemStack[] { new ItemStack(Item.seeds, 64) };
	private static ItemStack[] renewableFoodReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest renewableFood = (new Quest("Renewable Food", "Gather 64 seeds", -2, 2, new ItemStack(Item.seeds), new QuestPageAgriculture(), logging,
			renewableFoodTools, renewableFoodReqdItems, renewableFoodReward));
	/* Make Wheat Farm Quest */
	private static ItemStack[] makeWheatFarmTools = new ItemStack[] { new ItemStack(Item.hoeIron, 1), new ItemStack(Item.seeds, 64), new ItemStack(Block.dirt, 64),
			new ItemStack(Item.bucketWater, 1) };
	private static ItemStack[] makeWheatFarmReqdItems = null;
	private static ItemStack[] makeWheatFarmReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest makeWheatFarm = (new Quest("Make Wheat Farm", "Plant 64 seeds in a small wheat farm", -2, 4, new ItemStack(Item.wheat), new QuestPageAgriculture(),
			renewableFood, makeWheatFarmTools, makeWheatFarmReqdItems, makeWheatFarmReward));
	/* Steal Potatoes Quest */
	private static ItemStack[] stealPotatoesTools = new ItemStack[] { new ItemStack(Item.swordIron, 1), new ItemStack(Item.beefCooked, 8) };
	private static ItemStack[] stealPotatoesReqdItems = new ItemStack[] { new ItemStack(Item.potato, 32) };
	private static ItemStack[] stealPotatoesReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest stealPotatoes = (new Quest("Steal Potatoes", "Raid another village and steal 32 potatoes.", -4, 4, new ItemStack(Item.poisonousPotato),
			new QuestPageAgriculture(), makeWheatFarm, stealPotatoesTools, stealPotatoesReqdItems, stealPotatoesReward));
	/* Plant Potatoes Quest */
	private static ItemStack[] plantPotatoesTools = new ItemStack[] { new ItemStack(Item.hoeIron, 1), new ItemStack(Item.potato, 32), new ItemStack(Block.dirt, 32),
			new ItemStack(Item.bucketWater, 1) };
	private static ItemStack[] plantPotatoesReqdItems = null;
	private static ItemStack[] plantPotatoesReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest plantPotatoes = (new Quest("Plant Potatoes", "Plant 32 potato plants", -6, 4, new ItemStack(Item.potato), new QuestPageAgriculture(), stealPotatoes,
			plantPotatoesTools, plantPotatoesReqdItems, plantPotatoesReward));
	/* Steal Carrots Quest */
	private static ItemStack[] stealCarrotsTools = new ItemStack[] { new ItemStack(Item.swordIron, 1), new ItemStack(Item.beefCooked, 8) };
	private static ItemStack[] stealCarrotsReqdItems = new ItemStack[] { new ItemStack(Item.carrot, 32) };
	private static ItemStack[] stealCarrotsReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest stealCarrots = (new Quest("Steal Carrots", "Raid another village and steal 32 potatoes", -2, 6, new ItemStack(Item.carrotOnAStick),
			new QuestPageAgriculture(), makeWheatFarm, stealCarrotsTools, stealCarrotsReqdItems, stealCarrotsReward));
	/* Plant Carrots Quest */
	private static ItemStack[] plantCarrotsTools = new ItemStack[] { new ItemStack(Item.hoeIron, 1), new ItemStack(Item.carrot, 32), new ItemStack(Block.dirt, 32),
			new ItemStack(Item.bucketWater, 1) };
	private static ItemStack[] plantCarrotsReqdItems = null;
	private static ItemStack[] plantCarrotsReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest plantCarrots = (new Quest("Plant Carrots", "Plant 32 carrot plants", -2, 8, new ItemStack(Item.carrot), new QuestPageAgriculture(), stealCarrots,
			plantCarrotsTools, plantCarrotsReqdItems, plantCarrotsReward));
	/* Find Dungeon Quest */
	private static ItemStack[] findDungeonTools = new ItemStack[] { new ItemStack(Item.pickaxeDiamond, 1), new ItemStack(Block.torchWood, 64) };
	private static ItemStack[] findDungeonReqdItems = new ItemStack[] { new ItemStack(Block.mobSpawner, 1) };
	private static ItemStack[] findDungeonReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest findDungeon = (new Quest("Find Dungeon", "Find a dungeon and obtain the monster spawner.", 2, 0, new ItemStack(Item.saddle),
			new QuestPageAgriculture(), (Quest) null, findDungeonTools, findDungeonReqdItems, findDungeonReward));
	/* Farm From Hell Quest */
	private static ItemStack[] farmFromHellTools = new ItemStack[] { new ItemStack(Block.mobSpawner, 1), new ItemStack(Block.cobblestone, 64), new ItemStack(Block.glass, 64),
			new ItemStack(Item.bucketWater) };
	private static ItemStack[] farmFromHellReqdItems = null;
	private static ItemStack[] farmFromHellReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest farmFromHell = (new Quest("Farm From Hell", "Create an XP farm from a monster spawner.", 2, 2, new ItemStack(Block.mobSpawner),
			new QuestPageAgriculture(), findDungeon, new ItemStack[] {}, new ItemStack[] {}, new ItemStack[] { new ItemStack(Item.emerald, 4) }));
	/* Gather Golden Apples Quest */
	private static ItemStack[] gatherGoldApplesTools = new ItemStack[] { new ItemStack(Item.pickaxeDiamond, 1), new ItemStack(Block.torchWood, 64) };
	private static ItemStack[] gatherGoldApplesReqdItems = new ItemStack[] {new ItemStack(Item.appleGold, 1)};
	private static ItemStack[] gatherGoldApplesReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest gatherGoldApples = (new Quest("Curative", "Search dungeons for a golden apple", 3, 1, new ItemStack(Item.appleGold), new QuestPageAgriculture(),
			(Quest) null, gatherGoldApplesTools, gatherGoldApplesReqdItems, gatherGoldApplesReward));
	/* Melon Farm Quest */
	private static ItemStack[] melonSeedsTools = new ItemStack[] { new ItemStack(Item.pickaxeDiamond, 1), new ItemStack(Block.torchWood, 64) };
	private static ItemStack[] melonSeedsReqdItems = new ItemStack[] {new ItemStack(Item.melonSeeds, 1)};
	private static ItemStack[] melonSeedsReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest melonSeeds = (new Quest("Melon Farm", "Search dungeons for a melon seeds and plant them", 5, 1, new ItemStack(Block.melon),
			new QuestPageAgriculture(), (Quest) null, melonSeedsTools, melonSeedsReqdItems, melonSeedsReward));
	/* Pumpkin Patch Quest */
	private static ItemStack[] pumpkinSeedsTools = new ItemStack[] { new ItemStack(Item.pickaxeDiamond, 1), new ItemStack(Block.torchWood, 64) };
	private static ItemStack[] pumpkinSeedsReqdItems = new ItemStack[] {new ItemStack(Item.pumpkinSeeds, 1)};
	private static ItemStack[] pumpkinSeedsReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest pumpkinSeeds = (new Quest("Pumpkin Patch", "Search dungeons for a pumpkin seeds and plant them", 4, 1, new ItemStack(Block.pumpkin),
			new QuestPageAgriculture(), (Quest) null, pumpkinSeedsTools, pumpkinSeedsReqdItems, pumpkinSeedsReward));

	@Override
	public String getTitle()
	{
		return "Agriculture";
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
		quests.add(logging);
		quests.add(makeSmallOrchard);
		quests.add(gatherApples);
		quests.add(renewableFood);
		quests.add(makeWheatFarm);
		quests.add(stealPotatoes);
		quests.add(plantPotatoes);
		quests.add(stealCarrots);
		quests.add(plantCarrots);
		quests.add(findDungeon);
		quests.add(farmFromHell);
		quests.add(gatherGoldApples);
		quests.add(melonSeeds);
		quests.add(pumpkinSeeds);
		return quests;
	}
}