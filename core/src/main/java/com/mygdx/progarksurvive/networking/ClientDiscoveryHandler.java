package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryo.Kryo;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ClientDiscoveryHandler implements com.esotericsoftware.kryonet.ClientDiscoveryHandler {

    private Map<String, InetAddress> hosts = new HashMap<>();

    public DatagramPacket onRequestNewDatagramPacket() {
        return new DatagramPacket(new byte[64], 64);
    }

    public void onDiscoveredHost(DatagramPacket datagramPacket, Kryo kryo) {
        String gameSessionName = getGameSessionName(datagramPacket);
        if(!hosts.containsKey(gameSessionName)) {
            hosts.put(gameSessionName, datagramPacket.getAddress());
        }
    }

    private String getGameSessionName(DatagramPacket datagramPacket){
        List<Byte> bytes = new ArrayList<>();
        for(Byte b : datagramPacket.getData()) {
            if(b == 0){
                break;
            }
            bytes.add(b);
        }
        byte[] result = new byte[bytes.size()];
        for(int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }

        return new String(result, StandardCharsets.UTF_8);
    }

    public Map<String, InetAddress> getHosts(){
        return hosts;
    }

    public void onFinally() {
    }
}
