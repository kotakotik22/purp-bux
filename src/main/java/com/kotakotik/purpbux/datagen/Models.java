package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.Purpbux;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Models extends ItemModelProvider {

    public Models(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Purpbux.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("bux_station", modLoc("block/bux_station"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}