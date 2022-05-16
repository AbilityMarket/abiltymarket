<template>
  <div class="main">
		<h3>비밀번호 변경</h3>

		<div class="password">
			<div>기존 비밀번호</div>
			<input v-model="state.pw" type="password">
		</div>

		<div class="new">
			<div class="password">
				<div>새로운 비밀번호</div>
				<input v-model="state.pw1" type="password">
			</div>

			<div class="password">
				<div>비밀번호 확인</div>
				<input v-model="state.pw2" type="password">
			</div>
		</div>
		<input type="button" value="비밀번호 변경" class="btn" @click="handleClick">
	</div>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  setup() {
		const state = reactive({
			pw:'',
			pw1:'',
			pw2:'',
		})

		const handleClick = ()=>{
			console.log("handleClick")
			// 정규표현식 등장!!!!
			// if(pw)
		}

    return {
			state,
			handleClick
		};
  },
};
</script>

<style scoped src="../../assets/css/changepassword2.css">
</style>