import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
	headers: {
		'cache-control': 'no-cache',
		'content-type': 'application/json',
	},
});

function registerUser(userData) {
	return instance.post('/join', userData);
}

function loginUser(userData) {
	return instance.post('/login', userData);
}

export { registerUser, loginUser };
