package com.bioxx.tfc.WorldGen.Generators.Trees.New;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;


public class WorldGenSacredOak extends WorldGenAbstractTree
{
  static final byte[] otherCoordPairs = new byte[] { 2, 0, 0, 1, 2, 1 };
  Random rand = new Random();
  World worldObj;
  int[] basePos = new int[] { 0, 0, 0 };

  private final int heightLimit;
  int height;
  private final double heightAttenuation = 0.45D;
  private final double branchSlope = 0.45D;
  private final double scaleWidth;
  private final double leafDensity = 1.0D;
  private int trunkSize = 1;

  private final int leafDistanceLimit;

  int[][] leafNodes;
  private final int meta;

  public WorldGenSacredOak(boolean par1, int id, double scale, int leaf, int heightl, int hr) {
    super(par1);
    this.scaleWidth = scale;
    this.leafDistanceLimit = leaf;
    this.heightLimit = vary(new Random(), heightl, hr);
    if (this.heightLimit > 25) this.trunkSize = 2;
    this.meta = id;
  }

  public int vary(Random rand, int min, int dist) {
    return rand.nextInt(dist) + min;
  }


  public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
    if (!TFC_Core.isSoil(par1World.getBlock(par3, par4 - 1, par5))) return false;
    if (!valid(par1World, par3, par4, par5)) par4--;

    this.worldObj = par1World;
    long var6 = par2Random.nextLong();
    this.rand.setSeed(var6);
    this.basePos[0] = par3;
    this.basePos[1] = par4;
    this.basePos[2] = par5;

    generateLeafNodeList();
    generateTrunk();
    generateLeafNodeBases();
    return true;
  }

  void generateLeafNodeList() {
    getClass(); this.height = (int)(this.heightLimit * 0.45D);
    if (this.height >= this.heightLimit) {
      this.height = this.heightLimit - 1;
    }

    getClass(); int var1 = (int)(1.382D + Math.pow(1.0D * this.heightLimit / 13.0D, 2.0D));
    if (var1 < 1) {
      var1 = 1;
    }

    int[][] var2 = new int[var1 * this.heightLimit][4];
    int var3 = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
    int var4 = 1;
    int var5 = this.basePos[1] + this.height;
    int var6 = var3 - this.basePos[1];
    var2[0][0] = this.basePos[0];
    var2[0][1] = var3;
    var2[0][2] = this.basePos[2];
    var2[0][3] = var5;
    var3--;


    while (var6 >= 0) {
      int var7 = 0;
      float var8 = layerSize(var6);
      if (var8 < 0.0F) {
        var3--;
        var6--; continue;
      }
      for (double var9 = 0.5D; var7 < var1; var7++) {
        double var11 = this.scaleWidth * var8 * (this.rand.nextFloat() + 0.328D);
        double var13 = this.rand.nextFloat() * 2.0D * Math.PI;
        int var15 = MathHelper.floor_double(var11 * Math.sin(var13) + this.basePos[0] + var9);
        int var16 = MathHelper.floor_double(var11 * Math.cos(var13) + this.basePos[2] + var9);
        int[] var17 = { var15, var3, var16 };
        int[] var18 = { var15, var3 + this.leafDistanceLimit, var16 };
        if (checkBlockLine(var17, var18) == -1) {
          int[] var19 = { this.basePos[0], this.basePos[1], this.basePos[2] };
          double var20 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var17[0]), 2.0D) + Math.pow(Math.abs(this.basePos[2] - var17[2]), 2.0D));
          getClass(); double var22 = var20 * 0.45D;
          if (var17[1] - var22 > var5) {
            var19[1] = var5;
          } else {
            var19[1] = (int)(var17[1] - var22);
          }

          if (checkBlockLine(var19, var17) == -1) {
            var2[var4][0] = var15;
            var2[var4][1] = var3;
            var2[var4][2] = var16;
            var2[var4][3] = var19[1];
            var4++;
          }
        }
      }

      var3--;
      var6--;
    }


    this.leafNodes = new int[var4][4];
    System.arraycopy(var2, 0, this.leafNodes, 0, var4);
  }



  void genTreeLayer(int par1, int par2, int par3, float par4, byte par5, Block leaves) {
    int var7 = (int)(par4 + 0.618D);
    byte var8 = otherCoordPairs[par5];
    byte var9 = otherCoordPairs[par5 + 3];
    int[] var10 = { par1, par2, par3 };
    int[] var11 = { 0, 0, 0 };
    int var12 = -var7;
    int var13 = -var7;


    for (var11[par5] = var10[par5]; var12 <= var7; var12++) {
      var11[var8] = var10[var8] + var12;
      var13 = -var7;



      while (var13 <= var7) {



        double var15 = Math.pow(Math.abs(var12) + 0.5D, 2.0D) + Math.pow(Math.abs(var13) + 0.5D, 2.0D);
        if (var15 > (par4 * par4)) {
          var13++; continue;
        }
        var11[var9] = var10[var9] + var13;
        Block block = this.worldObj.getBlock(var11[0], var11[1], var11[2]);
        if (block != Blocks.air && block != leaves) {
          var13++; continue;
        }
        setBlockAndNotifyAdequately(this.worldObj, var11[0], var11[1], var11[2], leaves, this.meta);
        var13++;
      }
    }
  }

  float layerSize(int par1) {
    float var4;
    if (par1 < this.heightLimit * 0.3D) {
      return -1.618F;
    }
    float var2 = this.heightLimit / 2.0F;
    float var3 = this.heightLimit / 2.0F - par1;

    if (var3 == 0.0F) {
      var4 = var2;
    } else if (Math.abs(var3) >= var2) {
      var4 = 0.0F;
    } else {
      var4 = (float)Math.sqrt(Math.pow(Math.abs(var2), 2.0D) - Math.pow(Math.abs(var3), 2.0D));
    }

    var4 *= 0.5F;
    return var4;
  }


  float leafSize(int par1) {
    return (par1 >= 0 && par1 < this.leafDistanceLimit) ? ((par1 != 0 && par1 != this.leafDistanceLimit - 1) ? 3.0F : 2.0F) : -1.0F;
  }

  void generateLeafNode(int par1, int par2, int par3) {
    int var4 = par2;

    for (int var5 = par2 + this.leafDistanceLimit; var4 < var5; var4++) {
      float var6 = leafSize(var4 - par2);
      genTreeLayer(par1, var4, par3, var6, (byte)1, TFCBlocks.leaves);
    }
  }


  void placeBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block par3) {
    int[] var4 = { 0, 0, 0 };
    byte var5 = 0;

    byte var6;
    for (var6 = 0; var5 < 3; var5 = (byte)(var5 + 1)) {
      var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];
      if (Math.abs(var4[var5]) > Math.abs(var4[var6])) {
        var6 = var5;
      }
    }

    if (var4[var6] != 0) {
      byte var9, var7 = otherCoordPairs[var6];
      byte var8 = otherCoordPairs[var6 + 3];

      if (var4[var6] > 0) {
        var9 = 1;
      } else {
        var9 = -1;
      }

      double var10 = var4[var7] / var4[var6];
      double var12 = var4[var8] / var4[var6];
      int[] var14 = { 0, 0, 0 };
      int var15 = 0;

      for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9) {
        var14[var6] = MathHelper.floor_double((par1ArrayOfInteger[var6] + var15) + 0.5D);
        var14[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var15 * var10 + 0.5D);
        var14[var8] = MathHelper.floor_double(par1ArrayOfInteger[var8] + var15 * var12 + 0.5D);

        setBlockAndNotifyAdequately(this.worldObj, var14[0], var14[1], var14[2], par3, this.meta);
      }
    }
  }


  void generateLeaves() {
    int var1 = 0;

    for (int var2 = this.leafNodes.length; var1 < var2; var1++) {
      int var3 = this.leafNodes[var1][0];
      int var4 = this.leafNodes[var1][1];
      int var5 = this.leafNodes[var1][2];
      generateLeafNode(var3, var4, var5);
    }
  }


  boolean leafNodeNeedsBase(int par1) {
    return (par1 >= this.heightLimit * 0.2D);
  }

  void generateTrunk() {
    int var1 = this.basePos[0];
    int var2 = this.basePos[1];
    int var3 = this.basePos[1] + this.height;
    int var4 = this.basePos[2];
    int[] var5 = { var1, var2, var4 };
    int[] var6 = { var1, var3, var4 };
    if (this.trunkSize == 2) {
      for (int i = -2; i < 3; i++) {
        var5[0] = var1 + i;
        var6[0] = var1 + i;

        for (int j = -2; j < 3; j++) {
          if ((i != -2 || j != -2) && (i != -2 || j != 2) && (i != 2 || j != -2) && (i != 2 || j != 2)) {
            var5[2] = var4 + j;
            var6[2] = var4 + j;
            placeBlockLine(var5, var6, TFCBlocks.logNatural);
          }
        }
      }
    }

    if (this.trunkSize == 1) {
      for (int i = 0; i < 2; i++) {
        var5[0] = var1 + i;
        var6[0] = var1 + i;

        for (int j = 0; j < 2; j++) {
          if ((i != -2 || j != -2) && (i != -2 || j != 2) && (i != 2 || j != -2) && (i != 2 || j != 2)) {
            var5[2] = var4 + j;
            var6[2] = var4 + j;
            placeBlockLine(var5, var6, TFCBlocks.logNatural);
          }
        }
      }
    }
  }

  void generateLeafNodeBases() {
    int var1 = 0;
    int var2 = this.leafNodes.length;

    for (int[] var3 = { this.basePos[0], this.basePos[1], this.basePos[2] }; var1 < var2; var1++) {
      int[] var4 = this.leafNodes[var1];
      int[] var5 = { var4[0], var4[1], var4[2] };
      var3[1] = var4[3];
      int var6 = var3[1] - this.basePos[1];
      Vec3 from = Vec3.createVectorHelper(var3[0], var3[1], var3[2]);
      Vec3 to = Vec3.createVectorHelper(var5[0], var5[1], var5[2]);
      if (from.distanceTo(to) < this.height * 2.5D &&
        leafNodeNeedsBase(var6)) {
        placeBlockLine(var3, var5, TFCBlocks.logNatural);
        int var3l = this.leafNodes[var1][0];
        int var4l = this.leafNodes[var1][1];
        int var5l = this.leafNodes[var1][2];
        generateLeafNode(var3l, var4l, var5l);
      }
    }
  }


  int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger) {
    byte var8;
    int[] var3 = { 0, 0, 0 };
    byte var4 = 0;

    byte var5;
    for (var5 = 0; var4 < 3; var4 = (byte)(var4 + 1)) {
      var3[var4] = par2ArrayOfInteger[var4] - par1ArrayOfInteger[var4];
      if (Math.abs(var3[var4]) > Math.abs(var3[var5])) {
        var5 = var4;
      }
    }

    if (var3[var5] == 0) {
      return -1;
    }
    byte var6 = otherCoordPairs[var5];
    byte var7 = otherCoordPairs[var5 + 3];

    if (var3[var5] > 0) {
      var8 = 1;
    } else {
      var8 = -1;
    }

    double var9 = var3[var6] / var3[var5];
    double var11 = var3[var7] / var3[var5];
    int[] var13 = { 0, 0, 0 };
    int var14 = 0;

    int var15;
    for (var15 = var3[var5] + var8; var14 != var15; var14 += var8) {
      var13[var5] = par1ArrayOfInteger[var5] + var14;
      var13[var6] = MathHelper.floor_double(par1ArrayOfInteger[var6] + var14 * var9);
      var13[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var14 * var11);
      Block block = this.worldObj.getBlock(var13[0], var13[1], var13[2]);
      if (block != Blocks.air && block != TFCBlocks.leaves) {
        break;
      }
    }
    return (var14 == var15) ? -1 : Math.abs(var14);
  }



  private boolean valid(World wo, int rx, int ry, int rz) {
    if (ry >= wo.getHeight() - this.heightLimit) return false;
    for (int x = -1; x < 1; x++) {
      for (int z = -1; z < 1; z++)
      { if (wo.getBlock(rx + x, ry - 1, rz + z) == Blocks.air) return false;  }
    }  return true;
  }
}