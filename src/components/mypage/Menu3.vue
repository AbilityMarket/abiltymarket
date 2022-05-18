<template>
  <div class="main">
    <header>
      <h3>프로필 정보</h3>
      <p>상세한 프로필 정보를 관리할 수 있어요.</p>
    </header>
    <div class="hr"></div>
    <section>
      <div class="left">
        <div class="profile_box">
          <img :src="state.imgUrl" />
          <div
            style="display: flex; align-items: center; justify-content: center"
          >
            <v-btn class="btn" @click="handleImageClick">파일선택</v-btn>
            <input
              @change="handleChangeImage"
              type="file"
              ref="imageFile"
              style="display: none"
            />
          </div>
        </div>
      </div>
      <div class="right">
        <label>이름</label>
        
        <label>닉네임</label>
        <label>휴대전회</label>
        <label>주소</label>
      </div>
    </section>
  </div>
</template>

<script>
import { reactive, ref } from "@vue/reactivity";
export default {
  setup() {
    const imageFile = ref("null");
    const state = reactive({
      imgUrl: require("../../assets/images/디그다.png"),
    });

    const handleImageClick = () => {
      imageFile.value.click();
    };
    const handleChangeImage = (e) => {
      console.log("BoardWirte.vue=>handleImg", e);
      console.log(e.target.files[0]);
      if (e.target.files[0]) {
        state.imgData = e.target.files[0];
        state.imgUrl = URL.createObjectURL(e.target.files[0]);
      } else {
        state.imgData = "";
        state.imgUrl = require("../../assets/images/디그다.png");
      }
    };
    return {
      state,
      imageFile,
      handleImageClick,
      handleChangeImage,
    };
  },
};
</script>

<style scoped src="../../assets/css/info2.css"></style>