<template>
<div class="select">
        <h2>문의글 수정</h2>
          <div class="inquiretitle">
            <div class="title">
                <div>제목</div>
                <input type="text" v-model="state.inqtitle" ref="inqtitle" placeholder="제목을 입력해주세요" />
            </div>
        </div>

        <div class="selectitem">
            <div class="box">
                <select
                    v-model="state.value"
                    ref = "value"
                    class="main_left-3-select">
                    <option v-for="tmp of state.type" :key="tmp" :value="tmp.value" :label="tmp.label">
                        
                    </option>
                </select>   
                <img :src="state.droparrow" class="droparrow" /> 
            </div>
        </div>

        <div v-if="state.info" class="information">
            <div class="user">
                <div>이름</div>
                <div class="readonly"> {{state.info.uphone}}</div>
            </div>
            <div class="user">
                <div>휴대폰 번호</div>
                <div class="readonly">  {{state.info.uname}}</div>
            </div>
        </div>

        <div style="margin-top: 20px;" > 문의글 작성 </div>
        <textarea
            placeholder="문의 내용을 입력해주세요"
            v-model="state.inqcontent"
            ref = "inqcontent"
            class="content"
            cols="50"
            rows="6"
        ></textarea>
        <div class="btn-box">
            <input type="button" value="수정완료" class="btn" @click="inquireUpdate">
        </div>

    </div>
</template>

<script>
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { reactive, ref, onMounted } from 'vue';
import axios from 'axios';
export default {
    setup () {
        const route = useRoute();
        const router = useRouter();
        const state = reactive({
            inqno : route.query.inqno,
            token : sessionStorage.getItem("TOKEN"),
            droparrow: require("../../assets/images/drop.png"),
            inqtitle : '',
            inqcontent : '',
            value : -1,
            type : [
                { value:-1, label:"유형을 선택해주세요" }, 

                { value:0, label:"거래문의" }, 
                { value:1, label:"계정문의" }, 
                { value:2, label:"광고문의" }, 
                { value:3, label:"오류제보" }, 
                { value:4, label:"이벤트/프로모션" },
                { value:5, label:"능력마켓사용방법" },
                { value:6, label:"게시글노출/비노출" },
                { value:7, label:"비매너사용자신고" },

                { value:8, label:"기타문의" },
              
            ],        
        })
        const inqtitle = ref(null);
        const inqcontent = ref(null);
        const value = ref(null);

        const handleData = async() => {
            const url =`/AbilityMarket/api/inquire/selectone?inqno=${state.inqno}`;
            const headers = {
                "Content-type" : "application/json",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            console.log(response);
            if(response.data.status === 200){
                state.inqtitle = response.data.inquire.inqtitle;
                state.inqcontent = response.data.inquire.inqcontent;
                state.value = response.data.inquire.inqselecttype;
            }
        } 

        const inquireUpdate = async () => {
            if(state.inqtitle === ''){
                alert('제목을 입력하세요')
                inqtitle.value.focus();
                return false;
            }    
            if(state.value === -1){
                alert('유형을 선택하세요')
                value.value.focus();
                return false;
            }
            if(state.inqcontent === ''){
                alert('내용을 입력하세요')
                inqcontent.value.focus();
                return false;
            }
            const url = `/AbilityMarket/api/inquire/updateone?inqno=${state.inqno}`;
            const headers = { 
                "Content-Type":"multipart/form-data",
                "token" : state.token     
                };
            const body = new FormData();
                body.append("inqtitle", state.inqtitle);
                body.append("inqcontent", state.inqcontent);
                body.append("inqselecttype", state.value);
        
            const response = await axios.put(url, body, { headers });
            console.log(response.data);
            if (response.data.status === 200) {
                alert("문의글이 수정되었습니다.");
                router.push({ name: "Inquire" });
            }
        };
        const information = async() => {
            const url = `/AbilityMarket/api/member/selectmember`
            const headers = {
                "Content-type" : "application",
                "token" : state.token
            };
            const response = await axios.get(url, {headers});
            if(response.data.status === 200) {
                 state.info = response.data;
            }
        }
        onMounted(async() => {
            information();
            handleData();
        })
        

        return {state, inquireUpdate, inqtitle, inqcontent, value}
    }
}
</script>

<style scoped src="../../assets/css/inquire-write.css"></style>