<script setup>

import { onBeforeMount, ref, computed } from 'vue'
import AddNewEvent from '../components/AddNewEvent.vue';

let categories = ref([])
// get
const getCategories = async () => {
    // const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCatagories`)
    const res = await fetch(`http://10.4.56.95:8080/api/eventCatagories`)

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
    console.log(`this is date = ${date}`)
    console.log(`this is inputdate = ${inputDate}`)
    if (date < inputDate) {
        console.log('date less than input date return true')
        return true
    } else {
        console.log('date more than input date return false')
        return false
    }
}

const checkNameEmpty = (bookingName) => {
    if (bookingName === '' || bookingName === "" || bookingName === null || bookingName === undefined) {
        alert('please insert name')
        console.log("name is null")
        return false
    } else {
        console.log("name is not null")
        return true
    }
}

const checkEmailEmpty = (bookingEmail) => {
    console.log(`this is bookingEmail ${bookingEmail}`)
    if (bookingEmail === '' || bookingEmail === "" || bookingEmail === null || bookingEmail === undefined) {
        console.log("email is null")
        alert('please insert email')
        return false
    } else {
        console.log("email is not null")
        return true
    }
}


// const checkCategoryEmpty = (categoryName) => {
//     if (categoryName === null) {
//         alert('please select category')
//         console.log("category is null")
//         return false
//     } else {
//         console.log("category is not null")
//         return true
//     }
// }

const checkEmpty = (newEvent) => {
    console.log("now checking...")
    if (checkNameEmpty(newEvent.bookingName) && checkEmailEmpty(newEvent.bookingEmail)) {
        console.log("check empty return true")
        return true
    } else {
        console.log("check empty return false")
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

// create
const createNewEvent = async (newEvent) => {
    // const res = await fetch(`${import.meta.env.BASE_URL}/api/events`, {
    if (checkDateTimeFuture(newEvent.eventStartTime) && checkEmpty(newEvent) && validateEmail(newEvent)) {
        const res = await fetch(`http://10.4.56.95:8080/api/events`, {
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