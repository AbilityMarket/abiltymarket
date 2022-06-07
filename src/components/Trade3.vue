<template>
    <div style="margin-left: 20px"><h3>카테고리</h3></div>
    <!-- <div class="cate_box">

    </div>

    <div class="main2">
        <div class="d-flex mb-6" style="margin-top: 30px">
            <div v-if="state.tab==='buy'"><h3>도와주세요</h3></div>
            <div v-if="state.tab1==='sell'"><h3>도와줄게요</h3></div>
        </div>
    </div> -->
</template>

<script>
import { reactive } from '@vue/reactivity';
import { onMounted } from '@vue/runtime-core';
import axios from "axios";

export default {
    setup () {

        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            tab : "buy",
            tab1 : "sell",
            ulatitude : "",
            ulongitude : "",
        });

        const handleDataMemAddr = async() => {
            const url = `/AbilityMarket/api/memaddr/selonememaddr`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            console.log(response.data.memAddrEnt.ulatitude);
            if(response.data.status === 200){
                state.ulatitude = response.data.memAddrEnt.ulatitude;
                state.ulongitude = response.data.memAddrEnt.ulongitude;
            }
        };

        const MemAddrUkm = async() => {

        };

        const handleDataBodAddr = async() => {
            // kakao.maps.LatLng(latitude, longitude);
            // kakao.maps.LatLngBounds(sw, ne);

            // state.ulatitude= response.data.documents[0].y // 위도
            // state.ulongitude = response.data.documents[0].x // 경도

            // 2000m -> 2km
            // "https://dapi.kakao.com/v2/local/search/keyword.json?y={state.ulatitude}&x={state.ulongitude}&radius=2000"
        };

        onMounted(() => {
            handleDataMemAddr();
            handleDataBodAddr();
            MemAddrUkm();
        });
        
        return {state}
    }
}
</script>

<style lang="scss" scoped>

</style>