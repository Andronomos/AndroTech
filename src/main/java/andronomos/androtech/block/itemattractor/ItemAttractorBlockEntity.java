package andronomos.androtech.block.itemattractor;

import andronomos.androtech.block.BaseBlockEntity;
import andronomos.androtech.block.damagepad.DamagePadBlock;
import andronomos.androtech.Constants;
import andronomos.androtech.registry.BlockEntityRegistry;
import andronomos.androtech.util.InventoryHelper;
import andronomos.androtech.util.RadiusHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemAttractorBlockEntity extends BaseBlockEntity implements MenuProvider {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public ItemAttractorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.ITEM_ATTRACTOR_BE.get(), pos, state, new SimpleContainerData(DamagePadBlock.PAD_SLOTS));
	}

	@Nonnull
	protected ItemStackHandler createInventoryItemHandler() {
		return new ItemStackHandler(Constants.VANILLA_INVENTORY_SLOT_COUNT) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if(!level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public AABB getWorkArea() {
		return RadiusHelper.nineByNineByNineBoxFromCenter(getBlockPos());
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(ItemAttractorBlock.DISPLAY_NAME);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory pPlayerInventory, Player pPlayer) {
		return new ItemAttractorMenu(containerId, pPlayerInventory, this, this.data);
	}

	@Override
	public void serverTick(ServerLevel level, BlockPos pos, BlockState state, BaseBlockEntity entity) {
		if(!state.getValue(POWERED)) return;

		if (level.getGameTime() % 10 == 0 && level.getBlockState(getBlockPos()).getBlock() instanceof ItemAttractorBlock) {
			setChanged(level, pos, state);

			if(!InventoryHelper.isFull(itemHandler)) {
				captureDroppedItems();
			}

			deleteCapturedXp();
		}
	}

	private void captureDroppedItems() {
		for(ItemEntity item : getCapturedItems()) {
			if(item == null)
				return;

			ItemStack stack = InventoryHelper.insert(item.getItem().copy(), itemHandler, false);

			if (!stack.isEmpty()) {
				item.setItem(stack);
			} else {
				item.remove(Entity.RemovalReason.KILLED);
			}
		}
	}

	private List<ItemEntity> getCapturedItems() {
		return getLevel().getEntitiesOfClass(ItemEntity.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private List<ExperienceOrb> getCapturedXP() {
		return getLevel().getEntitiesOfClass(ExperienceOrb.class, getWorkArea(), EntitySelector.ENTITY_STILL_ALIVE);
	}

	private void deleteCapturedXp() {
		for(ExperienceOrb orb : getCapturedXP()) {
			orb.remove(Entity.RemovalReason.KILLED);
		}
	}
}
