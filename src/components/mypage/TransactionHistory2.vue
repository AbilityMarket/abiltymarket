<template>
  <div class="main">
    <h3>내역</h3>
    <p>마켓에서 구매한 상품 및 거래 내용을 관리할 수 있어요</p>
    <header>
      <div class="header_1">
        <div class="header_icon"><ion-icon name="apps-outline"></ion-icon></div>
        <div class="count">
        <p>전체</p>
        <p>0건</p>
        </div>
      </div>
        
      <div class="header_2">
        <div class="header_icon"><ion-icon name="warning-outline"></ion-icon></div>
        <div class="count">
        <p>진행중</p>
        <p>0건</p>
        </div>
      </div>
      <div class="header_3">
        <div class="header_icon"><ion-icon name="hourglass-outline"></ion-icon></div>
        <div class="count">
        <p>예약중</p>
        <p>0건</p>
        </div>
      </div>
      <div class="header_4">
        <div class="header_icon"><ion-icon name="chevron-down-circle-outline"></ion-icon></div>
        <div class="count">
        <p>종료</p>
        <p>0건</p>
        </div>
      </div>
    </header>

<!-- 내역이 없는 경우 -->
    <section v-if="state.empty">
      <img :src="state.oops" style="width:40px" />
      <p class="p1">마켓 구매 내역이 없습니다.</p>
      <p class="p2">나에게 맞는 재능을 찾아보세요.</p>
      <v-btn class="btn"><router-link class="btn_a" to="/trade2">마켓보러 가기</router-link></v-btn>
    </section>

    <!-- 내역이 있는 경우 -->
    <div class="not_empty" v-if="!state.empty">
      <div class="box"></div>
    </div>

  </div>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  setup() {
    const state = reactive({
      oops : require("../../assets/images/oops.png"),
      empty : true,
    })
    return {
      state
    };
  },
};
</script>

<style scoped src= "../../assets/css/transactionHistory.css">
</style>