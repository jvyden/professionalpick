package exposed.jvyden.professionalpick;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(
        modid = ProfessionalPick.MOD_ID,
        name = ProfessionalPick.MOD_NAME,
        version = ProfessionalPick.VERSION
)
public class ProfessionalPick {

    public static final String MOD_ID = BuildInfo.MAVEN_NAME;
    public static final String MOD_NAME = "The Professional's Pickaxe";
    public static final String VERSION = BuildInfo.VERSION;

    @Mod.Instance(MOD_ID)
    public static ProfessionalPick INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        ItemHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
