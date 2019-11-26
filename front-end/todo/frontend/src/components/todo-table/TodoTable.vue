<template>
  <div class="todo-table-container">

    <TodoInput 
        id="todo-search" 
        :value.sync="internalSearchTerm" 
        :isSearch="true"
    />

    <table class="todo-table">

        <!-- Header -->
        <thead>
            <th v-for="field in fields" :key="field.name">
                <slot name="table-col" :field="field">
                    {{field.label}}
                </slot>
            </th>
        </thead>

        <!-- Data -->
        <tbody>
            <template v-if="hasData">
                <tr v-for="(item, itemIndex) in internalItems" :key="`tr_${itemIndex}`" @click.stop="rowClicked(item)">
                    <td v-for="(field, fieldIndex) in fields" :key="`td_${field.name}_${itemIndex}_${fieldIndex}`">
                        <slot name="table-row" :field="field.name" :item="item" :value="item[field.name]">
                            {{item[field.name]}}
                        </slot>
                    </td>
                </tr>
            </template>
            <template v-else>
                <tr>
                    <td :colspan="fields.length" class="todo-table-nodata">
                        Não há dados
                    </td>
                </tr>
            </template>
        </tbody>

        <!-- Pagination -->
        <tfoot v-if="paginated">
            <tr>
                <td :colspan="fields.length" class="todo-table-footer">
                    <TodoTablePagination 
                        :currentPage.sync="internalCurrentPage" 
                        :pages="pages" 
                        :pageSize.sync="internalPageSize"
                        :disabled="searched"
                    />
                </td>
            </tr>
        </tfoot>
    </table>

  </div>
</template>

<script>
import TodoInput from '@/components/todo-input/TodoInput';
import TodoTablePagination from '@/components/todo-table/TodoTablePagination';

export default {
    components: { TodoInput, TodoTablePagination },
    props: {
        fields: {
            type: Array,
            required: true
        },
        items: {
            type: Array,
            required: true
        },
        searchable: {
            type: Boolean,
            default: false
        },
        paginated: {
            type: Boolean,
            default: false
        },
        pageSize: {
            type: Number,
            default: 10,
            validator: (value) => [5, 10, 20].find(size => value === size)
        }
    },
    data() {
        return {
            internalSearchTerm: '',
            internalItems: this.items,
            internalPageSize: this.pageSize,
            internalCurrentPage: 1,
        }
    },
    computed: {
        pages() {
            return this.internalPageSize != 0 
                ? Math.ceil(this.items.length / this.internalPageSize)
                : 1;
        },
        searched() {
            return this.internalSearchTerm != '';
        },
        hasData() {
            return this.internalItems.length > 0;
        }
    },
    watch: {
        items() {
            this.updateData();
        },
        internalSearchTerm() {
            this.search();
        },
        internalCurrentPage() {
            this.updateData();
        },
        internalPageSize() {
            this.updateData();
        }
    },
    methods: {
        updateData() {
            if (!this.paginated || this.internalPageSize === 0) {
                this.internalItems = this.items;
                return;
            } 

            let end = this.internalCurrentPage * this.internalPageSize;
            let begin = (this.internalCurrentPage * this.internalPageSize) - this.internalPageSize;
            
            this.internalItems = this.items.slice(begin, end);
        },
        search() {
            this.internalSearchTerm.length === 0
                ? this.updateData()
                : this.internalItems = this.items.filter(item => {
                    for(let prop in item) {
                        return new String(item[prop]).toLowerCase()
                            .includes(this.internalSearchTerm.toLowerCase());
                    }
                })
        },
        rowClicked(item) {
            this.$emit('onRowClick', item);
        }
    },
    created() {
        setTimeout(() => {
            if (!this.hasData) {
                this.internalCurrentPage = 0
            }            
        }, 100);
    }

}
</script>

<style>
.todo-table-container {
    display: grid;
}
.todo-table {
    border-collapse: collapse;
}
.todo-table thead th {    
    text-align: center;
    padding: 10px;
    background: var(--table-th-color);
    color: var(--white-color);
    border-right: 1px solid var(--table-border-color);
}
.todo-table thead th:first-child {
    border-left: 1px solid var(--table-th-color);
}
.todo-table thead th:last-child {
    border-right: 1px solid var(--table-th-color);
}
.todo-table tbody tr:hover {
    background: var(--table-tr-hover-color);
}
.todo-table td {
    border-bottom: 1px solid var(--table-border-color);
    border-right: 1px solid var(--table-border-color);
    padding: 10px;
}
.todo-table td:last-child {
    border-right: none;
}
.todo-table-nodata {
    padding: 10px;
    font-size: 1.2rem;
    font-weight: bold;
}
.todo-table-footer {
    background: var(--gray-light-color);
}
</style>