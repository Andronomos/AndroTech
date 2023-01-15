package andronomos.androtech.data.client;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.machine.Machine;
import andronomos.androtech.block.pad.PadBlock;
import andronomos.androtech.block.pad.PadEffectBlock;
import andronomos.androtech.block.pad.RotatablePadBlock;
import andronomos.androtech.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, AndroTech.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(b -> {
            String blockName = ForgeRegistries.BLOCKS.getKey(b).getPath();

            if(b instanceof Machine machine) {
                if(machine.hasMultipleStates) {
                    registerMultiState(machine);
                } else {
                    registerSingleState(machine);
                }
            }

            if(b instanceof PadBlock) {
                registerPadStateAndModel(b, blockName + "_top", b instanceof RotatablePadBlock);
            }
        });

        //registerSingleState((Machine) ModBlocks.CROP_FARMER.get());
        //registerSingleState((Machine) ModBlocks.MOB_CLONER.get());
        //registerSingleState((Machine) ModBlocks.ITEM_INCINERATOR.get());
        //registerDirectionalState((Machine) ModBlocks.SHEEP_FARMER.get());
        //registerDirectionalState((Machine) ModBlocks.COW_FARMER.get());
        //registerMultiState((Machine) ModBlocks.ITEM_ATTRACTOR.get());
        //registerMultiState((Machine) ModBlocks.REDSTONE_TRANSMITTER.get());
        //registerMultiState((Machine) ModBlocks.REDSTONE_RECEIVER.get());
        //registerHologramBlockStateAndModel(ModBlocks.OVERLAY.get(), "overlay");
        //registerSimpleStateAndModel(ModBlocks.CREATIVE_ENERGY_GENERATOR.get(), "creative_energy_generator");
    }

    private void registerHologramBlockStateAndModel(Block block, String name) {
        ModelFile model;

        model = models().cubeAll(name, modLoc("block/" + name))
                .renderType("translucent");

        simpleBlock(block, model);
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), modLoc("block/" + name));
    }

    private void registerSimpleStateAndModel(Block block, String name) {
        simpleBlock(block);
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), modLoc("block/" + name));
    }

    private void registerSingleState(Machine machine) {
        String blockName = ForgeRegistries.BLOCKS.getKey(machine).getPath();

        String topTexture = String.format("block/%s", machine.textures.get("top"));
        String bottomTexture = String.format("block/%s", machine.textures.get("bottom"));
        String sideTexture = String.format("block/%s", machine.textures.get("side"));
        String frontTexture = String.format("block/%s", machine.textures.get("front"));

        ModelFile model = models().cube(ForgeRegistries.BLOCKS.getKey(machine).getPath(),
                modLoc(bottomTexture),
                modLoc(topTexture),
                modLoc(frontTexture),
                modLoc(sideTexture),
                modLoc(sideTexture),
                modLoc(sideTexture)).texture("particle", sideTexture);

        simpleBlock(machine, model);
        itemModels().withExistingParent(blockName, modLoc("block/" + blockName));
    }

    private void registerMultiState(Machine machine) {
        String machineName = ForgeRegistries.BLOCKS.getKey(machine).getPath();

        getVariantBuilder(machine).forAllStates(state -> {
            boolean isPowered = state.getValue(BlockStateProperties.POWERED);

            String topTexture = String.format("block/%s", machine.textures.get("top"));
            String bottomTexture = String.format("block/%s", machine.textures.get("bottom"));
            String sideTexture = String.format("block/%s", machine.textures.get("side"));

            if(isPowered) {
                if(machine.textures.containsKey("top_on")) {
                    topTexture = String.format("block/%s", machine.textures.get("top_on"));
                }

                if(machine.textures.containsKey("bottom_on")) {
                    bottomTexture = String.format("block/%s", machine.textures.get("bottom_on"));
                }

                if(machine.textures.containsKey("side_on")) {
                    sideTexture = String.format("block/%s", machine.textures.get("side_on"));
                }
            } else {
                if(machine.textures.containsKey("top_off")) {
                    topTexture = String.format("block/%s", machine.textures.get("top_off"));
                }

                if(machine.textures.containsKey("bottom_off")) {
                    bottomTexture = String.format("block/%s", machine.textures.get("bottom_off"));
                }

                if(machine.textures.containsKey("side_off")) {
                    sideTexture = String.format("block/%s", machine.textures.get("side_off"));
                }
            }

            ResourceLocation sideResource = modLoc(sideTexture);
            ModelFile model = models().cube(machineName + "_off", modLoc(bottomTexture), modLoc(topTexture), sideResource, sideResource, sideResource, sideResource).texture("particle", sideResource);

            if(isPowered) {
                model = models().cube(machineName + "_on", modLoc(bottomTexture), modLoc(topTexture), sideResource, sideResource, sideResource, sideResource).texture("particle", sideResource);
            }

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        itemModels().withExistingParent(machineName, modLoc("block/" + machineName + "_off"));
    }

    private void registerDirectionalState(Machine machine) {
        //String blockName = ForgeRegistries.BLOCKS.getKey(machine).getPath();
        //String topTexture = String.format("block/%s_top", machine.useDefaultTopTexture ? "machine" : blockName);
        //String bottomTexture = String.format("block/%s_bottom", machine.useDefaultBottomTexture ? "machine" : blockName);
        //String sideTexture = String.format("block/%s_side", machine.useDefaultSideTexture ? "machine" : blockName);
        //String frontTexture = String.format("block/%s_front", machine.useDefaultFrontTexture ? "machine" : blockName);
        //
        //ModelFile model = models().cube(ForgeRegistries.BLOCKS.getKey(machine).getPath(),
        //        modLoc(bottomTexture),
        //        modLoc(topTexture),
        //        modLoc(frontTexture),
        //        modLoc(sideTexture),
        //        modLoc(sideTexture),
        //        modLoc(sideTexture)).texture("particle", sideTexture);
        //
        //getVariantBuilder(machine).forAllStatesExcept(state -> {
        //    Direction direction = state.getValue(IDirectionalMachine.FACING);
        //
        //    int yRot = 0;
        //
        //    switch (direction) {
        //        case EAST:
        //            yRot = 90;
        //            break;
        //        case WEST:
        //            yRot = 270;
        //            break;
        //        case SOUTH:
        //            yRot = 180;
        //            break;
        //    }
        //
        //    return ConfiguredModel.builder()
        //            .modelFile(model)
        //            .rotationY(yRot)
        //            .build();
        //});
        //
        //
        //itemModels().withExistingParent(blockName, modLoc("block/" + blockName));
    }

    private void registerPadStateAndModel(Block block, String top, boolean isDirectional) {
        String blockName = ForgeRegistries.BLOCKS.getKey(block).getPath();

        ModelFile model = models().withExistingParent(blockName, modLoc("pad_base"))
                .texture("particle", modLoc("block/" + top))
                .texture("design", modLoc("block/" + top))
                .texture("pad", modLoc("block/machine_bottom"));

        if(isDirectional) {
            getVariantBuilder(block).forAllStatesExcept(state -> {
                Direction direction = state.getValue(PadEffectBlock.FACING);

                int yRot = 0;

                switch (direction) {
                    case EAST:
                        yRot = 90;
                        break;
                    case WEST:
                        yRot = 270;
                        break;
                    case SOUTH:
                        yRot = 180;
                        break;
                }

                return ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationY(yRot)
                        .build();
            });
        } else {
            simpleBlock(block, model);
        }

        itemModels().withExistingParent(blockName, modLoc("block/" + blockName));
    }
}