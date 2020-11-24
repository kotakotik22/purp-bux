package com.kotakotik.purpbux.screens;

import com.kotakotik.purpbux.ClientStorage;
import com.kotakotik.purpbux.Purpbux;
import com.kotakotik.purpbux.containers.BuxStationContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BuxStationScreen extends ContainerScreen<BuxStationContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Purpbux.MODID, "textures/gui/bux_station_gui.png");

    public BuxStationScreen(BuxStationContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        int l = getCookProgressionScaled();
        this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
    }

    public int getCookProgressionScaled() {
//        int i = this.container.tileEntity.getTileData().getInt("progress");
//        int j = this.container.tileEntity.getTileData().getInt("totalProgress");
//        this.container.tileEntity.getTileData().
//        int i = BuxStationProgressUpdate.getProgress();
//        int j = BuxStationProgressUpdate.getTotalProgress();

//        ;
//        AbstractFurnaceContainer

        int i = ClientStorage.BuxStationProgress;
        int j = ClientStorage.BuxStationTotalProgress;

        System.out.println("i: "+i);
        System.out.println("j: "+j);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }
}
