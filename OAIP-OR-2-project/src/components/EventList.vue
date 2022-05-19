<!-- @format -->

<script setup>
import EventDetails from './EventDetails.vue'
import DetailIcon from './DetailIcon.vue'
import DeleteIcon from './DeleteIcon.vue'
import AddIcon from './AddIcon.vue'
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

const getProperDate = (dateTime) => {
  console.log(dateTime)
  return dateTime.toString().replace('@', 'T')
}

const getDate = (dateTime) => {
  console.log('-----------------date-----------------')
  const date = new Date(dateTime)
  console.log('this is datetime after change' + date)
  return date.toDateString()
}
const getTime = (dateTime) => {
  console.log('-----------------time-----------------')
  const date = new Date(dateTime)
  console.log('this is datetime after change' + date)
  return date.toTimeString().slice(0, 5)
}
const curEvent = ref()
const statusDetail = ref(false)
const showDetailsToggle = (event) => {
  console.log('show datail toggle hit')
  curEvent.value = {
    bookingEmail: event.bookingEmail,
    bookingName: event.bookingName,
    categoryId: event.eventCategoryName,
    eventNotes: event.eventNotes,
    eventStartTime: getProperDate(event.eventStartTime),
    eventDuration: event.eventDuration,
    id: event.id
  }
  toggleStatus()
}
const toggleStatus = () => {
  console.log('toggleStatus hit')
  if (statusDetail.value == true) {
    statusDetail.value = false
  } else {
    statusDetail.value = true
  }
}

const toggleTofalse = () => {
  console.log('toggleTofalse hit')
  statusDetail.value = false
}

const updatedEvent = ref()

const getUpdateEvent = (updateEvent) => {
  console.log('getUpdateEvent is working right now here is update event-----')
  console.log(updateEvent)
  updatedEvent.value = updateEvent
  console.log(' here is update event that sendeing to views-----')
  console.log(updatedEvent.value)
  return updatedEvent
}

const dateTime = ref()
const curNameEmail = ref('')
const curCategory = ref('Event Category')
const eventStatus = ref('Event Status')
const filterTopic = ref()

const searchData = computed(() => {
  const filteredEvents = ref(props.events)
  if (curNameEmail.value.length != 0) {
    filterTopic.value = 'name, email matching'
    filteredEvents.value = filteredEvents.value.filter((event) => {
      event.bookingEmail
        .toLowerCase()
        .includes(
          curNameEmail.value.toLocaleLowerCase() ||
            event.bookingName.includes(curNameEmail.value)
        )
    })
  }
  if (curCategory.value != 'Event Category') {
    filterTopic.value = 'category matching'
    filteredEvents.value = filteredEvents.value.filter((event) => {
      return event.eventCategoryName === curCategory.value
    })
  }
  if (dateTime.value != undefined) {
    filterTopic.value = 'date matching'
    filteredEvents.value = filteredEvents.value.filter((event) => {
      const findDate = new Date(dateTime.value).toDateString()
      const eventDate = new Date(event.eventStartTime).toDateString()
      return eventDate === findDate
    })
  }
  if (eventStatus.value != 'Event Status') {
    filteredEvents.value = filteredEvents.value.filter((event) => {
      const eventDate = new Date(event.eventStartTime)
      const currentDate = new Date()
      if (eventStatus.value === 'upComingEvents') {
        filterTopic.value = 'up coming'
        return currentDate <= eventDate
      } else if (eventStatus.value === 'pastEvents') {
        filterTopic.value = 'past'
        return currentDate > eventDate
      }
    })
  }

  return filteredEvents.value
})

const resetFilter = () => {
  curNameEmail.value = ''
  curCategory.value = undefined
  eventStatus.value = 'Event Status'
  dateTime.value = undefined
}
</script>

<template>
  <div class="flex items-center mt-10 justify-center">
    <div
      class="w-full md:w-2/3 shadow p-5 rounded-lg bg-white bg-opacity-40 rounded-3xl"
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

        <!--    class="px-4 py-3 w-full rounded-md bg-gray-100 border-transparent focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"> -->
        <input
          v-model="dateTime"
          type="date"
          :min="formatted_date"
          class="pl-2 bg-white px-4 py-2 pr-2 rounded-md shadow border-transparent focus:border-gray-500 focus:bg-white"
        />
        <select
          v-model="eventStatus"
          class="px-4 py-3 w-full rounded-md bg-white border-transparent shadow focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"
        >
          <option selected disabled hidden>Event Status</option>
          <option value="upComingEvents">Up Coming events</option>
          <option value="pastEvents">Past events</option>
        </select>
        <select
          v-model="curCategory"
          class="px-4 py-3 w-full rounded-md bg-white border-transparent shadow focus:border-gray-500 focus:bg-white focus:ring-0 text-sm"
        >
          <option selected disabled hidden>Event Category</option>
          <option v-for="(category, index) in categories" :key="index">
            {{ category.eventCategoryName }}
          </option>
        </select>
        <button
          @click="resetFilter"
          class="px-4 py-2 bg-red-300 hover:bg-pastel-yellow text-gray-800 shadow text-sm font-medium font-Kanit rounded-md"
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
      :event="curEvent"
      @closePopUp="toggleStatus()"
      @deleteEvent="$emit('deleteEvent', curEvent.id), toggleStatus()"
      @getEditedEvent="getUpdateEvent"
      @updateEvent="$emit('updateThisEvent', updatedEvent), toggleTofalse()"
    />
  </div>

  <div class="flex items-center justify-center">
    <div class="col-span-12">
      <div
        class="scrollTable lg:overflow-visible mt-8 relative z-0 bg-white bg-opacity-40 pt-4 pl-8 pr-8 pb-8 rounded-2xl"
      >
        <table class="table text-blood-bird border-separate space-y-6">
          <thead class="text-lg bg-pastel-orange">
            <th class="p-3">Booking Name</th>
            <th class="p-3 text-left">Clinic Category</th>
            <th class="p-3 text-left">Date</th>
            <th class="p-3 text-left">Time</th>
            <th class="p-3 text-left">Duration</th>
            <th class="p-3 text-left" colspan="2">Action</th>
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
            <tr></tr>
          </tbody>
          <tbody v-else>
            <tr
              v-for="(event, index) in searchData"
              :key="index"
              class="border-b border-blood-bird bg-white text-black text-center hover:bg-orange-50"
            >
              <td class="p-3 font-medium capitalize">
                {{ event.bookingName }}
              </td>
              <td class="p-3 text-left">{{ event.eventCategoryName }}</td>
              <td class="p-3 text-left">
                {{ getDate(event.eventStartTime) }}
              </td>
              <td class="p-3 text-left">
                {{ getTime(event.eventStartTime) }}
              </td>
              <td class="p-3 text-left">{{ event.eventDuration }} minutes</td>
              <td class="justify-center">
                <DetailIcon
                  class="hover:drop-shadow-md"
                  @click="showDetailsToggle(event)"
                />
              </td>
              <td class="">
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
.gradient-delete-color {
  background-image: linear-gradient(to top, #ffffff, #fbbf98);
}

.table {
  border-spacing: 0 15px;
}

.table tr {
  border-radius: 20px;
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

/* .scrollTable::-webkit-scrollbar-track {
  background: #d28d4c;
  border-radius: 10px;
} */

.scrollTable::-webkit-scrollbar-thumb {
  background: #fbbf98;
  border-radius: 10px;
}
</style>
