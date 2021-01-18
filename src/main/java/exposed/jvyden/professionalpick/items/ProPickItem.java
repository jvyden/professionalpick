package exposed.jvyden.professionalpick.items;

import exposed.jvyden.professionalpick.ProPickMode;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        final ItemStack itemStack = player.getHeldItem(hand);

        final RayTraceResult rayTraceResult = rayTrace(world, player, false);

        // intellij is wrong, this *does* return null
        if (rayTraceResult == null) { // player right clicked the air
            switchMode(itemStack);
            player.sendStatusMessage(new TextComponentString("Mode: " + mode.getPrettyString()), true);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    public ProPickMode getMode() {
        return mode;
    }

    public void removeEnchants(ItemStack item) {
        NBTTagCompound tagCompound = item.getTagCompound();
        if(tagCompound != null) {
            if(tagCompound.hasKey("ench", 9)) {
                NBTTagList enchCompound = tagCompound.getTagList("ench", 10);
                int i = 0;
                List<Integer> toBeRemoved = new ArrayList<>();
                for(NBTBase enchBase : enchCompound) {
                    NBTTagCompound enchTag = (NBTTagCompound) enchBase;
                    // Jank, i should just be able to cast to NBTTagCompound in the for loop itself
                    // like for((NBTTagCompound) NBTBase enchBase : enchCompound)
                    // or just for(NBTTagCompound enchBase : enchCompound)

                    short id = enchTag.getShort("id"); // Enchantment id
                    Enchantment enchantment = Enchantment.getEnchantmentByID(id);
                    switch(enchantment.getRegistryName().getPath()) {
                        case "fortune":
                        case "silk_touch": // If the enchantment id is fortune or silk_touch, add it to the removal queue
                            toBeRemoved.add(i);
                            break;
                    }

                    i++; // Iterate
                }
                Collections.reverse(toBeRemoved); // Reverse array to prevent fucking with indexes

                for(int r : toBeRemoved)
                    enchCompound.removeTag(r); // Removes all tags in removal queue
            }
        }


    }

    public void switchMode(ItemStack item) {
        int modeInt = mode.getID();
        modeInt++;
        if (modeInt > 2) {
            modeInt = 0;
        }
        mode = ProPickMode.valueOf(modeInt);

        Enchantment enchantment;

        removeEnchants(item);

        switch(mode) {
            case SILK:
                enchantment = Enchantment.getEnchantmentByLocation("silk_touch");
                item.addEnchantment(enchantment, 1);
                break;
            case NORMAL:

                break;
            case FORTUNE:
                enchantment = Enchantment.getEnchantmentByLocation("fortune");
                item.addEnchantment(enchantment, 3);
                break;
        }
    }
}
