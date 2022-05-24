<template>
  <div>
    <div class="commentcontainer">
      <div class="notice">
        <span style="color: #ff0000">!</span>
        <span
          > 능력과 관계없는 글, 광고성, 욕설, 비방, 도배 등의 글은
          신고처리됩니다.</span
        >
      </div>

      <div class="replybox">
        <textarea
          cols="30"
          rows="10"
          title="문의 작성"
          id="qnaTextarea"
        ></textarea>
        </div>
         <div class="commit">
        <input type="checkbox" /><span>비밀글</span>
        <button class="btn_register">등록하기</button>
      </div>
      
        <div class="commentbox">
          <div class="comment_profile">
          <div class="comment_pic"></div>
          <div class="username" style="margin-left: 5px">푸른청춘</div>
          <div class="replydate">2022-05-23</div>
        </div>
        <div class="comment">
          안녕하세요
          </div>
          <div class="replybutton">
          <button>
            <ion-icon name="chatbubbles-outline"></ion-icon>
            </button>
             <button>
              <ion-icon name="pencil-outline" style="margin-left:5px;"></ion-icon></button>
          </div>
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

<style lang="css" coped>
.commentcontainer {
  width: 100%;
}

.notice {
  margin-top: 10px;
  font-size: 16px;
  color: #000000;
}

.commit {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top:5px;
}

.commit > span {
  font-size:15px;
  color: #808080;
}

.btn_register {
  all: unset;
  width: 100px;
  height: 30px;
  background-color: #3476d8;
  color:#ffffff;
  border-radius: 5px;
  font-size: 0.9rem;
  cursor: pointer;
  margin-right: 10px;
  text-align: center;
  margin-left: auto;
}

.replydate {
  margin-left: auto;
  color: #707070;
  font-size: 13px;
}

textarea {
  margin-top:10px;
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
  color: #959da6;
  resize: vertical;
}

textarea:focus {
  outline: none;
}

input[type="checkbox"] {
  width: 15px;
  height: 15px;
  margin: 5px;
  margin-bottom: 8px;
}

.commentbox {
  min-height: 100px;
  padding: 10px;
  border-left:2px solid #3476D8;
  box-shadow: rgba(0, 8, 8, 0.08) 0px 5px 8px 0px;
  font-size:15px;
  display: flex;
  flex-direction: column;
  margin-top:35px;
}

.comment_pic {
  width: 30px;
  height: 30px;
  background-image: url("../../assets/images/user.png");
  background-size: cover;
  border-radius: 50%;
}

.commentbox > .comment_profile {
  display: flex;
  display: row;
  align-items: center;
  color: #000000;
  border-bottom:1px solid #e1f0ff;
  font-size: 15px;
}

.commentbox > .comment {
  padding:20px;
}

.commentbox > .replybutton {
  display: flex;
  flex-direction: row;
  margin-left: auto;
  margin-bottom: -10px;
  font-size:20px;
  color: #3476d8;
}


</style>
