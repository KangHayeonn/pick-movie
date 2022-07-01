import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

export default new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/login',
		},
		{
			path: '/login',
			component: () => import('@/views/LoginPage.vue'),
			name: 'login',
		},
		{
			path: '/signup',
			component: () => import('@/views/SignupPage.vue'),
			name: 'signup',
		},
		{
			path: '/main',
			component: () => import('@/views/MainPage.vue'),
			name: 'main',
		},
		{
			path: '*',
			component: () => import('@/views/NotFoundPage.vue'),
		},
	],
});
