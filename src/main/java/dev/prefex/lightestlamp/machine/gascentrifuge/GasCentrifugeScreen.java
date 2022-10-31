package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.google.common.primitives.UnsignedInteger;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.prefex.lightestlamp.Config;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.util.network.Networking;
import dev.prefex.lightestlamp.util.network.PacketButtonModeControl;
import dev.prefex.lightestlamp.util.network.PacketButtonRedstone;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
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
            renderComponentTooltip(pPoseStack, Collections.singletonList(new TranslatableComponent(redstone_tooltip)),x+4,y+4,font);
        }
        if(Config.ENERGY_MODE.get() != Config.EnergyModes.passive_only) {
            checker = new HoverChecker(marginHorizontal + 25, marginHorizontal + 36, marginVertical + 20, marginVertical + 9, 0);
            if (checker.checkHover(x, y, true)) {
                renderComponentTooltip(pPoseStack, formatUTooltip(power_tooltip), x + 4, y + 4, font);
            }
        }
        // Energy bar
        checker = new HoverChecker(marginHorizontal+153,marginHorizontal+166,marginVertical+70,marginVertical+19,0);
        if (checker.checkHover(x,y, true))
        {
            float power_percent = convertToWatts()/convertMaxToWatts();
            renderComponentTooltip(pPoseStack, List.of(
                    new TranslatableComponent("tooltip.lightestlamp.machine.energy_stored"),
                    new TextComponent(getMenu().data.get(5)+"/"+(1600*GasCentrifugeBlockEntity.magic)+" FE"),
                    new TextComponent(convertToWatts()+"/"+convertMaxToWatts()+" W"),
                    new TextComponent(Math.round(power_percent*10000)/100+"%")
                            .withStyle(Style.EMPTY.withColor(power_percent < 0.5f ?
                                    blend(new Color(0xff0000),new Color(0xffff00),power_percent*2f).getRGB()
                                    : blend(new Color(0xffff00),new Color(0x00ff00),power_percent*2f-1f).getRGB()
                            ))
            ),x+4,y+4,font);
        }
    }

    private Color blend( Color c1, Color c2, float ratio) {
        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = c1.getRGB();
        int i2 = c2.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));

        return new Color( a << 24 | r << 16 | g << 8 | b );
    }

    private float convertToWatts() {
        return Math.round((getMenu().data.get(5) / 400f)*100f)/100f;
    }
    private float convertMaxToWatts() {
        return Math.round(((1600*GasCentrifugeBlockEntity.magic) / 400f)*100f)/100f;
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
        if (sc.data.get(4)!=0){
            int m = (int)((GasCentrifugeMenu)this.sc).getLiquidScaled();
            //Z Y T-Z T-Y W H
            this.blit(pPoseStack,i + 153, j + 19 + 50 - m + 1 - 3, 204, 99 - m - 1, 13, m + 1);
            if (sc.data.get(4)==2)
                this.blit(pPoseStack,i + 153, j + 63, 176, 94, 13, 5);
        } else {
            this.blit(pPoseStack,i + 153, j + 19 + 1 - 3, 218, 99 - 1 - 50, 14, 51);
        }

        int marginHorizontal = (width - getXSize()) / 2;
        int marginVertical = (height - getYSize()) / 2;
        switch (sc.data.get(1)) {
            case 0 -> this.blit(pPoseStack, marginHorizontal + 9, marginVertical + 9, 176, 128, 12, 12);
            case 1 -> this.blit(pPoseStack, marginHorizontal + 9, marginVertical + 9, 176, 141, 12, 12);
            case 2 -> this.blit(pPoseStack, marginHorizontal + 9, marginVertical + 9, 176, 154, 12, 12);
        }
        if(Config.ENERGY_MODE.get() != Config.EnergyModes.passive_only) {
            switch (sc.data.get(4)) {
                case 0 -> this.blit(pPoseStack, marginHorizontal + 25, marginVertical + 9, 192, 128, 12, 12);
                case 1 -> this.blit(pPoseStack, marginHorizontal + 25, marginVertical + 9, 192, 141, 12, 12);
                case 2 -> this.blit(pPoseStack, marginHorizontal + 25, marginVertical + 9, 192, 154, 12, 12);
            }
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
                Networking.INSTANCE.sendToServer(new PacketButtonRedstone(sc.getBlockPos(),0));
            }
        }
        // Clicked Power button
        if(Config.ENERGY_MODE.get() != Config.EnergyModes.passive_only) {
            if (mouseX - marginHorizontal >= 25 && mouseX - marginHorizontal <= 36) {
                if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20) {
                    if (sc.data.get(4) >= ((Config.ENERGY_MODE.get() == Config.EnergyModes.no_overclocking_with_passive
                            || Config.ENERGY_MODE.get() == Config.EnergyModes.no_overclocking)
                            ? (Config.ENERGY_MODE.get() == Config.EnergyModes.passive_only) ? 0 : 1 : 2)) {
                        sc.data.set(4, (Config.ENERGY_MODE.get() == Config.EnergyModes.energy_only
                                        || Config.ENERGY_MODE.get() == Config.EnergyModes.no_overclocking)
                                ? 1 : 0
                        );
                    } else {
                        sc.data.set(4, sc.data.get(4) + 1);
                    }
                    Networking.INSTANCE.sendToServer(new PacketButtonModeControl(sc.getBlockPos(), 0));
                }
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