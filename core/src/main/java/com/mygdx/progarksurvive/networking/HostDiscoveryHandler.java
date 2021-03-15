package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryonet.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class HostDiscoveryHandler implements com.esotericsoftware.kryonet.ServerDiscoveryHandler {

    public ByteBuffer gameSessionName;

    public HostDiscoveryHandler(String gameSessionName){
        this.gameSessionName = ByteBuffer.wrap(gameSessionName.getBytes(StandardCharsets.UTF_8));

    }

    @Override
    public boolean onDiscoverHost(DatagramChannel datagramChannel, InetSocketAddress fromAddress, Serialization serialization) throws IOException {
        datagramChannel.send(this.gameSessionName, fromAddress);
        return true;
    }
}
