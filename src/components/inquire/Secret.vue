<template>
    <div class="main">
        <h4> Q&A</h4>
        <div class="image">
            <v-img src="../../assets/images/자물쇠.png" style="width:50px; height:50px; "/>
        </div>
        <div class="guide">
            <h5>이 글은 비밀글 입니다. </h5>
            <h4>비밀번호를 입력하여 주세요.</h4>
        </div>
        <div class="password">
          <label>비밀번호</label>
          <div class="password1">
             <input class="input" type="password" v-model="state.upw" ref="upw" />
          </div>
        </div>
        <div class="btn-box">
            <router-link to="/inquire"><v-btn class="btn">목록으로</v-btn></router-link>
            <v-btn class="btn1" @click="handleClick">확인</v-btn>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { reactive, ref } from "@vue/reactivity";
export default {
    setup () {
        const router = useRouter();
        const route = useRoute();
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            upw : '',
            inqno : route.query.inqno
        })
        const upw = ref(null);

        const handleClick = async() => {
            if(state.upw === '') {
                alert('암호를 입력해주세요')
                upw.value.focus();
                return false;
            }

            // 비밀글 암호 입력
            const url = `/AbilityMarket/api/member/secret`;
            const headers = {
                "Content-type" : "application/json",
                "token" : state.token
            }
            const body = new FormData();
                body.append("pw1", state.upw);

            const response = await axios.post(url, body, {headers})
            console.log(response); 

            if(response.data.status === 200) {
                router.push({name:'SelectOne', query:{inqno:state.inqno}})    
            }
            else {
                alert("작성자/관리자만 확인할 수 있습니다")
                router.push({name:'Inquire'});
            }
        }

        return {state, upw, handleClick}
    }
}
</script>

<style scoped>

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: "GmarketSansMedium";
}

.main {
    display: flex;
    flex-direction: column;
    align-items: center; 
    justify-content: center;
    width: 100%;
    height: 500px;
    padding-left: 20px;
    
}
.main > h4 {
    padding : 30px; 
}

.image{
    padding: 20px;
    margin-right: 14px;
    filter: opacity(0.2) drop-shadow(0 0 0 #999999);
}
.guide{
    display:flex;
    align-items: center;
    margin-bottom: 20px;
}
.guide > h5 {
    color: #adadad;
}
.guide > h4 {
    margin-left: 5px;
}
.password{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 20px;
}
input{
    background: #f4f4f4;
    color: #adadad;
    padding : 0 15px;
    height: 40px;
    width: 220px;
}
.password1{
    margin-left : 10px;
    display: flex;
    align-items: center;
    /* border: 1px solid #adadad;  */
}
.btn-box {
    display : flex;
    width : 100%;
    justify-content: center;
    align-items: center;
    padding: 10px;
}
.btn{
    padding: 10px;
    width: 180px;
    background-color: #f4f4f4;
    color: black;
    margin: 10px;    
    /* border: 1px solid #adadad;  */
    border-radius: 4px;
}
.btn1{
    padding: 10px;
    width: 180px;
    background-color: #f4f4f4;
    color: black;
    /* border: 1px solid #adadad;  */
    border-radius: 4px;
}


</style>