import EventBus from '@/components/EventBus';

export default class TodoModalService {

    static subscribe(modalId, callback) {
        EventBus.bus.$on(modalId, callback);
    }

    static open(modalId) {
        EventBus.bus.$emit(modalId, { open: true });
    }

    static close(modalId) {
        EventBus.bus.$emit(modalId, { open: false });
    }

    static unsubscribe(modalId) {
        EventBus.bus.$off(modalId);
    }

}