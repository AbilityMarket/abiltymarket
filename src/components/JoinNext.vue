<template>
  <div v-if="state.list">

    <div class="jncontainer2">
      <div class="inter_title">
          <h4>* 프로필 사진 설정</h4>
        </div>

      <div class="procontainer">
        <div class="profile">
        
          <img v-if="!state.profileImg"  :src="state.defaultImg" style="width: 50%; height: 50%;object-fit: object;" />
          <img v-if="state.profileImg" :src="state.profileImg" style="width: 100%; height: 100%; border-radius:100%; object; margin-bottom:5px;" />
        </div>

        <!-- 프로필 이미지 설정 -->
        <div
          style="display: flex; align-items: center; justify-content: center"
        >
          <button class="proimg_btn" @click="handleImage">
            <ion-icon name="camera" class="proicon"> </ion-icon>
            <input
              @change="handleChangeImage"
              type="file"
              ref="imageFile"
              style="display: none"
            />
          </button>
        </div>
      </div>

      <!-- 관심사 설정 -->
      <div class="interest">
        <div class="inter_title">
          <h4>* 관심사 설정</h4>
        </div>
        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[0].incode" v-model="state.incode"/>
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon1" />
              <!-- <input type="hidden" v-model="incode" value="2"> -->
              <div class="name">생활</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[1].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon2" />
              <div class="name">핸드메이드</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[2].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon3" />
              <div class="name">시공/조립</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[3].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon4" />
              <div class="name">수리</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[4].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon5" />
              <div class="name">건강</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[5].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon6" />
              <div class="name">운동</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[6].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon7" />
              <div class="name">요리</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[7].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon8" />
              <div class="name">취미</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[8].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon9" />
              <div class="name">IT</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[9].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon10" />
              <div class="name">디자인/그래픽</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[10].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon11" />
              <div class="name">사무</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[11].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon12" />
              <div class="name">비즈니스</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[12].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon13" />
              <div class="name">미용</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[13].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon14" />
              <div class="name">심리</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[14].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon15" />
              <div class="name">애완동물</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[15].incode" v-model="state.incode"  />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon16" />
              <div class="name">전통</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[16].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon17" />
              <div class="name">컨설팅</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[17].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon18" />
              <div class="name">공부</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[18].incode" v-model="state.incode"/>
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon19" />
              <div class="name">외국어</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" :value="state.list[19].incode" v-model="state.incode" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src="state.icon20" />
              <div class="name">기타</div>
            </div>
          </label>
        </div>
        <div class="btn">
          <button class="btn_join" @click="handleJoin">가입완료</button>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script>
import {useRouter} from 'vue-router';
import {useRoute} from 'vue-router';
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
import { onMounted } from '@vue/runtime-core';
export default {
  setup() {
    const router = useRouter();
    const route = useRoute();
    const imageFile = ref(null);

    const state = reactive({
      unickname: "",
      icon1: require("../assets/images/life_icon.png"),
      icon2: require("../assets/images/handmade_icon.png"),
      icon3: require("../assets/images/construction_icon.png"),
      icon4: require("../assets/images/fix_icon.png"),
      icon5: require("../assets/images/health_icon.png"),
      icon6: require("../assets/images/activity_icon.png"),
      icon7: require("../assets/images/cook_icon.png"),
      icon8: require("../assets/images/hobby_icon.png"),
      icon9: require("../assets/images/it_icon.png"),
      icon10: require("../assets/images/graphic_icon.png"),
      icon11: require("../assets/images/office_icon.png"),
      icon12: require("../assets/images/business_icon.png"),
      icon13: require("../assets/images/beauty_icon.png"),
      icon14: require("../assets/images/mind_icon.png"),
      icon15: require("../assets/images/pet_icon.png"),
      icon16: require("../assets/images/trandition_icon.png"),
      icon17: require("../assets/images/consulting_icon.png"),
      icon18: require("../assets/images/study_icon.png"),
      icon19: require("../assets/images/lang_icon.png"),
      icon20: require("../assets/images/etc_icon.png"),
      defaultImg: require("../assets/images/people.png"),
      incode: [],
      uid : route.query.uid,
      imgData: "",
    });
    

    const handleImage = () => {
      imageFile.value.click();
    };

    const handleChangeImage = (e) => {
      console.log("handleChangeImage", e);
      if (e.target.files[0]) {
        state.imgData = e.target.files[0];
        state.profileImg = URL.createObjectURL(e.target.files[0]);
      } else {
        state.imgData = "";
        state.profileImg = `../assets/images/people.png`;
      }
    };

    const handleData = async() =>{
     const url = `/ROOT/api/interest/select`;
      const headers = { "Content-Type":"multipart/form-data" };
      // const body = new FormData();
      // body.append("file", state.imgData);
      
      const response = await axios.get(url, { headers });
      console.log(response);
      if(response.data.status ===200){
        state.list = response.data.result;
        // consol
      }
    }

    const handleJoin = async () => {
      if(state.imgData ===""){
        alert("이미지를 선택해주세요")
        return;
      }
      const url = `/ROOT/api/member/joinnext?uid=${state.uid}`;
      const headers = { "Content-Type":"multipart/form-data" };
      const body = new FormData();
      body.append("file", state.imgData);
      
      const response = await axios.post(url, body, { headers });
      console.log("111");
      console.log(response);
      if (response.data.status === 200) {
        insertInterest();  
      }
      
    };

    const insertInterest = async()=>{
      const url = `/ROOT/api/meminterest/insert?uid=azaz`;
      const headers = {"content-type": "multipart-form/data"};
      const body = new FormData();
      // body.append("uid", state.uid);
      for(let i =0; i< state.incode.length; i++){
        body.append('incode', state.incode[i]);
      }
      const response = await axios.post(url, body, {headers})
      console.log("2222")
      console.log(response)
      if(response.data.status===200){
        alert("회원가입이 완료되었습니다.")
        router.push({name:"Home"});
      }
    }

    onMounted(()=>{
      handleData();
    })

    return { state, handleImage, handleJoin, imageFile, handleChangeImage };
  },
};
</script>

<style scoped src="../assets/css/joinnext.css"></style>
