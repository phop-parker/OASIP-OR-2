<script setup>
import EventList from '../components/EventList.vue';
import DeleteComfirm from '../components/DeleteComfirm.vue'

import { onBeforeMount, ref } from 'vue'


let events = ref([])

// GET
const getEvents = async () => {
    const res = await fetch('http://ip21or2.sit.kmutt.ac.th:8080/api/events')
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

//DELETE
const deleteEvent = async (deleteEventId) => {
    if (confirm("Are you sure you want to delete") == true) {
        const res = await fetch(`http://ip21or2.sit.kmutt.ac.th:8080/api/events/${deleteEventId}`, {
            method: 'DELETE'
        })
        if (res.status === 200) {
            events.value = events.value.filter((event) => event.id !== deleteEventId)
            console.log('delete successfully')
        } else console.log('error cannot delete')
    }
}

</script> 
<template>
    <div>
        <EventList :events="events" @deleteEvent="deleteEvent" />
    </div>
    <div>
        <DeleteComfirm />
    </div>
</template>
 
<style>
</style>
