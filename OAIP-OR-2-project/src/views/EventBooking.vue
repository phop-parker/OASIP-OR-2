<!-- @format -->

<script setup>
import { onBeforeMount, ref, computed } from 'vue'
import AddNewEvent from '../components/AddNewEvent.vue'
import AddAlert from '../components/AddAlert.vue'
import ErrorAlert from '../components/ErrorAlert.vue'

const categories = ref([])
const events = ref([])
const errorDetail = ref([])
const addSuccessStatus = ref(false)
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
    console.log('response reply')
    console.log(res)
    console.log(events.value)
    events.value = await res.json()
  } else console.log("error, cann't get data")
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
    console.log(res)
    categories.value = await res.json()
  } else console.log("error, cann't get data")
}


const checkDateTimeFuture = (inputDate) => {
  console.log(`this is inputDate -> ${inputDate}`)
  if (inputDate != undefined) {
    const date = new Date()
    const newDate = new Date(inputDate)
    console.log(`this is date = ${date}`)
    console.log(`this is inputdate = ${newDate}`)
    if (date < newDate) {
      console.log('date less than input date return true')
      return true
    } else {
      errorDetail.value.push('Please insert future date time')
      console.log('date more than input date return false')
      // alert("please insert future date time")
      return false
    }
  } else {
    errorDetail.value.push('Please insert date and time')
    // alert("please insert date and time.")
    return false
  }
}

const checkInput = (input) => {
  if (input === '' || input === '' || input === null || input === undefined) {
    errorDetail.value.push('Information required.')
    console.log('input is null')
    return false
  } else {
    console.log('input is not null')
    return true
  }
}

const checkInputCategoty = (input) => {
  console.log(`this is category=>${input}`)
  if (input === undefined || input === null || input === 0) {
    // alert('please select category')
    errorDetail.value.push('Please select category')
    console.log('category is null')
    return false
  } else {
    console.log('category is not null')
    return true
  }
}

const checkEmpty = (newEvent) => {
  console.log('now checking...')
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
  if (bookingName.length > 100) {
    errorDetail.value.push('BookingName should be lessthan 100 letters.')
    console.log('bookingName is morethan 100 return false')
    return false
  } else {
    console.log('bookingName is lessthan 100 return true')
    return true
  }
}

const checkLengthEmail = (bookingEmail) => {
  console.log('BookingEmail Check')
  if (bookingEmail.length > 50) {
    errorDetail.value.push('BookingEmail should be lessthan 50 letters.')
    console.log('BookingEmail is morethan 100 return false')
    return false
  } else {
    console.log('BookingEmail is lessthan 50 return true')
    return true
  }
}

const checkLengthNote = (eventNotes) => {
  if (eventNotes == undefined) {
    eventNotes = ''
  }
  if (eventNotes.length > 500) {
    errorDetail.value.push('EventNotes should be lessthan 500 letters.')
    // alert("eventNotes should be lessthan 500 letters.");
    console.log('eventNotes is morethan 500 return false')
    return false
  } else {
    console.log('eventNotes is lessthan 500 return true')
    return true
  }
}

const checkLength = (newEvent) => {
  console.log('newEvent')
  console.log(newEvent)
  if (
    checkLengthEmail(newEvent.bookingEmail) &&
    checkLengthName(newEvent.bookingName) &&
    checkLengthNote(newEvent.eventNotes)
  ) {
    console.log('check length return true')
    return true
  } else {
    console.log('check length return false')
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

const timesOverlap = (inputDate, newEventCategoryName, newEventDuration) => {
  console.log('hello overlap')
  console.log(`this is input Date ${inputDate}`)
  let newEventStartDate = new Date(inputDate)
  let newEventEndDate = eventEndTime(newEventStartDate, newEventDuration)
  let status = ref(true)
  console.log(`-----this is newEventStartDate ${newEventStartDate}----`)
  console.log(`-----this is newEventEndDate ${newEventEndDate}----`)
  for (let i = 0; i < events.value.length; i++) {
    let eventStartDateTime = new Date(
      events.value[i].eventStartTime.replace('@', 'T')
    )
    let eventDuration = events.value[i].eventDuration
    let eventEndDateTime = eventEndTime(eventStartDateTime, eventDuration)
    console.log(`-----overlap check number ${i}----`)
    console.log(`-----this is eventStartTime ${eventStartDateTime}----`)
    console.log(`-----this is eventEndTime ${eventEndDateTime}-----`)
    if (events.value[i].eventCategoryName == newEventCategoryName) {
      console.log(`-----this is CategoryName ${newEventCategoryName}-----`)
      if (
        eventStartDateTime < newEventStartDate &&
        newEventStartDate < eventEndDateTime
      ) {
        console.log('time is overlapping')
        status.value = false
      }
      if (
        eventStartDateTime < newEventEndDate &&
        newEventEndDate < eventEndDateTime
      ) {
        console.log('time is overlapping')
        status.value = false
      }
      if (
        newEventStartDate < eventStartDateTime &&
        eventEndDateTime < newEventEndDate
      ) {
        console.log('time is overlapping')
        status.value = false
      }
    }
  }
  if (status.value == false) {
    errorDetail.value.push('Date or time is overlapping.')
  }
  console.log(`validation return ${status.value}`)
  return status.value
}

function eventEndTime(date, minutes) {
  let dateFormat = new Date(date)
  let endDate = new Date(dateFormat.getTime() + minutes * 60 * 1000)
  return endDate
}

// create
const createNewEvent = async (newEvent) => {
  errorDetail.value = [];
  if (
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
        bookingName: newEvent.bookingName,
        bookingEmail: newEvent.bookingEmail,
        eventStartTime: newEvent.eventStartTime,
        categoryId: newEvent.categoryId.categoryId,
        eventNotes: newEvent.eventNotes
      })
    })
    if (res.status === 200 || res.status === 201) {
      console.log(res.status)
      addSuccessStatus.value = true
    } else {
      let status = res.status
      errorDetail.value.push(`Backend ${status} error, Bad Request !`)
      errorStatus.value = true
      console.log('error cannot add')
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
  <div class="">
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
.bgimage {
  background-image: url('../assets/bg-project.jpg');
  background-repeat: no-repeat;
  background-size: contain, cover;
  background-size: 100% 100%;
  background-attachment: fixed;
}
</style>
