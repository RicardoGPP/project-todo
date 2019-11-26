<template>
    <TodoModal :id="id">
        <template v-slot:header v-if="title">
            <div class="todo-modal-confirm-header">
                {{title}}
            </div>
        </template>

        <template v-slot:content >
            <div class="todo-modal-confirm-content">
                <i class="fa fa-question fa-3x"/>
                <div class="todo-modal-confirm-content_message">
                    <p v-if="message">{{message}}</p>
                    <p v-if="messageComp">{{messageComp}}</p>
                </div>
            </div>
        </template>

        <template v-slot:footer >
            <div class="todo-modal-confirm-actions">
                <TodoButton 
                    :label="labelOk"
                    @onClick="onOkClicked"
                />
                <TodoButton 
                    :label="labelCancel"
                    @onClick="onCancelClicked"
                /> 
            </div>
        </template>
    </TodoModal>
</template>

<script>
import TodoModal from '@/components/todo-modal/TodoModal';
import TodoModalService from '@/components/todo-modal/TodoModalService';

export default {
    name: 'TodoModalConfirm',
    components: { TodoModal },
    props: {
        id: {
            type: String,
            required: true
        },
        title: {
            type: String
        },
        message: {
            type: String
        },
        messageComp: {
            type: String
        },
        labelOk: {
            type: String,
            default: 'OK'
        },
        labelCancel: {
            type: String,
            default: 'Cancelar'
        }
    },
    methods: {
        onOkClicked() {
            this.$emit('onOk')
            TodoModalService.close(this.id);
        },
        onCancelClicked() {
            this.$emit('onCancel');
            TodoModalService.close(this.id);
        }
    }
}
</script>

<style scoped>
.todo-modal-confirm-header {
    padding: 20px 0;
    font-size: 1.1rem;
}
.todo-modal-confirm-content {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding: 20px 0 40px 0;
}
.todo-modal-confirm-content_message {
    display: inline-block;
    margin-left: 20px;
    font-size: 1rem;
}
.todo-modal-confirm-content_message p:nth-child(2) {
    font-style: italic;
}
.todo-modal-confirm-actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding: 20px 0;
}
.todo-modal-confirm-actions button {
    min-width: 100px;
    margin-left: 10px;
}
</style>