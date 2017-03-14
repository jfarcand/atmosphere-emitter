/*
 * Copyright 2017 Async-IO.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.emitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.handler.AtmosphereHandlerAdapter;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.atmosphere.util.SimpleBroadcaster;

import java.io.IOException;

@Singleton
@AtmosphereHandlerService(path = "/{channel}",
        interceptors = {
            AtmosphereResourceLifecycleInterceptor.class,
            TrackMessageSizeInterceptor.class,
            BroadcastOnPostAtmosphereInterceptor.class,
            SuspendTrackerInterceptor.class},
        broadcaster = SimpleBroadcaster.class)

public class ChannelEmitter extends AtmosphereHandlerAdapter {

    // Jackson JSON encoder/decoder
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {
        if (event.isSuspended()) {
            event.getResource().write(mapper.writeValueAsBytes(event.getMessage()));
        }
    }
}
