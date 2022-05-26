<template>
    <div class="main"> 
        <h2>상세페이지</h2>
        <div v-if="state.inquire" class="select-box" >
            <div class="top">
                <div class="top-n">
                    <div class="top-n1">
                        <label>번호.</label>
                        <div class="top-s"> {{ state.inquire.inqno }}</div>
                    </div>
                    <div class="top-n2">
                        <label>등록일.</label>
                        <div class="top-s"> {{ state.inquire.inqregdate }}</div>
                    </div>
                </div>
                <div class="top-n">
                    <label>제목.</label>
                    <div class="top-s"> {{ state.inquire.inqtitle }}</div>
                </div>
            </div>
            <div class="middle">
                <div class="">{{ state.inquire.inqcontent }}</div>
            </div>
            <div class="btn-box">
                <v-btn class="btn1" @click="handleUpdate">수정</v-btn>
                <v-btn class="btn1" @click="handleDelete">삭제</v-btn>
                <router-link to="/Inquire"><v-btn class="btn1">목록으로</v-btn></router-link>
            </div>
        </div>
          
        <div class="answer-box">
            <div class="answer">
                <textarea 
                    v-model = "state.answerr"
                    class = "in"
                    placeholder = "댓글을 작성해주세요"
                    cols = "30"
                    rows = "3"
                    ref = "answer"
                ></textarea>
            </div>
            <div>
                <v-btn class="btn" @click="handleClick">등록하기</v-btn>
            </div>
        </div>

        <div class="answer-top">
            <div v-for="tmp, idx in state.answer" :key="tmp" class="answer-a">   
                <div class="v-img-box">
                    <b>관리자</b>
                    <v-img src="../../assets/images/dot.png" class="v-img"  @click="dotButton(idx) "/>
                    <div class="v-im" v-show="state.button[idx] === 1">
                        <div class="v-im-1" @click="answerUpdate">수정하기</div>
                        <div class="v-im-1" @click="answerDelete(state.anno)">삭제하기</div>
                    </div>
                </div>
                <div class="answer-content">{{ tmp.ancontent }}</div>
                <div class="answer-date">{{ tmp.anregdate }}</div>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { useRouter } from 'vue-router'
import axios from 'axios';
export default {
    setup () {
        const route = useRoute();
        const router = useRouter();
        const state = reactive({
            inqno : route.query.inqno,
            anno : route.query.anno,
            token : sessionStorage.getItem("TOKEN"),
            answerr : "",
            button : []
        });
        const answer = ref(null);

        // 업데이트 창으로 이동
        const handleUpdate = () => {
            router.push({name:'InquireUpdate', query:{inqno: state.inqno}})
        }

        function dotButton(idx) {
            if(state.button[idx] == 0){
                state.button[idx] = 1
                
            }else{state.button[idx] = 0}
        }

        const handleData = async() => {
            const url = `/ROOT/api/inquire/selectone?inqno=${state.inqno}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            if(response.data.status === 200) {
                state.inquire = response.data.inquire;      
            }
        }

        // 관리자용 댓글달기
        const handleClick = async() => {
            if(state.answer === ''){
                alert('댓글을 작성해주세요')
                answer.value.focus();
                return false;
            }
            const url = `/ROOT/api/answer/insertone?inqno=${state.inqno}`;
            const headers = {
                "Content-type" : "application/json",
                "token" : state.token
            }
            const body = new FormData();
            body.append("ancontent", state.answerr)

            const response = await axios.post(url, body, {headers})
            console.log(response);
            if(response.data.status === 200 || response.data.status === 100){
                alert("댓글이 등록되었습니다.");
                state.answerr='';
                answerData();
            }
        } 
        // 댓글 불러오기
        const answerData = async() => {
            const url = `/ROOT/api/answer/selectone?inqno=${state.inqno}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            }
            const response = await axios.get(url, {headers})
            console.log(response.data);
            if(response.data.status === 200){
                state.answer = response.data.result;
                for(let i = 0; i< state.answer.length; i++){
                    state.button[i] = 0;
                }
            }
        }
        // 문의글 삭제하기 (작성자만)
        const handleDelete = async() => {
            const url = `/ROOT/api/inquire/deleteone?inqno=${state.inqno}`;
            const headers = {
                "Content-type" : "application/json",
                "token" : state.token
            }
            const response = await axios.delete(url, {headers : headers});
            //console.log(response.data);
            if(response.data.status === 200) {
                alert('게시글이 삭제되었습니다')
                router.push({ name : "Inquire" })
            }
        }

        onMounted( async() => {
            handleData();
            answerData();
        })

        // 댓글 삭제
        const answerDelete = async(anno) => {
            const url = `/ROOT/api/answer/deleteone?&anno=${state.anno}`;
            const headers = {
                "Content-type" : "application/json",
                "token" : state.token
            } 
            const body = {anno}
            const response = await axios.delete(url, {headers : headers, data : body})
            console.log(response);
        }



        return {state, handleClick, answer, handleUpdate, handleDelete, answerDelete, dotButton}
    }
}
</script>

<style scoped>

*{
    margin:0;
    padding: 0;
    box-sizing: border-box;
    font-family: "GmarketSansMedium";
}
.main {
    display: flex;
    flex-direction: column;
    width: 100%;
}
.main > h2 {
    margin-bottom: 20px;
}
.select-box {
    display: flex;
    align-items: center;
    flex-direction: column;
    width: 100%;
}
.top {
    display: flex;
    flex-direction: column;
    width:100%;
    height: 50%;
    border: 1px solid #d9d9d9;
    padding: 10px;
    margin-bottom: 10px;
}
.top-n {
    display: flex;   
}
.top-n1 {
    display: flex;
    width: 100%;
}
.top-n2 {
    display: flex;
    width: 100%;
}
.top-s {
    margin-left: 20px;
}
.middle {
    display: flex;
    flex-direction: column;
    width:100%;
    height: 50%;
    border: 1px solid #d9d9d9;
    padding: 10px;
    margin-bottom: 10px;
}
.answer-top {
    display: flex;
    flex-direction: column;
    width:100%;
    border: 1px solid #d9d9d9;
    padding: 10px;
    margin-bottom: 10px;
}
.answer-box {
    display: flex;
    align-items: center;
    width: 100%;
}
.answer-a {
    padding: 10px;
}
.writer {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 10%;
    border: 1px solid #d9d9d9;
}
.answer {
    width: 100%;
    margin-right: 10px;
}
.in {
    padding: 10px;
    width:100%;
    font-size: 15px;
    color: #808080;
    border: 1px solid #d9d9d9;
    border-radius: 4px ;
}
.btn {
    width: 100px;
    height: 80px;
    padding:15px;
    margin-bottom: 10px;
    border-radius: 4px;
    background-color: #f4f4f4;
    border: 1px solid #ebebeb;
    
}
.btn-box {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: 100%;
    margin-bottom: 30px;
}
.btn1 {
    margin-left: 10px;
}
.v-img-box {
    position: relative;
}
.v-img {
    width:20px; 
    height:20px; 
    float:right; 
    cursor: pointer;
}
.v-im {
    position: absolute;
    right: 5px;
    top: 23px;
    width: 85px;
    height: 68px;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    font-size: 13px;
    color: #b9b4b4;
    padding: 10px;
}
.v-im-1 {
    margin-bottom: 10px;
    margin-left: 3px;
    cursor: pointer;
}
.answer-content {
    margin-top: 11px;
    font-size: 15px;
}
.answer-date {
    font-size: 12px;
    color: #b9b4b4;
}



</style>