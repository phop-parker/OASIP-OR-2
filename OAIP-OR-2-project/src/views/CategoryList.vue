<script setup>
import { onBeforeMount, ref, computed } from 'vue'
import CategoriesList from '../components/CategoriesList.vue';


let categories = ref([])

// GET
const getCategories = async () => {
    // const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCategories/forBooking`)
    const res = await fetch(`http://10.4.56.95:8080/api/eventCategories`)

    if (res.status === 200) {
        console.log(res);
        categories.value = await res.json()
    } else
        console.log("error, cann't get data");
}
onBeforeMount(async () => {
    await getCategories()
})
</script>
 
<template>
    <CategoriesList :categories="categories" />
</template>
 
<style>
</style>