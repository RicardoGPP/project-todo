import EventBus from '@/components/EventBus';

export default class MessageService {

    static TYPES = {
        SUCCESS: 'success',
        ERROR: 'error',
        WARNING: 'warning'
    }

    static subscribe(id, callback) {
        EventBus.bus.$on(id, callback);
    }

    static success(id, message) {
        EventBus.bus.$emit(id, { message, type: MessageService.TYPES.SUCCESS});
    }

    static error(id, message) {
        EventBus.bus.$emit(id, { message, type: MessageService.TYPES.ERROR});
    }

    static warning(id, message) {
        EventBus.bus.$emit(id, { message, type: MessageService.TYPES.WARNING});
    }

    static unsubscribe(id) {
        EventBus.bus.$off(id);
    }

}