<template>

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
      </div>
      <div class="commit">
        <input type="checkbox" /><span>비밀글</span>
        <button class="btn_register">등록</button>
      </div>

      <!-- 댓글 내역이 있는 경우 -->
      <div class="yes_comment" v-if="!state.empty">
      <!-- 달린 댓글1 -->
      <div class="commentbox">
        <div class="comment_profile">
          <div class="comment_pic"></div>
          <div class="customer" style="margin-left: 5px">손님</div>
          <div class="replydate">| 2022-05-23</div>
        </div>
        <div class="comment">댓글입니다</div>

        <!-- 댓글 보기, 쓰기 버튼 -->
        <div class="replybutton">
          <button @click="showreply">
            <ion-icon name="chatbubbles-outline"></ion-icon>
          </button>
          <button @click="showwrite">
            <ion-icon name="pencil-outline" style="margin-left: 5px"></ion-icon>
          </button>
        </div>
      </div>

      <!-- 대댓글 -->
      <div class="cocontainer" :class= "{active : state.active}">
      <div class="cocobox" >

        <div class="arrow">
          <v-img
            src="../../assets/images/arrow.png"
            style="width: 20px; height: 20px"
          ></v-img>
        </div>
        <div class="cocobox_inner">
          <div class="comment_profile">
            <div class="comment_pic"></div>
            <div class="seller" style="margin-left: 5px">작성자</div>
            <div class="replydate">| 2022-05-24</div>
          </div>
          <div class="comment">대댓글입니다</div>
        </div>
      </div>
      </div>

      <!-- 대댓글 달기 -->
      <div class="recontainer" :class= "{active : state.active2}">
      <div class="rereplybox">
       <div class="re_arrow">
          <v-img
            src="../../assets/images/arrow.png"
            style="width: 20px; height: 20px"
          ></v-img>
        </div>
     <div class="replybox">
        <textarea cols="300" rows="20" class="rereply"></textarea>
      </div>
      </div>

      <div class="rere_commit">
        <div class="secret"><input type="checkbox" /><span>비밀글</span></div>
        <button class="btn_register">등록</button>
      </div>
      </div>
      </div>


      <!-- 댓글 내역이 없는 경우 -->
      <div class="no_comment" v-if="state.empty">
        <img :src="state.img" style="width: 180px;height: 180px;margin-bottom:10px;" />
        
        <span>아직 작성된 댓글이 없습니다.</span>
        <span>능력에 대한 문의나 댓글을 작성해주세요.</span>
        
      </div>
        




    </div>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  setup() {
    const state = reactive({
      active : false,
      active2 : false,
      empty : true,
      img : require("../../assets/images/nocomment.png")
    })

    const showreply = ()=>{
      // 대댓글 보이게 하기
      if(state.active ===false){
        state.active = true;
      }
      // 대댓글 숨기기
      else{
        state.active = false
      }
    }

    const showwrite = () => {
      // 대댓글 쓰는 창 보이게 하기
      if(state.active2 ===false) {
        state.active2 = true;
      }
      // 대댓글 쓰는 창 숨기기 
      else {
        state.active2 = false;
      }
    }

    return {
      state,
      showreply,
      showwrite
      };
  },
};
</script>

<style lang="css" scoped>
@import "../../assets/css/detail-comment.css";


</style>
