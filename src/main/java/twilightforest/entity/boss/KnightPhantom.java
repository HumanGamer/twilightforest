package twilightforest.entity.boss;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import twilightforest.init.TFSounds;
import twilightforest.advancements.TFAdvancements;
import twilightforest.init.TFBlocks;
import twilightforest.entity.ai.control.NoClipMoveControl;
import twilightforest.entity.ai.goal.PhantomAttackStartGoal;
import twilightforest.entity.ai.goal.PhantomThrowWeaponGoal;
import twilightforest.entity.ai.goal.PhantomUpdateFormationAndMoveGoal;
import twilightforest.entity.ai.goal.PhantomWatchAndAttackGoal;
import twilightforest.init.TFItems;
import twilightforest.loot.TFLootTables;
import twilightforest.init.TFDamageSources;
import twilightforest.init.TFLandmark;
import twilightforest.world.registration.TFGenerationSettings;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightPhantom extends FlyingMob implements Enemy {

	private static final EntityDataAccessor<Boolean> FLAG_CHARGING = SynchedEntityData.defineId(KnightPhantom.class, EntityDataSerializers.BOOLEAN);
	private static final AttributeModifier CHARGING_MODIFIER = new AttributeModifier("Charging attack boost", 7, AttributeModifier.Operation.ADDITION);

	private int number;
	private int ticksProgress;
	private Formation currentFormation;
	private BlockPos chargePos = BlockPos.ZERO;
	private final List<ServerPlayer> hurtBy = new ArrayList<>();

	public KnightPhantom(EntityType<? extends KnightPhantom> type, Level world) {
		super(type, world);
		this.noPhysics = true;
		this.currentFormation = Formation.HOVER;
		this.xpReward = 93;
		this.moveControl = new NoClipMoveControl(this);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		SpawnGroupData data = super.finalizeSpawn(accessor, difficulty, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(accessor.getRandom(), difficulty);
		this.populateDefaultEquipmentEnchantments(accessor.getRandom(), difficulty);
		return data;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TFItems.KNIGHTMETAL_SWORD.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TFItems.PHANTOM_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TFItems.PHANTOM_HELMET.get()));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(FLAG_CHARGING, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PhantomWatchAndAttackGoal(this));
		this.goalSelector.addGoal(1, new PhantomUpdateFormationAndMoveGoal(this));
		this.goalSelector.addGoal(2, new PhantomAttackStartGoal(this));
		this.goalSelector.addGoal(3, new PhantomThrowWeaponGoal(this));

		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 35.0D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D);
	}

	public Formation getCurrentFormation() {
		return this.currentFormation;
	}

	public BlockPos getChargePos() {
		return this.chargePos;
	}

	public void setChargePos(BlockPos pos) {
		this.chargePos = pos;
	}

	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return false;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource src) {
		return src == DamageSource.IN_WALL || super.isInvulnerableTo(src);
	}

	@Override
	public void checkDespawn() {
		if (this.getLevel().getDifficulty() == Difficulty.PEACEFUL) {
			if (this.hasHome() && this.getNumber() == 0) {
				this.getLevel().setBlockAndUpdate(getRestrictCenter(), TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get().defaultBlockState());
			}
			this.discard();
		} else {
			super.checkDespawn();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.isChargingAtPlayer()) {
			// make particles
			for (int i = 0; i < 4; ++i) {
				Item particleID = this.getRandom().nextBoolean() ? TFItems.PHANTOM_HELMET.get() : TFItems.KNIGHTMETAL_SWORD.get();

				this.getLevel().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(particleID)), getX() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), getY() + this.getRandom().nextFloat() * (this.getBbHeight() - 0.75D) + 0.5D, getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), 0, -0.1, 0);
				this.getLevel().addParticle(ParticleTypes.SMOKE, getX() + (this.getRandom().nextFloat() - 0.5D) * getBbWidth(), getY() + this.getRandom().nextFloat() * (this.getBbHeight() - 0.75D) + 0.5D, getZ() + (this.getRandom().nextFloat() - 0.5D) * this.getBbWidth(), 0, 0.1, 0);
			}
		}
	}

	@Override
	protected void tickDeath() {
		super.tickDeath();

		for (int i = 0; i < 20; ++i) {
			double d0 = this.getRandom().nextGaussian() * 0.02D;
			double d1 = this.getRandom().nextGaussian() * 0.02D;
			double d2 = this.getRandom().nextGaussian() * 0.02D;
			this.getLevel().addParticle(ParticleTypes.EXPLOSION, getX() + this.getRandom().nextFloat() * getBbWidth() * 2.0F - getBbWidth(), getY() + this.getRandom().nextFloat() * getBbHeight(), getZ() + this.getRandom().nextFloat() * getBbWidth() * 2.0F - getBbWidth(), d0, d1, d2);
		}
	}

	@Override
	public void die(DamageSource cause) {

		super.die(cause);

		if (this.getLevel() instanceof ServerLevel serverLevel && this.getNearbyKnights().isEmpty() && cause != DamageSource.OUT_OF_WORLD) {

			BlockPos treasurePos = this.hasHome() ? this.getRestrictCenter().below() : this.blockPosition();

			// make treasure for killing the last knight
			// This one won't receive the same loot treatment like the other bosses because this chest is supposed to reward for all of them instead of just the last one killed
			TFLootTables.STRONGHOLD_BOSS.generateChest(serverLevel, treasurePos, Direction.NORTH, false);

			//trigger criteria for killing every phantom in a group
			if(cause.getEntity() instanceof ServerPlayer player) {
				TFAdvancements.KILL_ALL_PHANTOMS.trigger(player);
			}

			// mark the stronghold as defeated
			TFGenerationSettings.markStructureConquered(this.getLevel(), treasurePos, TFLandmark.KNIGHT_STRONGHOLD);

			for(ServerPlayer player : this.hurtBy) {
				TFAdvancements.HURT_BOSS.trigger(player, this);
			}

			for(ServerPlayer player : this.getLevel().getEntitiesOfClass(ServerPlayer.class, new AABB(this.homePosition).inflate(10.0D))) {
				TFAdvancements.HURT_BOSS.trigger(player, this);
			}
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if(this.isDamageSourceBlocked(source)){
			this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.getLevel().getRandom().nextFloat() * 0.4F);
		}

		if(source.getEntity() instanceof ServerPlayer player && !this.hurtBy.contains(player)) {
			this.hurtBy.add(player);
		}
		return super.hurt(source, amount);
	}

	// [VanillaCopy] Exact copy of Mob.doHurtTarget, but change the damage source
	@Override
	public boolean doHurtTarget(Entity entity) {
		float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

		if (entity instanceof LivingEntity living) {
			f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), living.getMobType());
			f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
		}

		int i = EnchantmentHelper.getFireAspect(this);
		if (i > 0) {
			entity.setSecondsOnFire(i * 4);
		}

		boolean flag = entity.hurt(TFDamageSources.haunt(this), f);
		if (flag) {
			if (f1 > 0.0F && entity instanceof LivingEntity living) {
				living.knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}

			if (entity instanceof Player player) {
				this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
			}

			this.doEnchantDamageEffects(this, entity);
			this.setLastHurtMob(entity);
		}

		return flag;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public void knockback(double damage, double xRatio, double zRatio) {
		this.hasImpulse = true;
		float f = Mth.sqrt((float) (xRatio * xRatio + zRatio * zRatio));
		float distance = 0.2F;
		this.setDeltaMovement(new Vec3(this.getDeltaMovement().x() / 2.0D, this.getDeltaMovement().y() / 2.0D, this.getDeltaMovement().z() / 2.0D));
		this.setDeltaMovement(new Vec3(
				this.getDeltaMovement().x() - xRatio / f * distance,
				this.getDeltaMovement().y() + distance,
				this.getDeltaMovement().z() - zRatio / f * distance));

		if (this.getDeltaMovement().y() > 0.4D) {
			this.setDeltaMovement(this.getDeltaMovement().x(), 0.4D, this.getDeltaMovement().z());
		}
	}

	public List<KnightPhantom> getNearbyKnights() {
		return this.getLevel().getEntitiesOfClass(KnightPhantom.class, new AABB(this.getX(), this.getY(), this.getZ(), this.getX() + 1, this.getY() + 1, this.getZ() + 1).inflate(32.0D), LivingEntity::isAlive);
	}

	private void updateMyNumber() {
		List<Integer> nums = Lists.newArrayList();
		List<KnightPhantom> knights = getNearbyKnights();
		for (KnightPhantom knight : knights) {
			if (knight == this)
				continue;
			nums.add(knight.getNumber());
			if (knight.getNumber() == 0)
				restrictTo(knight.getRestrictCenter(), 20);
		}
		if (nums.isEmpty())
			return;
		int[] n = Ints.toArray(nums);
		Arrays.sort(n);
		int smallest = n[0];
		int largest = knights.size() + 1;
		int smallestUnused = largest + 1;
		if (smallest > 0) {
			smallestUnused = 0;
		} else {
			for (int i = 1; i < largest; i++) {
				if (Arrays.binarySearch(n, i) < 0) {
					smallestUnused = i;
					break;
				}
			}
		}
		if (this.number > smallestUnused || nums.contains(this.number))
			setNumber(smallestUnused);
	}

	public boolean isChargingAtPlayer() {
		return this.entityData.get(FLAG_CHARGING);
	}

	private void setChargingAtPlayer(boolean flag) {
		this.entityData.set(FLAG_CHARGING, flag);
		this.gameEvent(GameEvent.ENTITY_INTERACT);
		if (!this.getLevel().isClientSide()) {
			if (flag) {
				if (!getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(CHARGING_MODIFIER)) {
					getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(CHARGING_MODIFIER);
				}
			} else {
				getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(CHARGING_MODIFIER);
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.PHANTOM_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.PHANTOM_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.PHANTOM_DEATH.get();
	}

	private void switchToFormationByNumber(int formationNumber) {
		this.currentFormation = Formation.values()[formationNumber];
		this.ticksProgress = 0;
	}

	public void switchToFormation(Formation formation) {
		this.currentFormation = formation;
		this.ticksProgress = 0;
		this.updateMyNumber();
		this.setChargingAtPlayer(this.currentFormation == Formation.ATTACK_PLAYER_START || this.currentFormation == Formation.ATTACK_PLAYER_ATTACK);

	}

	private int getFormationAsNumber() {
		return this.currentFormation.ordinal();
	}

	public int getTicksProgress() {
		return this.ticksProgress;
	}

	public void setTicksProgress(int ticksProgress) {
		this.ticksProgress = ticksProgress;
	}

	public int getMaxTicksForFormation() {
		return this.currentFormation.duration;
	}

	public boolean isSwordKnight() {
		return this.getMainHandItem().is(TFItems.KNIGHTMETAL_SWORD.get());
	}

	public boolean isAxeKnight() {
		return this.getMainHandItem().is(TFItems.KNIGHTMETAL_AXE.get());
	}

	public boolean isPickKnight() {
		return this.getMainHandItem().is(TFItems.KNIGHTMETAL_PICKAXE.get());
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;

		// set weapon per number
		switch (number % 3) {
			case 0 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TFItems.KNIGHTMETAL_SWORD.get()));
			case 1 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TFItems.KNIGHTMETAL_AXE.get()));
			case 2 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TFItems.KNIGHTMETAL_PICKAXE.get()));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.hasHome()) {
			BlockPos home = this.getRestrictCenter();
			compound.put("Home", this.newDoubleList(home.getX(), home.getY(), home.getZ()));
		}
		compound.putInt("MyNumber", this.getNumber());
		compound.putInt("Formation", this.getFormationAsNumber());
		compound.putInt("TicksProgress", this.getTicksProgress());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		if (compound.contains("Home", 9)) {
			ListTag nbttaglist = compound.getList("Home", 6);
			int hx = (int) nbttaglist.getDouble(0);
			int hy = (int) nbttaglist.getDouble(1);
			int hz = (int) nbttaglist.getDouble(2);
			restrictTo(new BlockPos(hx, hy, hz), 20);
		}
		this.setNumber(compound.getInt("MyNumber"));
		this.switchToFormationByNumber(compound.getInt("Formation"));
		this.setTicksProgress(compound.getInt("TicksProgress"));
	}

	public enum Formation {

		HOVER(90),

		LARGE_CLOCKWISE(180),

		SMALL_CLOCKWISE(90),

		LARGE_ANTICLOCKWISE(180),

		SMALL_ANTICLOCKWISE(90),

		CHARGE_PLUSX(180),

		CHARGE_MINUSX(180),

		CHARGE_PLUSZ(180),

		CHARGE_MINUSZ(180),

		WAITING_FOR_LEADER(10),

		ATTACK_PLAYER_START(50),

		ATTACK_PLAYER_ATTACK(50);

		final int duration;

		Formation(int duration) {
			this.duration = duration;
		}
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	protected float getWaterSlowDown() {
		return 1.0F;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	// [VanillaCopy] Home fields and methods from CreatureEntity, changes noted
	private BlockPos homePosition = BlockPos.ZERO;
	private float maximumHomeDistance = -1.0F;

	@Override
	public boolean isWithinRestriction() {
		return this.isWithinRestriction(new BlockPos(this.blockPosition()));
	}

	@Override
	public boolean isWithinRestriction(BlockPos pos) {
		return this.maximumHomeDistance == -1.0F || this.homePosition.distSqr(pos) < this.maximumHomeDistance * this.maximumHomeDistance;
	}

	@Override
	public void restrictTo(BlockPos pos, int distance) {
		// set the chargePos here as well so we dont go off flying in some direction when we first spawn
		this.homePosition = this.chargePos = pos;
		this.maximumHomeDistance = distance;
	}

	@Override
	public BlockPos getRestrictCenter() {
		return this.homePosition;
	}

	@Override
	public float getRestrictRadius() {
		return this.maximumHomeDistance;
	}

	@Override
	public boolean hasRestriction() {
		this.maximumHomeDistance = -1.0F;
		return false;
	}

	public boolean hasHome() {
		return this.maximumHomeDistance != -1.0F;
	}
	// End copy
}
