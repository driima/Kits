package com.dragonphase.kits.permissions;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import com.dragonphase.kits.util.Message;
import com.dragonphase.kits.util.Message.MessageType;

public class Permissions {

    public static final String KITS_BASE = "kits";

    public static final String KITS_LIST = KITS_BASE + ".list";

    public static final String KITS_SPAWN = KITS_BASE + ".spawn";
    public static final String KITS_SPAWN_OTHERS = KITS_SPAWN + ".others";

    public static final String KITS_SIGN = KITS_BASE + ".sign";

    public static final String KITS_FLAGS = KITS_BASE + ".flags";

    public static final String KITS_NODELAY = KITS_BASE + ".nodelay";

    public static final String KITS_ADMIN = KITS_BASE + ".admin";

    public static boolean checkPermission(Player player, String permission) {
        if (!player.hasPermission(permission.toLowerCase())) {
            Message.showMessage(player, Message.show("", "You do not have permission to perform that action.", MessageType.WARNING), permission);
            return false;
        }
        return true;
    }

    public static boolean checkPermission(Player player, String permission, String... subPerms) {
        return checkPermission(player, permission + "." + StringUtils.join(subPerms, "."));
    }

    public static boolean hasPermission(Player player, String permission, String... subPerms) {
        return player.hasPermission((permission + "." + StringUtils.join(subPerms, ".")).toLowerCase());
    }
}
