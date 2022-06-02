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
              <h2>능력을</h2>

              <!-- 구매인지 판매인지 -->
              <div class="select">
                <select class="buyorsale" v-model="state.brole">
                  <option disabled value="">삽니다/팝니다</option>
                  <option value="1">삽니다</option>
                  <option value="2">팝니다</option>
                </select>
                {{ state.brole }}
              </div>
            </div>

            <label style="margin-top: 10px">카테고리</label>
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
                <option
                  v-for="tmp of state.list"
                  :key="tmp"
                  :value="tmp.inname"
                >
                  {{ tmp.inname }}
                </option>
              </select>
            </div>
          </div>

          <div class="titlebox">
            <label>제목</label>
            <input type="text" class="title" v-model="state.btitle" />
          </div>

          <div class="titlebox">
            <label>지역</label>
            <div class="addressbox">
              <input
                type="text"
                v-model="state.baddress"
                style="width: 270px"
              />
              <button class="btn_addr" @click="showApi">설정</button>
            </div>
          </div>

          <div class="row">
            <div class="countbox">
              <label>인원수</label>
              <input
                type="number"
                class="count"
                v-model="state.bcount"
                style="width: 160px"
              />
            </div>

            <div class="countbox">
              <label>희망가격</label>
              <input
                type="number"
                class="count"
                v-model="state.bprice"
                style="width: 160px"
              />
            </div>
          </div>

          <div class="titlebox">
            <label>기간</label>
            <v-date-picker v-model="state.date" class="date" is-range />
          </div>
        </div>
        {{ state.date }}
        <div class="rightform">
          <div calss="contentbox">
            <label>상세내용</label>
            <textarea
              class="content"
              cols="50"
              rows="16"
              v-model="state.bcontent"
            ></textarea>
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
        <button class="btn_write" @click="handleInsert">등록하기</button>
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
      token: sessionStorage.getItem("TOKEN"),

      files: [],
      uploadImageIndex: 0,
      close: require("../assets/images/close.png"),

      baddress: "",
      brole: "",
      btitle: "",
      bcontent: "",
      bprice: "",
      imgData: "",
      blatitude: "",
      blongitude: "",

      date: "",
    });

    // 글쓰기
    const handleInsert = async () => {
      const url = `/ROOT/api/board/insert`;
      const headers = {
        "Content-Type": "multipart/form-data",
        token: state.token,
      };
      const body = new FormData(); //이미지가 있는 경우
      body.append("btitle", state.btitle);
      body.append("brole", state.brole);
      body.append("bcontent", state.bcontent);
      body.append("bprice", state.bprice);
      body.append("baddress", state.baddress);
      body.append("blatitude", state.blatitude);
      body.append("blongitude", state.blongitude);
      body.append("bstartdate", state.date.start);
      body.append("benddate", state.date.end);
      body.append("file", state.files[0].file);
      const response = await axios.post(url, body, { headers });
      console.log(response);
      handleInterest();
    };

    const handleInterest = async () => {
      const url = `/ROOT/api/board/insertBnoTag?incode=${state.incode}`;
      const headers = {
        "Content-Type": "multipart/form-data",
        token: state.token
        }
      const body = new FormData();
      const response = await axios.post(url,body, {headers})
      console.log(response);
      if(response.data.status===200){
        alert("관심사 등록완료");
      }
        
      };




    // 서브 이미지 등록
    const insertImg = async () => {
      if (state.files.length < 2) {
        return;
      }
      const url = `/ROOT/api/boardimg/insert`;
      const headers = {
        "Content-Type": "multipart/form-data",
        token: state.token,
      };
      const body = new FormData(); //이미지가 있는 경우
      for (let i = 1; i < state.files.length; i++) {
        body.append("file", state.files[i].file);
      }

      const response = await axios.post(url, body, { headers });
      console.log(response);
    };

    // 주소 설정 api
    const showApi = async () => {
      new window.daum.Postcode({
        oncomplete: async (data) => {
          let fullRoadAddr = data.roadAddress;
          let extraRoadAddr = "";
          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraRoadAddr +=
              extraRoadAddr !== ""
                ? ", " + data.buildingName
                : data.buildingName;
          }
          if (extraRoadAddr !== "") {
            extraRoadAddr = " (" + extraRoadAddr + ")";
          }
          if (fullRoadAddr !== "") {
            fullRoadAddr += extraRoadAddr;
          }
          console.log(fullRoadAddr);
          state.zip = data.zonecode; //5자리 새우편번호 사용
          state.uaddress = fullRoadAddr;
          const config = {
            headers: {
              Authorization: "KakaoAK eddc9574385a3fb5f33707a8d3bfcb98",
            },
          };
          const url =
            "https://dapi.kakao.com/v2/local/search/address.json?query=" +
            state.uaddress;
          const response = await axios.get(url, config);
          console.log(response);
          console.log(response.data.documents[0].x);
          state.baddress = response.data.documents[0].address_name;
          state.blongitude = response.data.documents[0].x;
          state.blatitude = response.data.documents[0].y;
        },
      }).open();
    };

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
      handleInsert,
      showApi,
      insertImg,
    };
  },
};
</script>

<style scoped>
@import "v-calendar/dist/style.css";

/* 달력 */
.date {
  width: 335px;
  height: 240px;
  border-radius: 10px;
  border: 1px solid #d8ebff;
}
</style>

<style scoped src="../assets/css/boardwrite.css"></style>
