<template>
    <div>

    </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'
import axios from 'axios';
export default {
    setup () {
        
        const state =reactive({

        })

        onMounted(()=>{
            handleData()
        })

        const handleData = async()=>{
            const url = "/AbilityMarket/api/mypage/transactionHistory";
            const headers = {"content-type": "application/json",
            "token": sessionStorage.getItem("TOKEN")};
            const response = await axios.get(url, {headers});
            console.log("mypage/review =>handleData",response)
            // state.list = response.data.
        }

        return { 
            state,
            }
    }
}
</script>

<style scoped src= "../../assets/css/transactionHistory.css">

</style>