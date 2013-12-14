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
	public static final Quest logging = (new Quest("Logging", "Cut down 64 logs", 0, 0, new ItemStack(Block.wood), new QuestPageAgriculture(), (Quest) null));
	public static final Quest makeSmallOrchard = (new Quest("Make Small Orchard", "Build a small orchard", 0, 2, new ItemStack(Block.sapling), new QuestPageAgriculture(), logging));
	public static final Quest gatherApples = (new Quest("Gather Apples", "Gather apples from the orchard", 0, 4, new ItemStack(Item.appleRed), new QuestPageAgriculture(), makeSmallOrchard));
	public static final Quest renewableFood = (new Quest("Renewable Food", "Gather 64 seeds", -2, 2, new ItemStack(Item.seeds), new QuestPageAgriculture(), logging));
	public static final Quest makeWheatFarm = (new Quest("Make Wheat Farm", "Make a small wheat farm", -2, 4, new ItemStack(Item.wheat), new QuestPageAgriculture(), renewableFood));
	public static final Quest stealPotatoes = (new Quest("Steal Potatoes", "Raid another village for their potatoes", -4, 4, new ItemStack(Item.poisonousPotato), new QuestPageAgriculture(), makeWheatFarm));
	public static final Quest plantPotatoes = (new Quest("Plant Potatoes", "Plant 16 potato plants", -6, 4, new ItemStack(Item.potato), new QuestPageAgriculture(), stealPotatoes));
	public static final Quest stealCarrots = (new Quest("Steal Carrots", "Raid another village for their carrots", -2, 6, new ItemStack(Item.carrotOnAStick), new QuestPageAgriculture(), makeWheatFarm));
	public static final Quest plantCarrots = (new Quest("Plant Carrots", "Plant 16 carrot plants", -2, 8, new ItemStack(Item.carrot), new QuestPageAgriculture(), stealCarrots));
	public static final Quest findDungeon = (new Quest("Find Dungeon", "Find a dungeon to loot", 2, 0, new ItemStack(Item.saddle), new QuestPageAgriculture(), (Quest) null));
	public static final Quest farmFromHell = (new Quest("Farm From Hell", "Make an XP farm from the spawner", 2, 2, new ItemStack(Block.mobSpawner), new QuestPageAgriculture(), findDungeon));
	public static final Quest gatherGoldApples = (new Quest("Curative", "Search dungeons for a golden apple", 3, 1, new ItemStack(Item.appleGold), new QuestPageAgriculture(), (Quest) null));
	public static final Quest melonSeeds = (new Quest("Melon Farm", "Search dungeons for a melon seeds and plant them", 5, 1, new ItemStack(Block.melon), new QuestPageAgriculture(), (Quest) null));
	public static final Quest pumpkinSeeds = (new Quest("Pumpkin Patch", "Search dungeons for a pumpkin seeds and plant them", 4, 1, new ItemStack(Block.pumpkin), new QuestPageAgriculture(), (Quest) null));

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