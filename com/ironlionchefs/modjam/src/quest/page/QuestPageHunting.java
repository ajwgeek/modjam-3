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

/*
 * Quests given by the butcher villager
 */
public class QuestPageHunting extends QuestPage implements IQuestPage
{
	/* Kill sheep quest */
	private static ItemStack[] killSheepTools = null;
	private static ItemStack[] killSheepItems = new ItemStack[] { new ItemStack(Block.cloth, 3) };
	private static ItemStack[] killSheepReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest killSheep = (new Quest("Shearing", "Kill 3 sheep and collect the wool.", 0, 0, new ItemStack(Block.cloth), new QuestPageHunting(), (Quest) null,
			killSheepTools, killSheepItems, killSheepReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));

	/* Sleep quest */
	private static ItemStack[] sleepTools = new ItemStack[] { new ItemStack(Block.cloth, 3), new ItemStack(Block.planks, 3) };
	private static ItemStack[] sleepItems = new ItemStack[] { new ItemStack(Item.bed, 3) };
	private static ItemStack[] sleepReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest sleep = (new Quest("Counting Sheep", "Craft a bed from harvested wool", -2, 2, new ItemStack(Item.bed), new QuestPageHunting(), killSheep,
			sleepTools, sleepItems, sleepReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));

	/* Colorful quest */
	private static ItemStack[] colorfulTools = null;
	private static ItemStack[] colorfulItems = new ItemStack[] { new ItemStack(Item.dyePowder, 16) };
	private static ItemStack[] colorfulReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest colorful = (new Quest("Colorful", "Obtain 16 dye powder", 0, 2, new ItemStack(Item.dyePowder), new QuestPageHunting(), killSheep, colorfulTools,
			colorfulItems, colorfulReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));

	/* Nice Floors quest */
	private static ItemStack[] niceFloorsTools = new ItemStack[] { new ItemStack(Item.dyePowder, 16) };
	private static ItemStack[] niceFloorsItems = new ItemStack[] { new ItemStack(Block.carpet, 16) };
	private static ItemStack[] niceFloorsReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest niceFloors = (new Quest("Nice Floors", "Craft 16 carpets", 0, 4, new ItemStack(Block.carpet), new QuestPageHunting(), killSheep, niceFloorsTools,
			colorfulItems, colorfulReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));

	/* Kill Cows quest */
	private static ItemStack[] killCowsTools = null;
	private static ItemStack[] killCowsItems = new ItemStack[] { new ItemStack(Item.beefRaw, 3) };
	private static ItemStack[] killCowsReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest killCows = (new Quest("Kill Cows", "Collect 3 steaks", 2, 0, new ItemStack(Item.beefRaw), new QuestPageHunting(), null, killCowsTools, killCowsItems,
			killCowsReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));

	/* T-Bone Steak Quest */
	private static ItemStack[] tBoneSteakTools = new ItemStack[] {new ItemStack(Item.beefRaw, 3)};
	private static ItemStack[] tBoneSteakItems = new ItemStack[] { new ItemStack(Item.beefCooked, 3) };
	private static ItemStack[] tBoneSteakReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest tBoneSteak = (new Quest("T-Bone Steak", "Cook three steaks", 2, 2, new ItemStack(Item.beefCooked), new QuestPageHunting(), killCows, tBoneSteakTools,
			tBoneSteakItems, tBoneSteakReward, Tracker.ITEMTURNIN, 0, 0, 0, 0));

	/* Milk Quest */
	private static ItemStack[] milkTools = null;
	private static ItemStack[] milkItems = new ItemStack[] { new ItemStack(Item.bucketMilk, 1) };
	private static ItemStack[] milkReward = new ItemStack[] { new ItemStack(Item.emerald, 4) };
	public static final Quest milk = new Quest("Milk", "Collect a bucket of milk", 2, 4, new ItemStack(Item.bucketMilk), new QuestPageHunting(), tBoneSteak, milkTools, milkItems,
			milkReward, Tracker.ITEMTURNIN, 0, 0, 0, 0);
	
	/* Kill Pig Quest */
	private static ItemStack[] killPigTools = null;
	private static ItemStack[] killPigsItems = new ItemStack[] {new ItemStack(Item.porkRaw, 3)};
	private static ItemStack[] killPigsReward = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest killPig = new Quest("Kill Pigs", "Kill 3 pigs and collect the meat", 4, 0, new ItemStack(Item.porkRaw), new QuestPageHunting(), null, killPigTools, killPigsItems, killPigsReward, Tracker.ITEMTURNIN, 0, 0, 0, 0);

	/* Juicy meat Quest */
	private static ItemStack[] juicyMeatTools = new ItemStack[] {new ItemStack(Item.porkRaw, 3)};
	private static ItemStack[] juicyMeatItems = new ItemStack[] {new ItemStack(Item.porkCooked, 3)};
	private static ItemStack[] juicyMeatReward = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest juicyMeat = new Quest("Juicy Meat", "Cook 3 raw porkchops", 4, 2, new ItemStack(Item.porkCooked), new QuestPageHunting(), killPig, juicyMeatTools, juicyMeatItems, juicyMeatReward, Tracker.ITEMTURNIN, 0, 0, 0, 0);
	
	/* Kill chicken quest */
	private static ItemStack[] killChickenTools = null;
	private static ItemStack[] killChickenItems = new ItemStack[] {new ItemStack(Item.chickenRaw, 3)};
	private static ItemStack[] killChickenRewards = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest killChickens = new Quest("Kill Chickens", "Kill 3 chickens and collect the meat", 6, 0, new ItemStack(Item.chickenRaw), new QuestPageHunting(), null, killChickenTools, killChickenItems, killChickenRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0);
	
	/* KFC Quest */
	private static ItemStack[] kfcTools = new ItemStack[] {new ItemStack(Item.chickenRaw, 3)};
	private static ItemStack[] kfcItems = new ItemStack[] {new ItemStack(Item.chickenCooked, 3)};
	private static ItemStack[] kfcRewards = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest kfc = new Quest("KFC", "Cook 3 raw chickens", 6, 2, new ItemStack(Item.chickenCooked), new QuestPageHunting(), killChickens, kfcTools, kfcItems, kfcRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0);
	
	/* Egg Quest */
	private static ItemStack[] eggTools = null;
	private static ItemStack[] eggItems = new ItemStack[] {new ItemStack(Item.egg, 16)};
	private static ItemStack[] eggRewards = new ItemStack[] {new ItemStack(Item.emerald, 4)};
	public static final Quest eggs = new Quest("Collect Eggs", "Collect 16 eggs", 8, 2, new ItemStack(Item.egg), new QuestPageHunting(), killChickens, eggTools, eggItems, eggRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0);
	
	/* Celebrate Quest */
	private static ItemStack[] celebrateTools = new ItemStack[] {new ItemStack(Item.egg, 1)};
	private static ItemStack[] celebrateItems = new ItemStack[] {new ItemStack(Item.cake)};
	private static ItemStack[] celebrateRewards = new ItemStack[] {new ItemStack(Item.emerald, 4), new ItemStack(Item.cake, 1)};
	public static final Quest celebrate = new Quest("Celebrate", "Bake a cake", 8, 4, new ItemStack(Item.cake), new QuestPageHunting(), eggs, celebrateTools, celebrateItems, celebrateRewards, Tracker.ITEMTURNIN, 0, 0, 0, 0);
	
	@Override
	public String getTitle()
	{
		return "Butchering";
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
		quests.add(colorful);
		quests.add(niceFloors);
		quests.add(killCows);
		quests.add(tBoneSteak);
		quests.add(milk);
		quests.add(killPig);
		quests.add(juicyMeat);
		quests.add(killChickens);
		quests.add(kfc);
		quests.add(eggs);
		quests.add(celebrate);
		return quests;
	}
}