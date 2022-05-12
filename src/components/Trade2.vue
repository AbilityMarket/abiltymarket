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
					<!-- 도와주세요 도와줄게요 탭 -->
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
							<select @change="handleOption($event)" name="" id="" class="rank-top2">
								<option value="">카테고리</option>
								<option v-for="tmp of state.category" :key="tmp" 
								:value="tmp.incategory">{{tmp.incategory}}</option>
								
							</select>
							<img :src=state.droparrow class="droparrow" />
						</div>
							<div class="box">
							<select ref="select" name="" id="" class="rank-top2">
								<option value="">상세</option>
								<option v-for="tmp of state.list" :key="tmp" 
								:value="tmp.inname">{{tmp.inname}}</option>
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
import { reactive } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'
import axios from 'axios';
export default {
	setup () {
		const state = reactive({
			img: require('../assets/images/lense.png'),
			droparrow: require('../assets/images/drop.png'),
			list:"",
		})

		const handleData= async() =>{
			const url = "/ROOT/api/interest/select";
			const headers = {"content=type": "application/json"};
			const response = await axios.get(url, headers);
			if(response.data.status === 200){
				state.category = response.data.result;
				console.log(state.list)
			}
		}

		const handleOption = async(e) =>{
			console.log(e.target.value)
			const url = `/ROOT/api/interest/selectName?incategory=${e.target.value}`
			const headers = {"content=type": "application/json"};
			const response = await axios.get(url, headers);
			if(response.data.status === 200){
				state.list = response.data.result;
				console.log(state.list)
			}
		}

		onMounted(()=>{
			handleData();
		})
		

		return {
			state,
			handleOption
		}
	}
}
</script>

<style scoped src="../assets/css/trade2.css">

</style>
