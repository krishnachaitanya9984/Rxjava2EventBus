package com.android.rxjavaeventbus;


import com.krinyny.reactive.EventBus;

/**
 * Created by KCBandatmakuru on 08-09-2017.
 */

public class EventPublisher {

    public static void publishClickEvent(String text) {
        ClickEvent event = new ClickEvent();
        event.text = text;
        EventBus.publishEvent(event);

    }
}
