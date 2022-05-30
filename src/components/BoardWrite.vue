<template>
  <div>
    <div class="writecontainer">
      <div class="leftside">
        <div class="leftform">

          <div calss="title"><label>제목</label><input type="text"/></div>
          <div calss="category">
              <label>분류</label>
              <!-- <select
                  @change="handleOption($event)"
                  class="big_cate"
                >
                <option value="">카테고리</option>
                  <option
                    v-for="tmp of state.category"
                    :key="tmp"
                    :value="tmp.incategory"
                  >
                  </option>
              </select> -->
              </div>

          <div calss="content">
            <label>내용</label
            ><textarea
              class="content"
              cols="30"
              rows="8"
            ></textarea>
          </div>
          
        </div>
      </div>
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

<style scoped src="../assets/css/boardwrite.css"></style>
