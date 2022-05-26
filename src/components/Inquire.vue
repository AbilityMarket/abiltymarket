<template>
    <div class="main">
        <aside>
            <v-expansion-panels class="accordion" multiple variant="accordion">
                <v-expansion-panel class="elevation-1">
                    <v-expansion-panel-title>
                        <p style="margin-left: 8px">고객센터</p>
                    </v-expansion-panel-title>
                    <v-expansion-panel-text>
                        <ul class="inquire">
                            <li @click="handleMenu(1)">자주묻는질문</li>
                            <li @click="handleMenu(2)">문의게시판</li>
                            <!-- <li @click="handleMenu(3)">문의글쓰기</li> -->
                            <!-- <li @click="handleMenu(4)">상세페이지</li> -->
                            <li @click="handleMenu(5)">비밀글</li>
                            <li @click="handleMenu(6)">----</li>
                        </ul>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </aside>
        <section>
            <menu-1 v-if="state.menu === 1"></menu-1>
            <inquire-list v-if="state.menu === 2"></inquire-list>
            <inquire-write v-if="state.menu === 3"></inquire-write>
            <!-- <select-one v-if="state.menu === 4"></select-one> -->
            <secret v-if="state.menu === 5"></secret>
            <menu-6 v-if="state.menu === 6"></menu-6>
        </section>
    </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import Menu1 from "./inquire/Menu1.vue";
import InquireList from "./inquire/InquireList.vue";
// import InquireWrite from "./inquire/InquireWrite.vue";
// import SelectOne from "./inquire/SelectOne.vue";
import Secret from "./inquire/Secret.vue";
import Menu6 from "./inquire/Menu6.vue";
import InquireWrite from './inquire/InquireWrite.vue';

export default {
    components: {
        Menu1, InquireList, Secret, Menu6,
        InquireWrite
        // SelectOne, InquireWrite,
    },
    setup () {
        const state = reactive({
            menu : 2,
           
        })
        const handleMenu = (idx) => {
            state.menu = idx;
        }

        return {state, handleMenu}
    }
}
</script>

<style scoped src="../assets/css/inquire.css">

</style>