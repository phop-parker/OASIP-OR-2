<script setup>
import CloseIcon from "./CloseIcon.vue";

defineEmits(['closePopUp'])

const props = defineProps({
    event: {
        type: Object,
        default: {}
    }
})

</script>
 
<template>

    <div class="box-content p-8 pb-12 rounded-3xl gradient-color drop-shadow-2xl font-Kanit ">
        <div class="grid grid-rows-8 gap-4 ">
            <div class="place-self-end">
                <CloseIcon class="hover:drop-shadow-xl " @click="$emit('closePopUp')" />
            </div>
            <div class="bg-pastel-yellow rounded-2xl text-center text-blood-bird ">
                Event Details
            </div>
            <div class="bg-white rounded-2xl pl-3 pb-4 drop-shadow-xl">
                note : {{ event.bookingName }}
            </div>
            <div class="bg-white rounded-2xl pl-3 pb-4 drop-shadow-xl">
                email : {{ event.bookingEmail }}
            </div>
            <div class="bg-white rounded-2xl pl-3 pb-4 drop-shadow-xl">
                Date :{{ event.eventStartDate }} Time :{{ event.eventStartTime }}
            </div>
            <div class="bg-white rounded-2xl pl-3 pb-4 drop-shadow-xl">
                Category : {{ event.categoryId }}
            </div>
            <div class="bg-white rounded-2xl pl-3 pb-4 drop-shadow-xl">
                Duration : {{ event.eventDuration }} minutes
            </div>
            <div v-if='event.eventNotes == ""' class="bg-white rounded-3xl pl-3 drop-shadow-xl">
                Note : -
            </div>
            <div v-else class="bg-white rounded-2xl pl-3 pb-6 drop-shadow-xl  ">
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