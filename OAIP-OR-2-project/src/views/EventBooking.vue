<script setup>

import { onBeforeMount, ref, computed } from 'vue'
import AddNewEvent from '../components/AddNewEvent.vue';

let categories = ref([])
// get
const getCategories = async () => {
    const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCatagories/forBooking`)
    // const res = await fetch(`http://10.4.56.95:8080/api/eventCategories/forBooking`)

    if (res.status === 200) {
        console.log(res);
        categories.value = await res.json()
    } else
        console.log("error, cann't get data");
}
onBeforeMount(async () => {
    await getCategories()
    console.log(categories)
})

const checkDateTimeFuture = (inputDate) => {
    const date = new Date()
    const newDate = new Date(inputDate)
    console.log(`this is date = ${date}`)
    console.log(`this is inputdate = ${newDate}`)
    if (date < newDate) {
        console.log('date less than input date return true')
        return true
    } else {
        console.log('date more than input date return false')
        return false
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
// เหลือดูเรื่อง checkCategory

const checkEmpty = (newEvent) => {
    console.log("now checking...")
    if (checkInput(newEvent.bookingName) && checkInput(newEvent.bookingEmail)) {
        console.log("check empty return true")
        return true
    } else {
        console.log("check empty return false")
        return false
    }
}


const checkLengthName = (bookingName) => {
    if (bookingName.length > 100) {
        alert("ใส่เกิน100ตัวจร้า");
        console.log("เกินร้อย")
        return false
    } else {
        console.log("ตัวอักษรไม่เกิน")
        return true
    }
}

const checkLengthNote = (eventNotes) => {
    if (eventNotes == undefined) {
        eventNotes = "";
    }
    if (eventNotes.length > 500) {
        alert("ใส่เกิน500ตัวจร้า");
        console.log("เกิน500")
        return false
    } else {
        console.log("ตัวอักษรไม่เกิน")
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

var timesOverlap = function (inputDate) {
    inputDate.sort(function (a, b) { return a.start - b.start; });
    for (let i = 0; i < dates.length - 1; i++) {
        if (inputDate[i].end >= inputDate[i + 1].start) {
            return true; // dates overlap
        }
    }
    return false; // no dates overlap ยังทำไรมะได้
};

// create
const createNewEvent = async (newEvent) => {
    if (checkDateTimeFuture(newEvent.eventStartTime) && checkEmpty(newEvent) && validateEmail(newEvent) && checkLength(newEvent)) {
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