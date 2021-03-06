package andronomos.androtech.data;

import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildCraftingRecipes(consumer);

        createChipRecipe(ModItems.BASIC_CHIP.get(), Items.GOLD_INGOT, consumer);
        createChipRecipe(ModItems.ADVANCED_CHIP.get(), Items.DIAMOND, consumer);
        createModuleRecipe(ModItems.MOB_CLONING_MODULE.get(), ModItems.BASIC_CHIP.get(), Items.GLASS_BOTTLE, consumer);
        createModuleRecipe(ModItems.PORTABLE_ITEM_ATTRACTOR.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, consumer);
        createModuleRecipe(ModItems.BLOCK_GPS_RECORDER.get(), ModItems.BASIC_CHIP.get(), Items.MAP, consumer);
        createModuleRecipe(ModItems.PORTABLE_ITEM_MENDER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, consumer);
        createMachineRecipe(ModBlocks.MOB_CLONER.get(), ModItems.ADVANCED_CHIP.get(), ModItems.MOB_CLONING_MODULE.get(), Items.SPAWNER, consumer);
        createMachineRecipe(ModBlocks.ITEM_ATTRACTOR.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, ModItems.PORTABLE_ITEM_ATTRACTOR.get(), consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_PICKAXE.get(), Items.NETHERITE_PICKAXE,  consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_AXE.get(), Items.NETHERITE_AXE, consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_SHOVEL.get(), Items.NETHERITE_SHOVEL, consumer);
        createNaniteToolRecipe(ModItems.NANITE_ENHANCED_SWORD.get(), Items.NETHERITE_SWORD, consumer);
        createMachineRecipe(ModBlocks.ITEM_MENDER.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, ModItems.PORTABLE_ITEM_MENDER.get(), consumer);
        createMachineRecipe(ModBlocks.REDSTONE_RECEIVER.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, Items.OBSERVER, consumer);
        createMachineRecipe(ModBlocks.REDSTONE_TRANSMITTER.get(), ModItems.BASIC_CHIP.get(), Items.ENDER_PEARL, Items.REPEATER, consumer);
        createMachineRecipe(ModBlocks.CROP_FARMER.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, Items.WATER_BUCKET, consumer);
        createMachineRecipe(ModBlocks.ANIMAL_FARMER.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, Items.SHEARS, consumer);
        createMachineRecipe(ModBlocks.AMETHYST_HARVESTER.get(), ModItems.ADVANCED_CHIP.get(), Items.CHEST, Items.AMETHYST_BLOCK, consumer);
        createMachineRecipe(ModBlocks.BLOCK_MINER.get(), ModItems.BASIC_CHIP.get(), Items.CHEST, Items.IRON_PICKAXE, consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ITEM_INCINERATOR.get())
                .define('1', Items.IRON_INGOT)
                .define('2', Items.GLASS)
                .define('3', Items.LAVA_BUCKET)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);

        createAdvancedPadRecipe(ModBlocks.MOB_KILLING_PAD.get(), Items.IRON_SWORD, consumer);
        createPadRecipe(ModBlocks.WEAK_ACCELERATION_PAD.get(), Items.SUGAR, consumer);
        createPadRecipe(ModBlocks.STRONG_ACCELERATION_PAD.get(), Items.RABBIT_FOOT, consumer);

        createModuleRecipe(ModItems.WATER_BREATHING_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.TURTLE_HELMET, consumer);
        createModuleRecipe(ModItems.SWIFTNESS_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.SUGAR, consumer);
        createModuleRecipe(ModItems.FIRE_RESISTANCE_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.BLAZE_POWDER, consumer);
        createModuleRecipe(ModItems.REGENERATION_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.GOLDEN_APPLE, consumer);
        createModuleRecipe(ModItems.NIGHT_VISION_EMITTER.get(), ModItems.ADVANCED_CHIP.get(), Items.NETHER_STAR, Items.GOLDEN_CARROT, consumer);

        createNullifierRecipe(ModItems.POISON_NULLIFIER.get(), Items.POISONOUS_POTATO, Items.MILK_BUCKET, consumer);
        createNullifierRecipe(ModItems.WITHER_NULLIFIER.get(), Items.WITHER_SKELETON_SKULL, Items.MILK_BUCKET, consumer);
    }

    private void createChipRecipe(Item chip, Item material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(chip, 4)
                .define('1', material)
                .define('2', Items.QUARTZ)
                .define('3', Tags.Items.DUSTS_REDSTONE)
                .define('4', Tags.Items.INGOTS_IRON)
                .pattern("343")
                .pattern("212")
                .pattern("343")
                .unlockedBy("has_item", has(material))
                .save(consumer);
    }

    private void createPadRecipe(Block outputBlock, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock, 4)
                .define('1', ModItems.BASIC_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .define('4', Items.LEATHER)
                .pattern("434")
                .pattern("212")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);
    }

    private void createAdvancedPadRecipe(Block outputBlock, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock, 4)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Tags.Items.INGOTS_IRON)
                .define('3', item)
                .define('4', Items.LEATHER)
                .pattern("434")
                .pattern("212")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);
    }

    private void createModuleRecipe(Item outputItem, Item chip, Item item, Consumer<FinishedRecipe> consumer) {
        createModuleRecipe(outputItem, chip, item, Items.AMETHYST_SHARD, consumer);
    }

    private void createModuleRecipe(Item outputItem, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', chip)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.AMETHYST_SHARD)
                .define('4', item)
                .define('5', item2)
                .pattern("212")
                .pattern("343")
                .pattern("252")
                .unlockedBy("has_item", has(Items.LAVA_BUCKET))
                .save(consumer);
    }

    private void createMachineRecipe(Block outputBlock, Item chip, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputBlock)
                .define('1', chip)
                .define('2', Items.IRON_INGOT)
                .define('3', Items.REDSTONE_BLOCK)
                .define('4', item)
                .define('5', item2)
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(chip))
                .save(consumer);
    }

    private void createNaniteToolRecipe(Item outputItem, Item item, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', ModItems.PORTABLE_ITEM_MENDER.get())
                .define('3', item)
                .pattern("121")
                .pattern("232")
                .pattern("121")
                .unlockedBy("has_item", has(item))
                .save(consumer);
    }

    private void createNullifierRecipe(Item outputItem, Item item, Item item2, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(outputItem)
                .define('1', ModItems.ADVANCED_CHIP.get())
                .define('2', Items.IRON_INGOT)
                .define('3', item)
                .define('4', item2)
                .define('5', Items.NETHER_STAR)
                .pattern("212")
                .pattern("353")
                .pattern("242")
                .unlockedBy("has_item", has(ModItems.BASIC_CHIP.get()))
                .save(consumer);
    }
}