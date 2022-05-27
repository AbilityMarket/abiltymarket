<template>
    <div class="main" v-if="state">
        <h3>후기작성</h3>
        <div class="header">
            <div class="profile_box">
                <img :src="state.productImg" />
            </div>
            <div class="bdescribe">
                <h5>{{state.reviewOne.btitle}}</h5>
                <p>{{state.reviewOne.bprice}}원</p>
            </div>
        </div>
        <div class="hr"></div>
        <section>
            <p>별점을 매겨주세요</p>
            
            <p>
            <ion-icon name="star-outline"></ion-icon>
            <ion-icon name="star-outline"></ion-icon>
            <ion-icon name="star-outline"></ion-icon>
            <ion-icon name="star-outline"></ion-icon>
            <ion-icon name="star-outline"></ion-icon>
            </p>
        </section>
        <!-- {{state.reviewOne}} -->
    </div>
</template>

<script>
import { reactive } from '@vue/reactivity';
import { onMounted } from '@vue/runtime-core';
import axios from 'axios';
export default {
    props:{
        reviewOne:Object
    },
    setup (props) {
        const state = reactive({
            reviewOne: props.reviewOne,
            productImg : `/ROOT/api/board/image?bno=${props.reviewOne.bno}`,
        })
        onMounted(()=>{
            console.log(state.reviewOne);
            handleData();
        })

        const handleData = async ()=>{
            // const url= "";
            // const headers = {"content-type": "application/json"};
            // const response = await axios.get(url, {headers});
            // console.log(response);
        }

        return {
            props,
            state,
        }
    }
}
</script>

<style scoped src= "../../assets/css/writeReview.css">

</style>