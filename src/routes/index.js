import { createRouter, createWebHashHistory } from 'vue-router'

import Home from '@/components/Home';
import Login from '@/components/Login';
import Join from '@/components/Join';
import Chat2 from '@/components/Chat2';
import Address2 from '@/components/Address2';
import Kakao2 from '@/components/Kakao2';
import Trade2 from '@/components/Trade2';

const routes = [
    { path : '/', redirect:'/home'},
    { path : '/home', name:'Home', component:Home },
    { path : '/login', name:'Login', component:Login },
    { path : '/join', name:'Join', component:Join },
    { path : '/chat2', name:'Chat2', component:Chat2 },
    { path : '/address2', name:'Address2', component:Address2 },
    { path : '/kakao2', name:'Kakao2', component:Kakao2 },
    { path : '/trade2', name:'Trade2', component:Trade2 },
];

const router = createRouter(
    { history : createWebHashHistory(), routes : routes }
);

export default router;