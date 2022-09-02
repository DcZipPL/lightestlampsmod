package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.prefex.lightestlamp.Reference;
import dev.prefex.lightestlamp.util.network.Networking;
import dev.prefex.lightestlamp.util.network.PacketButtonModeControl;
import dev.prefex.lightestlamp.util.network.PacketButtonRedstone;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@SuppressWarnings("NullableProblems")
@OnlyIn(Dist.CLIENT)
public class GasCentrifugeScreen extends AbstractContainerScreen<GasCentrifugeMenu>
{
    public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,"textures/gui/container/gas_centrifuge.png");
    private final GasCentrifugeMenu sc;

    public static final Logger LOGGER = LoggerFactory.getLogger(GasCentrifugeScreen.class);

    public GasCentrifugeScreen(GasCentrifugeMenu screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, titleIn);
        sc = screenContainer;
    }

    @Override
    public void render(PoseStack pPoseStack, int x, int y, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, x, y, pPartialTick);
        renderTooltip(pPoseStack,x,y);
        int tmp = 75;
        //this.font.draw(pPoseStack, this.title, (float)(this.getXSize() / 2 - tmp / 2), 6.0F, 4210752);
        //this.font.draw(pPoseStack, this.playerInventoryTitle, 8.0F, (float)(this.getYSize() - 96 + 2), 4210752);

        String redstone_tooltip = switch (sc.data.get(1)) {
            case 0 -> "Mode: Ignore Redstone";
            case 1 -> "Mode: Redstone off";
            case 2 -> "Mode: Redstone on";
            default -> "Mode: NULL";
        };
        String fluid_tooltip = switch (sc.data.get(4)) {
            case 0 -> "Mode: Neutralize Waste";
            case 1 -> "Mode: Store";
            case 2 -> "Mode: Dump (Starts dumping after 3 sec)";
            default -> "Mode: NULL";
        };

        int marginHorizontal = (width - getXSize()) / 2;
        int marginVertical = (height - getYSize()) / 2;

        //(marginHorizontal+9 <V>,marginHorizontal+20,marginVertical+9 <V>,marginVertical+20, 0 <V>)
        HoverChecker checker = new HoverChecker(marginHorizontal+9,marginHorizontal+20,marginVertical+20,marginVertical+9,0);
        if (checker.checkHover(x,y, true))
        {
            renderComponentTooltip(pPoseStack, Collections.singletonList(new TextComponent(redstone_tooltip)),x-marginHorizontal+4,y-marginVertical+4,font);
        }
        checker = new HoverChecker(marginHorizontal+25,marginHorizontal+36,marginVertical+20,marginVertical+9,0);
        if (checker.checkHover(x,y, true))
        {
            renderComponentTooltip(pPoseStack, Collections.singletonList(new TextComponent(fluid_tooltip)),x-marginHorizontal+4,y-marginVertical+4,font);
        }
        //renderHoveredToolTip(mouseX-marginHorizontal+4,mouseY-marginVertical+4);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);

        int x = (this.width - this.getXSize()) / 2;
        int y = (this.height - this.getYSize()) / 2;

        blit(pPoseStack, x, y, 0, 0, this.getXSize(), this.getYSize(), 256,256);

        int i = this.leftPos;
        int j = this.topPos;

        if (((GasCentrifugeMenu)this.menu).func_217061_l()) {
            int k = ((GasCentrifugeMenu)this.menu).getBurnLeftScaled()*2;
            if (k >= 300)
                k = 299;
            //Z Y T-Z T-Y W H
            this.blit(pPoseStack,i + 41 + 17 - k, j + 54, 194 - k, 100, k + 1,  5);
        }
        int m = ((GasCentrifugeMenu)this.menu).getLiquidScaled();
        //Z Y T-Z T-Y W H
        this.blit(pPoseStack,i + 160, j + 65 + 13 - m, 212, 120 - m, 9, m + 1);

        int marginHorizontal = (width - getXSize()) / 2;
        int marginVertical = (height - getYSize()) / 2;
        switch (sc.data.get(1)) {
            case 0 -> this.blit(pPoseStack, marginHorizontal + 9, marginVertical + 9, 176, 128, 12, 12);
            case 1 -> this.blit(pPoseStack, marginHorizontal + 9, marginVertical + 9, 176, 141, 12, 12);
            case 2 -> this.blit(pPoseStack, marginHorizontal + 9, marginVertical + 9, 176, 154, 12, 12);
        }
        switch (sc.data.get(4)) {
            case 0 -> this.blit(pPoseStack, marginHorizontal + 25, marginVertical + 9, 192, 128, 12, 12);
            case 1 -> this.blit(pPoseStack, marginHorizontal + 25, marginVertical + 9, 192, 141, 12, 12);
            case 2 -> this.blit(pPoseStack, marginHorizontal + 25, marginVertical + 9, 192, 154, 12, 12);
        }

        int l = ((GasCentrifugeMenu)this.menu).getCookProgressionScaled();
        this.blit(pPoseStack,i + 63, j + 34, 176, 14, l + 1, 16);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int id) {
        int marginHorizontal = (width - getXSize()) / 2;
        int marginVertical = (height - getYSize()) / 2;
        //Main.LOGGER.info("Clicked at: " + mouseX + ":" + mouseY + ":" + id + ", With margin: " + (mouseX - marginHorizontal) + ":" + (mouseY - marginVertical) + ":" + id);

        if (mouseX - marginHorizontal >= 9 && mouseX - marginHorizontal <= 20)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
                //Main.LOGGER.info("redstone button clicked!");
                if (sc.data.get(1) == 2)
                {
                    sc.data.set(1, 0);
                } else {
                    sc.data.set(1, sc.data.get(1)+1);
                }
                LOGGER.warn("Pos: "+sc.getBlockPos());
                Networking.INSTANCE.sendToServer(new PacketButtonRedstone(sc.getBlockPos(),0));
            }
        }
        if (mouseX - marginHorizontal >= 25 && mouseX - marginHorizontal <= 36)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
                //Main.LOGGER.info("redstone button clicked!");
                if (sc.data.get(4) == 2)
                {
                    sc.data.set(4, 0);
                } else {
                    sc.data.set(4, sc.data.get(4)+1);
                }
                LOGGER.warn("Pos: "+sc.getBlockPos());
                Networking.INSTANCE.sendToServer(new PacketButtonModeControl(sc.getBlockPos(),0));
            }
        }

        return super.mouseClicked(mouseX, mouseY, id); //Forge, Call parent to release buttons
    }

    public class HoverChecker{
        double buttonX0,buttonX1,buttonY0,buttonY1;

        public HoverChecker(double buttonX0, double buttonX1,double buttonY0, double buttonY1,int id){
            this.buttonX0 = buttonX0;
            this.buttonX1 = buttonX1;
            this.buttonY0 = buttonY0;
            this.buttonY1 = buttonY1;
        }

        public boolean checkHover(double mouseX, double mouseY,boolean simulate){
            return mouseX >= buttonX0 && mouseY >= buttonY1 && mouseX <= buttonX1 && mouseY <= buttonY0;
        }
        /*public boolean checkHoverLegacy(double mouseX, double mouseY, double buttonX0, double buttonX1,double buttonY0, double buttonY1){
            return mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ && mouseX < this.field_230690_l_ + this.field_230688_j_ && mouseY < this.field_230691_m_ + this.field_230689_k_;
        }*/
    }
}