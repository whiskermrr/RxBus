# RxBus
[![Packagist](https://img.shields.io/packagist/l/doctrine/orm.svg)](https://github.com/whiskermrr/rxandroid-fortnite-api/blob/master/LICENSE)

## Usage

Just copy content of RxBus.kt to your project. Bear in mind that you need rxAndroid to use this bus.

### How to subscribe

```kotlin
  RxBus.subscribe(ExampleEvent::class.java.name, this, Consumer {
            it as ExampleEvent // pretty save casting
            println(it.name)
        })
```

### How to unsubscribe

```kotlin
  RxBus.unsubscribe(this)
```
