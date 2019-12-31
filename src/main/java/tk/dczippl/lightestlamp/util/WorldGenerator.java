package tk.dczippl.lightestlamp.util;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import tk.dczippl.lightestlamp.Config;
import tk.dczippl.lightestlamp.init.ModBlocks;

import java.util.BitSet;
import java.util.Random;

public class WorldGenerator
{
    private static final GlowstoneOreFeature GLOWSTONE = new GlowstoneOreFeature();
    //private static final OreFeatureConfig.FillerBlockType GLOWSTONE = new OreFeatureConfig.FillerBlockType().;

    private static final CountRangeConfig NEON_BLOCK = new CountRangeConfig(362, 64, 0, 128);
    private static final int NEON_BLOCK_VEINSIZE = 48;
    private static final CountRangeConfig ARGON_BLOCK = new CountRangeConfig(284, 64, 0, 128);
    private static final int ARGON_BLOCK_VEINSIZE = 48;
    private static final CountRangeConfig KRYPTON_BLOCK = new CountRangeConfig(248, 86, 0, 128);
    private static final int KRYPTON_BLOCK_VEINSIZE = 42;

    public static void setupWorldGeneraton()
    {

        for (Biome biome : ForgeRegistries.BIOMES)
        {
            if (biome.getCategory() == Biome.Category.NETHER)
            {
                if (Config.NEON_GLOWSTONE_SPAWN.get())
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GLOWSTONE.func_225566_b_(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.NEON_BLOCK.getDefaultState(),NEON_BLOCK_VEINSIZE))
                            .func_227228_a_( Placement.COUNT_RANGE.func_227446_a_(NEON_BLOCK)));
                if (Config.ARGON_GLOWSTONE_SPAWN.get())
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GLOWSTONE.func_225566_b_(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.ARGON_BLOCK.getDefaultState(),ARGON_BLOCK_VEINSIZE))
                            .func_227228_a_( Placement.COUNT_RANGE.func_227446_a_(ARGON_BLOCK)));
                if (Config.KRYPTON_GLOWSTONE_SPAWN.get())
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GLOWSTONE.func_225566_b_(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.KRYPTON_BLOCK.getDefaultState(),KRYPTON_BLOCK_VEINSIZE))
                            .func_227228_a_( Placement.COUNT_RANGE.func_227446_a_(KRYPTON_BLOCK)));
            }
        }
    }

    public static class GlowstoneOreFeature extends OreFeature {
        public GlowstoneOreFeature() {
            super(null);
        }

        @Override
        protected boolean func_207803_a(IWorld p_207803_1_, Random p_207803_2_, OreFeatureConfig p_207803_3_, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_) {
            int lvt_21_1_ = 0;
            BitSet lvt_22_1_ = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
            BlockPos.Mutable lvt_23_1_ = new BlockPos.Mutable();
            double[] lvt_24_1_ = new double[p_207803_3_.size * 4];

            int lvt_25_2_;
            double lvt_27_2_;
            double lvt_29_2_;
            double lvt_31_2_;
            double lvt_33_2_;
            for (lvt_25_2_ = 0; lvt_25_2_ < p_207803_3_.size; ++lvt_25_2_) {
                float lvt_26_1_ = (float) lvt_25_2_ / (float) p_207803_3_.size;
                lvt_27_2_ = MathHelper.lerp((double) lvt_26_1_, p_207803_4_, p_207803_6_);
                lvt_29_2_ = MathHelper.lerp((double) lvt_26_1_, p_207803_12_, p_207803_14_);
                lvt_31_2_ = MathHelper.lerp((double) lvt_26_1_, p_207803_8_, p_207803_10_);
                lvt_33_2_ = p_207803_2_.nextDouble() * (double) p_207803_3_.size / 16.0D;
                double lvt_35_1_ = ((double) (MathHelper.sin(3.1415927F * lvt_26_1_) + 1.0F) * lvt_33_2_ + 1.0D) / 2.0D;
                lvt_24_1_[lvt_25_2_ * 4 + 0] = lvt_27_2_;
                lvt_24_1_[lvt_25_2_ * 4 + 1] = lvt_29_2_;
                lvt_24_1_[lvt_25_2_ * 4 + 2] = lvt_31_2_;
                lvt_24_1_[lvt_25_2_ * 4 + 3] = lvt_35_1_;
            }

            for (lvt_25_2_ = 0; lvt_25_2_ < p_207803_3_.size - 1; ++lvt_25_2_) {
                if (lvt_24_1_[lvt_25_2_ * 4 + 3] > 0.0D) {
                    for (int lvt_26_2_ = lvt_25_2_ + 1; lvt_26_2_ < p_207803_3_.size; ++lvt_26_2_) {
                        if (lvt_24_1_[lvt_26_2_ * 4 + 3] > 0.0D) {
                            lvt_27_2_ = lvt_24_1_[lvt_25_2_ * 4 + 0] - lvt_24_1_[lvt_26_2_ * 4 + 0];
                            lvt_29_2_ = lvt_24_1_[lvt_25_2_ * 4 + 1] - lvt_24_1_[lvt_26_2_ * 4 + 1];
                            lvt_31_2_ = lvt_24_1_[lvt_25_2_ * 4 + 2] - lvt_24_1_[lvt_26_2_ * 4 + 2];
                            lvt_33_2_ = lvt_24_1_[lvt_25_2_ * 4 + 3] - lvt_24_1_[lvt_26_2_ * 4 + 3];
                            if (lvt_33_2_ * lvt_33_2_ > lvt_27_2_ * lvt_27_2_ + lvt_29_2_ * lvt_29_2_ + lvt_31_2_ * lvt_31_2_) {
                                if (lvt_33_2_ > 0.0D) {
                                    lvt_24_1_[lvt_26_2_ * 4 + 3] = -1.0D;
                                } else {
                                    lvt_24_1_[lvt_25_2_ * 4 + 3] = -1.0D;
                                }
                            }
                        }
                    }
                }
            }

            for (lvt_25_2_ = 0; lvt_25_2_ < p_207803_3_.size; ++lvt_25_2_) {
                double lvt_26_3_ = lvt_24_1_[lvt_25_2_ * 4 + 3];
                if (lvt_26_3_ >= 0.0D) {
                    double lvt_28_1_ = lvt_24_1_[lvt_25_2_ * 4 + 0];
                    double lvt_30_1_ = lvt_24_1_[lvt_25_2_ * 4 + 1];
                    double lvt_32_1_ = lvt_24_1_[lvt_25_2_ * 4 + 2];
                    int lvt_34_1_ = Math.max(MathHelper.floor(lvt_28_1_ - lvt_26_3_), p_207803_16_);
                    int lvt_35_2_ = Math.max(MathHelper.floor(lvt_30_1_ - lvt_26_3_), p_207803_17_);
                    int lvt_36_1_ = Math.max(MathHelper.floor(lvt_32_1_ - lvt_26_3_), p_207803_18_);
                    int lvt_37_1_ = Math.max(MathHelper.floor(lvt_28_1_ + lvt_26_3_), lvt_34_1_);
                    int lvt_38_1_ = Math.max(MathHelper.floor(lvt_30_1_ + lvt_26_3_), lvt_35_2_);
                    int lvt_39_1_ = Math.max(MathHelper.floor(lvt_32_1_ + lvt_26_3_), lvt_36_1_);

                    for (int lvt_40_1_ = lvt_34_1_; lvt_40_1_ <= lvt_37_1_; ++lvt_40_1_) {
                        double lvt_41_1_ = ((double) lvt_40_1_ + 0.5D - lvt_28_1_) / lvt_26_3_;
                        if (lvt_41_1_ * lvt_41_1_ < 1.0D) {
                            for (int lvt_43_1_ = lvt_35_2_; lvt_43_1_ <= lvt_38_1_; ++lvt_43_1_) {
                                double lvt_44_1_ = ((double) lvt_43_1_ + 0.5D - lvt_30_1_) / lvt_26_3_;
                                if (lvt_41_1_ * lvt_41_1_ + lvt_44_1_ * lvt_44_1_ < 1.0D) {
                                    for (int lvt_46_1_ = lvt_36_1_; lvt_46_1_ <= lvt_39_1_; ++lvt_46_1_) {
                                        double lvt_47_1_ = ((double) lvt_46_1_ + 0.5D - lvt_32_1_) / lvt_26_3_;
                                        if (lvt_41_1_ * lvt_41_1_ + lvt_44_1_ * lvt_44_1_ + lvt_47_1_ * lvt_47_1_ < 1.0D) {
                                            int lvt_49_1_ = lvt_40_1_ - p_207803_16_ + (lvt_43_1_ - p_207803_17_) * p_207803_19_ + (lvt_46_1_ - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                            if (!lvt_22_1_.get(lvt_49_1_)) {
                                                lvt_22_1_.set(lvt_49_1_);
                                                lvt_23_1_.setPos(lvt_40_1_, lvt_43_1_, lvt_46_1_);
                                                if (p_207803_1_.getBlockState(lvt_23_1_).getBlock() == Blocks.GLOWSTONE) {
                                                    p_207803_1_.setBlockState(lvt_23_1_, p_207803_3_.state, 2);
                                                    ++lvt_21_1_;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return lvt_21_1_ > 0;
        }
    }
}
