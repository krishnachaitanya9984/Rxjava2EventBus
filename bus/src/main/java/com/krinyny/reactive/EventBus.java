package com.krinyny.reactive;

import android.util.Log;

import java.util.HashMap;

import io.reactivex.Flowable;

/**
 * Created by KCBandatmakuru on 07-09-2017.
 */

public class EventBus {

    public enum EventType {
        Behavior,
        Replay,
        Publish
    }

    private static HashMap<Class, HashMap<EventType, EventProcessor>> processorTypeMap = new HashMap<>();

    public static <T> Flowable<T> getFlowable(Class<T> eventClass, EventType type) {
        return getProcessorQueue(eventClass, type).toFlowable();
    }

    public static <T> Flowable<T> getFlowable(Class<T> eventClass) {
        return getFlowable(eventClass, EventType.Publish);
    }

    public static <R> void publishEvent(R event, EventType type) {
        EventProcessor<R> queue = (EventProcessor<R>) getProcessorQueue(event.getClass(), type);
        try {
            queue.process(event);
        } catch (Exception ex) {
            Log.e("EventBus",ex.getMessage());
        }
    }

    public static <R> void publishEvent(R event) {
        publishEvent(event, EventType.Publish);
    }

    private synchronized static <T> EventProcessor<T> getProcessorQueue(Class<T> event, EventType type) {
        EventProcessor<T> eventProcessor;
        HashMap<EventType, EventProcessor> eventProcessorMap;

        if(processorTypeMap.containsKey(event)) {
            eventProcessorMap = processorTypeMap.get(event);
            if(eventProcessorMap == null) {
                eventProcessorMap = new HashMap<>();
                processorTypeMap.put(event, eventProcessorMap);
            }
            if(eventProcessorMap.containsKey(type)) {
                eventProcessor = eventProcessorMap.get(type);
            } else {
                eventProcessor = new EventProcessor<>(type);
                eventProcessorMap.put(type, eventProcessor);
            }
        } else {
            eventProcessorMap = new HashMap<>();
            eventProcessor  = new EventProcessor<>(type);
            eventProcessorMap.put(type, eventProcessor);
            processorTypeMap.put(event, eventProcessorMap);
        }


        return eventProcessor;
    }


}
