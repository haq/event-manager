package me.ihaq.eventmanager.event;

import me.ihaq.eventmanager.EventManager;
import me.ihaq.eventmanager.event.data.EventData;
import me.ihaq.eventmanager.event.data.EventType;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Event {

    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public void call() {

        CopyOnWriteArrayList<EventData> dataList = EventManager.INSTANCE.get(getClass());

        if (dataList == null)
            return;

        dataList.forEach(data -> {

            try {
                data.getTarget().invoke(data.getSource(), this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        });

    }

    public EventType getType() {
        return type;
    }

}