<script setup>

import NavBar from './components/NavBar.vue'


</script>


<template>
    <div>
        <NavBar />
        <router-view></router-view>
    </div>
</template>



<style >
@import url('https://fonts.googleapis.com/css2?family=Kanit:wght@300&display=swap');

/* .bgimage {
    background-image: url("../assets/bg-project.jpg");
    background-repeat: no-repeat;
    background-size: contain, cover;
    background-size: 100% 100%;
    background-attachment: fixed;


} */

/* .main {
    background: linear-gradient(360deg, rgba(253, 211, 184, 1) 0%, rgba(255, 255, 255, 1) 100%);
} */
</style>
