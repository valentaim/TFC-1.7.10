package com.bioxx.tfc.WorldGen.Generators;//CHECK

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumOreGen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;

public class OreSpawnData {
    public EnumOreGen type;
    public int size;
    public int meta;
    public int rarity;
    public int min;
    public int max;
    public int rnd;
    public int SphereXSize;
    public int SphereYSize;
    public int SphereZSize;
    public int VeinWidth;
    public int VeinBaseHeight;
    public int VeinDownFactor;
    public int AreaNumber;
    public int AreaMaxDistance;
    public int CellSize;
    public Block block;
    public Map<Block, List<Integer>> base;

    public OreSpawnData(String t, String s, String blockName, int m, int r, String[] baseRocks) {
        this.min = 5;
        this.max = 128;
        this.block = Block.getBlockFromName(blockName);
        if (this.block == null) {
            TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + TFC_Core.translate("error.OreCFG") + " " + blockName);
            throw new NullPointerException(TFC_Core.translate("error.OreCFG") + " " + blockName);
        } else {
            this.meta = m;
            this.rarity = r;
            if (EnumOreGen.Area.name().equalsIgnoreCase(t)) {
                this.type = EnumOreGen.Area;
            } else if (EnumOreGen.Vein.name().equalsIgnoreCase(t)) {
                this.type = EnumOreGen.Vein;
            } else if (EnumOreGen.Lens.name().equalsIgnoreCase(t)) {
                this.type = EnumOreGen.Lens;
            } else {
                this.type = EnumOreGen.Vein;
            }

            if ("small".equals(s)) {
                this.size = 0;
            } else if ("medium".equals(s)) {
                this.size = 1;
            } else {
                this.size = 2;
            }

            this.base = new HashMap();

            for(String name : baseRocks) {
                this.getOre(name);
            }

        }
    }

    public OreSpawnData(String t, String s, String blockName, int m, int r, String[] baseRocks, int minHeight, int maxHeight, int rnd, int sxs, int sys, int szs, int vw, int vbh, int vdf, int an, int amd, int cs) {
        this(t, s, blockName, m, r, baseRocks);
        this.min = minHeight;
        this.max = maxHeight;
        this.rnd = rnd;
        this.SphereXSize = sxs;
        this.SphereYSize = sys;
        this.SphereZSize = szs;
        this.VeinWidth = vw;
        this.VeinBaseHeight = vbh;
        this.VeinDownFactor = vdf;
        this.AreaNumber = an;
        this.AreaMaxDistance = amd;
        this.CellSize = cs;
    }

    private void getOre(String name) {
        for(int i = 0; i < Global.STONE_IGIN.length; ++i) {
            if (name.equalsIgnoreCase(Global.STONE_IGIN[i])) {
                List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneIgIn) ? (List)this.base.get(TFCBlocks.stoneIgIn) : new ArrayList());
                metadata.add(i);
                this.base.put(TFCBlocks.stoneIgIn, metadata);
                return;
            }
        }

        for(int i = 0; i < Global.STONE_IGEX.length; ++i) {
            if (name.equalsIgnoreCase(Global.STONE_IGEX[i])) {
                List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneIgEx) ? (List)this.base.get(TFCBlocks.stoneIgEx) : new ArrayList());
                metadata.add(i);
                this.base.put(TFCBlocks.stoneIgEx, metadata);
                return;
            }
        }

        for(int i = 0; i < Global.STONE_SED.length; ++i) {
            if (name.equalsIgnoreCase(Global.STONE_SED[i])) {
                List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneSed) ? (List)this.base.get(TFCBlocks.stoneSed) : new ArrayList());
                metadata.add(i);
                this.base.put(TFCBlocks.stoneSed, metadata);
                return;
            }
        }

        for(int i = 0; i < Global.STONE_MM.length; ++i) {
            if (name.equalsIgnoreCase(Global.STONE_MM[i])) {
                List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneMM) ? (List)this.base.get(TFCBlocks.stoneMM) : new ArrayList());
                metadata.add(i);
                this.base.put(TFCBlocks.stoneMM, metadata);
                return;
            }
        }

        if ("igneous intrusive".equalsIgnoreCase(name)) {
            List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneIgIn) ? (List)this.base.get(TFCBlocks.stoneIgIn) : new ArrayList());
            metadata.add(-1);
            this.base.put(TFCBlocks.stoneIgIn, metadata);
        } else if ("igneous extrusive".equalsIgnoreCase(name)) {
            List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneIgEx) ? (List)this.base.get(TFCBlocks.stoneIgEx) : new ArrayList());
            metadata.add(-1);
            this.base.put(TFCBlocks.stoneIgEx, metadata);
        } else if ("sedimentary".equalsIgnoreCase(name)) {
            List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneSed) ? (List)this.base.get(TFCBlocks.stoneSed) : new ArrayList());
            metadata.add(-1);
            this.base.put(TFCBlocks.stoneSed, metadata);
        } else if ("metamorphic".equalsIgnoreCase(name)) {
            List<Integer> metadata = (List<Integer>)(this.base.containsKey(TFCBlocks.stoneMM) ? (List)this.base.get(TFCBlocks.stoneMM) : new ArrayList());
            metadata.add(-1);
            this.base.put(TFCBlocks.stoneMM, metadata);
        }
    }
}
