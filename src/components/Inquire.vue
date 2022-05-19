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
                        </ul>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </aside>
        <section>
            <menu-1 v-if="state.menu === 1"></menu-1>
            <menu-2 v-if="state.menu === 2"></menu-2>
            <menu-3 v-if="state.menu === 3"></menu-3>
        </section>
    </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import Menu1 from "./inquire/Menu1.vue";
import Menu2 from "./inquire/Menu2.vue";
import Menu3 from "./inquire/Menu3.vue";

export default {
    components: {
        Menu1, Menu2, Menu3
    },
    setup () {
        const state = reactive({
            menu : 3,
           
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