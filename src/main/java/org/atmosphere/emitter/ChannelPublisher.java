/**
 * Copyright 2015-2016 Yulplay.com
 */
package org.atmosphere.emitter;

import org.atmosphere.cpr.Universe;

public class ChannelPublisher {

    public final static void publisTo(String channel) {

        Universe.broadcasterFactory().get("/channel_1");

    }
}
