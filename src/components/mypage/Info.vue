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
          <img :src="state.profileImg" />
          <div class="close_box" @click="clickClose">
            <img :src="state.close" />
          </div>
          <div
            style="display: flex; align-items: center; justify-content: center"
          >
            <v-btn class="btn" :class="{toggle: state.btnToggle}" @click="handleImageClick">파일선택</v-btn>
            <v-btn class="btn" :class="{toggle: !state.btnToggle}" @click="handleImageClick2">선택완료</v-btn>
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
          <v-btn variant="outlined" class="right_btn">수정</v-btn>
          </div>
        </div>
        <div class="right_detail" >
          <label>휴대전화</label>
          <div class="ddd">
          <input class="right_input detail2" type="text"
           :value="state.uphone" readonly />
           <v-btn variant="outlined" class="right_btn">수정</v-btn>
          </div>
        </div>
        <div class="right_detail">
          <label>주소</label>
          <div class="ddd">
          <input class="right_input detail2" type="text"
           :value="state.uaddress" readonly />
           <v-btn variant="outlined" class="right_btn" @click="showApi">수정</v-btn>
          </div>
        </div>
        <v-btn class="save_btn">저장하기</v-btn>
      </div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import {useStore} from 'vuex';
import { reactive, ref } from "@vue/reactivity";
import { onMounted, computed } from '@vue/runtime-core';
export default {
  setup() {
    const store = useStore();
    const imageFile = ref("null");
    const state = reactive({
      imgUrl: require("../../assets/images/디그다.png"),
      close: require("../../assets/images/close.png"),
      uname: "미리",
      uphone: "010-2553-4586",
      unickname: "아이스아메리카",
      uaddress: "아이스아메리카",
      addr1:'',
			addr2:'',
      btnToggle : false,
    });

    // 파일선택 이벤트
    const handleImageClick = () => {
      imageFile.value.click();
    };

    // 선택완료 누르기
    const handleImageClick2 = async() => {
      state.btnToggle = false;
      const url = "/ROOT/api/member/updateimg";
      const headers = {"content-type": "multipart/form-data",
      "token": sessionStorage.getItem("TOKEN")};
      const body = new FormData();
      body.append("file", state.imgData);
      const response = await axios.put(url,body,{headers});
      console.log(response);
      if(response.data.status==200){
        console.log("저장완료")
        store.dispatch('handleMember');
        // console.log(storeUimg)
      }
      
    };

    const storeUimg = computed(() => {
      // console.log("발동!")
      return store.getters.getUimg;
    });

    // 이미지 변할 때 이벤트
    const handleChangeImage = (e) => {
      console.log("mypage/info.vue=>handleChangeImage", e);
      console.log(e.target.files[0]);
      if (e.target.files[0]) {
        state.imgData = e.target.files[0];
        state.profileImg = URL.createObjectURL(e.target.files[0]);
      } else {
        state.imgData = "";
        state.profileImg = state.profileImg = `/ROOT/api/member/image?uid=${state.uid}`;
      }
      state.btnToggle = true;
    };
    
    // 이미지 x 버튼 이벤트
    const clickClose= async()=>{
      state.imgData = "";
      state.profileImg = state.profileImg = `/ROOT/api/member/image?uid=${state.uid}`;
      state.btnToggle = false;
    }

    // 주소 변경 이벤트
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
            console.log(fullRoadAddr)
						state.zip = data.zonecode; //5자리 새우편번호 사용 
						state.uaddress = fullRoadAddr;
                        const config = { headers: {Authorization : 'KakaoAK eddc9574385a3fb5f33707a8d3bfcb98'}};
                        const url = 'https://dapi.kakao.com/v2/local/search/address.json?query='+state.uaddress;
                        const response = await axios.get(url,config);
                        console.log(response)
                 }
				 }).open();
    }

    onMounted(()=>{
      handleData()
    })

    const handleData = async()=>{
      const url = "/ROOT/api/member/selectmember";
      const headers = {"content-type":"application/json", 
      "token" : sessionStorage.getItem("TOKEN")}
      const response = await axios.get(url, {headers});
      console.log(response);
      if(response.data.status ===200){
        state.uid = response.data.uid
        state.uname = response.data.uname
        state.uphone = response.data.uphone
        state.unickname = response.data.unickname
        state.uaddress = response.data.uaddress
        state.profileImg = `/ROOT/api/member/image?uid=${state.uid}`;
      }
    }

		
    return {
      state,
      imageFile,
      handleImageClick,
      handleChangeImage,
      handleImageClick2,
      showApi,
      clickClose,
      storeUimg,
      
    };
  },
};
</script>

<style scoped src="../../assets/css/info2.css"></style>