<!-- @format -->
<script setup>

import { onBeforeMount, ref, computed } from 'vue'
import AddNewEvent from '../components/AddNewEvent.vue'
import AddAlert from '../components/AddAlert.vue'
import ErrorAlert from '../components/ErrorAlert.vue'

const categories = ref([])
const events = ref([])
const addSuccessStatus = ref(false)

const errorDetail = ref([])
const errorStatus = ref(false)

onBeforeMount(async () => {
  await getCategories()
  await getEvents()
})

// GET
const getEvents = async () => {
  const res = await fetch(`${import.meta.env.BASE_URL}/api/events`)
  // const res = await fetch(`http://10.4.56.95:8080/api/events`)
  if (res.status === 200) {
    events.value = await res.json()
  } else {}
}

// GET
const getCategories = async () => {
  const res = await fetch(
    `${import.meta.env.BASE_URL}/api/eventCategories/list`
  )
  // const res = await fetch(
  //   `http://10.4.56.95:8080/api/eventCategories/list`
  // )

  if (res.status === 200) {
    categories.value = await res.json()
  } else {}
}


const checkDateTimeFuture = (inputDate) => {
  if (inputDate != undefined) {
    const date = new Date()
    const newDate = new Date(inputDate)
    if (date < newDate) {
      return true
    } else {
      errorDetail.value.push('Please insert future date time')
      return false
    }
  } else {
    errorDetail.value.push('Please insert date and time')
    return false
  }
}

const checkInput = (input) => {
  if (input === '' || input === '' || input === null || input === undefined) {
    errorDetail.value.push('Information required.')
    return false
  } else {
    return true
  }
}

const checkInputCategoty = (input) => {
  if (input === undefined || input === null || input === 0) {
    errorDetail.value.push('Please select category')
    return false
  } else {
    return true
  }
}

const checkEmpty = (newEvent) => {
  if (
    checkInput(newEvent.bookingName) &&
    checkInput(newEvent.bookingEmail) &&
    checkInputCategoty(newEvent.categoryId.categoryId)
  ) {
    return true
  } else {
    return false
  }
}

const checkLengthName = (bookingName) => {
  if (bookingName.trim().length > 100) {
    errorDetail.value.push('BookingName should be lessthan 100 letters.')
    return false
  } else {
    return true
  }
}

const checkLengthEmail = (bookingEmail) => {
  if (bookingEmail.trim().length > 50) {
    errorDetail.value.push('BookingEmail should be lessthan 50 letters.')
    return false
  } else {
    return true
  }
}

const checkLengthNote = (eventNotes) => {
  if (eventNotes == undefined) {
    eventNotes = ''
  }
  if (eventNotes.trim().length > 500) {
    errorDetail.value.push('EventNotes should be lessthan 500 letters.')
    return false
  } else {
    return true
  }
}

const checkLength = (newEvent) => {
  if (
    checkLengthEmail(newEvent.bookingEmail) &&
    checkLengthName(newEvent.bookingName) &&
    checkLengthNote(newEvent.eventNotes)
  ) {
    return true
  } else {
    return false
  }
}

const validateEmail = (newEvent) => {
  let validRegex =
     /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  if (newEvent.bookingEmail.match(validRegex)) {
    return true
  } else {
    errorDetail.value.push('Invalid email address.')
    errorStatus.value = true
    return false
  }
}

function eventEndTime(date, minutes) {
  let dateFormat = new Date(date)
  let endDate = new Date(dateFormat.getTime() + minutes * 60 * 1000)
  return endDate
}

const timesOverlap = (inputDate, newEventCategoryName, newEventDuration) => {
  let newEventStartDate = new Date(inputDate)
  let newEventEndDate = eventEndTime(newEventStartDate, newEventDuration)
  let status = ref(true)
  let eventCategoryMatch = events.value.filter((event)=>event.eventCategoryName==newEventCategoryName);
  for (let i = 0; i < eventCategoryMatch.length; i++) {
    let eventStartDateTime = new Date(eventCategoryMatch[i].eventStartTime.replace('@', 'T'))
    let eventDuration = eventCategoryMatch[i].eventDuration
    let eventEndDateTime = eventEndTime(eventStartDateTime, eventDuration)
      if ((eventStartDateTime < newEventStartDate &&newEventStartDate < eventEndDateTime)||
          (eventStartDateTime < newEventEndDate && newEventEndDate < eventEndDateTime)||
          (newEventStartDate < eventStartDateTime &&eventEndDateTime < newEventEndDate)||
          (eventStartDateTime.valueOf() == newEventStartDate.valueOf())
          ||(eventEndDateTime.valueOf() == newEventEndDate.valueOf())) {
        status.value = false
      }
  }
  if (status.value == false) {
    errorDetail.value.push('Date or time is overlapping.')
  }
  return status.value
}


const checkConstarints = (bookingName,eventStartTime) => {
  let date = new Date(eventStartTime);
  const status = ref(true)
  for (let i = 0; i < events.value.length; i++) {
    let eventStartDateTime = new Date(events.value[i].eventStartTime.replace('@', 'T'))
    if(events.value[i].bookingName.toLowerCase() == bookingName.toLowerCase() && eventStartDateTime.valueOf() == date.valueOf()){
      errorDetail.value.push("Someone already booked by this name and date")
      status.value = false
    }
  }
  console.log(status.value)
  return status.value
}

// create
const createNewEvent = async (newEvent) => {
  errorDetail.value = [];
  if (
    checkConstarints(newEvent.bookingName,newEvent.eventStartTime)&&
    checkEmpty(newEvent) &&
    checkDateTimeFuture(newEvent.eventStartTime) &&
    validateEmail(newEvent) &&
    checkLength(newEvent) &&
    timesOverlap(
      newEvent.eventStartTime,
      newEvent.categoryId.eventCategoryName,
      newEvent.categoryId.eventDuration
    )
  ) {
    // const res = await fetch(`http://10.4.56.95:8080/api/events`, {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/events`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        bookingName: newEvent.bookingName.trim(),
        bookingEmail: newEvent.bookingEmail.trim(),
        eventStartTime: newEvent.eventStartTime,
        categoryId: newEvent.categoryId.categoryId,
        eventNotes: newEvent.eventNotes
      })
    })
    if (res.status === 200 || res.status === 201) {
      addSuccessStatus.value = true
    } else {
      let status = res.status
      errorDetail.value.push(`Backend ${status} error, Bad Request !`)
    }
  }
  else {
    errorStatus.value = true
  }
}

const toggleAddSuccessStatus = () => {
  if (addSuccessStatus.value) {
    addSuccessStatus.value = false
  } else {
    addSuccessStatus.value = true
  }
}

const toggleErrorStatus = () => {
  if (errorStatus.value) {
    errorStatus.value = false
  } else {
    errorStatus.value = true
  }
}
</script>

<template>
  <div>
    <div>
      <AddNewEvent
        :eventCategories="categories"
        @addNewEvent="createNewEvent"
      />
    </div>
    <div
      v-if="addSuccessStatus == true"
      class="absolute inset-0 flex justify-center items-center z-20"
    >
      <AddAlert @ConfirmAndGoToAnotherPage="toggleAddSuccessStatus" />
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
