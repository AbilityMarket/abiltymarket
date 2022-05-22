<template>
    <h3>Detail</h3>
    
    <div>
        <div v-if="state.inquire">
            코드 {{ state.inquire.inqno }}
            제목 {{ state.inquire.inqtitle }}
            내용 {{ state.inquire.inqcontent }}
            <!-- 등록일 {{ state.item.regdate }} -->
        </div>

    </div>
</template>

<script>
import { reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
export default {
    setup () {
        const route = useRoute();
        const state = reactive({
            inqno : route.query.inqno,
            token : sessionStorage.getItem("TOKEN")
        });

        const handleData = async() => {
            const url = `/ROOT/api/inquire/selectone?inqno=${state.inqno}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            console.log(response.data);

            if(response.data.status === 200){
            state.inquire = response.data.result;
            }

        }

        onMounted( async() => {
            await handleData();
        })

        return {state}
    }
}
</script>

<style lang="scss" scoped>

</style>