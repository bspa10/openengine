package org.openengine.games.minecraft;

import org.openengine.core.logging.LoggingSettings;
import org.openengine.enty.AbstractGame;

public class Minecraft extends AbstractGame {
    public Minecraft() {
        LoggingSettings.setTrace(true);
    }
}
