<template>
  <div>
    <div class="jncontainer2">
      <div class="procontainer">
        <div class="profile">
          <v-img src="../assets/images/people.png" style="width: 120px" />
        </div>

        <!-- 프로필 이미지 설정 -->
        <div
          style="display: flex; align-items: center; justify-content: center">
          <button class="proimg_btn" @click="handleImage">
            <ion-icon name="camera" class="proicon"> </ion-icon>
            <input
              @change="handleImage2"
              type="file"
              ref="imageFile"
              style="display: none"
            />
          </button>
        </div>
        
        <!-- 닉네임 설정 -->
        <div class="textbox">
          <input type="text" v-model="state.unickname" required />
          <span></span>
          <label>닉네임</label>
        </div>
      </div>

      <!-- 관심사 설정 -->
      <div class="interest">
        <h3>관심사 설정</h3>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon1 />
              <div class="name">생활</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon2 />
              <div class="name">핸드메이드</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon3 />
              <div class="name">시공/조립</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon4 />
              <div class="name">수리</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon5 />
              <div class="name">건강</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon6 />
              <div class="name">운동</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon7 />
              <div class="name">요리</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon8 />
              <div class="name">취미</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon9 />
              <div class="name">IT</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon10 />
              <div class="name">디자인/그래픽</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon11 />
              <div class="name">사무</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon12 />
              <div class="name">비즈니스</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon13 />
              <div class="name">미용</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
              <img :src=state.icon14 />
              <div class="name">심리</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon15 />
              <div class="name">애완동물</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon16 />
              <div class="name">전통</div>
            </div>
          </label>
        </div>

        <div class="incontainer">
          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon17 />
              <div class="name">컨설팅</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon18 />
              <div class="name">공부</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon19 />
              <div class="name">외국어</div>
            </div>
          </label>

          <label class="option_item">
            <input type="checkbox" class="checkbox" />
            <div class="option_inner">
              <div class="mark"></div>
               <img :src=state.icon20 />
              <div class="name">기타</div>
            </div>
          </label>
        </div>

        <button class="btn_join" @click="handleJoin">가입완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "@vue/reactivity";
export default {
  setup() {
    const imageFile = ref(null);
    const state = reactive({
      unickname: "",
      icon1 : require("../assets/images/life_icon.png"),
      icon2 : require("../assets/images/handmade_icon.png"),
      icon3 : require("../assets/images/construction_icon.png"),
      icon4 : require("../assets/images/fix_icon.png"),
      icon5 : require("../assets/images/health_icon.png"),
      icon6 : require("../assets/images/activity_icon.png"),
      icon7 : require("../assets/images/cook_icon.png"),
      icon8 : require("../assets/images/hobby_icon.png"),
      icon9 : require("../assets/images/it_icon.png"),
      icon10 : require("../assets/images/graphic_icon.png"),
      icon11 : require("../assets/images/office_icon.png"),
      icon12 : require("../assets/images/business_icon.png"),
      icon13 : require("../assets/images/beauty_icon.png"),
      icon14 : require("../assets/images/mind_icon.png"),
      icon15 : require("../assets/images/pet_icon.png"),
      icon16 : require("../assets/images/trandition_icon.png"),
      icon17 : require("../assets/images/consulting_icon.png"),
      icon18 : require("../assets/images/study_icon.png"),
      icon19 : require("../assets/images/lang_icon.png"),
      icon20 : require("../assets/images/etc_icon.png"),
      
    });

    const handleImage = () => {
      imageFile.value.click();
    };

    return { state, handleImage, imageFile };
  },
};
</script>

<style scoped src="../assets/css/joinnext.css">


</style>
