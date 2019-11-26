<template>
  <div class="todo-overlay">
      <slot />
  </div>
</template>

<script>
export default {
    name: 'TodoOverlay'
}
</script>

<style scoped>
.todo-overlay {
    position : fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0,0,0,.3);
    z-index: 99;
}
</style>