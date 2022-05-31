<template>

    <div class="joincontainer">

    <div class="joinaside">
      <div class="logo" >
      <v-img src="../assets/images/logo_image3.jpg" style="width:370px;margin-top:180px;margin-right:120px;"/>
    </div>
    </div>
    
      <div class="joininfo">
        <h2>회원가입</h2>
  
    <div class="joinbox" style="margin-top:10px;">

      <div class="textbox">
        <input type="text" v-model="state.uid" required />
        <span></span>
        <label>아이디</label>
        <button class="btn_idcheck" @click="idCheck">중복확인</button>
        </div>
      

      <div class="textbox">
        <input type="password" v-model="state.upw" required />
        <span></span>
        <label>비밀번호</label>
      </div>
      <div class="textbox">
        <input type="password" v-model="state.upw" required />
        <span></span>
        <label>비밀번호 확인</label>
      </div>

      <div class="textbox">
        <input type="text" v-model="state.uname" required />
        <span></span>
        <label>이름</label>
      </div>

      <div class="textbox">
        <input type="text" v-model="state.uphone" required />
        <span></span>
        <label>전화번호</label>
      </div>

       <div class="textbox">
        <input type="text" v-model="state.uaddress" required />
        <span class="span_addr"></span>
        <label>주소</label>
        <button class="btn_addr" @click="showApi">설정</button>
      </div>
    
        <div class="simplejoin" style="margin-top: 40px;">
      <dt class="m_hide"><span>SNS 계정으로 간편 가입</span></dt>

    <div class="sns">
      <div class="snsitem">
        <button class="kakao">
          <v-img
            src="../assets/images/kakao.png"
            style="width: 35px; margin-left: 7px"
          />
        </button>
      </div>
      <div class="snsitem">
        <button class="google">
          <v-img
            src="../assets/images/google.png"
            style="width: 37px; margin-left: 6px"
          />
        </button>
      </div>
      <div class="snsitem">
        <button class="naver">
          <v-img
            src="../assets/images/naver.png"
            style="width: 37px; margin-left: 6px"
          />
        </button>
      </div>
    </div>
    </div>
    <router-link to="/joinnext">
    <button class="btn_next" @click="handleNext">다음으로</button></router-link>
    </div>
    </div>
    </div>
  
  
</template>

<script>
import axios from 'axios';
import { reactive } from '@vue/reactivity';
export default {
  setup() {
 
    const state = reactive ({
      uid : '',
      upw : '',
      uname : '',
      uphone : '',
      uaddress : '',
     });

     const idCheck = async () => {
      const url = `/ROOT/api/member/check?uid=${state.uid}`;
      const headers = {
        "Content-Type": "application/json"};
        
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status === 0) {
        alert('아이디 사용이 가능합니다.')
      }
      else if (response.data.status === 200) {
        alert('이미 사용중인 아이디입니다.')
      }
    };

      const handleNext = async () => {
      const url = `/ROOT/api/member/join`;
      const headers = { "Content-Type": "form-data" };
      const body = new FormData();
        body.append("uid", state.uid);
        body.append("upw", state.upw);
        body.append("uname", state.uname);
        body.append("uphone", state.uphone);
        body.append("uaddress", state.uaddress);
      
      const response = await axios.post(url, body, { headers });
      console.log(response);
      if(response.data.status===200){
        router.push({name:'JoinNext'});
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
            console.log(fullRoadAddr)
						state.zip = data.zonecode; //5자리 새우편번호 사용 
						state.uaddress = fullRoadAddr;
                        const config = { headers: {Authorization : 'KakaoAK eddc9574385a3fb5f33707a8d3bfcb98'}};
                        const url = 'https://dapi.kakao.com/v2/local/search/address.json?query='+state.uaddress;
                        const response = await axios.get(url,config);
                        console.log(response)
                        console.log(response.data.documents[0].x)
                        state.ulongitude = response.data.documents[0].x
                        state.ulatitude= response.data.documents[0].y 
                 }
				 }).open();
    }

     return {state, handleNext, idCheck, showApi};

  }
};
</script>

<style scoped src="../assets/css/join.css">



</style>
