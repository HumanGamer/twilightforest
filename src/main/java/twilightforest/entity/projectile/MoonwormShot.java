package twilightforest.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;

public class MoonwormShot extends TFThrowable {

	public MoonwormShot(EntityType<? extends MoonwormShot> type, Level level) {
		super(type, level);
	}

	public MoonwormShot(EntityType<? extends MoonwormShot> type, Level level, LivingEntity thrower) {
		super(type, level, thrower);
		this.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0F, 1.5F, 1.0F);
	}

	public MoonwormShot(Level level, double x, double y, double z) {
		super(TFEntities.MOONWORM_SHOT.get(), level, x, y, z);
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public float getPickRadius() {
		return 1.0F;
	}

	@Override
	protected float getGravity() {
		return 0.03F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.getLevel().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, TFBlocks.MOONWORM.get().defaultBlockState()), false, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		BlockPos pos = result.getBlockPos().relative(result.getDirection());
		BlockState currentState = this.getLevel().getBlockState(pos);

		if (currentState.getMaterial().isReplaceable() && !currentState.is(BlockTags.FIRE) && !currentState.is(Blocks.LAVA)) {
			this.getLevel().setBlockAndUpdate(pos, TFBlocks.MOONWORM.get().defaultBlockState().setValue(DirectionalBlock.FACING, result.getDirection()));
			this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
			// todo sound
		} else {
			ItemEntity squish = new ItemEntity(this.getLevel(), pos.getX(), pos.getY(), pos.getZ(), Items.LIME_DYE.getDefaultInstance());
			squish.spawnAtLocation(squish.getItem());
			this.gameEvent(GameEvent.ENTITY_DIE);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (result.getEntity() instanceof Player player && !player.hasItemInSlot(EquipmentSlot.HEAD)) {
			player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TFBlocks.MOONWORM.get()));
		} else {
			result.getEntity().hurt(new IndirectEntityDamageSource("moonworm", this, this), this.random.nextInt(3) == 0 ? 1 : 0);
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.getLevel().isClientSide()) {
			this.getLevel().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}
}
