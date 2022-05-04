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

// DELETE
const deleteEvent = async (deleteEventId) => {
    if (deleteEventId > 0) {
        const res = await fetch(`http://ip21or2.sit.kmutt.ac.th:8080/api/events/${deleteEventId}`, {
            method: 'DELETE'
        })
        if (res.status === 200) {
            events.value = events.value.filter((event) => event.id !== deleteEventId);
            alert('delete successfully');
            cancelDelete();
        } else {
            alert('error cannot delete');
            cancelDelete();
        }

    } else {
        alert('no event to delete');
        cancelDelete();
    }
}


const deleteConfirm = ref(false);
const deleteThisEvent = ref(0);
const deleteEventConfirm = (eventId) => {
    deleteConfirm.value = true
    deleteThisEvent.value = eventId
}

const cancelDelete = () => {
    deleteConfirm.value = false
    deleteThisEvent.value = 0;

}


</script> 
<template>
    <div>

        <EventList :events="events" @deleteEvent="deleteEventConfirm" />


        <DeleteComfirm v-show="deleteConfirm" @cancelConfirmation="cancelDelete"
            @yesConfirmation="deleteEvent(deleteThisEvent)" />

    </div>
</template>
 
<style>
</style>
