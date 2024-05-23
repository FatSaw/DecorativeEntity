package me.bomb.decorativeentity.packet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

public final class PacketPlayOutPlayerInfo extends Packet {

	public EnumPlayerInfoAction action;
	public HashMap<UUID, PlayerInfoData> playerinfodata;
	
	public PacketPlayOutPlayerInfo(EnumPlayerInfoAction action) {
		this(action, new HashMap<UUID, PlayerInfoData>());
	}

    public PacketPlayOutPlayerInfo(EnumPlayerInfoAction action, HashMap<UUID, PlayerInfoData> playerinfodata) {
    	super((byte) 46);
    	this.action = action;
    	this.playerinfodata = playerinfodata;
    }

    protected void write(PacketDataSerializer packetdataserializer) throws IOException {
    	super.write(packetdataserializer);
        packetdataserializer.writeEnum(this.action);
        packetdataserializer.writeNum(this.playerinfodata.size());
        Iterator<Entry<UUID, PlayerInfoData>> iterator = this.playerinfodata.entrySet().iterator();
        while (iterator.hasNext()) {
        	Entry<UUID, PlayerInfoData> entry = iterator.next();
        	UUID uuid = entry.getKey();
        	PlayerInfoData playerinfodata = entry.getValue();
            packetdataserializer.writeUUID(uuid);
        	switch (this.action) {
        	case ADD_PLAYER:
                packetdataserializer.writeString(playerinfodata.name);
                if(playerinfodata.skinvalue == null) {
                	packetdataserializer.writeNum(0);
                } else {
                	packetdataserializer.writeNum(1);
                	packetdataserializer.writeString("textures");
                    packetdataserializer.writeString(playerinfodata.skinvalue);
                    if(playerinfodata.skinsignature == null) {
                        packetdataserializer.writeBoolean(false);
                    } else {
                        packetdataserializer.writeBoolean(true);
                        packetdataserializer.writeString(playerinfodata.skinsignature);
                    }
                }
                packetdataserializer.writeNum(playerinfodata.gameMode.id);
                packetdataserializer.writeNum(playerinfodata.latency);
                if (playerinfodata.displayName == null) {
                    packetdataserializer.writeBoolean(false);
                } else {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.writeString(playerinfodata.displayName);
                }
                break;
        	case UPDATE_GAME_MODE:
                packetdataserializer.writeNum(playerinfodata.gameMode.id);
                break;

            case UPDATE_LATENCY:
                packetdataserializer.writeNum(playerinfodata.latency);
                break;

            case UPDATE_DISPLAY_NAME:
                if (playerinfodata.displayName == null) {
                    packetdataserializer.writeBoolean(false);
                } else {
                    packetdataserializer.writeBoolean(true);
                    packetdataserializer.writeString(playerinfodata.displayName);
                }
                break;

            case REMOVE_PLAYER:
                break;
        	}
        }
    }

    public static class PlayerInfoData {
    	
    	public int latency;
    	public EnumGamemode gameMode;
    	public String name, displayName, skinvalue, skinsignature;

        public PlayerInfoData(String name, @Nullable String skinvalue, @Nullable String skinsignature, int latency, EnumGamemode enumgamemode, @Nullable String displayName) {
            this.name = name;
            this.skinvalue = skinvalue;
            this.skinsignature = skinsignature;
            this.latency = latency;
            this.gameMode = enumgamemode;
            this.displayName = displayName;
        }
    }

    public static enum EnumPlayerInfoAction {
        ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER;
    }
}
