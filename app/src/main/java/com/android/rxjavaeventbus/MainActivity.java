package com.android.rxjavaeventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.krinyny.reactive.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private Button clickBtn;
    private CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickBtn = (Button) findViewById(R.id.button);
        compositeDisposable = new CompositeDisposable();
        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventPublisher.publishClickEvent("hello !!!");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Disposable clickDisposable = EventBus.getFlowable(ClickEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClickEvent>() {
                    @Override
                    public void accept(@NonNull ClickEvent event) throws Exception {
                        clickBtn.setText(event.text);
                    }
                });
        compositeDisposable.add(clickDisposable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
