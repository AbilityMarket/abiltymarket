<template>
    <div>
        <button @click="createConnection()">1. 서버연결</button>
        <button @click="doSubscribe()">2. 토픽설정</button>
        <button @click="doUnSubscribe()">3. 토픽해제</button>
        <input type="text" v-model="state.message" />
        <button @click="sendMessage()">4. 메세지보내기</button>
    </div>
</template>

<script>
import mqtt from 'precompiled-mqtt';
import { reactive } from '@vue/reactivity';
import { onMounted } from '@vue/runtime-core';
export default {
    setup () {
        const state = reactive({
            client : '', //접속한 클라이언트 객체
            host : '1.234.5.158',   //서버주소
            port : 11884, //web용
            options : {
                clean : true, //세션초기화
                reconnectPeriod : 20000, // 주기적인 접속 시간
                // 고유값
                clientId : 'd202_' + new Date().getTime(),
                username : 'ds606', // 아이디 
                password : 'ds606', // 암호
            },

            topic : 'ds/abilitymarket/customer',
            qos : 0,  //quality of service 0부터 2까지의 숫자
            // 상대방에게 정확하게 보내는 수치, 중요한 건 2번으로 보내야함. 대신 리소스를 많이 쓰게 됨.
        });

        const createConnection= ()=>{
            const url = `ws://${state.host}:${state.port}`;
            try{
                
                state.client = mqtt.connect(url, state.options);
                console.log(state.client);
                state.client.on("connect", ()=>{
                    console.log("connect success!")
                })

                state.client.on("error", ()=>{
                    console.log("connect error!!");
                })

                state.client.on("message", (topic,message)=> {
                    console.log(topic, JSON.parse(message));
                })
            }
            catch(e){
                console.log("mqtt error", e);
            }
        }

        const doSubscribe = () => {
            state.client.subscribe(state.topic, {qos:state.qos}, (error, res) => {
                if(error) {
                    console.log('subscribe topic error', error);
                    return;
                }
                console.log('subscribe success', res);
            });
        };

        const doUnSubscribe = () => {
            state.client.unsubscribe(state.topic, error => {
                if(error) {
                    console.log('unsubscribe topic error', error);
                    return;
                }
                console.log('unsubscribe success');
            });            
        };

        
        const sendMessage= ()=>{
            // json object => string : JSON.stringify()
            // string => json object : JSON.parse()

            const payload = JSON.stringify({ userid: '', msg : state.message });

            // 보낼 토픽, 보내는내용(문자), qos(0~2)
            state.client.publish(`ds/abilitymarket/customer/xx` , payload, 0, error =>{
                if(error){
                    console.log("publish error", error);
                }
            })
        }
        
        onMounted(()=>{
            console.log(state.options.clientId);
            createConnection()
            doSubscribe()
        });

        return {
            state,
            createConnection,
            doSubscribe,
            doUnSubscribe,
            sendMessage
        }
    }
}
</script>

<style lang="scss" scoped>

</style>