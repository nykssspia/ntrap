package com.example.ntrap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NTrapPlugin extends JavaPlugin {

    public List<Trap> traps = new ArrayList<>();

    @Override
    public void onEnable() {
        getLogger().info("NTrap Plugin enabled!");

        // PlaceholderAPI desteği varsa kaydet
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new NTrapPlaceholder(this).register();
            getLogger().info("PlaceholderAPI desteği etkinleştirildi.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("NTrap Plugin disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komutu sadece oyuncular kullanabilir.");
            return true;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("trap")) {
            if (args.length == 0) {
                sendTrapHelp(player);
                return true;
            }

            // Komut işlemleri (örnek)
            if (args[0].equalsIgnoreCase("oluştur")) {
                if (!player.hasPermission("ntrap.use")) {
                    player.sendMessage("§cBu komutu kullanmak için izniniz yok!");
                    return true;
                }
                Trap newTrap = new Trap(traps.size() + 1, player.getName());
                traps.add(newTrap);
                player.sendMessage("Trap oluşturuldu! ID: " + newTrap.getId());
                return true;
            }

            if (args[0].equalsIgnoreCase("bilgi")) {
                Trap trap = getTrapByPlayer(player);
                if (trap == null) {
                    player.sendMessage("Trap bulunamadı.");
                } else {
                    player.sendMessage("Trap ID: " + trap.getId());
                    player.sendMessage("Sahip: " + trap.getOwnerName());
                    player.sendMessage("Üyeler: " + String.join(", ", trap.getMembers()));
                    player.sendMessage("Kalan süre: " + trap.getRemainingTimeFormatted());
                }
                return true;
            }

            // Diğer komutlar için izin kontrolü yapıp buraya ekleyebilirsin...
        }

        return false;
    }

    private void sendTrapHelp(Player player) {
        player.sendMessage("§6----- Trap Komutları -----");

        if (player.hasPermission("ntrap.use")) {
            player.sendMessage("§e/trap oluştur §7- Yeni trap oluşturur");
            player.sendMessage("§e/trap bilgi §7- Sahip olduğun trap hakkında bilgi verir");
        }
        if (player.hasPermission("ntrap.ver")) {
            player.sendMessage("§e/trap ver §7- Başkasına trap verir");
        }
        if (player.hasPermission("ntrap.ekle")) {
            player.sendMessage("§e/trap ekle §7- Trap’e üye ekler");
        }
        if (player.hasPermission("ntrap.sil")) {
            player.sendMessage("§e/trap sil §7- Trap’i siler");
        }
        if (player.hasPermission("ntrap.uzat")) {
            player.sendMessage("§e/trap uzat §7- Trap süresini uzatır");
        }
        if (player.hasPermission("ntrap.zamanayarla")) {
            player.sendMessage("§e/trap zamanayarla §7- Trap süresini ayarlar");
        }
        if (player.hasPermission("ntrap.chunkekle")) {
            player.sendMessage("§e/trap chunkekle §7- Trap’e chunk ekler");
        }
        if (player.hasPermission("ntrap.chunksil")) {
            player.sendMessage("§e/trap chunksil §7- Trap’ten chunk siler");
        }
        if (player.hasPermission("ntrap.devret")) {
            player.sendMessage("§e/trap devret §7- Trap’i başka oyuncuya devreder");
        }
        if (player.hasPermission("ntrap.reload")) {
            player.sendMessage("§e/trap reload §7- Plugin’i yeniden yükler");
        }
    }

    public Trap getTrapByPlayer(Player player) {
        return traps.stream()
                .filter(t -> t.getOwnerName().equalsIgnoreCase(player.getName()) ||
                        t.getMembers().contains(player.getName()))
                .findFirst()
                .orElse(null);
    }

    public Trap getTrapById(int id) {
        return traps.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
