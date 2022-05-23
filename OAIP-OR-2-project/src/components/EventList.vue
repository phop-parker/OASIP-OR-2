<!-- @format -->

<script setup>
import EventDetails from './EventDetails.vue'
import DetailIcon from '../icon/DetailIcon.vue'
import DeleteIcon from '../icon/DeleteIcon.vue'
import AddIcon from '../icon/AddIcon.vue'
import { ref, computed } from 'vue'

defineEmits(['deleteEvent', 'updateThisEvent'])
const props = defineProps({
  events: {
    type: Array,
    default: []
  },
  categories: {
    type: Array,
    required: true
  }
})

const formatted_date = new Date().toJSON().slice(0, 10)
const statusDetail = ref(false)
const updatedEvent = ref()

const getDate = (dateTime) => {
  return new Date(dateTime).toDateString()
}
const getTime = (dateTime) => {
  return new Date(dateTime).toTimeString().slice(0, 5)
}

const toggleStatus = () => 
    statusDetail.value === false
    ? (statusDetail.value = true)
    : (statusDetail.value = false)
  
const toggleTofalse = () => {
  statusDetail.value = false
}

const getUpdateEvent = (updateEvent) => {
  updatedEvent.value = updateEvent
  return updatedEvent
}

const dateTime = ref()
const curNameEmail = ref('')
const curCategory = ref('All categories')
const eventStatus = ref('eventStatus')
const filterTopic = ref()

const searchData = computed(() => {
  filterTopic.value = '';
  const filteredEvents = ref(props.events)
  if (curNameEmail.value.length != 0) {
    filteredEvents.value = filteredEvents.value.filter((event) => {
      return (event.bookingEmail.toLowerCase().includes(curNameEmail.value.toLocaleLowerCase()) ||
      event.bookingName.toLowerCase().includes(curNameEmail.value.toLowerCase())
        )
    })
  }
  if (curCategory.value != 'All categories') {
      filteredEvents.value.sort(compareValues('eventStartTime','desc'))
      filteredEvents.value = filteredEvents.value.filter((event) => {
      return event.eventCategoryName === curCategory.value
    })
  }
  if (dateTime.value != undefined) {
    filteredEvents.value.sort(compareValues('eventStartTime'))
    filteredEvents.value = filteredEvents.value.filter((event) => {
      const findDate = new Date(dateTime.value).toDateString()
      const eventDate = new Date(event.eventStartTime).toDateString()
      return eventDate === findDate
    })
  }
  if (eventStatus.value != 'eventStatus') {
      const currentDate = new Date()
      if (eventStatus.value === 'upComingEvents') {
        filteredEvents.value.sort(compareValues('eventStartTime'))
        filteredEvents.value = filteredEvents.value.filter((event) => {
        filterTopic.value = 'on-going or upcoming'
        return currentDate <= new Date(event.eventStartTime) || currentDate <= eventEndTime(event.eventStartTime,event.eventDuration)})
      } else if (eventStatus.value === 'pastEvents') {
        filteredEvents.value.sort(compareValues('eventStartTime','desc'))
        filteredEvents.value = filteredEvents.value.filter((event) => {
        filterTopic.value = 'past'
        return currentDate > new Date(event.eventStartTime)})
      }else if(eventStatus.value === 'allEvents'){
        filteredEvents.value = filteredEvents.value.filter((event) => {
        return currentDate > new Date(event.eventStartTime) || currentDate <= new Date(event.eventStartTime)
      })
  }}
  return filteredEvents.value
})


function eventEndTime(date, minutes) {
  let dateFormat = new Date(date)
  let endDate = new Date(dateFormat.getTime() + minutes * 60 * 1000)
  return endDate
}

const resetFilter = () => {
  curNameEmail.value = ''
  curCategory.value = 'All categories'
  eventStatus.value = 'allEvents'
  dateTime.value = undefined
}

// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/sort

function compareValues(key, order = 'asc') {
  return function innerSort(a, b) {
    if (!a.hasOwnProperty(key) || !b.hasOwnProperty(key)) {
      return 0;
    }

    const varA = (typeof a[key] === 'string')
      ? a[key].toUpperCase() : a[key];
    const varB = (typeof b[key] === 'string')
      ? b[key].toUpperCase() : b[key];

    let comparison = 0;
    if (varA > varB) {
      comparison = 1;
    } else if (varA < varB) {
      comparison = -1;
    }
    return (
      (order === 'desc') ? (comparison * -1) : comparison
    );
  };
}

const selectedEvent = ref({});
const getEventSelected = async(selectedEventId) =>{
  const res = await fetch(`${import.meta.env.BASE_URL}/api/events/${selectedEventId}`)
  // const res = await fetch(`http://10.4.56.95:8080/api/events/${selectedEventId}`)
  if (res.status === 200) {
    selectedEvent.value = await res.json();
    selectedEvent.value.eventStartTime = selectedEvent.value.eventStartTime.replace("@","T")
    toggleStatus()
  } else { console.log("cannot get selectedEvent")}
}
</script>

<template>
  <!-- https://v2.vuejs.org/v2/guide/filters.html -->
  <div class="flex items-center mt-10 justify-center font-Kanit">
    <div
      class="w-full md:w-2/3 shadow p-5 rounded-lg bg-white bg-opacity-40 "
    >
      <div class="grid grid-cols-2 md:grid-cols-3 xl:grid-cols-5 gap-4 mt-4">
        <div class="relative">
          <div class="absolute flex items-center ml-2 h-full">
            <svg
              class="w-4 h-4 fill-current text-primary-black-dark"
              viewBox="0 0 16 16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M15.8898 15.0493L11.8588 11.0182C11.7869 10.9463 11.6932 10.9088 11.5932 10.9088H11.2713C12.3431 9.74952 12.9994 8.20272 12.9994 6.49968C12.9994 2.90923 10.0901 0 6.49968 0C2.90923 0 0 2.90923 0 6.49968C0 10.0901 2.90923 12.9994 6.49968 12.9994C8.20272 12.9994 9.74952 12.3431 10.9088 11.2744V11.5932C10.9088 11.6932 10.9495 11.7869 11.0182 11.8588L15.0493 15.8898C15.1961 16.0367 15.4336 16.0367 15.5805 15.8898L15.8898 15.5805C16.0367 15.4336 16.0367 15.1961 15.8898 15.0493ZM6.49968 11.9994C3.45921 11.9994 0.999951 9.54016 0.999951 6.49968C0.999951 3.45921 3.45921 0.999951 6.49968 0.999951C9.54016 0.999951 11.9994 3.45921 11.9994 6.49968C11.9994 9.54016 9.54016 11.9994 6.49968 11.9994Z"
              ></path>
            </svg>
          </div>
          <input
            type="text"
            v-model="curNameEmail"
            placeholder="Search by Name , Email"
            class="px-8 py-3 w-full rounded-md bg-white shadow border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"
          />
        </div>
        <input
          v-model="dateTime"
          type="date"
          class="pl-2 bg-white px-4 py-2 pr-2 rounded-md shadow border-transparent focus:border-gray-500 focus:bg-white"
        />
        <select
          v-model="eventStatus"
          class="truncate ... px-4 py-3 w-full rounded-md bg-white border-transparent shadow focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"
        >
          <option selected disabled hidden value="eventStatus">Event Status</option>
          <option value="allEvents">All events</option>
          <option value="upComingEvents">Up Coming events & On Going event</option>
          <option value="pastEvents">Past events</option>
        </select>
        <select
          v-model="curCategory"
          class="truncate ... px-4 py-3 w-full rounded-md bg-white border-transparent shadow focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"
        >
          <option>All categories</option>
          <option v-for="(category, index) in categories" :key="index">
            {{ category.eventCategoryName }}
          </option>
        </select>
        <button
          @click="resetFilter"
          class="px-4 py-2 bg-cute-orange hover:bg-pastel-yellow text-gray-800 shadow text-sm font-medium font-Kanit rounded-md"
        >
          Reset Filter
        </button>
      </div>
    </div>
  </div>

  <div
    v-if="statusDetail"
    class="absolute inset-0 flex justify-center items-center z-20"
  >
    <EventDetails
      class="justify-center"
      :event="selectedEvent"
      @closePopUp="toggleStatus()"
      @deleteEvent="$emit('deleteEvent', selectedEvent.id), toggleStatus()"
      @getEditedEvent="getUpdateEvent"
      @updateEvent="$emit('updateThisEvent', updatedEvent), toggleTofalse()"
    />
  </div>
  <!-- https://tailwindcomponents.com/component/table-ui -->
  <div class="flex items-center justify-center font-Kanit ">
    <div class="col-span-12">
      <div
        class="scrollTable lg:overflow-visible mt-8 relative z-0 bg-white bg-opacity-40 pt-4 pl-8 pr-8 pb-8 rounded-2xl"
      >
        <table class="table table-fixed text-blood-bird border-separate space-y-6 style-table ">
          <thead class="text-lg bg-pastel-orange">
            <th style="width:44px" class="p-3">Booking Name</th>
            <th style="width:44px" class="p-3 text-center ">Clinic Category</th>
            <th style="width:40px" class="p-3 text-center ">Date</th>
            <th style="width:24px" class="p-3 text-center ">Time</th>
            <th style="width:24px" class="p-3 text-center ">Duration</th>
            <th style="width:24px" class="p-3 text-center " colspan="2">Action</th>
          </thead>
          <tbody v-if="searchData.length == 0" class="bg-white">
            <tr>
              <td colspan="7" class="font-Kanit" rowspan="2">
                <div class="text-center mt-8">
                  no {{ filterTopic }} scheduled events.
                </div>
                <div class="flex justify-end">
                  <router-link :to="{ name: 'EventBooking' }">
                    <button
                      class="bg-orange-200 hover:bg-pink-200 rounded-full mb-4 mr-4"
                    >
                      <AddIcon />
                    </button>
                  </router-link>
                </div>
              </td>
            </tr>
          </tbody>
          <tbody v-else>
            <tr style="height:80px;"
              v-for="(event, index) in searchData"
              :key="index"
              class="border-b border-blood-bird bg-white text-blood-bird text-center transition hover:z-[1] hover:shadow-xl hover:bg-orange-50 hover:text-black "
            >
              <td class="font-medium capitalize truncate ...">
                {{ event.bookingName }}
              </td>
              <td class="truncate ...">{{ event.eventCategoryName }}</td>
              <td class=" truncate ...">
                {{ getDate(event.eventStartTime) }}
              </td>
              <td class=" truncate ...">
                {{ getTime(event.eventStartTime) }}
              </td>
              <td class=" ">{{ event.eventDuration }} min.</td>
              <td class="justify-center ">
                <DetailIcon
                  class="hover:drop-shadow-md "
                  @click="getEventSelected(event.id)"
                />
              </td>
              <td style="width:20px" class="">
                <DeleteIcon
                  class="hover:drop-shadow-md"
                  @click="$emit('deleteEvent', event.id)"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.table {
  border-spacing: 0 15px;  

}

.table tr {
  border-radius: 20px;
  height: 22%
}
tr td:nth-child(7),
tr th:nth-child(7) {
  border-radius: 0 0.625rem 0.625rem 0;
}

tr td:nth-child(1),
tr th:nth-child(1) {
  border-radius: 0.625rem 0 0 0.625rem;
}

.scrollTable {
  height: 56vh;
  overflow-y: scroll;
  display: block;
  padding-right: 40px;
}

.scrollTable::-webkit-scrollbar {
  width: 0.8vw;
}

.scrollTable::-webkit-scrollbar-thumb {
  background: #fbbf98;
  border-radius: 10px;
}

.style-table {
  width: 960px;
}

</style>
