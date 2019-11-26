<template>
    <transition appear>
        <TodoOverlay v-if="open">
            <div class="todo-modal">
                <div class="todo-modal_container">
                    <div class="todo-modal_container-header" v-if="hasHeaderSlot">
                        <slot name="header" />
                    </div>

                    <div class="todo-modal_container-content" v-if="hasContentSlot">
                        <slot name="content" />
                    </div>

                    <div class="todo-modal_container-footer" v-if="hasFooterSlot">
                        <slot name="footer" />
                    </div>                
                </div>
            </div>
        </TodoOverlay>
    </transition>
</template>

<script>
import TodoOverlay from '@/components/todo-overlay/TodoOverlay';
import TodoModalService from '@/components/todo-modal/TodoModalService';

export default {
    name: 'TodoModal',
    components: { TodoOverlay },
    props: {
        id: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            open: false
        }
    },
    computed: {
        hasHeaderSlot () {
            return this.$slots['header']
        },
        hasContentSlot () {
            return this.$slots['content']
        },
        hasFooterSlot () {
            return this.$slots['footer']
        }        
    },
    created() {
        TodoModalService.subscribe(this.id, objModal => {
            this.open = objModal.open;
        });
    },
    destroyed() {
        TodoModalService.unsubscribe(this.id);
    }
}
</script>

<style>
.todo-modal {
    width: 100vw;
    height: 100vh;
    background: transparent;

    display: flex;
    justify-content: center;
    align-items: center;
}
.todo-modal_container {
    display: grid;    
    min-width: 400px;
    max-width: 600px;
    border-radius: 4px;
    background: var(--white-color);
    box-shadow: 2px 4px 6px rgba(0,0,0,.4);
}
.todo-modal_container-header {
    padding: 0 20px;
    border-bottom: 1px solid var(--gray-soft-color);
}
.todo-modal_container-content {
    padding: 0 20px;
}
.todo-modal_container-footer {
    padding: 0 20px;
    border-top: 1px solid var(--gray-soft-color);
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