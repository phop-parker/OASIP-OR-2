<script setup>

import { onBeforeMount, ref, computed } from 'vue'
import AddNewEvent from '../components/AddNewEvent.vue';

let categories = ref([])
// get
const getCategories = async () => {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCatagories`)
    if (res.status === 200) {
        console.log(res);
        categories.value = await res.json()
    } else
        console.log("error, cann't get data");
}
onBeforeMount(async () => {
    await getCategories()
    console.log(categories)
})


// create
const createNewEvent = async (newEvent) => {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/events`, {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            bookingName: newEvent.bookingName,
            bookingEmail: newEvent.bookingEmail,
            eventStartTime: newEvent.eventStartTime,
            eventDuration: newEvent.eventDuration,
            categoryId: newEvent.categoryId,
            eventNotes: newEvent.eventNotes
        }
        )
    })
    if (res.status === 200 || res.status === 201) {
        console.log(res.status)
        alert('added suceccfully')
    } else {
        console.log(res.status)
        console.log('error cannot add')
    }

}

</script>
 
<template>
    <div class="">
        <!-- <CategoryListVue :eventCategories="categories" /> -->
        <AddNewEvent :eventCategories="categories" @addNewEvent="createNewEvent" />
    </div>
</template>

<style scoped>
.bgimage {
    background-image: url("../assets/bg-wave.png");
    background-repeat: no-repeat;
    /* background-size: contain, cover; */
    background-size: 100% 100%;

}
</style>