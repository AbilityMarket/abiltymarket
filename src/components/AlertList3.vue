<template>
<div>
  <v-card class="mx-auto" max-width="300" tile>
    <v-list flat>
      <v-subheader>alert list</v-subheader>
        <v-list-item-group color="primary" v-if="state.items">
          <v-list-item v-for="tmp of state.items" :key="tmp">
            <v-list-item-content>
              <v-list-item-title v-text="tmp.almessage" style="cursor:pointer;" @click="clickOne(tmp.alno)"></v-list-item-title>
            </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</div>
</template>

<script>
import { reactive, onMounted } from 'vue';
import { useRouter} from 'vue-router';
import axios from 'axios';
export default {
  setup () {
    const router = useRouter();
    const state = reactive({
      token : sessionStorage.getItem("TOKEN"),
      alno : ''
    });

    const handleData = async() => {
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

    onMounted(() => {
      handleData();
    });

    const clickOne = async (alno) => {
      console.log("확인듕=========" + alno);
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
    }

    return {state, clickOne}
  }
}
</script>

<style lang="scss" scoped>

</style>