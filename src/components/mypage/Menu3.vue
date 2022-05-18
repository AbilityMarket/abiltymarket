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
        <div class="right_detail name">
          <label>이름</label>
          <div class="ddd">
          <input class="right_input" type="text" 
          :value="state.uname" readonly/>
          </div>
        </div>
        <div class="right_detail">
          <label>닉네임</label>
          <div class="ddd">
          <input class="right_input detail2" type="text"
          :value="state.unickname" readonly />
          <v-btn class="right_btn">수정</v-btn>
          </div>
        </div>
        <div class="right_detail" >
          <label>휴대전회</label>
          <div class="ddd">
          <input class="right_input detail2" type="text"
           :value="state.uphone" readonly />
           <v-btn class="right_btn">수정</v-btn>
          </div>
        </div>
        <div class="right_detail">
          <label>주소</label>
          <div class="ddd">
          <input class="right_input detail2" type="text"
           :value="state.uaddress" readonly />
           <v-btn  class="right_btn" @click="showApi">수정</v-btn>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import { reactive, ref } from "@vue/reactivity";
export default {
  setup() {
    const imageFile = ref("null");
    const state = reactive({
      imgUrl: require("../../assets/images/디그다.png"),
      uname: "미리",
      uphone: "010-2553-4586",
      unickname: "아이스아메리카",
      uaddress: "아이스아메리카",
      addr1:'',
			addr2:''
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

    const showApi = async()=>{
			new window.daum.Postcode({
				 oncomplete: async(data) => {
					 	let fullRoadAddr = data.roadAddress;
						let extraRoadAddr = '';
						if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
							extraRoadAddr += data.bname; 
						}
						if(data.buildingName !== '' && data.apartment === 'Y'){
							extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
						}
						if(extraRoadAddr !== ''){ 
							extraRoadAddr = ' (' + extraRoadAddr + ')'; 
						}
						if(fullRoadAddr !== ''){ 
							fullRoadAddr += extraRoadAddr; 
						}
						state.zip = data.zonecode; //5자리 새우편번호 사용 
						state.uaddress = fullRoadAddr;
                        const config = { headers: {Authorization : 'KakaoAK eddc9574385a3fb5f33707a8d3bfcb98'}};
                        const url = 'https://dapi.kakao.com/v2/local/search/address.json?query='+state.uaddress;
                        const response = await axios.get(url,config);
                        console.log(response)
                 }
				 }).open();

		}
    return {
      state,
      imageFile,
      handleImageClick,
      handleChangeImage,
      showApi
    };
  },
};
</script>

<style scoped src="../../assets/css/info2.css"></style>