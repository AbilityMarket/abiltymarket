<template>
    <v-row justify="left">
        <v-menu bottom min-width="300px" rounded offset-y>
            <template v-slot:activator="{ props }">
                <v-btn size="20" icon v-bind="props" @click="alertList">
                    <v-avatar color="yellow" size="20">
                        <span class="text-h6">{{state.alertCnt}}</span>
                    </v-avatar>
                </v-btn>
            </template>
            <v-card>
                <v-list>
                    <v-list-item-group v-if="state.items">
                        <v-list-item v-for="tmp of state.items" :key="tmp">
                            <v-list-item-content v-text="tmp.almessage" style="cursor:pointer;" @click="clickOne(tmp.alno)"></v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-card>
      </v-menu>
    </v-row>
</template>

<script>
import { reactive, onMounted } from 'vue';
import axios from 'axios';
import { useRouter} from 'vue-router';

export default {
  setup () {
        const router = useRouter();
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            alertCnt : ''
        });

        // 알림 개수
        const alertCnt = async() => {
            const url = `/ROOT/api/alert/alunreadcnt`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };        
            const response = await axios.get(url, {headers});
            // console.log(response.data.result);
            if(response.data.status ===200) {
                // 알림 개수
                state.alertCnt = response.data.result;
            }
        }

        onMounted(() => {
            alertCnt();
        });

        // 알림 목록
        const alertList = async() => {
            console.log("확인------");
            const url = `/ROOT/api/alert/alreadlist`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token                
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            // console.log(response.data.list[0].almessage);
            if(response.data.status === 200) {
                state.items = response.data.list;
            }
        };

        // 해당 알림 클릭
        const clickOne = async (alno) => {
            console.log("확인==="+alno);
            const url = `/ROOT/api/alert/alselectone?alno=${state.alno}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token 
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            if(response.data.satus === 200) {
                // router.push(response.data.list.alurl);
                router.push({name:'Home'});
            }
        };

        return {state, alertList, clickOne}
  }
}
</script>

<style lang="scss" scoped>

</style>

