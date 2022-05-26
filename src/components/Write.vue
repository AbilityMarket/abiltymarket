<template>
    <div>
      <div class="writecontainer">
      <div class="form">
        <div class="title" style="margin-bottom:10px;">
        <label>제목</label><input type="text"/>
        </div>
        <div class="writer" style="margin-bottom:10px;">
        <label>작성자</label><input type="text" />
        </div>
      </div>

        <ckeditor :editor="state.editor" v-model="state.editorData" @ready="onReady"></ckeditor>
        <button class="btn_write">글쓰기</button>
    </div>
    </div>
</template>

<script>

import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import UploadAdapter from './UploadAdapter.js';
import CKEditor from '@ckeditor/ckeditor5-vue'
import { reactive } from '@vue/reactivity';

export default {
    components: { ckeditor: CKEditor.component },


    setup () {

      const state = reactive({
            editor       : ClassicEditor, // ckeditor종류
            editorData   : '<p>미리추가되는내용테스트</p>',
      })

      const onReady = ( editor ) => {
          console.log(editor);
          editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
                return new UploadAdapter( loader );
          };
          
          
          editor.editing.view.change( writer => {
            writer.setStyle( 'height', '600px', editor.editing.view.document.getRoot() );
          });
      }

      return {state, onReady}
    },

}
</script>

<style lang="css" scoped>

.writecontainer{
  margin-top:50px;
}

input {
  border-bottom: 2px solid #3476d8;
}

input:focus {
  outline: none;
}
.form {
  display: flex;
  flex-direction: column;
}

.ck.ck-editor {
    	max-width: 500px;
	}
.ck-editor__editable {
	    min-height: 300px;
	}

.btn_write {
  all: unset;
  width: 380px;
  height: 45px;
  font-size: 15px;
  background-color: #3476d8;
  color: #ffffff;
  font-family: "GmarketSansLight";
  border-radius: 5px;
  text-align: center;
  cursor: pointer;
  margin-top:30px;
}



</style>