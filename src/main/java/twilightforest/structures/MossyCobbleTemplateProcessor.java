package twilightforest.structures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;

public class MossyCobbleTemplateProcessor extends RandomizedTemplateProcessor {

    public MossyCobbleTemplateProcessor(BlockPos pos, PlacementSettings settings) {
        super(pos, settings);
    }

    @Nullable
    @Override
    public Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfo) {
        if (shouldPlaceBlock()) {
            BlockState state = blockInfo.blockState;
            Block block = state.getBlock();

            if (block == Blocks.COBBLESTONE)
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), null);

            if (block == Blocks.COBBLESTONE_WALL)
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, state.with(BlockWall.VARIANT, BlockWall.EnumType.MOSSY), null);

            return blockInfo;
        }

        return null;
    }
}