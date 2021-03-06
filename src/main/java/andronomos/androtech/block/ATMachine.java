package andronomos.androtech.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public abstract class ATMachine extends Block {
	public boolean hasTopTexture;
	public boolean hasBottomTexture;
	public boolean hasSideTexture;
	public boolean hasFrontTexture;
	public boolean hasMultiStates;
	public boolean isDirectional;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public ATMachine(Properties properties) {
		this(properties, false, false, false, false, false, false);
	}

	public ATMachine(Properties properties, boolean hasTopTexture, boolean hasBottomTexture, boolean hasSideTexture, boolean hasFrontTexture, boolean hasMultiStates, boolean isDirectional) {
		super(properties);
		this.isDirectional = isDirectional;
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
		this.hasTopTexture = hasTopTexture;
		this.hasBottomTexture = hasBottomTexture;
		this.hasSideTexture = hasSideTexture;
		this.hasFrontTexture = hasFrontTexture;
		this.hasMultiStates = hasMultiStates;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.POWERED);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			level.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
				for(int i = 0; i < itemHandler.getSlots(); i++) {
					popResource(level, pos, itemHandler.getStackInSlot(i));
				}
			});
			level.updateNeighbourForOutputSignal(pos, this);
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}
}
