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

public class QuestDataPerPlayer extends WorldSavedData
{
	public static final String IDENTIFIER = "QUESTPLAYERDATA";
	private Map<String, String> dataMap = new HashMap<String, String>();

	public QuestDataPerPlayer(String s)
	{
		super(IDENTIFIER);
	}

	public QuestDataPerPlayer()
	{
		super(IDENTIFIER);
	}

	public void set(EntityPlayer entity, Quest quest)
	{
		if (quest == null)
		{
			dataMap.put(entity.username, "null");
		}
		else
		{
			dataMap.put(entity.username, quest.getName());
		}
		this.markDirty();
	}

	public String get(EntityPlayer entity)
	{
		if (dataMap.containsKey(entity.username))
		{
			if (dataMap.get(entity.username).equals("null"))
			{
				return null;
			}
			else
			{
				return dataMap.get(entity.username);
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		dataMap = new HashMap<String, String>();
		NBTTagList tagList = nbttagcompound.getTagList("dataMap");
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			String username = tag.getString("USER");
			String questName = tag.getString("QUEST");
			dataMap.put(username, questName);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList tagList = new NBTTagList();
		Map<String, String> m1 = new HashMap<String, String>();
		m1.putAll(dataMap);
		Iterator it = m1.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pairs = (Map.Entry) it.next();
			String username = (String) pairs.getKey();
			String questName = (String) pairs.getValue();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("USER", username);
			tag.setString("QUEST", questName);
			tagList.appendTag(tag);
			it.remove();
		}
		nbttagcompound.setTag("dataMap", tagList);
	}

	public static QuestDataPerPlayer forWorld(World world)
	{
		QuestDataPerPlayer d = (QuestDataPerPlayer) world.mapStorage.loadData(QuestDataPerPlayer.class, IDENTIFIER);
		if (d == null)
		{
			d = new QuestDataPerPlayer();
			world.mapStorage.setData(IDENTIFIER, d);
		}
		return d;
	}
}