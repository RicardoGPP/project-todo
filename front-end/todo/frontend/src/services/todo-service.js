import axios from 'axios';

export default class TodoService {

    static _axioInstance = undefined;

    static get _http() {
        if (!TodoService._axioInstance) {
            TodoService._axioInstance = axios.create({
                baseURL: 'http://localhost:8080/tasks'
            });
        }
        return TodoService._axioInstance;
    }

    static listAll() {
        return TodoService._http.get('/')
                .then(result => result.data);
    }

    static find(taskId) {
        return TodoService._http.get(`/${taskId}`)
                .then(result => result.data);
    }

    static create(task) {
        return TodoService._http.post('/', task);
    }

    static update(task) {
        return TodoService._http.put(`/${task.id}`, task);
    }

    static updateState(task) {
        return TodoService._http.put(`/${task.id}`, {
                description: task.description,
                done: !task.done
            });
    }

    static delete(taskId) {
        return TodoService._http.delete(`/${taskId}`);
    }

}