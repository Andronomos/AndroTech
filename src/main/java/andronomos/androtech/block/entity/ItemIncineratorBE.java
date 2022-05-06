package andronomos.androtech.block.entity;

import andronomos.androtech.block.entity.base.BaseContainerBlockEntity;
import andronomos.androtech.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemIncineratorBE extends BaseContainerBlockEntity {
    public ItemIncineratorBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_INCINERATOR.get(), pos, state);
    }

    @Override
    protected ItemStackHandler createItemHandler() {
        return new ItemStackHandler(1) {

            @Override
            public void setStackInSlot(int slot, @Nonnull ItemStack stack){
            }

            @Nonnull
            @Override
            public ItemStack getStackInSlot(int slot){
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate){
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }
}
