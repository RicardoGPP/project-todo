<template>
    <transition appear>
        <div 
            v-if="value" 
            class="message-container" 
            :class="type"
        >
            <i :class="`message-icon fa fa-${icon} fa-2x`" />
            {{this.value}}
        </div>
    </transition>
</template>

<script>

import TodoMessageService from './TodoMessageService';

export default {
    name: 'Message',
    data() {
        return {
            value: '',
            type: 'success'
        }
    },
    props: {
        id: {
            type: String,
            required: true
        }
    },
    computed: {
        icon() {
            switch (this.type) {
                case TodoMessageService.TYPES.SUCCESS:
                    return 'check';
                case TodoMessageService.TYPES.ERROR:
                    return 'bomb';
                case TodoMessageService.TYPES.WARNING:
                    return 'exclamation-triangle';
                default:
                    return 'check';
            }
        }
    },
    created() {
        TodoMessageService.subscribe(this.id, objMessage => {
            this.value = objMessage.message;
            this.type = objMessage.type;

            if (this.type === 'success') {
                setTimeout(() => {
                    this.value = '';
                }, 2000);
            }
        });
    },
    destroyed() {
        TodoMessageService.unsubscribe(this.id);
    }
}
</script>

<style scoped>
.message-container {
    padding: 12px;
    color: var(--white-color);
    font-size: 1.1rem;
    font-weight: bold;
    border-radius: 4px;

    display: flex;
    justify-content: flex-start;
    align-items: center;
}
.message-icon {
    margin-right: 15px;
}
.message-container.error {
    background: var(--message-error-color);
}
.message-container.success {
    background: var(--message-success-color);
}
.message-container.warning {
    background: var(--message-warning-color);
}

.v-enter-active,
.v-leave-active {
  transition: opacity .5s;
}
.v-enter,
.v-leave-to {
  opacity: 0;
}
</style>
