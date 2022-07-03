<template>
	<main class="container">
		<div class="inner-container">
			<h1 class="screen_out">메인 페이지</h1>
			<div>
				<strong>영화와 시리즈를 내 취향대로.</strong>
				<p>내가 관심있는 장르의 영화가 무엇이 있는지 알아보세요.</p>
				<p>요즘 뜨고 있는 핫한 영화가 무엇인지 알아보세요.</p>
			</div>
			<input type="text" class="main-input" />
			<button type="button" @click="clickLogin">바로 시작하기</button>
		</div>
	</main>
</template>

<script>
export default {
	methods: {
		clickLogin() {
			this.$router.push('/login');
		},
	},
};
</script>

<style scoped lang="scss">
@import './scss/main.scss';
</style>
