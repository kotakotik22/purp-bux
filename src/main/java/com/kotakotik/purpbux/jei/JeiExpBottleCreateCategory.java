package com.kotakotik.purpbux.jei;

import com.google.common.collect.Lists;
import com.kotakotik.purpbux.Config;
import com.kotakotik.purpbux.ModBlocks;
import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.Purpbux;
import com.kotakotik.purpbux.jei.recipes.purpbux.JeiExpBottleCreateRecipe;
import com.kotakotik.purpbux.translation.TranslationKeys;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class JeiExpBottleCreateCategory implements IRecipeCategory<JeiExpBottleCreateRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public static final ResourceLocation CATEGORY_UUID = new ResourceLocation(Purpbux.MODID, "exp_bottle_creation");

    private final IGuiHelper helper;

    public JeiExpBottleCreateCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.EXP_EXTRACTOR.get()));
        this.background = helper.createDrawable(GUI, 1, 1, 128, 80);
        this.helper = helper;
    }

    @Override
    public ResourceLocation getUid() {
        return CATEGORY_UUID;
    }

    @Override
    public Class<? extends JeiExpBottleCreateRecipe> getRecipeClass() {
        return JeiExpBottleCreateRecipe.class;
    }

    public static List<JeiExpBottleCreateRecipe> getRecipes() {
        List<JeiExpBottleCreateRecipe> recipes = new ArrayList<>();
        recipes.add(new JeiExpBottleCreateRecipe(Items.GLASS_BOTTLE));
        return recipes;
    }

    @Override
    public String getTitle() {
        return TranslationKeys.JEI_EXP_BOTTLE_CREATION.translate();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(JeiExpBottleCreateRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, new ItemStack(recipe.getItem()));

        iIngredients.setOutput(VanillaTypes.ITEM, new ItemStack(ModItems.EXP_BOTTLE.get()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, JeiExpBottleCreateRecipe jeiExpBottleCreateRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        // i1 = x
        // i2 = y
        final int of = -1;
        final int of2 = 3;
        guiItemStacks.init(0, false, 26 + of, 11 + of);
        guiItemStacks.init(1, false, 83 + of2, 27 + of2);

        guiItemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
        guiItemStacks.set(1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
//        guiItemStacks.set(2, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    private final ResourceLocation GUI = new ResourceLocation(Purpbux.MODID, "textures/gui/jei/exp_bottle_creation.png");

    public static List<ItemStack> getCatalysts() {
        return Lists.newArrayList(new ItemStack(ModBlocks.EXP_EXTRACTOR.get()));
    }

    @Override
    public void draw(JeiExpBottleCreateRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        // text that tells you how much exp is needed
        int reqExp = Config.EXP_EXTRACTOR_EXP_REQUIRED.get();

        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        String help = TranslationKeys.JEI_EXP_BOTTLE_EXP_REQUIRED.translate(String.valueOf(reqExp));
        fontRenderer.drawString(matrixStack, help, 64 - fontRenderer.getStringWidth(help) / 2f, 70, 0xff404040);
    }
}
