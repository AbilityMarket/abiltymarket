<template>
<div class="body" v-if="state">

    <div style="margin-left: 20px"><h3>카테고리</h3></div>
    <div class="cate_box">
        <div class="cate_small" :class="{active:state.cateSmall[idx]}" @click="handleCateSmall(tmp,idx)" v-for="(tmp,idx) of state.category" :key="tmp">
        </div>
    </div>

	<!-- 게시판 -->
    <div class="main2" v-if="state.boardComp">
        <div class="d-flex mb-6" style="margin-top: 30px">
            <div v-if="state.tab2==='buy'"><h3>도와주세요</h3></div>
            <div v-if="state.tab2==='sell'"><h3>도와줄게요</h3></div>
        </div>        
    </div>
    <div class="gribox">
        <div class="helpme" v-for="tmp in state.board" :key="tmp">
            <ul class="helpmelist" style="margin-right: 10px; margin-left: 10px">
                <li>
					{{tmp.bno}}
                    <a href="#">
                        <div class="profile">
                            <div class="profile-item">
                                <v-img
                                src="../assets/images/user.png"
                                style="width: 25px; height: 25px"
                                />
                            </div>
							<p class="nickname">{{tmp.uid}}</p>
                        </div>
                    </a>
                    <a href="#" class="imghover">
                        <div class="wrphover">
                            <div class="thumbnail">
                                <v-img :src="`ROOT/api/trade/image?bno=${tmp.bno}`"
                                    style="width:100%; height:100%; object-fit:cover;"/>
                            </div>
                            <span class="new">NEW</span>
                        </div>
                        <div class="profilebottom">
                            <div class="check">
                                <v-img
                                src="../assets/images/check.png"
                                style="width: 30px; height: 30px"
                                />
                            </div>
                            <div class="profilebottom-item">
                                <p>{{tmp.btitle}}</p>
                            </div>
                        </div>

                        <div class="location">
                            <div class="location-item">
                                <v-img
                                src="../assets/images/location.png"
                                style="width: 20px; height: 20px; margin-bottom: 40px"
                                />
                            </div>
                            <div class="address"><p>{{tmp.baddress}}</p></div>
                        </div>
                    </a>
                </li>
            </ul>            
        </div>
    </div>
</div>

</template>

<script>
import { reactive } from '@vue/reactivity'
// import { onMounted } from "@vue/runtime-core";
// import axios from "axios";

export default {
    setup () {
        const state = reactive({
            tab2: "buy",
            boardComp: false,
            page:1,
            cateSmall:[],
            brole: 1,
            selectcategoryname: "",
        })

        // 카테고리 클릭이벤트 (해당 회원 관심사만 나오게 설정)
        const handleCateSmall= async(v, idx)=>{
            console.log(v.incode, idx)
            state.cateSmall = Array(state.category.length).fill(false, 0);
            if(state.cateSmall[idx]===true){
                state.cateSmall[idx] = false;
            }
            else if(state.cateSmall[idx]===false){
                state.cateSmall[idx] = true;
            }
            const params = `page=${state.page}&brole=${state.brole}&inname=${state.selectcategoryname}`
            const url = "/ROOT/api/meminterest/memintchk?"+params
            const headers = {"content-type": "application/json"};
            const response = await axios.get(url, {headers})
            console.log(response);
            if(response.data.status===200){
                state.board = response.data.list
                state.page = response.data.page
            }
        }

        return {
            state,
            handleCateSmall
        }
    }
}
</script>

<style scoped src="../assets/css/trade2.css">
</style>