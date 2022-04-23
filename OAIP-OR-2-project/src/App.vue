<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
// import HelloWorld from './components/HelloWorld.vue'
import { onBeforeMount, ref } from 'vue'
let categories = ref([])
// GET
const getCategories= async () =>{
    const res = await fetch('/api/eventCatagories')
    if(res.status === 200) {
    console.log(res);
    categories.value = await res.json()
    }else   
    console.log("error, cann't get data");
}
onBeforeMount( async () => {
   await getCategories()
})
</script>


<template>
{{categories}}
</template>


<style>

</style>
