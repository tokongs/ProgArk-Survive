package com.mygdx.progarksurvive.networking.events;

import com.mygdx.progarksurvive.networking.NetworkedEntityInfo;

import java.util.ArrayList;
import java.util.List;

public class HostUpdateEvent extends HostNetworkEvent{
    public List<NetworkedEntityInfo> entities = new ArrayList<>();

    public HostUpdateEvent() {
    }

    public HostUpdateEvent(List<NetworkedEntityInfo> entities) {
        this.entities = entities;
    }
}
