<template>
    <input  
        type="text" 
        :id="id"
        :placeholder="isSearch ? 'Busca' : placeholder"
        :class="`todo-input ${isSearch ? 'search' : ''}`"
        :value="value"
        @input="input"
    >  
</template>

<script>
export default {
    props: {
        id: {
            type: String,
            required: true
        },
        value: {
            type: String
        },
        placeholder: {
            type: String
        },
        isSearch: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            internalValue: !this.value ? '' : this.value
        }
    },
    methods: {
        input(event) {
            this.internalValue = event.target.value;
            this.$emit('update:value', this.internalValue)
        }
    }
}
</script>

<style>
.todo-input {
    padding: 10px;
    border: 1px solid var(--gray-light-color);
    border-radius: 4px;
    margin-bottom: 15px;
    font-size: 1rem;
    font-weight: bold;
}
.todo-input:hover {
    border: 1px solid var(--primary-color);
    cursor: pointer;
}
.todo-input:focus {
    border: 1px solid var(--secondary-color);
    outline: none;
}
.todo-input.search {
    background-image: url('./assets/search-solid.svg');
    background-repeat: no-repeat;
    background-position: center right 10px;
    background-size: 16px 16px;
    padding-right: 30px;
}
</style>