<template>
  <div>
    <div class="commentcontainer">
      <!-- 주의 사항 -->
      <div class="notice">
        <span style="color: #ff0000">!</span>
        <span>
          능력과 관계없는 글, 광고성, 욕설, 비방, 도배 등의 글은
          신고처리됩니다.</span
        >
      </div>

      <!-- 댓글 달기 창 -->
      <div class="replybox">
        <textarea cols="30" rows="20" class="reply"></textarea>
        <span></span>
      </div>
      <div class="commit">
        <input type="checkbox" /><span>비밀글</span>
        <button class="btn_register">등록</button>
      </div>

      <!-- 달린 댓글1 -->
      <div class="commentbox">
        <div class="comment_profile">
          <div class="comment_pic"></div>
          <div class="customer" style="margin-left: 5px">손님</div>
          <div class="replydate">2022-05-23</div>
        </div>
        <div class="comment">댓글입니다</div>
        <div class="replybutton">
          <button @click="showreply">
            <ion-icon name="chatbubbles-outline"></ion-icon>
          </button>
          <button>
            <ion-icon name="pencil-outline" style="margin-left: 5px"></ion-icon>
          </button>
        </div>
      </div>

      <!-- 대댓글 -->
      <div class="cocoboxox" :class= "{active : state.active}">
      <div class="cocobox" >

        <div class="arrow">
          <v-img
            src="../../assets/images/reply.png"
            style="width: 20px; height: 20px"
          ></v-img>
        </div>
        <div class="cocobox_inner">
          <div class="comment_profile">
            <div class="comment_pic"></div>
            <div class="seller" style="margin-left: 5px">작성자</div>
            <div class="replydate">2022-05-24</div>
          </div>
          <div class="comment">대댓글입니다</div>
        </div>
      </div>
      </div>

       <div class="replybox">
        <textarea cols="30" rows="20" class="rereply"></textarea>
      </div>
      <div class="commit">
        <input type="checkbox" style="margin-left:60px;"/><span>비밀글</span>
        <button class="btn_register">등록</button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  setup() {
    const state = reactive({
      active : false,
    })

    const showreply = ()=>{
      // 코멘트창 보이게 하기
      if(state.active ===false){
        state.active = true;
      }
      // 코멘트창 숨기기
      else{
        state.active = false
      }
      
    }
    return {
      state,
      showreply
      };
  },
};
</script>

<style lang="css" scoped>

textarea {
  resize: none;
}
.commentcontainer {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 댓글 주의사항 */
.notice {
  margin-top: 10px;
  font-size: 16px;
  color: #000000;
}

/* 비밀글, 등록 */
.commit {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 5px;
}

.commit > span {
  font-size: 15px;
  color: #808080;
}

/* 등록하기 */
.btn_register {
  all: unset;
  width: 80px;
  height: 30px;
  background-color: #3476d8;
  font-family: "GmarketSansLight";
  color: #ffffff;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
  text-align: center;
  margin-left: auto;
}

/* 댓글달기 창 */
.reply {
  margin-top: 10px;
  vertical-align: top;
  width: 100%;
  height: 100px;
  padding: 8px 10px;
  border: 1px solid #b7bfc8;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  font-size: 14px;
  line-height: 22px;
  color: #000000;
}

.reply:focus {
  outline: none;
}

.rereply:focus {
  outline: none;
}


input[type="checkbox"] {
  width: 15px;
  height: 15px;
  margin: 5px;
  margin-bottom: 8px;
}

/* 댓글 */
.commentbox {
  min-height: 120px;
  padding: 10px;
  border-left: 2px solid #3476d8;
  box-shadow: rgba(0, 8, 8, 0.08) 0px 5px 8px 0px;
  font-size: 15px;
  display: flex;
  flex-direction: column;
  margin-top: 35px;
  position: relative;
}

.comment_pic {
  width: 23px;
  height: 23px;
  background-image: url("../../assets/images/user.png");
  background-size: cover;
  border-radius: 50%;
  margin-bottom: 5px;
}

.comment_profile {
  display: flex;
  display: row;
  align-items: center;
  color: #000000;
  border-bottom: 1px solid #e1f0ff;
  font-size: 15px;
}

.replydate {
  margin-left: auto;
  color: #707070;
  font-size: 12px;
}

.comment {
  padding: 10px;
}

.replybutton {
  display: flex;
  flex-direction: row;
  font-size: 19px;
  color: #3476d8;
  position: absolute;
  left: 92%;
  top: 78%;
}

/* 대댓글 */
.cocobox {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  box-sizing: border-box;
}
.cocobox_inner {
  width: 91%;
  min-height: 120px;
  padding: 10px;
  border-right: 2px solid #3476d8;
  font-size: 15px;
  display: flex;
  flex-direction: column;
  margin-top: 15px;
  float: right;
  box-shadow: rgba(0, 8, 8, 0.08) 0px 5px 8px 0px;
}

.arrow {
  padding-top: 18px;
  padding-left: 25px;
}

.rereply {
  margin-top: 10px;
  float: right;
  vertical-align: top;
  width: 91%; 
  height: 100px;
  padding: 8px 10px;
  border: 1px solid #b7bfc8;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  font-size: 14px;
  line-height: 22px;
  color: #000000;
  margin-top: 15px;
}

.cocoboxox.active{
  display: block;
}

.cocoboxox{
  display: none;
}
</style>
