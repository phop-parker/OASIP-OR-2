<script setup>
import EventList from '../components/EventList.vue';
import DeleteComfirm from '../components/DeleteComfirm.vue'

import { onBeforeMount, ref } from 'vue'


let events = ref([])

// GET
const getEvents = async () => {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/events`)
    // const res = await fetch(`http://10.4.56.95:8080/api/events`)
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
    await getCategories()
    console.log(events)
})

// DELETE
const deleteEvent = async (deleteEventId) => {
    if (deleteEventId > 0) {
        const res = await fetch(`${import.meta.env.BASE_URL}/api/events/${deleteEventId}`, {
            // const res = await fetch(`http://10.4.56.95:8080/api/events/${deleteEventId}`, {
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


let categories = ref([])

const category = (categoryName) => {
    for (let i = 0; i < categories.value.length; i++) {
        if (categoryName == categories.value[i].eventCategoryName) {
            category.value = categories.value[i];
        }
    }
    console.log(category.value);
    return category.value;
}

// get
const getCategories = async () => {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCatagories`)
    // const res = await fetch(`http://10.4.56.95:8080/api/eventCategories`)
    if (res.status === 200) {
        console.log(res);
        categories.value = await res.json()
    } else
        console.log("error, cann't get data");
}

const updateEvent = async (updateEvent) => {
    console.log(updateEvent);
    const res = await fetch(`${import.meta.env.BASE_URL}/api/events/${updateEvent.id}`, {
        // const res = await fetch(`http://10.4.56.95:8080/api/events/${updateEvent.id}`, {
        method: 'PATCH',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            eventStartTime: updateEvent.eventStartTime,
            eventNotes: updateEvent.eventNotes
        })
    })
    if (res.status === 200) {
        const modifyEvent = await res.json()
        console.log(modifyEvent)
        const date = new Date(modifyEvent.eventStartTime)
        events.value = events.value.map(
            (event) =>
                event.id === modifyEvent.id
                    ? { ...event, eventStartTime: date, eventNotes: modifyEvent.eventNotes }
                    : event
        )
        console.log('updated suceccfully')
        alert('updated suceccfully')
    } else {
        console.log('error cannot add')
        alert('error cannot add')
    }
}

</script> 
<template>
    <div class="">

        <div class="">
            <EventList :events="events" @deleteEvent="deleteEventConfirm" @updateThisEvent="updateEvent" />
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
