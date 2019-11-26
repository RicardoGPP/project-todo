<template>
    <div class="pagination-container">
        <div class="pagination-pages">
            <span>Registros por páginas:</span>
            <select 
                v-model="internalPageSize" 
                @change="changePageSize"
            >
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="0">todos</option>
            </select>
        </div>

        <div class="pagination-controls">
            <TodoButton 
                icon="angle-left" 
                circle
                :disabled="disableLeftButtons" 
                @onClick="previwesPage" 
                size="small"
                clazz="pagination-controls_button"
            />
            <TodoButton 
                icon="angle-double-left" 
                circle
                :disabled="disableLeftButtons" 
                @onClick="firstPage" 
                size="small"
                clazz="pagination-controls_button"
            />

            <div class="pagination-controls_pages">
                {{internalCurrentPage}} de {{pages}}
            </div>

            <TodoButton
                icon="angle-double-right" 
                circle
                :disabled="disableRightButtons" 
                @onClick="lastPage" 
                size="small"
                clazz="pagination-controls_button"
            />
            <TodoButton 
                icon="angle-right" 
                circle
                :disabled="disableRightButtons" 
                @onClick="nextPage" 
                size="small"
                clazz="pagination-controls_button"
            />
        </div>
    </div> 
</template>

<script>
export default {
    name: 'TodoTablePagination',
    props: {
        currentPage: {
            type: Number,
            required: true
        },
        pages: {
            type: Number,
            required: true
        },
        pageSize: {
            type: Number,
            required: true
        },
        disabled: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            internalCurrentPage: this.currentPage,
            internalPageSize: this.pageSize
        }
    },
    watch: {
        currentPage() {
            this.internalCurrentPage = this.currentPage
        }
    },
    methods: {
        previwesPage() {
            this.internalCurrentPage--;
            this.$emit('update:currentPage', this.internalCurrentPage);
        },
        firstPage() {
            this.internalCurrentPage = 1;
            this.$emit('update:currentPage', this.internalCurrentPage);
        },
        lastPage() {
            this.internalCurrentPage = this.pages;
            this.$emit('update:currentPage', this.internalCurrentPage);
        },
        nextPage() {
            this.internalCurrentPage++;
            this.$emit('update:currentPage', this.internalCurrentPage);
        },
        changePageSize() {
            this.$emit('update:pageSize', parseInt(this.internalPageSize));
        }
    },
    computed: {
        isBeginOfPages() {
            return this.internalCurrentPage === 1;
        },
        isEndOfPages() {
            return this.internalCurrentPage === this.pages;
        },
        disableLeftButtons() {
            return this.disabled || this.isBeginOfPages || !this.internalCurrentPage;
        },
        disableRightButtons() {
            return this.disabled || this.isEndOfPages || !this.internalCurrentPage;
        }
    }
}
</script>

<style scoped>
.pagination-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.pagination-pages {
    display: inline-block;
    margin-right: 15px;
}
.pagination-pages span {
    margin-right: 10px;
}
.pagination-pages select {
    padding: 5px;
    border: 1px solid var(--gray-soft-color);
    border-radius: 4px;
    font-family: 'Roboto';
    font-size: .9rem;
    outline: none;
}
.pagination-controls_button{
    margin-left: 5px;
}
.pagination-controls_pages {
    display: inline-block;
    margin: 0 10px;
}
</style>