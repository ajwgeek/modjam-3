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

public class QuestSaveData extends WorldSavedData
{
	public static final String IDENTIFIER = "QUESTDATA";
	private Map<String, Boolean> questMap = new HashMap<String, Boolean>();

	public QuestSaveData()
	{
		super(IDENTIFIER);
	}
	
	public QuestSaveData(String s)
	{
		super(IDENTIFIER);
	}

	public void set(Quest quest, EntityPlayer entity, boolean b)
	{
		questMap.put(entity.username + "_" + quest.getName(), b);
		this.markDirty();
	}
	
	public boolean get(Quest quest, EntityPlayer entity)
	{
		if (questMap.containsKey(entity.username + "_" + quest.getName()))
		{
			return questMap.get(entity.username + "_" + quest.getName());
		}
		else
		{
			return false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList tagList = nbttagcompound.getTagList("questMap");
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			String username = tag.getString("USER");
			String questName = tag.getString("QUEST");
			boolean questStatus = tag.getBoolean("STATUS");
			questMap.put(username + "_" + questName, questStatus);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList tagList = new NBTTagList();
		Map<String, Boolean> m1 = new HashMap<String, Boolean>();
		m1.putAll(questMap);
		Iterator it = m1.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			String username = key.split("_")[0];
			String questName = key.split("_")[1];
			boolean questStatus = (Boolean) pairs.getValue();
			NBTTagCompound tag = new NBTTagCompound();
            tag.setString("USER", username);
            tag.setString("QUEST", questName);
            tag.setBoolean("STATUS", questStatus);
            tagList.appendTag(tag);
			it.remove();
		}
		nbttagcompound.setTag("questMap", tagList); 
		this.markDirty();
	}

	public static QuestSaveData forWorld(World world)
	{
		QuestSaveData d = (QuestSaveData) world.mapStorage.loadData(QuestSaveData.class, IDENTIFIER);
		if (d == null)
		{
			d = new QuestSaveData();
			world.mapStorage.setData(IDENTIFIER, d);
		}
		return d;
	}
}