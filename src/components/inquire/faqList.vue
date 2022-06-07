<template>
    <h2 class="faq"> FAQ_ 자주묻는질문 </h2>

    <div class="main">
        <div class="text-subtitle-2 mt-4 mb-2"></div>
        <v-expansion-panels variant="accordion">
            <v-expansion-panel v-for="tmp in state.faq" :key="tmp">
                <v-expansion-panel-title>
                    {{tmp.inqtitle}}
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                    <p v-html= "tmp.inqcontent"></p>
                </v-expansion-panel-text>
            </v-expansion-panel>
        </v-expansion-panels>
    </div>

</template>

<script>
import { reactive, onMounted } from 'vue';
// import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
    setup () {
        // const router = useRouter();
        const state = reactive({
            aa:0,
            token : sessionStorage.getItem("TOKEN"),
            page: 1,
            title: '',
            select: 2,  
        })     

        const handleData = async() => {
            const url = `/AbilityMarket/api/inquire/faq/selectlist?page=${state.page}&title=${state.title}&select=${state.select}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            if(response.data.status === 200) {
                console.log(response);
                state.faq = response.data.list              
            }
        }

        onMounted( () => {
            handleData();
        })

        return {state}
    }
}
</script>

<style scoped>
.faq {
    padding: 15px;
    margin-left: 15px;
    margin-bottom: 40px;
}
.main { 
    margin: 10px;
    margin-left: 20px;
}
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