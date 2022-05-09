<script setup>
import EventList from '../components/EventList.vue';
import DeleteComfirm from '../components/DeleteComfirm.vue'

import { onBeforeMount, ref } from 'vue'


let events = ref([])

// GET
const getEvents = async () => {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/events`)
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
        const res = await fetch(`${import.meta.env.BASE_URL}/api/events/${deleteEventId}`, {
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
    <div class="">

        <div class="">
            <EventList :events="events" @deleteEvent="deleteEventConfirm" />
        </div>

        <div v-show="deleteConfirm" class="absolute inset-0 flex justify-center items-center z-20">
            <DeleteComfirm @cancelConfirmation="cancelDelete" @yesConfirmation="deleteEvent(deleteThisEvent)" />
        </div>
    </div>


</template>
 
<style scoped>
.bgimage {
    background-image: url("../assets/bgfull.png");
    background-size: contain, cover;
    background-size: 100% 100%;
    background-position: right bottom;

}
</style>
