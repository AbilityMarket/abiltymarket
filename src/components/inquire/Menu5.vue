<template>
    <div class="main">
        <h4> Q&A</h4>
        <div class="image">
            <v-img src="../../assets/images/자물쇠.png" style="width:50px; height:50px; "/>
        </div>
        <div class="">
            <p>이 글은 비밀글 입니다. 비밀번호를 입력하여 주세요.</p>
        </div>
        <div class="password">
          <label>비밀번호</label>
          <div class="ddd">
          <input class="input" type="password" v-model="state.upw" />
          </div>
        </div>
    </div>
</template>

<script>
// import axios from 'axios';
import { reactive } from "@vue/reactivity";
export default {
    setup () {
        const state = reactive({
            upw: '',
        })
        

        return {state}
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
    width: 100%;
    padding-left: 20px;
    padding-right: 10%;
    align-items: center;  
}
.main > h4 {
    padding : 30px; 
}

.image{
    padding: 20px;
    filter: opacity(0.3) drop-shadow(0 0 0 #999999);
}
.password{
    display: flex;
    width: 75%;
    height: 100%;
    flex-direction: column;
    margin-left: 40px;
}
input{
    border: 1px solid black;
    border-color: #eee;
    background: #f4f4f4;
    color: #adadad;
    padding : 0 15px;
    height: 42px;
    width: 100%
}

</style>