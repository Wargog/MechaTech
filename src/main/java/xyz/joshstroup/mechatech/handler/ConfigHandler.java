package xyz.joshstroup.mechatech.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static Configuration config;

    // Config default values


    public static void init(File file)
    {
        if (config == null)
        {
            config = new Configuration(file);
            loadConfiguration();
        }
    }

    public static void loadConfiguration()
    {
        // Load actual values into config


        if (config.hasChanged())
        {
            config.save();
        }
    }
}
