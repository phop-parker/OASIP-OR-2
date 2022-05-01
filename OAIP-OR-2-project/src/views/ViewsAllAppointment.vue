<script setup>
import EventList from '../components/EventList.vue';
import EventDetails from '../components/EventDetails.vue';

import { onBeforeMount, ref } from 'vue'

let events = ref([])

// GET
const getEvents = async () => {
    const res = await fetch('http://10.4.56.95:8080/api/events')
    if (res.status === 200) {
        console.log("response reply")       
        console.log(res);;
	console.log(events.value);
        events.value = await res.json()
    } else
        console.log("error, cann't get data");
}

onBeforeMount(async () => {
    await getEvents()
    console.log(events)
})
</script>
 
<template>
    <div>
        <EventList :events="events" />
    </div>
</template>
 
<style>
</style>
