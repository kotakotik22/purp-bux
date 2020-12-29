package com.kotakotik.purpbux.jei;

import com.kotakotik.purpbux.Purpbux;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class PurpbuxJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Purpbux.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new JeiPurpbuxCreateCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JeiExpBottleCreateCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        JeiPurpbuxCreateCategory.getCatalysts().forEach(c -> registration.addRecipeCatalyst(c, JeiPurpbuxCreateCategory.CATEGORY_UUID));
        JeiExpBottleCreateCategory.getCatalysts().forEach(c -> registration.addRecipeCatalyst(c, JeiExpBottleCreateCategory.CATEGORY_UUID));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(JeiPurpbuxCreateCategory.getRecipes(), JeiPurpbuxCreateCategory.CATEGORY_UUID);
        registration.addRecipes(JeiExpBottleCreateCategory.getRecipes(), JeiExpBottleCreateCategory.CATEGORY_UUID);
    }
}
