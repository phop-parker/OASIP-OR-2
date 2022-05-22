<!-- @format -->

<script setup>
import EventList from '../components/EventList.vue'
import DeleteComfirm from '../components/DeleteComfirm.vue'
import SuccessAlert from '../components/SuccessAlert.vue'

import { onBeforeMount, ref } from 'vue'

const events = ref([])
const categories = ref([])

const successDesc = ref()
const successStatus = ref(false)

const deleteConfirm = ref(false)
const deleteThisEvent = ref(0)

const errorDetail = ref([])
const errorStatus = ref(false)


onBeforeMount(async () => {
  await getEvents()
  await getCategories()
})

// GET
const getEvents = async () => {
  const res = await fetch(`${import.meta.env.BASE_URL}/api/events`)
  // const res = await fetch(`http://10.4.56.95:8080/api/events`)
  if (res.status === 200) {
    events.value = await res.json()
  } else {}
}

// DELETE
const deleteEvent = async (deleteEventId) => {
  if (deleteEventId > 0) {
    const res = await fetch(
      `${import.meta.env.BASE_URL}/api/events/${deleteEventId}`,
       {
        // const res = await fetch(
        //   `http://10.4.56.95:8080/api/events/${deleteEventId}`,
        //   {
        method: 'DELETE'
      }
    )
    if (res.status === 200) {
      events.value = events.value.filter((event) => event.id !== deleteEventId)
      successDesc.value = 'Delete'
      successStatus.value = true
      cancelDelete()
    } else {
      alert('error cannot delete')
      cancelDelete()
    }
  } else {
    alert('no event to delete')
    cancelDelete()
  }
  setTimeout(successToggle, 2000)
}


// GET
const getCategories = async () => {
  const res = await fetch(
    `${import.meta.env.BASE_URL}/api/eventCategories/forBooking`
  )
  // const res = await fetch(
  //   `http://10.4.56.95:8080/api/eventCategories/forBooking`
  // )
  if (res.status === 200) {
    categories.value = await res.json()
  } else {}
}


//PATCH
const updateEvent = async (updateEvent) => {
  if(timesOverlap(updateEvent.eventStartTime,updateEvent.categoryName,updateEvent.duration,updateEvent.id)
  &&checkLengthNote(updateEvent.eventNotes)){
  const res = await fetch(
    `${import.meta.env.BASE_URL}/api/events/${updateEvent.id}`,
    {
      // const res = await fetch(
      //   `http://10.4.56.95:8080/api/events/${updateEvent.id}`,
      //   {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        eventStartTime: updateEvent.eventStartTime,
        eventNotes: updateEvent.eventNotes
      })
    }
  )
  if (res.status === 200) {
    const modifyEvent = await res.json()
    const date = modifyEvent.eventStartTime.toString().replace(' ', '@')
    events.value = events.value.map((event) =>
      event.id === modifyEvent.id
        ? { ...event, eventStartTime: date, eventNotes: modifyEvent.eventNotes }
        : event
    )
    successStatus.value = true
    successDesc.value = 'Update'
  } else {
    alert('error cannot add')
  }}else{
    errorStatus.value = true
  }
  setTimeout(successToggle, 2000)
}

//toggle 
const successToggle = () =>
  successStatus.value === false
    ? (successStatus.value = true)
    : (successStatus.value = false)

const deleteEventConfirm = (eventId) => {
  deleteConfirm.value = true
  deleteThisEvent.value = eventId
}
const cancelDelete = () => {
  deleteConfirm.value = false
  deleteThisEvent.value = 0
}

//Checking State
const timesOverlap = (inputDate, newEventCategoryName, newEventDuration,eventId) => {
  let newEventStartDate = new Date(inputDate)
  let newEventEndDate = eventEndTime(newEventStartDate, newEventDuration)
  let status = ref(true)
  for (let i = 0; i < events.value.length; i++) {
    if(events.value[i].id != eventId){
    let eventStartDateTime = new Date(events.value[i].eventStartTime.replace('@', 'T'))
    let eventDuration = events.value[i].eventDuration
    let eventEndDateTime = eventEndTime(eventStartDateTime, eventDuration)

    if (events.value[i].eventCategoryName == newEventCategoryName) {
    if ((eventStartDateTime < newEventStartDate &&newEventStartDate < eventEndDateTime)||
          (eventStartDateTime < newEventEndDate && newEventEndDate < eventEndDateTime)||
          (newEventStartDate < eventStartDateTime &&eventEndDateTime < newEventEndDate)||
          (eventStartDateTime.valueOf() == newEventStartDate.valueOf())
          ||(eventEndDateTime.valueOf() == newEventEndDate.valueOf())) {
        status.value = false
      }
    }
  }
  if (status.value == false) {
    errorDetail.value.push('Date or time is overlapping.')
  }}
  return status.value
}

const checkLengthNote = (eventNotes) => {
  if (eventNotes == undefined) {
    eventNotes = ''
  }
  if (eventNotes.length > 500) {
    errorDetail.value.push('EventNotes should be lessthan 500 letters.')
    return false
  } else {
    return true
  }
}

</script>
<template>
  <div>
    <div>
      <EventList
        :events="events"
        :categories="categories"
        @deleteEvent="deleteEventConfirm"
        @updateThisEvent="updateEvent"
      />
    </div>

    <div
      v-if="deleteConfirm == true"
      class="absolute inset-0 flex justify-center items-center z-20"
    >
      <DeleteComfirm
        @cancelConfirmation="cancelDelete"
        @yesConfirmation="deleteEvent(deleteThisEvent)"
      />
    </div>

    <div
      v-if="successStatus == true"
      class="absolute inset-0 flex justify-center items-center z-20"
    >
      <SuccessAlert :successTitle="successDesc" />
    </div>
        <div
      v-if="errorStatus == true"
      class="absolute inset-0 flex justify-center items-center z-20"
    >
      <ErrorAlert :errorTitle="errorDetail" @backTo="toggleErrorStatus" />
    </div>
  </div>
</template>

<style scoped>

</style>
