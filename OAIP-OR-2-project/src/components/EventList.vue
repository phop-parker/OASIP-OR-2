<script setup>
import EventDetails from './EventDetails.vue';
import DetailIcon from './DetailIcon.vue';
import DeleteConfirm from './DeleteEvent.vue';
import AddIcon from './AddIcon.vue';

import { ref } from 'vue';
import DeleteEvent from './DeleteEvent.vue';

defineEmits(['deleteEvent'])

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
        eventDuration: event.eventDuration,
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



const delthis = "delete";


</script>
 
<template>
    <div v-show="statusDetail">
        <EventDetails :event="curEvent" @closePopUp="toggleStatus()" />
    </div>
    <div class="relative overflow-x-auto shadow-md mt-12 mr-80 ml-20 rounded-2xl">
        <table class="w-full font-Kanit text-lg text-center text-blood-bird">
            <thead class="text-lg bg-gradient-to-r from-pink-200 to-orange-300">
                <th scope="col" class="px-6 py-3">Booking Name</th>
                <th scope="col" class="px-6 py-3">Clinic Category</th>
                <th scope="col" class="px-6 py-3">Date</th>
                <th scope="col" class="px-6 py-3">Time</th>
                <th scope="col" class="px-6 py-3">Duration</th>
                <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
            </thead>
            <tbody v-if="events.length == 0">
                <tr>
                    <td colspan="8" class="font-Kanit">
                        <p class="mt-4">no scheduled events.</p>
                    </td>
                </tr>
                <tr>
                    <td colspan="8">
                        <div class="flex justify-end">
                            <router-link :to="{ name: 'EventBooking' }">
                                <button class=" bg-orange-200 hover:bg-pink-200 rounded-full mb-4 mr-4">
                                    <AddIcon />
                                </button>
                            </router-link>
                        </div>
                    </td>

                </tr>
            </tbody>
            <tbody v-else>
                <tr v-for="(event, index) in events" :key="index"
                    class="border-b border-blood-bird  text-black hover:bg-orange-50">
                    <td>{{ event.bookingName }}</td>
                    <td>{{ event.eventCategory.eventCategoryName }}</td>
                    <td>{{ getDate(event.eventStartTime) }}</td>
                    <td>{{ getTime(event.eventStartTime) }}</td>
                    <td>{{ event.eventDuration }} minutes </td>
                    <td>
                        <DetailIcon class="hover:drop-shadow-md" @click="showDetailsToggle(event)" />
                    </td>
                    <td>
                        <DeleteEvent @click="$emit('deleteEvent', event.id)" />
                    </td>
                    <td> </td>
                </tr>
            </tbody>
        </table>
    </div>

    <DeleteComfirm :ConfirmationTopic="delthis" />
</template>
 
<style>
</style>
