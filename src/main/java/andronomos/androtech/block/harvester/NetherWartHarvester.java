package andronomos.androtech.block.harvester;

import andronomos.androtech.util.ItemStackUtil;
import com.google.common.collect.Iterables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class NetherWartHarvester implements IHarvester {
	@Override
	public boolean tryHarvest(Block crop, BlockState cropState, ServerLevel level, BlockPos pos, LazyOptional<IItemHandler> itemHandler) {
		if(!(crop instanceof NetherWartBlock)) {
			return false;
		}

		if(!isReadyForHarvest((NetherWartBlock) crop, cropState)) {
			return false;
		}

		final List<ItemStack> drops = crop.getDrops(cropState, level, pos, null);
		level.destroyBlock(pos, false);
		level.setBlock(pos, Blocks.NETHER_WART.defaultBlockState(), 0);

		for (ItemStack drop : drops) {
			drop.shrink(1);

			ItemStack stack = ItemStackUtil.insertIntoContainer(drop, itemHandler);

			if (!stack.isEmpty()) {
				ItemStackUtil.drop(level, pos, stack);
			}
		}

		return true;
	}

	private boolean isReadyForHarvest(NetherWartBlock crop, BlockState cropState) {
		int maxAge = Iterables.getLast(crop.AGE.getPossibleValues());
		int age = cropState.getValue(crop.AGE);
		return age == maxAge;
	}
}
