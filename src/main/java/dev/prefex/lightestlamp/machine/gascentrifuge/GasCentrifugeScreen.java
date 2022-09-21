package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.util.network.Networking;
import dev.prefex.lightestlamp.util.network.PacketButtonModeControl;
import dev.prefex.lightestlamp.util.network.PacketButtonRedstone;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("NullableProblems")
@OnlyIn(Dist.CLIENT)
public class GasCentrifugeScreen extends AbstractContainerScreen<GasCentrifugeMenu>
{
    public static final ResourceLocation texture = new ResourceLocation(Util.MOD_ID,"textures/gui/container/gas_centrifuge.png");
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

        String redstone_tooltip = switch (sc.data.get(1)) {
            case 0 -> "tooltip.lightestlamp.machine.redstone_ignore";
            case 1 -> "tooltip.lightestlamp.machine.redstone_off";
            case 2 -> "tooltip.lightestlamp.machine.redstone_on";
            default -> "tooltip.lightestlamp.machine.redstone_na";
        };
        String power_tooltip = switch (sc.data.get(4)) {
            case 0 -> "tooltip.lightestlamp.machine.passive_mode";
            case 1 -> "tooltip.lightestlamp.machine.normal_mode";
            case 2 -> "tooltip.lightestlamp.machine.overclock_mode";
            default -> "tooltip.lightestlamp.machine.mode_na";
        };

        int marginHorizontal = (width - getXSize()) / 2;
        int marginVertical = (height - getYSize()) / 2;

        HoverChecker checker = new HoverChecker(marginHorizontal+9,marginHorizontal+20,marginVertical+20,marginVertical+9,0);
        if (checker.checkHover(x,y, true))
        {
            renderComponentTooltip(pPoseStack, Collections.singletonList(new TranslatableComponent(redstone_tooltip)),x-marginHorizontal+4,y-marginVertical+4,font);
        }
        checker = new HoverChecker(marginHorizontal+25,marginHorizontal+36,marginVertical+20,marginVertical+9,0);
        if (checker.checkHover(x,y, true))
        {
            renderComponentTooltip(pPoseStack, formatUTooltip(power_tooltip),x-marginHorizontal+4,y-marginVertical+4,font);
        }
    }

    private List<MutableComponent> formatUTooltip(String utooltip) {
        return Arrays.stream(I18n.get(utooltip).replace("Format error: ", "*").split("¬")).map(
                s -> new TextComponent(s).setStyle(Style.EMPTY.withColor(
                        s.contains("§r") ? ChatFormatting.WHITE
                                : s.contains("§7") ? ChatFormatting.GRAY
                                : s.contains("§c") ? ChatFormatting.RED
                                : s.contains("§a") ? ChatFormatting.GREEN
                                : ChatFormatting.WHITE
                ))
        ).toList();
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

        // Clicked Redstone button
        if (mouseX - marginHorizontal >= 9 && mouseX - marginHorizontal <= 20)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
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
        // Clicked Power button
        if (mouseX - marginHorizontal >= 25 && mouseX - marginHorizontal <= 36)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
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
    }
}