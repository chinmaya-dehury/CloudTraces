import Vue from 'vue';
import Router from 'vue-router';
import UploadFile from '@/views/UploadFile.vue';
import NotFound from '@/components/errorPages/NotFound';
import App from "@/App";

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Home',
            component: App,
        },
        {
            path: '/trace-file-upload',
            name: 'TraceFileUpload',
            component: UploadFile,
        },
        {
            path: '*',
            name: 'NotFound',
            component: NotFound,
        }
    ],
});
