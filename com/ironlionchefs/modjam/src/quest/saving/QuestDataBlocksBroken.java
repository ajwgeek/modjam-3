package com.ironlionchefs.modjam.src.quest.saving;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ironlionchefs.modjam.src.quest.Quest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class QuestDataBlocksBroken extends WorldSavedData
{
	public static final String IDENTIFIER = "QUEST_BLOCKBREAK_DATA";
	private Map<String, Integer> blockMap = new HashMap<String, Integer>();

	public QuestDataBlocksBroken()
	{
		super(IDENTIFIER);
	}

	public QuestDataBlocksBroken(String s)
	{
		super(IDENTIFIER);
	}

	public void set(Quest quest, EntityPlayer entity, int ItemID, int broken)
	{
		blockMap.put(entity.username + "_" + quest.getName() + "_" + ItemID, broken);
		this.markDirty();
	}

	public int get(Quest quest, EntityPlayer entity, int ItemID)
	{
		if (blockMap.containsKey(entity.username + "_" + quest.getName() + "_" + ItemID))
		{
			return blockMap.get(entity.username + "_" + quest.getName() + "_" + ItemID);
		}
		else
		{
			return 0;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList tagList = new NBTTagList();
		Map<String, Integer> m1 = new HashMap<String, Integer>();
		m1.putAll(blockMap);
		Iterator it = m1.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			
			String username = key.split("_")[0];
			String questName = key.split("_")[1];
			int ItemID = Integer.valueOf(key.split("_")[2]);
			int placedCount = (Integer) pairs.getValue();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("USER", username);
			tag.setString("QUEST", questName);
			tag.setInteger("ITEMID", ItemID);
			tag.setInteger("PLACED", placedCount);
			tagList.appendTag(tag);
			it.remove();
		}
		nbttagcompound.setTag("blockPlacedMap", tagList);
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList tagList = nbttagcompound.getTagList("blockPlacedMap");
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			
			String username = tag.getString("USER");
			String questName = tag.getString("QUEST");
			int ItemID = tag.getInteger("ITEMID");
			int placedCount = tag.getInteger("PLACED");
			blockMap.put(username + "_" + questName + "_" + ItemID, placedCount);
		}
	}



	public static QuestDataBlocksBroken forWorld(World world)
	{
		QuestDataBlocksBroken d = (QuestDataBlocksBroken) world.mapStorage.loadData(QuestDataBlocksBroken.class, IDENTIFIER);
		if (d == null)
		{
			d = new QuestDataBlocksBroken();
			world.mapStorage.setData(IDENTIFIER, d);
		}
		return d;
	}
}