<template>
  <div class="dtcontainer" v-if="state.item" style="min-width: 1168px">
    <div class="leftside">
      <!-- 경로 -->
      <div class="category_list">
        <a href="#"><span>생활</span></a>
        <ion-icon
          name="chevron-forward-outline"
          style="font-size: 22px; color: #979797; margin-bottom: 3px"
        ></ion-icon>
        <a href="#"><span>청소</span></a>
      </div>

      <!-- 사진 -->
      <div class="leftslide">
        <vueper-slides
          class="no-shadow"
          ref="vueperslides1"
          :touchable="false"
          fade
          :autoplay="false"
          :bullets="false"
          @slide="
            $refs.vueperslides2.goToSlide($event.currentSlide.index, {
              emit: false,
            })
          "
          fixed-height="450px"
        >
          <vueper-slide
            v-for="(slide, i) in state.slides"
            :key="i"
            :image="slide.image"
          >
          </vueper-slide>
        </vueper-slides>

        <vueper-slides
          class="no-shadow thumbnails"
          ref="vueperslides2"
          @slide="
            $refs.vueperslides1.goToSlide($event.currentSlide.index, {
              emit: false,
            })
          "
          :visible-slides="state.slides.length"
          fixed-height="130px"
          :bullets="false"
          :touchable="false"
          :gap="2.5"
          :arrows="false"
        >
          <vueper-slide
            v-for="(slide, i) in state.slides"
            :key="i"
            :image="slide.image"
            @click="$refs.vueperslides2.goToSlide(i)"
          >
          </vueper-slide>
        </vueper-slides>
      </div>

      <!-- 탭메뉴   -->
      <div class="tab">
        <input id="tab1" type="radio" name="tabs" checked />
        <label for="tab1">상세정보</label>
        <input id="tab2" type="radio" name="tabs" />
        <label for="tab2">문의&댓글(n)</label>
        <input id="tab3" type="radio" name="tabs" />
        <label for="tab3">후기(n)</label>

        <section id="content1">
          <p>상세정보</p>
        </section>

        <section id="content2">
          <comment></comment>
        </section>
        <section id="content3">
          <review></review>
        </section>
      </div>
    </div>

    <!-- 오른쪽 -->
    <div class="rightside">
      <!-- 능력 -->
      <div class="product">
        <div class="pro_title">
          <h3>{{state.item.btitle}}</h3>
        </div>

        <!-- 능력정보   -->
        <div class="pro_inner">
          <div class="pro_text">
            {{state.item.bcontent}}
          </div>
          <div class="condition">
            <div class="con_inner">
              <ion-icon
                name="location-outline"
                style="font-size: 25px; color: #3476d8"
              ></ion-icon>
              <div>{{state.item.baddress}}</div>
            </div>
            <div class="con_inner">
              <ion-icon
                name="checkmark-outline"
                style="font-size: 25px; color: #3476d8"
              ></ion-icon
              ><span>시작일 : </span>
              <div class="count" style="margin-left: 5px">{{state.item.bstartdate.split("T")[0]}}</div>
              <ion-icon
                name="checkmark-outline"
                style="font-size: 25px; color: #3476d8; margin-left: 20px"
              ></ion-icon
              ><span>종료일 : </span>
              <div class="count" style="margin-left: 5px">{{state.item.benddate.split("T")[0]}}</div>
            </div>
            <div class="con_inner">
              <ion-icon
                name="checkmark-outline"
                style="font-size: 25px; color: #3476d8"
              ></ion-icon
              ><span>참가 가능 인원 : </span>
              <div class="count" style="margin-left: 5px">n명</div>
            </div>
            
     
          </div>

          <!-- 가격 -->
          <div class="pricebox">
            <span>희망가격</span>
            <div class="price">{{state.item.bprice}}</div>
          </div>
        </div>
      </div>

      <!-- 글쓴이 정보 -->
      <div class="profilebox">
        <div class="profile_pic"></div>

        <div class="profile">
          <div class="nickname">
            <span style="font-size: 13px; color: #979797">닉네임</span>
            <p>루피</p>
          </div>

          <div class="grade">
            <span style="font-size: 13px; color: #979797">등급</span>
            <div>
              <v-img
                src="../assets/images/medal1.png"
                style="width: 25px; height: 25px"
              ></v-img>
            </div>
          </div>

          <div class="rating">
            <span style="font-size: 13px; color: #979797">평점</span>
            <p>4.5</p>
          </div>

          <div class="abcount">
            <span style="font-size: 13px; color: #979797">활동개수</span>
            <p>10</p>
          </div>
        </div>
      </div>

      <!-- 달력 -->
      <v-date-picker
        v-model="state.date"
        class="date"
        :available-dates="{
          start: state.dates.start,
          end: state.dates.end,
        }"
      />
      <!-- 채팅으로 거래하기 -->
      <div class="likechat">
        <button class="btn_like" @click="handleLike">
          <ion-icon v-if="!state.heartClicked"
            name="heart-outline"
            ref="heart"
            style="font-size: 21px; margin-bottom: 4px"
          ></ion-icon
          >
          <ion-icon v-if="state.heartClicked"
            name="heart"
            ref="heart"
            style="font-size: 21px; margin-bottom: 4px"
          ></ion-icon
          >
          <span>{{ state.like }}</span>
        </button>
        <button class="btn_chat">채팅으로 거래하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "vue";
import { VueperSlides, VueperSlide } from "vueperslides";
import Comment from "./detail/Comment.vue";
import Review from "./detail/Review.vue";
import axios from "axios";

export default {
  components: { VueperSlides, VueperSlide, Comment, Review },
  setup() {
    const heart = ref(null);
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),

      slides: [
        // { image: `` },
        // { image: require("../assets/images/clean2.jpg") },
        // { image: require("../assets/images/clean3.jpg") },
      ],
      heartClicked : false,
      bno: 18,
      like: 0,
      date: "",
      dates: {
        // start: new Date(2022, 4, 21),
        // end: new Date(),
      },
    });

    // 게시글 받아오기
    const handleData = async ()=> {
      const url = `/ROOT/api/board/selectone?bno=${state.bno}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        console.log(response.data.result);
        state.item = response.data.result;
        state.dates.start= response.data.result.bstartdate;
        state.dates.end= response.data.result.benddate;
        state.slides.push({image:`/ROOT/api/board/image?bno=${state.item.bno}`})
        selectSubImage()
        // state.btitle = response.data.btitle
        // state.bprice = response.data.bprice
        // state.bcontent = response.data.content
        // state.baddress = response.data.baddress
        // state.bcount = response.data.bcount
        // state.bimage = `/ROOT/api/board/image?bno=${state.bno}`;
      }
    };

    // 서브이미지 조회하기
    const selectSubImage = async()=>{
      const url = `/ROOT/api/boardimg/select?bno=${state.item.bno}`;
      const headers = {"content-type": "application/json"};
      const response = await axios.get(url,{headers})
      console.log(response)
      if(response.data.status===200){
        // state.subImageList = response.data.list;
        for(let i =0; i< response.data.list.length; i++){
          state.slides.push({image: response.data.list[i]})
        }
      }
    }

    const handleCountLike = async () => {
      const url = `/ROOT/api/bolike/countlike?bno=${state.bno}`;
      const headers = {
        "Content-Type": "application/json",
        token: state.token,
      };
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        console.log(response.data.result);
        state.like = response.data.result;
      }
    };

    // 하트 모양 바꾸기
    const handleHeart = async () => {
      const url = `/ROOT/api/bolike/likeChk?bno=${state.bno}`;
      const headers = {
        "Content-Type": "application/json",
        token: state.token,
      };
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        state.heartClicked = true;
      }
    };

    // 좋아요
    const handleLike = async () => {
      const url = `/ROOT/api/bolike/like?bno=${state.bno}`;
      const headers = {
        "Content-Type": "application/json",
        token: state.token,
      };
      const body = {};
      const response = await axios.post(url, body, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        state.heartClicked = true;
        console.log(response.data.result);
        state.like += 1;
      } else if (response.data.status === 0) {
        state.heartClicked = false;
        state.like -= 1;
      }
    };
    onMounted(() => {
      handleData();
     handleCountLike();
      handleHeart();
    });
    return {
      state,
      handleLike,
      heart,
      // selectSubImage,
      
    };
  },
};
</script>

<style scoped>
@import "v-calendar/dist/style.css";

/* 달력 */
.date {
  width: 467px;
  height: 250px;
  border-radius: 10px;
  margin-top: 10px;
  border: 1px solid #3476d8;
}
</style>

<style scoped src="../assets/css/detail.css"></style>
