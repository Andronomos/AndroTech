package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ModLootTableProvider extends LootTableProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private final DataGenerator generator;

    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

     private void addTables() {
        lootTables.put(ModBlocks.MOB_CLONER.get(), createSimpleTable("mob_cloner", ModBlocks.MOB_CLONER.get()));
        lootTables.put(ModBlocks.ITEM_ATTRACTOR.get(), createSimpleTable("item_attractor", ModBlocks.ITEM_ATTRACTOR.get()));
        lootTables.put(ModBlocks.ITEM_INCINERATOR.get(), createSimpleTable("item_incinerator", ModBlocks.ITEM_INCINERATOR.get()));
        lootTables.put(ModBlocks.MOB_KILLING_PAD.get(), createSimpleTable("mob_killing_pad", ModBlocks.MOB_KILLING_PAD.get()));
        lootTables.put(ModBlocks.WEAK_ACCELERATION_PAD.get(), createSimpleTable("weak_acceleration_pad", ModBlocks.WEAK_ACCELERATION_PAD.get()));
        lootTables.put(ModBlocks.STRONG_ACCELERATION_PAD.get(), createSimpleTable("strong_acceleration_pad", ModBlocks.STRONG_ACCELERATION_PAD.get()));
        lootTables.put(ModBlocks.CROP_HARVESTER.get(), createSimpleTable("crop_harvester", ModBlocks.CROP_HARVESTER.get()));
        lootTables.put(ModBlocks.WIRELESS_LIGHT.get(), createSimpleTable("wireless_light", ModBlocks.WIRELESS_LIGHT.get()));
        lootTables.put(ModBlocks.REDSTONE_RECEIVER.get(), createSimpleTable("redstone_receiver", ModBlocks.REDSTONE_RECEIVER.get()));
        lootTables.put(ModBlocks.REDSTONE_TRANSMITTER.get(), createSimpleTable("redstone_transmitter", ModBlocks.REDSTONE_TRANSMITTER.get()));
    }

    protected LootTable.Builder createSimpleTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block));
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    public void run(HashCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    @Override
    public String getName() {
        return andronomos.androtech.AndroTech.MOD_ID + "_lootTables";
    }
 }
