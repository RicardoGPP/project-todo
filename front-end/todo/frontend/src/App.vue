<template>
  <div id="app">
    <transition appear mode="out-in">
      <router-view/>
    </transition>
  </div>
</template>

<style>
:root {
  --white-color: #fff;
  
  --red-color: #cc3333;
  --red-soft-color: #c25b5b;
  --red-light-color: #c93f3f;
  --red-dark-color: #ca0f0f;

  --green-color: #30971b;
  --green-soft-color: #5f9255;
  --green-light-color: #419630;
  --green-dark-color: #1b9104;

  --orange-color: #d68d1f;
  --orange-soft-color: #ccbba1;
  --orange-light-color: #cf9743;
  --orange-dark-color: #9c620b;

  --gray-color: #adaaaa;
  --gray-soft-color: #e7e3e3;
  --gray-light-color: #d3d3d3;
  --gray-dark-color: #686767;
    
  --primary-color: var(--orange-color);
  --secondary-color: var(--orange-light-color);

  --message-error-color: var(--red-color);
  --message-success-color: var(--green-color);
  --message-warning-color: var(--orange-color);

  --btn-color: var(--primary-color);
  --btn-color-hover: var(--secondary-color);
  --btn-color-active: var(--orange-dark-color);
  --btn-color-disabled: var(--orange-soft-color);
  --btn-font-color: var(--white-color);

  --table-border-color: var(--gray-soft-color);
  --table-th-color: var(--gray-dark-color);
  --table-tr-hover-color: var(--gray-light-color);
}
@font-face {
  font-family: "Roboto";
  src: url("assets/fonts/Roboto-Bold.ttf");
}
body, p, h1, h2, h3, h4, h5, div {
  margin: 0;
  padding: 0;
  font-family: 'Roboto', 'Courier New', Courier, monospace
}
#app {
  width: 800px;
  margin: 10px auto 0 auto;
  border-radius: 4px;
  box-shadow: 2px 4px 6px rgba(0,0,0,.4);
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
