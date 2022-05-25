<template>
  <v-app>
    <v-main>
      <div class="dtcontainer">
        <div class="leftside">
          <div class="category_list">
            <a href="#"><span>생활</span></a>
            <ion-icon
              name="chevron-forward-outline"
              style="font-size: 22px; color: #979797; margin-bottom: 3px"
            ></ion-icon>
            <a href="#"><span>청소</span></a>
          </div>

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
            <label for="tab2">문의&댓글</label>
            <input id="tab3" type="radio" name="tabs" />
            <label for="tab3">후기</label>

            <section id="content1">
              <p>상세정보</p>
            </section>

            <section id="content2">
              <menu-2></menu-2>
            </section>
            <section id="content3">
              <p>후기</p>
            </section>
          </div>
        </div>
        <!-- 오른쪽 -->
        <div class="rightside">
          <div class="product">
            <div class="pro_title">
              <h3>생활 청소 능력자입니다.</h3>
            </div>
            <div class="pro_inner">
              <div class="pro_text">
                전문가에게 맡기긴 애매하고 사소한 청소 꿀팁들을 전수해요. 신혼인
                가정이나 자취하는 학생들에게 도움을 주고싶어요.
              </div>
              <div class="condition">
                <div class="con_inner">
                  <ion-icon
                    name="location-outline"
                    style="font-size: 25px; color: #3476d8"
                  ></ion-icon>
                  <div>부산시 부산진구</div>
                </div>
                <div class="con_inner">
                  <ion-icon
                    name="checkmark-outline"
                    style="font-size: 25px; color: #3476d8"
                  ></ion-icon
                  ><span>시작일 : </span>
                  <div class="count" style="margin-left: 5px">0000-00-00</div>
                  <ion-icon
                    name="checkmark-outline"
                    style="font-size: 25px; color: #3476d8; margin-left: 20px"
                  ></ion-icon
                  ><span>종료일 : </span>
                  <div class="count" style="margin-left: 5px">0000-00-00</div>
                </div>
                <div class="con_inner">
                  <ion-icon
                    name="checkmark-outline"
                    style="font-size: 25px; color: #3476d8"
                  ></ion-icon
                  ><span>참가 가능 인원 : </span>
                  <div class="count" style="margin-left: 5px">n명</div>
                </div>
                <div class="con_inner">
                  <ion-icon
                    name="pricetag-outline"
                    style="font-size: 22px; color: #3476d8"
                  ></ion-icon>
                  <div class="count" style="margin-left: 5px">
                    #청소 #정리 #생활 #세탁
                  </div>
                </div>
              </div>
              <div class="pricebox">
                <span>희망가격</span>
                <div class="price">30,000~</div>
              </div>
            </div>
          </div>
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
         
    <v-date-picker v-model="state.date" class="date" :available-dates='{ start: state.dates.start, end: state.dates.end }' />
    {{state.date}}
    <div class="likechat">
    <button class="btn_like">
      <ion-icon name="heart" style="font-size:21px;margin-bottom:4px;"></ion-icon><span> 3</span></button>
    <button class="btn_chat">채팅으로 거래하기</button>
    </div>
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import { reactive } from "vue";
import { VueperSlides, VueperSlide } from "vueperslides";
import Menu2 from "./detail/Menu2.vue";



export default {
  components: { VueperSlides, VueperSlide, Menu2},
  setup() {
    const state = reactive({

      slides: [
        { image: require("../assets/images/clean.jpg") },
        { image: require("../assets/images/clean2.jpg") },
        { image: require("../assets/images/clean3.jpg") },
      ],

      date: '',
      dates: {
        start: new Date(2022, 4, 21), 
        end: new Date(2022,4, 25) 
      }
  
  
    
    });
    return { state};
  },
};
</script>

<style scoped>
@import "v-calendar/dist/style.css";

/* 달력 */
.date {
  width:467px;
  height:250px;
  border-radius: 10px;
  margin-top:10px;
  border: 1px solid #3476d8;
}

</style>

<style scoped src="../assets/css/detail.css">

</style>
