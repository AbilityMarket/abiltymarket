<template>
  <div>
    <div class="reviewcontainer">
      <div class="notice">
        <span>실제 능력마켓을 이용하신 분들이 남긴 후기입니다.</span>
      </div>

      <div class="reviewbox">
        <div class="re_profile">
          <div class="re_pic"></div>
          <div class="re_nickname" style="margin-left: 5px">손님</div>
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
          <div class="score"><span>5</span></div>

          <div class="re_date">| 2022-05-23</div>
        </div>
        <div class="re_content">리뷰내용</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    return {};
  },
};
</script>

<style lang="css" scoped>
@import "../../assets/css/detail-review.css";
</style>
