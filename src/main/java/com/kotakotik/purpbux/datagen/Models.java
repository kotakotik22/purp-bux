package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.Purpbux;
import com.kotakotik.purpbux.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class Models extends ItemModelProvider {

    public Models(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Purpbux.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(ModItems.EXP_BOTTLE.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(Purpbux.MODID, "item/exp_bottle"));

        for (RegistryObject<Block> item : Registration.BLOCKS.getEntries()) {
            withExistingParent(item.get().getRegistryName().getPath(), item.getId().toString().replaceFirst(":", ":block/"));
        }
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
    }
}