package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.ModBlocks;
import com.kotakotik.purpbux.Purpbux;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        register(
                ShapedRecipeBuilder.shapedRecipe(ModBlocks.BUX_STATION.get())
                        .patternLine("rhr")
                        .patternLine("bed")
                        .patternLine("sgi")
                        .key('r', Items.REDSTONE)
                        .key('h', Items.HOPPER)
                        .key('b', Blocks.BREWING_STAND)
                        .key('e', Blocks.EMERALD_BLOCK)
                        .key('d', Blocks.DROPPER)
                        .key('s', Blocks.SUGAR_CANE)
                        .key('g', Blocks.REDSTONE_LAMP)
                        .key('i', Tags.Items.GEMS_DIAMOND)
                        .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE)),
                consumer,
                "bux_station"
        );

        register(
                ShapelessRecipeBuilder.shapelessRecipe(Blocks.CHEST)
                    .addIngredient(Ingredient.fromTag(Tags.Items.CHESTS))
                    .addCriterion("wood", hasItem(Tags.Items.CHESTS)),
                consumer,
                "chest_to_vanilla"
        );
    }

    private void register(ShapelessRecipeBuilder recipeBuilder, Consumer<IFinishedRecipe> consumer, String name) {
        recipeBuilder.build(consumer, new ResourceLocation(Purpbux.MODID, name));
    }

    private void register(ShapedRecipeBuilder recipeBuilder, Consumer<IFinishedRecipe> consumer, String name) {
        recipeBuilder.build(consumer, new ResourceLocation(Purpbux.MODID, name));
    }
}
