package twilightforest.structures.mushroomtower;

import net.minecraft.block.BlockPlanks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public class ComponentTFMushroomTowerBridge extends ComponentTFMushroomTowerWing {

	int dSize;
	int dHeight;

	public ComponentTFMushroomTowerBridge() {
		super();
	}

	protected ComponentTFMushroomTowerBridge(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(feature, i, x, y, z, pSize, pHeight, direction);

		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, 3, direction);

		this.dSize = pSize;
		this.dHeight = pHeight;
	}

	/**
	 * Save to NBT
	 */
	@Override
	protected void writeStructureToNBT(CompoundNBT tagCompound) {
		super.writeStructureToNBT(tagCompound);

		tagCompound.putInt("destSize", this.dSize);
		tagCompound.putInt("destHeight", this.dHeight);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void readStructureFromNBT(CompoundNBT tagCompound, TemplateManager templateManager) {
		super.readStructureFromNBT(tagCompound, templateManager);
		this.dSize = tagCompound.getInt("destSize");
		this.dHeight = tagCompound.getInt("destHeight");
	}

	@Override
	public void buildComponent(StructureComponent parent, List<StructureComponent> list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponentOld) {
			this.deco = ((StructureTFComponentOld) parent).deco;
		}

		int[] dest = new int[]{dSize - 1, 1, 1};
		boolean madeWing = makeTowerWing(list, rand, this.getComponentType(), dest[0], dest[1], dest[2], dSize, dHeight, Rotation.NONE);

		if (!madeWing) {
			int[] dx = offsetTowerCoords(dest[0], dest[1], dest[2], dSize, Direction.SOUTH);
			TwilightForestMod.LOGGER.info("Making tower wing failed when bridge was already made.  Size = {}, x = {}, z = {}", dSize, dx[0], dx[2]);
		}
	}

	public StructureBoundingBox getWingBB() {
		int[] dest = offsetTowerCoords(dSize - 1, 1, 1, dSize, this.getCoordBaseMode());
		return StructureTFComponentOld.getComponentToAddBoundingBox(dest[0], dest[1], dest[2], 0, 0, 0, dSize - 1, dHeight - 1, dSize - 1, this.getCoordBaseMode());

	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {

		// make walls
		for (int x = 0; x < dSize; x++) {
			setBlockState(world, deco.fenceState, x, 1, 0, sbb);
			setBlockState(world, deco.fenceState, x, 1, 2, sbb);

			setBlockState(world, this.isAscender ? deco.floorState.with(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE) : deco.floorState, x, 0, 1, sbb);
		}

		// clear bridge walkway
		this.fillWithAir(world, sbb, 0, 1, 1, 2, 2, 1);


		return true;
	}

}
