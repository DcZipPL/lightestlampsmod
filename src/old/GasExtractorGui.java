package tk.dczippl.lightestlamp.machine.gasextractor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.HoverChecker;
import net.minecraftforge.fml.client.gui.GuiButtonClickConsumer;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModPacketHandler;

public class GasExtractorGui extends ContainerScreen
{
    private static final ResourceLocation COMPACTOR_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/container/gas_extractor.png");
    //private final InventoryPlayer inventoryPlayer;
    private final GasExtractorTileEntity tileEntity;

    /**
     * Instantiates a new gui compactor.
     *
     * @param parContainer the par container
     * @param parTe        the par inventory TileEntity
     */
    public GasExtractorGui(GasExtractorTileEntity parTe, GasExtractorContainer parContainer)
    {
        super(parContainer);
        // DEBUG
        System.out.println("GUI LabTable constructor");
        //inventoryPlayer = parTe;
        tileEntity = parTe;
    }

    @Override
    protected void initGui()
    {
        super.initGui();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int id)
    {
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        Main.LOGGER.info("Clicked at: " + mouseX + ":" + mouseY + ":" + id + ", With margin: " + (mouseX - marginHorizontal) + ":" + (mouseY - marginVertical) + ":" + id);

        if (mouseX - marginHorizontal >= 9 && mouseX - marginHorizontal <= 20)
        {
            if (mouseY - marginVertical >= 9 && mouseY - marginVertical <= 20)
            {
                Main.LOGGER.info("redstone button clicked!");
                if (tileEntity.getField(5) == 2)
                {
                    tileEntity.setField(5, 0);
                } else {
                    tileEntity.setField(5, tileEntity.getField(5)+1);
                }
            }
        }
        //ModPacketHandler.sendToServer(5);
        return super.mouseClicked(mouseX, mouseY, id);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     *
     * @param mouseX the mouse X
     * @param mouseY the mouse Y
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = "Gas Centrifuge";//tileEntity.getDisplayName().getFormattedText();
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        fontRenderer.drawString("Inventory"/*tileEntity.getDisplayName().getFormattedText()*/, 8, ySize - 96 + 2, 4210752);

        String redstone_tooltip = "(NULL)";
        switch (tileEntity.getField(5))
        {
            case 0:
                redstone_tooltip = "Ignore Redstone";
                break;
            case 1:
                redstone_tooltip = "Redstone off";
                break;
            case 2:
                redstone_tooltip = "Redstone on";
                break;
        }

        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        HoverChecker checker = new HoverChecker(marginVertical+9,marginVertical+20,marginHorizontal+9,marginHorizontal+20,0);
        if (checker.checkHover(mouseX,mouseY, true))
        {
            drawHoveringText(redstone_tooltip,mouseX-marginHorizontal+4,mouseY-marginVertical+4);
        }
        checker = new HoverChecker(marginVertical+9,marginVertical+20,marginHorizontal+25,marginHorizontal+36,0);
        if (checker.checkHover(mouseX,mouseY, true))
        {
            drawHoveringText("Neutralize Waste",mouseX-marginHorizontal+4,mouseY-marginVertical+4);
        }
        renderHoveredToolTip(mouseX-marginHorizontal+4,mouseY-marginVertical+4);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY.
     *
     * @param partialTicks the partial ticks
     * @param mouseX       the mouse X
     * @param mouseY       the mouse Y
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.renderBackground();
        //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(COMPACTOR_GUI_TEXTURES);
        int marginHorizontal = (width - xSize) / 2;
        int marginVertical = (height - ySize) / 2;
        re
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

            switch (tileEntity.getField(5))
            {
                case 0:
                    drawTexturedModalRect(marginHorizontal+9, marginVertical+9, 176, 128, 12, 12);
                    break;
                case 1:
                    drawTexturedModalRect(marginHorizontal+9, marginVertical+9, 176, 141, 12, 12);
                    break;
                case 2:
                    drawTexturedModalRect(marginHorizontal+9, marginVertical+9, 176, 154, 12, 12);
                    break;
            }

        // Draw progress indicator
        int progressLevel = getProgressLevel(24);
        drawTexturedModalRect(marginHorizontal + 73, marginVertical + 34, 176, 14, progressLevel + 1, 16);

        // Draw progress indicator
        int argonLevel = getArgonLevel(72);
        drawTexturedModalRect(marginHorizontal+142, marginVertical+7, 176, 49, 9, argonLevel+1);

        // Draw progress indicator
        int progressLevel_1 = getProgressLevel(72);
        drawTexturedModalRect(marginHorizontal+151, marginVertical+7, 185, 49, 9, progressLevel_1+1);

        // Draw progress indicator
        int kryptonLevel = getKryptonLevel(72);
        drawTexturedModalRect(marginHorizontal+160, marginVertical+7, 194, 49, 9, kryptonLevel+1);
    }

    private int getProgressLevel(int progressIndicatorPixelWidth)
    {
        int ticksCompactingItemSoFar = tileEntity.getField(2);
        int ticksPerItem = tileEntity.getField(3);
        return ticksPerItem != 0 && ticksCompactingItemSoFar != 0 ? ticksCompactingItemSoFar * progressIndicatorPixelWidth / ticksPerItem : 0;
    }

    private int getArgonLevel(int progressIndicatorPixelWidth)
    {
        int ticksResearchSoFar = tileEntity.getField(4);
        int ticksPerItem = 20;
        return ticksPerItem != 0 && ticksResearchSoFar != 0 ? ticksResearchSoFar * progressIndicatorPixelWidth / ticksPerItem : 0;
    }

    private int getKryptonLevel(int progressIndicatorPixelWidth)
    {
        int ticksResearchSoFar = tileEntity.getField(6);
        int ticksPerItem = 120;
        return ticksPerItem != 0 && ticksResearchSoFar != 0 ? ticksResearchSoFar * progressIndicatorPixelWidth / ticksPerItem : 0;
    }
}
