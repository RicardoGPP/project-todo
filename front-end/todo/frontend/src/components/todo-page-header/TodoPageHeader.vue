<template>
  <h2>{{title}}</h2>
</template>

<script>
export default {
    name: 'TodoPageHeader',
    props: ['title'],
}
</script>

<style scoped>
h2 {
    text-align: center;
    font-size: 2rem;
    margin: 50px 0;
}
</style>