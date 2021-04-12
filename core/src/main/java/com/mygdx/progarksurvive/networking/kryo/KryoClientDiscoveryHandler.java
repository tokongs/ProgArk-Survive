package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;

import javax.inject.Singleton;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Singleton
public class KryoClientDiscoveryHandler implements ClientDiscoveryHandler {

    private Map<String, InetAddress> hosts = new HashMap<>();

    @Override
    public DatagramPacket onRequestNewDatagramPacket() {
        return new DatagramPacket(new byte[64], 64);
    }

    @Override
    public void onDiscoveredHost(DatagramPacket datagramPacket, com.esotericsoftware.kryo.Kryo kryo) {
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

    public void reset(){
        hosts.clear();
    }

    public Map<String, InetAddress> getHosts(){
        return hosts;
    }

    @Override
    public void onFinally() {
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof KryoClientDiscoveryHandler)) return false;

        return hosts.equals(((KryoClientDiscoveryHandler) o).hosts);
    }
}
