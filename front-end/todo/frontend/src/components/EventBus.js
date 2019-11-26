import Vue from 'vue';

export default class EventBus {

    static _bus = undefined;

    static get bus() {
        if (!EventBus._bus) {
            EventBus._bus = new Vue();
        }
        return EventBus._bus;
    }

}