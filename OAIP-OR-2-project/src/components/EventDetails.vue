<script setup>
import CloseIcon from "./CloseIcon.vue";

defineEmits(['closePopUp'])

const props = defineProps({
    event: {
        type: Object,
        default: {}
    }
})

console.log(props.event)


const getDate = (dateTime) => {
    const date = new Date(dateTime);
    return date.toDateString()
}

const getTime = (dateTime) => {
    const date = new Date(dateTime);
    return date.toTimeString().slice(0, 8)
}

const getCategory = (event) => {
    console.log(event)
    return event;
}
</script>
 
<template>

    <div class="relative box-content p-8 pb-12 rounded-3xl gradient-color drop-shadow-2xl font-Kanit ">
        <div class="grid grid-rows-8 gap-4">
            <div class="place-self-end">
                <CloseIcon class="hover:drop-shadow-xl" @click="$emit('closePopUp')" />
            </div>
            <div>
                Event Details
            </div>
            <div>
                note : {{ event.bookingName }}
            </div>
            <div>
                email : {{ event.bookingEmail }}
            </div>
            <div>
                Date :{{ getDate(event.eventStartTime) }} Time :{{ getTime(event.eventStartTime) }}
            </div>
            <div>
                Category : {{ event.categoryId }}
            </div>
            <div>
                Duration : {{ event.eventDuration }} minutes
            </div>
            <div v-if='event.eventNotes == ""'>
                Note : -
            </div>
            <div v-else>
                Note : {{ event.eventNotes }}
            </div>
        </div>
    </div>
</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>