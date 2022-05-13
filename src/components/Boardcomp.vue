<template>
  <div>
    <div class="main">
			<div class="d-flex mb-6" style="margin-top:30px;">
        <div><h3>도와주세요</h3></div>
    </div>
      <div class="gridbox">
        <div class="helpme" v-for="n in 12" :key="n">
          <ul class="helpmelist" style="margin-right: 10px; margin-left: 10px">
            <li>
              <a href="#">
                <div class="profile">
                  <div class="profile-item">
                    <v-img
                      src="../assets/images/user.png"
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
                      src="../assets/images/check.png"
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
                      src="../assets/images/location.png"
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
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { onMounted, computed } from "@vue/runtime-core";
export default {
  // props:{
  //     selectcate : String,
  //     selectname : String
  // },
  setup() {
    const store = useStore();
    // console.log(props)
    onMounted(() => {});

    const selectcategory = computed(() => {
      return store.getters.getSelectcategory;
    });
    const selectcategoryname = computed(() => {
      return store.getters.getSelectcategoryname;
    });
    console.log(selectcategory, selectcategoryname);
    return {
      selectcategory,
      selectcategoryname,
    };
  },
};
</script>

<style scoped src="../assets/css/trade2-board.css">
</style>