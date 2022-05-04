<script setup>

import NavBar from './components/NavBar.vue'


</script>


<template>
    <div class="main" :style="{ 'backgroundImage': 'url(../assets/bgwave.png)' }">
        <NavBar />
        <router-view></router-view>
    </div>
</template>



<style >
@import url('https://fonts.googleapis.com/css2?family=Kanit:wght@300&display=swap');


/* .main {
    background: linear-gradient(360deg, rgba(253, 211, 184, 1) 0%, rgba(255, 255, 255, 1) 100%);
} */
</style>
