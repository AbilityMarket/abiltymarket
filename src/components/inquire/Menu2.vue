<template>
    <div class="main">
        <div>
            <input type="button" value="문의하기" class="btn" @click="handleClick">
        </div>
        <v-table class="table">
            <thead>
                <tr>
                    <th class="text-left1"> 번호 </th>
                    <th class="text-left2"> 제목 </th>
                    <th class="text-left3"> 완료 | 미완료</th>
                    <th class="text-left4"> 작성일 </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="tmp in state.items" :key="tmp">
                    <td> {{ tmp.inqno }} </td>
                    <td> {{ tmp.inqtitle }} <v-img src="../../assets/images/자물쇠.png" style="width:20px; height:20px; float:right; "/></td>
                    <td> {{ tmp.inqtype }} </td>
                    <td> {{ tmp.inqregdate }} </td>
                </tr>
            </tbody>
        </v-table>
        <div class="text-center">
            <v-pagination
                v-model="state.page" :length="5"
                prev-icon="mdi-menu-left" next-icon="mdi-menu-right" @click="handleData()"
            ></v-pagination>
        </div>
    </div>
    
</template>

<script>
import { reactive, onMounted } from 'vue';
import axios from 'axios';
export default {
    setup(){
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            page : 1,
            title : '',
            select: 1,

        })

        const handleData = async() => {
            const url = `/ROOT/api/inquire/selectlist?page=${state.page}&title=${state.title}&select=${state.select}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            if(response.data.status === 200) {
                state.items = response.data.list;
               
            }
        }
        onMounted( () => {
            handleData();
        })
        return { state, handleData }
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
    width : 100%;
    padding : 10px;
}
.btn {
    background: white; 
    border: 1px solid #6666; 
    box-sizing: border-box; 
    padding: 4px; 
    color: gray; 
    border-radius: 5px; 
    margin-top: 15px;
    margin-left: 15px;
}

.table {
    padding: 10px;
}
.text-left1 {
    width: 10%;
}
.text-left2 {
    width: 50%;
    
}
.text-left3 {
    width: 15%;
}
.text-left4 {
    width: 25%;
}
.text-center {
    display: flex;
}

</style>