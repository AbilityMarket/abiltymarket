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
             <input class="input" type="password" v-model="state.upw" />
          </div>
        </div>
        <div class="btn-box">
            <input type="button" value="목록" class="btn" @click="handleClick">
            <input type="button" value="확인"  class="btn1" @click="handleClick1">
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
    width: 70%;
    margin-left: 40px;
    margin-bottom: 20px;
}
input{
    background: #f4f4f4;
    color: #adadad;
    padding : 0 15px;
    height: 40px;
    width: 100%
}
.password1{
    margin-left : 10px;
    display: flex;
    align-items: center;
    /* border: 1px solid #adadad;  */
}
.btn-box {
    display : flex;
    width : 50%;
    justify-content: center;
    align-items: center;
    padding: 10px;
}
.btn{
    padding: 10px;
    width: 50%;
    background-color: #f4f4f4;
    color: black;
    margin: 10px;    
    /* border: 1px solid #adadad;  */
    border-radius: 4px;
}
.btn1{
    padding: 10px;
    width: 50%;
    background-color: #f4f4f4;
    color: black;
    /* border: 1px solid #adadad;  */
    border-radius: 4px;
}


</style>