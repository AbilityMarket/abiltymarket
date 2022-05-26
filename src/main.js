import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import stores from './stores/index.js';
import VueSweetalert2 from 'vue-sweetalert2';

//router 설정
import routes from './routes/index.js';

//Datepicker
import VCalendar from 'v-calendar';

//CKEditor
import CKEditor from '@ckeditor/ckeditor5-vue';

loadFonts()

// 객체 생성
const app = createApp(App);

// 필요한 라이브러리
app.use(vuetify);
app.use(routes);
app.use(stores)
app.use(VueSweetalert2);
app.mount('#app');

app.use(VCalendar, {})

app.use(CKEditor);

