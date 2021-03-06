package andronomos.androtech.item;

import andronomos.androtech.registry.ModItems;
import andronomos.androtech.util.ChatUtil;
import andronomos.androtech.util.ItemStackUtil;
import andronomos.androtech.util.NBTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockGpsRecorder extends Item {
    public static final String TOOLTIP_BLOCK_GPS_MODULE = "tooltip.androtech.block_gps_module_location";
    public static final String TOOLTIP_BLOCK_GPS_MODULE_X = "tooltip.androtech.block_gps_module_x";
    public static final String TOOLTIP_BLOCK_GPS_MODULE_Y = "tooltip.androtech.block_gps_module_y";
    public static final String TOOLTIP_BLOCK_GPS_MODULE_Z = "tooltip.androtech.block_gps_module_z";
    public static final String BLOCK_GPS_MODULE_SAVED = "item.androtech.block_gps_module.saved";

    public BlockGpsRecorder(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        ItemStack held = player.getItemInHand(hand);

        int numHeld = held.getCount();

        if(numHeld == 1) {
            held = recordPos(held, pos);
        } else {
            recordPos(pos, player);
            held.shrink(1);
        }

        player.swing(hand);
        ChatUtil.sendStatusMessage(player, ChatUtil.createTranslation(BLOCK_GPS_MODULE_SAVED) + ChatUtil.blockPosToString(pos));
        return InteractionResult.SUCCESS;
    }

    private void recordPos(BlockPos pos, Player player) {
        ItemStack drop = new ItemStack(ModItems.BLOCK_GPS_RECORDER.get());
        NBTUtil.setBlockPos(drop, pos);
        if(!player.addItem(drop)) ItemStackUtil.drop(player.level, player.blockPosition(), drop);
    }

    private ItemStack recordPos(ItemStack stack, BlockPos pos) {
        NBTUtil.setBlockPos(stack, pos);
        return stack;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return ItemStackUtil.getBlockPos(stack) == null ? 64 : 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        BlockPos pos = ItemStackUtil.getBlockPos(stack);

        if(pos != null) {
            tooltip.add(new TextComponent(ChatUtil.createTranslation(TOOLTIP_BLOCK_GPS_MODULE)).withStyle(ChatFormatting.GRAY));
            String xCoord = String.format("%s%s", ChatUtil.createTranslation(TOOLTIP_BLOCK_GPS_MODULE_X), pos.getX());
            String yCoord = String.format("%s%s", ChatUtil.createTranslation(TOOLTIP_BLOCK_GPS_MODULE_Y), pos.getY());
            String zCoord = String.format("%s%s", ChatUtil.createTranslation(TOOLTIP_BLOCK_GPS_MODULE_Z), pos.getZ());
            String coords = String.format("%s %s %s", xCoord, yCoord, zCoord);
            tooltip.add(new TextComponent(coords).withStyle(ChatFormatting.BLUE));
        }
    }
}
