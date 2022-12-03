package surreal.ticon.config;

import net.minecraftforge.common.config.Config;
import surreal.ticon.TinkersConstruct;

//TODO better config system
@Config(modid = TinkersConstruct.MODID)
public class FillerConfig {
    @Config.Comment("Tank size")
    public static int tankSize = 4000;

    @Config.Comment("If tank should retain liquid when broken")
    public static boolean tankRetain = true;
}
