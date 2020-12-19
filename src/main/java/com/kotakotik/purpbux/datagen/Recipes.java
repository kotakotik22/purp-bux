package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.ModBlocks;
import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.Purpbux;
import com.kotakotik.purpbux.items.AbstractWallet;
import com.kotakotik.purpbux.items.Wallets;
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
//        register(
//                ShapedRecipeBuilder.shapedRecipe(ModBlocks.BUX_STATION.get())
//                        .patternLine("rhr")
//                        .patternLine("bed")
//                        .patternLine("sgi")
//                        .key('r', Items.REDSTONE)
//                        .key('h', Items.HOPPER)
//                        .key('b', Blocks.BREWING_STAND)
//                        .key('e', Blocks.EMERALD_BLOCK)
//                        .key('d', Blocks.DROPPER)
//                        .key('s', Blocks.SUGAR_CANE)
//                        .key('g', Blocks.REDSTONE_LAMP)
//                        .key('i', Tags.Items.GEMS_DIAMOND)
//                        .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE)),
//                consumer,
//                "bux_station"
//        );

        register(
                ShapedRecipeBuilder.shapedRecipe(ModBlocks.BUX_STATION.get())
                        .patternLine("aba")
                        .patternLine("cdc")
                        .patternLine("eee")
                        .key('a', Tags.Items.DUSTS_REDSTONE)
                        .key('b', Blocks.HOPPER)
                        .key('c', Blocks.DROPPER)
                        .key('d', Blocks.EMERALD_BLOCK)
                        .key('e', Tags.Items.GEMS_EMERALD)
                        .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE)),
                consumer,
                "bux_station"
        );

        register(
                ShapedRecipeBuilder.shapedRecipe(ModBlocks.EXP_EXTRACTOR.get())
                        .patternLine("bab")
                        .patternLine("ede")
                        .patternLine("fcf")
                        .key('a', Blocks.HOPPER)
                        .key('b', Items.GLASS_BOTTLE)
                        .key('c', Tags.Items.CHESTS)
                        .key('d', Blocks.REDSTONE_BLOCK)
                        .key('e', Blocks.DROPPER)
                        .key('f', Tags.Items.INGOTS_IRON)
                        .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE)),
                consumer,
                "exp_extractor"
        );

        register(
                ShapelessRecipeBuilder.shapelessRecipe(Blocks.CHEST)
                        .addIngredient(Ingredient.fromTag(Tags.Items.CHESTS))
                        .addCriterion("wood", hasItem(Tags.Items.CHESTS)),
                consumer,
                "chest_to_vanilla"
        );

        register(ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.BUX_PILE.get())
                        .addIngredient(ModItems.PURP_BUX.get(), 9)
                        .addCriterion("bux", InventoryChangeTrigger.Instance.forItems(ModItems.PURP_BUX.get())),
                consumer,
                "bux_pile");

        register(ShapelessRecipeBuilder.shapelessRecipe(ModItems.PURP_BUX.get(), 9)
                        .addIngredient(ModBlocks.BUX_PILE.get())
                        .addCriterion("bux", InventoryChangeTrigger.Instance.forItems(ModItems.PURP_BUX.get())),
                consumer,
                "bux"
        );

        register(
                ShapedRecipeBuilder.shapedRecipe(ModBlocks.SVELTEBUX_GRAVESTONE.get())
                        .patternLine("xox")
                        .patternLine(" i ")
                        .patternLine("kkk")
                        .key('x', Tags.Items.STONE)
                        .key('o', Items.ORANGE_DYE)
                        .key('i', ModItems.PURP_BUX.get())
                        .key('k', Items.STONE_SLAB)
                        .addCriterion("stone", hasItem(Tags.Items.STONE)),
                consumer,
                "sveltebux_gravestone"
        );

        for (Class<?> wallet1 : Wallets.wallets) {
            Class<AbstractWallet> walletClass = (Class<AbstractWallet>) wallet1;
            AbstractWallet wallet = null;
            try {
                wallet = walletClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            assert wallet != null;
            register(ShapedRecipeBuilder.shapedRecipe(wallet.getProvider(), 1)
                            .addCriterion("wallet_" + wallet.getWalletName() + "_criterion", InventoryChangeTrigger.Instance.forItems(wallet.getMaterial()))
                            .patternLine("x x")
                            .patternLine("xox")
                            .patternLine("xxx")
                            .key('x', wallet.getMaterial())
                            .key('o', wallet.getPreviousWallet() == null ? ModItems.PURP_BUX.get() : wallet.getPreviousWallet()),
                    consumer,
                    "wallet_" + wallet.getWalletName()
            );
        }
    }

    private void register(ShapelessRecipeBuilder recipeBuilder, Consumer<IFinishedRecipe> consumer, String name) {
        recipeBuilder.build(consumer, new ResourceLocation(Purpbux.MODID, name));
    }

    private void register(ShapedRecipeBuilder recipeBuilder, Consumer<IFinishedRecipe> consumer, String name) {
        recipeBuilder.build(consumer, new ResourceLocation(Purpbux.MODID, name));
    }
}
