<template>
  <div>
    <div class="reviewcontainer">
      <div class="yes_review" v-if="!state.empty">
      <div class="notice">
        <span>실제 능력마켓을 이용하신 분들이 남긴 후기입니다.</span>
      </div>

      <!-- 리뷰가 있을 때 -->
      

      <!-- 리뷰박스 -->
      <div class="reviewbox">
        <div class="re_profile">
          <div class="re_pic"></div>
          <div class="re_nickname" style="margin-left: 5px">손님</div>

          <!-- 별점 -->
          <div class="star-ratings">
            <div
              class="star-ratings-fill space-x-2 text-lg"
              :style="{ width: ratingToPercent + '%' }">
              <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
            </div>
            <div class="star-ratings-base space-x-2 text-lg">
              <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
            </div>
          </div>

          <!-- 평점 -->
          <div class="score"><span>5</span></div>
          <!-- 날짜 -->
          <div class="re_date">| 2022-05-23</div>
        </div>

        <!-- 리뷰 내용 -->
        <div class="re_content">리뷰내용</div>

      </div>
      </div>
    


    <!-- 리뷰가 없을 때 -->
    <div class="no_review" v-if="state.empty">

        <img :src="state.img" style="width: 180px;height: 180px;margin-bottom:10px;" />
        <span>아직 작성된 후기가 없습니다.</span>
        <span>후기를 남겨주세요.</span>
        
    </div>
    

  </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  setup() {

    const state = reactive({
      empty : true,
      img : require("../../assets/images/noreview.png")
    })
    return {state};
  },
};
</script>

<style lang="css" scoped>
@import "../../assets/css/detail-review.css";
</style>
