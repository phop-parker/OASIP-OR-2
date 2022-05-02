<script setup>
import EventDetails from './EventDetails.vue';
import DetailIcon from './DetailIcon.vue';


import { ref } from 'vue';


const props = defineProps({
    events: {
        type: Array,
        default: []
    }
})


const getDate = (dateTime) => {
    const date = new Date(dateTime);
    return date.toDateString()
}

const getTime = (dateTime) => {
    const date = new Date(dateTime);
    return date.toTimeString().slice(0, 8)
}

const curEvent = ref();
const statusDetail = ref(false);

const showDetailsToggle = (event) => {
    curEvent.value = {
        bookingEmail: event.bookingEmail,
        bookingName: event.bookingName,
        categoryId: event.eventCategory.eventCategoryName,
        eventNotes: event.eventNotes,
        eventStartTime: event.eventStartTime,
        id: event.id,
    };
    console.log(curEvent.value)
    toggleStatus()
}

const toggleStatus = () => {
    if (statusDetail.value == true) {
        statusDetail.value = false;
    } else {
        statusDetail.value = true;
    }
}
</script>
 
<template>
    <div v-show="statusDetail">
        <EventDetails :event="curEvent" @closePopUp="toggleStatus()" />
    </div>
    <div class="box-content p-8 mt-5 ml-24 mr-24 bg-white rounded-md drop-shadow-2xl">
        <div class="relative overflow-x-auto shadow-md ">
            <table class="w-full font-Kanit text-lg text-center">
                <thead class="text-lg text-dark-green border-b-2 border-darker-green ">
                    <th scope="col" class="px-6 py-3">Booking Name</th>
                    <th scope="col" class="px-6 py-3">Clinic Category</th>
                    <th scope="col" class="px-6 py-3">Date</th>
                    <th scope="col" class="px-6 py-3">Time</th>
                    <th scope="col" class="px-6 py-3">Duration</th>
                    <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                    <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                    <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                </thead>
                <tbody>
                    <tr v-if="events.length == 0">
                        <td colspan="8" class="font-Kanit">no scheduled events.</td>
                    </tr>
                    <tr v-else v-for="(event, index) in events" :key="index"
                        class="border-b border-dark-green text-black">
                        <td>{{ event.bookingName }}</td>
                        <td>{{ event.eventCategory.eventCategoryName }}</td>
                        <td>{{ getDate(event.eventStartTime) }}</td>
                        <td>{{ getTime(event.eventStartTime) }}</td>
                        <td>{{ event.eventDuration }} minutes </td>
                        <td>
                            <DetailIcon class="hover:drop-shadow-md" @click="showDetailsToggle(event)" />
                        </td>
                        <td> </td>
                        <td> </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

</template>
 
<style>
</style>
