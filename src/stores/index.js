import axios from 'axios';
import { createStore } from 'vuex';

export default createStore({

    // 상태변수
    // 공통적으로 써야하는 변수
    state:{
        selectcategory: "",
        selectcategoryname: "",
        clicklogged : sessionStorage.getItem("CLICKLOGGED"),
        logged : false,
        page : '',
        uid : "",
        uimg : "",
        unickname : '',
        rankimg : "",
    },

    

    // getter => App.vue
    getters: {
        getSelectcategoryname(state){
            return state.selectcategoryname;
        },
        getSelectcategory(state){
            return state.selectcategory;
        },
        getClicklogged(state){
            return state.clicklogged;
        },
        getLogged(state){
            return state.logged;
        },
        getPage(state){
            return state.page;
        },
        getUimg(state){
            return state.uimg;
        },
        getRankimg(state){
            return state.rankimg;
        },
        getUnickname(state){
            return state.unickname;
        },
        getUid(state){
            return state.uid;
        },
    },

    // mutations => Login.vue에서 사용, logout.vue에서 사용
    mutations:{
        setSelectcategoryname(state, value){
            state.selectcategoryname = value;
        },
        setSelectcategory(state, value){
            state.selectcategory = value;
        },
        setClicklogged(state, value){
            state.clicklogged = value;
        },
        setLogged(state, value){
            state.logged = value;
        },
        setPage(state, value){
            state.page = value;
        },
        setUimg(state, value){
            console.log("setasdsad",value)
            state.uimg = `/AbilityMarket/api/member/image?uid=`+value+'&dt=' +new Date().getTime();
        },
        setRankimg(state, value){
            state.rankimg = `/AbilityMarket/api/rank/image?uid=`+value +'&dt=' +new Date().getTime();
        },
        setUnickname(state, value){
            state.unickname = value;
        },
        setUid(state, value){
            state.uid = value;
        },
    },



    // actions

    actions: {

        async handleMember(context, payload){
            // console.log(payload);
            const url ="/AbilityMarket/api/member/selectmember"
            const headers = {"content-type":"application/json",
        "token": sessionStorage.getItem("TOKEN")};
            const response = await axios.get(url,{headers});
            console.log(response);
            if(response.data.status ===200){
                context.commit("setUnickname", response.data.unickname)
                context.commit("setUid", response.data.uid)
                context.commit("setUimg", response.data.uid)
                context.commit("setRankimg", response.data.uid)
                console.log("store > handelMemberactio>동작!");
            }
        }
    //     async handleMenu(context, payload){
    //         console.log(route.query.menu)
    //     }

        // async handleData(context , payload){
        //     console.log(payload)
        //     const token = sessionStorage.getItem("TOKEN");
        //     if(typeof token !== 'undefined' && token !==null){
        //         const url = `/member/validation`;
        //         const headers = {
        //         "Content-Type": "application/json",
        //         "token" : token,
        //         };
        //         const response = await axios.get(url, {headers})
        //         if(response.data.status ===200){
        //             context.commit("setUid", response.data.uid);
        //             context.commit("setUname", response.data.uname);
        //             context.commit("setUrole", response.data.urole);
        //             context.commit("setLogged", true);
        //         }
        //         else{
        //             //토큰의 유효성을 검사하여 통과하지 못할경우
        //             context.commit("setLogged", false);
        //         }
        //     }
            
        // }
    }
})