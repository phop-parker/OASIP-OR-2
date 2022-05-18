<script setup>
import EventDetails from "./EventDetails.vue";
import DetailIcon from "./DetailIcon.vue";
import DeleteIcon from "./DeleteIcon.vue";
import AddIcon from "./AddIcon.vue";
import { ref } from 'vue';

defineEmits(['deleteEvent', 'updateThisEvent'])
const props = defineProps({
    events: {
        type: Array,
        default: []
    }
})

const getProperDate = (dateTime) => {
    console.log(dateTime)
    return dateTime.toString().replace("@", "T")
}

const getDate = (dateTime) => {
    console.log("-----------------date-----------------")
    const date = new Date(dateTime);
    console.log("this is datetime after change" + date);
    return date.toDateString()
}
const getTime = (dateTime) => {
    console.log("-----------------time-----------------")
    const date = new Date(dateTime);
    console.log("this is datetime after change" + date);
    return date.toTimeString().slice(0, 5)
}
const curEvent = ref();
const statusDetail = ref(false);
const showDetailsToggle = (event) => {
    console.log('show datail toggle hit')
    curEvent.value = {
        bookingEmail: event.bookingEmail,
        bookingName: event.bookingName,
        categoryId: event.eventCategoryName,
        eventNotes: event.eventNotes,
        eventStartTime: getProperDate(event.eventStartTime),
        eventDuration: event.eventDuration,
        id: event.id,
    };
    toggleStatus()
}
const toggleStatus = () => {
    console.log('toggleStatus hit')
    if (statusDetail.value == true) {
        statusDetail.value = false;
    } else {
        statusDetail.value = true;
    }
}

const toggleTofalse = () => {
    console.log('toggleTofalse hit')
    statusDetail.value = false;
}

const updatedEvent = ref()

const getUpdateEvent = (updateEvent) => {
    console.log('getUpdateEvent is working right now here is update event-----')
    console.log(updateEvent)
    updatedEvent.value = updateEvent;
    console.log(' here is update event that sendeing to views-----')
    console.log(updatedEvent.value)
    return updatedEvent;
}
</script>
  
<template>
    <div class="flex items-center mt-10 justify-center ">
        <div class="w-full md:w-2/3 shadow p-5  rounded-lg bg-white bg-opacity-40 rounded-2xl ">
            <div class="relative">
                <div class="absolute flex items-center ml-2 h-full">
                    <svg class="w-4 h-4 fill-current text-primary-gray-dark" viewBox="0 0 16 16" fill="none"
                        xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M15.8898 15.0493L11.8588 11.0182C11.7869 10.9463 11.6932 10.9088 11.5932 10.9088H11.2713C12.3431 9.74952 12.9994 8.20272 12.9994 6.49968C12.9994 2.90923 10.0901 0 6.49968 0C2.90923 0 0 2.90923 0 6.49968C0 10.0901 2.90923 12.9994 6.49968 12.9994C8.20272 12.9994 9.74952 12.3431 10.9088 11.2744V11.5932C10.9088 11.6932 10.9495 11.7869 11.0182 11.8588L15.0493 15.8898C15.1961 16.0367 15.4336 16.0367 15.5805 15.8898L15.8898 15.5805C16.0367 15.4336 16.0367 15.1961 15.8898 15.0493ZM6.49968 11.9994C3.45921 11.9994 0.999951 9.54016 0.999951 6.49968C0.999951 3.45921 3.45921 0.999951 6.49968 0.999951C9.54016 0.999951 11.9994 3.45921 11.9994 6.49968C11.9994 9.54016 9.54016 11.9994 6.49968 11.9994Z">
                        </path>
                    </svg>
                </div>

                <input type="text" placeholder="Search by Name , Email"
                    class="px-8 py-3 w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm">
            </div>
            <div>
                <div class="grid grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-4 mt-4">

                    <!--    class="px-4 py-3 w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"> -->
                    <input v-model="dateTime" type="date"
                        class="pl-2  bg-gray-100 hover:border-gray-500 px-4 py-2 pr-2 rounded-md focus:border-gray-500 focus:bg-white  shadow leading-tight focus:outline-none focus:shadow-outline" />

                    <select v-model="curCategory"
                        class="px-4 py-3 w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm">
                        <option value="">Select Category</option>
                        <option v-for="(category, index) in eventCategories" :key="index">
                            {{ category.eventCategoryName }}
                        </option>
                    </select>

                    <select
                        class="px-4 py-3 w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm">
                        <option value="">Event Status</option>
                        <option value="1000">RM 1000</option>
                        <option value="2000">RM 2000</option>
                        <option value="3000">RM 3000</option>
                        <option value="4000">RM 4000</option>
                    </select>
                    <select
                        class="px-4 py-3 w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm">
                        <option value="">Any Price</option>
                        <option value="1000">RM 1000</option>
                        <option value="2000">RM 2000</option>
                        <option value="3000">RM 3000</option>
                        <option value="4000">RM 4000</option>
                    </select>
                </div>
                <div class="flex justify-end mt-4 ">
                    <button class="px-4 py-2  bg-rose  hover:bg-gray-200 text-gray-800 text-sm font-medium rounded-md">
                        Reset Filter
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div v-if="statusDetail" class=" absolute inset-0 flex justify-center items-center z-20">
        <EventDetails class="justify-center" :event="curEvent" @closePopUp="toggleStatus()"
            @deleteEvent="$emit('deleteEvent', curEvent.id), toggleStatus()" @getEditedEvent="getUpdateEvent"
            @updateEvent="$emit('updateThisEvent', updatedEvent), toggleTofalse()" />
    </div>

    <div class="flex items-center justify-center">
        <div class="col-span-12">
            <div class="scrollTable lg:overflow-visible mt-8 relative z-0 bg-white bg-opacity-40 p-8 rounded-2xl ">

                <table class="table text-blood-bird border-separate space-y-6">
                    <thead class="text-lg bg-pastel-orange">
                        <th class="p-3 ">Booking Name</th>
                        <th class="p-3 text-left">Clinic Category</th>
                        <th class="p-3 text-left">Date</th>
                        <th class="p-3 text-left">Time</th>
                        <th class="p-3 text-left">Duration</th>
                        <th colspan="2" class="p-3 text-left">Action</th>
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
                            class="border-b w-full border-blood-bird bg-white text-black text-center hover:bg-orange-50">
                            <td class="p-3 font-medium capitalize">{{ event.bookingName }}</td>
                            <td class="p-3 text-left">{{ event.eventCategoryName }}</td>
                            <td class="p-3 text-left">{{ getDate(event.eventStartTime) }}</td>
                            <td class="p-3 text-left">{{ getTime(event.eventStartTime) }}</td>
                            <td class="p-3 text-left">{{ event.eventDuration }} minutes </td>
                            <td class="">
                                <DetailIcon class="hover:drop-shadow-md" @click="showDetailsToggle(event)" />
                            </td>
                            <td class="">
                                <DeleteIcon class="hover:drop-shadow-md" @click="$emit('deleteEvent', event.id)" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
/* .gradient-color {
    background-image: linear-gradient(to left, #FBBF98, #FFC2C2);
} */


.gradient-delete-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}

.table {
    border-spacing: 0 15px;
}


.table tr {
    border-radius: 20px;
}

tr td:nth-child(n + 7),
tr th:nth-child(n + 7) {
    border-radius: 0 0.625rem 0.625rem 0;
}

tr td:nth-child(1),
tr th:nth-child(1) {
    border-radius: 0.625rem 0 0 0.625rem;
}

.scrollTable {
    height: 48vh;
    overflow-y: scroll;
    display: block;
    padding-right: 40px;
}

.scrollTable::-webkit-scrollbar {
    width: 0.8vw;
}

.scrollTable::-webkit-scrollbar-track {
    background: #d28d4c;
    border-radius: 10px;
}

.scrollTable::-webkit-scrollbar-thumb {
    background: #FBBF98;
    border-radius: 10px;
}
</style>

