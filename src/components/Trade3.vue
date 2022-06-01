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
export default {
    setup () {

        const state = reactive({
            tab : "buy",
            tab1 : "sell",
        });

        const handleDataMemAddr = async() => {
            // kakao.maps.LatLng(latitude, longitude);
            // kakao.maps.LatLngBounds(sw, ne);
        }

        const handleDataBodAddr = async() => {

        }

        onMounted(() => {
            handleDataMemAddr();
            handleDataBodAddr();
        });
        

        return {state}
    }
}
</script>

<style lang="scss" scoped>

</style>