package com.ironlionchefs.modjam.src.quest.saving;

public class QuestSaveKey
{
	public final String username;
	public final String questName;

	public QuestSaveKey(String username, String questName)
	{
		this.username = username;
		this.questName = questName;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		else if (!(o instanceof QuestSaveKey))
		{
			return false;
		}
		else
		{
			QuestSaveKey key = (QuestSaveKey) o;
			return username == key.username && questName == key.questName;
		}
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		for (int i = 0; i < username.length(); i++)
		{
			hash = hash * 31 + username.charAt(i);
		}	
		int hash2 = 7;
		for (int j = 0; j < questName.length(); j++)
		{
			hash2 = hash2 * 31 + questName.charAt(j);
		}
		return hash * hash2 & 4;
	}
}