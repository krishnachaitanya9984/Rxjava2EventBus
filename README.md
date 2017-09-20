# Rxjava2EventBus

## Library for Event bus - implemented using RxJava2 in Android

### Usage of the library :
* Step 1 : 
Add following code in build.gradle (By default jcenter() is present in repositories)

```sh
repositories {
    jcenter()
}
```

* Step 2 :
Add the library in the dependencies section(build.gradle)
```sh
compile 'com.krinyny.reactive:rxjava2-eventbus:1.0.1'
```

>As EventBus mechanism follows pub-sub pattern,there should be event publisher and subscriber for the event.

Following is the way to publish an event using this event bus.
```sh
EventBus.publishEvent(event);
```
"event" is of any class type(where data resides) so that subscriber 
can consume that event and perform action on top of it.

There are three modes of sending event. 
>Publish,Behaviour & Replay.

By Default, the event sent is of type "Publish".
Different events can be sent in the following way
 
 ```sh
EventBus.publishEvent(event,EventBus.EventType.Behavior);
```

Following describes how to subscribe and consume an event:

```sh
EventBus.getFlowable(Event.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Event>() {
                    @Override
                    public void accept(@NonNull Event event) throws Exception {
                        //Do something with "event" object
                    }
                });
```
Different event processor can be defined as follows
```sh
EventBus.getFlowable(Event.class, EventBus.EventType.Behavior);
EventBus.getFlowable(Event.class, EventBus.EventType.Replay);
```

We can use different operators like map,flatmap,zip etc., while subscribing for an event.

We can also mention the thread on which it has to run & take action using subscribeOn & ObserveOn

In RxJava2 , to avoid back pressure Flowable is used.So using Flowable Processor we can send events.

Don't forget to dispose the Disposable after its usage 
```sh
Disposable testDisposable = EventBus.getFlowable(Event.class).subscribe();

> use CompositeDisposible or dispose() method to dispose it.
```

To see it in action checkout the project and run the app.

Well, there is always room for improvements.Inbox me for suggestions @ krishnachaitanya9984@gmail.com
