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
        <div class="img_box" v-for="(file, index) in state.files" :key="index">
          <img
            :src="file.preview"
            style="width: 100%; height: 100%"
          />
          <div class="close_box">
            <img
              :src="state.close"
              class="close"
              @click="closeImg"
              v-if="state.files[index]"
              :name="file.number"
            />
          </div>
          <ion-icon class="plusIcon" name="add-outline"></ion-icon>
          <!-- <input
            type="file"
            ref="files"
            hidden
            multiple
            @change="handleChangeImage($event, 1)"
          /> -->
        </div>
        <div class="img_box" @click="clickImgBox" v-if="state.files.length!==3">
          <ion-icon class="plusIcon" name="add-outline"></ion-icon>
          <input
            type="file"
            ref="files"
            hidden
            multiple
            @change="handleChangeImage($event, 1)"
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
    const files = ref(null);

    const state = reactive({
      files: [],
      filesPreview: [],
      uploadImageIndex: 0,
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

    const handleChangeImage = (e) => {
      console.log("1",e.target.files[0]);

      let num = -1;
      for (let i = 0; i < files.value.files.length; i++) {
        console.log(state.uploadImageIndex);
        state.files = [
          ...state.files,
          //이미지 업로드
          {
            //실제 파일
            file: files.value.files[i],
            //이미지 프리뷰
            preview: URL.createObjectURL(files.value.files[i]),
            //삭제및 관리를 위한 number
            number: i + state.uploadImageIndex,
          },
        ];
        num = i;
      }
      state.uploadImageIndex = state.uploadImageIndex + num + 1;

      console.log("2",state.files[0].file);
    };

    // x버튼 눌렀을 때 이미지
    const closeImg = (e) => {
      const name = e.target.getAttribute("name");
      state.files = state.files.filter((data) => data.number !== Number(name));
    };

    // +버튼 클릭시 숨겨져있는 파일타입의 인풋 실행됨
    const clickImgBox = (no) => {
      files.value.click();
      if (no === 1) {
        files.value.click();
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
      if(response.data.status===200 && state.files.length > 0){
        saveImage();
      }
    };

    // 리뷰 이미지 저장하기
    const saveImage = async()=>{
      const url = `/ROOT/api/reviewimage/insert?crno=${state.reviewOne.crno}`;
      const headers = {
        "content-type": "multipart/form-data",
        token: sessionStorage.getItem("TOKEN"),
      };
      const body = new FormData();
      for(let i = 0; i< state.files.length; i++){
        body.append("files", state.files[i].file)
      }
      
      const response = await axios.post(url, body, { headers });
      console.log(response);
    }

    // 평점 관리
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
      files,
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
