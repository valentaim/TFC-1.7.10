package com.bioxx.tfc.WorldGen.Generators.Trees.New;

import com.bioxx.tfc.Core.TFC_Core;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;




public class WorldGenCyprusTree
  extends WorldGenerator {
  World wo;
  Random rand;
  int rootRand;
  int rootAlt;
  private Block cyprusWoodBlock;
  private Block cyprusLeafBlock;
  private int woodMeta;
  private int leafMeta;
  private int heightmin;
  private int heightmax;
  private int meta;

  public WorldGenCyprusTree(boolean flag, int id) {
    super(flag);
    this.meta = id;
    this.rootRand = 0;
    this.rootAlt = 0;
    this.woodMeta = this.meta;
    this.leafMeta = this.meta;
  }

  public void setConfigOptions(Block wood, Block leaf, int height1, int height2) {
    this.cyprusWoodBlock = wood;
    this.cyprusLeafBlock = leaf;
    this.heightmin = height1;
    this.heightmax = height2;
  }

  public boolean generate(World world, Random random, int i, int j, int k) {
    this.wo = world;
    this.rand = random;

    int l = random.nextInt(this.heightmax - this.heightmin) + this.heightmin;
    if (j < 1) return false;
    if (j + l + 1 > 256) {
      return false;
    }
    if (!valid(i, j, k)) return false;


    double pitch = Math.PI/2.0*0.8;
    double dir = this.rand.nextFloat(), spin = 2.0*Math.PI*0.618;

    double rootSlope = -Math.PI/3.0;
    j += 4;
    growRoot(i - 1, j, k, 0.5625D, rootSlope);
    growRoot(i - 1, j, k + 1, 0.4375D, rootSlope);
    growRoot(i, j, k + 2, 0.3125D, rootSlope);
    growRoot(i + 1, j, k + 2, 0.1875D, rootSlope);
    growRoot(i + 2, j, k + 1, 0.0625D, rootSlope);
    growRoot(i + 2, j, k, 0.9375D, rootSlope);
    growRoot(i + 1, j, k - 1, 0.8125D, rootSlope);
    growRoot(i, j, k - 1, 0.6875D, rootSlope);
    j -= 4;
    int n;
    for (n = 0; n < l; n++) {
      if (n < l - 2) {
        setBlockAndNotifyAdequately(world, i, j + n, k, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k + 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i, j + n, k + 1, this.cyprusWoodBlock, this.woodMeta);
      } else {
        setBlockAndNotifyAdequately(world, i, j + n, k, this.cyprusLeafBlock, this.leafMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k, this.cyprusLeafBlock, this.leafMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k + 1, this.cyprusLeafBlock, this.leafMeta);
        setBlockAndNotifyAdequately(world, i, j + n, k + 1, this.cyprusLeafBlock, this.leafMeta);
      }
      if (n <= 8) {
        setBlockAndNotifyAdequately(world, i, j + n, k - 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k - 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 2, j + n, k, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 2, j + n, k + 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k + 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i, j + n, k + 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 1, j + n, k + 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 1, j + n, k, this.cyprusWoodBlock, this.woodMeta);
      }
      if (n <= 3) {
        setBlockAndNotifyAdequately(world, i - 1, j + n, k - 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 1, j + n, k + 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 2, j + n, k + 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 2, j + n, k - 1, this.cyprusWoodBlock, this.woodMeta);
      }
      if (n <= 1) {
        setBlockAndNotifyAdequately(world, i, j + n, k + 3, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k + 3, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i, j + n, k - 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 1, j + n, k - 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 3, j + n, k, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 3, j + n, k + 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 2, j + n, k, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 2, j + n, k + 1, this.cyprusWoodBlock, this.woodMeta);
      }
      if (n == 0) {
        setBlockAndNotifyAdequately(world, i - 2, j + n, k - 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 1, j + n, k - 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 2, j + n, k + 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i - 1, j + n, k + 3, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 3, j + n, k + 2, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 2, j + n, k + 3, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 3, j + n, k - 1, this.cyprusWoodBlock, this.woodMeta);
        setBlockAndNotifyAdequately(world, i + 2, j + n, k - 2, this.cyprusWoodBlock, this.woodMeta);
      }
    }

    for (n = 5; n < l - 2; n++) {
      if (this.rand.nextInt(l + 10) < n)
        growBranch(i, j + n, k, (l - n) * 0.618D, dir, pitch, 0.0D, 0.0D, 0);
      dir += spin;
    }
    return true;
  }

  private void growBranch(int i, int j, int k, double len, double dir, double pitch, double pbias, double pbias2, int size) {
    double dx = 0.0D;
    double dy = 0.0D;
    double dz = 0.0D;


    double spin = 0.0D;
    double heave = 0.0D;

    double blen = len * 0.75D;
    plotWood(i, j, k, size);
    while (len > 1.0D) {
      dy += Math.sin(pitch);
      double dd = Math.cos(pitch);
      dx += Math.cos(dir) * dd;
      dz += Math.sin(dir) * dd;
      boolean step = false;
      if (dx >= 1.0D) {
        i++;
        dx--;
        step = true;
      } else if (dx <= -1.0D) {
        i--;
        dx++;
        step = true;
      }
      if (dy >= 1.0D) {
        j++;
        dy--;
        step = true;
      } else if (dy <= -1.0D) {
        j--;
        dy++;
        step = true;
      }
      if (dz >= 1.0D) {
        k++;
        dz--;
        step = true;
      } else if (dz <= -1.0D) {
        k--;
        dz++;
        step = true;
      }
      if (step) {
        Block id = this.wo.getBlock(i, j, k);
        if (id == Blocks.air || id == this.cyprusWoodBlock || id == this.cyprusLeafBlock) {
          plotWood(i, j, k, size);
        } else {
          break;
        }
      }

      if (len > 2.0D && len < blen) {
        int id1;
        if (size == 0) {
          id1 = this.rand.nextInt(8);
        } else {
          id1 = 0;
        }

        if (id1 == 1) growBranch(i, j, k, len, dir + 0.7853981633974483D, 0.0D, 0.0D, 0.0D, 1);
        if (id1 == 2) growBranch(i, j, k, len, dir - 0.7853981633974483D, 0.0D, 0.0D, 0.0D, 1);

      }

      spin += this.rand.nextFloat() * 0.1D - 0.05D;
      heave += this.rand.nextFloat() * 0.1D - 0.05D;
      dir += spin;
      if (pitch > pbias) {
        heave -= 0.01D;
      } else {
        heave += 0.01D;
      }
      if (heave > 0.2D) heave = 0.2D;
      if (heave < -0.2D) heave = -0.2D;
      pitch += heave;

      pitch = (pitch - pbias2) * 0.8D + pbias2;
      len--;
    }
    treeLeaf(i, j, k, 3);
  }

  private void plotWood(int i, int j, int k, int size) {
    setBlockAndNotifyAdequately(this.wo, i, j, k, this.cyprusWoodBlock, this.woodMeta);
  }

  void treeLeaf(int i, int j, int k, int r) {
    if (r <= 0)
      return;
    int rr = r * r + 1;
    for (int ii = -r; ii <= r; ii++) {
      for (int jj = 0; jj <= 1; jj++) {
        for (int kk = -r; kk <= r; kk++) {
          if (ii * ii + jj * jj + kk * kk <= rr &&
                  this.wo.getBlock(i + ii, j + jj, k + kk) == Blocks.air) {
            setBlockAndNotifyAdequately(this.wo, i + ii, j + jj, k + kk, this.cyprusLeafBlock, this.leafMeta);
          }
        }
      }
    }
  }


    private int getMedium ( int i, int j, int k){
      Block[] canGrowOpen = {Blocks.air, Blocks.sapling, (Block) Blocks.flowing_water, Blocks.water, (Block) Blocks.flowing_lava, Blocks.lava, Blocks.log, Blocks.log2, (Block) Blocks.leaves, (Block) Blocks.leaves2};
      Block[] canGrowSolid = {(Block) Blocks.grass, Blocks.dirt, (Block) Blocks.sand, Blocks.gravel};
      Block qq = this.wo.getBlock(i, j, k);

      int medium = 0;
      int m;
      for (m = 0; m < canGrowOpen.length; m++) {
        if (qq == canGrowOpen[m]) {
          medium = 1;
          break;
        }
      }
      if (medium == 0) {
        for (m = 0; m < canGrowSolid.length; m++) {
          if (qq == canGrowSolid[m]) {
            medium = 2;
            break;
          }
        }
      }
      return medium;
    }


    void growRoot ( int l, int m, int n, double theta, double phi){
      double x, z;
      if (this.rootAlt == 1) {
        this.rootRand = this.rand.nextInt(2);
        m -= this.rootRand;
        this.rootAlt = 2;
      } else if (this.rootAlt == 2) {
        if (this.rootRand == 0)
          m--;
        this.rootAlt = 0;
      } else if (this.rootAlt == 10) {
        m -= this.rand.nextInt(2);
      }
      m++;
      phi -= this.rand.nextFloat() * 0.05D;
      theta += this.rand.nextFloat() * 0.1D - 0.05D;
      double direction = 6.283185307179586D * theta;
      double curl = (this.rand.nextFloat() * 0.4F - 0.2F);
      double pitch = 6.283185307179586D * phi;
      int length = 20 + this.rand.nextInt(4);

      if (l > 0) {
        x = l + 0.5D;
      } else {
        x = l - 0.5D;
      }

      double y = m + 0.5D;
      if (n > 0) {
        z = n + 0.5D;
      } else {
        z = n - 0.5D;
      }

      int i = (int) x, j = (int) y, k = (int) z;

      int med = getMedium(i, j, k);
      int cnt = 0;
      while (length > 0.0D) {
        boolean dug;
        length--;

        curl = curl + (this.rand.nextFloat() * 0.06F) - 0.029999999329447746D;
        if (pitch < 0.0D) {
          dug = false;
        } else {
          dug = true;
        }
        if (med == 1) {
          pitch = (pitch + 1.5707963267948966D) * 0.7D - 1.5707963267948966D;
        } else {
          pitch = (pitch - 1.5707963267948966D) * 0.7D + 1.5707963267948966D;
        }

        double hoz = Math.cos(pitch);
        double x2 = x + Math.cos(direction) * hoz;
        double y2 = y + Math.sin(pitch);
        double z2 = z + Math.sin(direction) * hoz;
        int i2 = (int) x2, j2 = (int) y2, k2 = (int) z2;
        if (i2 != i || j2 != j || k2 != k) {
          setBlockAndNotifyAdequately(this.wo, i, j, k, this.cyprusWoodBlock, this.woodMeta);
          if (dug) {
            if (this.wo.getBlock(i - 1, j, k) == Blocks.air)
              return;
            if (this.wo.getBlock(i + 1, j, k) == Blocks.air)
              return;
            if (this.wo.getBlock(i, j, k - 1) == Blocks.air)
              return;
            if (this.wo.getBlock(i, j, k + 1) == Blocks.air)
              return;
          }
          cnt++;
          if (cnt < 4 && (
                  j2 != j - 1 || i2 != i || k2 != k)) {
            setBlockAndNotifyAdequately(this.wo, i, j - 1, k, this.cyprusWoodBlock, this.woodMeta);
          }
          med = getMedium(i2, j2, k2);
          if (med != 0) {
            x = x2;
            y = y2;
            z = z2;
            i = i2;
            j = j2;
            k = k2;
            continue;
          }
          med = getMedium(i, j - 1, k);
          if (med != 0) {
            y--;
            j--;
            pitch = -1.5707963267948966D;
            continue;
          }
          x2 = x + Math.cos(direction);
          z2 = z + Math.sin(direction);
          i2 = (int) x2;
          k2 = (int) z2;
          med = getMedium(i2, j, k2);
          if (med != 0) {
            x = x2;
            z = z2;
            i = i2;
            k = k2;
            pitch = 0.0D;
            continue;
          }
          int dir = (int) (direction * 8.0D / Math.PI);
          if (dir < 0) {
            dir = 15 - (15 - dir) % 16;
          } else {
            dir %= 16;
          }
          int pol = dir % 2;
          int di = i2 - i, dk = k2 - k;
          int[] tdir = {0, 0, 0, 0};
          if (di == 0 && dk == 0)
            if (dir < 1) {
              di = 1;
              dk = 0;
            } else if (dir < 3) {
              di = 1;
              dk = 1;
            } else if (dir < 5) {
              di = 0;
              dk = 1;
            } else if (dir < 7) {
              di = -1;
              dk = 1;
            } else if (dir < 9) {
              di = -1;
              dk = 0;
            } else if (dir < 11) {
              di = -1;
              dk = -1;
            } else if (dir < 13) {
              di = 0;
              dk = -1;
            } else if (dir < 15) {
              di = 1;
              dk = -1;
            } else {
              di = 1;
              dk = 0;
            }

          if (dk == 0) {
            if (di > 0) {
              if (pol == 1) {
                tdir[0] = 2;
                tdir[1] = 14;
                tdir[2] = 4;
                tdir[3] = 12;
              } else {
                tdir[0] = 14;
                tdir[1] = 2;
                tdir[2] = 12;
                tdir[3] = 4;
              }

            } else if (pol == 1) {
              tdir[0] = 6;
              tdir[1] = 10;
              tdir[2] = 4;
              tdir[3] = 12;
            } else {
              tdir[0] = 10;
              tdir[1] = 6;
              tdir[2] = 12;
              tdir[3] = 4;
            }

          } else if (di == 0) {
            if (dk > 0) {
              if (pol == 1) {
                tdir[0] = 2;
                tdir[1] = 6;
                tdir[2] = 0;
                tdir[3] = 8;
              } else {
                tdir[0] = 6;
                tdir[1] = 2;
                tdir[2] = 8;
                tdir[3] = 0;
              }

            } else if (pol == 1) {
              tdir[0] = 10;
              tdir[1] = 14;
              tdir[2] = 8;
              tdir[3] = 0;
            } else {
              tdir[0] = 14;
              tdir[1] = 10;
              tdir[2] = 0;
              tdir[3] = 8;
            }

          } else if (dk > 0) {
            if (di > 0) {
              if (pol == 1) {
                tdir[0] = 0;
                tdir[1] = 4;
                tdir[2] = 14;
                tdir[3] = 6;
              } else {
                tdir[0] = 4;
                tdir[1] = 0;
                tdir[2] = 6;
                tdir[3] = 14;
              }

            } else if (pol == 1) {
              tdir[0] = 4;
              tdir[1] = 8;
              tdir[2] = 2;
              tdir[3] = 10;
            } else {
              tdir[0] = 8;
              tdir[1] = 4;
              tdir[2] = 10;
              tdir[3] = 2;
            }

          } else if (di > 0) {
            if (pol == 1) {
              tdir[0] = 12;
              tdir[1] = 0;
              tdir[2] = 10;
              tdir[3] = 2;
            } else {
              tdir[0] = 0;
              tdir[1] = 12;
              tdir[2] = 2;
              tdir[3] = 10;
            }

          } else if (pol == 1) {
            tdir[0] = 8;
            tdir[1] = 12;
            tdir[2] = 6;
            tdir[3] = 14;
          } else {
            tdir[0] = 12;
            tdir[1] = 8;
            tdir[2] = 14;
            tdir[3] = 6;
          }


          for (int q = 0; q < 4; q++) {
            if (tdir[q] == 0) {
              di = 1;
              dk = 0;
            } else if (tdir[q] == 2) {
              di = 1;
              dk = 1;
            } else if (tdir[q] == 4) {
              di = 0;
              dk = 1;
            } else if (tdir[q] == 6) {
              di = -1;
              dk = 1;
            } else if (tdir[q] == 8) {
              di = -1;
              dk = 0;
            } else if (tdir[q] == 10) {
              di = -1;
              dk = -1;
            } else if (tdir[q] == 12) {
              di = 0;
              dk = -1;
            } else {
              di = 1;
              dk = -1;
            }
            i2 = i + di;
            k2 = k + dk;
            med = getMedium(i2, j, k2);
            if (med != 0) {
              i = i2;
              k = k2;
              x = i + 0.5D;
              z = k + 0.5D;
              pitch = 0.0D;
              direction = tdir[q] * 2.0D * Math.PI / 16.0D;
              break;
            }
          }
          if (med == 0) {
            return;
          }
        }
      }
    }

    private boolean valid ( int rx, int ry, int rz) {
      if (ry >= this.wo.getHeight() - this.heightmax) return false;
      for (int x = -2; x < 2; x++) {
        for (int z = -2; z < 2; z++) {
          if (!TFC_Core.isSoil(this.wo.getBlock(rx + x, ry - 1, rz + z))) return false;
        }
      }
      return true;
    }
  }