<template>
	<header class="header-wrapper">
		<router-link to="/main">
			<img
				:src="require(`@/assets/images/${images}`)"
				alt="logo"
				class="logo-img"
			/>
		</router-link>
		<button
			v-if="loginCheck()"
			type="button"
			class="direct-link"
			@click="clickLogin"
		>
			로그인
		</button>
	</header>
</template>

<script>
export default {
	data() {
		return {
			images: 'Logo.png',
		};
	},
	methods: {
		clickLogin() {
			this.$router.push('/login');
		},
		loginCheck() {
			const isLogin = true;
			return isLogin;
		},
	},
};
</script>

<style scoped lang="scss">
@import '@/styles/variables/index.scss';
.header-wrapper {
	display: flex;
	justify-content: space-between;
}
.logo-img {
	width: 181px;
	height: 45px;
	margin: 25px;
}
.direct-link {
	width: 91px;
	height: 36px;
	margin: 29px;
	background-color: $red-60;
	border-radius: 5px;
	color: $gray-100;
	font-size: 17px;
	font-weight: 400;
}
</style>
