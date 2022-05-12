<template>
	<div class="body" v-if="state">
		<section>
			<div class="top">
				<div class="top_left">
					<div>당신의 재능을</div>
					<div>사고 파세요</div>
				</div>
				<div class="top_right">

				</div>
			</div>

			<div class="main">
				<div class="main_left">
					<div class="main_left-1">
						<div class="tab_buy"><p>도와주세요</p></div>
						<div class="tab_sell"><p>도와줄게요</p></div>
					</div>
					<div class="main_left-2">
						<img :src=state.img class="img" />
						<span>무엇을 도와드릴까요?</span>
					</div>
					<div class="main_left-3">
						<div class="box2">
							<div class="box">
							<select name="" id="" class="rank-top2">
								<option value="">운동</option>
								<option value="">달리기</option>
								<option value="">농구</option>
								<option value="">축구</option>
								<option value="">야구</option>
								<option value="">미술</option>
							</select>
							<img :src=state.droparrow class="droparrow" />
						</div>
							<div class="box">
							<select ref="select" name="" id="" class="rank-top2">
								<option value="">운동</option>
								<option value="">달리기</option>
								<option value="">농구</option>
								<option value="">축구</option>
								<option value="">야구</option>
								<option value="">미술</option>
							</select>
							<img :src=state.droparrow class="droparrow" />
						</div>
						</div>
					</div>
					<div class="main_left-4">
						검색
					</div>
				</div>
			<div class="main_right">
			</div>
			</div>

		</section>
	</div>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
export default {
	setup () {
		const state = reactive({
			img: require('../assets/images/lense.png'),
			droparrow: require('../assets/images/drop.png')
		})
		

		return {
			state,
		}
	}
}
</script>

<style scoped src="../assets/css/trade2.css">

</style>
