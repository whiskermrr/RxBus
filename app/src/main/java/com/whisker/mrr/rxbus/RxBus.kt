package com.whisker.mrr.rxbus

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

object RxBus {

    private val subjectMap = HashMap<String, PublishSubject<Any>>()
    private val subscriptionMap = HashMap<Any, CompositeDisposable>()

    private val stickySubjectMap = HashMap<String, BehaviorSubject<Any>>()
    private val stickySubscriptionMap = HashMap<Any, CompositeDisposable>()

    private val replaySubjectMap = HashMap<String, ReplaySubject<Any>>()
    private val replaySubscriptionMap = HashMap<Any, CompositeDisposable>()

    private fun getSubject(subjectKey: String): PublishSubject<Any> {
        var subject: PublishSubject<Any>? = subjectMap[subjectKey]
        if (subject == null) {
            subject = PublishSubject.create()
            subjectMap[subjectKey] = subject
        }

        return subject
    }

    private fun getSubscription(obj: Any) : CompositeDisposable {
        var subscription: CompositeDisposable? = subscriptionMap[obj]
        if (subscription == null) {
            subscription = CompositeDisposable()
            subscriptionMap[obj] = subscription
        }

        return subscription
    }

    fun subscribe(subject: String, lifecycle: Any, action: Consumer<Any>) {
        val disposable: Disposable = getSubject(subject).subscribe(action)
        getSubscription(lifecycle).add(disposable)
    }

    fun unsubscribe(lifecycle: Any) {
        val subscription: CompositeDisposable? = subscriptionMap.remove(lifecycle)
        subscription?.dispose()
    }

    fun publish(event: Any) {
        getSubject(event.javaClass.name).onNext(event)
    }

    private fun getStickySubject(subjectKey: String) : BehaviorSubject<Any> {
        var stickySubject: BehaviorSubject<Any>? = stickySubjectMap[subjectKey]
        if(stickySubject == null) {
            stickySubject = BehaviorSubject.create()
            stickySubjectMap[subjectKey] = stickySubject
        }

        return stickySubject
    }

    private fun getStickySubscription(obj: Any) : CompositeDisposable {
        var stickySubscription: CompositeDisposable? = stickySubscriptionMap[obj]
        if(stickySubscription == null) {
            stickySubscription = CompositeDisposable()
            stickySubscriptionMap[obj] = stickySubscription
        }

        return stickySubscription
    }

    fun subscribeSticky(subject: String, lifecycle: Any, action: Consumer<Any>) {
        val disposable: Disposable = getStickySubject(subject).subscribe(action)
        getStickySubscription(lifecycle).add(disposable)
    }

    fun unsubscribeSticky(lifecycle: Any) {
        val subscription: CompositeDisposable? = stickySubscriptionMap.remove(lifecycle)
        subscription?.dispose()
    }

    fun publishSticky(event: Any) {
        getStickySubject(event.javaClass.name).onNext(event)
    }

    private fun getReplaySubject(subjectKey: String) : ReplaySubject<Any> {
        var replaySubject: ReplaySubject<Any>? = replaySubjectMap[subjectKey]
        if(replaySubject == null) {
            replaySubject = ReplaySubject.create()
            replaySubjectMap[subjectKey] = replaySubject
        }

        return replaySubject
    }

    private fun getReplaySubscription(obj: Any) : CompositeDisposable {
        var replaySubscription: CompositeDisposable? = replaySubscriptionMap[obj]
        if(replaySubscription == null) {
            replaySubscription = CompositeDisposable()
            replaySubscriptionMap[obj] = replaySubscription
        }

        return replaySubscription
    }

    fun subscribeReplay(subject: String, lifecycle: Any, action: Consumer<Any>) {
        val disposable: Disposable = getReplaySubject(subject).subscribe(action)
        getReplaySubscription(lifecycle).add(disposable)
    }

    fun unsubscribeReplay(lifecycle: Any) {
        val subscription: CompositeDisposable? = replaySubscriptionMap.remove(lifecycle)
        subscription?.dispose()
    }

    fun publishReplay(event: Any) {
        getReplaySubject(event.javaClass.name).onNext(event)
    }
}