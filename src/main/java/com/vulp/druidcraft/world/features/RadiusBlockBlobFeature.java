package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.world.config.BlockStateRadiusFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class RadiusBlockBlobFeature extends Feature<BlockStateRadiusFeatureConfig> {
  public RadiusBlockBlobFeature(Codec<BlockStateRadiusFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateRadiusFeatureConfig config) {
    while (true) {
      label46:
      {
        if (pos.getY() > 3) {
          if (reader.isAirBlock(pos.down())) {
            break label46;
          }

          Block block = reader.getBlockState(pos.down()).getBlock();
          if (!isDirt(block) && !isStone(block)) {
            break label46;
          }
        }

        if (pos.getY() <= 3) {
          return false;
        }

        int i1 = config.startRadius;

        for (int i = 0; i1 >= 0 && i < 3; ++i) {
          int j = i1 + rand.nextInt(2);
          int k = i1 + rand.nextInt(2);
          int l = i1 + rand.nextInt(2);
          float f = (float) (j + k + l) * 0.333F + 0.5F;

          for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-j, -k, -l), pos.add(j, k, l))) {
            if (blockpos.distanceSq(pos) <= (double) (f * f)) {
              reader.setBlockState(blockpos, config.state, 4);
            }
          }

          pos = pos.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), 0 - rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
        }

        return true;
      }

      pos = pos.down();
    }
  }
}
