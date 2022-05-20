<template>
  <v-app>
    <v-main>
      <div class="dtcontainer">
        <div class="leftside">
          <div class="category_list">
            <a href="#"><span>생활</span></a>
            <ion-icon
              name="chevron-forward-outline"
              style="font-size: 20px; margin-bottom: 4px; color: #707070"
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
              fixed-height="430px"
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

          <div class="profilebox">
            <div class="name"></div>
            <div class="picture" />
          </div>
        </div>

        <div class="rightside">
          <div class="product">
            <div class="pro_title">
              <ion-icon name="checkmark-outline" style="font-size:30px;color:#3476d8;"></ion-icon>
              <h3>생활 청소 능력자입니다.</h3></div>
            <div class="pro_inner">
              <div class="pro_text">
                전문가에게 맡기긴 애매하고 사소한 청소 꿀팁들을 전수해요. 신혼인
                가정이나 자취하는 학생들에게 도움을 주고싶어요.
              </div>
            </div>
          </div>
        </div>
        <v-date-picker
      v-model="picker"
      color="green lighten-1"
    ></v-date-picker>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import { reactive } from "vue";
import { VueperSlides, VueperSlide } from "vueperslides";
import "vueperslides/dist/vueperslides.css";

export default {
  components: { VueperSlides, VueperSlide },
  setup() {
    const state = reactive({
      slides: [
        { image: require("../assets/images/clean.jpg") },
        { image: require("../assets/images/clean2.jpg") },
        { image: require("../assets/images/clean3.jpg") },
      ],
    });

    return { state };
  },
};
</script>

<style lang="css" scoped>

v-img {
  object-fit: cover;
}

h3 {
  margin-left:5px;
  margin-top:2px;
}
.dtcontainer {
  width: 100%;
  display: flex;
  flex-direction: row;
  gap: 60px;
}

.leftside {
  width: 55%;
  display: flex;
  flex-direction: column;
}

.leftslide {
  margin-top: 10px;
}

.rightside {
  width: 40%;
  display: flex;
  flex-direction: column;
  margin-top: 35px;
}

.category_list {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.category_list > a {
  color: #707070;
}

.profilebox {
  width: auto;
  height: 200px;
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
  border: 1px solid rgb(228, 229, 237);
  padding: 0px;
  background: rgb(255, 255, 255);
}

.picture {
  width: 150px;
  height: 150px;
  background-image: url("../assets/images/cleaner.jpg");
  background-size: cover;
  border-radius: 50%;
  border: 3px solid #e6f0ff;
}

.thumbnails .vueperslide {
  transition: 0.5s ease-in-out;
  opacity: 0.7;
  cursor: pointer;
  margin-top: 10px;
}

.pro_title {
  display: flex;
  flex-direction: row;
  padding: 5px 10px;
  height:50px;
  font-size: 17px;
  color: #000000;
  border-bottom:1px solid #3476d8;
  font-family: "GmarketSansMedium";
}

.product {
  height:500px;
  gap: 10px;
  color: #000000;
  border : 1px solid #3476d8;
  font-family: "GmarketSansLight";
  padding: 20px;
  border-radius: 10px;
}

.pro_text {
  margin-top:20px;
  background-color: #e9f1ff;
  padding:10px;
  font-size:15px;
}
</style>
