<!-- @format -->

<script setup>
import CloseIcon from '../icon/CloseIcon.vue'
import { ref, computed } from 'vue'

defineEmits(['closePopUp', 'deleteEvent', 'updateEvent', 'getEditedEvent'])

const props = defineProps({
  event: {
    type: Object,
    default: {}
  }
})

const editMode = ref(false)

const getDate = (dateTime) => {
  const date = new Date(dateTime)
  return date.toDateString()
}
const getTime = (dateTime) => {
  const date = new Date(dateTime)
  return date.toTimeString().slice(0, 5)
}

const newEvent = computed(() => {
  return {
    id: props.event.id,
    eventStartTime: props.event.eventStartTime,
    eventNotes: props.event.eventNotes
  }
})

const toggleEditMode = () => {
  if (editMode.value) {
    editMode.value = false
  } else {
    editMode.value = true
  }
}
</script>
<template>
  <div class="absolute bg-black opacity-20 inset-0 z-0"></div>
  <div
    class="box-content detail-box p-8 pb-8 rounded-3xl bg-opacity-90 bg-pastel-yellow drop-shadow-3xl font-Kanit z-10"
  >
    <div
      class=" detail-box grid grid-rows-8 gap-4 bg-white shadow-lg rounded-2xl p-7 pb-10 "
    >
      <div class="place-self-end">
        <CloseIcon
          class="hover:drop-shadow-5xl"
          @click="$emit('closePopUp'), (editMode = false)"
        />
      </div>
      <div class="text-center text-black bg-white bg-opacity-20 mt-0">Details</div>
      <div class="pl-3  drop-shadow-xl bg-white bg-opacity-20 rounded-2xl">
        name : {{ event.bookingName }}
      </div>
      <div class="pl-3  drop-shadow-xl bg-white bg-opacity-20 rounded-2xl">
        email : {{ event.bookingEmail }}
      </div>
      <div class="pl-3  drop-shadow-xl bg-white bg-opacity-20 rounded-2xl">
        Category : {{ event.categoryId }}
      </div>
      <div class="pl-3  drop-shadow-xl bg-white bg-opacity-20 rounded-2xl">
        Duration : {{ event.eventDuration }} minutes
      </div>
      <div v-if="editMode == true">
        Date :<input
          type="datetime-local"
          v-model="newEvent.eventStartTime"
          class="pl-3 border border-gray-400 bg-white hover:border-gray-500 px-4 py-2 rounded-2xl shadow leading-tight focus:outline-none focus:shadow-outline"
        />
      </div>
      <div v-else>
        <div
          class="pl-3 pb-1 drop-shadow-xl bg-white bg-opacity-20 rounded-2xl"
        >
          Date : {{ getDate(event.eventStartTime) }} Time :{{
            getTime(event.eventStartTime)
          }}
        </div>
      </div>
      <div v-if="editMode == true">
        Note :
        <textarea
          v-model="newEvent.eventNotes"
          class="pl-8 pr-4 w-96 h-32 bg-white border border-gray-400 hover:border-gray-500 rounded-2xl shadow leading-tight focus:outline-none focus:shadow-outline resize-none"
          rows="5"
          cols="100"
        />
      </div>
      <div
        v-else-if="event.eventNotes == '' || event.eventNotes == null"
        class="pl-3 drop-shadow-xl bg-white bg-opacity-10"
      >
        Note : -
      </div>
      <div
        v-else
        class="pl-3 pb-1 drop-shadow-xl bg-white bg-opacity-20 rounded-2xl"
      >
        Note : {{ event.eventNotes }}
      </div>
      <div class="pt-4 flex gap-4 justify-center">
        <button
          v-if="editMode == true"
          @click="
            $emit('getEditedEvent', newEvent),
              $emit('updateEvent'),
              toggleEditMode()
          "
          class="bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
        >
          submit
        </button>
        <button
          v-else
          @click="toggleEditMode()"
          class="bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
        >
          edit
        </button>
        <button
          v-if="editMode == true"
          @click="toggleEditMode()"
          class="bg-dark-pink hover:bg-yellow-100 hover:text-dark-pink text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
        >
          cancel
        </button>
        <button
          v-else
          @click="$emit('deleteEvent', event.id)"
          class="bg-dark-pink hover:bg-yellow-100 hover:text-dark-pink text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
        >
          delete
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>

.detail-box {
  height: 550px;
  width: 550px;
}


</style>
