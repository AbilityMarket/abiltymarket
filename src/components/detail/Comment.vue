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
        <textarea cols="30" rows="20" class="reply" ref="commreply"
          v-model="state.boardreply"></textarea>
      </div>
      <div class="commit">
        <input type="checkbox"  id="checkbox" v-model="state.coopen" /><span>비밀글</span>
        <button class="btn_register" @click="replyClick">등록</button>
      </div>
    
      <!-- 댓글 내역이 있는 경우 -->
      <div class="yes_comment" v-if="!state.empty">
      <!-- 달린 댓글1 -->
      <div v-for="tmp in state.list" :key="tmp" class="commentbox">
        <div v-show="tmp.update == false">
          <div class="comment_profile">
            <div class="comment_pic"></div>
            <div class="customer" style="margin-left: 5px">손님</div>
            <div class="replydate"> {{tmp.coregdate.split("T")[0]}} {{tmp.coregdate.split("T")[1].split(":")[0]}}
                              : {{tmp.coregdate.split("T")[1].split(":")[1]}}</div>
          </div>
          <div class="comment">{{tmp.cocontent}}</div>

          <div class="replyupdelete">
            <div class="replyupdate" @click="tmp.update = true">수정</div>
            <div class="replydelete" @click="commDelete(tmp.cono)">삭제</div>
          </div>
        </div>
        <div v-show="tmp.update == true" class="reply-update-box"> 
          <input v-model="tmp.cocontent" class="reply-update"/>
            <div class="replyupdate1" @click="commUpdate(tmp.cono, tmp.cocontent)">수정</div>
            <div class="replydelete1" @click="tmp.update = false">취소</div>
        </div> 
        
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
import { reactive, onMounted, ref } from 'vue';
import { useRoute } from  'vue-router';
import axios from 'axios';
export default {
  setup() {
    const route = useRoute(); 
    const state = reactive({
      token : sessionStorage.getItem("TOKEN"),
      active : false,
      active2 : false,
      boardreply : "",
      img : require("../../assets/images/nocomment.png"),
      coopen : 1,
      page: 1,
      // bno : 23,
      // empty : true,
      bno : route.query.bno,
      
    })
    const commreply = ref(null);

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

    // 댓글 작성
    const replyClick = async() => {
      if(state.boardreply === '') {
        alert('댓글을 작성해주세요')
        commreply.value.focus();
        return false;
      }
      const url = `/ROOT/api/comm/insert?bno=${state.bno}`;
      const headers = {
            "Content-type" : "application/json",
            "token" : state.token
        }
      const body = new FormData();
      body.append("cocontent", state.boardreply)
      body.append("coopen", state.coopen)

      const response = await axios.post(url, body, {headers : headers})
       console.log(response)
      if(response.data.status === 200) {
        alert("댓글이 등록되었습니다.");
        state.boardreply = '';
        commData();
      }
    }

    // 댓글 조회
    const commData = async() => {
      const url = `/ROOT/api/comm/selectlist?bno=${state.bno}&page=${state.page}`;
      const headers = {
          "Content-Type" : "application/json",
          "token" : state.token
      }
      const response = await axios.get(url, {headers})
      console.log(response);
      if(response.data.status === 200) {
        state.list = response.data.list
      console.log(state.list)
        for(let i = 0; i<state.list.length; i++){
          state.list[i].update = false;
        }
        console.log(state.list)
        state.empty = false;
      }
      else{
        if(response.data.status === 0) {
          state.empty = true;
        }
      }
       
    }

    // 댓글 삭제
    const commDelete = async(cono) => {
      console.log("cono ===" + cono);
      const url = `/ROOT/api/comm/delete?cono=${cono}`;
      const headers = {
        "Content-type" : "application/json",
        "token" : state.token
      }
      const body = {}
      const response = await axios.delete(url, {headers, data : body})
      if(response.data.status === 200) {
        alert("댓글이 삭제되었습니다.")
        commData();
      }
    }

    // 댓글 수정
    const commUpdate = async(cono, cocontent) => {
      const url = `/ROOT/api/comm/update?cono=${cono}`;
      const headers = {
          "Content-type" : "application/json",
          "token" : state.token
      }
      const body = new FormData();
      body.append("cocontent", cocontent)
      body.append("coopen", 1)

      const response = await axios.put(url, body, {headers})
      console.log(response);
      if(response.data.status === 200) {
        alert("댓글이 수정되었습니다.")
        commData();
      }
    }
    



    // 대댓글 작성
    // const replyClick = async() => {
    //   if(state.boardreply === '') {
    //     alert('댓글을 작성해주세요')
    //     commreply.value.focus();
    //     return false;
    //   }
    //   const url = `/ROOT/api/comm/insert?bno=${state.bno}`;
    //   const headers = {
    //         "Content-type" : "application/json",
    //         "token" : state.token
    //     }
    //   const body = new FormData();
    //   body.append("cocontent", state.boardreply)
    //   body.append("coopen", state.coopen)

    //   const response = await axios.post(url, body, {headers : headers})
    //    console.log(response)
    //   if(response.data.status === 200) {
    //     alert("댓글이 등록되었습니다.");
    //    
    //     
    //   }
    // }

    // 대댓글 조회
    // const recommData = async() => {
    //   const url = `/ROOT/api/comm/selectlistRecomment?cono=${state.cono}`;
    //   const headers = {
    //       "Content-Type" : "application/json",
    //       "token" : state.token
    //   }
    //   const response = await axios.get(url, {headers})
    //   console.log(response.data);
      // if(response.data.status === 200) {
      //   state.list = response.data.list
      //   state.empty = false;

      // }
    // }

    onMounted( () => {
          commData();
          // recommData();
        })


    return {
      state,
      showreply,
      showwrite,
      replyClick,
      commreply,
      commDelete,
      commUpdate
    };  
  },
};
</script>

<style lang="css" scoped>
@import "../../assets/css/detail-comment.css";


</style>
