<template>
	<div class="app">
		<AppHeader></AppHeader>
		<div class="app-contents">
			<router-view></router-view>
		</div>
	</div>
</template>

<script>
import AppHeader from '@/components/layouts/AppHeader.vue';
export default {
	components: { AppHeader },
};
</script>

<style lang="scss">
@import '@/styles/style.scss';
.app {
	position: relative;
	width: 100%;
	height: 100%;
	font-size: 1.4rem;
}
.app-contents {
	height: calc(100% - 64px - 102px);
}
</style>
