package com.bioxx.tfc.WorldGen.Generators;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.Util.CaseInsensitiveHashMap;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;

public class WorldGenOre implements IWorldGenerator
{
	//private int Min;
	//private int Max;
	private int chunkX;
	private int chunkZ;
	private World worldObj;
	private Random random;

	public static Map<String, OreSpawnData> oreList = new CaseInsensitiveHashMap<OreSpawnData>();

	public WorldGenOre()
	{
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		worldObj = world;
		random = rand;

		Iterator iter = oreList.values().iterator();
		while(iter.hasNext())
		{
			OreSpawnData osd = (OreSpawnData) iter.next();
			if(osd.type == 0)
			{
				if(osd.size == 0)
					oreSmall(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMedium(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLarge(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 3)
					oreBig(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
			else if(osd.type == 1)
			{
				if(osd.size == 0)
					oreSmallVeins(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMediumVeins(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLargeVeins(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 3)
					oreBigVeins(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
			else if(osd.type == 2)
			{
				if(osd.size == 0)
					oreSmallVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMediumVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLargeVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 3)
					oreBigVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
			else if (osd.type == 3)
			{
				if(osd.size == 0)
					oreSmallArea(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMediumArea(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLargeArea(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 3)
					oreBigArea(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
			else if (osd.type == 4)
			{
				if(osd.size == 0)
					oreSmallLens(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMediumLens(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLargeLens(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 3)
					oreBigLens(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
		}
	}

	private void oreSmallVeins(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVeins(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,
 worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreMediumVeins(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVeins(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,
 worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreLargeVeins(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVeins(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
 worldObj, random, chunkX, chunkZ, min, max);
	}
	private void oreBigVeins(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVeins(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreSmallVein(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreMediumVein(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreLargeVein(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}
	private void oreBigVein(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/50,/*height*/40,/*diameter*/100,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreSmallArea(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreMediumArea(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreLargeArea(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}
	private void oreBigArea(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreSmallLens(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreMediumLens(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreLargeLens(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}
	private void oreBigLens(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreSmall(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta, baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
 worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreMedium(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,
 worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreLarge(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/40,/*height*/5,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,
 worldObj, random, chunkX, chunkZ, min, max);
	}

	private void oreBig(Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/120,/*veinAmt*/50,/*height*/5,/*diameter*/240,/*vDensity*/vDensity,/*hDensity*/hDensity,
worldObj, random, chunkX, chunkZ, min, max);
	}

	private static void createOre(Block block, int j, Map<Block, List<Integer>> layers, int rarity, int veinSize,
			int veinAmount, int height, int diameter, int vDensity, int hDensity, World world, Random rand, int chunkX, int chunkZ, int min, int max)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block b : layers.keySet())
			{
				for(int metadata : layers.get(b))
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
					DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
					DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);
					if (rockLayer1.block == b && (rockLayer1.data2 == metadata || metadata == -1) ||
						rockLayer2.block == b && (rockLayer2.data2 == metadata || metadata == -1) ||
						rockLayer3.block == b && (rockLayer3.data2 == metadata || metadata == -1))
					{
						int grade = rand.nextInt(100);
						if(grade<20)
							grade = 1;
						else if(grade <50)
							grade = 2;
						else
							grade = 0;

						new WorldGenMinable(block, j, b, metadata, rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, false, grade)
								.generate(world, rand, chunkX, chunkZ, min, max);
					}
				}
			}
		}
	}

	private static void createOreVeins(Block block, int j, Map<Block, List<Integer>> layers, int rarity, int veinSize,
			int veinAmount, int height, int diameter, int vDensity, int hDensity, World world, Random rand, int chunkX, int chunkZ, int min, int max)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block b : layers.keySet())
			{
				for (int metadata : layers.get(b))
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
					DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
					DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);
					if (rockLayer1.block == b && (rockLayer1.data2 == metadata || metadata == -1) ||
						rockLayer2.block == b && (rockLayer2.data2 == metadata || metadata == -1) ||
						rockLayer3.block == b && (rockLayer3.data2 == metadata || metadata == -1))
					{
						int grade = rand.nextInt(100);
						if (grade < 20)
							grade = 1;
						else if (grade < 50)
							grade = 2;
						else
							grade = 0;

						new WorldGenMinable(block, j, b, metadata, rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, true, grade)
								.generate(world, rand, chunkX, chunkZ, min, max);
					}
				}
			}
		}
	}

	private static void createOreVein(Block block, int j, Map<Block, List<Integer>> layers, int rarity, int veinSize,
									   int veinAmount, int height, int diameter, int vDensity, int hDensity, World world, Random rand, int chunkX, int chunkZ, int min, int max)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block b : layers.keySet())
			{
				for (int metadata : layers.get(b))
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
					DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
					DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);
					if (rockLayer1.block == b && (rockLayer1.data2 == metadata || metadata == -1) ||
						rockLayer2.block == b && (rockLayer2.data2 == metadata || metadata == -1) ||
						rockLayer3.block == b && (rockLayer3.data2 == metadata || metadata == -1))
					{
						int grade = rand.nextInt(100);
						if (grade < 20)
							grade = 1;
						else if (grade < 50)
							grade = 2;
						else
							grade = 0;

						new WorldGenMinable(block, j, b, metadata, rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, true, grade)
								.generate(world, rand, chunkX, chunkZ, min, max);
					}
				}
			}
		}
	}
}
