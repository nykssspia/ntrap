package com.example.ntrap;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class NTrapPlaceholder extends PlaceholderExpansion {

    private final NTrapPlugin plugin;

    public NTrapPlaceholder(NTrapPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "ntrap"; // Kullanım: %ntrap_something%
    }

    @Override
    public String getAuthor() {
        return "SeninAdin";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        String idPart = null;
        String baseIdentifier = identifier;
        if (identifier.contains("_")) {
            String[] split = identifier.split("_");
            baseIdentifier = split[0];
            idPart = split[1];
        }

        Trap trap = null;
        if (idPart != null) {
            try {
                int trapId = Integer.parseInt(idPart);
                trap = plugin.getTrapById(trapId);
            } catch (NumberFormatException e) {
                return "";
            }
        } else {
            trap = plugin.getTrapByPlayer(player);
        }

        if (trap == null) {
            return "Yok";
        }

        switch (baseIdentifier) {
            case "members":
                return String.join(", ", trap.getMembers());
            case "remaining":
                return trap.getRemainingTimeFormatted();
            case "owner":
                return trap.getOwnerName();
            case "status":
                return trap.isActive() ? "Aktif" : "Süresi Dolmuş";
            case "chunks":
                return String.valueOf(trap.getChunkCount());
            case "membercount":
                return String.valueOf(trap.getMembers().size());
            case "created":
                return trap.getCreatedDateFormatted();
            default:
                return "";
        }
    }
}
``
