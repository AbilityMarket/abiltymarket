<template>
    <div class="select">
        <div class="selectitem">
            <select class="selectwi">
                <option v-for="tmp of items" :key="tmp" :value="tmp.no">
                    <!-- {{tmp.title}} -->
                </option>

               
            </select>
            
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue';
export default {
   setup () {
        const state = reactive({
                
        })


        return {state, }
    }
}
</script>

<style scoped>
.select {
    display : flex; 
    justify-content : center; 
}
.selectitem {
    width : 600px;
    background : rgb(210, 238, 252);
    height : 600px;
}
.selectwi {
    margin-left: 10px;
    width : 400px;
    height : 30px;
}
</style>