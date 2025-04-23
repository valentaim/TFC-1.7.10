package com.bioxx.tfc.WorldGen.Generators.Trees.New;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenWillow
  extends WorldGenAbstractTree {
  private Block woodBlock;
  private int woodMeta;
  private Block leafBlock;
  private int leafMeta;
  private int minHeight;
  private int maxHeight;
  private boolean needsWater;
  
  public LOTRWorldGenWillow(boolean flag, int id) {
    super(flag);
    this.woodBlock = TFCBlocks.logNatural;
    this.woodMeta = id;
    this.leafBlock = TFCBlocks.leaves;
    this.leafMeta = id;
    this.minHeight = 8;
    this.maxHeight = 13;
    this.needsWater = false;
  }
  
  public LOTRWorldGenWillow setNeedsWater() {
    this.needsWater = true;
    return this;
  }
  
  public boolean generate(World world, Random random, int i, int j, int k) {
    int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
    boolean flag = true;
    
    if (j >= 1 && height + 1 <= 256) {
      for (int below = j; below <= j + height + 1; below++) {
        byte isSoil = 1;
        if (below == j) {
          isSoil = 0;
        }
        
        if (below >= j + height - 1) {
          isSoil = 2;
        }
        
        for (int vineGrows = i - isSoil; vineGrows <= i + isSoil && flag; vineGrows++) {
          for (int m = k - isSoil; m <= k + isSoil && flag; m++) {
            if (below >= 0 && below < 256) {
              if (!isReplaceable(world, vineGrows, below, m)) {
                flag = false;
              }
            } else {
              flag = false;
            } 
          } 
        } 
      } 
    } else {
      return false;
    } 

    Block var21 = world.getBlock(i, j - 1, k);

    //    if (!TFC_Core.isSoil(world.getBlock(i, j - 1, k)) || j >= world.func_72800_K() - height) {
    if (!TFC_Core.isSoil(world.getBlock(i, j - 1, k)) || j >= world.getHeight() - height) {
      return false;
    }
    
    if (this.needsWater) {
      boolean var23 = false;
      byte var24 = 4;
      
      for (int m = 0; m < var24; m++) {
        int coords = i + MathHelper.getRandomIntegerInRange(random, -12, 12);
        int i2 = j + MathHelper.getRandomIntegerInRange(random, -8, 4);
        int k2 = k + MathHelper.getRandomIntegerInRange(random, -12, 12);
        //if (world.func_147439_a(coords, i2, k2).func_149688_o() == Material.field_151586_h) {
        if (world.getBlock(coords, i2, k2).getMaterial() == Material.water) {
          var23 = true;
          break;
        } 
      } 
      if (!var23) {
        return false;
      }
    }
    
    var21.onPlantGrow(world, i, j - 1, k, i, j, k);
    ArrayList<ChunkCoordinates> var25 = new ArrayList<ChunkCoordinates>();
    int angle = 0;
    
    while (angle < 360) {
      angle += 30 + random.nextInt(30);
      float var26 = (float)Math.toRadians(angle);
      float var27 = MathHelper.cos(var26);
      float var28 = MathHelper.sin(var26);
      int k2 = j + height - 3 - random.nextInt(3);
      int rootX = 2 + random.nextInt(4);
      int rootY = i;
      int rootZ = k2;
      int roots = k;
      
      for (int l = 0; l < rootX; l++) {
        if (l > 0 && (l % 4 == 0 || random.nextInt(3) == 0)) {
          rootZ++;
        }
        
        if (random.nextFloat() < Math.abs(var28)) {
          rootY = (int)(rootY + Math.signum(var28));
        }
        
        if (random.nextFloat() < Math.abs(var27)) {
          roots = (int)(roots + Math.signum(var27));
        }
        setBlockAndNotifyAdequately(world, rootY, rootZ, roots, this.woodBlock, this.woodMeta);
      } 
      
      spawnLeafCluster(world, random, rootY, rootZ, roots);
      var25.add(new ChunkCoordinates(rootY, rootZ, roots));
    } 
    int i1;
    for (i1 = 0; i1 < height; i1++) {
      setBlockAndNotifyAdequately(world, i, j + i1, k, this.woodBlock, this.woodMeta);
      if (i1 == height - 1) {
        spawnLeafCluster(world, random, i, j + i1, k);
        var25.add(new ChunkCoordinates(i, j + i1, k));
      } 
    } 
    
    for (i1 = i - 1; i1 <= i + 1; i1++) {
      for (int coords = k - 1; coords <= k + 1; coords++) {
        int i2 = i1 - i;
        int k2 = coords - k;
        if (Math.abs(i2) != Math.abs(k2)) {
          int rootX = i1;
          int rootY = j + 1 + random.nextInt(2);
          int rootZ = coords;
          int roots = 0;

          //while (world.func_147439_a(rootX, rootY, coords).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
          while (world.getBlock(rootX, rootY, coords).isReplaceable((IBlockAccess)world, rootX, rootY, rootZ)) {
            setBlockAndNotifyAdequately(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta);
            //world.func_147439_a(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
            world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
            rootY--;
            roots++;
            if (roots > 4 + random.nextInt(3)) {
              break;
            }
          } 
        } 
      } 
    } 
    
    Iterator<ChunkCoordinates> var29 = var25.iterator();

    while (var29.hasNext()) {
      ChunkCoordinates var30 = var29.next();
      //spawnVineCluster(world, random, var30.field_71574_a, var30.field_71572_b, var30.field_71573_c);
      spawnVineCluster(world, random, var30.posX, var30.posY, var30.posZ);
    }
    return true;
  }
  
  private void spawnLeafCluster(World world, Random random, int i, int j, int k) {
    byte leafRange = 3;
    int leafRangeSq = leafRange * leafRange;
    int leafRangeSqLess = (int)((leafRange - 0.5D) * (leafRange - 0.5D));
    
    for (int i1 = i - leafRange; i1 <= i + leafRange; i1++) {
      for (int j1 = j - leafRange; j1 <= j + leafRange; j1++) {
        for (int k1 = k - leafRange; k1 <= k + leafRange; k1++) {
          int i2 = i1 - i;
          int j2 = j1 - j;
          int k2 = k1 - k;
          int dist = i2 * i2 + j2 * j2 + k2 * k2;
          int taxicab = Math.abs(i2) + Math.abs(j2) + Math.abs(k2);
          if ((dist < leafRangeSqLess || (dist < leafRangeSq && random.nextInt(3) == 0)) && taxicab <= 4) {
            //Block block = world.func_147439_a(i1, j1, k1);
            Block block = world.getBlock(i1, j1, k1);
            if (block.isReplaceable((IBlockAccess)world, i1, j1, k1) || block.isLeaves((IBlockAccess)world, i1, j1, k1)) {
              //func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
              setBlockAndNotifyAdequately(world, i1, j1, k1, this.leafBlock, this.leafMeta);
            }
          } 
        } 
      } 
    } 
  }

  
  private void spawnVineCluster(World world, Random random, int i, int j, int k) {
    byte leafRange = 3;
    int leafRangeSq = leafRange * leafRange;
    
    for (int i1 = i - leafRange; i1 <= i + leafRange; i1++) {
      for (int j1 = j - leafRange; j1 <= j + leafRange; j1++) {
        for (int k1 = k - leafRange; k1 <= k + leafRange; k1++) {
          int i2 = i1 - i;
          int j2 = j1 - j;
          int k2 = k1 - k;
          int dist = i2 * i2 + j2 * j2 + k2 * k2;
          if (dist < leafRangeSq) {
            //Block block = world.func_147439_a(i1, j1, k1);
            Block block = world.getBlock(i1, j1, k1);
//            int meta = world.func_72805_g(i1, j1, k1);
            int meta = world.getBlockMetadata(i1, j1, k1);
            if (block == this.leafBlock && meta == this.leafMeta) {
              byte vineChance = 2;
              if (random.nextInt(vineChance) == 0 && world.getBlock(i1 - 1, j1, k1).isAir((IBlockAccess)world, i1 - 1, j1, k1)) {
                growVines(world, random, i1 - 1, j1, k1, 8);
              }
              
              if (random.nextInt(vineChance) == 0 && world.getBlock(i1 + 1, j1, k1).isAir((IBlockAccess)world, i1 + 1, j1, k1)) {
                growVines(world, random, i1 + 1, j1, k1, 2);
              }
              
              if (random.nextInt(vineChance) == 0 && world.getBlock(i1, j1, k1 - 1).isAir((IBlockAccess)world, i1, j1, k1 - 1)) {
                growVines(world, random, i1, j1, k1 - 1, 1);
              }
              
              if (random.nextInt(vineChance) == 0 && world.getBlock(i1, j1, k1 + 1).isAir((IBlockAccess)world, i1, j1, k1 + 1)) {
                growVines(world, random, i1, j1, k1 + 1, 4);
              }
            } 
          } 
        } 
      } 
    } 
  }

  
  private void growVines(World world, Random random, int i, int j, int k, int meta) {
    setBlockAndNotifyAdequately(world, i, j, k, TFCBlocks.vine, meta);
    int vines = 0;
    
    while (true) {
      j--;
      if (!world.getBlock(i, j, k).isAir(world, i, j, k) || vines >= 2 + random.nextInt(4)) {
        return;
      }
      setBlockAndNotifyAdequately(world, i, j, k, TFCBlocks.vine, meta);
      vines++;
    } 
  }
}