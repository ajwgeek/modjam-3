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
	private Map<QuestSaveKey, Boolean> questMap = new HashMap<QuestSaveKey, Boolean>();

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
		questMap.put(new QuestSaveKey(entity.username, quest.getName()), b);
		this.markDirty();
	}
	
	public boolean get(Quest quest, EntityPlayer entity)
	{
		QuestSaveKey key = new QuestSaveKey(entity.username, quest.getName());
		if (questMap.containsKey(key))
			return questMap.get(key);
		else
			return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		questMap.clear();
		NBTTagList tagList = nbttagcompound.getTagList("questMap");
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			String username = tag.getString("USER");
			String questName = tag.getString("QUEST");
			boolean questStatus = tag.getBoolean("STATUS");
			questMap.put(new QuestSaveKey(username, questName), questStatus);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList tagList = new NBTTagList();
		Map<QuestSaveKey, Boolean> m1 = new HashMap<QuestSaveKey, Boolean>();
		m1.putAll(questMap);
		Iterator it = m1.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry) it.next();
			QuestSaveKey key = (QuestSaveKey) pairs.getKey();
			String username = key.username;
			String questName = key.questName;
			boolean questStatus = (Boolean) pairs.getValue();
			NBTTagCompound tag = new NBTTagCompound();
            tag.setString("USER", username);
            tag.setString("QUEST", questName);
            tag.setBoolean("STATUS", questStatus);
            tagList.appendTag(tag);
			it.remove();
		}
		nbttagcompound.setTag("questMap", tagList);
	}

	public static QuestSaveData forWorld(World world)
	{
		QuestSaveData d = (QuestSaveData) world.mapStorage.loadData(QuestSaveData.class, IDENTIFIER);
		if (d == null)
		{
			System.out.println("HUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUL");
			d = new QuestSaveData();
			world.mapStorage.setData(IDENTIFIER, d);
		}
		return d;
	}
}