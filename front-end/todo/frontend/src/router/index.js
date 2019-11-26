import Vue from 'vue'
import VueRouter from 'vue-router'
import TodoList from '../views/TodoList.vue'
import TodoMaintenance from '../views/TodoMaintenance.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'todo-list',
    component: TodoList
  },
  {
    path: '/create',
    name: 'todo-create',
    component: TodoMaintenance
  },
  {
    path: '/update/:id',
    name: 'todo-update',
    component: TodoMaintenance,
    props: true
  },  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
