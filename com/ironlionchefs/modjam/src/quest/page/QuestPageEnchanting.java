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

public class QuestPageEnchanting extends QuestPage implements IQuestPage
{
	@Override
	public String getTitle()
	{
		return "Enchanting";
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
		return quests;
	}
}