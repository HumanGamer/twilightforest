package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.init.TFBlocks;
import twilightforest.loot.TFLootTables;
import twilightforest.init.TFLandmark;
import twilightforest.init.TFStructurePieceTypes;


public class MazeRoomVaultComponent extends MazeRoomComponent {

	public MazeRoomVaultComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
		super(TFStructurePieceTypes.TFMMRV.get(), nbt);
	}

	public MazeRoomVaultComponent(TFLandmark feature, int i, RandomSource rand, int x, int y, int z) {
		super(TFStructurePieceTypes.TFMMRV.get(), feature, i, rand, x, y, z);

		// specify a non-existant high spawn list value to stop actual monster spawns
		this.spawnListIndex = Integer.MAX_VALUE;
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// fill room with bricks
		generateBox(world, sbb, 0, 1, 0, 15, 4, 15, TFBlocks.DECORATIVE_MAZESTONE.get().defaultBlockState(), AIR, false);
		generateBox(world, sbb, 0, 2, 0, 15, 3, 15, TFBlocks.MAZESTONE_BRICK.get().defaultBlockState(), AIR, false);

		// 4x4 room in the middle
		generateAirBox(world, sbb, 6, 2, 6, 9, 3, 9);

		// pressure plates, sand & tnt
		generateBox(world, sbb, 6, 2, 5, 9, 2, 5, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 6, 2, 10, 9, 2, 10, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 5, 2, 6, 5, 2, 9, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 10, 2, 6, 10, 2, 9, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), AIR, false);

		// unfair sand
		generateBox(world, sbb, 6, 4, 5, 9, 4, 5, Blocks.SAND.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 6, 4, 10, 9, 4, 10, Blocks.SAND.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 5, 4, 6, 5, 4, 9, Blocks.SAND.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 10, 4, 6, 10, 4, 9, Blocks.SAND.defaultBlockState(), AIR, false);

		generateBox(world, sbb, 6, 0, 5, 9, 0, 5, Blocks.TNT.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 6, 0, 10, 9, 0, 10, Blocks.TNT.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 5, 0, 6, 5, 0, 9, Blocks.TNT.defaultBlockState(), AIR, false);
		generateBox(world, sbb, 10, 0, 6, 10, 0, 9, Blocks.TNT.defaultBlockState(), AIR, false);

		// LEWTZ!
		this.setDoubleLootChest(world, 7, 2, 6, 8, 2, 6, Direction.SOUTH, TFLootTables.LABYRINTH_VAULT, TFLootTables.LABYRINTH_ROOM, sbb, false);
		this.setDoubleLootChest(world, 8, 2, 9, 7, 2, 9, Direction.NORTH, TFLootTables.LABYRINTH_VAULT, TFLootTables.LABYRINTH_ROOM, sbb, false);
		this.setDoubleLootChest(world, 6, 2, 8, 6, 2, 7, Direction.EAST, TFLootTables.LABYRINTH_VAULT, TFLootTables.LABYRINTH_ROOM, sbb, false);
		this.setDoubleLootChest(world, 9, 2, 7, 9, 2, 8, Direction.WEST, TFLootTables.LABYRINTH_VAULT, TFLootTables.LABYRINTH_ROOM, sbb, false);

		// mazebreaker!
	}
}
