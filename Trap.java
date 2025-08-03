package com.example.ntrap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trap {

    private final int id;
    private final String ownerName;
    private final List<String> members;
    private long expireTimestamp;
    private final List<String> chunks;
    private final long createdTimestamp;

    public Trap(int id, String ownerName) {
        this.id = id;
        this.ownerName = ownerName;
        this.members = new ArrayList<>();
        this.chunks = new ArrayList<>();
        this.createdTimestamp = System.currentTimeMillis();
        this.expireTimestamp = System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000); // 30 gün
    }

    public int getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public List<String> getMembers() {
        return members;
    }

    public void addMember(String member) {
        if (!members.contains(member)) {
            members.add(member);
        }
    }

    public void removeMember(String member) {
        members.remove(member);
    }

    public boolean isActive() {
        return System.currentTimeMillis() < expireTimestamp;
    }

    public String getRemainingTimeFormatted() {
        long remaining = expireTimestamp - System.currentTimeMillis();
        if (remaining <= 0) return "Süresi Dolmuş";
        long seconds = remaining / 1000 % 60;
        long minutes = remaining / (1000 * 60) % 60;
        long hours = remaining / (1000 * 60 * 60) % 24;
        long days = remaining / (1000 * 60 * 60 * 24);
        return String.format("%d gün %02d:%02d:%02d", days, hours, minutes, seconds);
    }

    public int getChunkCount() {
        return chunks.size();
    }

    public void addChunk(String chunk) {
        if (!chunks.contains(chunk)) {
            chunks.add(chunk);
        }
    }

    public void removeChunk(String chunk) {
        chunks.remove(chunk);
    }

    public String getCreatedDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(createdTimestamp));
    }

    // expireTimestamp getter/setter
    public long getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(long expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }
}
