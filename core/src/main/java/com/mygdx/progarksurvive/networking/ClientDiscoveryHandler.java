package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryo.Kryo;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ClientDiscoveryHandler implements com.esotericsoftware.kryonet.ClientDiscoveryHandler {

    private Map<String, InetAddress> hosts = new HashMap<>();

    public DatagramPacket onRequestNewDatagramPacket() {
        return new DatagramPacket(new byte[64], 64);
    }

    public void onDiscoveredHost(DatagramPacket datagramPacket, Kryo kryo) {
        String gameSessionName = new String(datagramPacket.getData(), StandardCharsets.UTF_8);
        if(!hosts.containsKey(gameSessionName)) {
            hosts.put(gameSessionName, datagramPacket.getAddress());
        }
    }

    public Map<String, InetAddress> getHosts(){
        return hosts;
    }

    public void onFinally() {
    }
}
