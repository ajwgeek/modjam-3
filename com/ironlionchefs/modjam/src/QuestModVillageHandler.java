package com.ironlionchefs.modjam.src;

import java.util.List;
import java.util.Random;

import com.ironlionchefs.modjam.src.village.components.VillageComponentBank;

import net.minecraft.world.gen.structure.ComponentVillageHouse2;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class QuestModVillageHandler implements VillagerRegistry.IVillageCreationHandler
{
	@Override
	public StructureVillagePieceWeight getVillagePieceWeight(Random random, int i)
	{
		return new StructureVillagePieceWeight(VillageComponentBank.class, 1, 1);
	}

	@Override
	public Class<?> getComponentClass()
	{
		return VillageComponentBank.class;
	}

	@Override
	public Object buildComponent(StructureVillagePieceWeight villagePiece, ComponentVillageStartPiece startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	{
		return VillageComponentBank.func_74915_a(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}
}