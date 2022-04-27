package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.inventory.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<MenuType<MobClonerContainer>> MOB_CLONER = CONTAINERS.register("mob_cloner", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MobClonerContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<LootAttractorContainer>> ITEM_ATTRACTOR = CONTAINERS.register("item_attractor", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new LootAttractorContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<LootIncineratorContainer>> ITEM_INCINERATOR = CONTAINERS.register("item_incinerator", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new LootIncineratorContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<MobKillingPadContainer>> MOB_KILLING_PAD = CONTAINERS.register("mob_killing_pad", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();

        return new MobKillingPadContainer(windowId, pos, inv);
    }));

    public static final RegistryObject<MenuType<CropHarvesterContainer>> CROP_HARVESTER = CONTAINERS.register("crop_harvester", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new CropHarvesterContainer(windowId, pos, inv);
    }));

    //public static final RegistryObject<MenuType<BackpackContainer>> BACKPACK = CONTAINERS.register("backpack", () -> IForgeMenuType.create((windowId, inv, data) -> {
    //    Level level = inv.player.getCommandSenderWorld();
    //    return new BackpackContainer(windowId, inv);
    //}));

    public static final RegistryObject<MenuType<RedstoneTransmitterContainer>> REDSTONE_TRANSMITTER = CONTAINERS.register("redstone_transmitter", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new RedstoneTransmitterContainer(windowId, pos, inv);
    }));
}
