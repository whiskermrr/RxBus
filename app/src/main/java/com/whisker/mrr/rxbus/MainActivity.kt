package com.whisker.mrr.rxbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxBus.subscribe(ExampleEvent::class.java.name, this, Consumer {
            it as String // pretty save casting
            println(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unsubscribe(this)
    }
}