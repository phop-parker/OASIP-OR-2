<script setup>
import EventDetails from "./EventDetails.vue";
import DetailIcon from "./DetailIcon.vue";
import DeleteIcon from "./DeleteIcon.vue";
import AddIcon from "./AddIcon.vue";
import { ref } from 'vue';

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
        eventStartTime: getTime(event.eventStartTime),
        eventStartDate: getDate(event.eventStartTime),
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
</script>
 
<template>
    <div>
        <div v-show="statusDetail" class="absolute inset-0 flex justify-center items-center z-20">
            <EventDetails :event="curEvent" @closePopUp="toggleStatus()" />
        </div>
        <div class="overflow-x-auto shadow-md mt-8 ml-20 mr-20  rounded-2xl relative z-0 drop-shadow-2xl">
            <table class="w-full font-Kanit text-lg text-center text-blood-bird ">
                <thead class="text-lg gradient-color">
                    <th scope="col" class="px-6 py-3">Booking Name</th>
                    <th scope="col" class="px-6 py-3">Clinic Category</th>
                    <th scope="col" class="px-6 py-3">Date</th>
                    <th scope="col" class="px-6 py-3">Time</th>
                    <th scope="col" class="px-6 py-3">Duration</th>
                    <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                    <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                    <th scope="col" class="px-6 py-3"><span class="sr-only">Edit</span></th>
                </thead>
                <tbody v-if="events.length == 0" class="bg-white">
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
                        class="border-b border-blood-bird bg-white text-black hover:bg-orange-50">
                        <td>{{ event.bookingName }}</td>
                        <td>{{ event.eventCategory.eventCategoryName }}</td>
                        <td>{{ getDate(event.eventStartTime) }}</td>
                        <td>{{ getTime(event.eventStartTime) }}</td>
                        <td>{{ event.eventDuration }} minutes </td>
                        <td>
                            <DetailIcon class="hover:drop-shadow-md hover:drop-shadow-md"
                                @click="showDetailsToggle(event)" />
                        </td>
                        <td>
                            <DeleteIcon @click="$emit('deleteEvent', event.id)" />
                        </td>
                        <td> edit </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to left, #FBBF98, #FFC2C2);
}


.gradient-delete-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>
