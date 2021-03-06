/**
 *   Wormhole X-Treme Plugin for Bukkit
 *   Copyright (C) 2011  Ben Echols
 *                       Dean Bailey
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.wormhole_xtreme.wormhole.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.wormhole_xtreme.wormhole.config.ConfigManager;
import com.wormhole_xtreme.wormhole.model.Stargate;
import com.wormhole_xtreme.wormhole.model.StargateManager;
import com.wormhole_xtreme.wormhole.permissions.WXPermissions;
import com.wormhole_xtreme.wormhole.permissions.WXPermissions.PermissionType;

/**
 * The Class Compass.
 * 
 * @author alron
 */
public class Compass implements CommandExecutor
{

    /**
     * Do compass.
     * 
     * @param player
     *            the player
     * @return true, if successful
     */
    private static boolean doCompass(final Player player)
    {
        final Player p = player;
        if (WXPermissions.checkWXPermissions(p, PermissionType.COMPASS))
        {
            final Stargate closest = StargateManager.findClosestStargate(p.getLocation());
            if (closest != null)
            {
                p.setCompassTarget(closest.getGatePlayerTeleportLocation());
                p.sendMessage(ConfigManager.MessageStrings.normalHeader.toString() + "Compass set to wormhole: " + closest.getGateName());
            }
            else
            {
                p.sendMessage(ConfigManager.MessageStrings.errorHeader.toString() + "No wormholes to track!");
            }
        }
        else
        {
            p.sendMessage(ConfigManager.MessageStrings.permissionNo.toString());
        }
        return true;
    }

    /* (non-Javadoc)
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        if (CommandUtilities.playerCheck(sender))
        {
            final Player player = (Player) sender;
            return doCompass(player);
        }
        return true;
    }

}
