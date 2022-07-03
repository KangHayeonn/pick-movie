const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
	transpileDependencies: true,
	devServer: {
		port: 8001,
		client: {
			overlay: false,
		},
		/*
		proxy: {
			// proxy할 경로
			'^/api/v1': {
				// proxy되는 target
				target: process.env.VUE_APP_BASE_URL,
				// cross origin 허용
				changeOrigin: true,
			},
		},*/
	},
});
