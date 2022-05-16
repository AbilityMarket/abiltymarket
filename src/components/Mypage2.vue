<template>
	<div class="main">

		<!-- 사이드 리스트 -->
		<aside>
			<div class="box">
				<div class="box_list" >
					<div class="box_list_head" @click="handleListClick(1)">
						<h3>프로필</h3>
						<img :src="state.drop" v-if="state.dropOn1===false" class="drop"/>
						<img :src="state.dropUp" v-if="state.dropOn1===true" class="drop"/>
					</div>
					<hr style="margin-bottom:10px">
					<div class="drop_menu" :class="{active:state.dropOn1}">
						<ul>
							<li>정보</li>
							<li>동네 설정</li>
							<li>관심사 설정</li>
						</ul>
					</div>
				</div>
				<div class="box_list" >
					<div class="box_list_head" @click="handleListClick(2)">
						<h3>마켓</h3>
						<img :src="state.drop" class="drop" v-if="state.dropOn2===false" />
						<img :src="state.dropUp" class="drop" v-if="state.dropOn2===true" />
					</div>
					<hr style="margin-bottom:10px">
					<div class="drop_menu" :class="{active:state.dropOn2}" >
						<ul>
							<li>찜 한 목록</li>
							<li>내가 쓴 글</li>
							<li>리뷰</li>
							<li>내역</li>
						</ul>
					</div>
				</div>
				<div class="box_list" >
					<div class="box_list_head" @click="handleListClick(3)">
						<h3>설정</h3>
						<img :src="state.drop" class="drop" v-if="state.dropOn3===false"/>
						<img :src="state.dropUp" class="drop" v-if="state.dropOn3===true"/>
					</div>
					<hr style="margin-bottom:10px">
					<div class="drop_menu" :class="{active:state.dropOn3}">
						<ul>
							<li @click="handleList(1)">비밀번호 변경</li>
							<li @click="handleList(2)">회원 탈퇴</li>
							<li>알림 설정</li>
						</ul>
					</div>
				</div>
			</div>
		</aside>

		<!-- 메인 -->
		<section>
			<menu-1 v-if="state.components===1"></menu-1>
			<menu-2 v-if="state.components===2"></menu-2>
		</section>
	</div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import menu1 from './mypage/Menu1.vue';
import menu2 from './mypage/Menu2.vue';
export default {
	components:{
		menu1, menu2,
	},
	setup () {
		const state = reactive({
			drop : require("../assets/images/drop.png"),
			dropUp : require("../assets/images/drop_up.png"),
			dropOn1 : false,
			dropOn2 : false,
			dropOn3 : false,
			components : "",
		})

		const handleListClick = (num)=>{
			console.log(num)
			if(num ===1){
				if(state.dropOn1 === false){
					state.dropOn1 = true;
				}
				else{
					state.dropOn1 = false;
				}
			}
			if(num ===2){
				if(state.dropOn2 === false){
					state.dropOn2 = true;
				}
				else{
					state.dropOn2 = false;
				}
			}
			if(num ===3){
				if(state.dropOn3 === false){
					state.dropOn3 = true;
				}
				else{
					state.dropOn3 = false;
				}
			}
			console.log("drop")
		}
    const handleList = (no)=>{
      state.components = no;
      console.log(state.components)
      console.log(no)
      console.log(typeof(no))
      
    }

		return {
			state,
			handleListClick,
      handleList
		}
	}
}
</script>

<style scoped src="../assets/css/mypage2.css">

</style>

