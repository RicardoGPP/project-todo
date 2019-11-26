<template>
    <section class="maintain-container">

        <TodoMessage 
            :id="messageId" 
        />

        <TodoPageHeader 
            :title="title" 
        />

        <form @submit.prevent="saveTask" class="maintain-form">
            <TodoInput 
                id="description" 
                :value.sync="task.description" 
                placeholder="Descrição"
            />

            <div class="maintain-actions">
                <TodoButton 
                    label="Voltar"
                    @onClick="backToList"
                    icon="arrow-left"
                />

                <TodoButton 
                    submit
                    label="Confirmar" 
                    icon="check"
                />                
            </div>
        </form>

        <TodoModalConfirm 
            :id="idSaveConfirm"
            :title="title"
            :message="confirmMessage"
            labelOk="Sim"
            labelCancel="Não"            
            @onOk="confirmSaveTask"
        />

    </section>
</template>

<script>
import TodoService from '@/services/todo-service.js';
import TodoPageHeader from '@/components/todo-page-header/TodoPageHeader';
import TodoInput from '@/components/todo-input/TodoInput';
import TodoMessageService from '@/components/todo-message/TodoMessageService';
import TodoModalConfirm from '@/components/todo-modal/TodoModalConfirm';
import TodoModalService from '@/components/todo-modal/TodoModalService';

export default {
    components: { TodoPageHeader, TodoInput, TodoModalConfirm },
    props: ['id'],
    data() {
        return {
            task: {
                description: ''
            }
        }
    },
    computed: {
        title() {
            return (!this.id ? 'Criar' : 'Alterar') + ' Task';
        },
        confirmMessage() {
            return (this.id ? 'Deseja realmente alterar a task?' : 'Deseja realmente inserir a task?');
        },
        messageId() {
            return 'todoInsertUpdate';
        },
        idSaveConfirm() {
            return 'saveTaskConfirm'
        }
    },
    methods: {
        saveTask() {
            TodoModalService.open(this.idSaveConfirm);
        },
        confirmSaveTask() {
            if (!this.task.description) {
                alert('Descrição não informada')
                return;
            }

            if (!this.id) {
                TodoService.create({
                        description: this.task.description,
                        done: false
                    })
                    .then(() => {
                        this.task.description = '';
                        TodoMessageService.success(this.messageId, 'Task criada com sucesso')
                    })
                    .catch(() => TodoMessageService.error(this.messageId, 'Erro ao inserir a task'));
            } else {
                TodoService.update({
                    id: this.id,
                    description: this.task.description,
                    done: false                    
                })
                .then(() =>  TodoMessageService.success(this.messageId, 'Task alterada com sucesso'))
                .catch(() => TodoMessageService.error(this.messageId, 'Erro ao alterar a task'));
            }
        },
        backToList() {
            this.$router.push({ name: 'todo-list'})
        }
    },
    created() {
        if (this.id) {
            TodoService.find(this.id)
                .then(task => this.task.description = task.description)
                .catch(() => TodoMessageService.error(this.messageId, 'Erro ao carregar a task'));
        }
    }
}
</script>

<style scoped>
.maintain-container {
    display: grid;
    border: 1px solid lightgray;
    border-radius: 4px;
    padding: 20px;
}
.maintain-form {
    display: grid;
}
.maintain-actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 20px;
}
.maintain-actions button {
    width: 150px;
    margin-left: 10px;
}
</style>
