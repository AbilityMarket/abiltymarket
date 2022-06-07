<template>
  <div class="maincontainer" style="min-width: 1168px">
    <article class="mainslide">
      <div class="slide">
        <vueper-slides autoplay>
          <vueper-slide
            v-for="(slide, i) in state.slides"
            :key="i"
            :image="slide.image"
          />
          <template #pause>
            <i class="icon pause_circle_outline"></i>
          </template>
        </vueper-slides>
      </div>
    </article>

    <article class="category1">
      <div class="bestcategory">
        <h3>베스트&nbsp;카테고리</h3>
        <nav>
          <ul class="category" style="margin-top: 10px">
            <li class="category-item">
              <v-img src="../assets/images/life_icon.png" style="width: 75px" />
              <p>생활</p>
              <div class="detail">

                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
                
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/construction_icon.png"
                style="width: 75px"
              />
              <p>시공/조립</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/handmade_icon.png"
                style="width: 75px"
              />
              <p>핸드메이드</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/graphic_icon.png"
                style="width: 75px"
              />
              <p>디자인/그래픽</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img src="../assets/images/pet_icon.png" style="width: 75px" />
              <p>애완동물</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img src="../assets/images/cook_icon.png" style="width: 75px" />
              <p>요리</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
          </ul>
          <ul class="category">
            <li class="category-item">
              <v-img
                src="../assets/images/study_icon.png"
                style="width: 75px"
              />
              <p>공부</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img src="../assets/images/it_icon.png" style="width: 75px" />
              <p>IT</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/business_icon.png"
                style="width: 75px"
              />
              <p>비즈니스</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/hobby_icon.png"
                style="width: 75px"
              />
              <p>취미</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/activity_icon.png"
                style="width: 75px"
              />
              <p>운동</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
            <li class="category-item">
              <v-img
                src="../assets/images/health_icon.png"
                style="width: 75px"
              />
              <p>건강</p>
              <div class="detail">
                <a href="http://localhost:8080/#/trade" class="buy"
                  ><span>구매</span></a
                ><span>|</span>
                <a href="http://localhost:8080/#/trade" class="sell"
                  ><span>판매</span></a
                >
              </div>
            </li>
          </ul>
        </nav>
      </div>
    </article>

    <div class="hr1"></div>

    <article class="category2">
      <div class="d-flex mb-6" style="margin-top: 30px">
        <div><h3>도와주세요</h3></div>
      </div>
      <!-- {{state.list}} -->
      <div
        class="d-flex justify-space-around mb-6"
        style="margin-top: -40px"
        v-if="state.list"
      >
        <div class="d-flex flex-row mb-6">
          <div class="helpme" v-for="tmp in state.list" :key="tmp">
            <ul
              class="helpmelist"
              style="margin-right: 10px; margin-left: 10px"
            >
              <li>
                <a href="#">
                  <div class="profile">
                    <div class="profile-item">
                      <v-img
                        :src="tmp.memberimg"
                        style="width: 25px; height: 25px"
                      />
                    </div>
                    <p class="nickname">{{ tmp.unickname }}</p>
                  </div>
                </a>
                <div style="cursor:pointer" @click="clickBoard(tmp.bno)">
                  <div class="wrphover">
                    <div class="thumbnail">
                      <img :src="tmp.img" style="width: 100%; height: 100%" />
                    </div>
                    <span class="new">NEW</span>
                  </div>
                  <div class="profilebottom">
                    <div class="check">
                      <v-img
                        src="../assets/images/check.png"
                        style="width: 30px; height: 30px"
                      />
                    </div>
                    <div class="profilebottom-item">
                      <p>{{ tmp.btitle }}</p>
                    </div>
                  </div>

                  <div class="location">
                    <div class="location-item">
                      <v-img
                        src="../assets/images/location.png"
                        style="width: 20px; height: 20px; margin-bottom: 40px"
                      />
                    </div>
                    <div class="address">
                      <p>{{ tmp.baddress }}</p>
                    </div>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <h3>도와줄게요</h3>
      <br />
      <div class="d-flex justify-space-around mb-6" style="margin-top: -40px">
        <div class="d-flex flex-row mb-6">
          <div class="helpme" v-for="tmp in state.list2" :key="tmp">
            <ul
              class="helpmelist"
              style="margin-right: 10px; margin-left: 10px"
            >
              <li>
                <a href="#">
                  <div class="profile">
                    <div class="profile-item">
                      <v-img
                        :src="tmp.memberimg"
                        style="width: 25px; height: 25px"
                      />
                    </div>
                    <p class="nickname">{{ tmp.unickname }}</p>
                  </div>
                </a>
                <div style="cursor:pointer" @click="clickBoard(tmp.bno)">
                  <div class="wrphover">
                    <div class="thumbnail">
                      <img :src="tmp.img" style="width: 100%; height: 100%" />
                    </div>
                    <span class="new">NEW</span>
                  </div>
                  <div class="profilebottom">
                    <div class="check">
                      <v-img
                        src="../assets/images/check.png"
                        style="width: 30px; height: 30px"
                      />
                    </div>
                    <div class="profilebottom-item">
                      <p>{{ tmp.btitle }}</p>
                    </div>
                  </div>

                  <div class="location">
                    <div class="location-item">
                      <v-img
                        src="../assets/images/location.png"
                        style="width: 20px; height: 20px; margin-bottom: 40px"
                      />
                    </div>
                    <div class="address"><p>{{ tmp.baddress }}</p></div>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </article>

    <div class="hr1"></div>
<!-- {{state.hotkeyword1[0].incategory}} -->
    <article class="category4">
      <h3 style="margin-top: 30px">인기있는 키워드</h3>
      <br />
      <div class="keyword">
        <div class="lst_keyword" v-if="state.hotkeyword1" >
          <ul class="keyword-list" >
            <li v-for="tmp of state.hotkeyword1" :key="tmp">
              <a href="#"
                ><span>#{{ tmp.incategory }}</span></a
              >
            </li>
            <!-- <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li> -->
          </ul>

          <ul
            class="lst_keyword2-list"
            style="margin-left: 70px; margin-top: 20px"
          >
            <li v-for="tmp of state.hotkeyword2" :key="tmp">
              <a href="#"
                ><span>#{{ tmp.incategory }}</span></a
              >
            </li>
            <!-- <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li>
            <li>
              <a href="#"><span>#플랜테리어</span></a>
            </li> -->
          </ul>
        </div>
      </div>
    </article>

    <div class="hr1"></div>

    <!-- <article class="category5">
      <div class="tip" style="margin-top: 30px">
        <h3>능력자의 팁</h3>
        <br />
      </div>
    </article>

    <div class="hr1"></div>

    <article class="category6">
      <div class="story" style="margin-top: 30px">
        <h3>사연</h3>
        <br />
      </div>
    </article> -->

    <!-- <div class="hr1"></div> -->

    <article class="category7">
      <h3 style="margin-top: 30px">이달의 랭킹</h3>
      <br />
      <div class="rank">
        <div
          class="d-flex justify-space-around mb-6"
          style="margin-top: -40px; margin: 0px !important"
        >
          <div class="d-flex flex-row mb-6" style="gap: 10px" v-if="state.rank">
            <div class="rank-item">
              <div class="d-flex flex-column mb-6">
                <div class="rank-top">이달의 능력자</div>
                <div class="rank-holder">
                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal1.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <span>
                      <!-- {{state.rank[0]}} -->
                      <v-img
                        :src="state.rank[0].img"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">
                      {{ state.rank[0].clickperson }}
                      <span style="margin-left: 30px"
                        >{{ state.rank[0].plus }}회</span
                      >
                    </div>
                  </div>
                </div>

                <div class="rank-other">
                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal2.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        :src="state.rank[1].img"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">{{ state.rank[1].clickperson }}
                      <span style="margin-left: 30px"
                        >{{ state.rank[1].plus }}회</span
                      >
                    </div>
                  </div>
                  <div class="rankline"></div>

                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal3.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        :src="state.rank[2].img"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">{{ state.rank[2].clickperson }}
                      <span style="margin-left: 30px"
                        >{{ state.rank[2].plus }}회</span
                      >
                    </div>
                  </div>
                  <div class="rankline"></div>

                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal4.png"
                        style="width: 30px; height: 30px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        :src="state.rank[3].img"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">{{ state.rank[3].clickperson }}
                      <span style="margin-left: 30px"
                        >{{ state.rank[3].plus }}회</span
                      >
                    </div>
                    </div>
                  <div class="rankline"></div>

                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal5.png"
                        style="width: 30px; height: 30px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        :src="state.rank[4].img"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">{{ state.rank[4].clickperson }}
                      <span style="margin-left: 30px"
                        >{{ state.rank[4].plus }}회</span
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="rank-item" v-for="n in 2" :key="n">
              <div class="d-flex flex-column mb-6">
                <div class="rank-top">이달의 능력자</div>
                <div class="rank-holder">
                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal1.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <span>
                      <!-- {{state.rank[0]}} -->
                      <v-img
                        src="../assets/images/user.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">안녕하세요</div>
                  </div>
                </div>

                <div class="rank-other">
                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal2.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        src="../assets/images/user.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">안녕하세요</div>
                  </div>
                  <div class="rankline"></div>

                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal3.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        src="../assets/images/user.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">안녕하세요</div>
                  </div>
                  <div class="rankline"></div>

                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal4.png"
                        style="width: 30px; height: 30px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        src="../assets/images/user.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">안녕하세요</div>
                  </div>
                  <div class="rankline"></div>

                  <div class="rank-inner">
                    <span>
                      <v-img
                        src="../assets/images/medal5.png"
                        style="width: 30px; height: 30px"
                      ></v-img>
                    </span>
                    <span>
                      <v-img
                        src="../assets/images/user.png"
                        style="width: 35px; height: 35px"
                      ></v-img>
                    </span>
                    <div class="rankname">안녕하세요</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </article>

    <div class="hr1"></div>
  </div>
</template>

<script>
import axios from "axios";
import { onMounted, reactive } from "vue";
import { VueperSlides, VueperSlide } from "vueperslides";
import "vueperslides/dist/vueperslides.css";
import { useRouter } from 'vue-router';
export default {
  components: { VueperSlides, VueperSlide },

  setup() {
    const router = useRouter();
    const state = reactive({
      slides: [
        { image: require("../assets/images/slide1.jpg") },
        { image: require("../assets/images/slide2.jpg") },
        { image: require("../assets/images/slide3.jpg") },
      ],
    });

    const clickBoard = (no)=>{
      router.push({name:"Detail", query:{bno:no}})
    }

    const handleHelpme = async () => {
      const url = `/AbilityMarket/api/main/helpMe`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      // console.log(response);
      if (response.data.status === 200) {
        // console.log("도와주세요" + response.data.list);
        state.list = response.data.list;
        for (let i = 0; i < response.data.list.length; i++) {
          state.list[
            i
          ].img = `/AbilityMarket/api/board/image?bno=${response.data.list[i].bno}`;
          state.list[
            i
          ].memberimg = `/AbilityMarket/api/member/image?uid=${response.data.list[i].uid}`;
        }
        // console.log(state.list);
      }
    };

     const handleHelpyou = async () => {
      const url = `/AbilityMarket/api/main/helpYou`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      // console.log(response);
      if (response.data.status === 200) {
        // console.log("도와줄게요" + response.data.list);
        state.list2 = response.data.list;
        for (let i = 0; i < response.data.list.length; i++) {
          state.list2[
            i
          ].img = `/AbilityMarket/api/board/image?bno=${response.data.list[i].bno}`;
          state.list2[
            i
          ].memberimg = `/AbilityMarket/api/member/image?uid=${response.data.list[i].uid}`;
        }
        // console.log(state.list);
      }
    };

    const handleKeyword = async () => {
      const url = `/AbilityMarket/api/main/hotKeyword`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      // console.log("전체");
      // console.log(response);
      if (response.data.status === 200) {
        state.hotkeyword1 = response.data.list.splice(0,8);
        state.hotkeyword2 = response.data.list;
        // console.log("여기기기기ㅣ기");
        // console.log(state.hotkeyword1);
        // console.log(state.hotkeyword2);
      }
    };

    const rank = async () => {
      const url = `/AbilityMarket/api/main/tradeRank`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      // console.log("이달의랭킹");
      // console.log(response);
      if (response.data.status === 200) {
        state.rank = response.data.list;
        for (let i = 0; i < response.data.list.length; i++) {
          state.rank[i].img = `/AbilityMarket/api/member/image?uid=${response.data.list[i].clickperson}`;
        }
        // console.log("state.rank");
        // console.log(state.rank);

        // state.hotkeyword1 = response.data.list.splice(0,7);
        // state.hotkeyword2 = response.data.list;
        // console.log("asdasdasd")
        // console.log(state.hotkeyword1)
        // console.log(state.hotkeyword2)
      }
    };

    onMounted(() => {
      handleHelpme();
      handleKeyword();
       handleHelpyou();
      rank();
    });
    return { state,
    clickBoard,
     };
  },
};
</script>

<style lang="css" scoped>
@import "../assets/css/default.css";
@import "../assets/css/keyword.css";
@import "../assets/css/category.css";

@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>

<style scoped src="../assets/css/home.css"></style>
