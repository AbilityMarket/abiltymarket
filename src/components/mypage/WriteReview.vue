<template>
  <div class="main" v-if="state">
    <h3>후기작성</h3>
    <div class="header">
      <div class="profile_box">
        <img :src="state.productImg" />
      </div>
      <div class="bdescribe">
        <h5>{{ state.reviewOne.btitle }}</h5>
        <p>{{ state.reviewOne.bprice }}원</p>
      </div>
    </div>
    <div class="hr"></div>
    <section>
      <p class="desc">별점을 매겨주세요</p>
      <p class="star_box">
        <button v-for="(tmp, idx) of 5" :key="tmp" @click="handleStar(idx)">
          <span class="fa fa-star" :class="{ checked: state.star[idx] }"></span>
        </button>
        <span class="msg" :class="{ checked: state.msg !== '선택해주세요' }">
          {{ state.msg }}
        </span>
      </p>
      <p class="desc">리뷰를 작성해주세요</p>
      <textarea
        v-model="state.revcontent"
        class="text"
        cols="30"
        rows="8"
        maxlength="600"
        placeholder="고객님의 솔직한 리뷰를 남겨주세요."
      ></textarea>

      <p class="desc">이미지 등록</p>
      <div class="img_container">
        <div
          class="img_box"
          @click="clickImgBox(1)"
          ref="img_box1"
        >
          <img
            v-if="state.profileImg1"
            ref="img1"
            :src="state.profileImg1"
            style="width: 100%; height: 100%"
          />
          <div class="close_box">
            <img
              :src="state.close"
              class="close"
              @click="closeImg(1)"
              v-if="state.profileImg1"
            />
          </div>
          <ion-icon class="plusIcon" name="add-outline"></ion-icon>
          <input
            type="file"
            ref="imgFile1"
            hidden
            @change="handleChangeImage($event, 1)"
          />
        </div>

        <div
          class="img_box"
          style="display: none"
          ref="img_box2"
          @click="clickImgBox(2)"
        >
          <img
            v-if="state.profileImg2"
            :src="state.profileImg2"
            style="width: 100%; height: 100%"
          />
          <div class="close_box">
            <img
              :src="state.close"
              @click="closeImg(2)"
              class="close"
              v-if="state.profileImg2"
            />
          </div>
          <ion-icon class="plusIcon" name="add-outline"></ion-icon>
          <input
            type="file"
            ref="imgFile2"
            hidden
            @change="handleChangeImage($event, 2)"
          />
        </div>

        <div
          class="img_box"
          style="display: none"
          ref="img_box3"
          @click="clickImgBox(3)"
        >
          <img
            v-if="state.profileImg3"
            :src="state.profileImg3"
            style="width: 100%; height: 100%"
          />
          <div class="close_box">
            <img
              :src="state.close"
              class="close"
              @click="closeImg(3)"
              v-if="state.profileImg3"
            />
          </div>
          <ion-icon class="plusIcon" name="add-outline"></ion-icon>
          <input
            type="file"
            ref="imgFile3"
            hidden
            @change="handleChangeImage($event, 3)"
          />
        </div>
      </div>
    </section>
    <div class="btn_box">
      <v-btn class="save_btn" @click="handleSaveAction">작성하기</v-btn>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import axios from "axios";
export default {
  props: {
    reviewOne: Object,
  },
  setup(props) {
    const imgFile1 = ref(null);
    const imgFile2 = ref(null);
    const imgFile3 = ref(null);
    const img_box1 = ref(null);
    const img_box2 = ref(null);
    const img_box3 = ref(null);
    const img1 = ref(null);

    const state = reactive({
      reviewOne: props.reviewOne,
      productImg: `/ROOT/api/board/image?bno=${props.reviewOne.bno}`,
      star: [],
      msg: "선택해주세요",
      close: require("../../assets/images/close.png"),
      revcontent: "",
    });
    
    onMounted(() => {
      console.log(state.reviewOne);
    });

    // x버튼 눌렀을 때 이미지
    const closeImg = (no) => {
      console.log(no);
      // 1번만 있을 때
      if (no === 1) {
        state.imgData1 = undefined;
        state.profileImg1 = undefined;
        img_box2.value.style.display = "none";
      }
      if (no === 2) {
        state.imgData2 = undefined;
        state.profileImg2 = undefined;
        img_box3.value.style.display = "none";
      }
      if (no === 3) {
        state.imgData3 = undefined;
        state.profileImg3 = undefined;
      }
    };

    const handleChangeImage = (e, no) => {
      console.log("mypage/info.vue=>handleChangeImage", no);
      console.log(e.target.files[0]);
      if (no === 1) {
        if (e.target.files[0]) {
          img_box2.value.style.display = "flex";
          state.imgData1 = e.target.files[0];
          // 미리보기를 나타내고
          state.profileImg1 = URL.createObjectURL(e.target.files[0]);
          // 이미지2번 스타일 none 되어있는 거 바꾸기
          
          console.log(img_box1.value);
          img_box1.value.removeEventListener("click", clickImgBox);
          img_box1.value.style.cursor = "Auto";
          // img1.value.style.zIndex='999'
          console.log(img1.value);
        }
      } else if (no === 2) {
        if (e.target.files[0]) {
          img_box3.value.style.display = "flex";
          img_box2.value.style.cursor = "Auto";
          state.imgData2 = e.target.files[0];
          state.profileImg2 = URL.createObjectURL(e.target.files[0]);
          
        }
      } else if (no === 3) {
        if (e.target.files[0]) {
          img_box3.value.style.cursor = "Auto";
          state.imgData3 = e.target.files[0];
          state.profileImg3 = URL.createObjectURL(e.target.files[0]);
          
        }
      }

      // else {
      //   state.imgData1 = "";
      //   // state.profileImg = `/ROOT/api/member/image?uid=${state.uid}`;
      // }
      // state.btnToggle = true;
    };

    // +버튼 클릭시 숨겨져있는 파일타입의 인풋 실행됨
    const clickImgBox = (no) => {
      if (no === 1) {
        imgFile1.value.click();
      } else if (no === 2) {
        imgFile2.value.click();
      } else if (no === 3) {
        imgFile3.value.click();
      }
    };

    // 저장하기 누르기
    const handleSaveAction = async () => {
      const url = `/ROOT/api/review/insert?crno=${state.reviewOne.crno}`;
      const headers = {
        "content-type": "application/json",
        token: sessionStorage.getItem("TOKEN"),
      };
      const body = new FormData();
      body.append("revscore", state.revscore);
      body.append("revcontent", state.revcontent);
      const response = await axios.post(url, body, { headers });
      console.log(response);
    };

    const handleStar = (idx) => {
      state.star.fill(false);
      switch (idx) {
        case 4:
          state.star[4] = true;
        case 3:
          state.star[3] = true;
        case 2:
          state.star[2] = true;
        case 1:
          state.star[1] = true;
        case 0:
          state.star[0] = true;
      }
      if (idx === 4) {
        state.msg = "최고예요❤";
      } else if (idx === 3) {
        state.msg = "좋아요";
      } else if (idx === 2) {
        state.msg = "괜찮아요";
      } else if (idx === 1) {
        state.msg = "별로예요";
      } else if (idx === 0) {
        state.msg = "실망이에요";
      }
      state.revscore = Number(idx) + 1;
    };

    return {
      props,
      imgFile1,
      imgFile2,
      imgFile3,
      img_box1,
      img_box2,
      img_box3,
      img1,
      state,
      handleStar,
      handleSaveAction,
      clickImgBox,
      handleChangeImage,
      closeImg,
    };
  },
};
</script>

<style scoped src="../../assets/css/writeReview.css"></style>
