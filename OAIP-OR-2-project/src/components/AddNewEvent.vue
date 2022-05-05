<script setup>
import { ref, computed } from 'vue'

defineEmits(['addNewEvent'])

const props = defineProps({
    eventCategories: {
        type: Array,
        default: []
    },
    Catagoty: {
        type: String,
        default: ""
    },
})



const categories = ref([]);

const allCategories = computed(() => {
    for (let i = 0; i < props.eventCategories.length; i++) {
        categories.value.push(props.eventCategories[i])
    }
    return categories;
})

// const getCategoryId = (curCategory) => { 
//     console.log("finding id of"+curCategory)
//     return props.Catagoty.indexOf(e => e.eventCategoryName == curCategory) }

const curCategory = ref()
const bookingEmail = ref()
const bookingName = ref()
const eventNotes = ref()
const dateTime = ref()

const getDateTime = () => {
    let date = new Date(dateTime.value)
    // return date.value+"T"+time.value+".00Z"
    return date;
}
const duration = computed(() => {
    const currentDuration = ref('-');
    for (let i = 0; i < props.eventCategories.length; i++) {
        if (curCategory.value == props.eventCategories[i].eventCategoryName) {
            currentDuration.value = props.eventCategories[i].eventDuration;
        }
    }
    return currentDuration.value;
})

const categoryId = computed(() => {
    const currentCategoryId = ref(0);
    for (let i = 0; i < props.eventCategories.length; i++) {
        if (curCategory.value == props.eventCategories[i].eventCategoryName) {
            currentCategoryId.value = props.eventCategories[i];
        }
    }
    return currentCategoryId.value;
})

const newEvent = computed(() => {
    return {
        bookingName: bookingName.value,
        bookingEmail: bookingEmail.value,
        eventStartTime: getDateTime(),
        eventDuration: duration.value,
        categoryId: categoryId.value,
        eventNotes: eventNotes.value
    }
})


</script>

<template>
    <div class=" box-content p-5 mt-5 mb-4 ml-20 mr-20 bg-white rounded-3xl font-Kanit drop-shadow-2xl">
        <div class="gradient-color rounded-3xl justify-center">
            <p class="font-medium font-Kanit text-center pt-2 pb-2 text-blood-bird ">
                Add New Event
            </p>
        </div>
        <div class="grid grid-cols-2 gap-8 mt-4">
            <div class="">
                <p>Name :</p>
                <input v-model="bookingName" type="text"
                    class="pl-2 pr-2 border border-gray-400 hover:border-gray-500 rounded-3xl shadow leading-tight focus:outline-none focus:shadow-outline" />
            </div>
            <div class="">
                <p>Email :</p>
                <input v-model="bookingEmail" type="email"
                    class="pl-2 pr-2 border border-gray-400 hover:border-gray-500 rounded-3xl shadow leading-tight focus:outline-none focus:shadow-outline" />
            </div>
            <div class="">
                <p>Category :</p>
                <div class="inline-block relative w-64">
                    <select v-model="curCategory"
                        class="pl-2 pr-2 block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 rounded-3xl shadow leading-tight focus:outline-none focus:shadow-outline">
                        <option v-for="(category, index) in eventCategories" :key="index">
                            {{ category.eventCategoryName }}
                        </option>
                    </select>
                    <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center text-gray-700 ">
                        <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                            <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
                        </svg>
                    </div>
                </div>
            </div>
            <div>
                <p>Duration : {{ duration }} minutes</p>
            </div>
            <div class="col-span-2">
                <p>Time :</p>
                <input v-model="dateTime" type="datetime-local"
                    class="pl-2 pr-2 border border-gray-400 hover:border-gray-500 px-4 py-2 pr-7 rounded-3xl shadow leading-tight focus:outline-none focus:shadow-outline" />
                {{ time }}
            </div>

            <div class="col-span-2">
                <p>Note :</p>
                <textarea v-model="eventNotes"
                    class="w-full p-6 border border-gray-400 hover:border-gray-500 rounded-3xl shadow leading-tight focus:outline-none focus:shadow-outline resize-none"
                    rows="5" cols="100" />
            </div>
            <div class="col-span-2 flex justify-center ">
                <button @click="$emit('addNewEvent', newEvent)"
                    class="gradient-color hover:bg-yellow-100  py-2 px-4 rounded-3xl">
                    BOOK EVENT
                </button>
            </div>
        </div>
    </div>
</template>

<style>
.gradient-color {
    background-image: linear-gradient(to left, #FBBF98, #FFC2C2);
}
</style>
