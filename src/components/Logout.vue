<template>
    <div>
    <!-- 라우터에 등록해야함 -->
    </div>
</template>

<script>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
export default {
    setup () {

        const router = useRouter();
        const store = useStore();
        
        onMounted( () => {
            sessionStorage.removeItem("TOKEN");
            store.commit('setLogged', false); // ('메소드명', 변경할 값)
                router.push({name:'Home'});
        });

        return {}
    }
}
</script>

<style lang="scss" scoped>

</style>