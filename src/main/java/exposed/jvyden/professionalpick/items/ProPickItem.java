package exposed.jvyden.professionalpick.items;

import exposed.jvyden.professionalpick.ProPickMode;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ProPickItem extends ItemPickaxe {
    ProPickMode mode = ProPickMode.NORMAL;
    public ProPickItem(String name) {
        super(ToolMaterial.DIAMOND);

        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        final ItemStack itemStack = player.getHeldItem(hand);

        final RayTraceResult rayTraceResult = rayTrace(world, player, false);

        // intellij is wrong, this *does* return null
        if(rayTraceResult == null) { // player right clicked the air
            switchMode();
            player.sendStatusMessage(new TextComponentString(mode.name()), true);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    public ProPickMode getMode() {
        return mode;
    }

    public void switchMode() {
        int modeInt = mode.getID();
        if(modeInt > 2) {
            modeInt = 0;
        } else {
            modeInt += 1;
        }
        mode = ProPickMode.valueOf(modeInt);
    }
}
