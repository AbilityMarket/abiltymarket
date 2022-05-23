<template>
  <div class="main">
		<h3>비밀번호 변경</h3>

		
			<Form @submit="onSubmit" :validation-schema="schema">
				<div class="password">
				<label for="email">기존 비밀번호</label>
				<Field v-model="state.pw1" id="email_addr" name="email_addr" type="password" />
				<ErrorMessage name="입력해주세요" />
		</div>
		<div class="new">
			<label for="password">새로운 비밀번호</label>
			<Field id="password" name="acc_pazzword" type="password"/>
			<ErrorMessage name="acc_pazzword" />

			<label for="password">비밀번호 확인</label>
			<Field id="password" name="acc_pazzword" type="password"/>
			<ErrorMessage name="acc_pazzword" />
		</div>

				<button>Submit</button>
			</Form>
			<div>기존 비밀번호</div>
			<input v-model="state.pw1" type="password">
		</div>

		<div class="new">
			<div class="password">
				<div>새로운 비밀번호</div>
				<input v-model="state.pw2" type="password">
			</div>

			<div class="password">
				<div>비밀번호 확인</div>
				<input v-model="state.pw3" type="password">
			</div>
		</div>
		<input type="button" value="비밀번호 변경" class="btn" @click="handleClick">
	<!-- </div> -->
</template>

<script>
import axios from 'axios';
import * as Yup from "yup";
import { Field, Form, ErrorMessage } from "vee-validate";
import { reactive } from '@vue/reactivity';
export default {
	components: {
    Field,
    Form,
    ErrorMessage,
  },
  setup() {

	  const schema = Yup.object().shape({
      email_addr: Yup.string().email().required().label("Email Address"),
      acc_pazzword: Yup.string().min(5).required().label("Your Password"),
    });

    function onSubmit(values) {
      alert(JSON.stringify(values, null, 2));
    }
		const state = reactive({
			pw1:'',
			pw2:'',
			pw3:'',
		})

		const handleClick = async()=>{
			console.log("handleClick");
			// 프론트 유효성 감사


			const url = "/ROOT/api/member/changePw";
			const headers = {
				"content-type": "multipart/form-data" ,
				"token" : sessionStorage.getItem("TOKEN")
				};
			const body = new FormData();
			body.append("pw1", state.pw1);
			body.append("pw2", state.pw2);
			const response = await axios.post(url,body,{headers})
			console.log(response);
			if(response.data.status ===200){
				alert("암호 변경 완료");
				sessionStorage.setItem("TOKEN", response.data.token);
				
			}
		}

    return {
			state,
			handleClick,
			schema,
			onSubmit
		};
  },
};
</script>

<style scoped src="../../assets/css/changepassword2.css">
</style>