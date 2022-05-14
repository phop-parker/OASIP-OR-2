<script setup>

import { onBeforeMount, ref, computed } from 'vue'
import AddNewEvent from '../components/AddNewEvent.vue';

let categories = ref([])
let events = ref([])

// GET
const getEvents = async () => {
    // const res = await fetch(`${import.meta.env.BASE_URL}/api/events`)
    const res = await fetch(`http://10.4.56.95:8080/api/events`)
    if (res.status === 200) {
        console.log("response reply")
        console.log(res);;
        console.log(events.value);
        events.value = await res.json()
    } else
        console.log("error, cann't get data");
}

// GET
const getCategories = async () => {
    // const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCategories/forBooking`)
    const res = await fetch(`http://10.4.56.95:8080/api/eventCategories/forBooking`)

    if (res.status === 200) {
        console.log(res);
        categories.value = await res.json()
    } else
        console.log("error, cann't get data");
}
onBeforeMount(async () => {
    await getCategories()
    await getEvents()
})

const checkDateTimeFuture = (inputDate) => {
    console.log(`this is inputDate -> ${inputDate}`)
    if (inputDate != undefined) {
        const date = new Date()
        const newDate = new Date(inputDate)
        console.log(`this is date = ${date}`)
        console.log(`this is inputdate = ${newDate}`)
        if (date < newDate) {
            console.log('date less than input date return true')
            return true;
        } else {
            console.log('date more than input date return false')
            return false;
        }
    } else {
        alert("please insert date and time.")
        return false;
    }
}

const checkInput = (input) => {
    if (input === '' || input === "" || input === null || input === undefined) {
        alert('input is null')
        console.log("input is null")
        return false
    } else {
        console.log("input is not null")
        return true
    }
}


const checkInputCategoty = (input) => {
    console.log(`this is category=>${input}`)
    if (input === undefined || input === null || input === 0) {
        alert('category please select category')
        console.log("category is null")
        return false
    } else {
        console.log("category is not null")
        return true
    }
}

const checkEmpty = (newEvent) => {
    console.log("now checking...")
    if (checkInput(newEvent.bookingName) && checkInput(newEvent.bookingEmail) && checkInputCategoty(newEvent.categoryId.id)) {
        console.log("check empty return true")
        return true
    } else {
        console.log("check empty return false")
        return false
    }
}


const checkLengthName = (bookingName) => {
    if (bookingName.length > 100) {
        alert("bookingName should be lessthan 100 letters.");
        console.log("bookingName is morethan 100 return false")
        return false
    } else {
        console.log("bookingName is lessthan 100 return false")
        return true
    }
}

const checkLengthNote = (eventNotes) => {
    if (eventNotes == undefined) {
        eventNotes = "";
    }
    if (eventNotes.length > 500) {
        alert("eventNotes should be lessthan 500 letters.");
        console.log("eventNotes is morethan 500 return false")
        return false
    } else {
        console.log("eventNotes is lessthan 500 return true")
        return true
    }
}


const checkLength = (newEvent) => {
    if (checkLengthName(newEvent.bookingName) && checkLengthNote(newEvent.eventNotes)) {
        console.log("check length return true")
        return true
    } else {
        console.log("check length return false")
        return false
    }
}


const validateEmail = (newEvent) => {
    let validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
    if (newEvent.bookingEmail.match(validRegex)) {
        alert("Valid email address!");
        return true
    } else {
        alert("Invalid email address!");
        return false
    }
}


const timesOverlap = (inputDate, newEventCategoryName, newEventDuration) => {
    console.log("hello overlap")
    let newDate = new Date(inputDate);
    let endDateTime = eventEndTime(newDate, newEventDuration);
    let status = ref(true)
    for (let i = 0; i < events.value.length; i++) {
        let eventStartDateTime = new Date(events.value[i].eventStartTime);
        let eventEndDateTime = eventEndTime(eventStartDateTime, events.value[i].eventDuration);
        console.log(`-----overlap check number ${i}----`)
        console.log(`-----this is newDate ${newDate}----`)
        console.log(`-----this is eventStartTime ${eventStartDateTime}----`)
        console.log(`-----this is eventEndTime ${eventStartDateTime}-----`)
        console.log(`-----this is newCategoryName ${newEventCategoryName}-----`)
        console.log(`-----this is categoryname ${events.value[i].eventCategoryName}-----`)
        if (events.value[i].eventCategoryName == newEventCategoryName) {
            if (eventStartDateTime <= newDate && newDate <= eventEndDateTime)
                console.log("time is overlapping")
            alert("time is overlapping")
            status.value = false;
        }
        if (eventStartDateTime <= endDateTime && endDateTime <= eventEndDateTime) {
            alert("time is overlapping")
            status.value = false;
        }
        if (newDate < eventStartDateTime && eventEndDateTime < endDateTime) {
            alert("time is overlapping")
            status.value = false;
        }
    }
    console.log(`validation return ${status.value}`)
    return status.value;
};

const eventEndTime = (date, minutes) => {
    return new Date(date.getTime() + minutes * 60000);
}


// create
const createNewEvent = async (newEvent) => {
    if (checkDateTimeFuture(newEvent.eventStartTime) && checkEmpty(newEvent)
        && validateEmail(newEvent) && checkLength(newEvent) && timesOverlap(newEvent.eventStartTime, newEvent.categoryId.eventCategoryName)) {
        const res = await fetch(`http://10.4.56.95:8080/api/events`, {
            // const res = await fetch(`${import.meta.env.BASE_URL}/api/events`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify({
                bookingName: newEvent.bookingName,
                bookingEmail: newEvent.bookingEmail,
                eventStartTime: newEvent.eventStartTime,
                eventDuration: newEvent.eventDuration,
                categoryId: newEvent.categoryId,
                eventNotes: newEvent.eventNotes
            }
            )
        })
        if (res.status === 200 || res.status === 201) {
            console.log(res.status)
            alert('added suceccfully')
        } else {
            console.log(res.status)
            console.log('error cannot add')
        }

    } else {
        alert('cannot add')
    }
}

</script>
 
<template>
    <div class="">
        <AddNewEvent :eventCategories="categories" @addNewEvent="createNewEvent" />
    </div>
</template>

<style scoped>
.bgimage {
    background-image: url("../assets/bg-wave.png");
    background-repeat: no-repeat;
    /* background-size: contain, cover; */
    background-size: 100% 100%;

}
</style>