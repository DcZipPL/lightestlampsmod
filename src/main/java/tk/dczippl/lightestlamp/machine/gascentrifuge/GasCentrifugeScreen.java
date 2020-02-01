package tk.dczippl.lightestlamp.machine.gascentrifuge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.HoverChecker;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.util.network.Networking;
import tk.dczippl.lightestlamp.util.network.PacketButtonModeControl;
import tk.dczippl.lightestlamp.util.network.PacketButtonRedstone;

@OnlyIn(Dist.CLIENT)
public class GasCentrifugeScreen extends ContainerScreen<GasCentrifugeContainer>
{
    public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,"textures/gui/container/gas_centrifuge.png");
    private boolean field_214090_m;
    private GasCentrifugeContainer sc;

    public GasCentrifugeScreen(GasCentrifugeContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
        sc = screenContainer;
    }

    public void init() {
        super.init();
        this.field_214090_m = this.width < 379;
    }

    public void tick() {
        super.tick();
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        if (this.field_214090_m) {
            this.drawGuiContainerBackgroundLayer(p_render_3_, p_render_1_, p_render_2_);
        } else {
            super.render(p_render_1_, p_render_2_, p_render_3_);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int id)
    {
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        //Main.LOGGER.info("Clicked at: " + mouseX + ":" + mouseY + ":" + id + ", With margin: " + (mouseX - marginHorizontal) + ":" + (mouseY - marginVertical) + ":" + id);

        if (mouseX - marginHorizontal >= 9 && mouseX - marginHorizontal <= 20)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
                //Main.LOGGER.info("redstone button clicked!");
                if (sc.field_217064_e.get(1) == 2)
                {
                    sc.field_217064_e.set(1, 0);
                } else {
                    sc.field_217064_e.set(1, sc.field_217064_e.get(1)+1);
                }
                Networking.INSTANCE.sendToServer(new PacketButtonRedstone(sc.getBlockPos(),sc.world.getDimension().getType().getId()));
            }
        }
        if (mouseX - marginHorizontal >= 25 && mouseX - marginHorizontal <= 36)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
                //Main.LOGGER.info("redstone button clicked!");
                if (sc.field_217064_e.get(4) == 2)
                {
                    sc.field_217064_e.set(4, 0);
                } else {
                    sc.field_217064_e.set(4, sc.field_217064_e.get(4)+1);
                }
                Networking.INSTANCE.sendToServer(new PacketButtonModeControl(sc.getBlockPos(),sc.world.getDimension().getType().getId()));
            }
        }
        //ModPacketHandler.sendToServer(5);
        return super.mouseClicked(mouseX, mouseY, id);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);

        String redstone_tooltip = "Mode: Ignore Redstone";
        switch (sc.field_217064_e.get(1))
        {
            case 0:
                redstone_tooltip = "Mode: Ignore Redstone";
                break;
            case 1:
                redstone_tooltip = "Mode: Redstone off";
                break;
            case 2:
                redstone_tooltip = "Mode: Redstone on";
                break;
        }
        String fluid_tooltip = "Mode: Neutralize Waste";
        switch (sc.field_217064_e.get(4))
        {
            case 0:
                fluid_tooltip = "Mode: Neutralize Waste";
                break;
            case 1:
                fluid_tooltip = "Mode: Store";
                break;
            case 2:
                fluid_tooltip = "Mode: Dump (Starts dumping after 3 sec)";
                break;
        }

        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        HoverChecker checker = new HoverChecker(marginVertical+9,marginVertical+20,marginHorizontal+9,marginHorizontal+20,0);
        if (checker.checkHover(mouseX,mouseY, true))
        {
            renderTooltip(redstone_tooltip,mouseX-marginHorizontal+4,mouseY-marginVertical+4);
        }
        checker = new HoverChecker(marginVertical+9,marginVertical+20,marginHorizontal+25,marginHorizontal+36,0);
        if (checker.checkHover(mouseX,mouseY, true))
        {
            renderTooltip(fluid_tooltip,mouseX-marginHorizontal+4,mouseY-marginVertical+4);
        }
        renderHoveredToolTip(mouseX-marginHorizontal+4,mouseY-marginVertical+4);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.texture);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        if (((GasCentrifugeContainer)this.container).func_217061_l()) {
            int k = ((GasCentrifugeContainer)this.container).getBurnLeftScaled()*4;
            //Z Y T-Z T-Y W H
            this.blit(i + 151, j + 65 + 12 - k, 203, 118 - k, 9, k + 1);
        }
        int m = ((GasCentrifugeContainer)this.container).getLiquidScaled();
        //Z Y T-Z T-Y W H
        this.blit(i + 160, j + 65 + 13 - m, 212, 120 - m, 9, m + 1);

        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        switch (sc.field_217064_e.get(1))
        {
            case 0:
                blit(marginHorizontal+9, marginVertical+9, 176, 128, 12, 12);
                break;
            case 1:
                blit(marginHorizontal+9, marginVertical+9, 176, 141, 12, 12);
                break;
            case 2:
                blit(marginHorizontal+9, marginVertical+9, 176, 154, 12, 12);
                break;
        }
        switch (sc.field_217064_e.get(4))
        {
            case 0:
                blit(marginHorizontal+25, marginVertical+9, 192, 128, 12, 12);
                break;
            case 1:
                blit(marginHorizontal+25, marginVertical+9, 192, 141, 12, 12);
                break;
            case 2:
                blit(marginHorizontal+25, marginVertical+9, 192, 154, 12, 12);
                break;
        }

        int l = ((GasCentrifugeContainer)this.container).getCookProgressionScaled();
        this.blit(i + 63, j + 34, 176, 14, l + 1, 16);
    }
}