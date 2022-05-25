<template>
    <div>
         <div class="reviewcontainer">
             <div class="reviewbox">
        <div class="re_profile">
          <div class="rev_pic"></div>
          <div class="re_nickname" style="margin-left: 5px">손님</div>
          <div class="re_date">2022-05-23</div>
        </div>
        <div class="re_content">리뷰내용</div>
      </div>
         </div>
    </div>
</template>

<script>
export default {
    setup () {
        

        return {}
    }
}
</script>

<style lang="css" scoped>

.reviewbox {
  min-height: 120px;
  padding: 10px;
  box-shadow: rgba(0, 8, 8, 0.08) 0px 5px 8px 0px;
  font-size: 15px;
  display: flex;
  flex-direction: column;
  margin-top: 35px;
  position: relative;
}

.re_pic {
  width: 23px;
  height: 23px;
  background-image: url("../../assets/images/user.png");
  background-size: cover;
  border-radius: 50%;
  margin-bottom: 5px;
}

.re_profile {
  display: flex;
  display: row;
  align-items: center;
  color: #000000;
  border-bottom: 1px solid #e1f0ff;
  font-size: 15px;
}

.re_date {
  margin-left: auto;
  color: #707070;
  font-size: 12px;
}

.re_content {
  padding: 10px;
}

</style>