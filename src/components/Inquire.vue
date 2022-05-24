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
                            <li @click="handleMenu(2)">1:1문의</li>
                            <li @click="handleMenu(3)">문의하기</li>
                            <li @click="handleMenu(4)">상세페이지</li>
                            <li @click="handleMenu(5)">비밀글</li>
                            <li @click="handleMenu(6)">----</li>
                        </ul>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </aside>
        <section>
            <menu-1 v-if="state.menu === 1"></menu-1>
            <menu-2 v-if="state.menu === 2"></menu-2>
            <menu-3 v-if="state.menu === 3"></menu-3>
            <menu-4 v-if="state.menu === 4"></menu-4>
            <menu-5 v-if="state.menu === 5"></menu-5>
            <menu-6 v-if="state.menu === 6"></menu-6>
        </section>
    </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import Menu1 from "./inquire/Menu1.vue";
import Menu2 from "./inquire/Menu2.vue";
import Menu3 from "./inquire/Menu3.vue";
import Menu4 from "./inquire/Menu4.vue";
import Menu5 from "./inquire/Menu5.vue";
import Menu6 from "./inquire/Menu6.vue";

export default {
    components: {
        Menu1, Menu2, Menu3, Menu4, Menu5, Menu6
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