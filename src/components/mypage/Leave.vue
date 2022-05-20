<template>
  <div class="main">
    <h3>회원탈퇴</h3>
    <p>능력마켓 서비스를 이용 해주셔서 감사합니다.</p>
    <div class="service">
      <p style="color: black; margin-bottom: 10px">탈퇴를 하시면</p>
      <p>
        능력마켓에서 이용하신 모든 회원정보(쿠폰, 포인트, 게시물)가 삭제되며, 더
        이상 서비스를 이용할 수 없게 됩니다. 또한 삭제된 정보는 복구할 수
        없으며, 탈퇴에 대한 다른 궁금한 사항이 있으시면 1:1문의를 통해 안내
        받으실 수 있습니다. * 회원 탈퇴 시 동일 이메일로 재가입이 불가합니다.
      </p>
    </div>

    <h4>
      탈퇴 하시려는 사유를 작성해주세요. 능력마켓 서비스 운영에 많은 도움이
      됩니다.
    </h4>

    <textarea
      placeholder="탈퇴 사유를 작성해주세요"
      class="reason"
      name=""
      id=""
      cols="30"
      rows="5"
    ></textarea>

    <div class="new">
      <div class="password">
        <div>비밀번호 입력</div>
        <input type="password" />
      </div>

      <div class="password">
        <div>비밀번호 확인</div>
        <input type="password" />
      </div>
    </div>
    <div class="btn_box">
    <input type="button" value="서비스 계속 이용하기" class="btn" @click="handleClick2">
    <input type="button" value="탈퇴하기" class="btn btn1" @click="handleClick1">
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    return {};
  },
};
</script>

<style scoped src="../../assets/css/withdrawal2.css">
</style>