package twilightforest.structures.courtyard;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import twilightforest.block.BlockTFNagastoneStairs;
import twilightforest.block.TFBlocks;
import twilightforest.structures.RandomizedTemplateProcessor;

import javax.annotation.Nullable;

public class CourtyardTerraceTemplateProcessor extends RandomizedTemplateProcessor {

    public CourtyardTerraceTemplateProcessor(BlockPos pos, PlacementSettings settings) {
        super(pos, settings);
    }

    @Nullable
    @Override
    public Template.BlockInfo processBlock(World world, BlockPos pos, Template.BlockInfo blockInfo) {

        if (!shouldPlaceBlock()) return null;

        boolean shouldMakeNewBlockInfo = false;
        BlockState state = blockInfo.blockState;

        final BlockState SMOOTHBRICK_SLAB_STATE = Blocks.STONE_SLAB.getDefaultState().with(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK);
        final BlockState SMOOTHBRICK_STATE = Blocks.STONEBRICK.getDefaultState(); //Blocks.DOUBLE_STONE_SLAB.getDefaultState().with(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK).with(BlockDoubleStoneSlab.SEAMLESS, false);

        //final BlockState SANDSTONE_SLAB_STATE = Blocks.STONE_SLAB.getDefaultState().with(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
        //final BlockState SANDSTONE_STATE = Blocks.DOUBLE_STONE_SLAB.getDefaultState().with(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).with(BlockDoubleStoneSlab.SEAMLESS, false);

        if (state.getBlock() == Blocks.DOUBLE_STONE_SLAB && state.getValue(BlockDoubleStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND) {
            BlockState stateCheck = world.getBlockState(pos);
            if (stateCheck == SMOOTHBRICK_SLAB_STATE)
                return new Template.BlockInfo(pos, SMOOTHBRICK_SLAB_STATE, null);
            else if (stateCheck.getMaterial() == Material.AIR)
                return null;
            else
                state = SMOOTHBRICK_STATE;

            shouldMakeNewBlockInfo = true;
        }

        if (state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND) {
            BlockState stateCheck = world.getBlockState(pos);

            if (stateCheck.getMaterial() == Material.AIR)
                return null;
            else
                return new Template.BlockInfo(pos, SMOOTHBRICK_SLAB_STATE, null);
        }

        Block block = state.getBlock();

        if (block == Blocks.STONEBRICK && state != Blocks.STONEBRICK.getDefaultState().with(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED))
            return random.nextBoolean() ? (shouldMakeNewBlockInfo ? new Template.BlockInfo(blockInfo.pos, state, blockInfo.tileentityData) : blockInfo) : new Template.BlockInfo(pos, state.with(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.values()[random.nextInt(3)]), null);

        if (state == Blocks.STONE_SLAB.getDefaultState().with(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE))
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, state.with(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE), null);

        if (block == TFBlocks.etched_nagastone)
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.etched_nagastone_mossy, TFBlocks.etched_nagastone_weathered), BlockDirectional.FACING), null);

        if (block == TFBlocks.nagastone_pillar)
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_pillar_mossy, TFBlocks.nagastone_pillar_weathered), BlockRotatedPillar.AXIS), null);

        if (block == TFBlocks.nagastone_stairs)
            return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_stairs, TFBlocks.nagastone_stairs), BlockTFNagastoneStairs.DIRECTION, BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);

        return blockInfo;
    }
}