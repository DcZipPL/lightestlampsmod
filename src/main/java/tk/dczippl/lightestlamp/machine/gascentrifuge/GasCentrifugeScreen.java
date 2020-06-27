package tk.dczippl.lightestlamp.machine.gascentrifuge;

import com.mojang.blaze3d.matrix.MatrixStack;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.util.network.Networking;
import tk.dczippl.lightestlamp.util.network.PacketButtonModeControl;
import tk.dczippl.lightestlamp.util.network.PacketButtonRedstone;

@SuppressWarnings("NullableProblems")
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

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.func_230446_a_(matrixStack);
        super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.field_230712_o_.func_238422_b_(matrixStack, this.field_230704_d_, (float)(this.xSize / 2 - this.field_230712_o_.getStringWidth(field_230704_d_.toString()) / 2), 6.0F, 4210752);
        this.field_230712_o_.func_238422_b_(matrixStack, this.playerInventory.getDisplayName(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);

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

        int marginHorizontal = (field_230708_k_ - xSize) / 2;
        int marginVertical = (field_230709_l_ - ySize) / 2;

        /*HoverChecker checker = new HoverChecker(marginVertical+9,marginVertical+20,marginHorizontal+9,marginHorizontal+20,0);
        if (checker.checkHover(mouseX,mouseY, true))
        {
            renderTooltip(redstone_tooltip,mouseX-marginHorizontal+4,mouseY-marginVertical+4);
        }
        checker = new HoverChecker(marginVertical+9,marginVertical+20,marginHorizontal+25,marginHorizontal+36,0);
        if (checker.checkHover(mouseX,mouseY, true))
        {
            renderTooltip(fluid_tooltip,mouseX-marginHorizontal+4,mouseY-marginVertical+4);
        }*/
        //renderHoveredToolTip(mouseX-marginHorizontal+4,mouseY-marginVertical+4);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.field_230706_i_.getTextureManager().bindTexture(this.texture);

        int x = (this.field_230708_k_ - this.xSize) / 2;
        int y = (this.field_230709_l_ - this.ySize) / 2;

        func_238463_a_(matrixStack, x, y, 0, 0, this.xSize, this.ySize, 256,256);

        int i = this.guiLeft;
        int j = this.guiTop;

        if (((GasCentrifugeContainer)this.container).func_217061_l()) {
            int k = ((GasCentrifugeContainer)this.container).getBurnLeftScaled()*2;
            if (k >= 300)
                k = 299;
            //Z Y T-Z T-Y W H
            this.func_238474_b_(matrixStack,i + 151, j + 65 + 12 - k, 203, 118 - k, 9, k + 1);
        }
        int m = ((GasCentrifugeContainer)this.container).getLiquidScaled();
        //Z Y T-Z T-Y W H
        this.func_238474_b_(matrixStack,i + 160, j + 65 + 13 - m, 212, 120 - m, 9, m + 1);

        int marginHorizontal = (field_230708_k_ - xSize) / 2;
        int marginVertical = (field_230709_l_ - ySize) / 2;
        switch (sc.field_217064_e.get(1))
        {
            case 0:
                this.func_238474_b_(matrixStack,marginHorizontal+9, marginVertical+9, 176, 128, 12, 12);
                break;
            case 1:
                this.func_238474_b_(matrixStack,marginHorizontal+9, marginVertical+9, 176, 141, 12, 12);
                break;
            case 2:
                this.func_238474_b_(matrixStack,marginHorizontal+9, marginVertical+9, 176, 154, 12, 12);
                break;
        }
        switch (sc.field_217064_e.get(4))
        {
            case 0:
                this.func_238474_b_(matrixStack,marginHorizontal+25, marginVertical+9, 192, 128, 12, 12);
                break;
            case 1:
                this.func_238474_b_(matrixStack,marginHorizontal+25, marginVertical+9, 192, 141, 12, 12);
                break;
            case 2:
                this.func_238474_b_(matrixStack,marginHorizontal+25, marginVertical+9, 192, 154, 12, 12);
                break;
        }

        int l = ((GasCentrifugeContainer)this.container).getCookProgressionScaled();
        this.func_238474_b_(matrixStack,i + 63, j + 34, 176, 14, l + 1, 16);
    }

    @Override
    public boolean func_231048_c_(double mouseX, double mouseY, int id) {
        int marginHorizontal = (field_230708_k_ - xSize) / 2;
        int marginVertical = (field_230709_l_ - ySize) / 2;
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
                Networking.INSTANCE.sendToServer(new PacketButtonRedstone(sc.getBlockPos(),0));
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
                Networking.INSTANCE.sendToServer(new PacketButtonModeControl(sc.getBlockPos(),0));
            }
        }

        return super.func_231048_c_(mouseX, mouseY, id); //Forge, Call parent to release buttons
    }
}