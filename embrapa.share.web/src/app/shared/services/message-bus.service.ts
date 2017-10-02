import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject, Observable, Subscription } from 'rxjs/Rx';

@Injectable()
export class MessageBusService {

    private broadcaster: Subject<string> = new BehaviorSubject<string>(null);
    private messagesMap: Map<string, object> = new Map<string, object>();

    constructor() { }

    store(key: string, value: object) {
        this.messagesMap.set(key, value);
        this.broadcaster.next(key);
    }

    consume(key: string): object {
        const message = this.messagesMap.get(key);
        this.messagesMap.delete(key);
        this.broadcaster.next();
        return message;
    }

    subscribe(onNext, onError, onSuccess): Subscription {
        return this.broadcaster.subscribe(onNext, onError, onSuccess);
    }
}
