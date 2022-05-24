<template>
    <h3>상세페이지</h3>
    <div class="main"> 
        <div class="detail">
            <!-- <div v-if="state.inquire">
                코드 {{ state.inquire.inqno }}
                제목 {{ state.inquire.inqtitle }}
                내용 {{ state.inquire.inqcontent }}
                등록일 {{ state.item.regdate }}
            </div> -->

        </div>
        <div class="comment-box">
            <div class="comment">
                <textarea 
                    class="in"
                    placeholder="댓글을 작성해주세요"
                    cols="47"
                    rows="2"
                ></textarea>
            </div>
            <div class="btn">
                <input type="button" value="등록하기"  @click="handleClick">
            </div>
        </div>
        
        <!-- <div class="btn-box">
            <input type="button" value="목록으로" class="btn1" @click="handleClick1">
        </div> -->
    </div>
</template>

<script>
// import { reactive, onMounted } from 'vue';
// import { useRoute } from 'vue-router';
// import axios from 'axios';
export default {
    setup () {
        // const route = useRoute();
        // const state = reactive({
        //     inqno : route.query.inqno,
        //     token : sessionStorage.getItem("TOKEN")
        // });

        // const handleData = async() => {
        //     const url = `/ROOT/api/inquire/selectone?inqno=${state.inqno}`;
        //     const headers = {
        //         "Content-Type" : "application/json",
        //         "token" : state.token
        //     };
        //     const response = await axios.get(url, {headers});
        //     console.log(response.data);

        //     if(response.data.status === 200){
        //     state.inquire = response.data.result;
        //     }

        // }

        // onMounted( async() => {
        //     await handleData();
        // })

        return {}
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
    width: 100%;
}

.comment-box {
    display: flex;
    align-items: center;
    width: 100%;
}
.comment {
    width: 80%;
    margin-right: 10px;
}
.in {
    padding: 10px;
    height: 80px;
    font-size: 15px;
    color: #808080;
    border: 1px solid #d9d9d9;
    border-radius: 4px ;
}
.btn {
    width: 15%;
    height: 53px;
    padding:15px;
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