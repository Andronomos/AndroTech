package andronomos.androtech.block.mobcloner;

import andronomos.androtech.Const;
import andronomos.androtech.block.TickingMachineBlockEntity;
import andronomos.androtech.item.MobCloningModule;
import andronomos.androtech.registry.ModBlockEntities;
import andronomos.androtech.util.ItemStackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MobClonerBE extends TickingMachineBlockEntity {
	private double spin;
	private double oSpin;
	private int requiredPlayerRange = 64;
	private int spawnCount = 1;
	private int spawnRange = 4;

	public static final int CLONER_SLOTS = 9;

	public MobClonerBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.MOB_CLONER_BE.get(), pos, state);
		tickDelay = Const.TicksInSeconds.FIVE;
	}

	public boolean shouldActivate(Level level, BlockPos pos) {
		if (!this.isNearPlayer(this.level, pos)
				|| level.hasNeighborSignal(pos)
				|| !this.getBlockState().getValue(MobCloner.POWERED))
			return false;

		return true;
	}

	@Override
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(CLONER_SLOTS) {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}

			@Override
			protected void onContentsChanged(int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				if(stack.getItem() instanceof MobCloningModule) {
					if(ItemStackUtil.hasEntityTag(stack)) {
						return true;
					}
				}

				return false;
			}
		};
	}

	@Override
	public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!shouldActivate(level, pos)) return;

		double d0 = (double)pos.getX() + level.random.nextDouble();
		double d1 = (double)pos.getY() + level.random.nextDouble();
		double d2 = (double)pos.getZ() + level.random.nextDouble();
		level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		this.spin = (this.spin + (double)(1000.0F / (20 + 200.0F))) % 360.0D;
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BlockEntity mobCloner) {
		if(!shouldActivate(level, pos)) return;

		if(shouldTick()) {
			for(int slotIndex = 0; slotIndex < inventoryItems.getSlots(); slotIndex++) {
				ItemStack clonerModule = inventoryItems.getStackInSlot(slotIndex);

				if(clonerModule == null
						|| clonerModule.isEmpty()
						|| !(clonerModule.getItem() instanceof MobCloningModule)
						|| !ItemStackUtil.hasEntityTag(clonerModule)) continue;

				for(int i = 0; i < spawnCount; ++i) {
					Entity entity = ItemStackUtil.getEntity(clonerModule, this.level, true);
					entity.setSilent(true);
					entity.setDeltaMovement(0, entity.getDeltaMovement().y(), 0);
					entity.setUUID(Mth.createInsecureUUID());
					double d0 = (double)pos.getX() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)this.spawnRange;
					double d1 = pos.getY() - 1;
					double d2 = (double)pos.getZ() + (this.level.random.nextDouble() - this.level.random.nextDouble()) * (double)this.spawnRange;

					if(this.level.noCollision(entity.getType().getAABB(d0, d1, d2))) {
						int k = this.level.getEntitiesOfClass(entity.getClass(), (new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)).inflate(this.spawnRange)).size();
						if (k >= getMaxNearbyEntities()) return;
						entity.absMoveTo(d0, d1, d2, 0, 0);
						this.level.addFreshEntity(entity);
						if (entity instanceof Mob) ((Mob)entity).spawnAnim();
					}
				}
			}
		}
	}

	private int getMaxNearbyEntities() {
		return spawnCount * CLONER_SLOTS;
	}

	private boolean isNearPlayer(Level level, BlockPos pos) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		return level.hasNearbyAlivePlayer(x, y, z, (double)this.requiredPlayerRange);
	}
}
