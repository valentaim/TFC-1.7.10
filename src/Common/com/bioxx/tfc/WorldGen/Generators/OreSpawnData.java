package com.bioxx.tfc.WorldGen.Generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class OreSpawnData
{
    public EnumOreGen type;
    public int size, meta, rarity, min = 5, max = 128, rnd, SphereXSize, SphereYSize, SphereZSize, VeinWidth, VeinBaseHeight, VeinDownFactor, AreaNumber, AreaMaxDistance, CellSize ;
	public Block block;
	public Map<Block, List<Integer>> base;

	public OreSpawnData(String t, String s, String blockName, int m, int r, String[] baseRocks)
	{
		block = Block.getBlockFromName(blockName);

		if (block == null)
		{
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + TFC_Core.translate("error.OreCFG") + " " + blockName);
			throw new java.lang.NullPointerException(TFC_Core.translate("error.OreCFG") + " " + blockName);
		}

		meta = m;
		rarity = r;
		try {
			type = EnumOreGen.getOreType(t);
		} catch (OreNameExceptionrion e) {e.printMessage();type = EnumOreGen.values()[0];}

		if ("small".equals(s))
			size = 0;
		else if ("medium".equals(s))
			size = 1;
		else
			size = 2;

		base = new HashMap<Block, List<Integer>>();
		for (String name : baseRocks)
		{
			getOre(name);
		}
	}

    public OreSpawnData(String t, String s, String blockName, int m, int r, String[] baseRocks, int minHeight, int maxHeight, int rnd, int sxs,
            int sys, int szs, int vw, int vbh, int vdf, int an, int amd, int cs)
	{
		this(t, s, blockName, m, r, baseRocks);
		min = minHeight;
		max = maxHeight;
		this.rnd = rnd;
        SphereXSize = sxs;
        SphereYSize = sys;
        SphereZSize = szs;
        VeinWidth = vw;
        VeinBaseHeight = vbh;
        VeinDownFactor = vdf;
        AreaNumber = an;
        AreaMaxDistance = amd;
        CellSize = cs;
	}

	private void getOre(String name)
	{		
		for (int i = 0; i < Global.STONE_IGIN.length; i++){
			if (name.equalsIgnoreCase(Global.STONE_IGIN[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgIn) ? base.get(TFCBlocks.stoneIgIn) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneIgIn, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_IGEX.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_IGEX[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgEx) ? base.get(TFCBlocks.stoneIgEx) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneIgEx, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_SED.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_SED[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneSed) ? base.get(TFCBlocks.stoneSed) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneSed, metadata);
				return;
			}
		}

		for (int i = 0; i < Global.STONE_MM.length; i++)
		{
			if (name.equalsIgnoreCase(Global.STONE_MM[i]))
			{
				List<Integer> metadata = base.containsKey(TFCBlocks.stoneMM) ? base.get(TFCBlocks.stoneMM) : new ArrayList<Integer>();
				metadata.add(i);
				base.put(TFCBlocks.stoneMM, metadata);
				return;
			}				
		}
		
		if ("igneous intrusive".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgIn) ? base.get(TFCBlocks.stoneIgIn) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneIgIn, metadata);
			return;
		}
		else if ("igneous extrusive".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneIgEx) ? base.get(TFCBlocks.stoneIgEx) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneIgEx, metadata);
			return;
		}
		else if ("sedimentary".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneSed) ? base.get(TFCBlocks.stoneSed) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneSed, metadata);
			return;
		}
		else if ("metamorphic".equalsIgnoreCase(name))
		{
			List<Integer> metadata = base.containsKey(TFCBlocks.stoneMM) ? base.get(TFCBlocks.stoneMM) : new ArrayList<Integer>();
			metadata.add(-1);
			base.put(TFCBlocks.stoneMM, metadata);
			return;
		}
	}

		public enum EnumOreGen {

		Vein, Area, Lens;

		public static EnumOreGen getOreType(String name) throws OreNameExceptionrion {
			for (EnumOreGen ore : EnumOreGen.values())
				if (ore.name().equalsIgnoreCase(name)) return ore;
			throw new OreNameExceptionrion(name);
		}
	}

	public static class OreNameExceptionrion extends Exception {

		private static final long serialVersionUID = 5681805256111119369L;

		private final String orename;

		public OreNameExceptionrion(String message) {
			this.orename = message;
		}

		public void printMessage() {
			TerraFirmaCraft.LOG.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
			TerraFirmaCraft.LOG.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!! WRONG ORE NAME: ".concat(this.orename).concat("!!!!!!!!!!!!!!!!!!!!!!!!!!!!"));
			TerraFirmaCraft.LOG.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}
}