<template>
    <div class="main">
        <div class="btn-box">
            <router-link to="/InquireWrite"><v-btn class="btn">문의하기</v-btn></router-link>
        </div>
        <v-table class="table">
            <thead>
                <tr class="text-left">
                    <th class="text-left1"> 번호 </th>
                    <th class="text-left2"> 제목 </th>
                    <th class="text-left3"> 댓글유무 </th>
                    <th class="text-left4"> 작성일 </th>
                </tr>
            </thead>
            <tbody>
                <a th:href="@{/item/selectone(code=${tmp.icode})}" th:text="${tmp.inqtitle}"></a>
                <tr v-for="tmp in state.items" :key="tmp">
                    <td> {{ tmp.inqno }} </td>
                    <td class="link" @click="handleDetailPage(tmp.inqno)"> 
                    {{ tmp.inqtitle }} <v-img src="../../assets/images/자물쇠.png" style="width:20px; height:20px; float:right; "/></td>
                    <td v-if="tmp.inqtype == 0"> 완료 </td>
                    <td v-if="tmp.inqtype == 1"> 미완료 </td>
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
import { useRouter} from 'vue-router';
import axios from 'axios';
export default {
    setup(){
        const router = useRouter();
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            page : 1,
            title : '',
            select: 1,

        })
        const handleDetailPage = (inqno) => {
            router.push({name:'SelectOne', query:{inqno:inqno}})
        }

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
        return { state, handleData, handleDetailPage }
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
.btn-box {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    margin-right: 20px;
}
.btn {
    background: white; 
    border: 1px solid #6666; 
    box-sizing: border-box; 
    padding: 4px; 
    color: gray; 
    border-radius: 5px; 
}
.table {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 10px;
}
.text-left1 {
    width: 80px;
}
.text-left2 {
    width: 400px;  
}
.text-left3 {
    width: 140px;
}
.text-left4 {
    width: 300px;
}
.link {
    cursor: pointer;
    color: rgb(15, 135, 182);
}
.text-center {
    display: flex;
    justify-content: center;
    padding: 10px;
    margin-top: 15px;
}

</style>