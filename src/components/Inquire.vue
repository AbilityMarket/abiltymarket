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
                        </ul>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </aside>
        <section>
            <faq-list v-if="state.menu === 1"></faq-list>
            <inquire-list v-if="state.menu === 2"></inquire-list>
        </section>
    </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import faqList from "./inquire/faqList.vue";
import InquireList from "./inquire/InquireList.vue";
import Secret from "./inquire/Secret.vue";
import InquireWrite from './inquire/InquireWrite.vue';

export default {
    components: {
        faqList, InquireList, Secret, InquireWrite
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