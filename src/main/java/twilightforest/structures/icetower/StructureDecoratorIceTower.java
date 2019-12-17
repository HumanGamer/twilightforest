package twilightforest.structures.icetower;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorIceTower extends StructureTFDecorator {

	public StructureDecoratorIceTower() {
		this.blockState = TFBlocks.aurora_block.getDefaultState();
		this.accentState = Blocks.PLANKS.getDefaultState().with(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
		this.fenceState = Blocks.OAK_FENCE.getDefaultState();
		this.stairState = Blocks.BIRCH_STAIRS.getDefaultState();
		this.pillarState = TFBlocks.aurora_pillar.getDefaultState().with(BlockRotatedPillar.AXIS, Direction.Axis.Y);
		this.platformState = Blocks.WOODEN_SLAB.getDefaultState().with(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.BIRCH);
		this.floorState = Blocks.PLANKS.getDefaultState().with(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
		this.randomBlocks = new StructureTFAuroraBricks();
	}

}
