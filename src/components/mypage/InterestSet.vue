<template>
  <div class="main">
    <h3>관심사 설정</h3>
    <p>당신의 관심사를 선택해주세요.</p>
    <!-- <div class="hr"></div> -->
    <div class="main2">
    <div class="interest">
      <div class="incontainer" v-for="n in 4" :key="n">
        <label class="option_item">
          <input type="checkbox" class="checkbox" />
          <div class="option_inner">
            <div class="mark"></div>
            <ion-icon name="home-outline" class="icon"></ion-icon>
            <div class="name">생활</div>
          </div>
        </label>

        <label class="option_item">
          <input type="checkbox" class="checkbox" />
          <div class="option_inner">
            <div class="mark"></div>
            <ion-icon name="construct-outline" class="icon"></ion-icon>
            <div class="name">조립/수리</div>
          </div>
        </label>

        <label class="option_item">
          <input type="checkbox" class="checkbox" />
          <div class="option_inner">
            <div class="mark"></div>
            <ion-icon name="color-palette-outline" class="icon"></ion-icon>
            <div class="name">취미</div>
          </div>
        </label>

        <label class="option_item">
          <input type="checkbox" class="checkbox" />
          <div class="option_inner">
            <div class="mark"></div>
            <ion-icon name="bicycle-outline" class="icon"></ion-icon>
            <div class="name">운동</div>
          </div>
        </label>
        <label class="option_item">
          <input type="checkbox" class="checkbox" />
          <div class="option_inner">
            <div class="mark"></div>
            <ion-icon name="bicycle-outline" class="icon"></ion-icon>
            <div class="name">운동</div>
          </div>
        </label>
      </div>
      <div class="btn_marign">
        <button class="btn_join">설정하기</button>
      </div>
    </div>
    <!-- <section>
      <v-container class="px-0" fluid>
        <v-switch
          v-model="state.model"
          :label="`Switch: ${state.model.toString()}`">
        </v-switch>
      </v-container>
    </section> -->
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  setup() {
    const state = reactive({
      model : false,
    })
    return {
      state
    };
  },
};
</script>

<style scoped src="../../assets/css/interestset.css">
</style>