//world gen minable
package com.bioxx.tfc.WorldGen.Generators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.bioxx.tfc.Blocks.Terrain.BlockIgEx;
import com.bioxx.tfc.Blocks.Terrain.BlockIgIn;
import com.bioxx.tfc.Blocks.Terrain.BlockMM;
import com.bioxx.tfc.Blocks.Terrain.BlockSed;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData.EnumOreGen;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMinableTFCNew extends WorldGenerator
{
        private static final Logger logger = FMLLog.getLogger();
        private static List<List<Object>> oreList = new ArrayList<List<Object>>();
        public static int mPChunkX;
        public static int mPChunkZ;
        private int xChunk;
        private int zChunk;
        public Block mPBlock;
        private final int minableBlockMeta;
        public static int mPPrevX;
        public static int mPPrevZ;
        public static Block mPPrevBlock;
        public static int mPPrevMeta;
        private static boolean genBeforeCheck;
        public static int mineCount;
        public static int mineCountM;

        private Random rand;
        private static World worldObj;
        
        private int oreMin, oreMax; 

        private final int rarity;       
        private final EnumOreGen type;
        private final int SphereXSize;
        private final int SphereYSize;
        private final int SphereZSize;
        private final int VeinWidth;
        private final int VeinBaseHeight;
        private final int VeinDownFactor;
        private final int AreaNumber;
        private final int AreaMaxDistance;
        private final int CellSize;
        private final int rnd;
        private final String oreName;


        private final Block minableBlock;
        private int numberOfBlocks;

        public WorldGenMinableTFCNew(EnumOreGen type, Block block, int j, Block layerBlock, int layerMeta, int rarity,
                        int rnd, int SphereXSize, int SphereYSize, int SphereZSize, int VeinWidth, int VeinBaseHeight, int VeinDownFactor, int an, int amd, int cs, String name)
        {
                this.minableBlock = block;
                this.minableBlockMeta = j;
                this.rarity = rarity;                               
                this.rnd = rnd;
                this.type = type;
                this.SphereXSize = SphereXSize; //35 
                this.SphereYSize = SphereYSize;  //5
                this.SphereZSize = SphereZSize;  // 25
                this.VeinWidth = VeinWidth;
                this.VeinBaseHeight = VeinBaseHeight;
                this.VeinDownFactor = VeinDownFactor;
                this.AreaNumber = an;//10;
                this.AreaMaxDistance = amd;//50;
                this.CellSize = cs;//10;
                this.oreName = name;        	
        }

        public boolean generateBeforeCheck() // takes a set of current global variables and checks to see if this ore has spawned before in this chunk
        {
                genBeforeCheck = false;
                genBeforeCheck = oreList.contains(Arrays.asList(mPBlock, minableBlockMeta));
                if(!genBeforeCheck) oreList.add(Arrays.asList(mPBlock, minableBlockMeta));
                return genBeforeCheck;
        }
        
        private int percent(float t)
        {
                int rrnd=1 + rand.nextInt(this.rnd-1);
                float z = t/100*rrnd;
                return (int)z;
        }

        private int toReal(int param)
        {               
                return rand.nextBoolean() ? param - percent(param) : param + percent(param);
        }
        
        private int getCenter(int min, int max, int size) throws Exception
        {        
        	int r = max - min - size;
        	if ( r < 0 ) throw new Exception();
        	return max - size/2 - rand.nextInt(max - min - size);
        }

        
	private void createMine(World worldObj, int x, int z, int miny, int maxy) {
		int posX = x;
		int posZ = z;

		numberOfBlocks = 0;
		int Y = 0;
		try {

			switch (type) {
			case Area:
				Y = getCenter(miny, maxy, this.AreaMaxDistance);
				bODgenerateArea(worldObj, rand, posX, Y, posZ, this.AreaMaxDistance);
				break;
			case Vein:
				int size = toReal(VeinBaseHeight);
				Y = getCenter(miny, maxy, size);
				bODgenerateVein(worldObj, rand, posX, Y, posZ, size);
				break;
			case Lens:
				int Ysize = toReal(this.SphereYSize);
				Y = getCenter(miny, maxy, Ysize);
				bODgenerateLens(worldObj, rand, posX, Y, posZ, toReal(this.SphereXSize), Ysize, toReal(this.SphereZSize));
				break;
			}
			if (TFCOptions.enableDebugMode) logger.warn("Generating " + this.oreName + " at x=" + posX + ", y=" + Y + ", z=" + posZ);
		} catch (Exception e) {
			logger.warn("Generating " + this.oreName + " at x=" + posX + ", y=" + Y + ", z=" + posZ);
			logger.warn("Configuration error ! ");
		}

		if (TFCOptions.enableDebugMode) logger.warn("Generated " + this.numberOfBlocks + " blocks");
	}

        public boolean generate(World world, Random random, int x, int z, int min, int max)//obsorb default system
        {
                mPChunkX = x;// set output chunk x // snap to grid
                mPChunkZ = z;// set output chunk z    
                this.rand = random;
                worldObj = world; // set world
                mineCount = 0; // this is a new chunk, so list gets set to the beginning
                oreList.clear(); // clear the list of ores, this is a new chunk   
                mPBlock = minableBlock;// set output block
                oreMin = min;
                oreMax = max;

                if(mPChunkX != mPPrevX || mPChunkZ != mPPrevZ || mPPrevBlock != mPBlock || minableBlockMeta != mPPrevMeta)
                {
                        if (!generateBeforeCheck())
                        {
                                mPPrevX = mPChunkX;
                                mPPrevZ = mPChunkZ;
                                xChunk = mPChunkX;
                                zChunk = mPChunkZ;
                                mPPrevBlock = mPBlock;
                                mPPrevMeta = minableBlockMeta;

                                if (rarity == 1 || rarity > 0 && rand.nextInt(rarity) == 0) createMine(worldObj, xChunk, zChunk, min, max);
                        }
                }
                return true;
        }

        public boolean bODgenerateVein(World world, Random rand, int parX, int parY, int parZ, int xyz)
        {
                int posX = parX;
                int posY = parY + xyz/2;
                int posZ = parZ;
                boolean directionxz;
                int vw = toReal(this.VeinWidth);  
                
                boolean [] array = new boolean[xyz];
                for (int l = 0; l < xyz ; l++)
                        array[l]=rand.nextBoolean();
                
                directionxz = rand.nextBoolean();
                int i=0;
                
                do {                    
                        if (directionxz) drawPlane(world, posX++, posY - i, posZ , array, directionxz, vw);
                        else drawPlane(world, posX, posY - i, posZ++ , array, directionxz, vw);
                        i += rand.nextInt(VeinDownFactor);
                        
                } while (i < xyz / 2);
                
                                
                
                return true;
        }
        
        private void drawPlane(World world, int x, int y, int z, boolean [] array, boolean directionxz, int size)
        {
                for (int i = 0 ; i < size ; i++)
                        if (directionxz)
                                drawLine(world, x, y, z + i, array, directionxz);
                        else drawLine(world, x + i, y, z, array, directionxz);

        }
        
        private void drawLine(World world, int x, int y, int z, boolean [] array, boolean directionxz)
        {
                int random = 10;
                for (int l = 0; l< array.length ; l++)
                {                       
                        int r = random - ((l * 100 / array.length)*random)/100;                 
                        if (r == 0) r = 1;                      
                        
                        
                        boolean smes=array[l];                  
                        
                        if (directionxz)
                        {
                                if (smes) x++;
                        }
                        else
                        {
                                if (smes) z++;
                        }
                        
                        int rar = 1;
                        if (l < array.length / 3) rar = 2;
                        else if (l < array.length / 3 * 2) rar = 0;                     
                                        
                        if (r == 1) setBlock(world, x, y + array.length / 2 - l, z, rar);
                        else if (rand.nextInt(r) == 1) setBlock(world, x, y + array.length / 2 - l, z, rar);
                }
        }
        
        private static double lengthSq(double x, double y, double z) {
        	return (x * x) + (y * y) + (z * z);
        }   

		public boolean bODgenerateArea(World world, Random rand, int par3, int par4, int par5, final int amd) {
			int amdDiv2real = toReal(amd) / 2;
			Vec3 start = Vec3.createVectorHelper(par3, par4, par5);
			for (int i = 0; i < this.AreaNumber; i++) {
				Vec3 to;
				do {
					int x = rand.nextInt(amdDiv2real);
					int y = rand.nextInt(amdDiv2real);
					int z = rand.nextInt(amdDiv2real);
					to = Vec3.createVectorHelper(rand.nextBoolean() ? par3 + x : par3 - x, rand.nextBoolean() ? par4 + y : par4 - y, rand.nextBoolean() ? 
							par5 + z : par5 - z);
				} while (start.distanceTo(to) > amdDiv2real);
				int mult = (int) (this.CellSize * 0.66666666666);
				int rx = 1 + this.CellSize / 3 + rand.nextInt(mult);
				int ry = 1 + this.CellSize / 3 + rand.nextInt(mult);
				int rz = 1 + this.CellSize / 3 + rand.nextInt(mult);
				bODgenerateLens(world, rand, (int) to.xCoord, (int) to.yCoord, (int) to.zCoord, rx, ry, rz);
			}
			return true;
		}
        
		public boolean bODgenerateLens(World world, Random rand, int par3, int par4, int par5, int dx, int dy, int dz) {

			int xpos = par3;
			int ypos = par4;
			int zpos = par5;

			double radiusX = dx / 2;
			double radiusY = dy / 2;
			double radiusZ = dz / 2;

			radiusX += 0.5;
			radiusY += 0.5;
			radiusZ += 0.5;

			final double invRadiusX = 1 / radiusX;
			final double invRadiusY = 1 / radiusY;
			final double invRadiusZ = 1 / radiusZ;

			final int ceilRadiusX = (int) Math.ceil(radiusX);
			final int ceilRadiusY = (int) Math.ceil(radiusY);
			final int ceilRadiusZ = (int) Math.ceil(radiusZ);

			double nextXn = 0;
			forX: for (int x = 0; x <= ceilRadiusX; ++x) {
				final double xn = nextXn;
				nextXn = (x + 1) * invRadiusX;
				double nextYn = 0;
				forY: for (int y = 0; y <= ceilRadiusY; ++y) {
					final double yn = nextYn;
					nextYn = (y + 1) * invRadiusY;
					double nextZn = 0;
					forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
						final double zn = nextZn;
						nextZn = (z + 1) * invRadiusZ;

						double distanceSq = lengthSq(xn, yn, zn);
						if (distanceSq > 1) {
							if (z == 0) {
								if (y == 0) break forX;								
								break forY;
							}
							break forZ;
						}

						double px = lengthSq(nextXn, yn, zn);
						double py = lengthSq(xn, nextYn, zn);
						double pz = lengthSq(xn, yn, nextZn);

						if (px <= 0.15 && py <= 0.15 && pz <= 0.05) createEllipse(world, xpos, ypos, zpos, x, y, z, 2, 1);
						else if (px <= 0.50 && py <= 0.50 && pz <= 0.43333) createEllipse(world, xpos, ypos, zpos, x, y, z, 8, 0);
						else createEllipse(world, xpos, ypos, zpos, x, y, z, 25, 2);
					}
				}
			}
			return true;
		}

		private void createEllipse(World world, int xpos, int ypos, int zpos, int x, int y, int z, int r, int g) {
			if (rand.nextInt(r) == 1) setBlock(world, xpos + x, ypos + y, zpos + z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos - x, ypos + y, zpos + z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos + x, ypos - y, zpos + z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos + x, ypos + y, zpos - z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos - x, ypos - y, zpos + z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos + x, ypos - y, zpos - z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos - x, ypos + y, zpos - z, g);
			if (rand.nextInt(r) == 1) setBlock(world, xpos - x, ypos - y, zpos - z, g);
		}

		private boolean canPlace(Block b) {
			return b instanceof BlockMM || b instanceof BlockIgIn || b instanceof BlockSed || b instanceof BlockIgEx;
		}

		private void setBlock(World world, int posX, int posY, int posZ, int g) {
			int m = world.getBlockMetadata(posX, posY, posZ);
			Block b = world.getBlock(posX, posY, posZ);

			if ((canPlace(b)) && (posY >= oreMin) && (posY <= oreMax)) {
                if (mPBlock != null && world.setBlock(posX, posY, posZ, mPBlock, minableBlockMeta, 2)) {
                    TEOre te = (TEOre) world.getTileEntity(posX, posY, posZ);
                    if (te != null) {
                        te.baseBlockID = Block.getIdFromBlock(b);
                        te.baseBlockMeta = m;
                        te.extraData = (byte) (g);
                        numberOfBlocks++;
                    }
                }
            }
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k) {
			return false;
		}
	}
