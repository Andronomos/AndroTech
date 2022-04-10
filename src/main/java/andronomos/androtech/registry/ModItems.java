package andronomos.androtech.registry;

import andronomos.androtech.item.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final Item.Properties BASE_PROPERTIES = new Item.Properties().tab(andronomos.androtech.AndroTech.ANDROTECH_TAB);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, andronomos.androtech.AndroTech.MOD_ID);

    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", () -> new DebugStickItem(BASE_PROPERTIES));
    public static final RegistryObject<Item> BASIC_CHIP = register("basic_chip");
    public static final RegistryObject<Item> ADVANCED_CHIP = register("advanced_chip");
    public static final RegistryObject<Item> MOB_STORAGE_CELL = ITEMS.register("mob_storage_cell", () -> new MobStorageCellItem(BASE_PROPERTIES));
    public static final RegistryObject<Item> FAKE_SWORD = ITEMS.register("fake_sword", () -> new FakeSword());
    public static final RegistryObject<Item> PORTABLE_LOOT_ATTRACTOR = ITEMS.register("portable_loot_attractor", () -> new PortableLootAttractorItem(BASE_PROPERTIES));

    //private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block) {
    //    return ITEMS.register(name, () -> new BlockItem(block.get(), BASE_PROPERTIES));
    //}

    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(BASE_PROPERTIES));
    }
}
