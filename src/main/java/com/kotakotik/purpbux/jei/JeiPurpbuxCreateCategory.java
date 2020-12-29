package com.kotakotik.purpbux.jei;

import com.google.common.collect.Lists;
import com.kotakotik.purpbux.Config;
import com.kotakotik.purpbux.ModBlocks;
import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.Purpbux;
import com.kotakotik.purpbux.jei.recipes.purpbux.JeiPurpbuxCreateRecipe;
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
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class JeiPurpbuxCreateCategory implements IRecipeCategory<JeiPurpbuxCreateRecipe> {
    // kinda just copying what flux networks did

    private final IDrawable background;
    private final IDrawable icon;

    public static final ResourceLocation CATEGORY_UUID = new ResourceLocation(Purpbux.MODID, "purpbux_creation");

    private final IGuiHelper helper;

    public JeiPurpbuxCreateCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.BUX_STATION.get()));
        this.background = helper.createDrawable(GUI, 1, 1, 128, 80);
        this.helper = helper;
    }

    @Override
    public ResourceLocation getUid() {
        return CATEGORY_UUID;
    }

    @Override
    public Class<? extends JeiPurpbuxCreateRecipe> getRecipeClass() {
        return JeiPurpbuxCreateRecipe.class;
    }

    public static List<JeiPurpbuxCreateRecipe> getRecipes() {
        List<JeiPurpbuxCreateRecipe> recipes = new ArrayList<>();
        recipes.add(new JeiPurpbuxCreateRecipe(Items.PAPER, ModItems.EXP_BOTTLE.get()));
        return recipes;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("purpbux.jei.purpbux_create").getString();
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
    public void setIngredients(JeiPurpbuxCreateRecipe recipe, IIngredients iIngredients) {
        List<ItemStack> items = new ArrayList<>();

        items.add(new ItemStack(recipe.getItem1()));
        items.add(new ItemStack(recipe.getItem2()));

        iIngredients.setInputs(VanillaTypes.ITEM, items);

        iIngredients.setOutput(VanillaTypes.ITEM, new ItemStack(ModItems.PURP_BUX.get()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, JeiPurpbuxCreateRecipe jeiPurpbuxCreateRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        // i1 = x
        // i2 = y
        final int of = -1;
        final int of2 = 3;
        guiItemStacks.init(0, false, 26 + of, 11 + of);
        guiItemStacks.init(1, false, 26 + of, 51 + of);
        guiItemStacks.init(2, false, 83 + of2, 27 + of2);

        guiItemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));
        guiItemStacks.set(1, iIngredients.getInputs(VanillaTypes.ITEM).get(1));
        guiItemStacks.set(2, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    private final ResourceLocation GUI = new ResourceLocation(Purpbux.MODID, "textures/gui/jei/purpbux_creation.png");

    public static List<ItemStack> getCatalysts() {
        return Lists.newArrayList(new ItemStack(ModBlocks.BUX_STATION.get()));
    }

    @Override
    public void draw(JeiPurpbuxCreateRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        // text that tells you how many seconds are needed
        int reqSeconds = Config.BUX_STATION_SECONDS_REQUIRED.get();

        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        String help = new TranslationTextComponent("message.purpbux_creation_time", reqSeconds).getString();
        fontRenderer.drawString(matrixStack, help, 64 - fontRenderer.getStringWidth(help) / 2f, 70, 0xff404040);
    }
}
