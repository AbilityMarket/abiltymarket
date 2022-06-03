import { createRouter, createWebHashHistory } from 'vue-router'

import Home from '@/components/Home';
import Login from '@/components/Login';
import Join from '@/components/Join';
import JoinNext from '@/components/JoinNext';
import Chat2 from '@/components/Chat2';
import Address2 from '@/components/Address2';
import Kakao2 from '@/components/Kakao2';
import Trade2 from '@/components/Trade2';
import Mypage3 from '@/components/Mypage3';
import Detail from '@/components/Detail';
import WriteReview from '@/components/mypage/WriteReview';
import Logout from '@/components/Logout';
import Inquire from '@/components/Inquire';
import BoardWrite from '@/components/BoardWrite';
import SelectOne from '@/components/inquire/SelectOne'
import InquireList from '@/components/inquire/InquireList'
import InquireWrite from '@/components/inquire/InquireWrite'
import InquireUpdate from '@/components/inquire/InquireUpdate';
import Secret from '@/components/inquire/Secret';

import Mqtt from '@/components/Mqtt';

const routes = [
    { path : '/', redirect:'/home'},
    { path : '/home', name:'Home', component:Home },
    { path : '/login', name:'Login', component:Login },
    { path : '/join', name:'Join', component:Join },
    { path : '/joinnext', name:'JoinNext', component:JoinNext },
    { path : '/chat', name:'Chat', component:Chat2 },
    { path : '/address2', name:'Address2', component:Address2 },
    { path : '/kakao2', name:'Kakao2', component:Kakao2 },
    { path : '/trade', name:'Trade', component:Trade2 },
    { path : '/mypage', name:'Mypage', component:Mypage3 },
    { path : '/detail', name:'Detail', component:Detail },
    { path : '/writeReview', name:'WriteReview', component:WriteReview },
    { path : '/logout', name:'Logout', component:Logout },
    { path : '/mqtt', name:'Mqtt', component:Mqtt },
    { path : '/boardWrite', name:'BoardWrite', component:BoardWrite },
    { path : '/inquire', name:'Inquire', component:Inquire },
    { path : '/selectone', name:'SelectOne', component:SelectOne },
    { path : '/inquirelist', name:'InquireList', component:InquireList },
    { path : '/inquirewrite', name:'InquireWrite', component:InquireWrite },
    { path : '/inquireupdate', name:'InquireUpdate', component:InquireUpdate },
    { path : '/secret', name:'Secret', component:Secret },
];

const router = createRouter(
    { history : createWebHashHistory(), routes : routes }
);

export default router;