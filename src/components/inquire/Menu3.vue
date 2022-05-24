<template>
    <div class="select">
        <h2>문의하기</h2>
          <div class="inquiretitle">
            <div class="title">
                <div>제목</div>
                <input type="text" placeholder="제목을 입력해주세요" v-model="state.inqtitle" ref="inqtitle" />
            </div>
        </div>

        <div class="selectitem">
            <div class="box">
                <select
                    v-model="state.value"
                    ref = "value"
                    class="main_left-3-select">
                    <option value="" selected disabled hidden>유형을 선택해주세요</option>
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
            <input type="button" value="접수완료" class="btn" @click="handleClick">
        </div>

    </div>
</template>

<script>
import { useRouter } from 'vue-router';
import { reactive, ref, onMounted } from 'vue';
// import { onMounted } from 'vue';
import axios from 'axios';
export default {
   setup () {
        const router = useRouter();
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            droparrow: require("../../assets/images/drop.png"),
            inqtitle : '',
            inqcontent : '',
           

            type : [
                {
                    value:0,
                    label:"문의/제안"
                }, {
                    value:1,
                    label:"거래관련"
                }, {
                    value:2,
                    label:"이벤트/프로모션"
                }, {
                    value:3,
                    label:"비매너사용자신고"
                }, {
                    value:4,
                    label:"기타"
                }
            ],
            value:""
        })

        const inqtitle = ref(null);
        const inqcontent = ref(null);
        const value = ref(null);

        const handleClick = async () => {
            if(state.inqtitle === ''){
                alert('제목을 입력하세요')
                inqtitle.value.focus();
                return false;
            }    
            if(state.value === ''){
                alert('유형을 선택하세요')
                value.value.focus();
                return false;
            }
            if(state.inqcontent === ''){
                alert('내용을 입력하세요')
                inqcontent.value.focus();
                return false;
            }
            const url = `/ROOT/api/inquire/insert`;
            const headers = { 
                "Content-Type": "form-data",
                "token" : state.token     
                };
            const body = new FormData();
                body.append("inqtitle", state.inqtitle);
                body.append("inqcontent", state.inqcontent);
                body.append("type", state.type.value);
        
            const response = await axios.post(url, body, { headers });
            console.log(response.data);
            if (response.data.status === 200) {
                alert("문의글이 등록되었습니다.");
                router.push({ name: "Home" });
            }
        };
        
        const information = async() => {
            const url = `/ROOT/api/member/selectmember`
            const headers = {
                "content-type" : "application/json",
                "token" : state.token
            };
            
            const response= await axios.get(url, {headers});
            if(response.data.status == 200) {
                console.log(response);
                state.info = response.data;
                console.log(state.info);
            }
        }
        onMounted(async() => {
            await information();
        })

        return {state, handleClick, inqtitle, inqcontent, value}
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
.select {
    display : flex; 
    flex-direction: column;
    justify-content : center; 
    font-family: "GmarketSansMedium";
    padding-left: 20px;   
}
.inquiretitle {
    display: flex;
}
.inquiretitle>.title {
    width: 100%;
}
.inquiretitle>.title >input {
    width: 100%;
    padding: 10px;
    padding-top: 13px;
    color: #808080;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
}

.select > h2{
    margin-bottom: 40px;
}
.selectitem {
    width: 100%;
    height: 17%;
    margin: 20px auto;
    
}
.box {
    width: 45%;
    height: 100%;
    position: relative;
    display: flex;
}
.main_left-3-select {
    width: 100%;
    padding: 12px 0px;
    font-size: 16px;
    color: #808080;
    padding-left: 10px;
    background-color: #f5f5f5;
    border-radius: 4px;
}
.droparrow{
    width:30px;
    position: absolute;
    right: 0;
    top: 15%;
    pointer-events: none;
}
.information {
    display: flex;
}
.information>.user {
    width: 100%;
}
.readonly {
    width: 95%;
    padding: 10px;
    padding-top: 13px;
    color: #808080;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    background-color: #f5f5f5;
}
.select > h4 {
    margin-top: 20px;
}
.content{
    padding: 10px 10px;
    line-height: 20px;
    font-size: 15px;
    color: #808080;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    margin-bottom: 10px;
}
.btn_box{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 25px;
}
.btn{
    padding: 10px;
    width: 20%;
    background-color: #3476D8;
    color: #fff;
    margin: 10px;    
    font-size: 15px;
    border-radius: 4px;
    display: block;
    margin-left: auto;
    margin-right: auto;
}
</style>