<template>
  <div class="main">
    <h3>찜 목록</h3>
    <section>
      <!-- 게시판 -->
      <div class="main2">
        <div class="d-flex mb-6" style="margin-top: 30px"></div>
        <div class="gridbox">
          <div class="helpme" v-for="n in 4" :key="n">
            <div class="close_box">
              <img :src="state.close" />
            </div>
            <ul
              class="helpmelist"
              style="margin-right: 10px; margin-left: 10px"
            >
              <li>
                <a href="#">
                  <div class="profile">
                    <div class="profile-item">
                      <v-img
                        src="../../assets/images/user.png"
                        style="width: 25px; height: 25px"
                      />
                    </div>
                    <p class="nickname">그린데이즈</p>
                  </div>
                </a>
                <a href="#" class="imghover">
                  <div class="wrphover">
                    <div class="thumbnail"></div>
                    <span class="new">NEW</span>
                  </div>
                  <div class="profilebottom">
                    <div class="check">
                      <v-img
                        src="../../assets/images/check.png"
                        style="width: 30px; height: 30px"
                      />
                    </div>
                    <div class="profilebottom-item">
                      <p>사진 보정하는 능력을 삽니다.</p>
                    </div>
                  </div>

                  <div class="location">
                    <div class="location-item">
                      <v-img
                        src="../../assets/images/location.png"
                        style="width: 20px; height: 20px; margin-bottom: 40px"
                      />
                    </div>
                    <div class="address"><p>부산시 부산진구</p></div>
                  </div>
                </a>
              </li>
            </ul>
          </div>
        </div>

        <!-- 페이지네이션 -->
        <div class="pagenation">
          <span v-for="tmp of state.page" :key="tmp">
            <div
              @click="clickpage(tmp)"
              style="display: inline-block; cursor: pointer; margin-left: 5px"
              :href="`/ROOT/api/trade/helpMe?page=${state.page}&brole=${
                state.brole
              }&incategory=${state.selectcategory.split(' ')[0]}&inname=${
                state.selectcategoryname
              }`"
            >
              {{ tmp }}
            </div>
          </span>
        </div>
      </div>
    </section>
  </div>
</template>

<script>

import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    const state = reactive({
      close: require("../../assets/images/close.png"),

    });

    
    onMounted(() => {
    });

    return {
      state,
    };
  },
};
</script>

<style scoped src="../../assets/css/list2.css"></style>