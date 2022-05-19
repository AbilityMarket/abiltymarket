<template>
    <div>
        <button style="background: white; border: 1px solid #6666; 
          box-sizing: border-box; padding: 4px; color: gray; border-radius: 5px; margin-top: 15px;">문의하기</button>
        <v-table density="compact">
            <thead>
                <tr>
                    <th class="text-left">
                    번호
                    </th>
                    <th class="text-left">
                    제목
                    </th>
                    <th class="text-left">
                    내용
                    </th>
                    <th class="text-left">
                    구매 | 판매
                    </th>
                    <th class="text-left">
                    완료 | 미완
                    </th>
                    <th class="text-left">
                    작성일
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="tmp in items" :key="tmp">
                    <td>{{ tmp.inqno }}</td>
                    <td>{{ tmp.inqtitle }}</td>
                    <td>{{ tmp.inqcontent }}</td>
                    <td>{{ tmp.inqselect }}</td>
                    <td>{{ tmp.inqtype }} <v-img src="../../assets/images/자물쇠.png" style="width:20px; height:20px; float:right; "/></td>
                    <td>{{ tmp.inqregdate }}</td>
                </tr>
            </tbody>
            <div class="text-center"> 
                <v-pagination
                v-model="page"
                :length="3"
                prev-icon="mdi-menu-left"
                next-icon="mdi-menu-right"
                ></v-pagination>
            </div>
        </v-table>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    created() {
         this.handleData();
    },
    data () {
        return {
            page : 1,
            items : [],
            // token : sessionStorage.getItem("TOKEN")

            // headers: [
            // {
            //     text: 'Dessert (100g serving)',
            //     align: 'start',
            //     sortable: false,
            //     value: 'name',
            // },
            // { text: 'Calories', value: 'calories' },
            // { text: 'Fat (g)', value: 'fat' },
            // { text: 'Carbs (g)', value: 'carbs' },
            // { text: 'Protein (g)', value: 'protein' },
            // { text: 'Iron (%)', value: 'iron' },
            // ],
            // desserts: [
            //     {
            //         name: 'Frozen Yogurt',
            //         calories: 159,
            //         fat: 6.0,
            //         carbs: 24,
            //         protein: 4.0,
            //         iron: '1%',
            //     },
            //     {
            //         name: 'Ice cream sandwich',
            //         calories: 237,
            //         fat: 9.0,
            //         carbs: 37,
            //         protein: 4.3,
            //         iron: '1%',
            //     },
            // ],
        }
    },
    methods : {
        async handleData() {
            const url = `/ROOT/api/inquire/selectlist`;
            const headers = {"Content-Type":"application/json",
                token : this.token
            };
            
            const response = await axios.get(
                url, { headers : headers }
            );
            console.log(response.data);
            if(response.data.status === 200) {
                this.items = response.data.result;
               
            }
        },
    }
}
</script>


<style lang="scss" scoped>

</style>