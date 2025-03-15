package com.bioxx.tfc.WorldGen.Generators;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.Util.CaseInsensitiveHashMap;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData.EnumOreGen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGenOre implements IWorldGenerator
{
	private int chunkX;
	private int chunkZ;
	private World worldObj;
	private Random random;

	public static Map<String, OreSpawnData> oreList = new CaseInsensitiveHashMap<OreSpawnData>();

    public WorldGenOre() {
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

        for (Map.Entry<String, OreSpawnData> entry : oreList.entrySet())
        {
        	String name = entry.getKey();
            OreSpawnData osd = entry.getValue();
            ore(osd.type, osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.rnd, osd.SphereXSize, osd.SphereYSize, osd.SphereZSize, osd.VeinWidth,
            		osd.VeinBaseHeight, osd.VeinDownFactor, osd.AreaNumber, osd.AreaMaxDistance, osd.CellSize, name);
        }
	}

	private void ore(EnumOreGen type, Block block, int meta, Map<Block, List<Integer>> baseRocks, int rarity, int min, int max, int rnd, int SphereXSize,
			int SphereYSize, int SphereZSize, int VeinWidth, int VeinBaseHeight, int VeinDownFactor, int AreaNumber, int AreaMaxDistance, int CellSize, String name)
	{
		createOre(type, block, meta ,baseRocks, rarity, rnd, worldObj, random, chunkX, chunkZ, min, max, SphereXSize, SphereYSize, SphereZSize, VeinWidth,
				VeinBaseHeight, VeinDownFactor, AreaNumber, AreaMaxDistance, CellSize, name );
	}

    private static void createOre(EnumOreGen type, Block block, int j, Map<Block, List<Integer>> layers, int rarity, int rnd, World world, Random rand, int chunkX,
            int chunkZ, int min, int max, int SphereXSize, int SphereYSize, int SphereZSize, int VeinWidth, int VeinBaseHeight, int VeinDownFactor, int AreaNumber,
            int AreaMaxDistance, int CellSize, String name)
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
                        new WorldGenMinableTFCNew(type, block, j, b, metadata, rarity, rnd,
                                SphereXSize,  SphereYSize, SphereZSize, VeinWidth, VeinBaseHeight, VeinDownFactor, AreaNumber, AreaMaxDistance, CellSize, name)
                                .generate(world, rand, chunkX, chunkZ, min, max);

					}
				}
			}
		}
	}
}
