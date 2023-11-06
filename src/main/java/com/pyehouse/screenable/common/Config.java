package com.pyehouse.screenable.common;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final Map<String, ScreenDef> screens = new HashMap<>();

    public static void resetScreens() {
        screens.clear();
    }

    public static ScreenDef getScreenDef(String screenDefId) {
        if (screenDefId == null) {
            return null;
        }
        ScreenDef retval = Config.screens.get(screenDefId);
        if (retval.isValid()) {
            return retval;
        } else {
            return null;
        }
    }

    public static void putScreenDef(ScreenDef screenDef) {
        if (screenDef == null) {
            throw new IllegalArgumentException("Cannot store null ScreenDef");
        }
        boolean replaced = Config.hasScreenDefId(screenDef.id);
        if (replaced) {
            Screenable.logWarn("Replacing ScreenDef id[%s]", screenDef.id);
        }
        Config.screens.put(screenDef.id, screenDef);
    }

    public static boolean hasScreenDefId(String screenDefId) {
        if (screenDefId == null) {
            return false;
        }
        return Config.screens.containsKey(screenDefId);
    }

    public static void forgetScreenDef(String screenDefId) {
        if (screenDefId != null) {
            Config.screens.remove(screenDefId);
        }
    }

    /*
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }
     */

    public static void initConfig() {
        /*
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        Config.loadConfig(Config.COMMON_SPEC, FMLPaths.CONFIGDIR.get().resolve(String.format("%s-common.toml", Screenable.MODID)).toString());
         */
    }

    /*
    public static class Common {
        public Common(ForgeConfigSpec.Builder builder) {
        }
    }

    private static void loadConfig(ForgeConfigSpec configSpec, String path) {
        final CommentedFileConfig file = CommentedFileConfig
                .builder(new File(path))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        configSpec.setConfig(file);
    }
     */
}
