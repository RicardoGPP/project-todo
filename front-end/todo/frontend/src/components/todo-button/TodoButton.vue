<template>
    <button 
        :type="submit ? 'submit' : 'button'"
        @click.stop="buttonClicked"
        :class="`${circle ? 'circle' : '' } ${size} ${clazz}`"
        :title="tooltip"
        :disabled="disabled"
    >
        <i v-if="icon" :class="`fa fa-${icon}`"></i>
        {{this.label}}
</button>
</template>

<script>
export default {
    props: {
        label: {
            type: String
        },
        submit: {
            type: Boolean,
            default: false
        },
        circle: {
            type: Boolean,
            default: false
        },
        icon: {
            type: String
        },
        tooltip: {
            type: String
        },
        disabled: {
            type: Boolean,
            default: false
        },
        size: {
            type: String,
            default: 'medium',
            validator: (value) => ['small', 'medium', 'large'].indexOf(value) >= 0
        },
        clazz: {
            type: String
        }
    },
    methods: {
        buttonClicked() {
            this.$emit('onClick');
        }
    }
}
</script>

<style scoped>
button {
    background: var(--btn-color);
    color: var(--btn-font-color);
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    outline: none;
}
button:hover {
    background: var(--btn-color-hover);
}
button:active {
    background: var(--btn-color-active);
}
button:disabled {
    background: var(--btn-color-disabled);
}
.circle {
    border-radius: 50%;
}
.small {
    width: 24px;
    height: 24px;
    font-size: .9rem;
}
.medium {
    width: 38px;
    height: 38px;
    font-size: 1rem;
}
.large {
    width: 52px;
    height: 52px;
    font-size: 1.1rem;
}
</style>
