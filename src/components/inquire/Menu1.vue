<template>
    <h2> 자주묻는질문 </h2>
    <!-- <div>
        <div class = "z"  v-for="n in 2" :key="n"> 
            <div class="btn">
                <p @click="state.aa=1">1번을 누르면</p>
            </div>
            <div class="btn" >
                <p @click="state.aa=2">2번을 누르면</p>
            </div>
        </div>
        <div class="b"></div>
            <div class="boxbox" >
                <div v-show="state.aa === 1"><p> 1번에 해당하는게 나온다. </p></div>
                <div v-show="state.aa === 2"><p> 2번에 해당하는게 나온다. </p></div>
            </div>
    </div> -->


</template>

<script>
import { reactive } from 'vue';
export default {
    setup () {
        const state = reactive({
                aa:0
        })        
        return {state}
    }
}
</script>



<style scoped>
.a {
    display: flex;
    width: 100px;
    background: beige;
}
.b {
    width:25%;
    height:100px;
}
.z {
    width:100%;
    display: flex;
    flex-direction: row;
}
.btn {
    margin : 5px;
    width:200px; 
    height:50px; 
    background: white; 
    border: 1px solid #6666; 
    box-sizing: border-box; 
    padding: 10px; 
    color: gray; 
    border-radius: 5px; 
}
.box {
    padding: 10px;
    font-size: 25px;
    cursor: pointer; 
    border: 1px solid #777777;
    background: bisque;
    margin: 10px;
}
.boxbox {
    width:100%;
    border: 1px solid #d9d9d9;
    font-size: 20px;
    padding : 10px;
}
</style>