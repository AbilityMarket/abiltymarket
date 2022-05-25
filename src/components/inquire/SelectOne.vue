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
                <div class="">{{ state.inquire.inqcontent }}}}</div>
            </div>
        </div>
        <div class="answer-top">
            <div> 여기 댓글박스 answerData</div>
            <!-- <div v-for="tmp in state.answer" :key="tmp">
                {{ tmp.anno }}  
            </div> -->
        </div>
        <div class="answer-box">
            <div class="answer">
                <textarea 
                    v-model = "state.answer"
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
        
       <div class="btn-box">
            <router-link to="/Inquire"><v-btn class="btn1">목록으로</v-btn></router-link>
        </div>
    </div>
</template>

<script>
import { reactive, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
export default {
    setup () {
        const route = useRoute();
        const state = reactive({
            inqno : route.query.inqno,
            token : sessionStorage.getItem("TOKEN"),
            answer : ""
        });
        const answer = ref(null);

        const handleData = async() => {
            const url = `/ROOT/api/inquire/selectone?inqno=${state.inqno}`;
            const headers = {
                "Content-Type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            //console.log(response);
            if(response.data.status === 200) {
                state.inquire = response.data.inquireentity;    
                state.writer = response.data.inquireentity.writer;    
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
            body.append("ancontent", state.answer)

            const response = await axios.post(url, body, {headers})
            console.log(response);
            if(response.data.status === 200){
                alert("댓글이 등록되었습니다.")
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
            console.log(response);
            // if(response.data.status === 200){
            //     state.answer = response.data;
            // }
        }

        onMounted( async() => {
            handleData()
            answerData()
        })

        return {state, handleClick, answer}
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
/* .btn-box {
    display: flex;
    align-items: center;
    width: 100%;
} */



</style>