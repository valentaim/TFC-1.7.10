package com.bioxx.tfc.WorldGen.Generators.Trees.New;

import java.util.Random;

import com.bioxx.tfc.TerraFirmaCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenBOPTaiga2
  extends WorldGenAbstractTree {
  private final int minTreeHeight;
  private final int randomTreeHeight;
  private final Block wood;
  private final Block leaves;
  private final int metaWood;
  private final int metaLeaves;
  private final int altNo;

  public WorldGenBOPTaiga2(int id, Block wood, Block leaves, boolean doBlockNotify, int minTreeHeight, int randomTreeHeight, int altNo, int metaFruit) {
    super(doBlockNotify);
    this.wood = wood;
    this.leaves = leaves;
    this.metaWood = id;
    this.metaLeaves = id;
    this.minTreeHeight = minTreeHeight;
    this.randomTreeHeight = randomTreeHeight;
    this.altNo = altNo;
  }

  public boolean generate(World world, Random random, int x, int y, int z) {
    int i1, j1, k1, l = random.nextInt(this.randomTreeHeight) + this.minTreeHeight;

    switch (this.altNo) {
      case 1:
        i1 = 2 + random.nextInt(4);
        j1 = l - i1;
        k1 = 2 + random.nextInt(4);
        break;
      case 2:
        i1 = 4 + random.nextInt(4);
        j1 = l - i1;
        k1 = 2;
        break;
      case 3:
        i1 = 1 - random.nextInt(4);
        j1 = l - i1;
        k1 = 2 + random.nextInt(2);
        break;
      case 4:
        i1 = 8 + random.nextInt(4);
        j1 = l - i1;
        k1 = 2 + random.nextInt(2);
        break;
      case 5:
        i1 = 2;
        j1 = l - i1;
        k1 = 2 + random.nextInt(2);
        break;
      case 6:
        i1 = 2 + random.nextInt(4);
        j1 = l - i1;
        k1 = 2 + random.nextInt(2);
        break;
      case 7:
        i1 = 2;
        j1 = l - i1;
        k1 = 3;
        break;
      case 8:
        i1 = 2 + random.nextInt(3);
        j1 = l - i1;
        k1 = 2 + random.nextInt(2);
        break;
      default:
        i1 = 1 + random.nextInt(2);
        j1 = l - i1;
        k1 = 2 + random.nextInt(2);
    }
    boolean flag = true;
    if (y >= 1 && y + l + 1 <= 256) {

      for (int block1 = y; block1 <= y + 1 + l && flag; block1++) {
        int l3; boolean bool = true;
        if (block1 - y < i1) {
          l3 = 0;
        } else {
          l3 = k1;
        }

        for (int i2 = x - l3; i2 <= x + l3 && flag; i2++) {
          for (int b0 = z - l3; b0 <= z + l3 && flag; b0++) {
            if (block1 >= 0 && block1 < 256) {
              Block k2 = world.getBlock(i2, block1, b0);
              if (!k2.isAir(world, i2, block1, b0) && !k2.isLeaves(world, i2, block1, b0)) {
                flag = false;
              }
            } else {
              flag = false;
            }
          }
        }
      }

      if (!flag) {
        return false;
      }
      Block var24 = world.getBlock(x, y - 1, z);
      boolean isSoil = var24.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable)Blocks.sapling);
      if (isSoil && y < 256 - l - 1) {
        var24.onPlantGrow(world, x, y - 1, z, x, y, z);
        int l3 = random.nextInt(2);
        int i2 = 1;
        byte var25 = 0;
        int i4;

        for (i4 = 0; i4 <= j1; i4++) {
          int i = y + l - i4;

          for (int fr = x - l3; fr <= x + l3; fr++) {
            int fl = fr - x;

            for (int f1 = z - l3; f1 <= z + l3; f1++) {
              int f2 = f1 - z;
              if ((Math.abs(fl) != l3 || Math.abs(f2) != l3 || l3 <= 0) && world.getBlock(fr, i, f1).canBeReplacedByLeaves(world, fr, i, f1)) {
                setBlockAndNotifyAdequately(world, fr, i, f1, this.leaves, this.metaLeaves);
              }
            }
          }

          if (l3 >= i2) {
            l3 = var25;
            var25 = 1;
            i2++;
            if (i2 > k1) {
              i2 = k1;
            }
          } else {
            l3++;
          }
        }

        i4 = random.nextInt(3);

        for (int var26 = 0; var26 < l - i4; var26++) {
          Block var27 = world.getBlock(x, y + var26, z);
          if (var27.isAir(world, x, y + var26, z) || var27.isLeaves(world, x, y + var26, z)) {
            setBlockAndNotifyAdequately(world, x, y + var26, z, this.wood, this.metaWood);
          }
        }
        return true;
      }
      return false;
    }
    return false;
  }
}