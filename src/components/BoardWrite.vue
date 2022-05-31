<template>
<div>
  <div class="bwcontainer">
    <div class="writecontainer">
      <div class="leftform">
        <!-- 왼쪽 꾸밈 -->
        <div class="mark">
          <div class="mart_chk"></div>
        </div>
        <!-- 카테고리 -->
        <div class="categorybox">
          <div class="title">
            <h2>당신의 능력을 삽니다.</h2>
          </div>
          <label style="margin-top: 20px">카테고리</label>
          <div class="categorybox_inner">
            <div calss="category">
              <!-- 대분류 -->
              <select class="big_cate">
                <option></option>
                <option
                  v-for="tmp of state.category"
                  :key="tmp"
                  :value="tmp.incategory"
                >
                  {{ tmp.incategory }}
                </option>
              </select>
            </div>
            <ion-icon
              class="arrow_right"
              name="chevron-forward-outline"
            ></ion-icon>
            <!-- 소분류 -->
            <select class="detail_cate">
              <option></option>
              <option v-for="tmp of state.list" :key="tmp" :value="tmp.inname">
                {{ tmp.inname }}
              </option>
            </select>
          </div>
        </div>

        <div class="titlebox">
          <label>제목</label>
          <input type="text" class="title" />
        </div>

        <div class="titlebox">
          <label>지역</label>
          <input type="text" class="title" />
        </div>

        <div class="titlebox">
          <label>기간</label>
          <input type="text" class="title" />
        </div>

        <div class="countbox">
          <label>인원수</label>
          <input type="number" class="count" />
        </div>

        <div class="countbox">
          <label>희망가격</label>
          <input type="number" class="count" />
        </div>
      </div>

      <div class="rightform">
        <div calss="contentbox">
          <label>상세내용</label>
          <textarea class="content" cols="50" rows="14"></textarea>
        </div>

        <div class="imgbox">
          <label>이미지 등록</label>
          <div class="imguploadbox">
            <div
              class="img_upload"
              v-for="(file, index) in state.files"
              :key="index"
            >
              <img :src="file.preview" style="width: 100%; height: 100%" />
              <div class="img_close">
                <img
                  :src="state.close"
                  class="close"
                  @click="closeImg"
                  v-if="state.files[index]"
                  :name="file.number"
                />
              </div>
            </div>

            <div
              class="img_upload"
              @click="clickImgBox"
              v-if="state.files.length !== 3"
            >
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
        </div>
      </div>
    </div>
    <div class="button">
      <button class="btn_write">등록하기</button>
    </div>
  </div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    const imgFile1 = ref(null);
    const files = ref(null);

    const state = reactive({
      files: [],
      uploadImageIndex: 0,
      close: require("../assets/images/close.png"),
    });

    // 카테고리 데이터 받기
    const handleData = async () => {
      const url = "/ROOT/api/interest/select";
      const headers = { "content-type": "application/json" };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.category = response.data.result;
        console.log(state.category);
      }
    };

    const handleChangeImage = (e) => {
      console.log("1", e.target.files[0]);

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

      console.log("2", state.files[0].file);
    };

    // x버튼 눌렀을 때
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

    onMounted(() => {
      handleData();
    });

    return {
      state,
      files,
      imgFile1,
      handleChangeImage,
      closeImg,
      clickImgBox,
    };
  },
};
</script>

<style scoped src="../assets/css/boardwrite.css"></style>
